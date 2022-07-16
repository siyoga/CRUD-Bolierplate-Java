package com.sdyak.crudboilerplatejava.misc.dto;

import lombok.Data;
import javax.validation.constraints.Email;

@Data
public class UserDTO {
    String firstname;
    String lastname;
    String username;
    @Email()
    String email;
    String password;
}
