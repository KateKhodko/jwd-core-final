package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.EntityFactory;

// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {

    @Override
    public CrewMember create(Object... args) {
        if (args.length == 4 &&
                args[1] instanceof String &&
                args[2] instanceof Role &&
                args[3] instanceof Rank) {
            Long id = args[0] instanceof Long ? (Long) args[0] : null;
            return new CrewMember(id, (String) args[1], (Role) args[2], (Rank) args[3]);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public CrewMember assignId(Long id, CrewMember item) {
        return new CrewMember(id, item.getName(), item.getRole(), item.getRank());
    }
}
