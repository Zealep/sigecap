package com.sigecap.sigecaprest.service;

import com.sigecap.sigecaprest.model.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario findById(Long id);

    List<Usuario> findAll();

    List<Usuario> findAllActives();

    Usuario save(Usuario c);

    Usuario update(Usuario c);

    void deleteById(Long id);

    boolean isExist(Usuario c);

}
