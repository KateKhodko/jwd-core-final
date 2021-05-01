package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlanetsLoader extends BaseLoader<Planet> {

    private final String SPACE_MAP_SEPARATOR = ",";

    @Override
    public List<Planet> readData() throws InvalidStateException {
        List<Planet> planets = new ArrayList<>();
        String fileName = applicationProperties.getSpacemapFileName();
        String inputDir = applicationProperties.getInputRootDir();

        read(fileName, inputDir, (line, y) -> {
            String[] planetStr = line.split(SPACE_MAP_SEPARATOR);
            for (int x = 0; x < planetStr.length; x++) {
                String str = planetStr[x].trim();
                if (!str.equals("null")) {
                    Point coords = new Point(x, y);
                    Planet planet = new PlanetFactory().create(null, planetStr[x], coords);
                    planets.add(planet);
                }
            }
        });
        return planets;
    }

    @Override
    public void writeData() throws IOException {
        throw new AssertionError("Method not implements");
    }
}
