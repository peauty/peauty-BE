package com.peauty.designer.presentation.controller.designer.dto;

import com.peauty.designer.business.designer.dto.GetDesignerProfileResult;

public record GetDesignerProfileResponse(
        Long designerId,
        String name,
        String nickname,
        String profileImageUrl,
        String email,
        String phoneNumber
) {

    public static GetDesignerProfileResponse from(GetDesignerProfileResult result) {
        return new GetDesignerProfileResponse(
                result.designerId(),
                result.name(),
                result.nickname(),
                result.profileImageUrl(),
                result.email(),
                result.phoneNumber()
        );
    }
}
