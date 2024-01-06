package com.example.service;

import com.example.entity.Organization;
import com.example.entity.User;
import com.example.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private UserOrganizationService userOrganizationService;

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Optional<Organization> getOrganizationById(Long organizationId) {
        return organizationRepository.findById(organizationId);
    }

    @Transactional
    public Organization addOrganization(Organization organization, User user) {
        Organization savedOrganization = organizationRepository.save(organization);
        userOrganizationService.addUserToOrganization(user, savedOrganization);
        return savedOrganization;
    }

    public Optional<Organization> updateOrganization(Long organizationId, Organization updatedOrganization) {
        Optional<Organization> existingOrganizationOptional = organizationRepository.findById(organizationId);
        if (existingOrganizationOptional.isPresent()) {
            Organization existingOrganization = existingOrganizationOptional.get();
            existingOrganization.setName(updatedOrganization.getName());
            existingOrganization.setDescription(updatedOrganization.getDescription());
            Organization savedOrganization = organizationRepository.save(existingOrganization);
            return Optional.of(savedOrganization);
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public void deleteOrganization(Long organizationId) {
        userOrganizationService.deleteUserOrganizationsByOrganizationId(organizationId);
        organizationRepository.deleteById(organizationId);
    }

    public Optional<Organization> getOrganizationByName(String organizationName) {
        return organizationRepository.findByName(organizationName);
    }

}
