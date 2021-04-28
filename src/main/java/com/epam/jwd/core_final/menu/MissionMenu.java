package com.epam.jwd.core_final.menu;

import com.epam.jwd.core_final.context.DataLoader;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.exception.CrewMemberNotAbleAssignedException;
import com.epam.jwd.core_final.exception.MissionNotAbleCreateException;
import com.epam.jwd.core_final.exception.MissionUpdateException;
import com.epam.jwd.core_final.exception.SpaceshipNotAbleAssignedException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceShipServiceImpl;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static com.epam.jwd.core_final.domain.MissionResult.IN_PROGRESS;

public class MissionMenu {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainMenu.class);

    private final MainMenu mainMenu;

    public MissionMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    void printMissions() {
        List<FlightMission> missions = MissionServiceImpl.getInstance().findAllMissions();
        missions.forEach(System.out::println);
    }

    void createMission() {
        System.out.println("Input mission name:");
        String name;
        do {
            Scanner scan = new Scanner(System.in);
            name = scan.nextLine();
            if (name.trim().length() == 0) {
                System.out.println("Name can not be empty string");
            } else {
                break;
            }
        } while (true);
        LocalDateTime startDate = LocalDateTime.now();
        Planet from = SpacemapServiceImpl.getInstance().getRandomPlanet();
        Planet to;
        do {
            to = SpacemapServiceImpl.getInstance().getRandomPlanet();
        } while (from.equals(to));

        FlightMission mission = new FlightMissionFactory().create(null, name, startDate, from, to, IN_PROGRESS);
        try {
            FlightMission newMission = MissionServiceImpl.getInstance().createMission(mission);
            SpaceShipServiceImpl.getInstance().assignRandomShipOnMission(newMission);
            CrewServiceImpl.getInstance().assignRandomMembersOnMission(newMission);
            System.out.println("Mission has created: " + newMission);
        } catch (SpaceshipNotAbleAssignedException | MissionNotAbleCreateException | CrewMemberNotAbleAssignedException e) {
            LOGGER.error(e.getMessage());
        }
    }

    void updateMission() {
        FlightMission flightMission = findMissionById();
        if (flightMission != null) {
            System.out.println(flightMission);
            MissionResult missionResult = getMissionResult();

            FlightMission newFlightMission = new FlightMissionFactory().create(
                    flightMission.getId(),
                    flightMission.getName(),
                    flightMission.getStartDate(),
                    flightMission.getFrom(),
                    flightMission.getTo(),
                    missionResult);
            newFlightMission.setAssignedCrew(flightMission.getAssignedCrew());
            newFlightMission.setAssignedSpaceShip(flightMission.getAssignedSpaceShip());
            try {
                MissionServiceImpl.getInstance().updateMissionDetails(newFlightMission);
                String updateMissionInfo = "Mission updated : \n" + newFlightMission;
                LOGGER.info(updateMissionInfo);
                System.out.println(updateMissionInfo);
            } catch (MissionUpdateException e) {
                String updateMissionError = "Error of mission update : " + e.getMessage();
                LOGGER.error(updateMissionError);
                System.out.println(updateMissionError);
            }
        }
    }

    private MissionResult getMissionResult() {
        System.out.println("\nResults");
        for (MissionResult result : MissionResult.values()) {
            System.out.println(result.getId() + ". " + result.getName());
        }
        do {
            System.out.println("Input id of a result : ");
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextInt()) {
                int i = scan.nextInt();
                try {
                    return MissionResult.resolveMissionResultById(i);
                } catch (UnknownEntityException e) {
                    String resultNotFound = "No mission result with such id";
                    LOGGER.error(resultNotFound);
                    System.out.println(resultNotFound);
                }
            }
        } while (true);
    }

    void writeMissions() {
        DataLoader.getInstance().writeMissions();
        String fileWriteInfo = "File has written successfully";
        LOGGER.info(fileWriteInfo);
        System.out.println(fileWriteInfo);
    }

    private void printSubmenu() {
        String submenu = "Input (e - for exit or mission id): ";
        System.out.println(submenu);
    }

    private FlightMission findMissionById() {
        printSubmenu();
        do {
            Scanner scan = new Scanner(System.in);
            if (mainMenu.exitToMenu(scan)) {
                return null;
            }
            if (scan.hasNextInt()) {
                int i = scan.nextInt();
                FlightMissionCriteria criteria = new FlightMissionCriteria.MissionBuilder().withId(i).build();
                Optional<FlightMission> optionalFlightMission = MissionServiceImpl.getInstance().findMissionByCriteria(criteria);
                if (optionalFlightMission.isPresent()) {
                    return optionalFlightMission.get();
                }
            }
            String noMissionFound = "No mission with such id";
            LOGGER.error(noMissionFound);
            System.out.println(noMissionFound);
        } while (true);
    }
}
