package com.cellphones10.controller.admin;



import com.cellphones10.dto.ProductDTO;
import com.cellphones10.dto.RateDTO;
import com.cellphones10.dto.ReviewDTO;
import com.cellphones10.dto.output.Output;
import com.cellphones10.service.impl.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
    @RequestMapping("/api/admin/review")
public class ReviewController {
    @Autowired private ReviewService reviewService;
    @PostMapping("/add")
    public ReviewDTO addReview(@RequestBody ReviewDTO reviewDTO, @CurrentSecurityContext(expression = "authentication?.name")String username)
    {
        return  reviewService.save(reviewDTO, username);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteReview(@PathVariable Long id)
    {
        if(reviewService.delete(id))
        {
            return new ResponseEntity(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping()
    public Output<ReviewDTO> getAllByProduct(@RequestParam("page") int page, @RequestParam("limit") int limit, @RequestParam("id") Long productId) {
        Pageable pageable = PageRequest.of(page - 1, limit);

        Output<ReviewDTO> output = new Output<>();
        output.setListResult(reviewService.findAllByProduct(pageable, productId));
        Long count = reviewService.count(productId);
        output.setTotalPage((long)Math.ceil((double)count / limit));
        output.setPage(page);
        Integer remainpage = (int) (count - (page * limit));
        if (remainpage < 0) {
            remainpage = 0;
        }
        output.setRemainingproduct(remainpage);
        return  output;

    }

}
