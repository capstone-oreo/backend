package com.oreo.backend.file.integration;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.oreo.backend.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;

public class FileIntegrationTest extends IntegrationTest {

    @MockBean
    RestTemplateBuilder restTemplateBuilder;

    @Test
    @DisplayName("POST /api/files")
    void postFiles() throws Exception {
        //given
        String uri = "/api/files";
        List<String> texts = List.of("hello", "world");

        RestTemplate restTemplate = mock(RestTemplate.class);
        given(restTemplateBuilder.build()).willReturn(restTemplate);

        MockMultipartFile mockFile = new MockMultipartFile("file", "test.wav", "audio/wav",
            "test data".getBytes());

        ResponseEntity<List<String>> response = ResponseEntity.ok(texts);
        given(
            restTemplate.exchange(eq("http://flask:8000/stt"), eq(HttpMethod.POST), any(),
                any(ParameterizedTypeReference.class))).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(multipart(uri).file(mockFile));
        System.out.println(actions.andReturn().getResponse().getContentAsString());

        //then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.[0]").value(texts.get(0)))
            .andExpect(jsonPath("$.[1]").value(texts.get(1)))
            .andDo(print());
    }

}
