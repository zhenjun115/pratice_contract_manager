package com.contract.manager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CommonUtil
{
  /**
   * 通过拷贝的方式创建新文件
   * @param oldFileName 文件名称
   * @param newFileName 新文件名称
   */
  public static File copyFile( String oldFileName, String newFileName ) {
    File newFile = null;
    try{
      File oldFile = new File( oldFileName );
      newFile = new File( newFileName );

      int byteRead = 0;
      if( oldFile.exists() ) {
        InputStream source = new FileInputStream( oldFile );
        FileOutputStream target = new FileOutputStream( newFile );

        byte[] buffer = new byte[512];
        while( (byteRead = source.read(buffer)) != -1 ) {
          target.write( buffer, 0, byteRead );
        }
        source.close();
        target.flush();
        // target.close();
      }
    }catch( IOException ioException ) {
      System.out.println( ioException.getMessage() );
      ioException.printStackTrace();
    }
    return newFile;
  }
}