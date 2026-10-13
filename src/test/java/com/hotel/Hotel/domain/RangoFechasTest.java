package com.hotel.Hotel.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RangoFechasTest {

    @Test
    @DisplayName("Debe lanzar excepción cuando fecha fin es anterior a fecha inicio")
    void debeLanzarExcepcionConFechaFinAnterior() {
        // Arrange
        LocalDateTime fechaInicio = LocalDateTime.of(2026, 10, 15, 14, 0);
        LocalDateTime fechaFinAnterior = LocalDateTime.of(2026, 10, 10, 14, 0); // 5 días antes

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RangoFechas(fechaInicio, fechaFinAnterior),
                "Debe lanzar IllegalArgumentException cuando fechaFin < fechaInicio"
        );

        assertEquals("La fecha fin no puede ser anterior a la de inicio", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando fecha inicio es nula")
    void debeLanzarExcepcionConFechaInicioNula() {
        // Arrange
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(3);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RangoFechas(null, fechaFin),
                "Debe lanzar IllegalArgumentException cuando fechaInicio es nula"
        );

        assertEquals("Las fechas no pueden ser nulas", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando fecha fin es nula")
    void debeLanzarExcepcionConFechaFinNula() {
        // Arrange
        LocalDateTime fechaInicio = LocalDateTime.now();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RangoFechas(fechaInicio, null),
                "Debe lanzar IllegalArgumentException cuando fechaFin es nula"
        );

        assertEquals("Las fechas no pueden ser nulas", exception.getMessage());
    }

    @Test
    @DisplayName("Debe crear RangoFechas válido cuando fechas son correctas")
    void debeCrearRangoFechasValido() {
        // Arrange
        LocalDateTime fechaInicio = LocalDateTime.of(2026, 10, 15, 14, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2026, 10, 20, 12, 0);

        // Act
        RangoFechas rango = new RangoFechas(fechaInicio, fechaFin);

        // Assert
        assertNotNull(rango);
        assertEquals(fechaInicio, rango.fechaInicio());
        assertEquals(fechaFin, rango.fechaFin());
    }

    @Test
    @DisplayName("Debe calcular correctamente los días del rango")
    void debeCalcularDiasCorrectamente() {
        // Arrange
        LocalDateTime fechaInicio = LocalDateTime.of(2026, 10, 15, 14, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2026, 10, 20, 14, 0); // 5 días después

        // Act
        RangoFechas rango = new RangoFechas(fechaInicio, fechaFin);

        // Assert
        assertEquals(5, rango.getDias(), "Debe calcular 5 días de diferencia");
    }

    @Test
    @DisplayName("Debe permitir crear rango con fechas iguales (0 días)")
    void debePermitirRangoConFechasIguales() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.of(2026, 10, 15, 14, 0);

        // Act
        RangoFechas rango = new RangoFechas(fecha, fecha);

        // Assert
        assertNotNull(rango);
        assertEquals(0, rango.getDias(), "Debe calcular 0 días cuando las fechas son iguales");
    }
}
