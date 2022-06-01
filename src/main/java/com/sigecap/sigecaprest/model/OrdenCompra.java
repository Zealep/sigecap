package com.sigecap.sigecaprest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "sgc_tz_orden_compra")
public class OrdenCompra {

    @Id
    @Column(name = "id_orden_compra")
    private String idOrdenCompra;

    @Column(name = "id_tipo_documento")
    private String idTipoDocumento;

    @Column(name = "id_solicitud_inscripcion")
    private String idSolicitudInscripcion;

    @Column(name = "id_banco")
    private String idBanco;

    @Column(name = "id_forma_pago")
    private String idFormaPago;

    @Column(name = "numero")
    private String numero;

    @Column(name = "fecha_emision")
    private Date fechaEmision;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    @Column(name = "id_usuario_registro")
    private String idUsuarioRegistro;

    @Column(name = "estado")
    private String estado;

    @Column(name = "in_procesado_facturacion")
    private String inProcesadoFacturacion;

    public String getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(String idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public String getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(String idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getIdSolicitudInscripcion() {
        return idSolicitudInscripcion;
    }

    public void setIdSolicitudInscripcion(String idSolicitudInscripcion) {
        this.idSolicitudInscripcion = idSolicitudInscripcion;
    }

    public String getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(String idBanco) {
        this.idBanco = idBanco;
    }

    public String getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(String idFormaPago) {
        this.idFormaPago = idFormaPago;
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

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getIdUsuarioRegistro() {
        return idUsuarioRegistro;
    }

    public void setIdUsuarioRegistro(String idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getInProcesadoFacturacion() {
        return inProcesadoFacturacion;
    }

    public void setInProcesadoFacturacion(String inProcesadoFacturacion) {
        this.inProcesadoFacturacion = inProcesadoFacturacion;
    }
}
