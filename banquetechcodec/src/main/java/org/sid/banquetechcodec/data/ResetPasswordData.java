package org.sid.banquetechcodec.data;

import lombok.Data;

@Data
public class ResetPasswordData {

    private String email;
    private String token;
    private String password;
    private String repeatPassword;

}
