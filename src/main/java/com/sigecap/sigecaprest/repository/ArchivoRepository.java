package com.sigecap.sigecaprest.repository;

import com.sigecap.sigecaprest.model.Archivo;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivoRepository extends CrudRepository<Archivo,String> {

    @Procedure(value = "spu_sgc_GeneraCorrelativoAnoMes")
    String generatePrimaryKey(String tabla,String campo);
}
