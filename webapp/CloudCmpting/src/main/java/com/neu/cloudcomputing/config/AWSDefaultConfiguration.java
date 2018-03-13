package com.neu.cloudcomputing.config;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AWSDefaultConfiguration {

    final static AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();

    public static AmazonS3 getInstance() {
        return s3;
    }
}
