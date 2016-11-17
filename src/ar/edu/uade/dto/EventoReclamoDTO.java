package ar.edu.uade.dto;

import java.util.Date;

public class EventoReclamoDTO {
    private String estado;
    private Date fecha;
    private String detalle;

    public EventoReclamoDTO(String estado, Date fecha, String detalle) {
        this.estado = estado;
        this.fecha = fecha;
        this.detalle = detalle;
    }

    public EventoReclamoDTO() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
