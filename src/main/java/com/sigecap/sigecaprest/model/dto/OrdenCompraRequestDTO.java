package com.sigecap.sigecaprest.model.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrdenCompraRequestDTO {

    private String codigoPago;
    private Integer tipoDocumento;
    private String serie;
    private String usuario;
    private String numero;
    private Date fechaEmision;
    private BigDecimal totalValorVenta;
    private BigDecimal totalIgvVenta;
    private BigDecimal totalPrecioVenta;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCodigoPago() {
        return codigoPago;
    }

    public void setCodigoPago(String codigoPago) {
        this.codigoPago = codigoPago;
    }

    public Integer getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Integer tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getTotalValorVenta() {
        return totalValorVenta;
    }

    public void setTotalValorVenta(BigDecimal totalValorVenta) {
        this.totalValorVenta = totalValorVenta;
    }

    public BigDecimal getTotalIgvVenta() {
        return totalIgvVenta;
    }

    public void setTotalIgvVenta(BigDecimal totalIgvVenta) {
        this.totalIgvVenta = totalIgvVenta;
    }

    public BigDecimal getTotalPrecioVenta() {
        return totalPrecioVenta;
    }

    public void setTotalPrecioVenta(BigDecimal totalPrecioVenta) {
        this.totalPrecioVenta = totalPrecioVenta;
    }
}
