package com.ecommerce.YourChoice.Service;
import com.ecommerce.YourChoice.DTO.CategoryDTO;
import com.ecommerce.YourChoice.DTO.CategoryResponseDTO;
import com.ecommerce.YourChoice.Entity.category;
import java.util.List;

public interface categoryService {

    CategoryResponseDTO getAllcategories(Integer pageNumber , Integer pageSize);
    String createCategory(CategoryDTO category);
    String deleteCategory(Long categoryId);
    String updateCategory(Long categoryId , CategoryDTO category);
}
