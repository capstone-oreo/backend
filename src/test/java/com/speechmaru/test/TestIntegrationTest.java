package com.speechmaru.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.speechmaru.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class TestIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("배포 테스트용 test api에 요청을 보내면 spring을 응답받는다.")
    void test() throws Exception {
        //given
        String uri = "/api/test";

        //when
        ResultActions actions = mockMvc.perform(get(uri)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$").value("spring"));
    }
}