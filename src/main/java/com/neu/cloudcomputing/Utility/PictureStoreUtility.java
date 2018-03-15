package com.neu.cloudcomputing.Utility;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class PictureStoreUtility {

    public static final String pictureLocalPath = "/var/tempimage/";
    public static final String pictureApplicationPath = "/css/images/";
    public static final String pictureApplicationAbsolutePath = "/home/sunrui/csye6225/dev/csye6225-spring2018/src/main/resources/static/css/images";


    public static File convertFromMultipart(MultipartFile file) throws Exception {
        File newFile = new File(pictureLocalPath + file.getOriginalFilename());
        newFile.createNewFile();
        FileOutputStream fs = new FileOutputStream(newFile);
        fs.write(file.getBytes());
        fs.close();
        return newFile;
    }
}
