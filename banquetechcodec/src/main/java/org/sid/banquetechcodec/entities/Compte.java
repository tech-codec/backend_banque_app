package org.sid.banquetechcodec.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_CP", discriminatorType = DiscriminatorType.STRING, length = 2)
public abstract class Compte implements Serializable {
    @Id
    private  String codeCp;
    private double solde;
    private Date dateCreationCp;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "compte")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Operation> operations;

    public void addOperationToCompte(Operation operation){
        if (getOperations() == null){
            this.operations = new ArrayList<>();
        }

        this.operations.add(operation);
        operation.setCompte(this);
    }
}
