package com.speechmaru.file.dto.response;

import com.speechmaru.file.document.File;
import com.speechmaru.file.service.FileService;
import lombok.Getter;

@Getter
public class FileResponse {

    private final String id;
    private final String uri;
    private final String title;
    private final String createdAt;

    public FileResponse(File file) {
        this.id = file.getId();
        this.uri = FileService.PRE_URI + file.getFilename();
        this.title = file.getTitle();
        this.createdAt = file.getCreatedAt();
    }

    public String getFilename() {
        return uri.substring(FileService.PRE_URI.length());
    }
}
