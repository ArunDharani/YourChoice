package com.ecommerce.YourChoice.Repository;

import com.ecommerce.YourChoice.Entity.category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<category, Long> {

}
