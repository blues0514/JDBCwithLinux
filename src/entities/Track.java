package entities;

import entities.base.Entity;
import lombok.Data;

@Data
public class Track extends Entity {
    private int trackId;
    private String name;
    private int albumId;
    private int genreId;
    private double unitPrice;

    @Override
    public String getKeyText() {
        return Integer.toString(trackId);
    }
}
