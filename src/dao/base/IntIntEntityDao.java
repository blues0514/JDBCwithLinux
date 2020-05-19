package dao.base;

import dao.ParameterSetter;
import entities.base.Entity;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;

public abstract class IntIntEntityDao<E extends Entity> extends DoubleKeyEntityDao<E, Integer, Integer> {
    @SneakyThrows
    public final E getByKey(int key, int key2) {
        String query = getByKeyQuery();

        return getOne(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, key);
                statement.setInt(2, key2);
            }
        });
    }

    @SneakyThrows
    public final boolean deleteByKey(int key, int key2) {
        String query = deleteByKeyQuery();

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, key);
                statement.setInt(2, key2);
            }
        });
    }
}
