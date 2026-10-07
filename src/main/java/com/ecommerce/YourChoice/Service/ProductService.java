package com.ecommerce.YourChoice.Service;

import com.ecommerce.YourChoice.DTO.ProductDTO;
import com.ecommerce.YourChoice.DTO.ProductResponseDTO;

public interface ProductService {
    String addProduct(String categoryId, ProductDTO product);

    ProductResponseDTO getAllProducts();
}
