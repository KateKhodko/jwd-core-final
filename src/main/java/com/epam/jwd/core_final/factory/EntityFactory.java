package com.epam.jwd.core_final.factory;

import com.epam.jwd.core_final.domain.BaseEntity;

public interface EntityFactory<T extends BaseEntity> {
    //todo: handle IllegalArgumentException??
    T create(Object... args);

    T assignId(Long id, T item);

}
