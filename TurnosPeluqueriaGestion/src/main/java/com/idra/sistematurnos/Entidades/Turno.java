package com.idra.sistematurnos.Entidades;

import java.util.Date;


public class Turno {
    private int id;
    private Cliente cliente;
    private Peluquero peluquero;
    private Servicio servicio;
    private Date fecha;
    private boolean activo;

    public Turno() {}

    public Turno(int id, Cliente cliente, Peluquero peluquero, Servicio servicio, Date fecha, boolean activo) {
        this.id = id;
        this.cliente = cliente;
        this.peluquero = peluquero;
        this.servicio = servicio;
        this.fecha = fecha;
        this.activo = activo;
    }

    public Turno(Cliente cliente, Peluquero peluquero, Servicio servicio, Date fecha) {
        this(0, cliente, peluquero, servicio, fecha, true);
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Peluquero getPeluquero() { return peluquero; }
    public void setPeluquero(Peluquero peluquero) { this.peluquero = peluquero; }

    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        String f = (fecha == null) ? "sin fecha" : fecha.toString();
        String c = (cliente == null) ? "Cliente N/D" : cliente.getNombre();
        String p = (peluquero == null) ? "Peluquero N/D" : peluquero.getNombre();
        String s = (servicio == null) ? "Servicio N/D" : servicio.getNombre();
        return "Turno[id=" + id + ", " + c + ", " + p + ", " + s + ", fecha=" + f + "]";
    }
}
