package main;

import dao.AlbumDao;
import dao.ArtistDao;
import dao.PlaylistTrackDao;
import dao.TrackDao;
import entities.Album;
import entities.Artist;
import entities.PlaylistTrack;
import entities.Track;
import exceptions.WrongUpdateException;
import lombok.SneakyThrows;


public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        // Album
        Album newAlbum = new Album();
        newAlbum.setTitle("new album");
        newAlbum.setArtistId(3);
        boolean inserted = AlbumDao.getInstance().insert(newAlbum);
        System.out.println(inserted);
        int maxAlbumId = AlbumDao.getInstance().getMaxAlbumId();
        System.out.println(maxAlbumId); // 새로 추가된 albumId

        // Artist
        Artist newArtist =new Artist();
        newArtist.setName("new artist - ljh");
        inserted = ArtistDao.getInstance().insert(newArtist);
        System.out.println(inserted);
        var maxArtistId = ArtistDao.getInstance().getMaxArtistId();
        System.out.println(maxArtistId);

        // Track
        Track newTrack = new Track();
        newTrack.setName("new Track - ljh");
        newTrack.setAlbumId(1);
        newTrack.setGenreId(11);
        newTrack.setUnitPrice(1.1);
        inserted = TrackDao.getInstance().insert(newTrack);
        System.out.println(inserted);
        var maxTrackId = TrackDao.getInstance().getMaxTrackId();
        System.out.println(maxTrackId);

        PlaylistTrack playlistTrack = new PlaylistTrack();
        playlistTrack.setTrackId(1);
        playlistTrack.setPlaylistId(1);

        try{
            PlaylistTrackDao.getInstance().update(playlistTrack);
        } catch (WrongUpdateException e){
            System.out.printf("Track Id is %d at %s", e.getTrackId(), e.getAt());
            e.printStackTrace();
        }

    }


}
