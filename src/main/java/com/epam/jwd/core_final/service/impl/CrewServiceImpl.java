package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.CrewMemberNotAbleAssignedException;
import com.epam.jwd.core_final.exception.CrewMemberNotAbleCreateException;
import com.epam.jwd.core_final.exception.CrewMemberUpdateException;
import com.epam.jwd.core_final.exception.MissionNotAbleCreateException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.CrewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrewServiceImpl implements CrewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrewServiceImpl.class);

    private static CrewServiceImpl instance;
    private final List<CrewMember> crewMembers;

    private CrewServiceImpl(ApplicationContext applicationContext) {
        crewMembers = (List<CrewMember>) applicationContext.retrieveBaseEntityList(CrewMember.class);
    }

    public static CrewServiceImpl getInstance() {
        if (instance == null) {
            throw new AssertionError("You have to call init first");
        }
        return instance;
    }

    public static void init(ApplicationContext applicationContext) {
        if (instance == null) {
            instance = new CrewServiceImpl(applicationContext);
        }
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return crewMembers;
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        return getFilteredStream(criteria).collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        return getFilteredStream(criteria).findFirst();
    }

    private Stream<CrewMember> getFilteredStream(Criteria<? extends CrewMember> criteria) {
        CrewMemberCriteria crewMemberCriteria = (CrewMemberCriteria) criteria;
        return findAllCrewMembers().stream().filter(crewMember -> crewMemberCriteria.isId(crewMember) &&
                crewMemberCriteria.isName(crewMember) &&
                crewMemberCriteria.isRole(crewMember) &&
                crewMemberCriteria.isRank(crewMember) &&
                crewMemberCriteria.readyForNextMissions(crewMember)
        );
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) throws CrewMemberUpdateException {
        if (!crewMembers.contains(crewMember)) {
            throw new CrewMemberUpdateException("Id was changed");
        }
        int index = crewMembers.indexOf(crewMember);
        CrewMember oldCrewMember = crewMembers.get(index);
        if (!oldCrewMember.getName().equals(crewMember.getName())) {
            throw new CrewMemberUpdateException("Name was changed");
        }
        if (!oldCrewMember.isReadyForNextMissions()) {
            throw new CrewMemberUpdateException("Crew member is not ready for next missions");
        }
        if (MissionServiceImpl.getInstance().checkCrewMembersOnMission(oldCrewMember)) {
            throw new CrewMemberUpdateException("Crew member is already on mission");
        }
        crewMembers.set(index, crewMember);
        return crewMember;
    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws CrewMemberNotAbleCreateException {
        if (crewMembers.contains(crewMember)) {
            throw new CrewMemberNotAbleCreateException(crewMember);
        }
        Long id = NassaContext.generateId(crewMembers);
        CrewMember newCrewMember = new CrewMemberFactory().assignId(id, crewMember);
        crewMembers.add(newCrewMember);
        return newCrewMember;
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember, FlightMission flightMission) throws CrewMemberNotAbleAssignedException {
        if (!crewMembers.contains(crewMember)
                || !crewMember.isReadyForNextMissions()
                || !validateMembersCountOnMission(crewMember, flightMission)
                || MissionServiceImpl.getInstance().checkCrewMembersOnMission(crewMember)) {
            throw new CrewMemberNotAbleAssignedException(crewMember);
        }
        flightMission.getAssignedCrew().add(crewMember);
    }

    public void assignRandomMembersOnMission(FlightMission flightMission) throws MissionNotAbleCreateException, CrewMemberNotAbleAssignedException {
        Map<Role, Short> assignedMap = new HashMap<>();

        for (CrewMember crewMember : crewMembers) {
            try {
                assignCrewMemberOnMission(crewMember, flightMission);
                short count = 0;
                if (assignedMap.containsKey(crewMember.getRole())) {
                    count = assignedMap.get(crewMember.getRole());
                }
                assignedMap.put(crewMember.getRole(), (short) (count + 1));
                if (assignedMap.equals(flightMission.getAssignedSpaceShip().getCrew())) {
                    return;
                }
            } catch (CrewMemberNotAbleAssignedException ignored) {
            }
        }
        throw new MissionNotAbleCreateException(flightMission);
    }

    private boolean validateMembersCountOnMission(CrewMember crewMember, FlightMission flightMission) throws CrewMemberNotAbleAssignedException {
        if (flightMission.getAssignedSpaceShip() == null) {
            throw new CrewMemberNotAbleCreateException(crewMember);
        }
        Map<Role, Short> crew = flightMission.getAssignedSpaceShip().getCrew();
        Role role = crewMember.getRole();
        if (!crew.containsKey(role)) {
            return false;
        }
        int count = 0;
        for (CrewMember member : flightMission.getAssignedCrew()) {
            if (member.getRole().equals(role)) {
                count++;
            }
        }
        return count < crew.get(role);
    }
}
