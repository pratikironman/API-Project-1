package com.ashu.blogapp.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Builder

public class Roles {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) we would not increment roles as 2 roles are manually entered into DB
//   501	ROLE_ADMIN
// 	 502	ROLE_NORMAL
    private int id;

    private String name;        // role Name




}
