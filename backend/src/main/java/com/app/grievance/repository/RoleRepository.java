package com.app.grievance.repository;

import com.app.grievance.model.Role;
import com.app.grievance.model.Role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(RoleName roleName);
}
