package com.example.service;

import com.example.entity.Organization;
import com.example.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Optional<Organization> getOrganizationById(Long organizationId) {
        return organizationRepository.findById(organizationId);
    }

    public Organization addOrganization(Organization organization) {
        return organizationRepository.save(organization);
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

    public void deleteOrganization(Long organizationId) {
        organizationRepository.deleteById(organizationId);
    }
}
