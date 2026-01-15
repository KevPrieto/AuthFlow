package com.accessflow.infrastructure.persistence.repository;

import com.accessflow.domain.Membership;
import com.accessflow.domain.repository.MembershipRepository;
import com.accessflow.infrastructure.persistence.mapper.MembershipMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MembershipRepositoryImpl implements MembershipRepository {

    private final MembershipJpaRepository jpaRepository;
    private final MembershipMapper mapper;

    public MembershipRepositoryImpl(MembershipJpaRepository jpaRepository, MembershipMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Membership save(Membership membership) {
        var jpaEntity = mapper.toJpa(membership);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Membership> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(mapper::toDomain);
    }

    @Override
    public Optional<Membership> findByUserIdAndOrganizationId(UUID userId, UUID organizationId) {
        return jpaRepository.findByUserIdAndOrganizationId(userId, organizationId)
            .map(mapper::toDomain);
    }

    @Override
    public List<Membership> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Membership> findByOrganizationId(UUID organizationId) {
        return jpaRepository.findByOrganizationId(organizationId).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Membership> findActiveByUserId(UUID userId) {
        return jpaRepository.findActiveByUserId(userId).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Membership> findActiveByOrganizationId(UUID organizationId) {
        return jpaRepository.findActiveByOrganizationId(organizationId).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByUserIdAndOrganizationId(UUID userId, UUID organizationId) {
        return jpaRepository.existsByUserIdAndOrganizationId(userId, organizationId);
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
