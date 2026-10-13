package com.hotel.Hotel.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaCostoTest {

    @Test
    @DisplayName("Debe calcular costo total correctamente para múltiples días")
    void debeCalcularCostoTotalParaMultiplesDias() {
        // Arrange
        Cliente cliente = new Cliente("Roberto Silva", "roberto@correo.com");
        Habitacion habitacion = new HabitacionEstandar("401", 2, 100.0, 2);
        
        LocalDateTime fechaInicio = LocalDateTime.of(2026, 10, 15, 14, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2026, 10, 19, 14, 0); // 4 días completos
        RangoFechas periodo = new RangoFechas(fechaInicio, fechaFin);

        // Act
        Reserva reserva = new Reserva(cliente, habitacion, periodo);

        // Assert
        double costoEsperado = 100.0 * 4; // precio por noche * días
        assertEquals(costoEsperado, reserva.getCostoTotal(), 0.01, 
                "El costo debe ser 400.0 (100 * 4 días)");
    }

    @Test
    @DisplayName("Debe calcular costo mínimo de 1 día cuando el rango es 0 días")
    void debeCalcularCostoMinimoDe1Dia() {
        // Arrange
        Cliente cliente = new Cliente("Sandra Martínez", "sandra@correo.com");
        Habitacion habitacion = new SuitePresidencial("501", 4, 500.0, true, true);
        
        LocalDateTime fecha = LocalDateTime.of(2026, 10, 15, 14, 0);
        RangoFechas periodo = new RangoFechas(fecha, fecha); // 0 días
        
        // Act
        Reserva reserva = new Reserva(cliente, habitacion, periodo);

        // Assert
        double costoEsperado = 500.0 * 1; // Se aplica Math.max(1, periodo.getDias())
        assertEquals(costoEsperado, reserva.getCostoTotal(), 0.01, 
                "El costo mínimo debe ser 1 día (500.0)");
    }

    @Test
    @DisplayName("Debe calcular costo para habitación estándar con 2 días")
    void debeCalcularCostoHabitacionEstandar2Dias() {
        // Arrange
        Cliente cliente = new Cliente("Miguel Ángel", "miguel@correo.com");
        Habitacion habitacion = new HabitacionEstandar("402", 3, 75.0, 2);
        
        LocalDateTime fechaInicio = LocalDateTime.of(2026, 11, 1, 14, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2026, 11, 3, 14, 0); // 2 días completos
        RangoFechas periodo = new RangoFechas(fechaInicio, fechaFin);

        // Act
        Reserva reserva = new Reserva(cliente, habitacion, periodo);

        // Assert
        double costoEsperado = 75.0 * 2;
        assertEquals(costoEsperado, reserva.getCostoTotal(), 0.01, 
                "El costo debe ser 150.0 (75 * 2 días)");
    }

    @Test
    @DisplayName("Debe calcular costo para suite presidencial con 6 días")
    void debeCalcularCostoSuitePresidencial6Dias() {
        // Arrange
        Cliente cliente = new Cliente("Gabriela Fernández", "gabriela@correo.com");
        Habitacion habitacion = new SuitePresidencial("502", 6, 800.0, true, true);
        
        LocalDateTime fechaInicio = LocalDateTime.of(2026, 12, 20, 15, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2026, 12, 26, 15, 0); // 6 días completos
        RangoFechas periodo = new RangoFechas(fechaInicio, fechaFin);

        // Act
        Reserva reserva = new Reserva(cliente, habitacion, periodo);

        // Assert
        double costoEsperado = 800.0 * 6;
        assertEquals(costoEsperado, reserva.getCostoTotal(), 0.01, 
                "El costo debe ser 4800.0 (800 * 6 días)");
    }

    @Test
    @DisplayName("Debe mantener el costo calculado inicialmente inmutable")
    void debeMantenrCostoInicialInmutable() {
        // Arrange
        Cliente cliente = new Cliente("Fernando López", "fernando@correo.com");
        Habitacion habitacion = new HabitacionEstandar("403", 2, 120.0, 1);
        
        LocalDateTime fechaInicio = LocalDateTime.of(2026, 10, 15, 14, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2026, 10, 17, 14, 0); // 2 días completos
        RangoFechas periodo = new RangoFechas(fechaInicio, fechaFin);

        // Act
        Reserva reserva = new Reserva(cliente, habitacion, periodo);
        double costoInicial = reserva.getCostoTotal();
        
        // Confirmar reserva (no debe cambiar el costo)
        reserva.confirmar();

        // Assert
        assertEquals(costoInicial, reserva.getCostoTotal(), 0.01, 
                "El costo no debe cambiar después de confirmar");
        assertEquals(240.0, reserva.getCostoTotal(), 0.01, 
                "El costo debe ser 240.0 (120 * 2 días)");
    }
}
