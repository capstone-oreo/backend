package com.oreo.backend.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String uploadVoice(MultipartFile file) throws IOException;

    void deleteVoice(String uri);
}
