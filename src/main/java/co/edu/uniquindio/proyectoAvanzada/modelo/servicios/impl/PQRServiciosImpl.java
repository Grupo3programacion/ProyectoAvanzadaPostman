package co.edu.uniquindio.proyectoAvanzada.modelo.servicios.impl;

import co.edu.uniquindio.proyectoAvanzada.dto.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.entidades.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.Especialidad;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.TipoPqrs;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.PQRServicios;
import co.edu.uniquindio.proyectoAvanzada.repositorios.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//por cada una de las interfaces debe tener un implement

@Service
@RequiredArgsConstructor
public class PQRServiciosImpl implements PQRServicios {

    private final CitaRepo citaRepo;
    private final PQRSRepo pqrsRepo;
    private final MensajeRepo mensajeRepo;
    private final AdministradorRepo administradorRepo;
    private final PacienteRepo pacienteRepo;

    @Override
    public int crearPQR(CrearPQRSDTO crearPQRSDTO) throws Exception {
        List<PQRS> pqrs1 = pqrsRepo.findAllByIdPacienteAndEstado(crearPQRSDTO.codigoPaciente(), "ABIERTA");
        if (pqrs1.size() > 3){
            throw new Exception(("No puede registar más PQRS"));
        }

        Optional<Cita> optional = citaRepo.findById(crearPQRSDTO.idCita());
        if (optional.isEmpty()){
            throw new Exception(("El codigo "+crearPQRSDTO.idCita() +" de la cita no es valido"));
        }

        PQRS pqrs = new PQRS();
        pqrs.setCita(optional.get());
        pqrs.setMotivo(crearPQRSDTO.motivo());
        pqrs.setFechaCreacion(LocalDate.now());
        pqrs.setIdPaciente(crearPQRSDTO.codigoPaciente());
        pqrs.setEstado("Abierta");

        PQRS pqrsNew = pqrsRepo.save(pqrs);
        return pqrsNew.getIdPQRS();
    }

    @Override
    public int cambiarEstadoPQRS(int codigoPQRS, String estadoPQRS) throws Exception {
        Optional<PQRS> opcional = pqrsRepo.findById(codigoPQRS);
        if (opcional.isEmpty()){
            throw new Exception(("El codigo"+codigoPQRS+"no esta asociado a ningun PQRS"));
        }
        PQRS pqrs = opcional.get();
        pqrs.setEstado(estadoPQRS);
        pqrsRepo.save(pqrs);
        return pqrs.getIdPQRS();
    }

    @Override
    public int responderPQRSAdmin(RegistroRespuestaDTO registroRespuestaDTO) throws Exception {
        //obtener el PQRS
        Optional<PQRS> opcional = pqrsRepo.findById(registroRespuestaDTO.codigoPQRS());
        if (opcional.isEmpty()){
            throw new Exception(("El codigo "+registroRespuestaDTO.codigoPQRS()+" no esta asociado a ningun PQRS"));
        }
        //obtener LA CUENTAPACIENTE
        Optional<Paciente> opcionalCuenta = pacienteRepo.findById(registroRespuestaDTO.codigoCuenta());
        if (opcionalCuenta.isEmpty()){
            throw new Exception(("El codigo " + registroRespuestaDTO.codigoCuenta() +" no esta asociado a ningun paciente"));
        }

        //obtener LA CUENTA
        Optional<Administrador> opcionalAdministrador = administradorRepo.findById(registroRespuestaDTO.codigoCuenta());
        if (opcionalAdministrador.isEmpty()){
            throw new Exception(("El codigo " + registroRespuestaDTO.idAdministrador()+" no esta asociado a ningun administrador"));
        }
        MensajePQRS mensajePQRS = new MensajePQRS();
        mensajePQRS.setPqrs(opcional.get());
        mensajePQRS.setAdministrador(opcionalAdministrador.get());
        mensajePQRS.setFecha(LocalDateTime.now());
        mensajePQRS.setMensajePQRS(registroRespuestaDTO.mensaje());
        return mensajeRepo.save(mensajePQRS).getId();
    }
    @Override
    public int responderPQRSPaciente(RegistroRespuestaPacienteDTO registroRespuestaPacienteDTO) throws Exception {

        //obtener LA CUENTAPACIENTE
        Optional<Paciente> opcionalCuenta = pacienteRepo.findById(registroRespuestaPacienteDTO.codigoCuenta());
        if (opcionalCuenta.isEmpty()){
            throw new Exception(("El codigo " + registroRespuestaPacienteDTO.codigoCuenta() +" no esta asociado a ningun paciente"));
        }

        //obtener el PQRS
        Optional<PQRS> opcional = pqrsRepo.findById(registroRespuestaPacienteDTO.codigoPQRS());
        if (opcional.isEmpty()){
            throw new Exception(("El codigo "+registroRespuestaPacienteDTO.codigoPQRS() +" no esta asociado a ningun PQRS"));
        }

        MensajePQRS mensajePQRS = new MensajePQRS();
        mensajePQRS.setPqrs(opcional.get());
        mensajePQRS.setFecha(LocalDateTime.now());
        mensajePQRS.setMensajePQRS(registroRespuestaPacienteDTO.mensaje());
        return mensajeRepo.save(mensajePQRS).getId();
    }


    @Override
    public List<PQRSDTO> listarPQRSPaciente(int codigoPaciente) throws Exception {
        List<PQRS>listaPqrs = pqrsRepo.findAllByCitaPacienteIdPaciente(codigoPaciente);
        List <PQRSDTO> respuesta = new ArrayList<>();

        for (PQRS pqrs : listaPqrs){
            respuesta.add(new PQRSDTO(
                    pqrs.getFechaCreacion(),
                    pqrs.getMotivo(),
                    pqrs.getCita().getIdCita(),
                    pqrs.getIdPQRS(),
                    pqrs.getCita().getPaciente().getIdPaciente(),
                    pqrs.getEstado()));
        }
        return respuesta;
    }

    @Override
    public InfoPQRSDTO verDetallePQRS(int codigo) throws Exception {
        Optional<PQRS> opcional = pqrsRepo.findById(codigo);
        if (opcional.isEmpty()){
            throw new Exception(("El codigo "+codigo+" no esta asociado a ningun PQRS"));
        }
        PQRS pqrs = opcional.get();

        return new InfoPQRSDTO(
                pqrs.getIdPQRS(),
                pqrs.getEstado(),
                pqrs.getCita().getIdCita(),
                pqrs.getFechaCreacion(),
                pqrs.getMotivo());
    }

    @Override
    public List<TipoPqrs> listarTiposPqrs() {
        List<TipoPqrs> tipoPqrs = new ArrayList<>();

        for (TipoPqrs tipoPqrsEnum : TipoPqrs.values()) {
            tipoPqrs.add(tipoPqrsEnum);
        }
        return tipoPqrs;
    }

    public List<CitaPQRSDTO> listarCitasPqrs(int idPaciente) throws Exception {

        // Obtener el medicamento por ID
        List<Cita> cita = citaRepo.findAllByPacienteIdPaciente(idPaciente);
        if (cita.isEmpty()) {
            throw new Exception("No hay citas asociadas al paciente.");
        }

        List<CitaPQRSDTO> citaPQRSDTO = new ArrayList<>();

        for (Cita m : cita) {
            citaPQRSDTO.add(new CitaPQRSDTO(
                    m.getIdCita(),
                    m.getMedico().getEspecilidadMedico() + "-" + m.getMedico().getNombreMedico()
                            + "-" + m.getFechaCreacion()
            ));
        }
        return citaPQRSDTO;
    }

}