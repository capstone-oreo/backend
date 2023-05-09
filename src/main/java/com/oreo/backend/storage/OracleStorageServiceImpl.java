package com.oreo.backend.storage;

import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oreo.backend.file.exception.InvalidFileException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OracleStorageServiceImpl implements StorageService {

    @Value("${oracle.cloud.bucket}")
    private String BUCKET;

    @Value("${oracle.cloud.namespace}")
    private String NAMESPACE;

    private final ObjectStorage objectStorage;

    public String uploadVoice(MultipartFile file) {
        validateM4aExtension(file.getOriginalFilename());
        String filename = "voice/" + UUID.randomUUID() + ".m4a";
        try{
            PutObjectRequest request = PutObjectRequest.builder()
                .bucketName(BUCKET)
                .namespaceName(NAMESPACE)
                .objectName(filename)
                .putObjectBody(file.getInputStream())
                .contentType("audio/x-m4a")
                .build();
            objectStorage.putObject(request);
        } catch (IOException e){
            throw new InvalidFileException("유효하지 않은 파일입니다.");
        }

        return URLEncoder.encode(filename, StandardCharsets.UTF_8);
    }

    public void deleteVoice(String filename) {
        String decodedFilename = URLDecoder.decode(filename, StandardCharsets.UTF_8);
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucketName(BUCKET)
                .namespaceName(NAMESPACE)
                .objectName(decodedFilename)
                .build();
        objectStorage.deleteObject(request);
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

    private void validateM4aExtension(String fileName) {
        Optional<String> extension = Optional.ofNullable(fileName).filter(f -> f.contains("."))
                .map(f -> f.substring(fileName.lastIndexOf(".") + 1));

        if (extension.isEmpty() || !extension.get().equals("m4a")) {
            throw new RuntimeException("음성파일이 m4a 확장자가 아닙니다.");
        }
    }
}