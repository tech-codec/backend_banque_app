package org.sid.banquetechcodec.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UserDtoRegister {

    @NotEmpty(message = "{registration.validation.firstName}")
    private String username;

    /*@NotEmpty(message = "{registration.validation.lastName}")
    private String lastName;*/

    @Email(message = "{registration.validation.email}")
    private String email;

    @NotEmpty(message = "{registration.validation.password}")
    private String password;

    private String repassword;
}
