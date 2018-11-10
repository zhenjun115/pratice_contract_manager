package com.contract.manager.controller;

import com.contract.manager.model.Msg;
import com.contract.manager.util.FileUploader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class FileController
{
  /**
   * 上传文件
   * @return Msg
   */
  @RequestMapping( "/file/upload" )
  public Msg upload( @RequestParam MultipartFile file, @RequestParam String cat, RedirectAttributes redirectAttributes ) {
    Msg msg = null;

    if( false == file.isEmpty() ) {
      msg = FileUploader.save( file );
    } else {
      msg = new Msg( 400, "上传失败" );
    }
    return msg;
  }

  @RequestMapping( "/file/download" )
  public void download() {
  }

  @RequestMapping( "/file/view" )
  public void view() {
  }
}