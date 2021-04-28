package com.epam.jwd.core_final.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 * from {@link Planet}
 * to {@link Planet}
 */
public class FlightMission extends AbstractBaseEntity {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Long distance;
    private Spaceship assignedSpaceShip;
    private List<CrewMember> assignedCrew = new ArrayList<>();
    private final MissionResult missionResult;
    private final Planet from;
    private final Planet to;

    public FlightMission(
            Long id,
            String name,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Planet from,
            Planet to,
            Long distance,
            MissionResult missionResult) {
        super(id, name);
        this.startDate = startDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.endDate = endDate;
        this.missionResult = missionResult;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getDistance() {
        return distance;
    }

    public Spaceship getAssignedSpaceShip() {
        return assignedSpaceShip;
    }

    public void setAssignedSpaceShip(Spaceship assignedSpaceShip) {
        this.assignedSpaceShip = assignedSpaceShip;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public void setAssignedCrew(List<CrewMember> assignedCrew) {
        this.assignedCrew = assignedCrew;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public Planet getFrom() {
        return from;
    }

    public Planet getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "FlightMission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", distance=" + distance +
                ", assignedSpaceShip=" + assignedSpaceShip +
                ", assignedCrew=" + assignedCrew +
                ", missionResult=" + missionResult +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
