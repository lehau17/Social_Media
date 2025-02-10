package com.booking.domain.repository;

import com.booking.domain.model.entity.Role;
import com.booking.domain.model.entity.RoleCreateDto;

import java.util.List;
import java.util.Optional;

public interface RoleRepositoryDomain {
    Role createRole(RoleCreateDto roleCreateDto);
    Optional<Role> findRoleById(Long id);
    Optional<Role> findRoleByName(String name);
    public List<Role> findAllRoles();
}
