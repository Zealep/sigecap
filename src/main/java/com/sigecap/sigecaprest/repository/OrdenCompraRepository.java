package com.sigecap.sigecaprest.repository;

import com.sigecap.sigecaprest.model.OrdenCompra;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrdenCompraRepository extends CrudRepository<OrdenCompra,String> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE OrdenCompra o set o.inProcesadoFacturacion = 'S' where o.idOrdenCompra =?1")
    void actualizarFacturacion(String codigoPago);
}
