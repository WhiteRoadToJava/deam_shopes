package dev.mohammad.dreamshopes.request;

import dev.mohammad.dreamshopes.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor

public class AddProductRequest {

    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private int inventory;

    private Category category;

}
