package co.edu.uniquindio.proyectoAvanzada.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DiaLibereDTOMedico(
        int idMedico,
        LocalDate fecha,
        String comentario
) {
}
