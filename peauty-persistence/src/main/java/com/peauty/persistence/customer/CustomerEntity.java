package com.peauty.persistence.customer;

import com.peauty.domain.user.SocialPlatform;
import com.peauty.domain.user.Status;
import com.peauty.persistence.config.BaseTimeEntity;
import com.peauty.persistence.puppy.PuppyEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CustomerEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_platform", nullable = false)
    private SocialPlatform socialPlatform;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "phoneNum", length = 50)
    private String phoneNum;

    @Column(name = "address")
    private String address;

    @Lob
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    // TODO domain 의 의존성 끊기 고려
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PuppyEntity> puppies;

}
