package com.ecommerce.YourChoice.ServiceImp;
import com.ecommerce.YourChoice.DTO.CategoryDTO;
import com.ecommerce.YourChoice.DTO.CategoryResponseDTO;
import com.ecommerce.YourChoice.Entity.category;
import com.ecommerce.YourChoice.Exception.APIException;
import com.ecommerce.YourChoice.Exception.ResourceNotFoundException;
import com.ecommerce.YourChoice.Repository.CategoryRepository;
import com.ecommerce.YourChoice.Service.categoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class categoryServiceImpl implements categoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponseDTO getAllcategories(Integer pageNumber , Integer pageSize) {


        Pageable pageDetails = PageRequest.of(pageNumber , pageSize);
        Page<category> categoryPage = categoryRepository.findAll(pageDetails);

        List<category> categories = categoryPage.getContent();

        if (categories.isEmpty()) {
            throw new APIException("NO categories exist");
        }

        List<CategoryDTO> categoriesDTO = categories.stream()
                .map(category -> modelMapper.map(category , CategoryDTO.class))
                .toList();

        CategoryResponseDTO response = new CategoryResponseDTO();
        response.setContent(categoriesDTO);

        return response;
    }

    @Override
    public String createCategory(CategoryDTO category) {

        // convert DTO into Entity
        category data = modelMapper.map(category , category.class);

        // first checking whether the given category exit or not
        category exist = categoryRepository.findByCategoryName(data.getCategoryName());
        if (exist != null) {
            throw new APIException("category with the name " + category.getCategoryName() + " already exist");
        }

        categoryRepository.save(data);
        return "New category has been created";
    }

    @Override
    public String deleteCategory(Long categoryId) {

        Optional<category> currentcategory = categoryRepository.findById(categoryId);
        category savedData = currentcategory.orElseThrow(() -> new ResourceNotFoundException("category" , "categoryId" , categoryId));
        categoryRepository.delete(savedData);
        return "Category has been removed";
    }

    @Override
    public String updateCategory(Long categoryId, CategoryDTO category) {

        // convert DTO into Entity
        category data = modelMapper.map(category , category.class);

        Optional<category> currentcategory = categoryRepository.findById(categoryId);
        category savedData = currentcategory.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        savedData.setCategoryName(data.getCategoryName());
        categoryRepository.save(savedData);

        return "Category has been updated";
    }


}
