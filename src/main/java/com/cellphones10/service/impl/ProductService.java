package com.cellphones10.service.impl;

import com.cellphones10.dto.CategoryDTO;
import com.cellphones10.dto.ProductDTO;
import com.cellphones10.entity.BrandEntity;
import com.cellphones10.entity.CategoryEntity;
import com.cellphones10.entity.ProductEntity;
import com.cellphones10.repository.BrandRepository;
import com.cellphones10.repository.CategoryRespository;
import com.cellphones10.repository.ProductRepository;
import com.cellphones10.service.IProductService;
import com.sun.jdi.InternalException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ProductDTO getProductById(Long id)
    {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if(productEntity.isEmpty())
        {
            throw  new RuntimeException();
        }
        ProductDTO result = mapper.map(productEntity.get(),ProductDTO.class);
        return  result;
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
    public List<ProductDTO> findTop10ProductByCategoryId(Long categoryId)
    {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<ProductEntity> categoryEntities = productRepository.findTop10ProdcutEntityByCategoryId(categoryId);
        categoryEntities.stream().forEach(categoryEntity -> {
            productDTOS.add(mapper.map(categoryEntity, ProductDTO.class));
        });
        return productDTOS;
    }
    public List<ProductDTO> filterProductByPrice(BigDecimal minPrice, BigDecimal maxPrice, Integer page, Integer limit){
            List<ProductEntity> productEntities = productRepository.filterProductByPrice(minPrice, maxPrice, page, limit);
            List<ProductDTO> productDTOS = new ArrayList<>();
        productEntities.stream().forEach(productEntity -> {
            productDTOS.add(mapper.map(productEntity, ProductDTO.class));

        });
        return  productDTOS;
    }



    public Long countProduct(List<ProductDTO> productDTOS){
        Long totalProduct = productDTOS.stream().count();
        return totalProduct;


    }
    public List<ProductDTO> findProductByCategoryCode(String categoryCode, Integer limit, Integer page)
    {
        Integer offset = (page-1) * limit;
        List<ProductEntity> productEntities = productRepository.findByCategoryCode(categoryCode, limit, offset);
        List<ProductDTO> productDTOS = new ArrayList<>();
        productEntities.stream().forEach(productEntity -> {productDTOS.add(mapper.map(productEntity,ProductDTO.class));
        });
        return  productDTOS;
    }

    public List<ProductDTO> findProductOrderBy(String categoryCode, Integer limit, Integer page, String order, String dir)
    {
        String p = "p."+order;
        Integer offset = (page-1) * limit;
        int d = 0;
        if(dir=="asc")
        {
            d = 0;
        }
        else  if(dir == "desc")
        {
            d=1;
        }
        List<ProductEntity> productEntities = productRepository.filterProduct(categoryCode, limit, offset,  p, d);
        List<ProductDTO> productDTOS = new ArrayList<>();
        productEntities.stream().forEach(productEntity -> {productDTOS.add(mapper.map(productEntity,ProductDTO.class));
        });
        return  productDTOS;
    }
    public Long countByCategoryCode(String categoryCode)
    {
        Long rs = productRepository.countProductByCategoryCode(categoryCode);
      return  rs;
    }

    public Long count() {

        return productRepository.count();
    }
}
