package com.epam.jwd.core_final.domain;

public enum PropertyKey {

    INPUT_ROOT_DIR("inputRootDir"),
    OUTPUT_ROOT_DIR("outputRootDir"),
    CREW_FILE_NAME("crewFileName"),
    MISSIONS_FILE_NAME("missionsFileName"),
    SPACESHIP_FILE_NAME("spaceshipsFileName"),
    SPACEMAP_FILE_NAME("spacemapFileName"),
    FILE_REFRESH_RATE("fileRefreshRate"),
    DATE_TIME_FORMAT("dateTimeFormat");

    private final String key;

    PropertyKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
