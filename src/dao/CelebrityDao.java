package dao;

import dao.base.StringEntityDao;
import entities.Celebrity;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CelebrityDao extends StringEntityDao<Celebrity> {
    //region singleton
    private CelebrityDao() {
    }

    private static CelebrityDao _instance;

    public static CelebrityDao getInstance() {
        if (_instance == null)
            _instance = new CelebrityDao();

        return _instance;
    }
    //endregion

    @Override
    protected String getCountQuery() {
        //language=TSQL
        return "select count(*) from Celebrity";
    }

    @Override
    protected String getAllQuery() {
        //language=TSQL
        return "select * from Celebrity";
    }

    @Override
    protected String getByKeyQuery() {
        //language=TSQL
        return "select * from Celebrity where Name = ?";
    }

    @Override
    protected String deleteByKeyQuery() {
        //language=TSQL
        return "delete Celebrity where Name = ?";
    }

    @SneakyThrows
    @Override
    protected Celebrity readEntity(ResultSet result) {
        Celebrity celebrity = new Celebrity();

        celebrity.setName(result.getString(1));
        celebrity.setScore(result.getBigDecimal(2));

        return celebrity;
    }

    @Override
    public boolean insert(Celebrity celebrity) {
        //language=TSQL
        String query = "insert into Celebrity values (?, ?)";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, celebrity.getName());
                statement.setBigDecimal(2, celebrity.getScore());
            }
        });
    }

    @Override
    public boolean update(Celebrity celebrity) {
        //language=TSQL
        String query = "update Celebrity set Score = ? where Name = ?";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setBigDecimal(1, celebrity.getScore());
                statement.setString(2, celebrity.getName());
            }
        });
    }
}
