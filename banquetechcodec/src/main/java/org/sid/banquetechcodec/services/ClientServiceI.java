package org.sid.banquetechcodec.services;


import org.sid.banquetechcodec.data.CompteRequest;
import org.sid.banquetechcodec.dto.UserRequestDTO;
import org.sid.banquetechcodec.entities.Compte;
import org.sid.banquetechcodec.entities.Operation;
import org.sid.banquetechcodec.entities.User;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;

public interface ClientServiceI {
    User addRoleToUser(UserRequestDTO userRequestDTO);
    User addCompteCourantToUser(CompteRequest compteRequest, Long idUser);
    User addCompteEpargneToUser(CompteRequest compteRequest, Long idUser);
    Compte consulterCompte(String codeCp);
    void versement(double montant, String codeCp);
    void retrait(double montant, String codeCp);
    void virement(String codeCpr, String codeCpv, double montant);
    public Page<Operation> listOperation(String codeCompte, int page, int size)throws ChangeSetPersister.NotFoundException;
    //Page<Operation> lisOperation(String codeCp, int page, int size);
}
