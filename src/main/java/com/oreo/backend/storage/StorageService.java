package com.oreo.backend.storage;

import java.io.File;
import java.io.IOException;

public interface StorageService {
    String uploadVoice(File file) throws IOException;
}
