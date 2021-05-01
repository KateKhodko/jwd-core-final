package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.CrewMemberNotAbleCreateException;
import com.epam.jwd.core_final.exception.CrewMemberUpdateException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CrewServiceImplTest {

    private final List<CrewMember> crewMembers = new ArrayList<>();
    private final CrewServiceImpl crewService;

    public CrewServiceImplTest() {
        CrewMember crewMember1 = new CrewMember(1L, "Petya", Role.COMMANDER, Rank.CAPTAIN);
        CrewMember crewMember2 = new CrewMember(2L, "Vasya", Role.FLIGHT_ENGINEER, Rank.CAPTAIN);
        CrewMember crewMember3 = new CrewMember(3L, "Bob", Role.COMMANDER, Rank.SECOND_OFFICER);

        crewMembers.add(crewMember1);
        crewMembers.add(crewMember2);
        crewMembers.add(crewMember3);

        CrewServiceImpl.init(crewMembers);
        crewService = CrewServiceImpl.getInstance();
    }

    @Test
    public void findAllCrewMembers() {
        List<CrewMember> crewMembersExp = crewService.findAllCrewMembers();
        Assert.assertEquals(crewMembersExp, crewMembers);
    }

    @Test
    public void createCrewMember() throws CrewMemberNotAbleCreateException {
        CrewMember crewMember4 = new CrewMember(null, "Ivan", Role.FLIGHT_ENGINEER, Rank.TRAINEE);
        CrewMember crewMemberExp = new CrewMember(4L, "Ivan", Role.FLIGHT_ENGINEER, Rank.TRAINEE);

        CrewMember createdMember = crewService.createCrewMember(crewMember4);
        Assert.assertEquals(createdMember, crewMemberExp);
        crewMembers.remove(createdMember);
    }

    @Test
    public void findCrewMemberByCriteria() {
        CrewMemberCriteria criteria1 = new CrewMemberCriteria.CrewMemberBuilder()
                .withId(1L)
                .withName("Petya")
                .build();
        CrewMemberCriteria criteria2 = new CrewMemberCriteria.CrewMemberBuilder()
                .withRole(Role.FLIGHT_ENGINEER)
                .withRank(Rank.CAPTAIN)
                .build();

        CrewMember crewMember1 = crewService.findCrewMemberByCriteria(criteria1).orElse(null);
        CrewMember crewMember2 = crewService.findCrewMemberByCriteria(criteria2).orElse(null);

        Assert.assertEquals(crewMember1, crewMembers.get(0));
        Assert.assertEquals(crewMember2, crewMembers.get(1));
    }

    @Test
    public void findCrewMembersByCriteria() {
        CrewMemberCriteria criteria = new CrewMemberCriteria.CrewMemberBuilder()
                .withRank(Rank.CAPTAIN)
                .build();
        List<CrewMember> foundMembers = crewService.findAllCrewMembersByCriteria(criteria);
        List<CrewMember> foundMembersExp = new ArrayList<>();
        foundMembersExp.add(crewMembers.get(0));
        foundMembersExp.add(crewMembers.get(1));

        Assert.assertEquals(foundMembers, foundMembersExp);
    }

    @Test
    public void updateCrewMemberDetails() throws CrewMemberUpdateException {
        MissionServiceImpl.init(new ArrayList<>());
        CrewMember crewMember = new CrewMember(1L, "Petya", Role.FLIGHT_ENGINEER, Rank.FIRST_OFFICER);
        CrewMember crewMemberUpdated = crewService.updateCrewMemberDetails(crewMember);
        Assert.assertEquals(crewMember, crewMemberUpdated);

        Assert.assertThrows(CrewMemberUpdateException.class, () -> {
            CrewMember crewMember2 = new CrewMember(1L, "Sasha", Role.FLIGHT_ENGINEER, Rank.FIRST_OFFICER);
            crewService.updateCrewMemberDetails(crewMember2);
        });
    }
}