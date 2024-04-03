package com.cellphones10.service.impl;

import com.cellphones10.dto.ProductDTO;
import com.cellphones10.entity.BrandEntity;
import com.cellphones10.entity.CategoryEntity;
import com.cellphones10.entity.ProductEntity;
import com.cellphones10.repository.BrandRepository;
import com.cellphones10.repository.CategoryRespository;
import com.cellphones10.repository.ProductRepository;
import com.cellphones10.service.IProductService;
import com.sun.jdi.InternalException;
import jdk.jshell.spi.ExecutionControl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRespository categoryRespository;

    @Override
    @Transactional
    public ProductDTO save(ProductDTO productDTO) throws InternalException {

        CategoryEntity categoryEntity = categoryRespository.findByCategoryName(productDTO.getCategoryName());
        if(categoryEntity==null)
        {
            throw new RuntimeException("Category is null");
        }
        BrandEntity brandEntity = brandRepository.findByBrandName(productDTO.getBrandName());
        if(brandEntity == null)
        {
            throw  new RuntimeException("Brand is null");
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity = mapper.map(productDTO, ProductEntity.class);
        productEntity.setBrand(brandEntity);
        productEntity.setCategory(categoryEntity);
        productEntity.setProductName(productDTO.getProductName());


        ProductEntity saved =  productRepository.save(productEntity);
        ProductDTO result = mapper.map(saved, ProductDTO.class);
        result.setCategoryName(categoryEntity.getCategoryName());
        result.setBrandName(brandEntity.getBrandName());
        return result;
    }

    @Override
    public List<ProductDTO> findAll(Pageable pageable) {
        List<ProductEntity> productEntities = productRepository.findAll(pageable).getContent();
        List<ProductDTO> productDto = new ArrayList<>();
        for (ProductEntity productEntity:productEntities) {
            productDto.add(mapper.map(productEntity, ProductDTO.class));
        }

        return productDto;
    }

    @Override
    @Transactional
    public boolean delete(List<Long> list) throws RuntimeException{

          Integer count = list.size();
          for (Long id:list) {

              if(productRepository.existsById(id))
              {
                  productRepository.deleteById(id);
                  count --;
              }


          }
          if(count == 0)
          {
              return  true;
          }


      return false;

    }
    public Long count() {

        return productRepository.count();
    }
}
