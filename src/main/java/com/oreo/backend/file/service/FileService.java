package com.oreo.backend.file.service;

import com.oreo.backend.file.document.File;
import com.oreo.backend.file.dto.response.FileResponse;
import com.oreo.backend.file.exception.FileNotFoundException;
import com.oreo.backend.file.exception.InvalidFileException;
import com.oreo.backend.file.exception.SttRequestException;
import com.oreo.backend.file.repository.FileRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    public static final String PRE_URI = "https://objectstorage.ap-seoul-1.oraclecloud.com/n/cnkzdnklb8xy/b/oreo/o/";

    private final FileRepository fileRepository;
    private final RestTemplateBuilder restTemplateBuilder;

    public String saveFile(String postUri, String filename) {
        File savedFile = fileRepository.save(new File(postUri, filename));
        return savedFile.getId();
    }

    // python으로 음성 파일을 전달하고 분석 결과를 얻는다.
    public List<String> analyzeVoiceFile(MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        try {
            ByteArrayResource contentsAsResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
            body.add("file", contentsAsResource);
        } catch (IOException e) {
            throw new InvalidFileException("유효하지 않은 파일입니다.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<List<String>> response = restTemplateBuilder.build()
            .exchange("http://flask:8000/stt", HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<>() {
                });
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new SttRequestException("STT 요청에 실패했습니다.");
        }
        return response.getBody();
    }

    public Page<FileResponse> findFiles(Pageable pageable) {
        return fileRepository.findAll(pageable).map(FileResponse::new);
    }

    public FileResponse deleteFile(String id) {
        File file = fileRepository.findById(id)
            .orElseThrow(() -> new FileNotFoundException("파일을 찾을 수 없습니다."));
        fileRepository.delete(file);
        return new FileResponse(file);
    }
}
