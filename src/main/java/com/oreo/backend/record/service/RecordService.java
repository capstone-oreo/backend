package com.oreo.backend.record.service;

import com.oreo.backend.record.document.Record;
import com.oreo.backend.record.dto.response.RecordResponse;
import com.oreo.backend.record.exception.RecordNotFoundException;
import com.oreo.backend.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public RecordResponse deleteRecord(String fileId) {
        Record record = recordRepository.findByFile_Id(fileId)
            .orElseThrow(() -> new RecordNotFoundException("분석 내용을 찾을 수 없습니다."));
        recordRepository.delete(record);
        return new RecordResponse(record);
    }
}
