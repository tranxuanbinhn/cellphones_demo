package com.cellphones10.controller;

import com.cellphones10.dto.BrandDTO;
import com.cellphones10.dto.output.Output;
import com.cellphones10.service.impl.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("api/test/brand")
public class test {

}
