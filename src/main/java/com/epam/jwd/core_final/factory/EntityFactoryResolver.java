package com.epam.jwd.core_final.factory;

import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;

public class EntityFactoryResolver {
    public static final EntityFactoryResolver instance = new EntityFactoryResolver();

    @SuppressWarnings("rawtypes")
    public  <T extends BaseEntity> EntityFactory getFactory(Class<T> tClass) {
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
