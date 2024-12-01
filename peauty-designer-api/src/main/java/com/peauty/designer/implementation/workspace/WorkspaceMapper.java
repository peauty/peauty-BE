package com.peauty.designer.implementation.workspace;

import com.peauty.domain.designer.Rating;
import com.peauty.domain.designer.Workspace;
import com.peauty.persistence.designer.RatingEntity;
import com.peauty.persistence.designer.WorkspaceEntity;

import java.util.Optional;

public class WorkspaceMapper {
    public static WorkspaceEntity toEntity(Workspace workspace, Long designerId) {
        return WorkspaceEntity.builder()
                .id(workspace.getWorkspaceId())
                .designerId(designerId)
                .noticeTitle(workspace.getNoticeTitle())
                .notice(workspace.getNotice())
                .introduceTitle(workspace.getIntroduceTitle())
                .introduce(workspace.getIntroduce())
                .workspaceName(workspace.getWorkspaceName())
                .bannerImageUrl(workspace.getBannerImageUrl())
                .openHours(workspace.getOpenHours())
                .closeHours(workspace.getCloseHours())
                .openDays(workspace.getOpenDays())
                // TODO: List PaymentOption을 리스트로 받고 있는데 테이블로 나눌 것인지 어떻게 할 것인지 확인하기
                .paymentOptions(workspace.getPaymentOptions())
                .directionGuide(workspace.getDirectionGuide())
                .reviewCount(Optional.ofNullable(workspace.getReviewCount())
                                .orElse(0))
                .reviewRating(Optional.ofNullable(workspace.getReviewRating())
                                .orElse(0.0))
                .build();
    }

    public static Workspace toDomain(WorkspaceEntity workspaceEntity) {
        return Workspace.builder()
                .workspaceId(workspaceEntity.getId())
                .noticeTitle(workspaceEntity.getNoticeTitle())
                .notice(workspaceEntity.getNotice())
                .introduceTitle(workspaceEntity.getIntroduceTitle())
                .introduce(workspaceEntity.getIntroduce())
                .workspaceName(workspaceEntity.getWorkspaceName())
                .bannerImageUrl(workspaceEntity.getBannerImageUrl())
                .openHours(workspaceEntity.getOpenHours())
                .closeHours(workspaceEntity.getCloseHours())
                .openDays(workspaceEntity.getOpenDays())
                .paymentOptions(workspaceEntity.getPaymentOptions()) // String 형태라면 파싱 로직이 필요할 수 있음
                .directionGuide(workspaceEntity.getDirectionGuide())
                .reviewCount(workspaceEntity.getReviewCount())
                .reviewRating(workspaceEntity.getReviewRating())
                .build();
    }

    public static Rating toRatingDomain(RatingEntity rating) {
        return Rating.builder()
                .ratingId(rating.getId())
                .totalScore(rating.getTotalScore())
                .scissor(rating.getScissor())
                .build();
    }
}
