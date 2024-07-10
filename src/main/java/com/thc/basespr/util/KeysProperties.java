package com.thc.basespr.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KeysProperties {
	//kg
	@Value("${kg.mid}") private String mid;
	@Value("${kg.apiKey}") private String apiKey;
	//aws
	@Value("${aws.s3.bucket_name}") private String bucket_name;
	@Value("${aws.s3.read_s3_url}") private String read_s3_url;
	@Value("${aws.s3.project_folder_name}") private String project_folder_name;
	@Value("${aws.s3.access_key}") private String access_key;
	@Value("${aws.s3.secret_key}") private String secret_key;
}
