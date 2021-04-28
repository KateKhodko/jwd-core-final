package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;

import java.time.LocalDateTime;

public class FlightMissionFactory implements EntityFactory<FlightMission> {

    @Override
    public FlightMission create(Object... args) {
        if (args.length == 6 &&
                args[1] instanceof String &&
                args[2] instanceof LocalDateTime &&
                args[3] instanceof Planet &&
                args[4] instanceof Planet &&
                args[5] instanceof MissionResult) {
            Long id = args[0] instanceof Long ? (Long) args[0] : null;
            Planet from = (Planet) args[3];
            Planet to = (Planet) args[4];
            LocalDateTime startDate = (LocalDateTime) args[2];
            long distance = SpacemapServiceImpl.getInstance().getDistanceBetweenPlanets(from, to);
            return new FlightMission(
                    id,
                    (String) args[1],
                    startDate,
                    startDate.plusSeconds(distance),
                    from,
                    to,
                    distance,
                    (MissionResult) args[5]);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public FlightMission assignId(Long id, FlightMission item) {
        return new FlightMission(
                id,
                item.getName(),
                item.getStartDate(),
                item.getEndDate(),
                item.getFrom(),
                item.getTo(),
                item.getDistance(),
                item.getMissionResult());
    }
}
