package com.oreo.backend.file.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.oreo.backend.file.exception.InvalidFileException;
import com.oreo.backend.file.exception.SttRequestException;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @InjectMocks
    FileService fileService;

    @Mock
    RestTemplate restTemplate;

    @Mock
    RestTemplateBuilder restTemplateBuilder;

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
            given(restTemplateBuilder.build()).willReturn(restTemplate);
            given(
                restTemplate.exchange(eq("http://flask:8000/stt"), eq(HttpMethod.POST), any(),
                    any(ParameterizedTypeReference.class))).willReturn(response);

            //when
            List<String> result = fileService.analyzeVoiceFile(mockFile);

            //then
            assertThat(result).isEqualTo(texts);
        }

        @Test
        @DisplayName("유효하지 않은 파일은 예외가 발생한다.")
        void invalidFile() throws IOException {
            // given
            MultipartFile mockFile = mock(MultipartFile.class);
            given(mockFile.getBytes()).willThrow(IOException.class);

            // when
            assertThrows(InvalidFileException.class, () -> fileService.analyzeVoiceFile(mockFile));
        }

        @Test
        @DisplayName("API 요청에 실패하면 예외가 발생한다.")
        void failApiRequest() {
            //given
            List<String> texts = List.of("hello", "world");
            MockMultipartFile mockFile = new MockMultipartFile("test", "test.wav", "audio/wav",
                "test data".getBytes());
            ResponseEntity<Object> response = ResponseEntity.internalServerError().build();
            given(restTemplateBuilder.build()).willReturn(restTemplate);
            given(
                restTemplate.exchange(eq("http://flask:8000/stt"), eq(HttpMethod.POST), any(),
                    any(ParameterizedTypeReference.class))).willReturn(response);

            //when
            assertThrows(SttRequestException.class, () -> fileService.analyzeVoiceFile(mockFile));
        }
    }
}