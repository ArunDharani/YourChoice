package com.ecommerce.YourChoice.Controller;
import com.ecommerce.YourChoice.DTO.ProductDTO;
import com.ecommerce.YourChoice.DTO.ProductResponseDTO;
import com.ecommerce.YourChoice.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService prodService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO product ,@PathVariable String categoryId) {
        String status = prodService.addProduct(categoryId , product);
        return new ResponseEntity<>(status ,HttpStatus.CREATED);
    }


    @GetMapping("/public/products")
    public ResponseEntity<ProductResponseDTO> getAllProducts() {
        ProductResponseDTO data = prodService.getAllProducts();
        return new ResponseEntity<>(data , HttpStatus.OK);
    }

    @GetMapping("/public/{categoryId}/products")
    public ResponseEntity<ProductResponseDTO> getProductsByCategoryId(@PathVariable String categoryId) {
        ProductResponseDTO data = prodService.getAllProductsById(categoryId);
        return new ResponseEntity<>(data , HttpStatus.OK);
    }

    @GetMapping("/public/products/{keyword}")
    public ResponseEntity<ProductResponseDTO> getProductsByKeyword(@PathVariable String keyword) {
        ProductResponseDTO data = prodService.getAllByKeyword(keyword);
        return new ResponseEntity<>(data , HttpStatus.OK);
    }

    @PutMapping("/admin/product/{productId}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO , @PathVariable Long productId) {
        String status = prodService.updateProduct(productDTO , productId);
        return new ResponseEntity<>(status , HttpStatus.OK);
    }

    @DeleteMapping("/admin/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        String status = prodService.deleteProduct(productId);
        return new ResponseEntity<>(status , HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productID}/image")
    public ResponseEntity<String> updateProductImage(@PathVariable Long productID , @RequestParam("image")MultipartFile image) throws IOException {
        String status = prodService.updateProdImage(productID , image);
        return new ResponseEntity<>(status , HttpStatus.OK);
    }


}
