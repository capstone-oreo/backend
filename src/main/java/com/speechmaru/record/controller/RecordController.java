package com.speechmaru.record.controller;

import com.speechmaru.record.dto.response.RecordResponse;
import com.speechmaru.record.service.RecordService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<RecordResponse> findRecord(@RequestParam(name = "fileId") String fileId) {
        RecordResponse record = recordService.findRecord(fileId);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/test")
    public ResponseEntity<RecordResponse> findRecordTest(@RequestParam(name = "fileId") String fileId) {
        int r = (int) (Math.random() * 2);
        RecordResponse response;
        if(r==0){
            response= RecordResponse.builder()
                .id("129387askdhiuh3")
                .text(List.of("hello", "world"))
                .speed(List.of(2, 3, 5))
                .volume(List.of(3, 2, 2))
                .keyword(List.of("hello", "world"))
                .habitualWorld(List.of("음"))
                .createdAt("23. 5. 18. 오후 7:03")
                .build();
        } else {
            response= RecordResponse.builder()
                .id("aa12zf43wr243")
                .text(List.of("i", "am", "oreo"))
                .speed(List.of(3, 2, 2, 1, 2))
                .volume(List.of(1, 2, 2, 3, 1))
                .habitualWorld(List.of("그러니까"))
                .keyword(List.of("oreo"))
                .createdAt("23. 1. 1. 오후 12:12")
                .build();
        }

        return ResponseEntity.ok(response);
    }
}
