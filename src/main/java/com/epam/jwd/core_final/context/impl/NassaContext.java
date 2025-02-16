package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.impl.EntityFactoryResolver;
import com.epam.jwd.core_final.repository.LoaderFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

// todo
public class NassaContext implements ApplicationContext {

    // no getters/setters for them
    private final Collection<CrewMember> crewMembers = new ArrayList<>();
    private final Collection<Spaceship> spaceships = new ArrayList<>();
    private final Collection<Planet> planetMap = new ArrayList<>();
    private final Collection<FlightMission> flightMissions = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        if (tClass.equals(CrewMember.class)) {
            return (Collection<T>) crewMembers;
        }
        if (tClass.equals(Spaceship.class)) {
            return (Collection<T>) spaceships;
        }
        if (tClass.equals(Planet.class)) {
            return (Collection<T>) planetMap;
        }
        if (tClass.equals(FlightMission.class)) {
            return (Collection<T>) flightMissions;
        }
        return null;
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        load();
    }

    private void load() throws InvalidStateException {
        loadData(CrewMember.class);
        loadData(Spaceship.class);
        loadData(Planet.class);
    }

    @SuppressWarnings("unchecked")
    private <T extends BaseEntity> void loadData(Class<T> tClass) throws InvalidStateException {
        LoaderFactory loaderFactory = new LoaderFactory();
        loaderFactory.getLoader(tClass).readData();
        loadCollection(loaderFactory.getLoader(tClass).readData(), tClass);
    }

    private <T extends BaseEntity> void loadCollection(List<T> entities, Class<T> tClass) {
        Collection<T> items = retrieveBaseEntityList(tClass);
        if (items == null) return;
        items.clear();
        @SuppressWarnings("unchecked")
        EntityFactory<T> entityFactory = EntityFactoryResolver.instance.getFactory(tClass);
        if (entityFactory == null) {
            return;
        }
        for (T entity : entities) {
            T newT = entityFactory.assignId(generateId(items), entity);
            items.add(newT);
        }
    }

    public static <T extends BaseEntity> Long generateId(Collection<T> collection) {
        List<T> list = (List<T>) collection;
        if (list.size() == 0) {
            return 1L;
        }
        BaseEntity baseEntity = list.stream().max(Comparator.comparing(BaseEntity::getId)).get();
        return baseEntity.getId() + 1;
    }
}
