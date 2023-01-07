package org.sid.banquetechcodec;

import org.sid.banquetechcodec.entities.Compte;
import org.sid.banquetechcodec.entities.CompteCourant;
import org.sid.banquetechcodec.entities.CompteEpargne;
import org.sid.banquetechcodec.entities.User;
import org.sid.banquetechcodec.services.BanqueServiceInitImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BanquetechcodecApplication implements CommandLineRunner {
	@Autowired
	private BanqueServiceInitImpl banqueServiceInit;

	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(BanquetechcodecApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repositoryRestConfiguration.exposeIdsFor(User.class, Compte.class, CompteEpargne.class, CompteCourant.class);
		banqueServiceInit.initBanque();
		System.out.println("bonjour");
	}

	@Bean
	public BCryptPasswordEncoder getBCE(){
		return new BCryptPasswordEncoder();
	}
}
