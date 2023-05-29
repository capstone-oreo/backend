package com.speechmaru.record.dto.response;


import com.speechmaru.file.document.File;
import com.speechmaru.record.document.Record;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SttResponse {

    private List<String> text;
    private List<Integer> speed;
    private List<Integer> volume;
    private List<String> keyword;
    private List<Integer> textInfo;
    private List<String> habitualWord;

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
