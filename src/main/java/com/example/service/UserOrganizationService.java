package com.example.service;

import com.example.entity.User;
import com.example.entity.Organization;
import com.example.entity.UserOrganization;
import com.example.repository.UserOrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserOrganizationService {

    @Autowired
    private UserOrganizationRepository userOrganizationRepository;

    public List<UserOrganization> getAllUserOrganizations() {
        return userOrganizationRepository.findAll();
    }

    public Optional<UserOrganization> getUserOrganizationById(Long id) {
        return userOrganizationRepository.findById(id);
    }

    public UserOrganization addUserToOrganization(User user, Organization organization) {
        if (isUserAlreadyInOrganization(user.getId(), organization.getId()))
            return null;
        UserOrganization userOrganization = UserOrganization.builder()
                .user(user)
                .organization(organization)
                .build();
        return userOrganizationRepository.save(userOrganization);
    }

    public void removeUserFromOrganization(Long userOrganizationId) {
        userOrganizationRepository.deleteById(userOrganizationId);
    }

    @Transactional
    public void deleteUserOrganizationsByOrganizationId(Long organizationId) {
        userOrganizationRepository.deleteByOrganizationId(organizationId);
    }

    public boolean isUserAlreadyInOrganization(Long userId, Long organizationId) {
        return userOrganizationRepository.existsByUserIdAndOrganizationId(userId, organizationId);
    }
    public boolean isUserInAnyOrganization(Long userId) {
        return userOrganizationRepository.existsByUserId(userId);
    }

}

