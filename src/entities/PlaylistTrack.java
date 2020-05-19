package entities;

import entities.base.Entity;
import lombok.Data;

@Data
public class PlaylistTrack extends Entity {
    private int playlistId;
    private int trackId;

    @Override
    public String getKeyText() {
        return Integer.toString(playlistId) + " : " + Integer.toString(trackId);
    }
}
