package com.oreo.backend.record.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.oreo.backend.common.config.MongoConfig;
import com.oreo.backend.file.document.File;
import com.oreo.backend.file.repository.FileRepository;
import com.oreo.backend.record.document.Record;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

@DataMongoTest
@Import(MongoConfig.class)
public class RecordRepositoryTest {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Record를 저장하고 삭제한다.")
    void saveAndDeleteRecord() {
        File file = new File("aa.com", "title");
        File savedFile = fileRepository.save(file);
        Record record = Record.builder()
            .text("hello")
            .file(savedFile).build();
        Record savedRecord = recordRepository.save(record);
        Record findRecord = recordRepository.findById(savedRecord.getId()).orElseThrow();
        recordRepository.delete(findRecord);

        assertThat(recordRepository.findById(savedRecord.getId()).isPresent()).isFalse();
    }
}
