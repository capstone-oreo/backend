package com.oreo.backend.file.controller;

import com.oreo.backend.file.document.File;
import com.oreo.backend.file.repository.FileRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private final FileRepository fileRepository;

    @PostMapping("/files")
    public File saveFile(@RequestBody File file) {
        return fileRepository.insert(file);
    }

    @GetMapping("/files")
    public List<File> findFile() {
        return fileRepository.findAll();
    }
}
