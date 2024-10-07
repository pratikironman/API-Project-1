package com.ashu.blogapp.Payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JWTAuthRequest {

    private String userName;   // username == email of user

    private String password;
}
