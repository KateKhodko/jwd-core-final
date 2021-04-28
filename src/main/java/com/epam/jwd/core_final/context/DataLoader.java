package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    private static DataLoader instance;

    private final String RESOURCES_DIR_PATH = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    private final String SPACESHIP_FIELDS_SEPARATOR = ";";
    private final String SPACESHIP_CREW_SEPARATOR = ",";
    private final String SPACESHIP_CREW_ENTRY_SEPARATOR = ":";
    private final String SPACESHIP_CREW_MAP_BOUNDS = "[{}]";
    private final String CREW_MEMBER_OBJECT_SEPARATOR = ";";
    private final String CREW_MEMBER_FIELDS_SEPARATOR = ",";
    private final String SPACE_MAP_SEPARATOR = ",";
    private final String DESCRIPTION_START_SYMBOL = "#";

    private final ApplicationProperties applicationProperties = new ApplicationProperties();

    private DataLoader() {

    }

    public static DataLoader getInstance() {
        if (instance == null) {
            instance = new DataLoader();
        }
        return instance;
    }

    public List<Spaceship> readSpaceships() throws InvalidStateException {
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

    public List<CrewMember> readCrew() throws InvalidStateException {
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

    public List<Planet> readPlanets() throws InvalidStateException {
        List<Planet> planets = new ArrayList<>();
        String fileName = applicationProperties.getSpacemapFileName();
        String inputDir = applicationProperties.getInputRootDir();

        read(fileName, inputDir, (line, y) -> {
            String[] planetStr = line.split(SPACE_MAP_SEPARATOR);
            for (int x = 0; x < planetStr.length; x++) {
                String str = planetStr[x].trim();
                if (!str.equals("null")) {
                    Point coords = new Point(x, y);
                    Planet planet = new PlanetFactory().create(null, planetStr[x], coords);
                    planets.add(planet);
                }
            }
        });
        return planets;
    }

    private void read(String fileName, String inputDir, Parser parser) throws InvalidStateException {
        try (InputStream inputStream = getFileFromResourceAsStream(inputDir + File.separator + fileName);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.startsWith(DESCRIPTION_START_SYMBOL)) {
                    parser.parse(line, i);
                    i++;
                }
            }
        } catch (IOException e) {
            throw new InvalidStateException("Error of file reading");
        }
    }

    private InputStream getFileFromResourceAsStream(String path) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + path);
        } else {
            return inputStream;
        }
    }


    public void writeMissions() {
        List<FlightMission> flightMissions = MissionServiceImpl.getInstance().findAllMissions();
        try {
            ApplicationProperties applicationProperties = new ApplicationProperties();
            String dirPath = RESOURCES_DIR_PATH + applicationProperties.getOutputRootDir() + File.separator;
            Path directory = Paths.get(dirPath);
            if (!Files.isDirectory(directory)) {
                Files.createDirectory(directory);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String formatter = new ApplicationProperties().getDateTimeFormat();
            SimpleDateFormat sdf = new SimpleDateFormat(formatter);
            objectMapper.setDateFormat(sdf);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(dirPath + applicationProperties.getMissionsFileName()), flightMissions);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
