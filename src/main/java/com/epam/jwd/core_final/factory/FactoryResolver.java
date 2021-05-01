package com.epam.jwd.core_final.factory;

import com.epam.jwd.core_final.domain.BaseEntity;

public interface FactoryResolver {

    @SuppressWarnings("rawtypes")
    <T extends BaseEntity> EntityFactory getFactory(Class<T> tClass);
}
