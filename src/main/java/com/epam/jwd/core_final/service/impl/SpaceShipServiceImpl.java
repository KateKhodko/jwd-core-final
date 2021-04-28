package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.SpaceshipNotAbleAssignedException;
import com.epam.jwd.core_final.exception.SpaceshipNotAbleCreateException;
import com.epam.jwd.core_final.exception.SpaceshipUpdateException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.SpaceshipService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpaceShipServiceImpl implements SpaceshipService {

    private static SpaceShipServiceImpl instance;

    private final List<Spaceship> spaceships;

    private SpaceShipServiceImpl(ApplicationContext applicationContext) {
        spaceships = (List<Spaceship>) applicationContext.retrieveBaseEntityList(Spaceship.class);
    }

    public static SpaceShipServiceImpl getInstance() {
        if (instance == null) {
            throw new AssertionError("You have to call init first");
        }
        return instance;
    }

    public static void init(ApplicationContext applicationContext) {
        if (instance == null) {
            instance = new SpaceShipServiceImpl(applicationContext);
        }
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return spaceships;
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        return getFilteredStream(criteria).collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        return getFilteredStream(criteria).findFirst();
    }

    private Stream<Spaceship> getFilteredStream(Criteria<? extends Spaceship> criteria) {
        SpaceshipCriteria spaceshipCriteria = (SpaceshipCriteria) criteria;
        return spaceships.stream().filter(spaceship -> spaceshipCriteria.isId(spaceship) &&
                spaceshipCriteria.isName(spaceship) &&
                spaceshipCriteria.isFlightDistance(spaceship) &&
                spaceshipCriteria.isCrew(spaceship) &&
                spaceshipCriteria.readyForNextMissions(spaceship)
        );
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) throws SpaceshipUpdateException {
        if (!spaceships.contains(spaceship)) {
            throw new SpaceshipUpdateException("Id was changed");
        }
        int index = spaceships.indexOf(spaceship);
        Spaceship oldSpaceShip = spaceships.get(index);
        if (!oldSpaceShip.getName().equals(spaceship.getName())) {
            throw new SpaceshipUpdateException("Name was changed");
        }
        if (!oldSpaceShip.getReadyForNextMissions()) {
            throw new SpaceshipUpdateException("Space ship not ready for next mission");
        }
        if (!MissionServiceImpl.getInstance().checkShipOnMission(spaceship)) {
            throw new SpaceshipUpdateException("Space ship is already on mission");
        }
        spaceships.set(index, spaceship);
        return spaceship;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship, FlightMission flightMission) throws SpaceshipNotAbleAssignedException {
        if (!spaceships.contains(spaceship) || !spaceship.getReadyForNextMissions()) {
            throw new SpaceshipNotAbleAssignedException(spaceship);
        }
        flightMission.setAssignedSpaceShip(spaceship);
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws SpaceshipNotAbleCreateException {
        if (spaceships.contains(spaceship)) {
            throw new SpaceshipNotAbleCreateException(spaceship);
        }
        Long id = NassaContext.generateId(spaceships);
        Spaceship newSpaceShip = new SpaceshipFactory().assignId(id, spaceship);
        spaceships.add(newSpaceShip);
        return newSpaceShip;
    }

    public void assignRandomShipOnMission(FlightMission flightMission) throws SpaceshipNotAbleAssignedException {
        List<Spaceship> readySpaceships = spaceships.stream()
                .filter(spaceship -> spaceship.getReadyForNextMissions()
                        && flightMission.getDistance() < spaceship.getFlightDistance()
                        && MissionServiceImpl.getInstance().checkShipOnMission(spaceship))
                .collect(Collectors.toList());
        if (readySpaceships.size() == 0) {
            throw new SpaceshipNotAbleAssignedException(null);
        }
        int i = (int) (Math.random() * readySpaceships.size());
        assignSpaceshipOnMission(readySpaceships.get(i), flightMission);
    }
}
