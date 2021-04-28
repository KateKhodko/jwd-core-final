package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long distance;
    private Spaceship assignedSpaceShip;
    private List<CrewMember> assignedCrew;
    private MissionResult missionResult;
    private Planet from;
    private Planet to;

    public boolean isStartDate(FlightMission flightMission) {
        return startDate == null || startDate.equals(flightMission.getStartDate());
    }

    public boolean isEndDate(FlightMission flightMission) {
        return endDate == null || endDate.equals(flightMission.getEndDate());
    }

    public boolean isDistance(FlightMission flightMission) {
        return distance == null || distance.equals(flightMission.getDistance());
    }

    public boolean isAssignedSpaceship(FlightMission flightMission) {
        return assignedSpaceShip == null || assignedSpaceShip.equals(flightMission.getAssignedSpaceShip());
    }

    public boolean isAssignedCrew(FlightMission flightMission) {
        return assignedCrew == null || assignedCrew.equals(flightMission.getAssignedCrew());
    }

    public boolean isMissionResult(FlightMission flightMission) {
        return missionResult == null || missionResult.equals(flightMission.getMissionResult());
    }

    public boolean isFrom(FlightMission flightMission) {
        return from == null || from.equals(flightMission.getFrom());
    }

    public boolean isTo(FlightMission flightMission) {
        return to == null || to.equals(flightMission.getTo());
    }

    public static class MissionBuilder extends BaseBuilder<FlightMission, MissionBuilder> {

        protected final FlightMissionCriteria flightMissionCriteria;

        public MissionBuilder() {
            super(new FlightMissionCriteria());
            flightMissionCriteria = (FlightMissionCriteria) criteria;
        }

        public MissionBuilder withStartDate(LocalDateTime startDate) {
            flightMissionCriteria.startDate = startDate;
            return this;
        }

        public MissionBuilder withEndDate(LocalDateTime endDate) {
            flightMissionCriteria.endDate = endDate;
            return this;
        }

        public MissionBuilder withDistance(long distance) {
            flightMissionCriteria.distance = distance;
            return this;
        }

        public MissionBuilder withAssignedSpaceShip(Spaceship assignedSpaceShip) {
            flightMissionCriteria.assignedSpaceShip = assignedSpaceShip;
            return this;
        }

        public MissionBuilder withAssignedCrew(List<CrewMember> assignedCrew) {
            flightMissionCriteria.assignedCrew = assignedCrew;
            return this;
        }

        public MissionBuilder withMissionResult(MissionResult missionResult) {
            flightMissionCriteria.missionResult = missionResult;
            return this;
        }

        public MissionBuilder withFrom(Planet from) {
            flightMissionCriteria.from = from;
            return this;
        }

        public MissionBuilder withTo(Planet to) {
            flightMissionCriteria.to = to;
            return this;
        }

        public FlightMissionCriteria build() {
            return flightMissionCriteria;
        }

        @Override
        protected MissionBuilder getThis() {
            return this;
        }
    }
}
