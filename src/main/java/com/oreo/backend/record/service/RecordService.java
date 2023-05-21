package com.oreo.backend.record.service;

import com.oreo.backend.file.document.File;
import com.oreo.backend.file.exception.FileNotFoundException;
import com.oreo.backend.file.repository.FileRepository;
import com.oreo.backend.record.document.Record;
import com.oreo.backend.record.dto.response.RecordResponse;
import com.oreo.backend.record.exception.RecordNotFoundException;
import com.oreo.backend.record.repository.RecordRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final FileRepository fileRepository;
    private final RecordRepository recordRepository;

    public String saveRecord(String fileId, List<String> stt) {
        File file = fileRepository.findById(fileId)
            .orElseThrow(() -> new FileNotFoundException("파일을 찾을 수 없습니다."));
        Record record = recordRepository.save(
            Record.builder().text(stt).file(file).build());
        return record.getId();
    }

    public RecordResponse deleteRecord(String fileId) {
        Record record = recordRepository.findByFile_Id(fileId)
            .orElseThrow(() -> new RecordNotFoundException("분석 내용을 찾을 수 없습니다."));
        recordRepository.delete(record);
        return new RecordResponse(record);
    }

    public RecordResponse findRecord(String fileId) {
        Record record = recordRepository.findByFile_Id(fileId)
            .orElseThrow(() -> new RecordNotFoundException("분석 내용을 찾을 수 없습니다."));
        return new RecordResponse(record);
    }
}
