package test;

import dao.*;
import entities.Celebrity;
import entities.PlaylistTrack;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CelebrityDaoTest {

    @Test
    void getCount의_반환값은_0보다_커야_함() {
        int count = CelebrityDao.getInstance().getCount();

        assertTrue(count > 0);
    }

    @Test
    void getByKey() {
        Celebrity celebrity = CelebrityDao.getInstance().getByKey("Aerosmith");

        assertEquals(new BigDecimal("9.8"), celebrity.getScore());
    }

    @Test
    void getAllQuery() {
        ArrayList<Celebrity> celebrities = CelebrityDao.getInstance().getAll();

        assertTrue(celebrities.size() > 0);
    }

    @Test
    void insert() {
        String now = LocalDateTime.now().toString();

        // CelebrityDao.getInstance().deleteByKey(now);

        int oldCount = CelebrityDao.getInstance().getCount();

        Celebrity celebrity = new Celebrity();
        celebrity.setName(now);
        celebrity.setScore(new BigDecimal("0.0"));
        boolean inserted = CelebrityDao.getInstance().insert(celebrity);

        assertTrue(inserted);

        int newCount = CelebrityDao.getInstance().getCount();

        assertEquals(oldCount + 1, newCount);
    }

    @Test
    void update() {
        final String key = "A Cor Do Som";

        if (CelebrityDao.getInstance().getByKey(key) == null) {
            Celebrity entity = new Celebrity();

            entity.setName(key);
            entity.setScore(new BigDecimal("0.0"));

            CelebrityDao.getInstance().insert(entity);
        }

        BigDecimal scoreForTesting = new BigDecimal("1.0");

        Celebrity celebrity = CelebrityDao.getInstance().getByKey(key);
        celebrity.setScore(scoreForTesting);
        CelebrityDao.getInstance().update(celebrity);

        celebrity = CelebrityDao.getInstance().getByKey(key);
        assertEquals(scoreForTesting, celebrity.getScore());
    }

    @Test
    void deleteByKey() {
        final String key = "A Cor Do Som";

        if (CelebrityDao.getInstance().getByKey(key) == null) {
            Celebrity entity = new Celebrity();

            entity.setName(key);
            entity.setScore(new BigDecimal("0.0"));

            CelebrityDao.getInstance().insert(entity);
        }

        int oldCount = CelebrityDao.getInstance().getCount();

        CelebrityDao.getInstance().deleteByKey(key);

        int newCount = CelebrityDao.getInstance().getCount();

        assertEquals(oldCount - 1, newCount);
    }
}