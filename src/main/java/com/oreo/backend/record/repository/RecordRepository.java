package com.oreo.backend.record.repository;

import com.oreo.backend.record.document.Record;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecordRepository extends MongoRepository<Record, String> {

}
