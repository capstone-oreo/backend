package com.oreo.backend.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String uploadVoice(MultipartFile file);

    void deleteVoice(String uri);
}
