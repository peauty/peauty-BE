package com.peauty.customer.implementaion.workspace;

import com.peauty.customer.business.workspace.WorkspacePort;
import com.peauty.customer.implementaion.customer.CustomerMapper;
import com.peauty.domain.designer.*;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.persistence.designer.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkspaceAdapter implements WorkspacePort {

    private final DesignerRepository designerRepository;
    private final WorkspaceRepository workspaceRepository;
    private final BadgeRepository badgeRepository;
    private final DesignerBadgeRepository designerBadgeRepository;
    private final RatingRepository ratingRepository;

    @Override
    public List<Workspace> findAllWorkspaceByAddress(String baseAddress) {
        return workspaceRepository.findByBaseAddress(baseAddress)
                .stream()
                .map(workspaceEntity -> {
                    Rating rating = getRatingByWorkspaceId(workspaceEntity.getId());
                    return CustomerMapper.toWorkspaceDomain(workspaceEntity, rating); // Rating 포함
                })
                .toList();
    }
/*  TODO: 뱃지 붙이기 전의 findDesignerById
    @Override
    public Designer findDesignerById(Long designerId) {
        return designerRepository.findById(designerId)
                .map(CustomerMapper::toDesignerDomain)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_DESIGNER));
    }*/
@Override
public Designer findDesignerById(Long designerId) {
    return designerRepository.findById(designerId)
            .map(entity -> {
                Designer designer = CustomerMapper.toDesignerDomain(entity);

                // DesignerBadgeEntity에서 대표 뱃지를 조회
                List<Badge> badges = designerBadgeRepository.findAllByDesignerIdAndIsRepresentativeBadge(designerId, true)
                        .stream()
                        .map(designerBadge -> {
                            BadgeEntity badgeEntity = badgeRepository.findById(designerBadge.getBadgeId())
                                    .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_DESIGNER));
                            return CustomerMapper.toBadgeDomain(badgeEntity);
                        })
                        .toList();

                designer.updateBadges(badges); // 대표 뱃지 업데이트
                return designer;
            })
            .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_DESIGNER));
            }

    @Override
    public Rating getRatingByWorkspaceId(Long workspaceId) {
        return ratingRepository.findRatingByWorkspaceId(workspaceId)
                .map(CustomerMapper::toRatingDomain)
                .orElseGet(() -> Rating.builder()
                        .scissors(Scissors.NONE)
                        .build());
    }


}
