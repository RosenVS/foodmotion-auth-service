package com.individual.authservice.Http;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
