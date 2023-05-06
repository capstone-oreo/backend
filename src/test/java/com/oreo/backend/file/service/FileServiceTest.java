package com.oreo.backend.file.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    FileService fileService;

    @Mock
    RestTemplate restTemplate;

    @Mock
    RestTemplateBuilder restTemplateBuilder;

    @BeforeEach
    void setUp() {
        given(restTemplateBuilder.build()).willReturn(restTemplate);
        fileService = new FileService(restTemplateBuilder);
    }

    @Nested
    class AnalyzeVoiceFile {

        @Test
        @DisplayName("multipart file을 받아 API 요청을 보내고 string 배열을 body로 받는다.")
        void analyzeVoiceFile() {
            //given
            List<String> texts = List.of("hello", "world");
            MockMultipartFile mockFile = new MockMultipartFile("test", "test.wav", "audio/wav",
                "test data".getBytes());
            ResponseEntity<List<String>> response = ResponseEntity.ok(texts);
            given(
                restTemplate.exchange(eq("http://flask:8000/stt"), eq(HttpMethod.POST), any(),
                    any(ParameterizedTypeReference.class))).willReturn(response);

            //when
            List<String> result = fileService.analyzeVoiceFile(mockFile);

            //then
            assertThat(result).isEqualTo(texts);
        }
    }
}