package org.sid.banquetechcodec.services;


import org.sid.banquetechcodec.dao.RoleRepository;
import org.sid.banquetechcodec.data.CompteRequest;
import org.sid.banquetechcodec.data.OperationRequest;
import org.sid.banquetechcodec.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BanqueServiceInitImpl implements BanqueServiceInitI {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ClientServiceImpl clientService;
    @Autowired
    private UserServiceI userServiceI;

    @Override
    public void initBanque(){
        User user1 = new User(null,"doudou","doudou@gmail.com","1234",null,false,null,null,null);
        User user2 = new User(null,"varus","varus@gmail.com","1234",null,false,null,null,null);
        User user3 = new User(null,"toutou","toutou@gmail.com","1234",null,false,null,null,null);
        User user4 = new User(null,"jean","jean@gmail.com","1234",null,false,null,null,null);

        CompteRequest compte1 = new CompteRequest(80000);
        CompteRequest compte2 = new CompteRequest(70000);
        CompteRequest compte3 = new CompteRequest(50000);
        CompteRequest compte4 = new CompteRequest(100000);
        OperationRequest operation1 = new OperationRequest("c1","c2",7000,"ret");
        /*roleRepository.save(new Role(null,"ADMIN"));
        roleRepository.save(new Role(null,"USER"));*/

        /*userServiceI.registerUser(user1);
        userServiceI.registerUser(user2);
        userServiceI.registerUser(user3);*/

        //userServiceI.addRoleToUser("jolie");
        /*userServiceI.addRoleToUser("cola");
        userServiceI.addRoleToUser("toutou");
        userServiceI.addRoleToUser("doudou");
        userServiceI.addRoleToUser("jean");
        userServiceI.addRoleToUser("paule");
        userServiceI.addRoleToAdmin("frank");
        userServiceI.addRoleToAdmin("varus");
        userServiceI.addRoleToAdmin("boris");*/



        /*userServiceI.saveUser(user1);
        userServiceI.saveUser(user2);
        userServiceI.saveUser(user3);
        userServiceI.saveUser(user4);*/

        /*clientService.addRoleToUser(user1);
        clientService.addRoleToUser(user2);
        clientService.addRoleToUser(user3);*/

        /*clientService.addCompteCourantToUser(compte1,(long)1);
        clientService.addCompteCourantToUser(compte2,(long)2);
        clientService.addCompteCourantToUser(compte3,(long)3);
        clientService.addCompteEpargneToUser(compte1,(long)1);
        clientService.addCompteEpargneToUser(compte2,(long)2);
        clientService.addCompteEpargneToUser(compte3,(long)3);*/

        /*clientService.versement(400000,"DU4D9TR");
        clientService.versement(400000,"9395YS7");
        clientService.versement(400000,"MIBTSX2");
        clientService.versement(400000,"2ZQ3ZZU");
        clientService.versement(400000,"KPYSL7D");
        clientService.versement(400000,"SC0CWLC");*/

        /*clientService.retrait(150000,"DU4D9TR");
        clientService.retrait(150000,"9395YS7");
        clientService.retrait(150000,"MIBTSX2");
        clientService.retrait(150000,"2ZQ3ZZU");
        clientService.retrait(150000,"KPYSL7D");
        clientService.retrait(150000,"SC0CWLC");*/

        /*clientService.virement("DU4D9TR","9395YS7",50000);
        clientService.virement("MIBTSX2","2ZQ3ZZU",50000);
        clientService.virement("KPYSL7D","SC0CWLC",50000);*/



    }
}
