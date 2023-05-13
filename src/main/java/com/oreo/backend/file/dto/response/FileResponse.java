package com.oreo.backend.file.dto.response;

import com.oreo.backend.file.document.File;
import com.oreo.backend.file.service.FileService;
import lombok.Getter;

@Getter
public class FileResponse {

    private final String id;
    private final String uri;
    private final String title;

    public FileResponse(File file) {
        this.id = file.getId();
        this.uri = FileService.PRE_URI + file.getUri();
        this.title = file.getTitle();
    }
}
