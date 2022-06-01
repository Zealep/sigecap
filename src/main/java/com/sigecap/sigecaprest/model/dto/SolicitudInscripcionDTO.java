package com.sigecap.sigecaprest.model.dto;

public class SolicitudInscripcionDTO {

    private String solicitud;
    private String solicitudDetalle;

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public String getSolicitudDetalle() {
        return solicitudDetalle;
    }

    public void setSolicitudDetalle(String solicitudDetalle) {
        this.solicitudDetalle = solicitudDetalle;
    }
}
