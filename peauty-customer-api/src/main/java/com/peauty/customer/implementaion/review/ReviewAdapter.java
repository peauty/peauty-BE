package com.peauty.customer.implementaion.review;

import com.peauty.customer.business.review.ReviewPort;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.review.Review;
import com.peauty.persistence.bidding.estimate.EstimateProposalEntity;
import com.peauty.persistence.bidding.estimate.EstimateProposalRepository;
import com.peauty.persistence.bidding.thread.BiddingThreadEntity;
import com.peauty.persistence.bidding.thread.BiddingThreadRepository;
import com.peauty.persistence.customer.CustomerEntity;
import com.peauty.persistence.customer.CustomerRepository;
import com.peauty.persistence.review.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReviewAdapter implements ReviewPort {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final BiddingThreadRepository biddingThreadRepository;
    private final EstimateProposalRepository estimateProposalRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Review registerNewReview(Review review) {
        ReviewEntity registerReviewEntity = reviewRepository.save(
                ReviewMapper.toReviewEntity(review)
        );
        List<ReviewImageEntity> reviewImageEntities = review.getReviewImages().stream()
                .map(image -> ReviewMapper.toReviewImageEntity(image, registerReviewEntity))
                .toList();
        List<ReviewImageEntity> registerReviewImageEntities = reviewImageRepository.saveAll(reviewImageEntities);
        return ReviewMapper.toReviewDomain(registerReviewEntity, registerReviewImageEntities);
    }

    @Override
    public Review findReviewById(Long reviewId) {
        ReviewEntity reviewEntity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_REVIEW));
        List<ReviewImageEntity> reviewImageEntities = reviewImageRepository.findAllByReviewId(reviewId);
        return ReviewMapper.toReviewDomain(reviewEntity, reviewImageEntities);
    }

    @Override
    public Review saveReview(Review review) {
        ReviewEntity updatedReviewEntity = reviewRepository.save(
                ReviewMapper.toReviewEntity(review)
        );
        List<ReviewImageEntity> updatedReviewImageEntities = review.getReviewImages().stream()
                .map(image -> ReviewMapper.toReviewImageEntity(image, updatedReviewEntity))
                .toList();
        reviewImageRepository.saveAll(updatedReviewImageEntities);
        return ReviewMapper.toReviewDomain(updatedReviewEntity, updatedReviewImageEntities);
    }

    @Override
    @Transactional
    public void deleteReviewById(Long reviewId) {
        // 1. 연결된 ReviewImage 삭제
        List<ReviewImageEntity> reviewImages = reviewImageRepository.findAllByReviewId(reviewId);
        reviewImageRepository.deleteAll(reviewImages);
        // 2. Review 삭제
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public Review getReviewByIdAndBiddingThreadId(Long id, Long biddingThreadId) {
        // 리뷰 조회
        ReviewEntity reviewEntity = reviewRepository.findByIdAndBiddingThreadId(id, biddingThreadId)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_FOUND_REVIEW));
        return ReviewMapper.toReviewDomain(reviewEntity, reviewImageRepository.findAllByReviewId(id));

    }

    @Override
    public Boolean existsByBiddingThreadId(Long biddingThreadId) {
        return reviewRepository.existsByBiddingThreadId(biddingThreadId);
    }

/*  TODO: Review 전체 조회(간단 조회: Only 리뷰)
    @Override
    public List<Review> findReviewsByDesignerId(Long designerId) {
        List<ReviewEntity> reviewEntities = reviewRepository.findReviewsByDesignerId(designerId);
        return reviewEntities.stream()
                .map(entity -> {
                    List<ReviewImageEntity> imageEntities = reviewImageRepository.findAllByReviewId(entity.getId());
                    return ReviewMapper.toReviewDomain(entity, imageEntities);
                })
                .toList();
    }*/

//    Review 전체 조회(고객 닉네임, 컷 종류까지 조회)
@Override
public List<Review> findReviewsByDesignerId(Long designerId) {
    List<BiddingThreadEntity> threads = biddingThreadRepository.findAllByDesignerIdWithBiddingProcess(designerId);
    Set<Long> processIds = threads.stream()
            .map(thread -> thread.getBiddingProcess().getId())
            .collect(Collectors.toSet());

    List<EstimateProposalEntity> proposals = estimateProposalRepository.findByProcessIdIn(processIds);

    return threads.stream()
            .map(thread -> {
                Optional<ReviewEntity> reviewEntityOpt = reviewRepository.findByBiddingThreadId(thread.getId());
                if (reviewEntityOpt.isEmpty()) {return null;}
                ReviewEntity reviewEntity = reviewEntityOpt.get();

                EstimateProposalEntity proposal = proposals.stream()
                        .filter(p -> p.getProcessId().equals(thread.getBiddingProcess().getId()))
                        .findFirst()
                        .orElse(null);

                CustomerEntity customerEntity = customerRepository.findByPuppyId(thread.getBiddingProcess().getPuppyId())
                        .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));

                return ReviewMapper.toReviewDomain(
                        reviewEntity,
                        customerEntity.getNickname(),
                        proposal,
                        reviewImageRepository.findAllByReviewId(reviewEntity.getId())
                );
            })
            .filter(Objects::nonNull)
            .toList();
}


}
