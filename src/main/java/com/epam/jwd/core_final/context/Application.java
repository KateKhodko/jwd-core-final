package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.menu.MainMenu;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceShipServiceImpl;
import com.epam.jwd.core_final.service.impl.SpacemapServiceImpl;

public interface Application {

    static void start() throws InvalidStateException {
        final NassaContext nassaContext = new NassaContext();
        nassaContext.init();

        CrewServiceImpl.init(nassaContext);
        MissionServiceImpl.init(nassaContext);
        SpacemapServiceImpl.init(nassaContext);
        SpaceShipServiceImpl.init(nassaContext);

        final MainMenu mainMenu = new MainMenu();
        mainMenu.start();
    }
}
