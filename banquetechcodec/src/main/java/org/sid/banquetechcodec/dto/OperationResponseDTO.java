package org.sid.banquetechcodec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class OperationResponseDTO {
    private Long codeOp;
    private Date dateCreationOp;
    private double montant;
}
