package com.muhardin.endy.belajar.spring.security.multiauth.controller;

import com.muhardin.endy.belajar.spring.security.multiauth.dao.PenggunaDao;
import com.muhardin.endy.belajar.spring.security.multiauth.dao.TransaksiDao;
import com.muhardin.endy.belajar.spring.security.multiauth.entity.Pengguna;
import com.muhardin.endy.belajar.spring.security.multiauth.entity.Transaksi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransaksiApiController {
    @Autowired
    private TransaksiDao transaksiDao;
    @Autowired
    private PenggunaDao penggunaDao;

    @GetMapping("/transaksi/")
    public Iterable<Transaksi> dataTransaksi() {
        Pengguna pengguna = penggunaDao.findByUsername("user001");
        return transaksiDao.findByPengguna(pengguna);
    }
}
