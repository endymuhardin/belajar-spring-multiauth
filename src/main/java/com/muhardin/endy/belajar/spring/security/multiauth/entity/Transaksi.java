package com.muhardin.endy.belajar.spring.security.multiauth.entity;

import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity @Data
public class Transaksi {

    @Id
    private String id;

    @NotNull
    @ManyToOne @JoinColumn(name = "id_pengguna")
    private Pengguna pengguna;

    @NotNull
    private LocalDateTime waktuTransaksi;

    private String keterangan;

    @NotNull @Min(10)
    private BigDecimal nilai;
}
