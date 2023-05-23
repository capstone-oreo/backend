package com.speechmaru.storage.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.DeleteObjectResponse;
import com.oracle.bmc.objectstorage.responses.PutObjectResponse;
import com.speechmaru.file.exception.InvalidFileException;
import com.speechmaru.storage.exception.InvalidFileExtensionException;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class OracleStorageServiceImplTest {

    @InjectMocks
    OracleStorageServiceImpl storageService;

    @Mock
    ObjectStorage objectStorage;

    @Nested
    class UploadVoice {

        @Test
        @DisplayName("m4a 음성 파일을 storage에 uuid로 저장한다.")
        void uploadVoiceM4aInStorage() throws IOException {
            //given
            MultipartFile mockFile = mock(MultipartFile.class);
            given(mockFile.getOriginalFilename()).willReturn("test.m4a");
            given(mockFile.getInputStream()).willReturn(mock(InputStream.class));
            given(objectStorage.putObject(any(PutObjectRequest.class))).willReturn(
                mock(PutObjectResponse.class));

            //when
            String uri = storageService.uploadVoice(mockFile);

            //then
            assertThat(uri).containsPattern(
                "^voice%2F[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}.m4a$");
        }

        @Test
        @DisplayName("mp3 음성 파일을 storage에 uuid로 저장한다.")
        void uploadVoiceMp3InStorage() throws IOException {
            //given
            MultipartFile mockFile = mock(MultipartFile.class);
            given(mockFile.getOriginalFilename()).willReturn("test.mp3");
            given(mockFile.getInputStream()).willReturn(mock(InputStream.class));
            given(objectStorage.putObject(any(PutObjectRequest.class))).willReturn(
                mock(PutObjectResponse.class));

            //when
            String uri = storageService.uploadVoice(mockFile);

            //then
            assertThat(uri).containsPattern(
                "^voice%2F[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}.mp3$");
        }

        @Test
        @DisplayName("음성 파일 확장자가 아닌 파일은 예외가 발생한다.")
        void NotM4aExtension() {
            //given
            MultipartFile mockFile = mock(MultipartFile.class);
            given(mockFile.getOriginalFilename()).willReturn("test.mp4");

            //when
            //then
            assertThrows(InvalidFileExtensionException.class,
                () -> storageService.uploadVoice(mockFile));
        }

        @Test
        @DisplayName("확장자가 없는 파일은 예외가 발생한다.")
        void emptyExtension() {
            //given
            MultipartFile mockFile = mock(MultipartFile.class);
            given(mockFile.getOriginalFilename()).willReturn("mp3m4a");

            //when
            //then
            assertThrows(InvalidFileExtensionException.class,
                () -> storageService.uploadVoice(mockFile));
        }

        @Test
        @DisplayName("getInputStream에서 발생한 예외를 처리한다.")
        void InvalidGetInputStream() throws IOException {
            //given
            MultipartFile mockFile = mock(MultipartFile.class);
            given(mockFile.getOriginalFilename()).willReturn("test.flac");
            given(mockFile.getInputStream()).willThrow(IOException.class);

            //when
            //then
            assertThrows(InvalidFileException.class, () -> storageService.uploadVoice(mockFile));
        }
    }

    @Nested
    class DeleteVoice {

        @Test
        @DisplayName("파일 이름으로 storage의 파일을 삭제한다.")
        void deleteFile() {
            //given
            String filename = "voice%2Faaaabbb.m4a";
            given(objectStorage.deleteObject(any(DeleteObjectRequest.class))).willReturn(
                mock(DeleteObjectResponse.class));

            //when
            storageService.deleteVoice(filename);
        }

        @Test
        @DisplayName("파일 삭제에 오류가 발생한다.")
        void deleteFileException() {
            //given
            String filename = "voice%2Faaaabbb.m4a";
            given(objectStorage.deleteObject(any(DeleteObjectRequest.class))).willThrow(
                RuntimeException.class);

            //when
            assertThrows(InvalidFileException.class, () -> storageService.deleteVoice(filename));
        }
    }
}