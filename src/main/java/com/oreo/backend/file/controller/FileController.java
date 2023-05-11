package com.oreo.backend.file.controller;

import com.oreo.backend.common.dto.PageResponse;
import com.oreo.backend.file.dto.response.FileResponse;
import com.oreo.backend.file.exception.InvalidFileException;
import com.oreo.backend.file.service.FileService;
import com.oreo.backend.storage.service.StorageService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final StorageService storageService;

    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveFile(@RequestPart(name = "file") MultipartFile file,
        @RequestParam(name = "title") String title) {

        String uri = storageService.uploadVoice(file);
        String id = fileService.saveFile(uri, title);

        return ResponseEntity.ok(id);
    }

    @GetMapping("/files")
    @Parameter(in = ParameterIn.QUERY
        , description = "페이지 번호 (0..N)"
        , name = "page"
        , schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(in = ParameterIn.QUERY
        , description = "페이지 당 요소의 개수"
        , name = "size"
        , schema = @Schema(type = "integer", defaultValue = "10"))
    public PageResponse<FileResponse> findFile(
        @Parameter(hidden = true) @PageableDefault Pageable pageable) {
        return new PageResponse<>(fileService.findFiles(pageable));
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
