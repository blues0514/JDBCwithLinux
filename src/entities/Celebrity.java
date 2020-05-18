package entities;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Celebrity {
    private String name;
    private BigDecimal score;
}
