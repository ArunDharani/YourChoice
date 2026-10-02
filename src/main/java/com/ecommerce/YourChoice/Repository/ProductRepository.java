package com.ecommerce.YourChoice.Repository;

import com.ecommerce.YourChoice.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Long> {
}
