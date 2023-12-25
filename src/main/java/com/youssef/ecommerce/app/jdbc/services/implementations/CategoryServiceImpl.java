package com.youssef.ecommerce.app.jdbc.services.implementations;


import com.youssef.ecommerce.app.jdbc.services.models.Category;
import com.youssef.ecommerce.app.jdbc.mappers.requests.AddCategoryRequestBodyMapper;
import com.youssef.ecommerce.app.jdbc.mappers.requests.UpdateCategoryRequestBodyMapper;
import com.youssef.ecommerce.app.jdbc.models.requests.AddCategoryRequestBody;
import com.youssef.ecommerce.app.jdbc.models.requests.UpdateCategoryRequestBody;
import com.youssef.ecommerce.app.jdbc.models.responses.AddCategoryResponseBody;
import com.youssef.ecommerce.app.jdbc.models.responses.FindCategoryByIdResponse;
import com.youssef.ecommerce.app.jdbc.repositories.core_interfaces.CategoryRepoInterface;
import com.youssef.ecommerce.app.jdbc.services.interfaces.CategoryServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryServiceInterface {


    // REPO
    @Autowired
    @Qualifier("jdbcTemplateCategoryRepoImpl")
    private CategoryRepoInterface categoryRepo;



    @Override
    public AddCategoryResponseBody addNewCategory(AddCategoryRequestBody categoryRequestBody) {
        if(categoryRepo.isExistCategoryByNameIgnoreCase(categoryRequestBody.getName())) {
            log.error(">>>>> Category With Name = " + categoryRequestBody.getName() + " IS Already EXIST");
            return AddCategoryResponseBody.builder()
                    .id(null)
                    .build();
        }
        Category category = AddCategoryRequestBodyMapper.toServiceModel(categoryRequestBody);
        category = categoryRepo.saveCategory(category);

        return AddCategoryResponseBody.builder()
                .id(category.getId())
                .build();
    }

    @Override
    public boolean isCategoryExistsById(Integer categoryId) {
        return categoryRepo.existsCategoryById(categoryId);
    }

    @Override
    public FindCategoryByIdResponse findById(Integer categoryId) {
        Optional<Category> category = categoryRepo.findCategoryById(categoryId);
        if(category.isEmpty()) {
            return FindCategoryByIdResponse.builder()
                    .id(null)
                    .build();
        }
        log.info(">>>>> Category = " + category);
        return FindCategoryByIdResponse.builder()
                .id(category.get().getId())
                .name(category.get().getName())
                .build();
    }

    @Override
    public boolean deleteById(Integer categoryId) {
        return categoryRepo.deleteCategoryById(categoryId);
    }

    @Override
    public boolean updateCategoryById(Integer categoryId, UpdateCategoryRequestBody updateCategoryRequestBody) {
        return categoryRepo.updateCategoryById(categoryId , UpdateCategoryRequestBodyMapper.toServiceModel(updateCategoryRequestBody));
    }

    @Override
    public Optional<Category> findCategoryById(Integer categoryId) {
        return categoryRepo.findCategoryById(categoryId);
    }


}
