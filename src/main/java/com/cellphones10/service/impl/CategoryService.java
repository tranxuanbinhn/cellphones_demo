package com.cellphones10.service.impl;

import com.cellphones10.dto.CategoryDTO;
import com.cellphones10.dto.ProductDTO;
import com.cellphones10.entity.CategoryEntity;
import com.cellphones10.entity.ProductEntity;
import com.cellphones10.repository.CategoryRespository;
import com.cellphones10.repository.ProductRepository;
import com.cellphones10.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRespository categoryRespository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductRepository productRepository;
    @Override
    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {
        if(categoryRespository.existsByCategoryCode(categoryDTO.getCategoryCode()))
        {
            throw new RuntimeException("exists");
        }



        CategoryEntity categoryEntity = mapper.map(categoryDTO, CategoryEntity.class);
        CategoryEntity categoryEntitySaved = categoryRespository.save(categoryEntity);


        CategoryDTO categoryDTOSave =  mapper.map(categoryEntitySaved, CategoryDTO.class);
        return categoryDTOSave;
    }

    @Override
    public List<CategoryDTO> findAll(Pageable pageable) {
        List<CategoryEntity> categoryEntities=  categoryRespository.findAll(pageable).getContent();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (CategoryEntity categoryEntity:categoryEntities) {
            categoryDTOS.add(mapper.map(categoryEntity, CategoryDTO.class) );
        }
        return categoryDTOS;
    }

    @Override
    public boolean delete(List<Long> list) {
        Integer countList = list.size();
        for (Long id:list) {
            if(categoryRespository.existsById(id))
            {
                categoryRespository.deleteById(id);
                countList --;
            }
        }
        if(countList == 0)
        {
            return  true;
        }

        return false;
    }
    public Long Count()
    {
        return  categoryRespository.count();
    }
}
