package com.speechmaru.record.dto.response;


import com.speechmaru.file.document.File;
import com.speechmaru.record.document.Record;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SttResponse {

    private final List<String> text;
    private final List<Integer> speed;
    private final List<Integer> volume;
    private final List<String> keyword;
    private final List<Integer> textInfo;
    private final List<String> habitualWord;

    public Record toRecord(File file) {
        return Record.builder()
            .text(text)
            .speed(speed)
            .volume(volume)
            .keyword(keyword)
            .textInfo(textInfo)
            .habitualWord(habitualWord)
            .file(file).build();
    }
}
