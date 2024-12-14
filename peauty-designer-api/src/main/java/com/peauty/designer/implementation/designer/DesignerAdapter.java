package com.peauty.designer.implementation.designer;

import com.peauty.designer.business.auth.dto.SignUpCommand;
import com.peauty.designer.business.designer.DesignerPort;
import com.peauty.domain.designer.Badge;
import com.peauty.domain.designer.Designer;
import com.peauty.domain.designer.License;
import com.peauty.domain.exception.PeautyException;
import com.peauty.domain.response.PeautyResponseCode;
import com.peauty.domain.user.Role;
import com.peauty.domain.user.Status;
import com.peauty.persistence.designer.*;
import com.peauty.persistence.designer.badge.*;
import com.peauty.persistence.designer.license.LicenseEntity;
import com.peauty.persistence.designer.license.LicenseRepository;
import com.peauty.persistence.designer.mapper.BadgeMapper;
import com.peauty.persistence.designer.mapper.DesignerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DesignerAdapter implements DesignerPort {

    private final DesignerRepository designerRepository;
    private final LicenseRepository licenseRepository;
    private final DesignerBadgeRepository designerBadgeRepository;
    private final BadgeRepository badgeRepository;

    @Override
    public void checkDesignerNicknameDuplicated(String nickname) {
        if (designerRepository.existsByNickname(nickname)) {
            throw new PeautyException(PeautyResponseCode.ALREADY_EXIST_USER);
        }
    }

    @Override
    public void checkDesignerPhoneNumDuplicated(String phoneNum) {
        if (designerRepository.existsByPhoneNumber(phoneNum)) {
            throw new PeautyException(PeautyResponseCode.ALREADY_EXIST_PHONE_NUMBER);
        }
    }

    @Override
    public Optional<Designer> findBySocialId(String socialId) {
        return Optional.ofNullable(designerRepository.findBySocialId(socialId))
                .orElse(Optional.empty())
                .map(DesignerMapper::toDesignerDomain);
    }

    @Override
    public Designer save(Designer designer) {
        DesignerEntity designerEntityToSave = DesignerMapper.toEntity(designer);
        List<LicenseEntity> licenseEntityToSave = DesignerMapper.toLicenseEntity(designer);

        DesignerEntity savedDesignerEntity = designerRepository.save(designerEntityToSave);
        List<LicenseEntity> savedLicenseEntity = licenseRepository.saveAll(licenseEntityToSave);

        return DesignerMapper.toDesignerAndLicenseDomain(savedDesignerEntity, savedLicenseEntity);
    }

    @Override
    public Designer registerNewDesigner(SignUpCommand command) {
        Designer designerToSave = Designer.builder()
                .designerId(0L)
                .socialId(command.socialId())
                .socialPlatform(command.socialPlatform())
                .name(command.name())
                .phoneNumber(command.phoneNumber())
                .email(command.email())
                .status(Status.ACTIVE)
                .role(Role.ROLE_DESIGNER)
                .nickname(command.nickname())
                .profileImageUrl(command.profileImageUrl())
                .build();
        return save(designerToSave);
    }

    @Override
    public Designer getByDesignerId(Long designerId) {
        return designerRepository.findById(designerId)
                .map(DesignerMapper::toDesignerDomain)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));
    }

    @Override
    public Designer getAllDesignerDataByDesignerId(Long userId) {
        Designer designer = designerRepository.findById(userId)
                .map(DesignerMapper::toDesignerDomain)
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_USER));

        List<License> licenses = Optional.ofNullable(licenseRepository.findByDesignerId(userId))
                .map(DesignerMapper::toLicenses)
                .orElse(Collections.emptyList());

        List<Badge> badges = getBadges(userId);

        designer.updateLicenses(licenses);
        designer.updateBadges(badges);
        return designer;
    }

    @Override
    public List<Badge> getBadges(Long userId) {
        List<Long> badgeIds = designerBadgeRepository.findRepresentativeBadgeIdsByDesignerId(userId);
        List<BadgeEntity> badgeEntities = badgeRepository.findAllById(badgeIds);
        return badgeEntities.stream()
                .map(badgeEntity -> Badge.builder()
                        .badgeId(badgeEntity.getId())
                        .badgeName(badgeEntity.getBadgeName())
                        .badgeContent(badgeEntity.getBadgeContent())
                        .badgeImageUrl(badgeEntity.getBadgeImageUrl())
                        .badgeColor(badgeEntity.getBadgeColor())
                        .isRepresentativeBadge(true)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Badge> getAllBadges() {
        return badgeRepository.findAll().stream()
                .map(badgeEntity -> Badge.builder()
                        .badgeId(badgeEntity.getId())
                        .badgeName(badgeEntity.getBadgeName())
                        .badgeContent(badgeEntity.getBadgeContent())
                        .badgeImageUrl(badgeEntity.getBadgeImageUrl())
                        .badgeType(badgeEntity.getBadgeType())
                        .badgeColor(badgeEntity.getBadgeColor())
                        .build())
                .toList();
    }

    @Override
    public List<Badge> getAcquiredBadges(Long userId) {
        List<DesignerBadgeEntity> designerBadges = designerBadgeRepository.findAllByDesignerId(userId);
        List<Long> badgeIds = designerBadges.stream()
                .map(DesignerBadgeEntity::getBadgeId)
                .toList();

        List<BadgeEntity> badgeEntities = badgeRepository.findAllById(badgeIds);

        return badgeEntities.stream()
                .map(badgeEntity -> {
                            // 뱃지 ID가 일치하면서 대표뱃지가 True인 항목이 있는지 확인
                    boolean isRepresentative = designerBadges.stream()
                            .anyMatch(designerBadgeEntity -> designerBadgeEntity.getBadgeId().equals(badgeEntity.getId()) && designerBadgeEntity.isRepresentativeBadge());
                    return Badge.builder()
                            .badgeId(badgeEntity.getId())
                            .badgeName(badgeEntity.getBadgeName())
                            .badgeContent(badgeEntity.getBadgeContent())
                            .badgeImageUrl(badgeEntity.getBadgeImageUrl())
                            .badgeColor(badgeEntity.getBadgeColor())
                            .badgeType(badgeEntity.getBadgeType())
                            .isRepresentativeBadge(isRepresentative)
                            .build();
                })
                .toList();
    }

    @Override
    public void updateBadgeStatus(Badge badge, Long userId) {
        DesignerBadgeEntity existingBadgeEntity = designerBadgeRepository
                .findByDesignerIdAndBadgeId(userId, badge.getBadgeId())
                .orElseThrow(() -> new PeautyException(PeautyResponseCode.NOT_EXIST_BADGE));
        // 업데이트 엔티티
        DesignerBadgeEntity updatedBadgeEntity = BadgeMapper.updateEntity(existingBadgeEntity, badge);
        // 엔티티 저장
        designerBadgeRepository.save(updatedBadgeEntity);
    }


}
