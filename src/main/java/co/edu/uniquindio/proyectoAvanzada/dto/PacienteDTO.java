package co.edu.uniquindio.proyectoAvanzada.dto;

import co.edu.uniquindio.proyectoAvanzada.modelo.enums.Ciudad;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.EPS;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.TipoAlergia;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.TipoSangre;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PacienteDTO(
        String nombrePaciente,
        String apellidoPaciente,
        int cedula,
        //int idPaciente,
        LocalDate fechaNacimiento,
        int telefonoPaciente,
        String emailPaciente,
        EPS epsPaciente,
        TipoSangre tipoSangre,
        Ciudad ciudadPaciente,
        String fotoPaciente,
        TipoAlergia tipoAlergia,
        String contrasenia,
        String confirmarContrasenia

) {
}
