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
}
