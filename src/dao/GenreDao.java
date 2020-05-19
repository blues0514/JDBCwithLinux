package dao;

import dao.base.IntEntityDao;
import entities.Genre;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GenreDao extends IntEntityDao<Genre> {
    //region singleton
    private GenreDao() {
    }

    private static GenreDao _instance;

    public static GenreDao getInstance() {
        if (_instance == null)
            _instance = new GenreDao();

        return _instance;
    }
    //endregion

    @SneakyThrows
    @Override
    protected Genre readEntity(ResultSet result) {
        Genre genre = new Genre();

        genre.setGenreId(result.getInt(1));
        genre.setName(result.getString(2));

        return genre;
    }

    @Override
    protected String getByKeyQuery() {
        //language=TSQL
        return "select * from Genre where GenreId = ?";
    }

    @Override
    protected String deleteByKeyQuery() {
        //language=TSQL
        return "delete Genre where GenreId = ?";
    }

    @Override
    protected String getCountQuery() {
        //language=TSQL
        return "select count(*) from Genre";
    }

    @Override
    protected String getAllQuery() {
        //language=TSQL
        return "select * from Genre";
    }

    @Override
    public boolean insert(Genre entity) {
        //language=TSQL
        String query = "insert into Genre values (?)";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, entity.getName());
            }
        });
    }

    @Override
    public boolean update(Genre entity) {
        //language=TSQL
        String query = "update Genre set Name = ? where GenreId = ?";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, entity.getName());
                statement.setInt(2, entity.getGenreId());
            }
        });
    }

}
