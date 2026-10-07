package com.ecommerce.YourChoice.Service;

import com.ecommerce.YourChoice.DTO.ProductDTO;
import com.ecommerce.YourChoice.DTO.ProductResponseDTO;
import com.ecommerce.YourChoice.Entity.category;

import java.util.List;

public interface ProductService {
    String addProduct(String categoryId, ProductDTO product);

    ProductResponseDTO getAllProducts();

    ProductResponseDTO getAllProductsById(String categoryId);

    ProductResponseDTO getAllByKeyword(String keyword);
}
