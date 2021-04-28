package com.epam.jwd.core_final.domain;

import static com.epam.jwd.core_final.domain.PropertyKey.CREW_FILE_NAME;
import static com.epam.jwd.core_final.domain.PropertyKey.DATE_TIME_FORMAT;
import static com.epam.jwd.core_final.domain.PropertyKey.FILE_REFRESH_RATE;
import static com.epam.jwd.core_final.domain.PropertyKey.INPUT_ROOT_DIR;
import static com.epam.jwd.core_final.domain.PropertyKey.MISSIONS_FILE_NAME;
import static com.epam.jwd.core_final.domain.PropertyKey.OUTPUT_ROOT_DIR;
import static com.epam.jwd.core_final.domain.PropertyKey.SPACEMAP_FILE_NAME;
import static com.epam.jwd.core_final.domain.PropertyKey.SPACESHIP_FILE_NAME;
import static com.epam.jwd.core_final.util.PropertyReaderUtil.getIntegerProperty;
import static com.epam.jwd.core_final.util.PropertyReaderUtil.getProperty;
import static com.epam.jwd.core_final.util.PropertyReaderUtil.loadProperties;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */
public final class ApplicationProperties {

    private final String inputRootDir;
    private final String outputRootDir;
    private final String crewFileName;
    private final String missionsFileName;
    private final String spaceshipsFileName;
    private final String spacemapFileName;
    private final Integer fileRefreshRate;
    private final String dateTimeFormat;

    public ApplicationProperties() {
        loadProperties();
        inputRootDir = getProperty(INPUT_ROOT_DIR);
        outputRootDir = getProperty(OUTPUT_ROOT_DIR);
        crewFileName = getProperty(CREW_FILE_NAME);
        missionsFileName = getProperty(MISSIONS_FILE_NAME);
        spaceshipsFileName = getProperty(SPACESHIP_FILE_NAME);
        spacemapFileName = getProperty(SPACEMAP_FILE_NAME);
        fileRefreshRate = getIntegerProperty(FILE_REFRESH_RATE);
        dateTimeFormat = getProperty(DATE_TIME_FORMAT);
    }

    public String getInputRootDir() {
        return inputRootDir;
    }

    public String getOutputRootDir() {
        return outputRootDir;
    }

    public String getCrewFileName() {
        return crewFileName;
    }

    public String getMissionsFileName() {
        return missionsFileName;
    }

    public String getSpaceshipsFileName() {
        return spaceshipsFileName;
    }

    public String getSpacemapFileName() {
        return spacemapFileName;
    }

    public Integer getFileRefreshRate() {
        return fileRefreshRate;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }
}
