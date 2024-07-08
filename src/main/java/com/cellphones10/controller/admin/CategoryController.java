package com.cellphones10.controller.admin;

import com.cellphones10.dto.CategoryDTO;
import com.cellphones10.dto.output.Output;
import com.cellphones10.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO)
    {
        CategoryDTO categoryDTOSaved = categoryService.save(categoryDTO);
        return  ResponseEntity.ok(categoryDTOSaved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,@RequestBody CategoryDTO categoryDTO)
    {
        categoryDTO.setId(id);
        CategoryDTO categoryDTOSaved = categoryService.save(categoryDTO);
        return  ResponseEntity.ok(categoryDTOSaved);
    }
    @GetMapping()
    public ResponseEntity<Output> getAllCategory(@PathParam("page") Integer page, @PathParam("limit") Integer limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        Output output = new Output();
        output.setPage(page);
        output.setTotalPage(categoryService.Count()/limit);
        output.setListResult(categoryService.findAll(pageable));
        return ResponseEntity.ok(output);

    }
    @GetMapping("/getall")
    public ResponseEntity<Output> getAllCategory()
    {

        Output output = new Output();

        output.setListResult(categoryService.findAll());
        return ResponseEntity.ok(output);

    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategory(@RequestBody List<Long> ids)
    {
        if(categoryService.delete(ids))
        {
            return  ResponseEntity.ok("Success");
        }
          return  ResponseEntity.internalServerError().build();
    }

}
