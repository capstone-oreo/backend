package com.speechmaru.record.repository;

import com.speechmaru.record.document.Record;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecordRepository extends MongoRepository<Record, String> {
    Optional<Record> findByFile_Id(String fileId);
}
