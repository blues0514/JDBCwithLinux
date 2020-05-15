package com.company;

import lombok.SneakyThrows;

import java.util.ArrayList;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        Album album = AlbumDao.getInstance().getByKey(2);
        ArrayList<Album> albums = AlbumDao.getInstance().getAll();
        ArrayList<Album> albums2 = AlbumDao.getInstance().getByArtistId(3);
        int albumCount = AlbumDao.getInstance().getCount();

    }
}