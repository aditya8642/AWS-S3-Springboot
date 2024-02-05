package com.awsbucket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.awsbucket.configuration.S3BucketConfiguration;

@RestController
public class S3Controller {

	@Autowired
	S3BucketConfiguration bucketConfiguration;
	
	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam(name = "fileName") String fileName) {
		ResponseEntity<String> resp= bucketConfiguration.upload(fileName);
		return resp;
	}
	
	@GetMapping("/download")
	public ResponseEntity<String> download(@RequestParam(name = "fileName") String fileName) {
		ResponseEntity<String> resp= bucketConfiguration.download(fileName);
		return resp;
	}
}
