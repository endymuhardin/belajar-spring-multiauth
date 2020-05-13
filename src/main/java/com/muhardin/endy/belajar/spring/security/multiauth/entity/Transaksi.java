package com.muhardin.endy.belajar.spring.security.multiauth.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
