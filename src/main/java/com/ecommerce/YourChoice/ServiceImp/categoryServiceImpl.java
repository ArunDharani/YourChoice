package com.ecommerce.YourChoice.ServiceImp;
import com.ecommerce.YourChoice.Entity.category;
import com.ecommerce.YourChoice.Repository.CategoryRepository;
import com.ecommerce.YourChoice.Service.categoryService;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class categoryServiceImpl implements categoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<category> getAllcategories() {
        return categoryRepository.findAll();
    }

    @Override
    public String createCategory(category category) {
        categoryRepository.save(category);
        return "New category has been created";
    }

    @Override
    public String deleteCategory(Long categoryId) {

        Optional<category> currentcategory = categoryRepository.findById(categoryId);
        category savedData = currentcategory.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        categoryRepository.delete(savedData);
        return "Category has been removed";
    }

    @Override
    public String updateCategory(Long categoryId, category category) {

        Optional<category> currentcategory = categoryRepository.findById(categoryId);
        category savedData = currentcategory.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        savedData.setCategoryName(category.getCategoryName());
        categoryRepository.save(savedData);

        return "Category has been updated";
    }


}
