package co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces;

import co.edu.uniquindio.proyectoAvanzada.dto.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.Especialidad;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.TipoAlergia;

import java.util.List;

public interface MedicoServicios {

    List<CitasPendientesMedicoDetalleDTO> listarCitasPendientes(int idMedico) throws Exception;
    List<CitasPendientesMedicoDetalleDTO> listarHistorialCitas(int idMedico) throws Exception;

    String atenderCita(AtenderCitaDTOMedico atenderCitaDTOMedico) throws Exception;

    String agendarDiaLibre(DiaLibereDTOMedico diaLibereDTOMedico) throws Exception;

    List listarCitasRealizadasMedico(int idMedico) throws Exception;

    String asignarMedicamentosOrden( AsignacionMedicamentosDTO asignacionMedicamentosDTO )throws Exception;

    List<Especialidad> cargarEspecialidadDesdeEnum() throws Exception;

    List listarMedicosEspecialidad(Especialidad nombre) throws Exception;



    AtenderCitaDTOMedico verDetalleCitaAtendida(int idCita) throws Exception;



}
