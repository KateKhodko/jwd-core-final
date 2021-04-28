package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.HashMap;
import java.util.Map;

public class SpaceshipFactory implements EntityFactory<Spaceship> {

    @Override
    public Spaceship create(Object... args) {
        if (args.length == 4 &&
                args[1] instanceof String &&
                args[2] instanceof Map &&
                args[3] instanceof Long) {
            Long id = args[0] instanceof Long ? (Long) args[0] : null;
            Map<?, ?> crewArgs = (Map<?, ?>) args[2];
            Map<Role, Short> crew = castCrewMap(crewArgs);
            return new Spaceship(id, (String) args[1], crew, (Long) args[3]);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Spaceship assignId(Long id, Spaceship item) {
        return new Spaceship(id, item.getName(), item.getCrew(), item.getFlightDistance());
    }

    private Map<Role, Short> castCrewMap(Map<?, ?> crewArgs) throws IllegalStateException {
        Map<Role, Short> crew = new HashMap<>();

        for (Map.Entry<?, ?> entry : crewArgs.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (key instanceof Role && value instanceof Short) {
                crew.put((Role) key, (Short) value);
            } else {
                throw new IllegalArgumentException();
            }
        }
        return crew;
    }
}
