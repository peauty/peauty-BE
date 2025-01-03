package com.peauty.customer.presentation.controller.review;

import com.peauty.customer.business.bidding.dto.GetAllStep3AboveThreadsResult;
import com.peauty.customer.business.review.ReviewService;
import com.peauty.customer.business.review.dto.*;
import com.peauty.customer.presentation.controller.bidding.dto.GetAllStep3AboveThreadsResponse;
import com.peauty.customer.presentation.controller.review.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Review", description = "Review API")
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/users/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads/{threadId}/reviews")
    @Operation(summary = "리뷰 작성", description = "고객이 자신의 강아지를 미용한 디자이너에게 리뷰를 작성하는 API 진입점입니다.")
    public RegisterReviewResponse registerReview(@PathVariable Long userId,
                                                 @PathVariable Long puppyId,
                                                 @PathVariable Long processId,
                                                 @PathVariable Long threadId,
                                                 @RequestBody RegisterReviewRequest request) {
        RegisterReviewResult result = reviewService.registerReview(
                userId,
                puppyId,
                processId,
                threadId,
                request.toCommand());
        return RegisterReviewResponse.from(result);
    }

    /* TODO: 추후 @PutMapping("/users/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads/{threadId}/reviews/{reviewId}")
        현재 프론트 단에서 개발 기간 완료까지 빠듯하여 PathVariable을 다 두기엔 무리가 있다고 판단. Restful하게 만드는데 있어서는 위와 같은 uri가 맞기에
        프로젝트 이후 리팩토링 예정
    */
    @PutMapping("/users/{userId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "고객이 자신의 리뷰를 수정하는 API입니다.")
    public UpdateReviewResponse updateReview(@PathVariable Long userId,
                                             @PathVariable Long reviewId,
                                             @RequestBody UpdateReviewRequest request) {
        UpdateReviewResult result = reviewService.updateReview(
                userId,
                reviewId,
                request.toCommand()
        );
        return UpdateReviewResponse.from(result);
    }

    // TODO: 추후 @DeleteMapping("/users/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads/{threadId}/reviews/{reviewId}") 리팩토링 예정
    @DeleteMapping("/users/{userId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "고객이 자신의 강아지를 미용한 디자이너에게 리뷰를 삭제하는 API 진입점입니다.")
    public DeleteReviewResponse deleteReview(@PathVariable Long userId,
                                             @PathVariable Long reviewId) {
        reviewService.deleteReview(userId, reviewId);
        return new DeleteReviewResponse("리뷰가 삭제되었습니다.");
    }

    @GetMapping("/users/{userId}/puppies/{puppyId}/bidding/processes/{processId}/threads/{threadId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 상세 조회", description = "고객이 자신이 작성한 리뷰를 상세 조회하는 API 진입점입니다.")
    public GetReviewDetailResponse getReviewDetail(@PathVariable Long userId,
                                                   @PathVariable Long puppyId,
                                                   @PathVariable Long processId,
                                                   @PathVariable Long threadId,
                                                   @PathVariable Long reviewId){
        GetReviewDetailResult result = reviewService.getReviewDetail(userId, puppyId, processId, threadId, reviewId);
        return GetReviewDetailResponse.from(result);
    }

    @GetMapping("/users/{designerId}/shop/reviews")
    @Operation(summary = "디자이너 리뷰 전체 조회", description = "디자이너의 리뷰를 조회하는 API 진입점입니다.")
    public GetDesignerReviewsResponse getDesignerReviews(@PathVariable Long designerId) {

        GetDesignerReviewsResult result = reviewService.getDesignerReviews(designerId);
        return GetDesignerReviewsResponse.from(result);
    }

    @GetMapping("/users/{userId}/reviews")
    @Operation(summary = "유저 리뷰 전체 조회", description = "유저의 리뷰를 전체 조회하는 API 진입점입니다.")
    public GetUserReviewsResponse getUserReviews(@PathVariable Long userId){
        GetUserReviewsResult result = reviewService.getCustomerReviews(userId);
        return GetUserReviewsResponse.from(result);
    }
}