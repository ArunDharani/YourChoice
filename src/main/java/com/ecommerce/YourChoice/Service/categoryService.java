package com.ecommerce.YourChoice.Service;
import com.ecommerce.YourChoice.DTO.CategoryResponseDTO;
import com.ecommerce.YourChoice.Entity.category;
import java.util.List;

public interface categoryService {

    CategoryResponseDTO getAllcategories();
    String createCategory(category category);
    String deleteCategory(Long categoryId);
    String updateCategory(Long categoryId , category category);
}
