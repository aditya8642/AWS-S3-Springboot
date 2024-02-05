package com.awsbucket.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class S3BucketConfiguration {

	@Value("${aws.access-key}")
	public String accessKey;

	@Value("${aws.secret-key}")
	public String secretKey;

	@Value("${aws.bucket-name}")
	public String bucketName;
	
	@Autowired
	AmazonS3 amazonS3;

	public ResponseEntity<String> upload(String fileName) {
		ResponseEntity<String> resp = null;
		try {
			File file = new File("C:\\Users\\desai\\Downloads\\" + fileName);
			String key = fileName;
			amazonS3.putObject(bucketName, key, file);
			resp = new ResponseEntity<>("File uploaded sucessfully", HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			resp = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;

	}

	public ResponseEntity<String> download(String fileName) {
		ResponseEntity<String> resp = null;
		try {
			S3Object object = amazonS3.getObject(bucketName, fileName);
			InputStream stream = object.getObjectContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String str = reader.readLine();
			resp = new ResponseEntity<>(str, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e);
			resp = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;

	}

	@Bean
	public AmazonS3 s3Config() {
		BasicAWSCredentials creds = new BasicAWSCredentials(accessKey,
				secretKey);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds))
				.build();

		return s3Client;
	}

}
