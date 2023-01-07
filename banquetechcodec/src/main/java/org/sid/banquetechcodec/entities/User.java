package org.sid.banquetechcodec.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class User implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    //private String repassword;
    private String photo;
    private boolean accountVerified;
    @OneToMany(mappedBy = "user")
    private Collection<SecureToken> tokens;
    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Compte> comptes;

    public void addCompteToUser(Compte compte){
        if(getComptes() == null){
            this.comptes = new ArrayList<>();
        }

        this.comptes.add(compte);
        compte.setUser(this);
    }

    public void addRoleToUser(Role role){
        if(getComptes() == null){
            this.comptes = new ArrayList<>();
        }

        this.roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
