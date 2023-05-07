package com.oreo.backend.file.controller;

import com.oreo.backend.file.document.File;
import com.oreo.backend.file.exception.InvalidFileException;
import com.oreo.backend.file.repository.FileRepository;
import com.oreo.backend.file.service.FileService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveFile(@RequestPart(name = "file") MultipartFile file) {
        List<String> messages = fileService.analyzeVoiceFile(file);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/files")
    public List<File> findFile() {
        return fileRepository.findAll();
    }

    @PostMapping(value = "/files-test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveFileTest(@RequestPart(name = "file") MultipartFile file) {
        try {
            if (file.getSize() <= 0) {
                throw new IOException();
            }
            ByteArrayResource contentsAsResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
        } catch (IOException e) {
            throw new InvalidFileException("유효하지 않은 파일입니다.");
        }
        return ResponseEntity.ok(file.getOriginalFilename());
    }
}
