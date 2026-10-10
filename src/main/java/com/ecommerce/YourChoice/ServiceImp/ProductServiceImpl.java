package com.ecommerce.YourChoice.ServiceImp;

import com.ecommerce.YourChoice.DTO.ProductDTO;
import com.ecommerce.YourChoice.DTO.ProductOutPutDTO;
import com.ecommerce.YourChoice.DTO.ProductResponseDTO;
import com.ecommerce.YourChoice.Entity.Product;
import com.ecommerce.YourChoice.Entity.category;
import com.ecommerce.YourChoice.Exception.ResourceNotFoundException;
import com.ecommerce.YourChoice.Repository.CategoryRepository;
import com.ecommerce.YourChoice.Repository.ProductRepository;
import com.ecommerce.YourChoice.Service.FileService;
import com.ecommerce.YourChoice.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository prodRepo;

    @Autowired
    private CategoryRepository cateRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

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

    @Override
    public ProductResponseDTO getAllProductsById(String categoryId) {

        // Obtaining the category
        category existingCategory = cateRepo.findById(Long.parseLong(categoryId))
                .orElseThrow(() ->
                        new ResourceNotFoundException("category", "category id" , categoryId));

        // obtain all the product data by categoryId
        List<Product> products = prodRepo.findByCategoryOrderByProductName(existingCategory);

        List<ProductOutPutDTO> output = products.stream()
                .map(product -> modelMapper.map(product, ProductOutPutDTO.class))
                .toList();

        ProductResponseDTO response = new ProductResponseDTO();
        response.setData(output);

        return response;
    }

    @Override
    public ProductResponseDTO getAllByKeyword(String keyword) {

        // obtain all the product data by categoryId
        List<Product> products = prodRepo.findByProductNameLikeIgnoreCase('%'+keyword+'%');

        List<ProductOutPutDTO> output = products.stream()
                .map(product -> modelMapper.map(product, ProductOutPutDTO.class))
                .toList();

        ProductResponseDTO response = new ProductResponseDTO();
        response.setData(output);

        return response;
    }

    @Override
    public String updateProduct(ProductDTO productDTO, Long productId) {

        // first let us check product exists or not
        Optional<Product> isExistProduct = prodRepo.findById(productId);
        Product newProduct = isExistProduct.orElseThrow(() -> new ResourceNotFoundException("product" , "Product Id" , productId));

        // Convert dto into Entity
        Product currentProduct = modelMapper.map(productDTO , Product.class);

        // updating the fields
        newProduct.setProductName(currentProduct.getProductName());
        newProduct.setDiscount(currentProduct.getDiscount());
        newProduct.setDescription(currentProduct.getDescription());
        newProduct.setCategory(currentProduct.getCategory());
        newProduct.setQuantity(currentProduct.getQuantity());
        newProduct.setPrice(currentProduct.getPrice());
        double specialPrice = currentProduct.getPrice() - (currentProduct.getDiscount() * 0.01) * currentProduct.getPrice();
        newProduct.setSpecialPrice(specialPrice);

        // saving the prduct
        prodRepo.save(newProduct);

        return "product updated successfully";
    }

    @Override
    public String deleteProduct(Long productId) {

        // Check whether the product exist
        prodRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product ", "product Id", productId));

        // delete the product
        prodRepo.deleteById(productId);

        return "Product has been deleted successfully";

    }

    @Override
    public String updateProdImage(Long productID, MultipartFile image) throws IOException {

        Product product = prodRepo.findById(productID).orElseThrow(() -> new ResourceNotFoundException("Product","product id",productID));

        String fileName = fileService.uploadImage(path , image);

        product.setImage(fileName);

        prodRepo.save(product);

        return "image have been updated for the product";
    }

}
