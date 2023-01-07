package org.sid.banquetechcodec.web;


import org.sid.banquetechcodec.data.CompteRequest;
import org.sid.banquetechcodec.data.OperationRequest;
import org.sid.banquetechcodec.data.UserDtoRegister;
import org.sid.banquetechcodec.dto.UserRequestDTO;
import org.sid.banquetechcodec.entities.Compte;
import org.sid.banquetechcodec.entities.Operation;
import org.sid.banquetechcodec.entities.User;
import org.sid.banquetechcodec.exception.InvalidTokenException;
import org.sid.banquetechcodec.exception.UserAlreadyExistException;
import org.sid.banquetechcodec.services.ClientServiceImpl;
import org.sid.banquetechcodec.services.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@RestController
@CrossOrigin("*")
public class ClientController {
    @Autowired
    private ClientServiceImpl clientService;
    @Autowired
    private UserServiceI userServiceI;

    @PostMapping(path = "/Operation")
    public void setOperation(@RequestBody OperationRequest operation){

        try{
            if(operation.getTypeOp().equals("ret")){
                clientService.retrait(operation.getMontant(),operation.getCodeCp());
            }
            if (operation.getTypeOp().equals("ver")){
                clientService.versement(operation.getMontant(),operation.getCodeCp());
            }
            if (operation.getTypeOp().equals("vir")){
                clientService.virement(operation.getCodeCp(),operation.getCodeCpv(),operation.getMontant());
                System.out.println("je suis moi un homme de principe");
            }


        }catch (Exception e){

        }

    }

    @GetMapping("/consultCp/{codeCp}")
    public Compte consultCompte(@PathVariable String codeCp){
        return clientService.consulterCompte(codeCp);
    }

    @PostMapping(path = "/addUser")
    public User addRoleToUser(@RequestBody UserRequestDTO user){
        User client = clientService.addRoleToUser(user);
        return client;
    }

    @PostMapping(path = "/addCompteCourantToUser/{idUser}")
    public User addCompteCourantToUser(@RequestBody CompteRequest compteRequest, @PathVariable Long idUser){
        User user = clientService.addCompteCourantToUser(compteRequest,idUser);
        return  user;
    }

    @PostMapping(path = "/addCompteEpargneToUser/{idUser}")
    public User addCompteEpargneToUser(@RequestBody CompteRequest compteRequest, @PathVariable Long idUser){
        User user = clientService.addCompteEpargneToUser(compteRequest,idUser);
        return  user;
    }

    @PostMapping(path = "/UserRegister")
    public void register(@RequestBody UserDtoRegister userDtoRegister,final BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            throw new RuntimeException(""+userDtoRegister);
        }
        try {
            System.out.println(userDtoRegister.getUsername());
            userServiceI.registerUser(userDtoRegister);
        }catch (UserAlreadyExistException e){
            bindingResult.rejectValue("email", "userData.email","An account already exists for this email.");
        }

    }


    @GetMapping("/verify")
    public String verifyCustomer(@RequestParam(required = false) String token){
        if(StringUtils.isEmpty(token)){
            return "<h2>la vérification du compte a échoué retournée sur le site vous enregistré</h2>";
        }
        try {
            userServiceI.verifyUser(token);
        } catch (InvalidTokenException e) {
            return " <h2>vous etes déjas enregistré retourné sur le site pour accéder a votre compte.</h2>";
        }
        return " <h2>vous avez été enregigstré avec sucess retourné sur le site pour accéder a votre compte.</h2>";
    }

    @GetMapping("/findUser/{username}")
    public User findUser(@PathVariable String username){
        return userServiceI.findUserByName(username);
    }

    @GetMapping("/listeoperation/{codecp}/{page}/{size}")
    public Page<Operation> listeOperation(@PathVariable String codecp, @PathVariable int page, @PathVariable int size) throws ChangeSetPersister.NotFoundException {
        Page<Operation> pageOperations =clientService.listOperation(codecp, page, size);
        return pageOperations;
    }

}
