package dao.base;

import dao.ParameterSetter;
import entities.base.Entity;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;

public abstract class StringStringEntityDao<E extends Entity> extends DoubleKeyEntityDao<E, String , String> {
    @SneakyThrows
    public final E getByKey(String key, String key2) {
        String query = getByKeyQuery();

        return getOne(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, key);
                statement.setString(2, key2);
            }
        });
    }

    @SneakyThrows
    public final boolean deleteByKey(String key, String key2) {
        String query = deleteByKeyQuery();

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, key);
                statement.setString(2, key2);
            }
        });
    }

}
