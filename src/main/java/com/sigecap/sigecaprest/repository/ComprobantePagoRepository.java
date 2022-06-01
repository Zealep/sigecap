package com.sigecap.sigecaprest.repository;

import com.sigecap.sigecaprest.model.ComprobantePago;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ComprobantePagoRepository extends CrudRepository<ComprobantePago,String> {

    @Procedure(value = "spu_sgc_GeneraCorrelativoAnoMes")
    String generatePrimaryKey(String tabla,String campo);



}
