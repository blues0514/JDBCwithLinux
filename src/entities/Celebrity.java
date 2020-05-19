package entities;

import entities.base.Entity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Celebrity extends Entity {
    private String name;
    private BigDecimal score;

    @Override
    public String getKeyText() {
        return name;
    }
}
