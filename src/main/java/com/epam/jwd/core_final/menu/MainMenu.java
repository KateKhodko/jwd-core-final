package com.epam.jwd.core_final.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class MainMenu {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainMenu.class);

    static final String menu = "1 - Get info about CrewMembers\n"
            + "2 - Update CrewMembers\n"
            + "3 - Get info about Spaceships\n"
            + "4 - Update Spaceships\n"
            + "5 - Create mission\n"
            + "6 - Update mission\n"
            + "7 - Write mission\n"
            + "8 - Get info about missions\n"
            + "9 - Get info about planets\n"
            + "10 - Exit\n\n"
            + "Input menu item number:\n";
    private static final String EXIT_FROM_SUBMENU = "e";
    private static final int GET_CREW_MEMBERS = 1;
    private static final int UPDATE_CREW_MEMBERS = 2;
    private static final int GET_SPACESHIPS = 3;
    private static final int UPDATE_SPACESHIPS = 4;
    private static final int CREATE_MISSION = 5;
    private static final int UPDATE_MISSION = 6;
    private static final int WRITE_MISSIONS = 7;
    private static final int GET_INFO_ABOUT_MISSIONS = 8;
    private static final int GET_INFO_ABOUT_PLANETS = 9;
    private static final int EXIT = 10;

    private final CrewMembersMenu crewMembersMenu;
    private final SpaceShipMenu spaceShipMenu;
    private final MissionMenu missionMenu;
    private final PlanetMenu planetMenu;

    public MainMenu() {
        crewMembersMenu = new CrewMembersMenu(this);
        spaceShipMenu = new SpaceShipMenu(this);
        missionMenu = new MissionMenu(this);
        planetMenu = new PlanetMenu(this);
    }

    public void start() {
        printMenu();
        handleInput();
    }

    static void printMenu() {
        System.out.println(menu);
    }

    private void handleInput() {
        String wrongMenuInput = "Wrong menu input";
        do {
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextInt()) {
                int n = scan.nextInt();
                switch (n) {
                    case GET_CREW_MEMBERS:
                        crewMembersMenu.printCrewMembers();
                        break;
                    case UPDATE_CREW_MEMBERS:
                        crewMembersMenu.updateCrewMembers();
                        break;
                    case GET_SPACESHIPS:
                        spaceShipMenu.printSpaceships();
                        break;
                    case UPDATE_SPACESHIPS:
                        spaceShipMenu.updateSpaceships();
                        break;
                    case CREATE_MISSION:
                        missionMenu.createMission();
                        break;
                    case UPDATE_MISSION:
                        missionMenu.updateMission();
                        break;
                    case WRITE_MISSIONS:
                        missionMenu.writeMissions();
                        break;
                    case GET_INFO_ABOUT_MISSIONS:
                        missionMenu.printMissions();
                        break;
                    case GET_INFO_ABOUT_PLANETS:
                        planetMenu.printPlanets();
                        break;
                    case EXIT:
                        return;
                    default:
                        System.out.println(wrongMenuInput);
                        LOGGER.error(wrongMenuInput);
                }
            } else {
                System.out.println(wrongMenuInput);
                LOGGER.error(wrongMenuInput);
            }
        } while (true);
    }

    boolean exitToMenu(Scanner scan) {
        if (!scan.hasNextInt()) {
            String inputStr = scan.nextLine();
            inputStr = inputStr.trim();
            if (inputStr.equals(EXIT_FROM_SUBMENU)) {
                MainMenu.printMenu();
                return true;
            }
        }
        return false;
    }

}
