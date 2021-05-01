package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.CrewMemberNotAbleCreateException;
import com.epam.jwd.core_final.exception.CrewMemberUpdateException;
import com.epam.jwd.core_final.exception.SpaceshipUpdateException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceShipServiceImplTest {

    private final List<Spaceship> spaceships = new ArrayList<>();
    private final SpaceShipServiceImpl shipService;

    public SpaceShipServiceImplTest() {
        Map<Role, Short> map1 = new HashMap<>();
        map1.put(Role.resolveRoleById(1), (short)1);
        map1.put(Role.resolveRoleById(2), (short)2);
        map1.put(Role.resolveRoleById(3), (short)3);
        Spaceship spaceship1 = new Spaceship(1L, "Starship", map1, 12000L);

        Map<Role, Short> map2 = new HashMap<>();
        map2.put(Role.resolveRoleById(1), (short)2);
        map2.put(Role.resolveRoleById(2), (short)5);
        map2.put(Role.resolveRoleById(3), (short)2);
        Spaceship spaceship2 = new Spaceship(2L, "SuperShip", map2, 24000L);

        Map<Role, Short> map3 = new HashMap<>();
        map2.put(Role.resolveRoleById(1), (short)3);
        map2.put(Role.resolveRoleById(2), (short)2);
        map2.put(Role.resolveRoleById(3), (short)5);
        Spaceship spaceship3 = new Spaceship(3L, "SunShip", map3, 24000L);

        spaceships.add(spaceship1);
        spaceships.add(spaceship2);
        spaceships.add(spaceship3);

        SpaceShipServiceImpl.init(spaceships);
        shipService = SpaceShipServiceImpl.getInstance();
    }

    @Test
    public void findAllSpaceships() {
        List<Spaceship> spaceshipsExp = shipService.findAllSpaceships();
        Assert.assertEquals(spaceshipsExp, spaceships);
    }

    @Test
    public void createSpaceship() throws CrewMemberNotAbleCreateException {
        Map<Role, Short> map = new HashMap<>();
        map.put(Role.resolveRoleById(1), (short)2);
        map.put(Role.resolveRoleById(2), (short)4);
        map.put(Role.resolveRoleById(3), (short)5);
        Spaceship spaceship = new Spaceship(null, "SuperStar", map, 20000L);

        Spaceship spaceshipExp = new Spaceship(4L, "SuperStar", map, 20000L);

        Spaceship createdSpaceship = shipService.createSpaceship(spaceship);
        Assert.assertEquals(createdSpaceship, spaceshipExp);
        spaceships.remove(createdSpaceship);
    }

    @Test
    public void findSpaceshipByCriteria() {
        SpaceshipCriteria spaceshipCriteria = new SpaceshipCriteria.SpaceshipBuilder()
                .withId(2)
                .withName("SuperShip")
                .build();

        Spaceship spaceship = shipService.findSpaceshipByCriteria(spaceshipCriteria).orElse(null);

        Assert.assertEquals(spaceship, spaceships.get(1));
    }



    @Test
    public void findAllSpaceshipsByCriteria() {
        SpaceshipCriteria spaceshipCriteria = new SpaceshipCriteria.SpaceshipBuilder()
                .withFlightDistance(24000L)
                .build();

        List<Spaceship> foundSpaceships = shipService.findAllSpaceshipsByCriteria(spaceshipCriteria);
        List<Spaceship> foundSpaceshipsExp = new ArrayList<>();
        foundSpaceshipsExp.add(spaceships.get(1));
        foundSpaceshipsExp.add(spaceships.get(2));

        Assert.assertEquals(foundSpaceships, foundSpaceshipsExp);
    }

    @Test
    public void updateSpaceshipDetails() throws SpaceshipUpdateException {
        MissionServiceImpl.init(new ArrayList<>());
        Map<Role, Short> map = new HashMap<>();
        map.put(Role.resolveRoleById(1), (short)1);
        map.put(Role.resolveRoleById(2), (short)2);
        map.put(Role.resolveRoleById(3), (short)3);
        Spaceship spaceship = new Spaceship(1L, "Starship", map, 24000L);
        Spaceship updatedSpaceship = shipService.updateSpaceshipDetails(spaceship);
        Assert.assertEquals(spaceship, updatedSpaceship);

        Assert.assertThrows(SpaceshipUpdateException.class, () -> {
            Spaceship spaceship2 = new Spaceship(1L, "Starlight", map, 24000L);
            shipService.updateSpaceshipDetails(spaceship2);
        });
    }


}