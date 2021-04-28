package com.epam.jwd.core_final.exception;

import com.epam.jwd.core_final.domain.CrewMember;

public class CrewMemberNotAbleCreateException extends RuntimeException {
    private final CrewMember crewMember;

    public CrewMemberNotAbleCreateException(CrewMember crewMember) {
        super();
        this.crewMember = crewMember;
    }

    @Override
    public String getMessage() {
        return "crewMember : id = " + crewMember.getId()
                + " name = " + crewMember.getName()
                + " is not able to be created";
    }
}
