package com.speechmaru.common.config;

import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class OracleConfig {

    @Bean
    public ObjectStorage getObjectStorage() throws IOException {
        // 인증 정보 설정
        String FILE_PATH = "./oracle-cloud-config";
        AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(
                FILE_PATH, "DEFAULT");

        // ObjectStorage 클라이언트 생성
        return ObjectStorageClient.builder().build(provider);
    }
}
