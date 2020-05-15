package com.company;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AlbumDao {
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

    //region helper methods
    @SneakyThrows
    private Album readAlbum(ResultSet result) {
        Album album = new Album();

        album.setAlbumId(result.getInt(1));
        album.setTitle(result.getString(2));
        album.setArtistId(result.getInt(3));

        return album;
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
        String query = "select count(*) from Album";
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
    public Album getByKey(int key){
        Connection connection = getConnection();

        //language=TSQL
        String query = "select * from Album where AlbumId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, key);

        ResultSet result = statement.executeQuery();

        ArrayList<Album> albums = new ArrayList<>();
        while (result.next()){
            Album album = readAlbum(result);
            albums.add(album);
        }

        result.close();
        statement.getConnection().close();
        statement.close();

        return albums.size() == 0 ? null : albums.get(0);
    }

    @SneakyThrows
    public ArrayList<Album> getByArtistId(int artistId) {
        Connection connection = getConnection();

        //language=TSQL
        String query = "select * from Album where ArtistId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, artistId);

        ResultSet result = statement.executeQuery();

        ArrayList<Album> albums = new ArrayList<>();
        while (result.next()){
            Album album = readAlbum(result);
            albums.add(album);
        }

        result.close();
        statement.getConnection().close();
        statement.close();

        return albums;
    }

    @SneakyThrows
    public ArrayList<Album> getAll() {
        Connection connection = getConnection();

        //language=TSQL
        String query = "select * from Album";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet result = statement.executeQuery();

        ArrayList<Album> albums = new ArrayList<>();
        while (result.next()){
            Album album = readAlbum(result);
            albums.add(album);
        }

        result.close();
        statement.getConnection().close();
        statement.close();

        return albums;
    }
}
