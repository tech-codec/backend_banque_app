package org.sid.banquetechcodec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class RoleRequestDTO {
    private Long id;
    private String roleName;
}
