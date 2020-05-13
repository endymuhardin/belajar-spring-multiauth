package com.muhardin.endy.belajar.spring.security.multiauth.dao;

import com.muhardin.endy.belajar.spring.security.multiauth.entity.Pengguna;
import com.muhardin.endy.belajar.spring.security.multiauth.entity.Transaksi;
import org.springframework.data.repository.CrudRepository;

public interface TransaksiDao extends CrudRepository<Transaksi, String> {
    Iterable<Transaksi> findByPengguna(Pengguna pengguna);
}
