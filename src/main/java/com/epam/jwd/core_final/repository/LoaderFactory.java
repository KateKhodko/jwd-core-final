package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;

public class LoaderFactory {

    @SuppressWarnings("rawtypes")
    public <T extends BaseEntity> BaseLoader getLoader(Class<T> clazz) {
        if (clazz.equals(CrewMember.class)) {
            return new CrewLoader();
        }
        if (clazz.equals(Spaceship.class)) {
            return new SpaceshipsLoader();
        }
        if (clazz.equals(FlightMission.class)) {
            return new MissionsLoader();
        }
        if (clazz.equals(Planet.class)) {
            return new PlanetsLoader();
        }
        return null;
    }
}
