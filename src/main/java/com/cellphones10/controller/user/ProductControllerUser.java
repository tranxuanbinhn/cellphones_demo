package com.cellphones10.controller.user;

import com.cellphones10.dto.ProductDTO;
import com.cellphones10.dto.output.Output;
import com.cellphones10.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/user/product")
public class ProductControllerUser {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public List<ProductDTO> getNewProduct(@RequestParam Long categoryId)
    {
        return  productService.findTop5ProductByCategoryId(categoryId);
    }
    @GetMapping("/getall")
    public Output<ProductDTO> GetAll(@RequestParam("page") int page, @RequestParam("limit") int limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        Output<ProductDTO> output = new Output<>();
        output.setListResult(productService.findAll(pageable));
        Long count = productService.count();
        output.setTotalPage(count/limit);
        output.setPage(page);
        Integer remainpage =(int) (count-(page*limit));
        if(remainpage < 0)
        {
            remainpage = 0;
        }
        output.setRemainingproduct(remainpage);


        return output;

    }
    @GetMapping("/detail/{id}")
    public ProductDTO getDetailProduct(@PathVariable Long id)
    {
        return  productService.getProductById(id);
    }

    @GetMapping("/filterbyprice")
    public Output<ProductDTO> getProductsByFilterPrice(@RequestParam BigDecimal minprice, BigDecimal maxprice, Integer page, Integer limit)
    {
        Integer pageset = page - 1;
        Output<ProductDTO> output = new Output<>();
        List<ProductDTO> list = productService.filterProductByPrice(minprice, maxprice, limit,pageset);
        output.setListResult(list);
        output.setPage(page);
//        findProductByCategoryCode
        Long count = productService.countProduct(list);
        Integer remainpage =(int) (count-(page*limit));
        if(remainpage < 0)
        {
            remainpage = 0;
        }
        output.setRemainingproduct(remainpage);
        return output;
    }
    @GetMapping("/getallproductbycategory")
    public Output<ProductDTO> getProductsByCategory(@RequestParam String categorycode, Integer page, Integer limit)
    {

        Output<ProductDTO> output = new Output<>();
        List<ProductDTO> list = productService.findProductByCategoryCode(categorycode, limit,page);
        output.setListResult(list);
        output.setPage(page);
//        findProductByCategoryCode
        Long count = productService.countByCategoryCode(categorycode);
        Integer remainpage =(int) (count-(page*limit));
        if(remainpage < 0)
        {
            remainpage = 0;
        }
        output.setRemainingproduct(remainpage);
        return output;
    }
    @GetMapping("/fillterproduct")
    public Output<ProductDTO> filterProduct(@RequestParam String categorycode, Integer page, Integer limit, String orderby, Integer dir)
    {
        Output<ProductDTO> output = new Output<>();
        List<ProductDTO> list = productService.findProductOrderBy(categorycode, limit,page, orderby,dir);
        output.setListResult(list);
        output.setPage(page);
//        findProductByCategoryCode
        Long count = productService.countByCategoryCode(categorycode);
        Integer remainpage =(int) (count-(page*limit));
        if(remainpage < 0)
        {
            remainpage = 0;
        }
        output.setRemainingproduct(remainpage);
        return output;
    }
    @GetMapping("/fillterall")
    public Output<ProductDTO> filterAllProduct(@RequestParam Integer page, Integer limit, String orderby, Integer dir)
    {
        Output<ProductDTO> output = new Output<>();
        List<ProductDTO> list = productService.filterAll(limit,page, orderby,dir);
        output.setListResult(list);
        output.setPage(page);
//        findProductByCategoryCode
        Long count = productService.count();
        Integer remainpage =(int) (count-(page*limit));
        if(remainpage < 0)
        {
            remainpage = 0;
        }
        output.setRemainingproduct(remainpage);
        return output;
    }
    @GetMapping("/search")
    public Output<ProductDTO> searchByProductName(@RequestParam String name, Integer page, Integer limit)
    {
        Output<ProductDTO> output = new Output<>();
        if(name.isEmpty())
        {
            return  null;
        }
        List<ProductDTO> list = productService.searchProductName(name, page,limit);
        output.setListResult(list);
        output.setPage(page);
//        findProductByCategoryCode
        Long count = productService.countProductSearch(name);
        Integer remainpage =(int) (count-(page*limit));
        if(remainpage < 0)
        {
            remainpage = 0;
        }
        output.setRemainingproduct(remainpage);
        return output;
    }



}
