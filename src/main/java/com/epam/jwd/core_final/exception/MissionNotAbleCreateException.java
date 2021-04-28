package com.epam.jwd.core_final.exception;

import com.epam.jwd.core_final.domain.FlightMission;

public class MissionNotAbleCreateException extends RuntimeException {
    private final FlightMission flightMission;

    public MissionNotAbleCreateException(FlightMission flightMission) {
        super();
        this.flightMission = flightMission;
    }

    @Override
    public String getMessage() {
        return "Flight mission : id = " + flightMission.getId()
                + " name = " + flightMission.getName()
                + " is not able to be created";
    }
}
