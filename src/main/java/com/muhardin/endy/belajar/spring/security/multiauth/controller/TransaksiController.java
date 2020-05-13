package com.muhardin.endy.belajar.spring.security.multiauth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muhardin.endy.belajar.spring.security.multiauth.dao.PenggunaDao;
import com.muhardin.endy.belajar.spring.security.multiauth.dao.TransaksiDao;
import com.muhardin.endy.belajar.spring.security.multiauth.entity.Pengguna;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
public class TransaksiController {
    @Autowired
    private TransaksiDao transaksiDao;
    @Autowired
    private PenggunaDao penggunaDao;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/transaksi/list")
    public ModelMap dataTransaksi(Authentication authentication) throws Exception {
        log.info("Authentication : {}", objectMapper.writeValueAsString(authentication));
        Pengguna pengguna = penggunaDao.findByUsername(authentication.getName());
        return new ModelMap().addAttribute("dataTransaksi",
                transaksiDao.findByPengguna(pengguna));
    }
}
