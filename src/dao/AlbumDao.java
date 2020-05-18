package dao;

import entities.Album;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AlbumDao extends EntityDao<Album>{
    //region singleton
    private AlbumDao() {
    }

    private static AlbumDao _instance;

    public static AlbumDao getInstance(){
        if (_instance == null)
            _instance = new AlbumDao();

        return _instance;
    }

    //endregion

    @SneakyThrows
    @Override
    protected Album readEntity(ResultSet result) {
        Album album = new Album();

        album.setAlbumId(result.getInt(1));
        album.setTitle(result.getString(2));
        album.setArtistId(result.getInt(3));

        return album;
    }

    @Override
    protected String getCountQuery() {
        //language=TSQL
        return "select count(*) from Album";
    }

    @Override
    protected String getAllQuery() {
        //language=TSQL
        return "select * from Album";
    }

    @SneakyThrows
    public Album getByKey(int key){
        //language=TSQL
        String query = "select * from Album where AlbumId = ?";

        return getOne(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, key);
            }
        });
    }

    @SneakyThrows
    public ArrayList<Album> getByArtistId(int artistId) {
        //language=TSQL
        String query = "select * from Album where ArtistId = ?";

        // verbose / decorating code
        return getMany(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, artistId);
            }
        });
    }

    @SneakyThrows
    public int getMaxAlbumId() {
        //language=TSQL
        String query = "select top 1 AlbumId from Album order by AlbumId desc ";

        return getInt(query, null);
    }

    @SneakyThrows
    public boolean insert(Album album){
        //language=TSQL
        String query = "insert into Album values (?, ?)";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, album.getTitle());
                statement.setInt(2, album.getArtistId());
            }
        });
    }

    @SneakyThrows
    public boolean update(Album album){
        //language=TSQL
        String query = "update Album set Title = ?, ArtistId = ? where AlbumId = ?";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setString(1, album.getTitle());
                statement.setInt(2, album.getArtistId());
                statement.setInt(3, album.getAlbumId());
            }
        });
    }

    @SneakyThrows
    public boolean deleteByKey(int key){
        //language=TSQL
        String query = "delete Album where AlbumId = ?";

        return execute(query, new ParameterSetter() {
            @SneakyThrows
            @Override
            public void setValue(PreparedStatement statement) {
                statement.setInt(1, key);
            }
        });
    }

}
