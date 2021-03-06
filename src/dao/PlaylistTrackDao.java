package dao;


import dao.base.IntIntEntityDao;
import entities.PlaylistTrack;
import exceptions.WrongUpdateException;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PlaylistTrackDao extends IntIntEntityDao<PlaylistTrack> {
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

    @Override
    protected String getByKeyQuery() {
        //language=TSQL
        return "select * from PlaylistTrack where PlaylistId = ? and TrackId = ?";
    }

    @Override
    protected String deleteByKeyQuery() {
        //language=TSQL
        return "delete PlaylistTrack where PlaylistId = ? and TrackId = ?";
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
    @Override
    public boolean update(PlaylistTrack entity) {
        throw new WrongUpdateException(entity.getTrackId(), LocalDateTime.now());
    }

}
