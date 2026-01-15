package com.accessflow.domain.repository;

import com.accessflow.domain.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Role aggregate root.
 */
public interface RoleRepository {

    Role save(Role role);

    Optional<Role> findById(UUID id);

    Optional<Role> findByName(String name);

    List<Role> findAllSystemRoles();

    List<Role> findAll();

    boolean existsByName(String name);

    void deleteById(UUID id);

    long count();
}
