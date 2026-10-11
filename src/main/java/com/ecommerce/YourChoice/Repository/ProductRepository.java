package com.ecommerce.YourChoice.Repository;

import com.ecommerce.YourChoice.Entity.Product;
import com.ecommerce.YourChoice.Entity.category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Long> {

    Page<Product> findByCategoryOrderByProductName(category categoryId, Pageable pageDetails);

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);
}
