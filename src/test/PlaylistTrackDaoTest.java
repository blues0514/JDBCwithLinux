package test;

import dao.PlaylistTrackDao;
import dao.TrackDao;
import entities.PlaylistTrack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTrackDaoTest {

    @Test
    void insert() {
        final int playlistId = 17;
        int trackId = TrackDao.getInstance().getMaxTrackId();
        PlaylistTrackDao.getInstance().deleteByKey(playlistId, trackId);

        int oldCount = PlaylistTrackDao.getInstance().getCount();

        PlaylistTrack playlistTrack = new PlaylistTrack();
        playlistTrack.setPlaylistId(playlistId);
        playlistTrack.setTrackId(trackId);

        boolean inserted = PlaylistTrackDao.getInstance().insert(playlistTrack);

        assertTrue(inserted);

        int newCount = PlaylistTrackDao.getInstance().getCount();

        assertEquals(oldCount + 1, newCount);
    }

    @Test
    void deleteByKey() {
        final int playlistId = 17;
        int trackId = TrackDao.getInstance().getMaxTrackId();

        if (PlaylistTrackDao.getInstance().getByKey(playlistId, trackId) == null)
        {
            var entity = new PlaylistTrack();
            entity.setPlaylistId(playlistId);
            entity.setTrackId(trackId);
            PlaylistTrackDao.getInstance().insert(entity);
        }

        int oldCount = PlaylistTrackDao.getInstance().getCount();

        PlaylistTrackDao.getInstance().deleteByKey(playlistId, trackId);

        int newCount = PlaylistTrackDao.getInstance().getCount();

        assertEquals(oldCount - 1, newCount);
    }
}