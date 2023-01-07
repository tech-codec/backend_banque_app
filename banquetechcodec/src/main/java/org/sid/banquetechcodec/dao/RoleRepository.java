package org.sid.banquetechcodec.dao;

import org.sid.banquetechcodec.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;


@RepositoryRestResource
@CrossOrigin("*")
public interface RoleRepository extends JpaRepository<Role,Long> {

    public Role findByRoleName(String roleName);
}
