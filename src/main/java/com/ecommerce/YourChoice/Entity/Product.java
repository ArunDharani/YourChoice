package com.ecommerce.YourChoice.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NotBlank
    @Size(min = 3 ,message = "Minimum product name size should be greater than 3")
    private String productName;

    @NotBlank
    @Size(min = 20 ,message = "Product Description should be minimum 21 characters")
    private String description;


    private Integer quantity;
    private Double price;
    private Double discount;
    private Double specialPrice;
    private String image;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private category category;

}


