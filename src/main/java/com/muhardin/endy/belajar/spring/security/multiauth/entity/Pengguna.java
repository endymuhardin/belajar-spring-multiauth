package com.muhardin.endy.belajar.spring.security.multiauth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Entity @Data
public class Pengguna {

    @Id
    private String id;

    @NotNull @NotEmpty
    private String username;

    @NotNull @NotEmpty
    private String nama;

    @NotNull @NotEmpty @Email
    private String email;
}
