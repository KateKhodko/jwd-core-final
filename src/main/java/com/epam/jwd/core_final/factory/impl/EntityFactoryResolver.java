package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.FactoryResolver;

public class EntityFactoryResolver implements FactoryResolver {
    public static final EntityFactoryResolver instance = new EntityFactoryResolver();

    @SuppressWarnings("rawtypes")
    @Override
    public <T extends BaseEntity> EntityFactory getFactory(Class<T> tClass) {
        if (tClass.equals(CrewMember.class)) {
            return new CrewMemberFactory();
        }
        if (tClass.equals(Spaceship.class)) {
            return new SpaceshipFactory();
        }
        if (tClass.equals(Planet.class)) {
            return new PlanetFactory();
        }
        if (tClass.equals(FlightMission.class)) {
            return new FlightMissionFactory();
        }
        return null;
    }
}
