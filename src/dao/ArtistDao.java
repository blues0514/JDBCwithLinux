package dao;

import entities.Artist;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ArtistDao {

    //region singleton
    private ArtistDao() {
    }

    private static ArtistDao _instance;

    public static ArtistDao getInstance() {
        if (_instance == null)
            _instance = new ArtistDao();

        return _instance;
    }
    //endregion

    //region helper method
    @SneakyThrows
    private Artist readArtist(ResultSet result) {
        Artist artist = new Artist();

        artist.setArtistId(result.getInt(1));
        artist.setName(result.getString(2));

        return artist;
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
        String query = "select count(*) from Artist";
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
    public Artist getByKey(int key){
        Connection connection = getConnection();

        //language=TSQL
        String query = "select * from Artist where ArtistId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, key);

        ResultSet result = statement.executeQuery();

        ArrayList<Artist> artists = new ArrayList<>();
        while (result.next()){
            Artist artist = readArtist(result);
            artists.add(artist);
        }

        result.close();
        statement.getConnection().close();
        statement.close();

        return artists.size() == 0 ? null : artists.get(0);
    }

    @SneakyThrows
    public ArrayList<Artist> getAll() {
        Connection connection = getConnection();

        //language=TSQL
        String query = "select * from Artist";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();

        ArrayList<Artist> artists = new ArrayList<>();
        while (result.next()){
            Artist artist = readArtist(result);
            artists.add(artist);
        }

        result.close();
        statement.getConnection().close();
        statement.close();

        return artists;
    }

    @SneakyThrows
    public Integer getMaxArtistId() {
        Connection connection = getConnection();

        //language=TSQL
        String query = "select top 1 ArtistId from Artist order by ArtistId desc ";
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
    public boolean insert(Artist artist){
        Connection connection = getConnection();

        //language=TSQL
        String query = "insert into Artist values (?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, artist.getName());

        int rowCount = statement.executeUpdate();

        statement.getConnection().close();
        statement.close();

        return rowCount == 1;
    }

    @SneakyThrows
    public boolean update(Artist artist){
        Connection connection = getConnection();

        //language=TSQL
        String query = "update Artist set Name = ? where ArtistId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, artist.getName());
        statement.setInt(2, artist.getArtistId());

        int rowCount = statement.executeUpdate();

        statement.getConnection().close();
        statement.close();

        return rowCount == 1;
    }

    @SneakyThrows
    public boolean deleteByKey(int key){
        Connection connection = getConnection();

        //language=TSQL
        String query = "delete Artist where ArtistId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, key);

        int rowCount = statement.executeUpdate();

        statement.getConnection().close();
        statement.close();

        return rowCount == 1;
    }

}
