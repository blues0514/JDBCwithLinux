package dao;


import entities.Track;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TrackDao {

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

    //region helper methods
    @SneakyThrows
    private Track readTrack(ResultSet result) {
        Track track = new Track();

        track.setTrackId(result.getInt(1));
        track.setName(result.getString(2));
        track.setAlbumId(result.getInt(3));
        track.setGenreId(result.getInt(4));
        track.setUnitPrice(result.getDouble(5));

        return track;
    }

    @SneakyThrows
    private Connection getConnection() {
        String connString =
                "jdbc:sqlserver://192.168.1.5;database=Chinook;user=sa;password=3512";
        return DriverManager.getConnection(connString);
    }

    @SneakyThrows
    private int getInt(String query, ParameterSetter parameterSetter){
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
    private Track getOne(String query, ParameterSetter parameterSetter){
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(query);

        if (parameterSetter != null)
            parameterSetter.setValue(statement);

        ResultSet result = statement.executeQuery();

        ArrayList<Track> tracks = new ArrayList<>();
        while (result.next()){
            Track track = readTrack(result);
            tracks.add(track);
        }

        result.close();
        statement.getConnection().close();
        statement.close();

        return tracks.size() == 0 ? null : tracks.get(0);
    }

    @SneakyThrows
    private ArrayList<Track> getMany(String query, ParameterSetter parameterSetter) {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(query);
        if (parameterSetter != null)
            parameterSetter.setValue(statement);

        ResultSet result = statement.executeQuery();

        ArrayList<Track> tracks = new ArrayList<>();
        while (result.next()){
            Track track = readTrack(result);
            tracks.add(track);
        }

        result.close();
        statement.getConnection().close();
        statement.close();

        return tracks;
    }

    @SneakyThrows
    private boolean execute(String query, ParameterSetter parameterSetter){
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(query);
        if (parameterSetter != null)
            parameterSetter.setValue(statement);

        int rowCount = statement.executeUpdate();

        statement.getConnection().close();
        statement.close();

        return rowCount == 1;
    }

    //endregion

    @SneakyThrows
    public int getCount() {
        //language=TSQL
        String query = "select count(*) from Track";

        return getInt(query, null);
    }

    @SneakyThrows
    public Track getByKey(int key){
        //language=TSQL
        String query = "select * from Track where TrackId = ?";

        return getOne(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, key);
            }
        });
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
    public ArrayList<Track> getAll() {
        //language=TSQL
        String query = "select * from Track";

        return getMany(query, null);
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

    @SneakyThrows
    public boolean deleteByKey(int key){
        //language=TSQL
        String query = "delete Track where TrackId = ?";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, key);
            }
        });
    }

}
