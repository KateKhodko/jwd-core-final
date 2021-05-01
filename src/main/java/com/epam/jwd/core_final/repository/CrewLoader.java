package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrewLoader extends BaseLoader<CrewMember> {

    private final String CREW_MEMBER_OBJECT_SEPARATOR = ";";
    private final String CREW_MEMBER_FIELDS_SEPARATOR = ",";

    @Override
    public List<CrewMember> readData() throws InvalidStateException {
        List<CrewMember> crewMembers = new ArrayList<>();
        String fileName = applicationProperties.getCrewFileName();
        String inputDir = applicationProperties.getInputRootDir();

        read(fileName, inputDir, (line, i) -> {
            try {
                String[] crewMembersStr = line.split(CREW_MEMBER_OBJECT_SEPARATOR);
                for (String crewMemberStr : crewMembersStr) {
                    String[] args = crewMemberStr.split(CREW_MEMBER_FIELDS_SEPARATOR);
                    if (args.length != 3) {
                        throw new InvalidStateException("Invalid spaceship arg");
                    }
                    Role role = Role.resolveRoleById(Integer.parseInt(args[0]));
                    Rank rank = Rank.resolveRankById(Integer.parseInt(args[2]));
                    CrewMember crewMember = new CrewMemberFactory().create(null, args[1], role, rank);
                    crewMembers.add(crewMember);
                }
            } catch (NumberFormatException e) {
                throw new InvalidStateException("Error of arguments parsing");
            }
        });
        return crewMembers;
    }

    @Override
    public void writeData() throws IOException {
        throw new AssertionError("Method not implements");
    }
}
