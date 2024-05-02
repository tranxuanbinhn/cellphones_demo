package com.cellphones10.controller.user;


import com.cellphones10.dto.BrandDTO;
import com.cellphones10.dto.output.Output;
import com.cellphones10.service.impl.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/user/brand")
public class BrandControllerUser {
    @Autowired
    private BrandService brandService;
    @GetMapping("/getall")
    public Output<BrandDTO> GetAll(@RequestParam("page") int page, @RequestParam("limit") int limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        Output<BrandDTO> output = new Output<>();
        output.setListResult(brandService.findAll(pageable));
        output.setTotalPage(brandService.CountPage()/limit);
        output.setPage(page);


        return output;

    }
}
