package com.ecommerce.YourChoice.Repository;

import com.ecommerce.YourChoice.Entity.Product;
import com.ecommerce.YourChoice.Entity.category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product , Long> {

    List<Product> findByCategoryOrderByProductName(category categoryId);

    List<Product> findByProductNameLikeIgnoreCase(String keyword);
}
