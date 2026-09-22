package com.ecommerce.YourChoice.Controller;
import com.ecommerce.YourChoice.Entity.category;
import com.ecommerce.YourChoice.ServiceImp.categoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class categoryController {

    @Autowired
    public categoryServiceImpl categoryServiceImpl;

    @GetMapping("/public/getcategories")
    public ResponseEntity<List<category>> getAllcategories() {
        List<category> results =  categoryServiceImpl.getAllcategories();
        return new ResponseEntity<>(results , HttpStatus.OK);
    }

    @PostMapping("/public/createCategory")
    public ResponseEntity<String> createCategory(@Valid @RequestBody category category) {
        String status =  categoryServiceImpl.createCategory(category);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            String status =  categoryServiceImpl.deleteCategory(categoryId);
            return new ResponseEntity<>(status , HttpStatus.FOUND);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason() , e.getStatusCode());
        }
    }

    @PutMapping("/admin/category/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId , @RequestBody category category) {
        try {
            String status = categoryServiceImpl.updateCategory(categoryId , category);
            return new ResponseEntity<>(status , HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason() , e.getStatusCode());
        }
    }

}
