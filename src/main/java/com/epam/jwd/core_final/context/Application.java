package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.menu.MainMenu;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceShipServiceImpl;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;

import java.util.List;

public interface Application {

    static void start() throws InvalidStateException {
        final NassaContext nassaContext = new NassaContext();
        nassaContext.init();

        CrewServiceImpl.getInstance().setCrewMembers((List<CrewMember>) nassaContext.retrieveBaseEntityList(CrewMember.class));
        MissionServiceImpl.getInstance().setMissions((List<FlightMission>) nassaContext.retrieveBaseEntityList(FlightMission.class));
        SpacemapServiceImpl.getInstance().setPlanets((List<Planet>) nassaContext.retrieveBaseEntityList(Planet.class));
        SpaceShipServiceImpl.getInstance().setSpaceships((List<Spaceship>) nassaContext.retrieveBaseEntityList(Spaceship.class));

        final MainMenu mainMenu = new MainMenu();
        mainMenu.start();
    }
}
