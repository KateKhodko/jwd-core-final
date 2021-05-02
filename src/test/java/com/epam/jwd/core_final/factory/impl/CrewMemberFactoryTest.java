package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import org.junit.Assert;
import org.junit.Test;

public class CrewMemberFactoryTest {

    private final CrewMember crewMemberExp = new CrewMember(null, "Ivan", Role.FLIGHT_ENGINEER, Rank.TRAINEE);

    @Test
    public void create() {
        CrewMember crewMemberAct = new CrewMemberFactory().create(null, "Ivan", Role.FLIGHT_ENGINEER, Rank.TRAINEE);
        Assert.assertEquals(crewMemberExp, crewMemberAct);
    }

    @Test
    public void assignId() {
        CrewMember crewMember = new CrewMemberFactory().assignId(1L, crewMemberExp);
        CrewMember crewMemberWithId = new CrewMember(1L, "Ivan", Role.FLIGHT_ENGINEER, Rank.TRAINEE);
        Assert.assertEquals(crewMember, crewMemberWithId);
    }
}