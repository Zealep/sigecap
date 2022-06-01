package com.sigecap.sigecaprest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "sgc_tz_comprobante_pago")
public class ComprobantePago {

    @Id
    @Column(name = "id_comprobante_pago")
    private String idComprobantePago;

    @Column(name = "id_tipo_documento")
    private String idTipoDocumento;

    @Column(name = "numero")
    private String numero;

    @Column(name = "fecha_emision")
    private Date fechaEmision;

    @Column(name = "total_valor_venta")
    private BigDecimal totalValorVenta;

    @Column(name = "total_igv_venta")
    private BigDecimal totalIgvVenta;

    @Column(name = "total_precio_venta")
    private BigDecimal totalPrecioVenta;

    @Column(name = "estado")
    private String estado;

    @Column(name = "id_usuario_registro")
    private String idUsuarioRegistro;

    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    public String getIdComprobantePago() {
        return idComprobantePago;
    }

    public void setIdComprobantePago(String idComprobantePago) {
        this.idComprobantePago = idComprobantePago;
    }

    public String getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(String idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdUsuarioRegistro() {
        return idUsuarioRegistro;
    }

    public void setIdUsuarioRegistro(String idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
