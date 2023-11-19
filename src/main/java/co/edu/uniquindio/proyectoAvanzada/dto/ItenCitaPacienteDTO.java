package co.edu.uniquindio.proyectoAvanzada.dto;

import co.edu.uniquindio.proyectoAvanzada.modelo.enums.Especialidad;

import java.time.LocalDateTime;

public record ItenCitaPacienteDTO(
        int idCita,
        LocalDateTime fecha,
                                  String nombreMedico,
                                  Especialidad especialidad) {
}
