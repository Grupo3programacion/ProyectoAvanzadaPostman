package co.edu.uniquindio.proyectoAvanzada.dto;

import co.edu.uniquindio.proyectoAvanzada.modelo.enums.EstadoCita;

import java.time.LocalDateTime;

public record CitasPendientesMedicoDetalleDTO(
        LocalDateTime fecha,
        int idMedico,
        int idCita,
        String nombrePaciente,
        EstadoCita estado
        //  String seleccionar
) {
}
