package dao.base;

import entities.base.Entity;

public abstract class SingleKeyEntityDao<E extends Entity, K> extends EntityDao<E> {
    protected abstract String getByKeyQuery();
    protected abstract String deleteByKeyQuery();
}
