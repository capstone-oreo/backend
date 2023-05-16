package com.oreo.backend.record.document;

import com.oreo.backend.file.document.File;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "record")
@Getter
public class Record {

    @Id
    private String id;

    private List<String> text;

    private List<Integer> speed;

    private List<Integer> volume;

    private List<String> keyword;

    private List<String> habitualWorld;

    @DBRef(lazy = true)
    @NotNull
    private File file;

    @CreatedDate
    private String createdAt;

    @Builder
    public Record(List<String> text, List<Integer> speed, List<Integer> volume,
        List<String> keyword, List<String> habitualWorld, File file) {
        this.text = text != null ? text : new ArrayList<>();
        this.speed = speed != null ? speed : new ArrayList<>();
        this.volume = volume != null ? volume : new ArrayList<>();
        this.keyword = keyword != null ? keyword : new ArrayList<>();
        this.habitualWorld = habitualWorld != null ? habitualWorld : new ArrayList<>();
        this.file = file;
    }
}
