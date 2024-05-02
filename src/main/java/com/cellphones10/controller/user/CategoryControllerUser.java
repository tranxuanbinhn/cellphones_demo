package com.cellphones10.controller.user;

import com.cellphones10.dto.CategoryDTO;
import com.cellphones10.dto.output.Output;
import com.cellphones10.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/user/category")
public class CategoryControllerUser {
    @Autowired
    private CategoryService categoryService;
    @GetMapping()
    public List<CategoryDTO> getAllCategory()
    {
        List<CategoryDTO> result = new ArrayList<>();
        result = categoryService.findAll();
        return result;

    }


}
