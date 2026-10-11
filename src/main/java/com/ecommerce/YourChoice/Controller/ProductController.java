package com.ecommerce.YourChoice.Controller;
import com.ecommerce.YourChoice.DTO.ProductDTO;
import com.ecommerce.YourChoice.DTO.ProductResponseDTO;
import com.ecommerce.YourChoice.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.ecommerce.YourChoice.config.AppConstants.*;
import static com.ecommerce.YourChoice.config.AppConstants.SORT_ORDER;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService prodService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDTO product , @PathVariable String categoryId) {
        String status = prodService.addProduct(categoryId , product);
        return new ResponseEntity<>(status ,HttpStatus.CREATED);
    }


    @GetMapping("/public/products")
    public ResponseEntity<ProductResponseDTO> getAllProducts
            (
                    @RequestParam(name = "pageNumber" , defaultValue = PAGE_NUMBER , required = false) Integer pageNumber,
                    @RequestParam(name = "pageSize" , defaultValue = PAGE_SIZE,required = false) Integer pageSize,
                    @RequestParam(name = "sortBy" , defaultValue = SORT_PRODUCTS_BY , required = false) String sortBy,
                    @RequestParam(name = "sortOrder" , defaultValue = SORT_ORDER , required = false) String sortOrder
            )
    {
        ProductResponseDTO data = prodService.getAllProducts(pageNumber , pageSize , sortBy , sortOrder);
        return new ResponseEntity<>(data , HttpStatus.OK);
    }

    @GetMapping("/public/{categoryId}/products")
    public ResponseEntity<ProductResponseDTO> getProductsByCategoryId
            (
                    @PathVariable String categoryId,
                    @RequestParam(name = "pageNumber" , defaultValue = PAGE_NUMBER , required = false) Integer pageNumber,
                    @RequestParam(name = "pageSize" , defaultValue = PAGE_SIZE,required = false) Integer pageSize,
                    @RequestParam(name = "sortBy" , defaultValue = SORT_PRODUCTS_BY , required = false) String sortBy,
                    @RequestParam(name = "sortOrder" , defaultValue = SORT_ORDER , required = false) String sortOrder
            )
    {
        ProductResponseDTO data = prodService.getAllProductsById(categoryId , pageNumber , pageSize , sortBy , sortOrder);
        return new ResponseEntity<>(data , HttpStatus.OK);
    }

    @GetMapping("/public/products/{keyword}")
    public ResponseEntity<ProductResponseDTO> getProductsByKeyword
            (
                    @PathVariable String keyword,
                    @RequestParam(name = "pageNumber" , defaultValue = PAGE_NUMBER , required = false) Integer pageNumber,
                    @RequestParam(name = "pageSize" , defaultValue = PAGE_SIZE,required = false) Integer pageSize,
                    @RequestParam(name = "sortBy" , defaultValue = SORT_PRODUCTS_BY , required = false) String sortBy,
                    @RequestParam(name = "sortOrder" , defaultValue = SORT_ORDER , required = false) String sortOrder
            )
    {
        ProductResponseDTO data = prodService.getAllByKeyword(keyword , pageNumber , pageSize , sortBy , sortOrder);
        return new ResponseEntity<>(data , HttpStatus.OK);

    }

    @PutMapping("/admin/product/{productId}")
    public ResponseEntity<String> updateProduct(@Valid @RequestBody ProductDTO productDTO , @PathVariable Long productId) {
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
