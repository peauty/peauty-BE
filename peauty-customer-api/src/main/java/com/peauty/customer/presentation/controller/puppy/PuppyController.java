package com.peauty.customer.presentation.controller.puppy;

import com.peauty.customer.business.puppy.PuppyService;
import com.peauty.customer.business.puppy.dto.*;
import com.peauty.customer.presentation.controller.puppy.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "Puppy", description = "Puppy API")
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class PuppyController {

    private final PuppyService puppyService;

    // 반려견 프로필 이미지 업로드
    @PostMapping(value = "/{userId}/puppies/{puppyId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "반려견 프로필 이미지 업로드", description = "반려견의 프로필 이미지 업로드 API 진입점입니다.")
    public UploadPuppyImageResponse uploadPuppyImage(@PathVariable Long userId, @PathVariable Long puppyId, @RequestParam("image") MultipartFile image) {
        UploadPuppyImageResult result = puppyService.uploadPuppyImage(userId, puppyId, image);
        return UploadPuppyImageResponse.from(result);
    }

    // 반려견 등록 API
    @PostMapping("/{userId}/puppies")
    @Operation(summary = "반려견 등록", description = "반려견을 등록하는 API 진입점입니다.")
    public RegisterPuppyResponse registerPuppy(@PathVariable Long userId, @RequestBody RegisterPuppyRequest req){
        RegisterPuppyCommand command = req.toCommand(userId);
        RegisterPuppyResult result = puppyService.registerPuppy(command);
        return RegisterPuppyResponse.from(result);
    }

    // 반려견 조회 API
    @GetMapping("/{userId}/puppies/{puppyId}")
    @Operation(summary = "반려견 조회", description = "반려견을 조회하는 API 진입점입니다.")
    public GetPuppyDetailResponse getPuppyDetail(@PathVariable Long userId, @PathVariable Long puppyId){
        GetPuppyDetailResult result = puppyService.getPuppyDetail(userId, puppyId);
        return GetPuppyDetailResponse.from(result);
    }

    // 반려견 수정 API
    @PutMapping("/{userId}/puppies/{puppyId}")
    @Operation(summary = "반려견 수정", description = "반려견을 수정하는 API 진입점입니다.")
    public UpdatePuppyDetailResponse updatePuppyDetail(@PathVariable Long userId,
                                                       @PathVariable Long puppyId,
                                                       @RequestBody UpdatePuppyDetailRequest request){
        UpdatePuppyDetailResult result = puppyService.updatePuppyDetail(userId, puppyId, request.toCommand());
        return UpdatePuppyDetailResponse.from(result);
    }

    // 반려견 삭제 API
    @DeleteMapping("/{userId}/puppies/{puppyId}")
    @Operation(summary = "반려견 삭제", description = "반려견을 삭제합니다.")
    public DeletePuppyResponse deletePuppy(
            @Parameter(description = "사용자 ID", example = "1") @PathVariable Long userId,
            @Parameter(description = "반려견 ID", example = "1") @PathVariable Long puppyId
    ) {
        puppyService.deletePuppy(userId, puppyId);
        return new DeletePuppyResponse("삭제되었습니다.");
    }

    // 반려견 전체 조회 API
    @GetMapping("/{userId}/puppies")
    @Operation(summary = "반려견 전체 조회", description = "고객 본인의 전체 반려견을 조회하는 API 진입점입니다.")
    public GetPuppyProfilesResponse getPuppyProfiles(@PathVariable Long userId) {
        GetPuppyProfilesResult result = puppyService.getPuppyProfiles(userId);
        return GetPuppyProfilesResponse.from(result);
    }

}