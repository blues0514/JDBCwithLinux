package dao.base;

import entities.base.Entity;

public abstract class DoubleKeyEntityDao<E extends Entity, K1, K2> extends EntityDao<E> {
    protected abstract String getByKeyQuery();

    protected abstract String deleteByKeyQuery();
}