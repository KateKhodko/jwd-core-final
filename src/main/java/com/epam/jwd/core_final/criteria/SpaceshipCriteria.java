package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {

    private Map<Role, Short> crew;
    private Long flightDistance;
    private Boolean isReadyForNextMissions;

    public boolean isCrew(Spaceship spaceship) {
        return crew == null || crew.equals(spaceship.getCrew());
    }

    public boolean isFlightDistance(Spaceship spaceship) {
        return flightDistance == null || flightDistance.equals(spaceship.getFlightDistance());
    }

    public boolean readyForNextMissions(Spaceship spaceship) {
        return isReadyForNextMissions == null || isReadyForNextMissions.equals(spaceship.getReadyForNextMissions());
    }

    public static class SpaceshipBuilder extends BaseBuilder<Spaceship, SpaceshipBuilder> {

        protected final SpaceshipCriteria spaceshipCriteria;

        public SpaceshipBuilder() {
            super(new SpaceshipCriteria());
            spaceshipCriteria = (SpaceshipCriteria) criteria;
        }

        public SpaceshipBuilder withCrew(Map<Role, Short> crew) {
            spaceshipCriteria.crew = crew;
            return this;
        }

        public SpaceshipBuilder withIsReadyForNextMissions(long flightDistance) {
            spaceshipCriteria.flightDistance = flightDistance;
            return this;
        }

        public SpaceshipBuilder withFlightDistance(boolean isReadyForNextMissions) {
            spaceshipCriteria.isReadyForNextMissions = isReadyForNextMissions;
            return this;
        }

        public SpaceshipCriteria build() {
            return spaceshipCriteria;
        }

        @Override
        protected SpaceshipBuilder getThis() {
            return this;
        }
    }
}
