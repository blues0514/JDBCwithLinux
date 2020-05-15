package test;

import entities.Album;
import dao.AlbumDao;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AlbumDaoTest {
    @Test
    void getCount() {
        int count = AlbumDao.getInstance().getCount();

        assertTrue(count > 0);
    }

    @Test
    void getByKey() {
        Album album = AlbumDao.getInstance().getByKey(1);

        assertEquals("For Those About To Rock We Salute You", album.getTitle());
    }

    @Test
    void getByArtistId() {
        ArrayList<Album> albums = AlbumDao.getInstance().getByArtistId(1);

        assertTrue(albums.size() > 0);

        for (Album album : albums) {
            assertEquals(1, album.getArtistId());
        }
    }

    @Test
    void getAll() {
        ArrayList<Album> albums = AlbumDao.getInstance().getAll();

        assertTrue(albums.size() > 0);
    }

    @Test
    void getMaxAlbumId() {
        Integer maxAlbumId = AlbumDao.getInstance().getMaxAlbumId();

        assertTrue(maxAlbumId != null);
    }

    @Test
    void insert() {
        String now = LocalDateTime.now().toString();

        int oldCount = AlbumDao.getInstance().getCount();

        Album album = new Album();
        album.setTitle(now);
        album.setArtistId(1);
        boolean inserted = AlbumDao.getInstance().insert(album);

        assertTrue(inserted);

        int newCount = AlbumDao.getInstance().getCount();

        assertEquals(oldCount + 1, newCount);

        int maxAlbumId = AlbumDao.getInstance().getMaxAlbumId();
        album = AlbumDao.getInstance().getByKey(maxAlbumId);

        assertEquals(now, album.getTitle());
    }

    @Test
    void update() {
        String now = LocalDateTime.now().toString();

        int maxAlbumId = AlbumDao.getInstance().getMaxAlbumId();
        Album album = AlbumDao.getInstance().getByKey(maxAlbumId);
        album.setTitle(now);
        AlbumDao.getInstance().update(album);

        album = AlbumDao.getInstance().getByKey(maxAlbumId);
        assertEquals(now, album.getTitle());
    }

    @Test
    void deleteByKey() {
        int oldCount = AlbumDao.getInstance().getCount();
        int maxAlbumId = AlbumDao.getInstance().getMaxAlbumId();

        AlbumDao.getInstance().deleteByKey(maxAlbumId);

        int newCount = AlbumDao.getInstance().getCount();

        assertEquals(oldCount - 1, newCount);
    }
}