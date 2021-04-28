package com.epam.jwd.core_final.exception;

import com.epam.jwd.core_final.domain.Spaceship;

public class SpaceshipNotAbleAssignedException extends RuntimeException {

    private final Spaceship spaceship;

    public SpaceshipNotAbleAssignedException(Spaceship spaceship) {
        super();
        this.spaceship = spaceship;
    }

    @Override
    public String getMessage() {
        return spaceship == null ? "Not able to assign spaceship"
                : "Spaceship : id = " + spaceship.getId()
                + " name = " + spaceship.getName()
                + " is not able to be assigned";
    }
}
