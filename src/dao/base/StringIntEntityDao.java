package dao.base;

import dao.ParameterSetter;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;

public abstract class StringIntEntityDao<E>  extends DoubleKeyEntityDao<E, String, Integer>{
    @SneakyThrows
    public final E getByKey(String key, int key2) {
        String query = getByKeyQuery();

        return getOne(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, key);
                statement.setInt(2, key2);
            }
        });
    }

    @SneakyThrows
    public final boolean deleteByKey(String key, int key2) {
        String query = deleteByKeyQuery();

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, key);
                statement.setInt(2, key2);
            }
        });
    }
}
