package com.accessflow.domain.repository;

import com.accessflow.domain.Permission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Permission aggregate root.
 */
public interface PermissionRepository {

    Permission save(Permission permission);

    Optional<Permission> findById(UUID id);

    Optional<Permission> findByKey(String key);

    List<Permission> findByResource(String resource);

    List<Permission> findAll();

    boolean existsByKey(String key);

    void deleteById(UUID id);

    long count();
}
