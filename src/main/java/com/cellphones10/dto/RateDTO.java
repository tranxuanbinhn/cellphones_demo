package com.cellphones10.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateDTO {
    private Long productId;
    private double average;
    private double oneStar;
    private double twoStar;
    private double threeStar;
    private double fourStar;
    private double fiveStar;

    public RateDTO() {

    }
}
