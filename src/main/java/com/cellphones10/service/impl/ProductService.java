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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private CloudinaryService cloudinaryService;


    @Transactional
    public ProductDTO save(ProductDTO productDTO, MultipartFile file) throws InternalException {

        ProductEntity productEntity = new ProductEntity();
        if(productDTO.getId()!=null)
        {
            productEntity = productRepository.findById(productDTO.getId()).get();
            productDTO.setImage(productEntity.getImage());
        }
        if(file!=null)
        {
            Map dataImage = cloudinaryService.upload(file);
            productDTO.setImage(dataImage.get("url").toString());
        }

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
    public ProductDTO save(ProductDTO productDTO) {
        return null;
    }

    @Override
    public List<ProductDTO> findAll(Pageable pageable) {
        List<ProductEntity> productEntities = productRepository.findAll(pageable).getContent();
        List<ProductDTO> productDto = new ArrayList<>();
        for (ProductEntity productEntity:productEntities) {
            ProductDTO productDTO= mapper.map(productEntity, ProductDTO.class);
            productDTO.setBrandName(productEntity.getBrand().getBrandName());
            productDTO.setCategoryName(productEntity.getCategory().getCategoryName());
            productDto.add(productDTO);
        }

        return productDto;
    }

    @Override
    public boolean delete(List<Long> list) {
        return false;
    }

    public List<ProductDTO> findAll() {
        List<ProductEntity> productEntities = productRepository.findAll();
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

        result.setBrandName(productEntity.get().getBrand().getBrandName());
        result.setCategoryName(productEntity.get().getCategory().getCategoryName());

        return  result;
    }


    @Transactional
    public boolean delete(Long id) throws RuntimeException{

         try{
             productRepository.deleteById(id);
             return true;
         }
         catch (RuntimeException runtimeException)
         {
                return false;
         }



    }
    public List<ProductDTO> findTop5ProductByCategoryId(Long categoryId)
    {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<ProductEntity> categoryEntities = productRepository.findTop5ProdcutEntityByCategoryId(categoryId);
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
    public List<ProductDTO> searchProductName(String productName, Integer page, Integer limit){
       Pageable pageable = PageRequest.of((page-1), limit);
       Page<ProductEntity>  productEntities= productRepository.findByProductName(productName, pageable);
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

    public List<ProductDTO> findProductOrderBy(String categoryCode, Integer limit, Integer page, String order, Integer dir)
    {
        Integer offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(order).ascending());;
        if(dir==1)
        {
             pageable = PageRequest.of(offset, limit, Sort.by(order).descending());
        }


        List<ProductEntity> productEntities = productRepository.findByCategoryCategoryCode(categoryCode, pageable).getContent();
        List<ProductDTO> productDTOS = new ArrayList<>();
        productEntities.stream().forEach(productEntity -> {productDTOS.add(mapper.map(productEntity,ProductDTO.class));
        });
        return  productDTOS;
    }
    public List<ProductDTO> filterAll(Integer limit, Integer page, String order, Integer dir)
    {
        Integer offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(order).ascending());;
        if(dir==1)
        {
            pageable = PageRequest.of(offset, limit, Sort.by(order).descending());
        }


        List<ProductEntity> productEntities = productRepository.findAll(pageable).getContent();
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
    public  Long countProductSearch(String productName)
    {
        return productRepository.countFindByProductName(productName);
    }
}
