package org.sid.banquetechcodec.dao;


import org.sid.banquetechcodec.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface OperationRepository extends JpaRepository<Operation,Long> {

    @Query("SELECT o FROM Operation o WHERE o.compte.codeCp=:x ORDER BY o.dateCreationOp DESC ")
    public Page<Operation> listeOperation(@Param("x") String codeCp, Pageable pageable);

}
