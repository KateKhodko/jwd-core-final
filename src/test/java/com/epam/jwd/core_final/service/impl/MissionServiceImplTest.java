package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import com.epam.jwd.core_final.exception.CrewMemberNotAbleCreateException;
import com.epam.jwd.core_final.exception.MissionUpdateException;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MissionServiceImplTest {

    private final List<FlightMission> missions = new ArrayList<>();
    private final MissionServiceImpl missionService;

    public MissionServiceImplTest() {
        Planet from1 = new Planet(1L, "Sun", new Point(29, 45));
        Planet to1 = new Planet(2L, "Neptune", new Point(56, 43));
        FlightMission flightMission1 = new FlightMission(
                1L,
                "Mission1",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(3),
                from1,
                to1,
                200L,
                MissionResult.COMPLETED);

        Planet from2 = new Planet(3L, "Mars", new Point(49, 75));
        Planet to2 = new Planet(4L, "Venera", new Point(40, 60));
        FlightMission flightMission2 = new FlightMission(
                2L,
                "Mission2",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                from2,
                to2,
                300L,
                MissionResult.CANCELLED);

        Planet from3 = new Planet(5L, "Upiter", new Point(89, 20));
        Planet to3 = new Planet(6L, "Uran", new Point(230, 120));
        FlightMission flightMission3 = new FlightMission(
                3L,
                "Mission3",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                from2,
                to2,
                900L,
                MissionResult.COMPLETED);

        missions.add(flightMission1);
        missions.add(flightMission2);
        missions.add(flightMission3);

        MissionServiceImpl.init(missions);
        missionService = MissionServiceImpl.getInstance();
    }


    @Test
    public void findAllMissions() {
        List<FlightMission> missionsExp = missionService.findAllMissions();
        Assert.assertEquals(missionsExp, missions);
    }

    @Test
    public void createMission() throws CrewMemberNotAbleCreateException {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(3);
        Planet from = new Planet(7L, "Moon", new Point(29, 45));
        Planet to = new Planet(8L, "Earth", new Point(56, 43));
        FlightMission flightMission = new FlightMission(
                null,
                "CreateMission",
                startDate,
                endDate,
                from,
                to,
                200L,
                MissionResult.COMPLETED);

        FlightMission flightMissionExp = new FlightMission(
                4L,
                "CreateMission",
                startDate,
                endDate,
                from,
                to,
                200L,
                MissionResult.COMPLETED);

        FlightMission flightMissionCreated = missionService.createMission(flightMission);
        Assert.assertEquals(flightMissionCreated, flightMissionExp);
        missions.remove(flightMissionCreated);
    }

    @Test
    public void findMissionByCriteria() {
        FlightMissionCriteria criteria1 = new FlightMissionCriteria.MissionBuilder()
                .withDistance(200L)
                .withName("Mission1")
                .build();

        FlightMissionCriteria criteria2 = new FlightMissionCriteria.MissionBuilder()
                .withId(2L)
                .withMissionResult(MissionResult.CANCELLED)
                .build();

        FlightMission flightMission1 = missionService.findMissionByCriteria(criteria1).orElse(null);
        FlightMission flightMission2 = missionService.findMissionByCriteria(criteria2).orElse(null);

        Assert.assertEquals(flightMission1, missions.get(0));
        Assert.assertEquals(flightMission2, missions.get(1));
    }

    @Test
    public void findAllMissionsByCriteria() {
        FlightMissionCriteria criteria = new FlightMissionCriteria.MissionBuilder()
                .withMissionResult(MissionResult.COMPLETED)
                .build();

        List<FlightMission> foundMissions = missionService.findAllMissionsByCriteria(criteria);
        List<FlightMission> flightMissionsExp = new ArrayList<>();
        flightMissionsExp.add(missions.get(0));
        flightMissionsExp.add(missions.get(2));

        Assert.assertEquals(foundMissions, flightMissionsExp);
    }

    @Test
    public void updateMissionDetails() throws MissionUpdateException {
        FlightMission oldFlightMission = missions.get(0);
        FlightMission flightMission = new FlightMission(
                oldFlightMission.getId(),
                oldFlightMission.getName(),
                oldFlightMission.getStartDate(),
                oldFlightMission.getEndDate(),
                oldFlightMission.getFrom(),
                oldFlightMission.getTo(),
                oldFlightMission.getDistance(),
                MissionResult.IN_PROGRESS);
        FlightMission updatedFlightMission = missionService.updateMissionDetails(flightMission);
        Assert.assertEquals(flightMission, updatedFlightMission);

        Assert.assertThrows(MissionUpdateException.class, () -> {
            FlightMission flightMission2 = new FlightMission(
                    1L,
                    "Mission1",
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(3),
                    oldFlightMission.getFrom(),
                    oldFlightMission.getTo(),
                    400L,
                    MissionResult.IN_PROGRESS);
            missionService.updateMissionDetails(flightMission2);
        });
    }
}