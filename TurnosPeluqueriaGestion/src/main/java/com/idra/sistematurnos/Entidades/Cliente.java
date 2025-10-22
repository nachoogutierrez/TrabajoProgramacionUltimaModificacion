package com.idra.sistematurnos.Entidades;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private boolean activo;

    // Constructores
    public Cliente() {}
    
    public Cliente(String nombre, String apellido, String telefono, String email, boolean activo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.activo = activo;
    }
    
    // Getters y Setters (usa Alt+Insert para generarlos)
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    @Override
    public String toString() {
        return nombre + " " + apellido + " - " + telefono;
    }
}