package org.sid.banquetechcodec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.banquetechcodec.entities.Compte;


import java.util.ArrayList;
import java.util.Collection;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    //private boolean accountVerified;
    private Collection<RoleResponseDTO> roles = new ArrayList<>();
    private Collection<Compte> comptes;

}
