package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public abstract class BaseLoader<T extends BaseEntity> {

    private final String DESCRIPTION_START_SYMBOL = "#";

    protected final ApplicationProperties applicationProperties = new ApplicationProperties();

    protected final void read(String fileName, String inputDir, com.epam.jwd.core_final.repository.Parser parser) throws InvalidStateException {
        try (InputStream inputStream = getFileFromResourceAsStream(inputDir + File.separator + fileName);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.startsWith(DESCRIPTION_START_SYMBOL)) {
                    parser.parse(line, i);
                    i++;
                }
            }
        } catch (IOException e) {
            throw new InvalidStateException("Error of file reading");
        }
    }

    private InputStream getFileFromResourceAsStream(String path) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + path);
        } else {
            return inputStream;
        }
    }

    public abstract List<T> readData() throws InvalidStateException;

    public abstract void writeData() throws IOException;
}
