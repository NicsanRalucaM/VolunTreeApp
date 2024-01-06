package com.example.repository;

import com.example.entity.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    void testSaveOrganization() {
        Organization organization = new Organization(null, "Organization Name", "Description");
        Organization savedOrganization = organizationRepository.save(organization);
        assertNotNull(savedOrganization.getId());
        Optional<Organization> retrievedOrganization = organizationRepository.findById(savedOrganization.getId());
        assertTrue(retrievedOrganization.isPresent());
        assertEquals("Organization Name", retrievedOrganization.get().getName());
        assertEquals("Description", retrievedOrganization.get().getDescription());
    }

    @Test
    void testFindById() {
        Organization organization = new Organization(null, "Organization Name", "Description");
        organizationRepository.save(organization);

        Optional<Organization> retrievedOrganization = organizationRepository.findById(1L);
        assertTrue(retrievedOrganization.isPresent());
        assertEquals("Organization Name", retrievedOrganization.get().getName());
        assertEquals("Description", retrievedOrganization.get().getDescription());
    }

    @Test
    void testDeleteOrganization() {
        Organization organization = new Organization(null, "Organization Name", "Description");
        Organization savedOrganization = organizationRepository.save(organization);

        organizationRepository.deleteById(savedOrganization.getId());
        Optional<Organization> retrievedOrganization = organizationRepository.findById(savedOrganization.getId());
        assertTrue(retrievedOrganization.isEmpty());
    }
    @Test
    void testFindAll() {
        Organization organization1 = new Organization(null, "Org 1", "Desc 1");
        Organization organization2 = new Organization(null, "Org 2", "Desc 2");
        organizationRepository.saveAll(List.of(organization1, organization2));

        List<Organization> organizations = organizationRepository.findAll();
        assertEquals(2, organizations.size());
    }

    @Test
    void testFindByName() {
        Organization organization = new Organization(null, "Org Name", "Org Description");
        organizationRepository.save(organization);

        Optional<Organization> retrievedOrganization = organizationRepository.findByName("Org Name");
        assertTrue(retrievedOrganization.isPresent());
        assertEquals("Org Description", retrievedOrganization.get().getDescription());
    }
}
