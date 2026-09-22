package com.ecommerce.YourChoice.Repository;
import com.ecommerce.YourChoice.Entity.category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<category, Long> {

    category findByCategoryName(@NotEmpty(message = "Category cannot be empty") @Size(min = 5) String categoryName);
}
