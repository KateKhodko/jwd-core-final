package com.epam.jwd.core_final.repository;

import com.epam.jwd.core_final.exception.InvalidStateException;

@FunctionalInterface
interface Parser {
    void parse(String str, int lineIndex) throws InvalidStateException;
}