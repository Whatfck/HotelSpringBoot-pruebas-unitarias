package com.hotel.Hotel.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    @DisplayName("Debe mantener activo al cliente con menos de 3 penalizaciones")
    void debeMantenereActivoConMenosDe3Penalizaciones() {
        // Arrange
        Cliente cliente = new Cliente("Juan Pérez", "juan@correo.com");

        // Act
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();

        // Assert
        assertTrue(cliente.isActivo(), "El cliente debe seguir activo con 2 penalizaciones");
        assertEquals(2, cliente.getPenalizaciones(), "Debe tener exactamente 2 penalizaciones");
    }

    @Test
    @DisplayName("Debe inactivar al cliente al alcanzar 3 penalizaciones")
    void debeInactivarClienteConTresPenalizaciones() {
        // Arrange
        Cliente cliente = new Cliente("María López", "maria@correo.com");

        // Act
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion(); // Tercera penalización

        // Assert
        assertFalse(cliente.isActivo(), "El cliente debe estar inactivo al alcanzar 3 penalizaciones");
        assertEquals(3, cliente.getPenalizaciones(), "Debe tener exactamente 3 penalizaciones");
    }

    @Test
    @DisplayName("Debe reactivar al cliente y resetear penalizaciones")
    void debeReactivarClienteYResetearPenalizaciones() {
        // Arrange
        Cliente cliente = new Cliente("Pedro Gómez", "pedro@correo.com");
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();

        // Act
        cliente.reactivar();

        // Assert
        assertTrue(cliente.isActivo(), "El cliente debe estar activo después de reactivar");
        assertEquals(0, cliente.getPenalizaciones(), "Las penalizaciones deben resetearse a 0");
    }

    @Test
    @DisplayName("Cliente activo puede realizar reservas")
    void clienteActivoPuedeRealizarReservas() {
        // Arrange
        Cliente cliente = new Cliente("Ana Torres", "ana@correo.com");

        // Act & Assert
        assertTrue(cliente.puedeRealizarReservas(), "Un cliente activo debe poder realizar reservas");
    }

    @Test
    @DisplayName("Cliente inactivo no puede realizar reservas")
    void clienteInactivoNoPuedeRealizarReservas() {
        // Arrange
        Cliente cliente = new Cliente("Luis Ramírez", "luis@correo.com");
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();

        // Act & Assert
        assertFalse(cliente.puedeRealizarReservas(), "Un cliente inactivo no debe poder realizar reservas");
    }
}
