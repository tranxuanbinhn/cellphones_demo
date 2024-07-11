package com.cellphones10.service.impl;


import com.cellphones10.dto.RateDTO;
import com.cellphones10.dto.ReviewDTO;
import com.cellphones10.entity.ProductEntity;
import com.cellphones10.entity.ReviewEntity;
import com.cellphones10.entity.User;
import com.cellphones10.repository.ProductRepository;
import com.cellphones10.repository.ReviewRepository;
import com.cellphones10.repository.UserRepository;
import com.cellphones10.service.IReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService {

    @Autowired private ReviewRepository reviewRepository;

    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private ModelMapper mapper;

    @Override
    public ReviewDTO save(ReviewDTO reviewDTO) {

        return null;
    }

    public ReviewDTO save(ReviewDTO reviewDTO, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent())
        {
            throw new RuntimeException("Not found user");

        }
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setUser(user.get());
        Optional<ProductEntity> productEntity = productRepository.findById(reviewDTO.getProductId());
        if(!productEntity.isPresent())
        {
            throw  new RuntimeException("Not found user");
        }
        reviewEntity.setProduct(productEntity.get());

        reviewEntity.setComment(reviewDTO.getComment());
        reviewEntity.setRate(reviewDTO.getRate());
        reviewEntity.setImage(reviewDTO.getImage());
        ReviewEntity reviewEntityResult  = reviewRepository.save(reviewEntity);
        ReviewDTO result =  mapper.map(reviewEntityResult, ReviewDTO.class);
        result.setUserName(reviewEntityResult.getUser().getUsername());
        return result;
    }

    @Override
    public List<ReviewDTO> findAll(Pageable pageable) {
        return null;
    }

    public List<ReviewDTO> findAllByProduct(Pageable pageable, Long productId) {
        List<ReviewEntity> list = reviewRepository.findAllByProductId(productId,pageable).getContent();
        List<ReviewDTO> result= new ArrayList<>();
        list.stream().forEach(reviewEntity -> {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setUserName(reviewEntity.getUser().getUsername());
            reviewDTO.setComment(reviewEntity.getComment());
            reviewDTO.setRate(reviewEntity.getRate());
            reviewDTO.setImage(reviewEntity.getImage());
            reviewDTO.setId(reviewEntity.getId());
            result.add(reviewDTO);

        });
        return result;
    }
    public Long count(Long productId) {
        return reviewRepository.countReviewBInProduct(productId);

    }
    public RateDTO handleRate(Long productID)
    {
        RateDTO rateDTO = new RateDTO();
        Long count = reviewRepository.countReviewBInProduct(productID);
        Double total = 0.0;
        Double oneRate = 0.0;
        Double twoRate = 0.0;
        Double threeRate = 0.0;
        Double fourRate = 0.0;
        Double fiveRate = 0.0;

        List<ReviewEntity> list = reviewRepository.findAllByProductId(productID);
       for (ReviewEntity reviewEntity:list)
       {
           total+=(double) reviewEntity.getRate();
           if(reviewEntity.getRate()==1)
           {
               oneRate+=1;
           }
           if(reviewEntity.getRate()==2)
           {
               twoRate+=1;
           }
           if(reviewEntity.getRate()==3)
           {
               threeRate+=1;
           }
           if(reviewEntity.getRate()==4)
           {
               fourRate+=1;
           }
           if(reviewEntity.getRate()==5)
           {
               fiveRate+=1;
           }

       }
       rateDTO.setOneStar(Math.round((oneRate/count)*100));
       rateDTO.setTwoStar(Math.round((twoRate/count)*100));
       rateDTO.setThreeStar(Math.round((threeRate/count)*100));
       rateDTO.setFourStar(Math.round((fourRate/count)*100));
       rateDTO.setFiveStar(Math.round((fiveRate/count)*100));
       rateDTO.setAverage(Math.round(total/count));
        return rateDTO;

    }
    @Override
    public boolean delete(List<Long> list) {
        try{
            for (Long id : list)
            {
                ReviewEntity reviewEntity = reviewRepository.findById(id).get();
                reviewRepository.delete(reviewEntity);
            }
            return true;
        }
        catch (RuntimeException runtimeException)
        {
            return  false;
        }
    }

    public boolean delete(Long id) {
        try{

                ReviewEntity reviewEntity = reviewRepository.findById(id).get();
                reviewRepository.delete(reviewEntity);

            return true;
        }
        catch (RuntimeException runtimeException)
        {
            return  false;
        }
    }
}
