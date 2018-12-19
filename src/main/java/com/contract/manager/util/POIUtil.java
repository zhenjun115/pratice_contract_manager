package com.contract.manager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.soap.Text;

public class POIUtil
{
    public static void generateDocWithDatas(Map<String, String> datas, File file) {
        try {
            FileInputStream source = new FileInputStream(file);
            XWPFDocument doc = new XWPFDocument( source );
            // 
            for (XWPFParagraph p : doc.getParagraphs()) {
                // System.out.println( p.getText() );
                // String text = p.getText();
                // for ( Map.Entry<String,String> entry:datas.entrySet() ) {
                //     if (text != null && text.contains(entry.getKey())) {
                //         text = text.replace(entry.getKey(), entry.getValue());
                //         r.setText(text, 0);
                //     }
                // }
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        // r.getArr
                        // r.getCTR().sizeOfArra
                        String text = r.text();
                        // System.out.println( text );
                        for ( Map.Entry<String,String> entry:datas.entrySet() ) {
                            System.out.println( entry.getKey() );
                            if (text != null && text.contains(entry.getKey())) {
                                text = text.replace(entry.getKey(), entry.getValue());
                                r.setText(text, 0);
                            }
                        }
                    }
                }
            }
            for (XWPFTable tbl : doc.getTables()) {
               for (XWPFTableRow row : tbl.getRows()) {
                  for (XWPFTableCell cell : row.getTableCells()) {
                     for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            String text = r.getText(0);
                            for ( Map.Entry<String,String> entry:datas.entrySet() ) {
                                if (text != null && text.contains(entry.getKey())) {
                                    text = text.replace(entry.getKey(), entry.getValue());
                                    r.setText(text, 0);
                                }
                            }
                        }
                     }
                  }
               }
            }
            doc.write( new FileOutputStream( file ) );
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
		} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
		}
    }
}