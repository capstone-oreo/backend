package com.oreo.backend.file.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.oreo.backend.file.document.File;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@DataMongoTest
class FileRepositoryTest {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    @DisplayName("File document를 저장한다.")
    void saveFile() {
        //given
        File file = new File("aa.com", "hello");

        //when
        File inserted = fileRepository.insert(file);

        //then
        List<File> files = mongoTemplate.findAll(File.class);

        assertThat(files).hasSize(1);
        assertThat(files.get(0)).usingRecursiveComparison()
            .isEqualTo(inserted);
    }
}