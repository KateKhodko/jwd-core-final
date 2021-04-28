package com.epam.jwd.core_final.menu;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;

import java.util.List;

public class PlanetMenu {

    public PlanetMenu(MainMenu mainMenu) {
    }

    void printPlanets() {
        List<Planet> planets = SpacemapServiceImpl.getInstance().findAllPlanets();
        planets.forEach(System.out::println);
    }
}
