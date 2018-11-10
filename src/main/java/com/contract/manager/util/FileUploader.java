package com.contract.manager.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.contract.manager.model.Msg;

import org.springframework.web.multipart.MultipartFile;

public class FileUploader
{
  private static String UPLOADED_FOLDER = "";
  public static Msg save( MultipartFile file ) {
    Msg msg = new Msg();

    try {
      byte[] bytes = file.getBytes();
      Path path = Paths.get( UPLOADED_FOLDER + file.getOriginalFilename() );
      Files.write(path, bytes );
      msg.setCode( 200 );
      msg.setContent( "上传成功" );
    } catch (Exception e) {
      msg.setCode( 400 );
      msg.setContent( "上传失败" );
    }
    
    return msg;
  }
}