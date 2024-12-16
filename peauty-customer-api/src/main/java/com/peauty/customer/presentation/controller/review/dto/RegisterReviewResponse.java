package com.peauty.customer.presentation.controller.review.dto;

import com.peauty.customer.business.review.dto.RegisterReviewResult;

import java.util.List;

public record RegisterReviewResponse(
        Long reviewId,
        Long biddingThreadId,
        Double reviewRating,
        String contentDetail,
        List<String> contentGenerals
) {
    public static RegisterReviewResponse from(RegisterReviewResult result) {
        return new RegisterReviewResponse(
                result.reviewId().value(),
                result.biddingThreadId().value(),
                result.reviewRating(),
                result.contentDetail(),
                result.contentGenerals()
        );
    }
}
