package com.epam.jwd.core_final.domain;

import java.util.Map;

/**
 * crew {@link java.util.Map<Role, Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class Spaceship extends AbstractBaseEntity {

    private final Map<Role, Short> crew;
    private final Long flightDistance;
    private Boolean isReadyForNextMissions = true;

    public Spaceship(Long id, String name, Map<Role, Short> crew, Long flightDistance) {
        super(id, name);
        this.crew = crew;
        this.flightDistance = flightDistance;
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setNotReadyForNextMissions() {
        isReadyForNextMissions = false;
    }

    @Override
    public String toString() {
        return "Spaceship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", crew=" + crew +
                ", flightDistance=" + flightDistance +
                ", isReadyForNextMissions=" + isReadyForNextMissions +
                '}';
    }
}
