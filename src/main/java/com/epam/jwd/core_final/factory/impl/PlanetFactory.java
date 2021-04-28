package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import com.epam.jwd.core_final.factory.EntityFactory;

public class PlanetFactory implements EntityFactory<Planet> {

    @Override
    public Planet create(Object... args) {
        if (args.length == 3 &&
                args[1] instanceof String &&
                args[2] instanceof Point) {
            Long id = args[0] instanceof Long ? (Long) args[0] : null;
            return new Planet(id, (String) args[1], (Point) args[2]);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Planet assignId(Long id, Planet item) {
        return new Planet(id, item.getName(), item.getLocation());
    }
}
