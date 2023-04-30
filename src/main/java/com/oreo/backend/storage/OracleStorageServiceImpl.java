package com.oreo.backend.storage;

import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class OracleStorageServiceImpl implements StorageService {

    @Value("${oracle.cloud.bucket}")
    private String BUCKET;

    @Value("${oracle.cloud.namespace}")
    private String NAMESPACE;

    public String uploadVoice(File file) throws IOException {
        ObjectStorage objectStorage = getObjectStorage();
        String fileName = "voice/" + UUID.randomUUID() + ".m4a";
        PutObjectRequest request = PutObjectRequest.builder()
                .bucketName(BUCKET)
                .namespaceName(NAMESPACE)
                .objectName(fileName)
                .putObjectBody(new FileInputStream(file))
                .contentType("audio/x-m4a")
                .build();
        objectStorage.putObject(request);
        return fileName;
    }

//    public File download() throws IOException {
//        ObjectStorage objectStorage = getObjectStorage();
//
//        // 파일 정보 받아오기
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                .bucketName(BUCKET)
//                .namespaceName(NAMESPACE)
//                .objectName("")
//                .build();
//        GetObjectResponse getObjectResponse = objectStorage.getObject(getObjectRequest);
//    }

    private ObjectStorage getObjectStorage() throws IOException {
        // 인증 정보 설정
        String FILE_PATH = "./oracle-cloud-config";
        AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(
                FILE_PATH, "DEFAULT");

        // ObjectStorage 클라이언트 생성
        return ObjectStorageClient.builder().build(provider);
    }
}