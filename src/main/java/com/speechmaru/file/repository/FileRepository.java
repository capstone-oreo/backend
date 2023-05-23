package com.speechmaru.file.repository;

import com.speechmaru.file.document.File;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<File, String> {

}
