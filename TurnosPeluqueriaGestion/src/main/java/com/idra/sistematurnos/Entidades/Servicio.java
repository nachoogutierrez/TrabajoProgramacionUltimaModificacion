package com.idra.sistematurnos.Entidades;

public class Servicio {
    private int idServicio;
    private String nombre;
    private String descripcion;
    private int duracionMinutos;
    private double precio;
    private boolean activo;

    // Constructores, Getters y Setters
    public Servicio() {}
    
    public Servicio(String nombre, String descripcion, int duracionMinutos, double precio, boolean activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinutos = duracionMinutos;
        this.precio = precio;
        this.activo = activo;
    }
    
    // Getters y Setters
    public int getIdServicio() { return idServicio; }
    public void setIdServicio(int idServicio) { this.idServicio = idServicio; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    @Override
    public String toString() {
        return nombre + " - $" + precio + " (" + duracionMinutos + " min)";
    }
}