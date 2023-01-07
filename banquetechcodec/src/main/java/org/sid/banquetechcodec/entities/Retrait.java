package org.sid.banquetechcodec.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@DiscriminatorValue("R")
public class Retrait extends Operation {
    public Retrait(Long id, Date dateCreationOp, double montant, Compte compte) {
        super(id, dateCreationOp, montant, compte);
    }

    public Retrait() {
    }
}
