package com.booking.infrastructure.persistence.repository;

import com.booking.domain.model.entity.Role;
import com.booking.domain.model.entity.RoleCreateDto;
import com.booking.domain.repository.RoleRepositoryDomain;
import com.booking.infrastructure.persistence.mapper.RoleJPAMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoleRepositoryDomainImpl implements RoleRepositoryDomain {
    @Autowired
    private RoleJPAMapper roleJPAMapper;

    @Override
    public Role createRole(RoleCreateDto roleCreateDto) {
        var newRole = new Role().setRoleName(roleCreateDto.getRoleName()).setIsActive(roleCreateDto.getIsActive());
        return this.roleJPAMapper.save(newRole);
    }

    @Override
    public Optional<Role> findRoleById(Long id) {
        return this.roleJPAMapper.findById(id);
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        return this.roleJPAMapper.findByRoleName(name);
    }

    @Override
    public List<Role> findAllRoles() {
        return this.roleJPAMapper.findAll();
    }

}
