package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceshipsLoader extends BaseLoader<Spaceship> {

    private final String SPACESHIP_FIELDS_SEPARATOR = ";";
    private final String SPACESHIP_CREW_SEPARATOR = ",";
    private final String SPACESHIP_CREW_ENTRY_SEPARATOR = ":";
    private final String SPACESHIP_CREW_MAP_BOUNDS = "[{}]";

    @Override
    public List<Spaceship> readData() throws InvalidStateException {
        List<Spaceship> spaceships = new ArrayList<>();
        String fileName = applicationProperties.getSpaceshipsFileName();
        String inputDir = applicationProperties.getInputRootDir();

        read(fileName, inputDir, (line, i) -> {
            try {
                String[] args = line.split(SPACESHIP_FIELDS_SEPARATOR);
                if (args.length != 3) {
                    throw new InvalidStateException("Invalid spaceship arg");
                }
                Long distance = Long.parseLong(args[1]);
                String[] mapEntries = args[2].replaceAll(SPACESHIP_CREW_MAP_BOUNDS, "").split(SPACESHIP_CREW_SEPARATOR);
                Map<Role, Short> crew = new HashMap<>();
                for (String entry : mapEntries) {
                    String[] roles = entry.split(SPACESHIP_CREW_ENTRY_SEPARATOR);
                    if (roles.length != 2) {
                        throw new InvalidStateException("Invalid spaceship arg");
                    }
                    Role role = Role.resolveRoleById(Integer.parseInt(roles[0]));
                    Short count = Short.parseShort(roles[1]);
                    if (crew.containsKey(role)) {
                        throw new InvalidStateException("Invalid spaceship arg");
                    }
                    crew.put(role, count);
                }
                Spaceship spaceship = new SpaceshipFactory().create(null, args[0], crew, distance);
                spaceships.add(spaceship);
            } catch (NumberFormatException e) {
                throw new InvalidStateException("Error of arguments parsing");
            }
        });
        return spaceships;
    }

    @Override
    public void writeData() throws IOException {
        throw new AssertionError("Method not implements");
    }
}
