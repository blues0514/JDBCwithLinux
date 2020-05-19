package dao;

import dao.base.IntEntityDao;
import entities.Track;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TrackDao extends IntEntityDao<Track> {
    //region singleton
    private TrackDao() {
    }

    private static TrackDao _instance;

    public static TrackDao getInstance() {
        if (_instance == null)
            _instance = new TrackDao();

        return _instance;
    }
    //endregion

    @SneakyThrows
    @Override
    protected Track readEntity(ResultSet result) {
        Track track = new Track();

        track.setTrackId(result.getInt(1));
        track.setName(result.getString(2));
        track.setAlbumId(result.getInt(3));
        track.setGenreId(result.getInt(4));
        track.setUnitPrice(result.getDouble(5));

        return track;
    }

    @Override
    protected String getCountQuery() {
        //language=TSQL
        return "select count(*) from Track";
    }

    @Override
    protected String getAllQuery() {
        //language=TSQL
        return "select * from Track";
    }

    @Override
    protected String getByKeyQuery() {
        //language=TSQL
        return "select * from Track where TrackId = ?";
    }

    @Override
    protected String deleteByKeyQuery() {
        //language=TSQL
        return "delete Track where TrackId = ?";
    }

    @SneakyThrows
    public ArrayList<Track> getByAlbumId(int albumId) {
        //language=TSQL
        String query = "select * from Track where AlbumId = ?";
        return getMany(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, albumId);
            }
        });
    }

    @SneakyThrows
    public ArrayList<Track> getByGenreId(int genreId) {
        //language=TSQL
        String query = "select * from Track where GenreId = ?";

        return getMany(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, genreId);
            }
        });
    }

    @SneakyThrows
    public int getMaxTrackId() {
        //language=TSQL
        String query = "select top 1 TrackId from Track order by TrackId desc ";

        return getInt(query, null);
    }

    @SneakyThrows
    public boolean insert(Track track){
        //language=TSQL
        String query = "insert into Track values (?, ?, ?, ?)";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, track.getName());
                statement.setInt(2, track.getAlbumId());
                statement.setInt(3, track.getGenreId());
                statement.setDouble(4, track.getUnitPrice());
            }
        });
    }

    @SneakyThrows
    public boolean update(Track track){
        //language=TSQL
        String query = "update Track set Name = ?, AlbumId = ?, GenreId = ?, UnitPrice = ? where TrackId = ?";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, track.getName());
                statement.setInt(2, track.getAlbumId());
                statement.setInt(3, track.getGenreId());
                statement.setDouble(4, track.getUnitPrice());
                statement.setInt(5,track.getTrackId());
            }
        });
    }

}
