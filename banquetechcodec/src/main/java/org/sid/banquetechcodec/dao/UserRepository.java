package org.sid.banquetechcodec.dao;

import org.sid.banquetechcodec.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface UserRepository extends JpaRepository<User,Long> {

    public User findByUsername(String nameUser);

    public User findByEmail(String email);

    @RestResource(path = "/userpage")
    public Page<User> findByUsernameContaining(@Param("mc") String des, Pageable pageable);

}
