package com.cellphones10.controller.admin;

import com.cellphones10.dto.BrandDTO;
import com.cellphones10.dto.ProductDTO;
import com.cellphones10.dto.output.Output;
import com.cellphones10.service.impl.BrandService;
import com.cellphones10.service.impl.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public Output<ProductDTO> GetAll(@RequestParam("page") int page, @RequestParam("limit") int limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        Output<ProductDTO> output = new Output<>();
        output.setListResult(productService.findAll(pageable));
        output.setTotalPage(productService.count()/limit);
        output.setPage(page);
        Long count = productService.count();
        output.setTotalItems(count);
        Integer remainpage =(int) (count-(page*limit));
        if(remainpage < 0)
        {
            remainpage = 0;
        }
        output.setRemainingproduct(remainpage);

        return output;

    }

    @PostMapping()
    public ProductDTO AddBrand(@RequestParam("productName")String productName,
                               @RequestParam("description")String description,
                               @RequestParam("size")String size,
                                @RequestParam("color")String color,
                               @RequestParam("price") BigDecimal price,
                               @RequestParam("weight") Integer weight,
                               @RequestParam("height") Integer height,
                               @RequestParam("length") Integer length,
                               @RequestParam("width") Integer width,
                               @RequestParam(value = "file", required = false) MultipartFile file,
                               @RequestParam("screensize") Integer screensize,
                               @RequestParam("screentech") String screentech,
                               @RequestParam("ramstorage") String ramstorage,
                               @RequestParam("internalmemory") String internalmemory,
                               @RequestParam("os") String os,
                               @RequestParam("brandName") String brandName,
                               @RequestParam("categoryName") String categoryName
    )
    {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(productName);
        productDTO.setBrandName(brandName);
        productDTO.setColor(color);
        productDTO.setCategoryName(categoryName);
        productDTO.setDescription(description);
        productDTO.setHeight(height);
        productDTO.setOs(os);
        productDTO.setInternalmemory(internalmemory);
        productDTO.setRamstorage(ramstorage);
        productDTO.setScreentech(screentech);
        productDTO.setScreensize(screensize);
        productDTO.setWidth(width);
        productDTO.setLength(length);
        productDTO.setWeight(weight);
        productDTO.setSize(size);
        productDTO.setPrice(price);
        return productService.save(productDTO, file);

    }
    @PutMapping("/{id}")
    public ProductDTO UpdateBrand(@RequestParam("productName")String productName,
                                  @RequestParam("description")String description,
                                  @RequestParam("size")String size,
                                  @RequestParam("color")String color,
                                  @RequestParam("price") BigDecimal price,
                                  @RequestParam("weight") Integer weight,
                                  @RequestParam("height") Integer height,
                                  @RequestParam("length") Integer length,
                                  @RequestParam("width") Integer width,
                                  @RequestParam(value = "file", required = false) MultipartFile file,
                                  @RequestParam("screensize") Integer screensize,
                                  @RequestParam("screentech") String screentech,
                                  @RequestParam("ramstorage") String ramstorage,
                                  @RequestParam("internalmemory") String internalmemory,
                                  @RequestParam("os") String os,
                                  @RequestParam("brandName") String brandName,
                                  @RequestParam("categoryName") String categoryName,
                                  @PathVariable Long id)
    {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(productName);
        productDTO.setBrandName(brandName);
        productDTO.setColor(color);
        productDTO.setCategoryName(categoryName);
        productDTO.setDescription(description);
        productDTO.setHeight(height);
        productDTO.setOs(os);
        productDTO.setInternalmemory(internalmemory);
        productDTO.setRamstorage(ramstorage);
        productDTO.setScreentech(screentech);
        productDTO.setScreensize(screensize);
        productDTO.setWidth(width);
        productDTO.setLength(length);
        productDTO.setWeight(weight);
        productDTO.setSize(size);
        productDTO.setPrice(price);
        productDTO.setId(id);
        ProductDTO brandSaved = productService.save(productDTO, file);
        return  brandSaved;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteProduct(@PathVariable Long id)
    {
        boolean result= productService.delete(id);
        if(result == true)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.internalServerError().build();
    }

}
