package dao;

import lombok.SneakyThrows;

import java.sql.PreparedStatement;

public interface ParameterSetter {
    @SneakyThrows
    void setValue(PreparedStatement statement);
}
