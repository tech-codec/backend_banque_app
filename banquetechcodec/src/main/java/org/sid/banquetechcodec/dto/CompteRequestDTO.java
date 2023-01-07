package org.sid.banquetechcodec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CompteRequestDTO {
    private  String codeCp;
    private double solde;
}
