package co.edu.uniquindio.proyectoAvanzada.dto;

import co.edu.uniquindio.proyectoAvanzada.modelo.entidades.MensajePQRS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PQRSDTO(

        LocalDate fecha,
        String motivo,
        int codigoCita,
        int idPqrs,
        int codigoPaciente,
        String estado
){
}
