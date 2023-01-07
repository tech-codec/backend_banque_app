package org.sid.banquetechcodec.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@DiscriminatorValue("CC")
public class CompteCourant extends Compte {
    private double decouvert;
}
