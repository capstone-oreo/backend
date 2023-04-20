package com.oreo.backend.file.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "File")
@Getter
public class File {

    @Id
    private String id;

    private String uri;

    private String title;

    @CreatedDate
    private String createdAt;

    @Builder
    public File(String uri, String title) {
        this.uri = uri;
        this.title = title;
    }
}
