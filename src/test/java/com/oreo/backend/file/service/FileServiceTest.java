package com.oreo.backend.file.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.oreo.backend.file.document.File;
import com.oreo.backend.file.dto.response.FileResponse;
import com.oreo.backend.file.exception.FileNotFoundException;
import com.oreo.backend.file.exception.InvalidFileException;
import com.oreo.backend.file.exception.SttRequestException;
import com.oreo.backend.file.repository.FileRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    FileRepository fileRepository;

    @Mock
    RestTemplate restTemplate;

    @Mock
    RestTemplateBuilder restTemplateBuilder;

    @Nested
    class SaveFile {

        @Test
        @DisplayName("uri와 제목을 저장한다.")
        void saveFile() {
            //given
            String uri = "aabb.com";
            String title = "file title";
            String id = "13981a980s1";
            File mockFile = mock(File.class);

            given(fileRepository.save(any(File.class))).willReturn(mockFile);
            given(mockFile.getId()).willReturn(id);

            //when
            String savedId = fileService.saveFile(uri, title);

            //then
            assertThat(savedId).isEqualTo(id);
            verifyFile(uri, title);
        }

        private void verifyFile(String uri, String title) {
            ArgumentCaptor<File> fileCaptor = ArgumentCaptor.forClass(File.class);
            verify(fileRepository).save(fileCaptor.capture());
            File file = fileCaptor.getValue();
            assertThat(file.getUri()).isEqualTo(uri);
            assertThat(file.getTitle()).isEqualTo(title);
        }
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

    @Nested
    class FindFiles {

        @Test
        @DisplayName("File을 페이지네이션하여 조회한다.")
        void findFilePagination() {
            //given
            long total = 10L;
            File t1 = new File("a.com", "t1");
            File t2 = new File("a.com", "t2");
            List<File> files = List.of(t1, t2);
            PageRequest pageRequest = PageRequest.of(1, 2);
            given(fileRepository.findAll(pageRequest)).willReturn(
                new PageImpl<>(files, pageRequest, total));

            //when
            Page<FileResponse> result = fileService.findFiles(pageRequest);

            //then
            assertThat(result.getTotalElements()).isEqualTo(total);
            assertThat(result.getContent()).usingRecursiveComparison().isEqualTo(
                files.stream().map(FileResponse::new).toList());
        }
    }

    @Nested
    class DeleteFile {

        @Test
        @DisplayName("파일을 삭제한다.")
        void deleteFile() {
            //given
            String fileId = "12345";
            File mockFile = mock(File.class);
            given(fileRepository.findById(fileId)).willReturn(Optional.of(mockFile));
            willDoNothing().given(fileRepository).delete(mockFile);

            //when
            fileService.deleteFile(fileId);
        }

        @Test
        @DisplayName("파일를 찾을 수 없으면 예외가 발생한다.")
        void cannotFindFileById() {
            //given
            String fileId = "12345";
            given(fileRepository.findById(fileId)).willReturn(Optional.empty());

            //when
            //then
            assertThrows(FileNotFoundException.class, () -> fileService.deleteFile(fileId));
        }
    }
}