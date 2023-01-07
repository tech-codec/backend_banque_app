package org.sid.banquetechcodec.data;

import lombok.Data;

@Data
public class UpdatePasswordF {
  private String password;
  private String newpassword;
  private String confirmpassword;
}
