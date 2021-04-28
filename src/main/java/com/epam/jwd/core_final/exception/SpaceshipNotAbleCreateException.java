package com.epam.jwd.core_final.exception;

import com.epam.jwd.core_final.domain.Spaceship;

public class SpaceshipNotAbleCreateException extends RuntimeException {

    private final Spaceship spaceship;

    public SpaceshipNotAbleCreateException(Spaceship spaceship) {
        super();
        this.spaceship = spaceship;
    }

    @Override
    public String getMessage() {
        return "Spaceship : id = " + spaceship.getId()
                + " name = " + spaceship.getName()
                + " is not able to be created";
    }
}
