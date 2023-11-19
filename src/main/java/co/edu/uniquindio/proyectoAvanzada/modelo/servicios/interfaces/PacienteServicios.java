package co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces;

import co.edu.uniquindio.proyectoAvanzada.dto.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.Ciudad;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.EPS;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.TipoAlergia;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.TipoSangre;

import java.util.List;

public interface PacienteServicios {

    int registrarse(PacienteDTO pacienteDTO) throws Exception;

    int editarPerfil(PacienteDTO pacienteDTO) throws Exception;

    void recuperarPassword(RecuperarPasswordDTO dto) throws Exception;

    void eliminarCuenta(int idPaciente) throws Exception;

    void enviarLinkRecuperacion(String emailPaciente) throws Exception;

    int cambiarPassword(int idPaciente, String nuevaPassword) throws Exception;

    void agendarCita(CitaPacienteDTO citaPacienteDTO) throws Exception;

    void responderPQRS(GestionDTOPQRSPaciente gestionDTOPQRSPaciente) throws Exception;

    List<ItenCitaPacienteDTO> listarCitasPaciente(int idPaciente);

    List<MedicoDTOPaciente> filtrarCitaPorMedico(int idPaciente, int idMedico) throws Exception;

    List<FiltrarFechaPacienteDTO> filtrarCitaPorFecha(FiltrarFechaPacienteDTO filtrarFechaPacienteDTO) throws Exception;

    MedicoDTOPaciente verDetalleCita(int idCita) throws Exception;

    List<ConsultaMedicamentosDTOPaciente> consultaMedicamentos(int idMedicamento) throws Exception;
    PacienteDTO obtenerPaciente(int codigo) throws Exception;

    List<Ciudad> cargarCiudadesDesdeEnum() throws Exception;

    List<EPS> cargarEPSDesdeEnum() throws Exception;

    List<TipoSangre> cargarTipoSangreDesdeEnum() throws Exception;
    List<TipoAlergia> cargarTipoAlergiaDesdeEnum() throws Exception;
}