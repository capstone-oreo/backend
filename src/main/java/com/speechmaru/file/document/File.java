package com.speechmaru.file.document;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "file")
@Getter
public class File {

    @Id
    private String id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String filename;

    @CreatedDate
    private String createdAt;

    @Builder
    public File(String filename, String title) {
        this.title = title;
        this.filename = filename;
    }
}
