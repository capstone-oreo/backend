package com.oreo.backend.storage;

import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.PutObjectResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OracleStorageServiceImplTest {

    @InjectMocks
    OracleStorageServiceImpl storageService;

    @Mock
    ObjectStorage objectStorage;

    @Nested
    class UploadVoice {
        @Test
        @DisplayName("음성 파일을 storage에 uuid로 저장한다.")
        void uploadVoiceInStorage() throws IOException {
            //given
            MultipartFile mockFile = mock(MultipartFile.class);
            given(mockFile.getOriginalFilename()).willReturn("test.m4a");
            given(mockFile.getInputStream()).willReturn(mock(InputStream.class));
            given(objectStorage.putObject(any(PutObjectRequest.class))).willReturn(mock(PutObjectResponse.class));

            //when
            String uri = storageService.uploadVoice(mockFile);

            //then
            assertThat(uri).containsPattern("^voice/[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}.m4a$");
        }
    }
}