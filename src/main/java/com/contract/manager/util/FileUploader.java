package com.contract.manager.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.contract.manager.model.Msg;

import org.springframework.web.multipart.MultipartFile;

public class FileUploader {
    private static String UPLOADED_FOLDER = "";

    public static Msg save(MultipartFile file) {
        Msg msg = new Msg();

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("filePath", path.getFileName().toString());
            result.put("fileName", file.getOriginalFilename());
            msg.setCode(200);
            msg.setContent("上传成功");
            msg.setPayload(result);
        } catch (Exception e) {
            msg.setCode(400);
            msg.setContent("上传失败");
        }

        return msg;
    }

    public static Msg save(MultipartFile file, String folder) {
        Msg msg = new Msg();

        try {
            byte[] bytes = file.getBytes();
            String newFileName = CommonUtil.randomUUID() + "." + file.getOriginalFilename().split( "\\." )[1];
            Path path = Paths.get(folder + newFileName );
            Files.write( path, bytes );
            Map<String, Object> result = new HashMap<String, Object>();
            result.put( "filePath", newFileName );
            // result.put( "fileName", file.getOriginalFilename() );
            result.put( "fileName", newFileName );
            result.put( "originalFileName", file.getOriginalFilename() );
            msg.setCode( 200 );
            msg.setContent( "上传成功" );
            msg.setPayload( result );
        } catch ( Exception e ) {
            msg.setCode( 400 );
            msg.setContent( "上传失败" );
        }

        return msg;
    }
}