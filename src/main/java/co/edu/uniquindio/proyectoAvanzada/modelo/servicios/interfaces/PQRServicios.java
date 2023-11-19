package co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces;

import co.edu.uniquindio.proyectoAvanzada.dto.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.EPS;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.TipoPqrs;

import java.util.List;

public interface PQRServicios {

    int crearPQR(CrearPQRSDTO crearPQRSDTO) throws Exception;
    int cambiarEstadoPQRS(int codigo, String estado) throws Exception;
    int responderPQRSAdmin(RegistroRespuestaDTO registroRespuestaDTO)throws Exception;
    int responderPQRSPaciente(RegistroRespuestaPacienteDTO registroRespuestaPacienteDTO)throws Exception;
    List<PQRSDTO> listarPQRSPaciente(int codigoPaciente) throws Exception;
    InfoPQRSDTO verDetallePQRS(int codigo)throws Exception;

    List<TipoPqrs> listarTiposPqrs() throws Exception;
    List<CitaPQRSDTO> listarCitasPqrs(int idPaciente) throws Exception;





}
