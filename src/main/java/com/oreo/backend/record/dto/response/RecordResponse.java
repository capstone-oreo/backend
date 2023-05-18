package com.oreo.backend.record.dto.response;

import com.oreo.backend.record.document.Record;
import java.util.List;
import lombok.Getter;

@Getter
public class RecordResponse {

    private final String id;
    private final List<String> text;
    private final List<Integer> speed;
    private final List<Integer> volume;
    private final List<String> keyword;
    private final List<String> habitualWorld;
    private final String createdAt;

    public RecordResponse(Record record) {
        this.id = record.getId();
        this.text = record.getText();
        this.speed = record.getSpeed();
        this.volume = record.getVolume();
        this.keyword = record.getKeyword();
        this.habitualWorld = record.getHabitualWorld();
        this.createdAt = getCreatedAt();
    }
}
