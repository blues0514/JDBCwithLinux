package entities;

import lombok.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;

// boiler-plate code

@Data
public class Album {
    private int albumId;
    private String title;
    private int artistId;
}
