package com.aptech.project.hotel.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.aptech.project.hotel.util.ConfigUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AwsService {

    private static final Logger logger = LoggerFactory.getLogger(AwsService.class);

    @Autowired
    private ConfigUtility config;
    public String uploadAvatar(File file, String avatarKey){
        AWSCredentials credentials = new BasicAWSCredentials(
                config.getProperty("s3.accessKey"),
                config.getProperty("s3.secretKey")
        );
        try {
            AmazonS3 s3client = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(Regions.US_EAST_2)
                    .build();
            s3client.putObject(new PutObjectRequest( config.getProperty("s3.bucket"), config.getProperty("s3.bucket.avatar")+avatarKey, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return config.getProperty("s3.url.avatar")+config.getProperty("s3.bucket.avatar")+avatarKey;
    }
}
