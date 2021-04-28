package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;

/**
 * Should be a builder for {@link BaseEntity} fields
 */
public abstract class Criteria<T extends BaseEntity> {

    protected Long id;
    protected String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isId(T entity) {
        return id == null || id.equals(entity.getId());
    }

    public boolean isName(T entity) {
        return name == null || name.equals(entity.getName());
    }

    public abstract static class BaseBuilder<T extends BaseEntity, B extends BaseBuilder<T, B>> {

        protected Criteria<T> criteria;

        protected BaseBuilder(Criteria<T> criteria) {
            this.criteria = criteria;
        }

        public B withId(long id) {
            criteria.id = id;
            return getThis();
        }

        public B withName(String name) {
            criteria.name = name;
            return getThis();
        }

        protected abstract B getThis();
    }

}
