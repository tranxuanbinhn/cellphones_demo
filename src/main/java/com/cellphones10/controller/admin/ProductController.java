package com.cellphones10.controller.admin;

import com.cellphones10.dto.BrandDTO;
import com.cellphones10.dto.ProductDTO;
import com.cellphones10.dto.output.Output;
import com.cellphones10.service.impl.BrandService;
import com.cellphones10.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
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
    public ProductDTO AddBrand(@RequestBody ProductDTO productDTO)
    {

        return productService.save(productDTO);

    }
    @PutMapping("/{id}")
    public ProductDTO UpdateBrand(@Valid @PathVariable("id") Long id, @RequestBody ProductDTO productDTO)
    {

        productDTO.setId(id);
        ProductDTO brandSaved = productService.save(productDTO);
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
