package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SpacemapServiceImplTest {

    private final List<Planet> planets = new ArrayList<>();
    private final SpacemapServiceImpl spacemapService = SpacemapServiceImpl.getInstance();

    @Before
    public void setUp() {
        planets.clear();
        Planet planet1 = new Planet(1L, "Uran", new Point(10, 20));
        Planet planet2 = new Planet(2L, "Saturn", new Point(40, 30));
        Planet planet3 = new Planet(3L, "Bob", new Point(20, 100));

        planets.add(planet1);
        planets.add(planet2);
        planets.add(planet3);

        spacemapService.setPlanets(planets);
    }

    @Test
    public void findAllPlanets() {
        List<Planet> planetsExp = spacemapService.findAllPlanets();
        Assert.assertEquals(planetsExp, planets);
    }

    @Test
    public void getRandomPlanet() {
        Planet planet = spacemapService.getRandomPlanet();
        Assert.assertTrue(planets.contains(planet));
    }

    @Test
    public void getDistanceBetweenPlanets() {
        Point p1 = planets.get(0).getLocation();
        Point p2 = planets.get(1).getLocation();
        int distance = spacemapService.getDistanceBetweenPlanets(planets.get(0), planets.get(1));
        int distanceExp = (int) Math.sqrt((p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY()));
        Assert.assertEquals(distance, distanceExp);
    }
}