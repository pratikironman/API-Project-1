package com.ashu.blogapp.Payloads;

import com.ashu.blogapp.Entity.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor

@Builder

public class UserDto {

    private int id;

    @NotEmpty //this will check notnull along with notblank
    @Size(min = 4, message = "User Name be minimum of 4 Characters !!")
    private String name;

    @Email(message = "No valid Email Address Found !!")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be minimum of 3 And maximum of 10 Characters !!")
    private String password;

    @NotEmpty
    private String about;

    private Set<RolesDto> roles = new HashSet<>();


}
