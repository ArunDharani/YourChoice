package com.ecommerce.YourChoice.Controller;
import com.ecommerce.YourChoice.DTO.CategoryDTO;
import com.ecommerce.YourChoice.DTO.CategoryResponseDTO;
import com.ecommerce.YourChoice.Entity.category;
import com.ecommerce.YourChoice.ServiceImp.categoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.ecommerce.YourChoice.config.AppConstants.*;

@RestController
@RequestMapping("/api")
public class categoryController {

    @Autowired
    public categoryServiceImpl categoryServiceImpl;

    @GetMapping("/public/getcategories")
    public ResponseEntity<CategoryResponseDTO> getAllcategories
            (
                @RequestParam(name = "pageNumber" , defaultValue = PAGE_NUMBER , required = false) Integer pageNumber,
                @RequestParam(name = "pageSize" , defaultValue = PAGE_SIZE,required = false) Integer pageSize,
                @RequestParam(name = "sortBy" , defaultValue = SORT_BY , required = false) String sortBy,
                @RequestParam(name = "sortOrder" , defaultValue = SORT_ORDER , required = false) String sortOrder
            )
    {
        CategoryResponseDTO  respone =  categoryServiceImpl.getAllcategories(pageNumber , pageSize , sortBy , sortOrder);
        return new ResponseEntity<>(respone , HttpStatus.OK);
    }

    @PostMapping("/public/createCategory")
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryDTO category) {
        String status =  categoryServiceImpl.createCategory(category);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
            String status =  categoryServiceImpl.deleteCategory(categoryId);
            return new ResponseEntity<>(status , HttpStatus.FOUND);
    }

    @PutMapping("/admin/category/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId , @RequestBody CategoryDTO category) {
            String status = categoryServiceImpl.updateCategory(categoryId , category);
            return new ResponseEntity<>(status , HttpStatus.OK);
    }

}
