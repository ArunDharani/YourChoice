package com.ecommerce.YourChoice.Service;

import com.ecommerce.YourChoice.DTO.ProductDTO;
import com.ecommerce.YourChoice.DTO.ProductResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    String addProduct(String categoryId, ProductDTO product);

    ProductResponseDTO getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponseDTO getAllProductsById(String categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponseDTO getAllByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    String updateProduct(ProductDTO productDTO, Long productId);

    String deleteProduct(Long productId);

    String updateProdImage(Long productID, MultipartFile image) throws IOException;
}
