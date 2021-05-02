package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import org.junit.Assert;
import org.junit.Test;

public class PlanetFactoryTest {

    private final Planet planetExp = new Planet(null, "Uran", new Point(23, 45));

    @Test
    public void create() {
        Planet planetAct = new PlanetFactory().create(null, "Uran", new Point(23, 45));
        Assert.assertEquals(planetAct, planetExp);
    }

    @Test
    public void assignId() {
        Planet planet = new PlanetFactory().assignId(1L, planetExp);
        Planet planetWithId = new Planet(1L, "Uran", new Point(23, 45));
        Assert.assertEquals(planet, planetWithId);
    }
}