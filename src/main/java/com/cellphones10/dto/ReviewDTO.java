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
    private List<Long> productIds;

    public ReviewDTO(String comment, String image, Integer rate, List<Long> productIds) {
        this.comment = comment;
        this.image = image;
        this.rate = rate;
        this.productIds = productIds;
    }

    public ReviewDTO() {

    }
}
