package com.ecommerce.YourChoice.Service;
import com.ecommerce.YourChoice.Entity.category;
import java.util.List;

public interface categoryService {

    List<category> getAllcategories();
    String createCategory(category category);
    String deleteCategory(Long categoryId);
    String updateCategory(Long categoryId , category category);
}
