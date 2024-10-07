package com.ashu.blogapp.Payloads;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Getter
//@Setter

@Builder
public class APIResponse {

    private String message;

    private boolean success;

}
