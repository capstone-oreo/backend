package com.oreo.backend.file.controller;

import com.oreo.backend.file.document.File;
import com.oreo.backend.file.repository.FileRepository;
import com.oreo.backend.file.service.FileService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final FileRepository fileRepository;

    @PostMapping("/files")
    public ResponseEntity<?> saveFile(@RequestPart(name="file") MultipartFile file) {
        List<String> messages = fileService.analyzeVoiceFile(file);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/files")
    public List<File> findFile() {
        return fileRepository.findAll();
    }
}
