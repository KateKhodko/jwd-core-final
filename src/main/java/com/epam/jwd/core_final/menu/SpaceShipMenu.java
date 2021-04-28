package com.epam.jwd.core_final.menu;

import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.SpaceshipUpdateException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.impl.SpaceShipServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SpaceShipMenu {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpaceShipMenu.class);

    private final MainMenu mainMenu;

    public SpaceShipMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    void printSpaceships() {
        List<Spaceship> spaceships = SpaceShipServiceImpl.getInstance().findAllSpaceships();
        spaceships.forEach(System.out::println);
    }

    void updateSpaceships() {
        Spaceship spaceship = findSpaceshipById();
        if (spaceship != null) {
            System.out.println(spaceship);
            Long distance = inputDistance();

            Spaceship newSpaceship = new SpaceshipFactory().create(
                    spaceship.getId(),
                    spaceship.getName(),
                    spaceship.getCrew(),
                    distance);
            try {
                SpaceShipServiceImpl.getInstance().updateSpaceshipDetails(newSpaceship);
                String updateShipInfo = "Spaceship updated : \n" + newSpaceship;
                LOGGER.info(updateShipInfo);
                System.out.println(updateShipInfo);
            } catch (SpaceshipUpdateException e) {
                String updateShipError = "Error of spaceship update : " + e.getMessage();
                LOGGER.error(updateShipError);
                System.out.println(updateShipError);
            }
        }
    }

    private Long inputDistance() {
        do {
            System.out.println("Input a new distance : ");
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextLong()) {
                long i = scan.nextLong();
                if (i > 0) {
                    return i;
                }
            }
            System.out.println("You should enter a positive number");
        } while (true);
    }

    private void printSubmenu() {
        String subMenu = "Input (e - for exit or spaceship id): ";
        System.out.println(subMenu);
    }

    private Spaceship findSpaceshipById() {
        printSubmenu();
        do {
            Scanner scan = new Scanner(System.in);
            if (mainMenu.exitToMenu(scan)) {
                return null;
            }
            if (scan.hasNextInt()) {
                int i = scan.nextInt();
                SpaceshipCriteria criteria = new SpaceshipCriteria.SpaceshipBuilder().withId(i).build();
                Optional<Spaceship> optionalSpaceship = SpaceShipServiceImpl.getInstance().findSpaceshipByCriteria(criteria);
                if (optionalSpaceship.isPresent()) {
                    return optionalSpaceship.get();
                }
            }
            String shipNotFound = "No spaceship with such id";
            LOGGER.error(shipNotFound);
            System.out.println(shipNotFound);
        } while (true);
    }
}
