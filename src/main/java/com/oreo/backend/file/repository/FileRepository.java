package com.oreo.backend.file.repository;

import com.oreo.backend.file.document.File;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<File, String> {

}
