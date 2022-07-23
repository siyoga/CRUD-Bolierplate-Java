package crudboilerplatejava.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {
    @NotBlank
    String username;
    @NotBlank
    String password;
}
