package com.contract.manager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.contract.manager.model.OfficePlaceholder;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.tomcat.util.buf.StringUtils;
import org.apache.xmlbeans.impl.regex.Match;

public class POIUtil
{
    public static void generateDocWithDatas(Map<String, String> datas, File file) {
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
            // TODO: 处理doc中的table
//            for (XWPFTable tbl : doc.getTables()) {
//               for (XWPFTableRow row : tbl.getRows()) {
//                  for (XWPFTableCell cell : row.getTableCells()) {
//                     for (XWPFParagraph p : cell.getParagraphs()) {
//                        for (XWPFRun r : p.getRuns()) {
//                            String text = r.getText(0);
//                            for ( Map.Entry<String,String> entry:datas.entrySet() ) {
//                                if (text != null && text.contains(entry.getKey())) {
//                                    text = text.replace(entry.getKey(), entry.getValue());
//                                    r.setText(text, 0);
//                                }
//                            }
//                        }
//                     }
//                  }
//               }
//            }
            doc.write( new FileOutputStream( file ) );
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
		} catch (IOException e) {
            // TODO Auto-generated catch block
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
                    Map<String,String> params = new HashMap<String,String>();
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
    private static void replacePlaceholder( XWPFParagraph paragraph, List<XWPFRun> runs, Map<String,String> params ) {

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
        for (Map.Entry<String, String> entry : params.entrySet()) {
            // 将所有符合regex规则的字符串替换掉
            String regex = "\\$\\{.*\\s*,*key=" + entry.getKey().trim() + "\\s*,*.*\\}";
            tmpText = tmpText.replaceAll( regex, entry.getValue() );
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
}