package com.ecommerce.YourChoice.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


@Entity(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotEmpty(message = "Category cannot be empty")
    @Size(min = 5)
    private String categoryName;

    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL)
    private List<Product> productList;

}
