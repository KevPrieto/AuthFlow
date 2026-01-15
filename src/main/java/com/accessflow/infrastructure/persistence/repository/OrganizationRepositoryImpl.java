package com.accessflow.infrastructure.persistence.repository;

import com.accessflow.domain.Organization;
import com.accessflow.domain.repository.OrganizationRepository;
import com.accessflow.infrastructure.persistence.mapper.OrganizationMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of OrganizationRepository using Spring Data JPA.
 */
@Component
public class OrganizationRepositoryImpl implements OrganizationRepository {

    private final OrganizationJpaRepository jpaRepository;
    private final OrganizationMapper mapper;

    public OrganizationRepositoryImpl(OrganizationJpaRepository jpaRepository, OrganizationMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Organization save(Organization organization) {
        var jpaEntity = mapper.toJpa(organization);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Organization> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(mapper::toDomain);
    }

    @Override
    public Optional<Organization> findBySlug(String slug) {
        return jpaRepository.findBySlug(slug)
            .map(mapper::toDomain);
    }

    @Override
    public List<Organization> findAllActive() {
        return jpaRepository.findByIsActiveTrue().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsBySlug(String slug) {
        return jpaRepository.existsBySlug(slug);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }
}
