package com.sigecap.sigecaprest.repository.jdbc;

import com.sigecap.sigecaprest.model.dto.OrdenCompraResponseDTO;
import com.sigecap.sigecaprest.model.dto.SolicitudInscripcionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class OrdenCompraJdbcRepository {

    @Value("${URL_PATH_BASE_ATTACHMENT}")
    private String path;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public List<OrdenCompraResponseDTO> obtenerDatos(String ruc){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ruc",ruc);
        parameters.addValue("ruta",path+"OC/");

        String sql = "SELECT " +
                "et.numero_documento ruc," +
                "oc.numero numero," +
                "ba.no_banco banco," +
                "fecha_emision fecha," +
                "fp.no_forma_pago forma_pago,"+
                "oc.observaciones observaciones,"+
                "si.total_precio_venta importe_total," +
                "monto importe_pagado," +
                "si.id_solicitud_inscripcion," +
                "oc.id_orden_compra," +
                "ac.id_archivo," +
                "concat(:ruta,si.id_solicitud_inscripcion,'/',oc.id_orden_compra,'/',ac.id_archivo,'/',ac.nombre) ruta_archivo,\n" +
                "ac.tipo tipo_archivo" +
                " FROM sgc_tz_orden_compra oc" +
                " inner join sgc_tz_solicitud_inscripcion si on oc.id_solicitud_inscripcion = si.id_solicitud_inscripcion" +
                " inner join sgc_tz_entidad et on si.id_entidad_cliente = et.id_entidad" +
                " inner join sgc_tz_archivo ac on concat('OC-' ,oc.id_orden_compra) = ac.id_documento" +
                " inner join sgc_tm_banco ba on oc.id_banco = ba.id_banco" +
                " inner join sgc_tm_forma_pago fp on oc.id_forma_pago = fp.id_forma_pago"+
                " where et.numero_documento = :ruc and oc.in_procesado_facturacion = 'N' and id_tipo_documento = 3"+
                " order by fecha desc";


        return (List<OrdenCompraResponseDTO>) jdbcTemplate.query(sql, parameters,new OrdenCompraDTOMapper());
    }

        private static final class OrdenCompraDTOMapper implements RowMapper<OrdenCompraResponseDTO> {

            @Override
            public OrdenCompraResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

                OrdenCompraResponseDTO c = new OrdenCompraResponseDTO();
                c.setRuc(rs.getString("ruc"));
                c.setNumeroOperacion(rs.getString("numero"));
                c.setNombreBanco(rs.getString("banco"));
                c.setFecha(rs.getDate("fecha"));
                c.setFormaPago(rs.getString("forma_pago"));
                c.setObservaciones(rs.getString("observaciones"));
                c.setImporteTotal(rs.getBigDecimal("importe_total"));
                c.setImportePagado(rs.getBigDecimal("importe_pagado"));
                c.setIdSolicitudInscripcion(rs.getString("id_solicitud_inscripcion"));
                c.setIdOrdenCompra(rs.getString("id_orden_compra"));
                c.setIdArchivo(rs.getString("id_archivo"));
                c.setRutaArchivo(rs.getString("ruta_archivo"));
                c.setTipoArchivo(rs.getString("tipo_archivo"));
                return c;
            }
        }


    public List<SolicitudInscripcionDTO> obtenerDatosSolicitud(String codigoPago){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("codigo",codigoPago);

        String sql = "select " +
                "concat('SI-',si.id_solicitud_inscripcion) as solicitud, " +
                "concat('SID-',sid.id_solicitud_inscripcion_detalle) as solicitud_detalle " +
                "from sgc_tz_orden_compra oc " +
                "inner join sgc_tz_solicitud_inscripcion si on oc.id_solicitud_inscripcion = si.id_solicitud_inscripcion " +
                "inner join sgc_tz_solicitud_inscripcion_detalle sid on si.id_solicitud_inscripcion =  sid.id_solicitud_inscripcion " +
                "where oc.id_orden_compra = :codigo";


        return (List<SolicitudInscripcionDTO>) jdbcTemplate.query(sql, parameters,new SolicitudDTOMapper());
    }

    private static final class SolicitudDTOMapper implements RowMapper<SolicitudInscripcionDTO> {

        @Override
        public SolicitudInscripcionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

            SolicitudInscripcionDTO c = new SolicitudInscripcionDTO();
            c.setSolicitud(rs.getString("solicitud"));
            c.setSolicitudDetalle(rs.getString("solicitud_detalle"));

            return c;
        }
    }

}
