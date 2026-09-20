package com.ecommerce.YourChoice.ServiceImp;
import com.ecommerce.YourChoice.Entity.category;
import com.ecommerce.YourChoice.Service.categoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class categoryServiceImpl implements categoryService {

    private List<category> categories = new ArrayList<>();

    @Override
    public List<category> getAllcategories() {
        return categories;
    }

    @Override
    public String createCategory(category category) {
        Long id = (long) (categories.size() + 1);
        category.setCategoryId(id);
        categories.add(category);
        return "New category has been created";
    }

    @Override
    public String deleteCategory(Long categoryId) {
        category current = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Resource not found"));
        if (current == null) {
            return "No such data exist";
        }
        categories.remove(current);
        return "Category has been removed";
    }

    @Override
    public String updateCategory(Long categoryId, category category) {
        Optional<category> current = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();

        category update = null;

        if (current.isPresent()) {
            update = current.get();
            categories.remove(update);
            update.setCategoryName(category.getCategoryName());
            categories.add(update);
        }


        return "Category has been updated";
    }


}
