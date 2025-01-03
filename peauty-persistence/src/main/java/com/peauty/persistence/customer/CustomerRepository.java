package com.peauty.persistence.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsBySocialId(String socialId);
    boolean existsByNickname(String nickname);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<CustomerEntity> findBySocialId(String socialId);

    @Query("""
        SELECT c
        FROM CustomerEntity c
        JOIN PuppyEntity p ON c.id = p.customer.id
        WHERE p.id = :puppyId
    """)
    Optional<CustomerEntity> findByPuppyId(@Param("puppyId") Long puppyId);

}