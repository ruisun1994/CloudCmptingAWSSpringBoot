package com.neu.cloudcomputing.Utility;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Utility {

    public static String trasferProfile(byte[] userProfile) throws UnsupportedEncodingException {
        byte[] encodeBase64 = Base64.getEncoder().encode(userProfile);
        return new String(encodeBase64, "utf-8");
    }

    public static String transferProfile(S3Object o) throws IOException {
        S3ObjectInputStream s3is = o.getObjectContent();
        byte[] userProfile = null;
        byte[] read_buf = new byte[1000];
        int n = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        while ((n = s3is.read(read_buf)) != -1) {
            bos.write(read_buf, 0, n);
        }
        userProfile = bos.toByteArray();
        bos.close();
        return trasferProfile(userProfile);
    }
}
