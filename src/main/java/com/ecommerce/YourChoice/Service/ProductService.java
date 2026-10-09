package com.ecommerce.YourChoice.Service;

import com.ecommerce.YourChoice.DTO.ProductDTO;
import com.ecommerce.YourChoice.DTO.ProductResponseDTO;
import com.ecommerce.YourChoice.Entity.category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    String addProduct(String categoryId, ProductDTO product);

    ProductResponseDTO getAllProducts();

    ProductResponseDTO getAllProductsById(String categoryId);

    ProductResponseDTO getAllByKeyword(String keyword);

    String updateProduct(ProductDTO productDTO, Long productId);

    String deleteProduct(Long productId);

    String updateProdImage(Long productID, MultipartFile image) throws IOException;
}
