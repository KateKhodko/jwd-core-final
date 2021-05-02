package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import com.epam.jwd.core_final.service.SpacemapService;

import java.util.ArrayList;
import java.util.List;

public class SpacemapServiceImpl implements SpacemapService {

    private static SpacemapServiceImpl instance;

    private List<Planet> planets = new ArrayList<>();

    private SpacemapServiceImpl() {
    }

    public static SpacemapServiceImpl getInstance() {
        if (instance == null) {
            instance = new SpacemapServiceImpl();
        }
        return instance;
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

    public void setPlanets(List<Planet> planets) {
        this.planets = planets;
    }
}
