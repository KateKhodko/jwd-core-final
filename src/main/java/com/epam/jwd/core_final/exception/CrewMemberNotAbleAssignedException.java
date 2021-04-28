package com.epam.jwd.core_final.exception;

import com.epam.jwd.core_final.domain.CrewMember;

public class CrewMemberNotAbleAssignedException extends RuntimeException {

    private final CrewMember crewMember;

    public CrewMemberNotAbleAssignedException(CrewMember crewMember) {
        super();
        this.crewMember = crewMember;
    }

    @Override
    public String getMessage() {
        return crewMember == null ? "Not able to assign crew member"
                : "crewMember : id = " + crewMember.getId()
                + " name = " + crewMember.getName()
                + " is not able to be assigned";
    }
}
