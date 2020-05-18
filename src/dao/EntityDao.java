package dao;

import entities.Album;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class EntityDao<E> {
    @SneakyThrows
    protected final Connection getConnection() {
        String connString =
                "jdbc:sqlserver://192.168.1.5;database=Chinook;user=sa;password=3512";
        return DriverManager.getConnection(connString);
    }
    //region abstract methods
    protected abstract E readEntity(ResultSet result);

    protected abstract String getCountQuery();
    //endregion


    @SneakyThrows
    protected final E getOne(String query, ParameterSetter parameterSetter){
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(query);

        if (parameterSetter != null)
            parameterSetter.setValue(statement);

        ResultSet result = statement.executeQuery();

        ArrayList<E> entities = new ArrayList<>();
        while (result.next()){
            E entity = readEntity(result);
            entities.add(entity);
        }

        result.close();
        statement.getConnection().close();
        statement.close();

        return entities.size() == 0 ? null : entities.get(0);
    }

    @SneakyThrows
    protected final ArrayList<E> getMany(String query, ParameterSetter parameterSetter) {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(query);
        if (parameterSetter != null)
            parameterSetter.setValue(statement);

        ResultSet result = statement.executeQuery();

        ArrayList<E> entities = new ArrayList<>();
        while (result.next()){
            E entity = readEntity(result);
            entities.add(entity);
        }

        result.close();
        statement.getConnection().close();
        statement.close();

        return entities;
    }

    @SneakyThrows
    protected final int getInt(String query, ParameterSetter parameterSetter){
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(query);

        if (parameterSetter != null)
            parameterSetter.setValue(statement);

        ResultSet result = statement.executeQuery();

        int count = 0;
        while (result.next()){
            count = result.getInt(1);
        }

        result.close();
        statement.getConnection().close();
        statement.close();

        return count;
    }

    @SneakyThrows
    public final int getCount(){
        //language=TSQL
        String query = getCountQuery();

        return getInt(query, null);
    }

    @SneakyThrows
    protected final boolean execute(String query, ParameterSetter parameterSetter){
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(query);
        if (parameterSetter != null)
            parameterSetter.setValue(statement);

        int rowCount = statement.executeUpdate();

        statement.getConnection().close();
        statement.close();

        return rowCount == 1;
    }


}
