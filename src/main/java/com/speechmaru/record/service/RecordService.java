package com.speechmaru.record.service;

import com.speechmaru.file.document.File;
import com.speechmaru.file.exception.FileNotFoundException;
import com.speechmaru.file.repository.FileRepository;
import com.speechmaru.record.document.Record;
import com.speechmaru.record.dto.response.RecordResponse;
import com.speechmaru.record.exception.RecordNotFoundException;
import com.speechmaru.record.repository.RecordRepository;
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
