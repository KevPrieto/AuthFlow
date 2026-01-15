package com.accessflow.infrastructure.persistence.repository;

import com.accessflow.domain.Permission;
import com.accessflow.domain.repository.PermissionRepository;
import com.accessflow.infrastructure.persistence.mapper.PermissionMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PermissionRepositoryImpl implements PermissionRepository {

    private final PermissionJpaRepository jpaRepository;
    private final PermissionMapper mapper;

    public PermissionRepositoryImpl(PermissionJpaRepository jpaRepository, PermissionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Permission save(Permission permission) {
        var jpaEntity = mapper.toJpa(permission);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Permission> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(mapper::toDomain);
    }

    @Override
    public Optional<Permission> findByKey(String key) {
        return jpaRepository.findByKey(key)
            .map(mapper::toDomain);
    }

    @Override
    public List<Permission> findByResource(String resource) {
        return jpaRepository.findByResource(resource).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Permission> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByKey(String key) {
        return jpaRepository.existsByKey(key);
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
