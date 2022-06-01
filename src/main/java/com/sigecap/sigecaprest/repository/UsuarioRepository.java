package com.sigecap.sigecaprest.repository;

import com.sigecap.sigecaprest.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {


    @Query("select u from Usuario u where u.nombreUsuario=?1")
    Optional<Usuario> findByUsername(String username);

    @Query("select e from Usuario e where e.estado=?1")
    public List<Usuario> findAllActives(String active);

    @Query("select u.idUsuario from Usuario u where u.nombreUsuario=?1")
    String findIdUsuarioByUsername(String usuario);

    @Modifying
    @Query("update Usuario e set e.estado=?1 where e.idUsuario=?2 ")
    public void deleteLogicById(String estado,Long id);


}
