package com.idra.sistematurnos.Entidades;

public class Peluquero {
    private int idPeluquero;
    private String nombre;
    private String apellido;
    private String especialidad;
    private boolean activo;

    // Constructores, Getters y Setters (similar a Cliente)
    public Peluquero() {}
    
    public Peluquero(String nombre, String apellido, String especialidad, boolean activo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.activo = activo;
    }
    
    // Getters y Setters (genera con Alt+Insert)
    public int getIdPeluquero() { return idPeluquero; }
    public void setIdPeluquero(int idPeluquero) { this.idPeluquero = idPeluquero; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + especialidad + ")";
    }

    public void setTelefono(String telefono) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getTelefono() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}