package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SpaceshipFactoryTest {

    private final Map<Role, Short> map = new HashMap<>();

    public SpaceshipFactoryTest() {
        map.put(Role.resolveRoleById(1), (short)2);
        map.put(Role.resolveRoleById(2), (short)4);
        map.put(Role.resolveRoleById(3), (short)5);
    }

    @Test
    public void create() {
        Spaceship spaceshipExp = new Spaceship(null, "SuperStar", map, 20000L);
        Spaceship spaceShipAct = new SpaceshipFactory().create(null, "SuperStar", map, 20000L);
        Assert.assertEquals(spaceshipExp, spaceShipAct);
    }

    @Test
    public void assignId() {
        Spaceship spaceshipNoId = new Spaceship(null, "SuperStar", map, 20000L);
        Spaceship spaceship = new SpaceshipFactory().assignId(1L, spaceshipNoId);
        Spaceship spaceshipWithId = new Spaceship(1L, "SuperStar", map, 20000L);
        Assert.assertEquals(spaceship, spaceshipWithId);
    }
}