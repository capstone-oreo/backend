package com.oreo.backend.record.document;

import com.oreo.backend.file.document.File;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "File")
@Getter
public class Record {

    @Id
    private String id;

    @NotEmpty
    private String text;

    @Default
    private List<Integer> speed = new ArrayList<>();

    @Default
    private List<Integer> volume = new ArrayList<>();

    @Default
    private List<String> keyword = new ArrayList<>();

    @Default
    private List<String> habitualWorld = new ArrayList<>();

    @DBRef(lazy = true)
    @NotNull
    private File file;

    @CreatedDate
    private String createdAt;

    @Builder
    public Record(String text, List<Integer> speed, List<Integer> volume, List<String> keyword,
        List<String> habitualWorld, File file) {
        this.text = text;
        this.speed = speed;
        this.volume = volume;
        this.keyword = keyword;
        this.habitualWorld = habitualWorld;
        this.file = file;
    }
}
