package entities;

import lombok.Data;

@Data
public class Track {
    private int trackId;
    private String name;
    private int albumId;
    private int genreId;
    private double unitPrice;
}
