package com.bemka.carrental.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {

    public static String getFileContentAsString(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
