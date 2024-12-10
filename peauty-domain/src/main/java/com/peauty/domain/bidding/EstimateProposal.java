package com.peauty.domain.bidding;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder
public class EstimateProposal {

    private ID id;
    @Getter private BiddingProcess.ID processId;
    @Getter private GroomingType type;
    @Getter private String detail;
    @Getter private List<EstimateProposalImage> images;
    @Getter private Long desiredCost;
    @Getter private LocalDateTime desiredDateTime;

    // can be null depends on grooming type
    @Getter private TotalGroomingBodyType totalGroomingBodyType;
    @Getter private TotalGroomingFaceType totalGroomingFaceType;

    public Optional<EstimateProposal.ID> getId() {
        return Optional.ofNullable(id);
    }

    public Profile getProfile() {
        return Profile.builder()
                .id(id.value())
                .type(type.getDescription())
                .detail(detail)
                .imageUrls(images.stream()
                        .map(EstimateProposalImage::getImageUrl)
                        .toList()
                )
                .desiredCost(desiredCost)
                .desiredDateTime(desiredDateTime)
                .totalGroomingBodyType(totalGroomingBodyType == null ? null : totalGroomingBodyType.getDescription())
                .totalGroomingFaceType(totalGroomingFaceType == null ? null : totalGroomingFaceType.getDescription())
                .build();
    }

    @Builder
    public record Profile(
            Long id,
            String type,
            String detail,
            List<String> imageUrls,
            Long desiredCost,
            LocalDateTime desiredDateTime,
            String totalGroomingBodyType,
            String totalGroomingFaceType
    ) {
    }


    public ReviewProfile getReviewProfile() {
        return ReviewProfile.builder()
                .id(id.value())
                .type(type.getDescription())
                .desiredCost(desiredCost)
                .desiredDateTime(desiredDateTime)
                .totalGroomingBodyType(totalGroomingBodyType == null ? null : totalGroomingBodyType.getDescription())
                .totalGroomingFaceType(totalGroomingFaceType == null ? null : totalGroomingFaceType.getDescription())
                .build();
    }

    @Builder
    public record ReviewProfile(
            Long id,
            String type,
            Long desiredCost,
            LocalDateTime desiredDateTime,
            String totalGroomingBodyType,
            String totalGroomingFaceType
    ) {
    }


    public record ID(Long value) {
        public boolean isSameId(Long id) {
            return value.equals(id);
        }
    }
}