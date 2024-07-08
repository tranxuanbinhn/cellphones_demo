package com.cellphones10.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ReviewDTO extends AbstractDTO{
    private  String comment;
    private  String image;
    private  Integer rate;
    private Long userId;
    private String userName;
    private Long productId;


    public ReviewDTO(String comment, String image, Integer rate, Long userId, String userName, Long productId) {
        this.comment = comment;
        this.image = image;
        this.rate = rate;
        this.userId = userId;
        this.userName = userName;
        this.productId = productId;
    }

    public ReviewDTO() {

    }
}
