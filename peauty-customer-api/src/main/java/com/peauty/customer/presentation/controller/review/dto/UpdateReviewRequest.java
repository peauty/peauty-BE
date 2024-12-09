package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.UpdateReviewCommand;
import com.peauty.domain.review.ContentGeneral;
import com.peauty.domain.review.ReviewImage;
import com.peauty.domain.review.ReviewRating;

import java.util.List;

public record UpdateReviewRequest(
        ReviewRating reviewRating,
        String contentDetail,
        ContentGeneral contentGeneral,
        List<ReviewImage> reviewImages
) {

    public UpdateReviewCommand toCommand() {
        return UpdateReviewCommand.builder()
                .reviewRating(reviewRating)
                .contentDetail(contentDetail)
                .contentGeneral(contentGeneral)
                .reviewImages(reviewImages)
                .build();
    }
}