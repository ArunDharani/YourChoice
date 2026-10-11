package com.ecommerce.YourChoice.ServiceImp;

import com.ecommerce.YourChoice.DTO.ProductDTO;
import com.ecommerce.YourChoice.DTO.ProductOutPutDTO;
import com.ecommerce.YourChoice.DTO.ProductResponseDTO;
import com.ecommerce.YourChoice.Entity.Product;
import com.ecommerce.YourChoice.Entity.category;
import com.ecommerce.YourChoice.Exception.APIException;
import com.ecommerce.YourChoice.Exception.ResourceNotFoundException;
import com.ecommerce.YourChoice.Repository.CategoryRepository;
import com.ecommerce.YourChoice.Repository.ProductRepository;
import com.ecommerce.YourChoice.Service.FileService;
import com.ecommerce.YourChoice.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

        // Now let us check that particular product exist or not
        boolean isProductExist = prodRepo.findAll().stream().anyMatch((prod) ->
           prod.getProductName().equals(newProduct.getProductName())
        );

        if (isProductExist) {
            throw new APIException("Product already exist");
        }

        newProduct.setCategory(existingCategory);
        double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();
        newProduct.setSpecialPrice(specialPrice);

        prodRepo.save(newProduct);

        return "New product have been created";
    }

    @Override
    public ProductResponseDTO getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber , pageSize , sortByAndOrder);

        Page<Product> productsPage = prodRepo.findAll(pageDetails);

        List<Product> products = productsPage.getContent();

        if (products.isEmpty()) {
            throw new APIException("No Product data exist");
        }

        List<ProductOutPutDTO> output = products.stream()
                .map(product -> modelMapper.map(product, ProductOutPutDTO.class))
                .toList();

        ProductResponseDTO response = new ProductResponseDTO();
        response.setData(output);
        response.setPageNumber(productsPage.getNumber());
        response.setPageSize(productsPage.getSize());
        response.setTotalPages(productsPage.getTotalPages());
        response.setLastPage(productsPage.isLast());

        return response;
    }

    @Override
    public ProductResponseDTO getAllProductsById(String categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        // Obtaining the category
        category existingCategory = cateRepo.findById(Long.parseLong(categoryId))
                .orElseThrow(() ->
                        new ResourceNotFoundException("category", "category id" , categoryId));

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber , pageSize , sortByAndOrder);

        Page<Product> productsPage = prodRepo.findByCategoryOrderByProductName(existingCategory , pageDetails);

        // obtain all the product data by categoryId
        List<Product> products = productsPage.getContent();

        List<ProductOutPutDTO> output = products.stream()
                .map(product -> modelMapper.map(product, ProductOutPutDTO.class))
                .toList();

        ProductResponseDTO response = new ProductResponseDTO();
        response.setData(output);
        response.setPageNumber(productsPage.getNumber());
        response.setPageSize(productsPage.getSize());
        response.setTotalPages(productsPage.getTotalPages());
        response.setLastPage(productsPage.isLast());

        return response;
    }

    @Override
    public ProductResponseDTO getAllByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber , pageSize , sortByAndOrder);

        Page<Product> productsPage = prodRepo.findByProductNameLikeIgnoreCase('%'+keyword+'%' , pageDetails);

        List<Product> products = productsPage.getContent();

        List<ProductOutPutDTO> output = products.stream()
                .map(product -> modelMapper.map(product, ProductOutPutDTO.class))
                .toList();

        ProductResponseDTO response = new ProductResponseDTO();
        response.setData(output);
        response.setPageNumber(productsPage.getNumber());
        response.setPageSize(productsPage.getSize());
        response.setTotalPages(productsPage.getTotalPages());
        response.setLastPage(productsPage.isLast());

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
