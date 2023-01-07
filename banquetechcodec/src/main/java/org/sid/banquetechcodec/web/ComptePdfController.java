package org.sid.banquetechcodec.web;

import org.sid.banquetechcodec.entities.Compte;
import org.sid.banquetechcodec.services.ClientServiceImpl;
import org.sid.banquetechcodec.services.UserServiceI;
import org.sid.banquetechcodec.util.ComptePdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@CrossOrigin("*")
public class ComptePdfController {

    @Autowired
    ClientServiceImpl clientService;
    @Autowired
    UserServiceI userServiceI;

    @GetMapping(path = "/pdfCompte/{codeCompte}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity filmRapport(@PathVariable(name ="codeCompte") String codeCompte) throws IOException {
        Compte compte = clientService.consulterCompte(codeCompte);
        ByteArrayInputStream bis = ComptePdf.comptePdf(compte);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=FicheCompte.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


}
