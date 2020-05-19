package entities;

import entities.base.Entity;
import lombok.Data;

@Data
public class Genre extends Entity {
    private int genreId;
    private String name;

    @Override
    public String getKeyText() {
        return Integer.toString(genreId);
    }
}
