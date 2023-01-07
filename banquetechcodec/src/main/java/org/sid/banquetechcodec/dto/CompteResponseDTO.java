package org.sid.banquetechcodec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteResponseDTO {

    private  String codeCp;
    private double solde;
    private Date dateCreationCp;
    private Collection<OperationResponseDTO> operations;

}
