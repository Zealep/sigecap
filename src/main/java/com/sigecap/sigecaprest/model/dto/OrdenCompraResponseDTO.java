package com.sigecap.sigecaprest.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public class OrdenCompraResponseDTO {

    private String ruc;
    private Date fecha;
    private BigDecimal importeTotal;
    private BigDecimal importePagado;

    @JsonIgnore
    private String idSolicitudInscripcion;

    private String idOrdenCompra;

    @JsonIgnore
    private String idArchivo;

    @JsonIgnore
    private String rutaArchivo;

    private String tipoArchivo;


    private String fileDocumento;


    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public BigDecimal getImportePagado() {
        return importePagado;
    }

    public void setImportePagado(BigDecimal importePagado) {
        this.importePagado = importePagado;
    }

    public String getIdSolicitudInscripcion() {
        return idSolicitudInscripcion;
    }

    public void setIdSolicitudInscripcion(String idSolicitudInscripcion) {
        this.idSolicitudInscripcion = idSolicitudInscripcion;
    }

    public String getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(String idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public String getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(String idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getFileDocumento() {
        return fileDocumento;
    }

    public void setFileDocumento(String fileDocumento) {
        this.fileDocumento = fileDocumento;
    }

}
