package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.DataLoader;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

// todo
public class NassaContext implements ApplicationContext {

    // no getters/setters for them
    private final Collection<CrewMember> crewMembers = new ArrayList<>();
    private final Collection<Spaceship> spaceships = new ArrayList<>();
    private final Collection<Planet> planetMap = new ArrayList<>();
    private final Collection<FlightMission> flightMissions = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        Collection<T> collection = null;
        if (tClass.equals(CrewMember.class)) {
            collection = (Collection<T>) crewMembers;
        } else if (tClass.equals(Spaceship.class)) {
            collection = (Collection<T>) spaceships;
        } else if (tClass.equals(Planet.class)) {
            collection = (Collection<T>) planetMap;
        } else if (tClass.equals(FlightMission.class)) {
            collection = (Collection<T>) flightMissions;
        }
        return collection;
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        addCrewMembers();
        addSpaceShips();
        addPlanets();
    }

    private void addCrewMembers() throws InvalidStateException {
        List<CrewMember> members = DataLoader.getInstance().readCrew();
        crewMembers.clear();
        for (CrewMember crewMember: members) {
            CrewMember newMember = new CrewMemberFactory().assignId(generateId(crewMembers), crewMember);
            crewMembers.add(newMember);
        }
    }

    private void addSpaceShips() throws InvalidStateException {
        List<Spaceship> ships = DataLoader.getInstance().readSpaceships();
        spaceships.clear();
        for (Spaceship spaceship: ships) {
            Spaceship newSpaceship = new SpaceshipFactory().assignId(generateId(spaceships), spaceship);
            spaceships.add(newSpaceship);
        }
    }

    private void addPlanets() throws InvalidStateException {
        List<Planet> planets = DataLoader.getInstance().readPlanets();
        planetMap.clear();
        for (Planet planet: planets) {
            Planet newPlanet = new PlanetFactory().assignId(generateId(planetMap), planet);
            planetMap.add(newPlanet);
        }
    }

    public static <T extends BaseEntity> Long generateId(Collection<T> collection) {
        List<T> list = (List<T>) collection;
        if (list.size() == 0) {
            return 1L;
        }
        BaseEntity baseEntity = list.stream().max(Comparator.comparing(BaseEntity::getId)).get();
        return baseEntity.getId() + 1;
    }
}
