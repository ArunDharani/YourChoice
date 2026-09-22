package com.ecommerce.YourChoice.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


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

}
