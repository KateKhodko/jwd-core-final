package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class FlightMissionFactoryTest {

    @Test
    public void create() {
        LocalDateTime startDate = LocalDateTime.now();
        Planet from = new Planet(1L, "Earth", new Point(21, 23));
        Planet to = new Planet(2L, "Neptune", new Point(156, 143));
        FlightMission flightMissionAct = new FlightMissionFactory().create(
                null,
                "Mission1",
                startDate,
                from,
                to,
                MissionResult.FAILED);
        Long distance = (long) Point.computeDistance(from.getLocation(), to.getLocation());
        FlightMission flightMissionExp = new FlightMission(
                null,
                "Mission1",
                startDate,
                startDate.plusSeconds(distance),
                from,
                to,
                distance,
                MissionResult.FAILED);

        Assert.assertEquals(flightMissionAct, flightMissionExp);
        Assert.assertEquals(distance, flightMissionAct.getDistance());
        Assert.assertEquals(flightMissionAct.getEndDate(), flightMissionExp.getEndDate());
    }

    @Test
    public void assignId() {
        LocalDateTime startDate = LocalDateTime.now();
        Planet from = new Planet(1L, "Earth", new Point(21, 23));
        Planet to = new Planet(2L, "Neptune", new Point(156, 143));
        long distance = (long) Point.computeDistance(from.getLocation(), to.getLocation());
        FlightMission flightMissionNoId = new FlightMission(
                null,
                "Mission1",
                startDate,
                startDate.plusSeconds(distance),
                from,
                to,
                distance,
                MissionResult.FAILED);
        FlightMission flightMission = new FlightMissionFactory().assignId(1L, flightMissionNoId);
        FlightMission flightMissionWithId = new FlightMission(
                1L,
                "Mission1",
                startDate,
                startDate.plusSeconds(distance),
                from,
                to,
                distance,
                MissionResult.FAILED);

        Assert.assertEquals(flightMission, flightMissionWithId);
    }
}