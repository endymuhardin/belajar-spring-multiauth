package com.muhardin.endy.belajar.spring.security.multiauth.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
