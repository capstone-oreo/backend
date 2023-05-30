package com.speechmaru.record.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.speechmaru.IntegrationTest;
import com.speechmaru.file.document.File;
import com.speechmaru.file.repository.FileRepository;
import com.speechmaru.record.document.Record;
import com.speechmaru.record.dto.response.RecordResponse;
import com.speechmaru.record.repository.RecordRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class RecordControllerTest extends IntegrationTest {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    RecordRepository recordRepository;

    @AfterEach
    void tearDown() {
        recordRepository.deleteAll();
        fileRepository.deleteAll();
    }

    @Nested
    @DisplayName("GET /api/records?fileId={fileId}")
    class FindRecord {

        String getUri(String fileId) {
            return "/api/records?fileId=" + fileId;
        }

        @Test
        @DisplayName("fileId로 record를 조회한다.")
        void findRecord() throws Exception {
            //given
            File file = fileRepository.save(new File("test", "hello world"));
            Record record = recordRepository.save(Record.builder()
                .text(List.of("hello", "world"))
                .speed(List.of(2, 2, 3))
                .volume(List.of(4, 4, 4))
                .keyword(List.of("hello"))
                .habitualWord(List.of("umm"))
                .file(file)
                .build());
            String uri = getUri(file.getId());

            //when
            ResultActions actions = mockMvc.perform(
                get(uri).contentType(MediaType.APPLICATION_JSON));

            //then
            RecordResponse expected = new RecordResponse(record);
            actions.andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(asParsedJson(expected))))
                .andDo(print());
        }

        @Test
        @DisplayName("fileId로 record를 조회할 수 없으면 예외가 발생한다.")
        void notFoundRecord() throws Exception {
            //given
            String uri = getUri("ad12asljda4o9");

            //when
            ResultActions actions = mockMvc.perform(
                get(uri).contentType(MediaType.APPLICATION_JSON));

            //then
            actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("1007"))
                .andDo(print());
        }
    }
}