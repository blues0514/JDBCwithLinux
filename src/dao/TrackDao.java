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
                "jdbc:sqlserver://192.168.1.33;database=Chinook;user=sa;password=3512";
        return DriverManager.getConnection(connString);
    }
    //endregion


    @SneakyThrows
    public int getCount(){
        Connection connection = getConnection();

        //language=TSQL
        String query = "select count(*) from Track";
        PreparedStatement statement = connection.prepareStatement(query);

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
    public Track getByKey(int key){
        Connection connection = getConnection();

        //language=TSQL
        String query = "select * from Track where TrackId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, key);

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
    public ArrayList<Track> getByAlbumId(int albumId) {
        Connection connection = getConnection();

        //language=TSQL
        String query = "select * from Track where AlbumId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, albumId);

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
    public ArrayList<Track> getByGenreId(int genreId) {
        Connection connection = getConnection();

        //language=TSQL
        String query = "select * from Track where GenreId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, genreId);

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
    public ArrayList<Track> getAll() {
        Connection connection = getConnection();

        //language=TSQL
        String query = "select * from Track";
        PreparedStatement statement = connection.prepareStatement(query);

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
    public Integer getMaxTrackId() {
        Connection connection = getConnection();

        //language=TSQL
        String query = "select top 1 TrackId from Track order by TrackId desc ";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();

        Integer value = null;
        if (result.next())
            value = result.getInt(1);

        result.close();
        statement.getConnection().close();
        statement.close();

        return value;
    }

    @SneakyThrows
    public boolean insert(Track track){
        Connection connection = getConnection();

        //language=TSQL
        String query = "insert into Track values (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, track.getName());
        statement.setInt(2, track.getAlbumId());
        statement.setInt(3, track.getGenreId());
        statement.setDouble(4, track.getUnitPrice());


        int rowCount = statement.executeUpdate();

        statement.getConnection().close();
        statement.close();

        return rowCount == 1;
    }

    @SneakyThrows
    public boolean update(Track track){
        Connection connection = getConnection();

        //language=TSQL
        String query = "update Track set Name = ?, AlbumId = ?, GenreId = ?, UnitPrice = ? where TrackId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, track.getName());
        statement.setInt(2, track.getAlbumId());
        statement.setInt(3, track.getGenreId());
        statement.setDouble(4, track.getUnitPrice());
        statement.setInt(5,track.getTrackId());

        int rowCount = statement.executeUpdate();

        statement.getConnection().close();
        statement.close();

        return rowCount == 1;
    }

    @SneakyThrows
    public boolean deleteByKey(int key){
        Connection connection = getConnection();

        //language=TSQL
        String query = "delete Track where TrackId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, key);

        int rowCount = statement.executeUpdate();

        statement.getConnection().close();
        statement.close();

        return rowCount == 1;
    }

}
