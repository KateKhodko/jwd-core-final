package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import com.epam.jwd.core_final.service.SpacemapService;

import java.util.List;

public class SpacemapServiceImpl implements SpacemapService {

    private static SpacemapServiceImpl instance;

    private final List<Planet> planets;

    private SpacemapServiceImpl(List<Planet> planets) {
        this.planets = planets;
    }

    public static SpacemapServiceImpl getInstance() {
        if (instance == null) {
            throw new AssertionError("You have to call init first");
        }
        return instance;
    }

    public synchronized static void init(List<Planet> planets) {
        if (instance == null) {
            instance = new SpacemapServiceImpl(planets);
        }
    }

    @Override
    public List<Planet> findAllPlanets() {
        return planets;
    }

    @Override
    public Planet getRandomPlanet() {
        int i = (int) (Math.random() * planets.size());
        return planets.get(i);
    }

    @Override
    public int getDistanceBetweenPlanets(Planet first, Planet second) {
        return (int) Point.computeDistance(first.getLocation(), second.getLocation());
    }
}
