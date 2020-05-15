package test;

import dao.ArtistDao;
import entities.Artist;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArtistDaoTest {

    @Test
    void getCount() {
        int count = ArtistDao.getInstance().getCount();

        assertTrue(count > 0);
    }

    @Test
    void getByKey() {
        Artist artist = ArtistDao.getInstance().getByKey(1);

        assertEquals("AC/DC", artist.getName());
    }

    @Test
    void getAll() {
        ArrayList<Artist> artists = ArtistDao.getInstance().getAll();

        assertTrue(artists.size() > 0);
    }

    @Test
    void getMaxArtistId() {
        Integer maxArtistId = ArtistDao.getInstance().getMaxArtistId();

        assertTrue(maxArtistId != null);
    }

    @Test
    void insert() {
        String now = LocalDateTime.now().toString();

        int oldCount = ArtistDao.getInstance().getCount();

        Artist artist = new Artist();
        artist.setName(now);
        boolean inserted = ArtistDao.getInstance().insert(artist);

        assertTrue(inserted);

        int newCount = ArtistDao.getInstance().getCount();

        assertEquals(oldCount + 1, newCount);

        int maxArtistId = ArtistDao.getInstance().getMaxArtistId();
        artist = ArtistDao.getInstance().getByKey(maxArtistId);

        assertEquals(now, artist.getName());
    }

    @Test
    void update() {
        String now = LocalDateTime.now().toString();

        int maxArtistId = ArtistDao.getInstance().getMaxArtistId();
        Artist artist = ArtistDao.getInstance().getByKey(maxArtistId);
        artist.setName(now);
        ArtistDao.getInstance().update(artist);

        artist = ArtistDao.getInstance().getByKey(maxArtistId);
        assertEquals(now, artist.getName());
    }

    @Test
    void deleteByKey() {
        int oldCount = ArtistDao.getInstance().getCount();
        int maxArtistId = ArtistDao.getInstance().getMaxArtistId();

        ArtistDao.getInstance().deleteByKey(maxArtistId);

        int newCount = ArtistDao.getInstance().getCount();

        assertEquals(oldCount - 1, newCount);
    }
}