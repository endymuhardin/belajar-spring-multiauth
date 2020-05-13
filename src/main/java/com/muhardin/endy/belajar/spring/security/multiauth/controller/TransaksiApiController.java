package com.muhardin.endy.belajar.spring.security.multiauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muhardin.endy.belajar.spring.security.multiauth.dao.PenggunaDao;
import com.muhardin.endy.belajar.spring.security.multiauth.dao.TransaksiDao;
import com.muhardin.endy.belajar.spring.security.multiauth.entity.Pengguna;
import com.muhardin.endy.belajar.spring.security.multiauth.entity.Transaksi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

@RestController @Slf4j
@RequestMapping("/api")
public class TransaksiApiController {
    @Autowired
    private TransaksiDao transaksiDao;
    @Autowired
    private PenggunaDao penggunaDao;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/transaksi/")
    public Iterable<Transaksi> dataTransaksi(Authentication currentUser) throws Exception {
        log.info("Current user : {}", objectMapper.writeValueAsString(currentUser));

        if (!OAuth2AuthenticatedPrincipal.class.isAssignableFrom(currentUser.getPrincipal().getClass())) {
            throw new IllegalStateException("Current Principal is not OAuth2 principal : " + currentUser.getPrincipal().getClass().getName());
        }

        String username = ((OAuth2AuthenticatedPrincipal) currentUser.getPrincipal())
                .getAttribute("user_name");
        log.info("Username : {}", username);
        Pengguna pengguna = penggunaDao.findByUsername(username);
        return transaksiDao.findByPengguna(pengguna);
    }
}
