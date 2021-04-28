package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String startMessage = "Program started";
        LOGGER.trace(startMessage);
        try {
            Application.start();
        } catch (InvalidStateException e) {
            LOGGER.error(e.getMessage());
        }
        String endMessage = "Program finished";
        LOGGER.trace(endMessage);
    }
}