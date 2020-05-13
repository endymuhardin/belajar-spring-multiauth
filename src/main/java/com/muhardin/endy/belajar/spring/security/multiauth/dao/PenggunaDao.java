package com.muhardin.endy.belajar.spring.security.multiauth.dao;

import com.muhardin.endy.belajar.spring.security.multiauth.entity.Pengguna;
import org.springframework.data.repository.CrudRepository;

public interface PenggunaDao extends CrudRepository<Pengguna, String> {
    Pengguna findByUsername(Object sub);
}
