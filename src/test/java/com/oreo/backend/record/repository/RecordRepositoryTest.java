package com.oreo.backend.record.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.oreo.backend.common.config.MongoConfig;
import com.oreo.backend.file.document.File;
import com.oreo.backend.file.repository.FileRepository;
import com.oreo.backend.record.document.Record;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

@DataMongoTest
@Import(MongoConfig.class)
public class RecordRepositoryTest {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    RecordRepository recordRepository;

    File savedFile;
    Record savedRecord;

    @BeforeEach
    void setUp() {
        File file = new File("aa.mp3", "title1");
        savedFile = fileRepository.save(file);
        Record record = Record.builder()
            .file(savedFile).build();
        savedRecord = recordRepository.save(record);
    }

    @AfterEach
    void tearDown() {
        recordRepository.deleteAll();
        fileRepository.deleteAll();
    }

    @Test
    @DisplayName("Record를 저장하고 삭제한다.")
    void saveAndDeleteRecord() {
        Record findRecord = recordRepository.findById(savedRecord.getId()).orElseThrow();
        recordRepository.delete(findRecord);

        assertThat(recordRepository.findById(savedRecord.getId()).isPresent()).isFalse();
    }

    @Test
    @DisplayName("file id로 record를 조회한다.")
    void findByFileId() {
        Record record = recordRepository.findByFile_Id(savedFile.getId()).orElseThrow();

        assertThat(record).usingRecursiveComparison()
            .ignoringFields("file").isEqualTo(savedRecord);
    }
}
