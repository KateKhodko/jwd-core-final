package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.domain.BaseEntity;

public enum LoaderType {

    CREW_LOADER(new CrewLoader()),
    SPACESHIP_LOADER(new SpaceshipsLoader()),
    MISSIONS_LOADER(new MissionsLoader()),
    PLANETS_LOADER(new PlanetsLoader());

    private final BaseLoader loader;

    <T extends BaseEntity> LoaderType(BaseLoader<T> loader) {
        this.loader = loader;
    }

    public <T extends BaseEntity> BaseLoader<T> getLoader() {
        return loader;
    }
}
