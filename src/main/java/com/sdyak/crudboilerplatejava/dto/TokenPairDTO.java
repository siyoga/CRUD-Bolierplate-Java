package com.sdyak.crudboilerplatejava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenPairDTO {
    String accessToken;
    String refreshToken;
}
