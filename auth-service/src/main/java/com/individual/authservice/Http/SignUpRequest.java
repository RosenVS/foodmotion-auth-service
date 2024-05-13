package com.individual.authservice.Http;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String password;
}