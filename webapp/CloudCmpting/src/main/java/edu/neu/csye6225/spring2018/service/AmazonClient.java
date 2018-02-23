package edu.neu.csye6225.spring2018.service;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class AmazonClient {

        private AmazonS3 s3client;

        @Value("${amazonProperties.endpointUrl}")
        private String endpointUrl;
        @Value("${amazonProperties.bucketName}")
        private String bucketName;
        @Value("${amazonProperties.accessKey}")
        private String accessKey;
        @Value("${amazonProperties.secretKey}")
        private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

//    public byte[] downloadByteArrayWithName(String FileName) throws IOException {
//        S3Object object = s3client.getObject(bucketName, FileName);
//        byte[] byteArray = IOUtils.toByteArray(object.getObjectContent());
//        return byteArray;
//    }

    public String encodeBase64URL(BufferedImage imgBuf, String suffix) throws IOException {
        String base64;

        if (imgBuf == null) {
            base64 = null;
        } else {
            Base64 encoder = new Base64();
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            ImageIO.write(imgBuf, suffix, out);

            byte[] bytes = out.toByteArray();
            base64 = "data:image/"+suffix+";base64," + new String(encoder.encode(bytes), "UTF-8");
        }

        return base64;
    }


    public String downloadImageFromS3(String id) throws IOException {

        S3Object obj = s3client.getObject(new GetObjectRequest(bucketName, id));
        BufferedImage imgBuf = ImageIO.read(obj.getObjectContent());
        String suffix=id.substring(id.lastIndexOf(".")+1);

        String base64 = encodeBase64URL(imgBuf,suffix);
        return base64;
    }

    public String uploadFile(MultipartFile multipartFile) {

        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = multipartFile.getOriginalFilename();
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }
    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
        return "Successfully deleted";
    }

}
