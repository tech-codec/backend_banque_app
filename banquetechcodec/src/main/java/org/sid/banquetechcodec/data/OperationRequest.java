package org.sid.banquetechcodec.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class OperationRequest {
   private String codeCp;
   private String codeCpv;
   private double montant;
   private String typeOp;
}
