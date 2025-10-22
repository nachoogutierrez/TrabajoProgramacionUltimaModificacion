package com.idra.sistematurnos.Persistencia;

import com.idra.sistematurnos.Entidades.Cliente;

public class TestClienteDAO {
    public static void main(String[] args) {
        try {
            ClienteDAO dao = new ClienteDAOImpl();

            // Crear cliente de prueba
            Cliente cliente = new Cliente();
            cliente.setNombre("Ana");
            cliente.setApellido("García");
            cliente.setTelefono("123456789");
            cliente.setEmail("ana@email.com");
            cliente.setActivo(true);

            dao.crear(cliente);
            System.out.println(":white_check_mark: Cliente creado exitosamente! ID: " + cliente.getIdCliente());

            // Listar todos los clientes
            System.out.println(":clipboard: Todos los clientes:");
            for (Cliente c : dao.listarTodos()) {
                System.out.println(" - " + c.getNombre() + " " + c.getApellido());
            }

        } catch (Exception e) {
            System.out.println(":x: Error: " + e.getMessage());
            e.printStackTrace();
        
    }
    }
}
