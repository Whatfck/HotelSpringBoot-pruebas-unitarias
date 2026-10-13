package com.hotel.Hotel.service;

import com.hotel.Hotel.domain.*;
import com.hotel.Hotel.dto.request.CrearReservaRequest;
import com.hotel.Hotel.exception.ReglaNegocioException;
import com.hotel.Hotel.mapper.ReservaMapper;
import com.hotel.Hotel.repository.ClienteRepository;
import com.hotel.Hotel.repository.HabitacionRepository;
import com.hotel.Hotel.repository.ReservaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceClienteInactivoTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private ReservaMapper reservaMapper;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    @DisplayName("Debe lanzar excepción al intentar crear reserva con cliente inactivo")
    void debeLanzarExcepcionConClienteInactivo() {
        // Arrange
        UUID clienteId = UUID.randomUUID();
        UUID habitacionId = UUID.randomUUID();
        
        Cliente clienteInactivo = new Cliente("Carlos Penalizado", "carlos@correo.com");
        clienteInactivo.registrarPenalizacion();
        clienteInactivo.registrarPenalizacion();
        clienteInactivo.registrarPenalizacion(); // Cliente ahora inactivo
        
        Habitacion habitacion = new HabitacionEstandar("301", 2, 150.0, 2);
        
        CrearReservaRequest request = new CrearReservaRequest(
                clienteId,
                habitacionId,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3)
        );

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteInactivo));
        when(habitacionRepository.findById(habitacionId)).thenReturn(Optional.of(habitacion));

        // Act & Assert
        ReglaNegocioException exception = assertThrows(
                ReglaNegocioException.class,
                () -> reservaService.crear(request),
                "Debe lanzar ReglaNegocioException para cliente inactivo"
        );

        assertEquals(
                "El cliente está inactivo o penalizado y no puede crear reservas.",
                exception.getMessage()
        );

        // Verificar que no se intentó guardar ninguna reserva
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    @DisplayName("Debe permitir crear reserva con cliente activo sin penalizaciones")
    void debePermitirCrearReservaConClienteActivo() {
        // Arrange
        UUID clienteId = UUID.randomUUID();
        UUID habitacionId = UUID.randomUUID();
        
        Cliente clienteActivo = new Cliente("Laura Activa", "laura@correo.com");
        Habitacion habitacion = new HabitacionEstandar("302", 2, 150.0, 2);
        
        CrearReservaRequest request = new CrearReservaRequest(
                clienteId,
                habitacionId,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3)
        );

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteActivo));
        when(habitacionRepository.findById(habitacionId)).thenReturn(Optional.of(habitacion));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act & Assert
        assertDoesNotThrow(() -> reservaService.crear(request));
        
        // Verificar que se guardó la reserva
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }
}
