package com.hotel.Hotel.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HabitacionTest {

    @Test
    @DisplayName("Debe lanzar excepción al asignar habitación ya ocupada")
    void debeLanzarExcepcionAlAsignarHabitacionOcupada() {
        // Arrange
        Habitacion habitacion = new HabitacionEstandar("201", 2, 150.0, 2);
        habitacion.asignarAReserva(); // Primera asignación - ahora está OCUPADA

        // Act & Assert
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> habitacion.asignarAReserva(),
                "Debe lanzar IllegalStateException al intentar asignar habitación ocupada"
        );

        assertEquals("La habitación no está disponible", exception.getMessage());
        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado());
    }

    @Test
    @DisplayName("Debe asignar habitación disponible exitosamente")
    void debeAsignarHabitacionDisponible() {
        // Arrange
        Habitacion habitacion = new HabitacionEstandar("202", 3, 180.0, 2);
        
        // Verificar estado inicial
        assertEquals(EstadoHabitacion.DISPONIBLE, habitacion.getEstado());

        // Act
        habitacion.asignarAReserva();

        // Assert
        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado(), 
                "La habitación debe cambiar a OCUPADA después de asignar");
    }

    @Test
    @DisplayName("Debe habilitar habitación ocupada y cambiarla a disponible")
    void debeHabilitarHabitacionOcupada() {
        // Arrange
        Habitacion habitacion = new HabitacionEstandar("203", 2, 150.0, 1);
        habitacion.asignarAReserva(); // Ocupar habitación
        
        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado());

        // Act
        habitacion.habilitar();

        // Assert
        assertEquals(EstadoHabitacion.DISPONIBLE, habitacion.getEstado(), 
                "La habitación debe cambiar a DISPONIBLE después de habilitar");
    }

    @Test
    @DisplayName("Debe crear habitación estándar con estado inicial DISPONIBLE")
    void debeCrearHabitacionEstandarDisponible() {
        // Act
        Habitacion habitacion = new HabitacionEstandar("204", 2, 120.0, 2);

        // Assert
        assertNotNull(habitacion);
        assertEquals(EstadoHabitacion.DISPONIBLE, habitacion.getEstado(), 
                "Una habitación nueva debe estar DISPONIBLE");
        assertEquals("204", habitacion.getNumero());
        assertEquals(2, habitacion.getCapacidadMaxima());
        assertEquals(120.0, habitacion.getPrecioPorNoche(), 0.01);
    }

    @Test
    @DisplayName("Debe crear suite presidencial con estado inicial DISPONIBLE")
    void debeCrearSuitePresidencialDisponible() {
        // Act
        Habitacion suite = new SuitePresidencial("501", 6, 1000.0, true, true);

        // Assert
        assertNotNull(suite);
        assertEquals(EstadoHabitacion.DISPONIBLE, suite.getEstado(), 
                "Una suite nueva debe estar DISPONIBLE");
        assertEquals("501", suite.getNumero());
        assertEquals(6, suite.getCapacidadMaxima());
        assertEquals(1000.0, suite.getPrecioPorNoche(), 0.01);
    }

    @Test
    @DisplayName("Debe permitir reasignar habitación después de habilitarla")
    void debePermitirReasignarDespuesDeHabilitar() {
        // Arrange
        Habitacion habitacion = new HabitacionEstandar("205", 2, 150.0, 2);
        
        // Primera asignación
        habitacion.asignarAReserva();
        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado());
        
        // Liberar
        habitacion.habilitar();
        assertEquals(EstadoHabitacion.DISPONIBLE, habitacion.getEstado());

        // Act - Segunda asignación
        assertDoesNotThrow(() -> habitacion.asignarAReserva(), 
                "Debe permitir reasignar después de habilitar");

        // Assert
        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado());
    }

    @Test
    @DisplayName("Debe lanzar excepción al crear habitación con número vacío")
    void debeLanzarExcepcionConNumeroVacio() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new HabitacionEstandar("", 2, 150.0, 2),
                "Debe lanzar IllegalArgumentException con número vacío"
        );

        assertEquals("Número obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción al crear habitación con capacidad menor a 1")
    void debeLanzarExcepcionConCapacidadInvalida() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new HabitacionEstandar("206", 0, 150.0, 2),
                "Debe lanzar IllegalArgumentException con capacidad 0"
        );

        assertEquals("Capacidad mínima: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción al crear habitación con precio negativo")
    void debeLanzarExcepcionConPrecioNegativo() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new HabitacionEstandar("207", 2, -50.0, 2),
                "Debe lanzar IllegalArgumentException con precio negativo"
        );

        assertEquals("Precio no puede ser negativo", exception.getMessage());
    }
}
