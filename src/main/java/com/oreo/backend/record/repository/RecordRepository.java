package com.oreo.backend.record.repository;

import com.oreo.backend.file.document.File;
import com.oreo.backend.record.document.Record;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecordRepository extends MongoRepository<Record, String> {
    Optional<Record> findByFile_Id(String fileId);
}
