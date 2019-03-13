package com.contract.manager.util;

import com.contract.manager.model.OfficePlaceholder;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class POIUtil
{
    public static void generateDocWithDatas(Map<String, Object> datas, File file) {
        try {
            FileInputStream source = new FileInputStream(file);
            XWPFDocument doc = new XWPFDocument( source );

            for( XWPFParagraph p : doc.getParagraphs() ) {
                List<XWPFRun> runs = p.getRuns();
                // 拼接一个段落出来
                if( runs == null ) {
                    continue;
                }
                // 读取占位符
                Set<List<XWPFRun>> result = isExistPlaceholder( runs );

                // 替换占位符
                for( List<XWPFRun> item : result ) {
                    replacePlaceholder( p, item, datas );
                }
            }

            // 批量生成table数据
            // 取得table
            List<XWPFTable> tables = doc.getTables();
            // 取得填充数据
//            List<List<String>> tableDatas = new LinkedList<List<String>>();
//            List<String> row1Data = new LinkedList<String>() {{
//                add( "产品名称1" );
//                add( "产地1" );
//                add( "品牌1" );
//                add( "包装1" );
//                add( "价格1" );
//                add( "交货地点1" );
//                add( "交货数量1" );
//            }};
//            List<String> row2Data = new LinkedList<String>() {{
//                add( "产品名称2" );
//                add( "产地2" );
//                add( "品牌2" );
//                add( "包装2" );
//                add( "价格2" );
//                add( "交货地点2" );
//                add( "交货数量2" );
//            }};
//            tableDatas.add( row1Data );
//            tableDatas.add( row2Data );

            List< List<List<String>> > tableDatas = ( List< List< List<String> > > )datas.get( "tableColl" );

            if( tables != null && tables.size() > 0 && tableDatas != null && tableDatas.size() > 0 ) {
                // 获取到传输过来的table数据
                List<XWPFTable> templateTableList = doc.getTables();
                // TODO: 判断边界值
                for( int i = 0; i < templateTableList.size(); i++ ) {
                    replaceTable( templateTableList.get(i), tableDatas.get( i ) );
                }
            }

            doc.write( new FileOutputStream( file ) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
		}
    }

    public static void generateThumbnailImageFromWord( String fileName, String thumbnailImageDir ) {
        try {
            XWPFDocument wordDocument = new XWPFDocument(new FileInputStream( fileName ));
            POIXMLProperties props = wordDocument.getProperties();

            String thumbnail = props.getThumbnailFilename();
            if (thumbnail == null) {
                // No thumbnail 
            } else {
                FileOutputStream fos;
                fos = new FileOutputStream( thumbnailImageDir + "/" + thumbnail );
                IOUtils.copy(props.getThumbnailImage(), fos);
            }
            wordDocument.close();
        } catch ( Exception exception ) {
            exception.printStackTrace();
        }
    }

    public static List<OfficePlaceholder> generateParamsFromDocs(String fileName, String regex ) {
        List<OfficePlaceholder> params = new ArrayList<OfficePlaceholder>();
        Pattern pattern = Pattern.compile( regex );

        try {
            FileInputStream source = new FileInputStream(fileName);
            XWPFDocument doc = new XWPFDocument( source );

            for (XWPFParagraph p : doc.getParagraphs()) {
                String text = "";
                for( XWPFRun run : p.getRuns() ) {
                    text += run.text();
                }

                Matcher matcher = pattern.matcher( text );
                if( matcher.find() ) {
                    String paramStr = matcher.group();
                    paramStr = paramStr.substring( 2, paramStr.length() - 1 );
                    String[] paramItem = paramStr.split( "\\," );
                    OfficePlaceholder placeholder = new OfficePlaceholder();
                    for( String item : paramItem ) {
                        String[] keyValPair = item.split( "\\=" );
                        if( keyValPair[0].trim().equals( "key") ) {
                            placeholder.setName( keyValPair[1].trim() );
                        } else if( keyValPair[0].trim().equals("desc") ) {
                            placeholder.setDesc( keyValPair[1].trim() );
                        }
                    }
                    params.add( placeholder );
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return params;
    }

    public static void testDemo( String fileName, String targetFile ) {
        try {
            // System.out.println( fileName );
            File file = new File( fileName );
            FileInputStream source = new FileInputStream(fileName);
            FileOutputStream target = new FileOutputStream( targetFile );

            XWPFDocument doc = new XWPFDocument( source );
            //
            for( XWPFParagraph p : doc.getParagraphs() ) {
                List<XWPFRun> runs = p.getRuns();
                // 拼接一个段落出来
                if( runs == null ) {
                    continue;
                }
                // 读取占位符
                Set<List<XWPFRun>> result = isExistPlaceholder( runs );

                // 替换占位符
                for( List<XWPFRun> item : result ) {
                    Map<String,Object> params = new HashMap<String,Object>();
                    params.put( "testName", "testName" );
                    replacePlaceholder( p, item, params );
                }
            }

            doc.write( target );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从一个list中检查占位符是否存在,如果存在，返回占位符信息
     * @param runs
     * @return
     */
    private static Set<List<XWPFRun>> isExistPlaceholder( List<XWPFRun> runs ) {
        String startSymbol = "${", endSymbol="}";
        Set<List<XWPFRun>> result = new HashSet<List<XWPFRun>>();
        LinkedList<XWPFRun> queue = new LinkedList<XWPFRun>();

        for( XWPFRun run : runs ) {

            // 读取到开始符号
            if( run.text().contains( startSymbol ) && queue.size() == 0 ) {
                // 如果stacks 不为空，那么清空
                queue.add( run );
                continue;
            }

            // 读取到结束符号
            if( run.text().contains( endSymbol ) && queue.size() > 0 ) {
                // 如果stacks 不为空,那么成功匹配到一个占位符
                queue.add( run );

                List<XWPFRun> item = new LinkedList<>();
                // 取出stack中的内容
                do {
                    item.add( queue.poll() );
                } while ( queue.size() > 0 );

                result.add( item );

                continue;
            }

            // 其他符号处理
            if( queue.size() > 0 ) {
                queue.add( run );

                continue;
            }
        }

        return result;
    }

    /**
     * 将一个段落中的，部分runs中的占位符给替换掉
     * @param paragraph
     * @param runs
     * @param params
     * @return
     */
    private static void replacePlaceholder( XWPFParagraph paragraph, List<XWPFRun> runs, Map<String,Object> params ) {

        // 获取文本值
        String text = "";
        String tmpText = "";

        if( runs.size() <= 0 ) {
            return;
        }

        for( XWPFRun run : runs ) {
            tmpText += run.text();
        }

        text = tmpText;

        // 将所有匹配到的占位符替换掉
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            // 将所有符合regex规则的字符串替换掉
            String regex = "\\$\\{.*\\s*,*key=" + entry.getKey().trim() + "\\s*,*.*\\}";
            tmpText = tmpText.replaceAll( regex, entry.getValue().toString() );
        }

        // 如果没有发生替换
        if( tmpText.equals(text) == true ) {
            return;
        }

        // 替换新的文本
        XWPFRun tmpRun = paragraph.insertNewRun( paragraph.getRuns().indexOf( runs.get(0) ) );
        tmpRun.setText( tmpText, 0 );

        // 删除旧文本
        for( XWPFRun run : runs ) {
            paragraph.removeRun( paragraph.getRuns().indexOf( run ) );
        }
    }

    /**
     * 将数据填充到table中
     *
     */
    private static void replaceTable( XWPFTable xwpfTable, List<List<String>> dataList ) {
        // 模版行
        // table中第0行为表头
        XWPFTableRow templateRow = xwpfTable.getRow( 1 );

        for( int i = 0; i < dataList.size(); i++ ) {
            // 创建一行
            XWPFTableRow contentRow = xwpfTable.insertNewTableRow( i + 2 );
            replaceTableRow( templateRow, contentRow, dataList.get(i) );
        }

        if( xwpfTable.getRows().size() > 2 ) {
            xwpfTable.removeRow( 1 );
        }
    }

    /**
     * 将数据填充到table中
     * @param
     * @return tableData 填充到table中的数据
     */
    private static void replaceTableRow( XWPFTableRow templateRow, XWPFTableRow contentRow, List<String> rowData ) {
        //复制行属性
        contentRow.getCtRow().setTrPr(templateRow.getCtRow().getTrPr());
        List<XWPFTableCell> cellList = templateRow.getTableCells();
        if (null == cellList) {
            return;
        }

        //复制列及其属性和内容
        XWPFTableCell contentCell = null, templateCell = null;
        List<XWPFTableCell> templateCellList = templateRow.getTableCells();

        // TODO: 增加鲁棒
        for( int i = 0; i < templateCellList.size(); i++ ) {
            templateCell = templateCellList.get( i );
            contentCell = contentRow.addNewTableCell();
            //列属性
            contentCell.getCTTc().setTcPr( templateCell.getCTTc().getTcPr());
            //段落属性
            if ( templateCell.getParagraphs() != null &&  templateCell.getParagraphs().size() > 0) {
                contentCell.getParagraphs().get(0).getCTP().setPPr( templateCell.getParagraphs().get(0).getCTP().getPPr());
                if ( templateCell.getParagraphs().get(0).getRuns() != null &&  templateCell.getParagraphs().get(0).getRuns().size() > 0) {
                    XWPFRun cellR = contentCell.getParagraphs().get(0).createRun();
                    cellR.setText( rowData.get(i) );
                    cellR.setBold( templateCell.getParagraphs().get(0).getRuns().get(0).isBold());
                } else {
                    contentCell.setText( rowData.get( i ) );
                }
            } else {
                contentCell.setText( rowData.get( i ) );
            }
        }
    }
}