package org.sid.banquetechcodec.services;


import org.sid.banquetechcodec.dao.CompteRepository;
import org.sid.banquetechcodec.dao.OperationRepository;
import org.sid.banquetechcodec.dao.RoleRepository;
import org.sid.banquetechcodec.dao.UserRepository;
import org.sid.banquetechcodec.data.CompteRequest;
import org.sid.banquetechcodec.dto.UserRequestDTO;
import org.sid.banquetechcodec.entities.*;
import org.sid.banquetechcodec.util.GenererChaine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class ClientServiceImpl implements ClientServiceI {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private OperationRepository operationRepository;

    @Override
    public User addRoleToUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setUsername(userRequestDTO.getUsername());
        Role role = new Role(null,"USER");
        roleRepository.save(role);
        user.addRoleToUser(role);
        System.out.println(user+"je suis moi");
        userRepository.save(user);
        return user;
    }

    @Override
    public User addCompteCourantToUser(CompteRequest compteRequest, Long idUser) {
        GenererChaine chaine = new GenererChaine();
        User user = userRepository.findById(idUser).orElse(null);
        Compte compte = new CompteCourant(6000);
        if(compteRequest == null)throw new RuntimeException("imposible enregistre le compte");
        compte.setCodeCp(chaine.getRandomString(7));
        compte.setSolde(compteRequest.getSolde());
        compte.setDateCreationCp(new Date());
        compte.setUser(user);
        if(user.getComptes().size()<=2){
            user.addCompteToUser(compte);
            compteRepository.save(compte);
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public User addCompteEpargneToUser(CompteRequest compteRequest, Long idUser) {
        GenererChaine chaine = new GenererChaine();
        User user = userRepository.findById(idUser).orElse(null);
        Compte compte = new CompteEpargne(5.5);
        if(compteRequest == null)throw new RuntimeException("imposible enregistre le compte");
        compte.setCodeCp(chaine.getRandomString(7));
        compte.setSolde(compteRequest.getSolde());
        compte.setDateCreationCp(new Date());
        compte.setUser(user);
        if(user.getComptes().size()<=2){
            user.addCompteToUser(compte);
            compteRepository.save(compte);
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public Compte consulterCompte(String codeCp) {
        if(codeCp==null) throw new RuntimeException("ce compte n'existe pas");
        Compte compte = compteRepository.findById(codeCp).orElse(null);
        return compte;
    }

    @Override
    public void versement(double montant, String codeCp) {
        Compte compte =  consulterCompte(codeCp);
        System.out.println(compte+ " je suis un hero");
        Operation versement = new Versement(null,new Date(),montant,compte);
        operationRepository.save(versement);
        compte.setSolde(compte.getSolde()+montant);
        compteRepository.save(compte);
    }

    @Override
    public void retrait(double montant, String codeCp) {
        Compte compte = consulterCompte(codeCp);
        if(compte instanceof CompteCourant){
            if(compte.getSolde()+((CompteCourant) compte).getDecouvert()<montant) throw new RuntimeException("impossible d'éffectué un retrait");
        }
        Operation retrait = new Retrait(null,new Date(),montant,compte);
        operationRepository.save(retrait);
        compte.setSolde((compte.getSolde())-montant);
        compteRepository.save(compte);
    }

    @Override
    public void virement(String codeCpr, String codeCpv, double montant) {
        retrait(montant,codeCpr);
        versement(montant,codeCpv);
    }

    @Override
    public Page<Operation> listOperation(String codeCompte, int page, int size) throws ChangeSetPersister.NotFoundException {
        Pageable pageable = PageRequest.of(page,size);
        return operationRepository.listeOperation(codeCompte,pageable);
    }

    /*@Override
    public Page<Operation> lisOperation(String codeCp, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return operationRepository.listeOperation(codeCp,pageable);
    }*/


}
