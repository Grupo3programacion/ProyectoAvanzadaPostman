package co.edu.uniquindio.proyectoAvanzada.dto;



import co.edu.uniquindio.proyectoAvanzada.modelo.entidades.MensajePQRS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record InfoPQRSDTO (
        int codigo,
        String estado,
        int codigoCita,
        LocalDate fecha,
        String motivo
        //List<MensajePQRS> mensajes
){
}
