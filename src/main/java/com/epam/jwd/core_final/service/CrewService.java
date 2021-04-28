package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.CrewMemberNotAbleAssignedException;
import com.epam.jwd.core_final.exception.CrewMemberNotAbleCreateException;
import com.epam.jwd.core_final.exception.CrewMemberUpdateException;

import java.util.List;
import java.util.Optional;

/**
 * All its implementations should be a singleton
 * You have to use streamAPI for filtering, mapping, collecting, iterating
 */
public interface CrewService {

    List<CrewMember> findAllCrewMembers();

    List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria);

    Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria);

    CrewMember updateCrewMemberDetails(CrewMember crewMember) throws CrewMemberUpdateException;

    // todo create custom exception for case, when crewMember is not able to be assigned
    void assignCrewMemberOnMission(CrewMember crewMember, FlightMission flightMission) throws CrewMemberNotAbleAssignedException;

    // todo create custom exception for case, when crewMember is not able to be created (for example - duplicate.
    // crewmember unique criteria - only name!
    CrewMember createCrewMember(CrewMember crewMember) throws CrewMemberNotAbleCreateException;
}