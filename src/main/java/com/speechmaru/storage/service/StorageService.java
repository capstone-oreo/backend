package com.speechmaru.storage.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String uploadVoice(MultipartFile file);

    void deleteVoice(String uri);

    String getValidExtension(String fileName);
}
