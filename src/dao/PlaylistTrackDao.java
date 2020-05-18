package dao;

import entities.PlaylistTrack;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PlaylistTrackDao {
    //region singleton
    private PlaylistTrackDao() {
    }

    private static PlaylistTrackDao _instance;

    public static PlaylistTrackDao getInstance() {
        if (_instance == null)
            _instance = new PlaylistTrackDao();

        return _instance;
    }
    //endregion

    //region helper methods
    @SneakyThrows
    private PlaylistTrack readPlaylist(ResultSet result) {
        PlaylistTrack playlistTrack = new PlaylistTrack();

        playlistTrack.setPlaylistId(result.getInt(1));
        playlistTrack.setTrackId(result.getInt(2));

        return playlistTrack;
    }

    @SneakyThrows
    private Connection getConnection() {
        String connString =
                "jdbc:sqlserver://192.168.1.5;database=Chinook;user=sa;password=3512";
        return DriverManager.getConnection(connString);
    }

    @SneakyThrows
    private int getInt(String query, ParameterSetter parameterSetter) {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(query);

        if (parameterSetter != null)
            parameterSetter.setValue(statement);

        ResultSet result = statement.executeQuery();

        int count = 0;
        while (result.next()) {
            count = result.getInt(1);
        }

        result.close();
        statement.getConnection().close();
        statement.close();

        return count;
    }

    @SneakyThrows
    private PlaylistTrack getOne(String query, ParameterSetter parameterSetter) {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(query);

        if (parameterSetter != null)
            parameterSetter.setValue(statement);

        ResultSet result = statement.executeQuery();

        ArrayList<PlaylistTrack> playlistTracks = new ArrayList<>();
        while (result.next()) {
            PlaylistTrack playlistTrack = readPlaylist(result);
            playlistTracks.add(playlistTrack);
        }

        result.close();
        statement.getConnection().close();
        statement.close();

        return playlistTracks.size() == 0 ? null : playlistTracks.get(0);
    }

    @SneakyThrows
    private ArrayList<PlaylistTrack> getMany(String query, ParameterSetter parameterSetter) {
        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(query);
        if (parameterSetter != null)
            parameterSetter.setValue(statement);

        ResultSet result = statement.executeQuery();

        ArrayList<PlaylistTrack> playlistTracks = new ArrayList<>();
        while (result.next()) {
            PlaylistTrack playlistTrack = readPlaylist(result);
            playlistTracks.add(playlistTrack);
        }

        result.close();
        statement.getConnection().close();
        statement.close();

        return playlistTracks;
    }

    @SneakyThrows
    private boolean execute(String query, ParameterSetter parameterSetter) {
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
        String query = "select count(*) from PlaylistTrack";

        return getInt(query, null);
    }

    @SneakyThrows
    public PlaylistTrack getByKey(int key, int key2) {
        //language=TSQL
        String query = "select * from PlaylistTrack where PlaylistId = ? and TrackId = ?";

        return getOne(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, key);
                statement.setInt(2, key2);
            }
        });
    }

    @SneakyThrows
    public ArrayList<PlaylistTrack> getByPlaylistId(int playlistId) {
        //language=TSQL
        String query = "select * from PlaylistTrack where PlaylistId = ?";

        // verbose / decorating code
        return getMany(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, playlistId);
            }
        });
    }

    @SneakyThrows
    public ArrayList<PlaylistTrack> getByTrackId(int trackId) {
        //language=TSQL
        String query = "select * from PlaylistTrack where TrackId = ?";

        // verbose / decorating code
        return getMany(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, trackId);
            }
        });
    }

    @SneakyThrows
    public ArrayList<PlaylistTrack> getAll() {
        //language=TSQL
        String query = "select * from PlaylistTrack";

        return getMany(query, null);
    }

    @SneakyThrows
    public boolean insert(PlaylistTrack playlistTrack) {
        //language=TSQL
        String query = "insert into PlaylistTrack values (?, ?)";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, playlistTrack.getPlaylistId());
                statement.setInt(2, playlistTrack.getTrackId());
            }
        });
    }

    @SneakyThrows
    public boolean deleteByKey(int key, int key2) {
        //language=TSQL
        String query = "delete PlaylistTrack where PlaylistId = ? and TrackId = ?";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, key);
                statement.setInt(2, key2);
            }
        });
    }
}
