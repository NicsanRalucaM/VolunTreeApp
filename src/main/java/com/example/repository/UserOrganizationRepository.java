package com.example.repository;

import com.example.entity.Organization;
import com.example.entity.User;
import com.example.entity.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserOrganizationRepository extends JpaRepository<UserOrganization, Long> {
    List<UserOrganization> findByUser(User user);

    List<UserOrganization> findByOrganization(Organization organization);

    @Transactional
    void deleteByOrganizationId(Long organizationId);

    boolean existsByUserIdAndOrganizationId(Long userId, Long organizationId);
    boolean existsByUserId(Long userId);


}
