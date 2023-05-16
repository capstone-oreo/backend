package com.oreo.backend.file.dto.response;

import com.oreo.backend.file.document.File;
import com.oreo.backend.file.service.FileService;
import lombok.Getter;

@Getter
public class FileResponse {

    private final String id;
    private final String title;
    private final String filename;

    public FileResponse(File file) {
        this.id = file.getId();
        this.filename = file.getFilename();
        this.title = file.getTitle();
    }

    public String getUri() {
        return FileService.PRE_URI + this.filename;
    }
}
