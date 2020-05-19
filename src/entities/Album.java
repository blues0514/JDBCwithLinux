package entities;

import entities.base.Entity;
import lombok.*;

// boiler-plate code

@Data
public class Album extends Entity {
    private int albumId;
    private String title;
    private int artistId;

    @Override
    public String getKeyText() {
        return Integer.toString(albumId);
    }
}
