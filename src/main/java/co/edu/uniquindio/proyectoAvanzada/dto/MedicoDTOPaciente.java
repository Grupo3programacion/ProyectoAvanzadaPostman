package co.edu.uniquindio.proyectoAvanzada.dto;

import co.edu.uniquindio.proyectoAvanzada.modelo.enums.Especialidad;

import java.time.LocalDateTime;

public record MedicoDTOPaciente(
        int idCita,
        int idPaciente,
        int codigoMedico,
        String nombreMedico,
        Especialidad especialidad,
        LocalDateTime fecha,
        String motivo
) {
}
