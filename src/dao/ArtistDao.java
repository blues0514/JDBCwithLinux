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
    private Artist getOne(String query, ParameterSetter parameterSetter){
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(query);

        if (parameterSetter != null)
            parameterSetter.setValue(statement);

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
    private ArrayList<Artist> getMany(String query, ParameterSetter parameterSetter) {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(query);
        if (parameterSetter != null)
            parameterSetter.setValue(statement);

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
    public int getCount(){
        //language=TSQL
        String query = "select count(*) from Artist";

        return getInt(query,null);
    }

    @SneakyThrows
    public Artist getByKey(int key){
        //language=TSQL
        String query = "select * from Artist where ArtistId = ?";

        return getOne(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1,key);
            }
        });
    }

    @SneakyThrows
    public ArrayList<Artist> getAll() {
        //language=TSQL
        String query = "select * from Artist";

        return getMany(query, null);
    }

    @SneakyThrows
    public int getMaxArtistId() {
        //language=TSQL
        String query = "select top 1 ArtistId from Artist order by ArtistId desc ";
        return getInt(query,null);
    }

    @SneakyThrows
    public boolean insert(Artist artist){
        //language=TSQL
        String query = "insert into Artist values (?)";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, artist.getName());
            }
        });
    }

    @SneakyThrows
    public boolean update(Artist artist){
        //language=TSQL
        String query = "update Artist set Name = ? where ArtistId = ?";
        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, artist.getName());
                statement.setInt(2, artist.getArtistId());
            }
        });
    }

    @SneakyThrows
    public boolean deleteByKey(int key){
        //language=TSQL
        String query = "delete Artist where ArtistId = ?";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, key);
            }
        });
    }

}
