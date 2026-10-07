package com.ecommerce.YourChoice.ServiceImp;

import com.ecommerce.YourChoice.DTO.ProductDTO;
import com.ecommerce.YourChoice.DTO.ProductOutPutDTO;
import com.ecommerce.YourChoice.DTO.ProductResponseDTO;
import com.ecommerce.YourChoice.Entity.Product;
import com.ecommerce.YourChoice.Entity.category;
import com.ecommerce.YourChoice.Exception.ResourceNotFoundException;
import com.ecommerce.YourChoice.Repository.CategoryRepository;
import com.ecommerce.YourChoice.Repository.ProductRepository;
import com.ecommerce.YourChoice.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository prodRepo;

    @Autowired
    private CategoryRepository cateRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String addProduct(String categoryId, ProductDTO product) {

        // Obtaining the category
        category existingCategory = cateRepo.findById(Long.parseLong(categoryId))
                .orElseThrow(() ->
                        new ResourceNotFoundException("category", "category id" , categoryId));

        // Convert product DTO into product Entity
        Product newProduct = modelMapper.map(product , Product.class);

        newProduct.setCategory(existingCategory);
        double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();
        newProduct.setSpecialPrice(specialPrice);

        prodRepo.save(newProduct);

        return "New product have been created";
    }

    @Override
    public ProductResponseDTO getAllProducts() {

        // obtain all the product data
        List<Product> products = prodRepo.findAll();
        List<ProductOutPutDTO> output = products.stream()
                .map(product -> modelMapper.map(product, ProductOutPutDTO.class))
                .toList();

        ProductResponseDTO response = new ProductResponseDTO();
        response.setData(output);

        return response;
    }
}
