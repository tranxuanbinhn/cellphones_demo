package com.cellphones10.controller.admin;

import com.cellphones10.dto.BrandDTO;
import com.cellphones10.dto.output.Output;
import com.cellphones10.service.impl.BrandService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/test/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping()
    public Output<BrandDTO> GetAll(@RequestParam("page") int page, @RequestParam("limit") int limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        Output<BrandDTO> output = new Output<>();
        output.setListResult(brandService.findAll(pageable));
        output.setTotalPage(brandService.CountPage()/limit);
        output.setPage(page);


        return output;

    }

    @PostMapping("/add")
    public BrandDTO AddBrand(@RequestBody BrandDTO brandDTO)
    {

        return brandService.save(brandDTO);

    }
    @PutMapping("/update/{id}")
    public BrandDTO UpdateBrand(@Valid @PathVariable("id") Long id, @RequestBody BrandDTO brandDTO)
    {

        brandDTO.setId(id);
       BrandDTO brandSaved = brandService.save(brandDTO);
        return  brandSaved;

    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> DeleteBrand(@RequestBody List<Long> ids)
    {
        boolean result= brandService.delete(ids);
        if(result == true)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.internalServerError().build();
    }
}
