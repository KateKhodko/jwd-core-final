package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.menu.MainMenu;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext();

    default void printAvailableOptions() {
        //MainMenu.instance.printMenu();
    }

    default void handleUserInput() {

    }
}
