package com.ashu.blogapp.Payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Builder

public class JWTAuthResponse {

    private String token;

    private String userName;


}
