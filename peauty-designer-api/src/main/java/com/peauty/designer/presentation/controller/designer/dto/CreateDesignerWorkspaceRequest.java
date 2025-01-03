package com.peauty.designer.presentation.controller.designer.dto;

import com.peauty.designer.business.designer.dto.CreateDesignerWorkspaceCommand;
import com.peauty.domain.designer.PaymentOption;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CreateDesignerWorkspaceRequest(

        @Schema(description = "배너 이미지 URL", example = "[\"이미지 url1\", \"이미지 url2\"]")
        List<String> bannerImageUrls,

        @Schema(description = "가게 이름", example = "호키포키 살롱")
        String workspaceName,

        @Schema(description = "소개 제목", example = "안녕하세요. 말티즈 및 푸들 모발 케어 호키포키입니다.")
        String introduceTitle,

        @Schema(description = "소개 내용", example = "저희 호키 포키는 ~~ ")
        String introduce,

        @Schema(description = "공지 제목", example = "호키포키의 5주년 공지글")
        String noticeTitle,

        @Schema(description = "공지 내용", example = "저희 호키포키에서 5주년 이벤트로 예약 자에 한에서 반려견 피부 보습 케어..")
        String notice,

        @Schema(description = "주소", example = "성남시 위례구")
        String address,

        @Schema(description = "상세 주소", example = "사미동 대상 베르힐 힐스테이트 1602호")
        String addressDetail,

        @Schema(description = "경력 연수", example = "5")
        Integer yearOfExperience,

        @Schema(description = "자격증 이미지 URL 목록", example = "[\"자격증 이미지 url1\", \"자격증 이미지 url2\"]")
        List<String> licenses,

        @Schema(description = "결제 방식 목록", example = "[\"ACCOUNT\", \"CARD\"]")
        List<PaymentOption> paymentOptions,

        @Schema(description = "영업 시작 시간", example = "10:00")
        String openHours,

        @Schema(description = "영업 종료 시간", example = "20:00")
        String closeHours,

        @Schema(description = "영업일", example = "주말휴무")
        String openDays,

        @Schema(description = "방향 안내", example = "방향 안내 내용")
        String directionGuide,

        @Schema(description = "전화번호", example = "010-1234-1234")
        String phoneNumber
) {
    public CreateDesignerWorkspaceCommand toCommand() {
        return new CreateDesignerWorkspaceCommand(
                this.bannerImageUrls,
                this.workspaceName,
                this.introduceTitle,
                this.introduce,
                this.noticeTitle,
                this.notice,
                this.address,
                this.addressDetail,
                this.yearOfExperience,
                this.licenses,
                this.paymentOptions,
                this.openHours,
                this.closeHours,
                this.openDays,
                this.directionGuide,
                this.phoneNumber
        );
    }
}
