package org.sid.banquetechcodec.dao;


import org.sid.banquetechcodec.entities.Compte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface CompteRepository extends JpaRepository<Compte,String> {

    @RestResource(path = "comptepage")
    public Page<Compte> findCompteByUserUsernameContains(@Param("mc") String mc, Pageable pageable);
}
