package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.exception.InvalidStateException;

public interface Parser {
    void parse(String str, int lineIndex) throws InvalidStateException;
}