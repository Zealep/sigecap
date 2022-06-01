package com.sigecap.sigecaprest.service.impl;

import com.sigecap.sigecaprest.model.Archivo;
import com.sigecap.sigecaprest.repository.ArchivoRepository;
import com.sigecap.sigecaprest.service.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("archivoService")
public class ArchivoServiceImpl implements ArchivoService {

    @Autowired
    ArchivoRepository archivoRepository;

    @Override
    @Transactional
    public Archivo save(Archivo a) {
        return archivoRepository.save(a);
    }
}
