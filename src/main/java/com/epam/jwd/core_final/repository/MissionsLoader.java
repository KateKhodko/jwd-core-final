package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;

public class MissionsLoader extends BaseLoader<FlightMission> {

    private final String RESOURCES_DIR_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator;

    @Override
    public List<FlightMission> readData() throws InvalidStateException {
        throw new AssertionError("Method not implements");
    }

    @Override
    public void writeData() throws IOException {
        List<FlightMission> flightMissions = MissionServiceImpl.getInstance().findAllMissions();
        ApplicationProperties applicationProperties = new ApplicationProperties();
        String dirPath = RESOURCES_DIR_PATH + applicationProperties.getOutputRootDir() + File.separator;
        Path directory = Paths.get(dirPath);
        if (!Files.isDirectory(directory)) {
            Files.createDirectory(directory);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String formatter = new ApplicationProperties().getDateTimeFormat();
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        objectMapper.setDateFormat(sdf);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(dirPath + applicationProperties.getMissionsFileName()), flightMissions);
    }
}
