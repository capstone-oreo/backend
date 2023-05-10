package com.oreo.backend.file.integration;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.PutObjectResponse;
import com.oreo.backend.IntegrationTest;
import com.oreo.backend.file.document.File;
import com.oreo.backend.file.repository.FileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;

public class FileIntegrationTest extends IntegrationTest {

    @Autowired
    FileRepository fileRepository;

    @Test
    @DisplayName("POST /api/files")
    void postFiles() throws Exception {
        //given
        String uri = "/api/files";
        String title = "file title";
//        List<String> texts = List.of("hello", "world");

        MockMultipartFile mockFile = new MockMultipartFile("file", "test.wav", "audio/wav",
            "test data".getBytes());

        given(objectStorage.putObject(any(PutObjectRequest.class))).willReturn(
            mock(PutObjectResponse.class));
//        RestTemplate restTemplate = mock(RestTemplate.class);
//        given(restTemplateBuilder.build()).willReturn(restTemplate);
//
//        ResponseEntity<List<String>> response = ResponseEntity.ok(texts);
//        given(
//            restTemplate.exchange(eq("http://flask:8000/stt"), eq(HttpMethod.POST), any(),
//                any(ParameterizedTypeReference.class))).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
            multipart(uri).file(mockFile).param("title", title));

        //then
        actions.andExpect(status().isOk())
            .andExpect(result -> {
                String id = result.getResponse().getContentAsString();
                File file = fileRepository.findById(id).orElseThrow();
                assertThat(file.getTitle()).isEqualTo(title);
            })
            .andDo(print());
    }

}
