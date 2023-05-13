package com.oreo.backend.file.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.oreo.backend.common.config.MongoConfig;
import com.oreo.backend.file.document.File;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

@DataMongoTest
@Import(MongoConfig.class)
class FileRepositoryTest {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @AfterEach
    void tearDown() {
        fileRepository.deleteAll();
    }

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

    @Test
    @DisplayName("uri에 빈값이 들어갈 수 없다.")
    void uriNotNull() {
        //given
        File file = new File("", "title");

        //when
        //then
        assertThrows(ConstraintViolationException.class, () -> fileRepository.insert(file));
    }

    @Test
    @DisplayName("title에 빈값이 들어갈 수 없다.")
    void titleNotNull() {
        //given
        File file = new File("uri", "");

        //when
        //then
        assertThrows(ConstraintViolationException.class, () -> fileRepository.insert(file));
    }
}