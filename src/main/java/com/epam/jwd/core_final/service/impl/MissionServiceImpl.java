package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.MissionNotAbleCreateException;
import com.epam.jwd.core_final.exception.MissionUpdateException;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.service.MissionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.jwd.core_final.domain.MissionResult.FAILED;

public class MissionServiceImpl implements MissionService {

    private static MissionServiceImpl instance;
    private final List<FlightMission> missions;

    private MissionServiceImpl(ApplicationContext applicationContext) {
        missions = (List<FlightMission>) applicationContext.retrieveBaseEntityList(FlightMission.class);
    }

    public static MissionServiceImpl getInstance() {
        if (instance == null) {
            throw new AssertionError("You have to call init first");
        }
        return instance;
    }

    public static void init(ApplicationContext applicationContext) {
        if (instance == null) {
            instance = new MissionServiceImpl(applicationContext);
        }
    }

    @Override
    public List<FlightMission> findAllMissions() {
        return missions;
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        return getFilteredStream(criteria).collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        return getFilteredStream(criteria).findFirst();
    }

    private Stream<FlightMission> getFilteredStream(Criteria<? extends FlightMission> criteria) {
        FlightMissionCriteria flightMissionCriteria = (FlightMissionCriteria) criteria;
        return missions.stream().filter(crewMember -> flightMissionCriteria.isId(crewMember) &&
                flightMissionCriteria.isName(crewMember) &&
                flightMissionCriteria.isStartDate(crewMember) &&
                flightMissionCriteria.isEndDate(crewMember) &&
                flightMissionCriteria.isDistance(crewMember) &&
                flightMissionCriteria.isAssignedSpaceship(crewMember) &&
                flightMissionCriteria.isAssignedCrew(crewMember) &&
                flightMissionCriteria.isMissionResult(crewMember) &&
                flightMissionCriteria.isFrom(crewMember) &&
                flightMissionCriteria.isTo(crewMember)
        );
    }

    @Override
    public FlightMission updateMissionDetails(FlightMission flightMission) throws MissionUpdateException {
        if (!missions.contains(flightMission)) {
            throw new MissionUpdateException("Id was changed");
        }
        int index = missions.indexOf(flightMission);
        FlightMission oldFlightMission = missions.get(index);
        if (!oldFlightMission.getName().equals(flightMission.getName())) {
            throw new MissionUpdateException("Name was changed");
        }
        if (!oldFlightMission.getStartDate().equals(flightMission.getStartDate())) {
            throw new MissionUpdateException("Start date was changed");
        }
        if (!oldFlightMission.getStartDate().equals(flightMission.getEndDate())) {
            throw new MissionUpdateException("End date was changed");
        }
        if (!oldFlightMission.getFrom().equals(flightMission.getFrom())) {
            throw new MissionUpdateException("From planet was changed");
        }
        if (!oldFlightMission.getTo().equals(flightMission.getTo())) {
            throw new MissionUpdateException("To planet was changed");
        }
        if (!oldFlightMission.getDistance().equals(flightMission.getDistance())) {
            throw new MissionUpdateException("Distance was changed");
        }
        missions.set(index, flightMission);
        return flightMission;
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) throws MissionNotAbleCreateException {
        if (missions.contains(flightMission)) {
            throw new MissionNotAbleCreateException(flightMission);
        }
        Long id = NassaContext.generateId(missions);
        FlightMission newMission = new FlightMissionFactory().assignId(id, flightMission);
        missions.add(newMission);
        return newMission;
    }

    public boolean checkCrewMembersOnMission(CrewMember crewMember) {
        for (FlightMission flightMission : missions) {
            for (CrewMember missionMember : flightMission.getAssignedCrew()) {
                if (crewMember.equals(missionMember)
                        && (flightMission.getEndDate().isAfter(LocalDateTime.now())
                        || flightMission.getMissionResult().equals(FAILED))
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkShipOnMission(Spaceship spaceship) {
        for (FlightMission flightMission : missions) {
            if (spaceship.equals(flightMission.getAssignedSpaceShip())
                    && (flightMission.getEndDate().isAfter(LocalDateTime.now())
                    || flightMission.getMissionResult().equals(FAILED))) {
                return false;
            }
        }
        return true;
    }
}
