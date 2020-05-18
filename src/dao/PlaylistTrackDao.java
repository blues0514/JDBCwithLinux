package dao;

import entities.PlaylistTrack;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PlaylistTrackDao extends EntityDao<PlaylistTrack>{
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

    @SneakyThrows
    @Override
    protected PlaylistTrack readEntity(ResultSet result) {
        PlaylistTrack playlistTrack = new PlaylistTrack();

        playlistTrack.setPlaylistId(result.getInt(1));
        playlistTrack.setTrackId(result.getInt(2));

        return playlistTrack;
    }

    @Override
    protected String getCountQuery() {
        //language=TSQL
        return "select count(*) from PlaylistTrack";
    }

    @Override
    protected String getAllQuery() {
        //language=TSQL
        return "select * from PlaylistTrack";
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
