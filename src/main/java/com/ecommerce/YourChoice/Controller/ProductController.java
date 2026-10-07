package com.ecommerce.YourChoice.Controller;
import com.ecommerce.YourChoice.DTO.ProductDTO;
import com.ecommerce.YourChoice.DTO.ProductResponseDTO;
import com.ecommerce.YourChoice.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService prodService;

    @RequestMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO product ,@PathVariable String categoryId) {
        String status = prodService.addProduct(categoryId , product);
        return new ResponseEntity<>(status ,HttpStatus.CREATED);
    }


    @RequestMapping("/public/products")
    public ResponseEntity<ProductResponseDTO> getAllProducts() {
        ProductResponseDTO data = prodService.getAllProducts();
        return new ResponseEntity<>(data , HttpStatus.OK);
    }
}
