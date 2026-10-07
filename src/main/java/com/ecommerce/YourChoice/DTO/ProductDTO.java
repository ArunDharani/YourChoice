package com.ecommerce.YourChoice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String productName;
    private String productImage;
    private Integer quantity;
    private Double price;
    private Double discount;
    private String description;
}
