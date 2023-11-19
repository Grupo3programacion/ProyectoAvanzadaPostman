package co.edu.uniquindio.proyectoAvanzada.modelo.servicios.impl;

import co.edu.uniquindio.proyectoAvanzada.dto.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.entidades.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.EmailServicio;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.PacienteServicios;
import co.edu.uniquindio.proyectoAvanzada.repositorios.*;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor

public class PacienteServiciosImpl implements PacienteServicios {

    private final PacienteRepo pacienteRepo;
    private final MensajeRepo mensajeRepo;
    private final MedicoRepo medicoRepo;
    private final GestionCitaRepo citaRepo;
    private final AdministradorRepo administradorRepo;
    private final PQRSRepo pqrsRepo;
    private final EmailServicio emailServicio;
    private final MedicamentoRepo medicamentoRepo;
    private final AutorizacionMedicamentoRepo setFechaAutorizacion;
    private final AutorizacionMedicamentoRepo autorizacionMedicamentoRepo;
    private final ConsultaMedicamentosRepo consultaMedicamentosRepo;

    @Override
    public int registrarse(PacienteDTO pacienteDTO) throws Exception {

        Optional<Paciente> buscado = pacienteRepo.findByCedula(pacienteDTO.cedula());

        if(buscado.isPresent() ){
            throw new Exception("El paciente con cedula "+pacienteDTO.cedula()+" ya esta registrado");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Paciente paciente = new Paciente();

        paciente.setNombrePaciente(pacienteDTO.nombrePaciente());
        paciente.setApellidoPaciente(pacienteDTO.apellidoPaciente());
        paciente.setCedula(pacienteDTO.cedula() );
        paciente.setFecheNacimiento(pacienteDTO.fechaNacimiento());
        paciente.setTelefonoPaciente(pacienteDTO.telefonoPaciente());
        paciente.setCiudadPaciente(pacienteDTO.ciudadPaciente());
        paciente.setEmaiLPaciente(pacienteDTO.emailPaciente() );
        paciente.setContraseniaPaciente( passwordEncoder.encode(pacienteDTO.contrasenia()) );
        paciente.setEpsPaciente(pacienteDTO.epsPaciente());
        paciente.setTipoSangre(pacienteDTO.tipoSangre());
        paciente.setTipoAlergia(pacienteDTO.tipoAlergia());
        paciente.setFotoPaciente(pacienteDTO.fotoPaciente());
        paciente.setEstado(true);


        Paciente pacienteNuevo = pacienteRepo.save(paciente);
        return pacienteNuevo.getIdPaciente();

    }

    @Override
    public int editarPerfil(PacienteDTO pacienteDTO) throws Exception {
        Optional<Paciente> buscado = pacienteRepo.findByCedula(pacienteDTO.cedula());

        if(buscado.isEmpty() ){
            throw new Exception("El código "+pacienteDTO.cedula()+" no existe");
        }

        Paciente paciente = buscado.get();

        paciente.setNombrePaciente(pacienteDTO.nombrePaciente());
        paciente.setApellidoPaciente(pacienteDTO.apellidoPaciente());
        paciente.setFecheNacimiento(pacienteDTO.fechaNacimiento());
        paciente.setTelefonoPaciente(pacienteDTO.telefonoPaciente());
        paciente.setCiudadPaciente(pacienteDTO.ciudadPaciente());
        paciente.setEmaiLPaciente(pacienteDTO.emailPaciente() );
        paciente.setEpsPaciente(pacienteDTO.epsPaciente());
        paciente.setTipoSangre(pacienteDTO.tipoSangre());
        paciente.setTipoAlergia(pacienteDTO.tipoAlergia());
        paciente.setFotoPaciente(pacienteDTO.fotoPaciente());

        pacienteRepo.save(paciente);
        return paciente.getIdPaciente();
    }

    @Override
    public void recuperarPassword(RecuperarPasswordDTO dto) throws Exception {

        Optional<Paciente> optionalCuenta = pacienteRepo.findByEmaiLPaciente(dto.emailPaciente());

        if (optionalCuenta.isEmpty()){
            throw new Exception("no existe una cuenta con el correo " + dto.emailPaciente());
        }

        Paciente paciente = optionalCuenta.get();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if( dto.nuevaContrasenia().equals( dto.confirmarContrasenia() ) ){
            paciente.setContraseniaPaciente( passwordEncoder.encode( dto.nuevaContrasenia() ) );
            pacienteRepo.save(paciente);
        }else{
            throw new Exception("Las contraseñas no coinciden");
        }
    }

    @Override
    public void eliminarCuenta(int idPaciente) throws Exception {

        //findById es para buscar
        Optional<Paciente> buscado = pacienteRepo.findById(idPaciente);

        if(buscado.isEmpty() ){
            throw new Exception("El código "+idPaciente+" no existe");
        }

        //medicoRepo.delete( buscado.get() );
        Paciente obtenido = buscado.get();
        obtenido.setEstado( false );

        pacienteRepo.save(obtenido);
    }

    @Override
    public void enviarLinkRecuperacion(String emailPaciente) throws Exception {

        Optional<Paciente> optional = pacienteRepo.findByEmaiLPaciente(emailPaciente);
        if (optional.isEmpty()){
            throw new Exception("no existe una cuenta con el correo" + emailPaciente);
        }

        LocalDateTime fecha= LocalDateTime.now();
        String parametro= Base64.getEncoder().encodeToString((optional.get().getIdPaciente()+";"+fecha).getBytes());

        emailServicio.enviarCorreo(new EmailDTO(
                "Recuperacion de contraseña",
                "Hola, para recuperar su contraseña, por favor ingrese al link",
                optional.get().getEmaiLPaciente()
        ));

    }

    @Override
    public int cambiarPassword(int idPaciente, String nuevaPassword) throws Exception {

        Optional<Paciente> buscado = pacienteRepo.findById(idPaciente);

        if (!buscado.isPresent()) {
            throw new NoSuchElementException("El código " + idPaciente + " no existe");
        }

        Paciente paciente = buscado.get();

        // Asegurarse de que la contraseña no sea nula antes de establecerla
        if (nuevaPassword != null) {
            paciente.setContraseniaPaciente(nuevaPassword);
        }

        // Considera manejar la lógica de hash y salting para contraseñas aquí si es necesario

        Paciente pacienteNuevo = pacienteRepo.save(paciente);
        return pacienteNuevo.getIdPaciente();
    }


    @Override
    public void agendarCita(CitaPacienteDTO citaPacienteDTO) throws Exception {
        // Buscar el paciente por ID
        Optional<Paciente> pacienteOptional = pacienteRepo.findById( citaPacienteDTO.codigoPaciente() );

        if (pacienteOptional.isEmpty()) {
            throw new Exception("Paciente con ID " + citaPacienteDTO.codigoPaciente() + " no encontrado");
        }

        List<Cita> cita = citaRepo.findAllByPacienteIdPaciente(citaPacienteDTO.codigoPaciente() );
        if (cita.size() > 3) {
            throw new Exception("No puedes registrar más citas");
        }

        Optional<Medico> medicoOptional = medicoRepo.findById( citaPacienteDTO.codigoMedico() );

        if (medicoOptional.isEmpty()) {
            throw new Exception("Médico con ID " + citaPacienteDTO.codigoMedico() + " no encontrado");
        }

        Paciente paciente = pacienteOptional.get();
        Medico medico = medicoOptional.get();

        // Crear una nueva cita
        Cita nuevaCita = new Cita();
        nuevaCita.setMedico( medico );
        nuevaCita.setMotivoConsulta(citaPacienteDTO.motivo());
        nuevaCita.setFechaCreacion(citaPacienteDTO.fecha());
        nuevaCita.setEstadoCita(EstadoCita.ABIERTA);
        // Asignar el paciente a la cita
        nuevaCita.setPaciente(paciente);

        // Guardar la nueva cita
        citaRepo.save(nuevaCita);
    }


    @Override
    public void responderPQRS(GestionDTOPQRSPaciente gestionDTOPQRSPaciente) throws Exception {

        // Obtener el PQRS
        Optional<PQRS> opcionalPqrs = pqrsRepo.findById(gestionDTOPQRSPaciente.idPQRS());

        if (opcionalPqrs.isEmpty()) {
            throw new Exception("El código " + gestionDTOPQRSPaciente.idPQRS() + " no está asociado a ningún PQRS");
        }

        // Obtener la cuenta
        Optional<Administrador> opcionalAdministrador = administradorRepo.findById(gestionDTOPQRSPaciente.idAmin());

        if (opcionalAdministrador.isEmpty()) {
            throw new Exception("El código " + gestionDTOPQRSPaciente.idPQRS() + " no está asociado a ninguna cuenta");
        }

        MensajePQRS mensaje = new MensajePQRS();
        mensaje.setFecha(LocalDateTime.now());
        mensaje.setMensajePQRS(gestionDTOPQRSPaciente.comentarios());
        mensaje.setPqrs(opcionalPqrs.get());
        mensaje.setAdministrador( (Administrador) opcionalAdministrador.get());

        mensajeRepo.save(mensaje);

    }

    @Override
    public List<ItenCitaPacienteDTO> listarCitasPaciente(int idPaciente) {

        // Obtener todas las citas asociadas al paciente por su ID
        List<Cita> citasPaciente = citaRepo.findAll();
        List<ItenCitaPacienteDTO> respuesta = new ArrayList<>();

        // Puedes procesar las citas según tus necesidades
        for (Cita cita : citasPaciente) {
            if( cita.getPaciente().getIdPaciente() == idPaciente ){
                respuesta.add( new ItenCitaPacienteDTO(
                        cita.getIdCita(),
                        cita.getFechaCreacion(),
                        cita.getMedico().getNombreMedico(),
                        cita.getMedico().getEspecilidadMedico()
                ) );
            }
        }

        return respuesta;
    }


    @Override
    public List<MedicoDTOPaciente> filtrarCitaPorMedico(int idPaciente, int idMedico) throws Exception {

        //Primero validar que idPaciente y idMedico existan

        Optional<Medico> opcionalMedico = medicoRepo.findById(idMedico);

        if (opcionalMedico.isEmpty()) {
            throw new Exception("El código " + idMedico + " no está asociado a ningún paciente");
        }
        //Traerse todas las citas del paciente
        Optional<Paciente> opcionalPaciente = pacienteRepo.findById(idPaciente);

        if (opcionalPaciente.isEmpty()) {
            throw new Exception("El código " + idPaciente + " no está asociado a ningún medico");
        }

        //filtrar las citas del paciante por medico

        List<Cita> listaCitas = citaRepo.findAll();
        List<MedicoDTOPaciente> citasPaciente = new ArrayList<>();

        for (Cita c : listaCitas) {

            citasPaciente.add(new MedicoDTOPaciente(

                    c.getIdCita(),
                    c.getPaciente().getIdPaciente(),
                    c.getMedico().getIdMedico(),
                    c.getMedico().getNombreMedico(),
                    c.getMedico().getEspecilidadMedico(),
                    c.getFechaCreacion(),
                    c.getMotivoConsulta()
            ));
        }

        return citasPaciente;
    }

    public List<FiltrarFechaPacienteDTO> filtrarCitaPorFecha(FiltrarFechaPacienteDTO filtrarFechaPacienteDTO) throws Exception {

        //Primero validar que idPaciente
        Optional<Paciente> opcionalPaciente = pacienteRepo.findById(filtrarFechaPacienteDTO.idPaciente());

        if (opcionalPaciente.isEmpty()) {
            throw new Exception("El código " + filtrarFechaPacienteDTO.idPaciente() + " no está asociado a ninguna cita");
        }
        //filtrar las citas del paciante por fecha

        List<Cita> listaCitasFecha = citaRepo.findAll();
        List<FiltrarFechaPacienteDTO> filtrarCitaXFecha = new ArrayList<>();

        for (Cita c : listaCitasFecha) {
// faltar filtrar fecha
            if (filtrarFechaPacienteDTO.fechaCreacion()== c.getFechaCreacion()){

                filtrarCitaXFecha.add(new FiltrarFechaPacienteDTO(
                        c.getPaciente().getIdPaciente(),
                        c.getFechaCreacion(),
                        c.getMedico().getNombreMedico(),
                        c.getMedico().getEspecilidadMedico()
                ));} else {System.out.println("No hay cita o informacion correspondiente a la fecha ingresada");
            }
        }

        return filtrarCitaXFecha;
    }

    public MedicoDTOPaciente verDetalleCita(int idCita) throws Exception {

        //Primero validar que idPaciente
       // Optional<Paciente> opcionalPaciente = pacienteRepo.findById(verDetalleCitaDTO.idPaciente());

        //if (opcionalPaciente.isEmpty()) {
         //   throw new Exception("El código " + verDetalleCitaDTO.idPaciente()+ " no está asociado a ninguna cita");
       // }
        //Primero validar que idCita
        Optional<Cita> opcionalCita = citaRepo.findById(idCita);

        if (opcionalCita.isEmpty()) {
            throw new Exception("El código " + idCita+ " no está asociado al paciente");
        }
        //filtrar las citas del paciante por medico
        Cita obtenido = opcionalCita.get();
        MedicoDTOPaciente citasPaciente = new MedicoDTOPaciente(

                obtenido.getIdCita(),
                obtenido.getPaciente().getIdPaciente(),
                obtenido.getMedico().getIdMedico(),
                obtenido.getMedico().getNombreMedico(),
                obtenido.getMedico().getEspecilidadMedico(),
                obtenido.getFechaCreacion(),
                obtenido.getMotivoConsulta()
            );


        return citasPaciente;
    }

    public List<ConsultaMedicamentosDTOPaciente> consultaMedicamentos(int idMedicamento) throws Exception {

        // Obtener el medicamento por ID
        Optional<Medicamento> optionalMedicamento = medicamentoRepo.findById(idMedicamento);
        if (optionalMedicamento.isEmpty()) {
            throw new Exception("El medicamento con el ID " + idMedicamento + " no existe");
        }

        // Filtrar medicamentos y construir la lista de consulta
        List<Medicamento> listaMedicamento = medicamentoRepo.findAll();
        List<ConsultaMedicamentosDTOPaciente> listaMedicina = new ArrayList<>();

        for (Medicamento m : listaMedicamento) {
            listaMedicina.add(new ConsultaMedicamentosDTOPaciente(

                    m.getIdMedicamento(),
                    LocalDateTime.now(),
                    m.getNombreMedicamento(),
                    m.getCantidadMedicamentos(),
                    m.getDosis()
            ));
        }

        return listaMedicina;
    }

    @Override
    public PacienteDTO obtenerPaciente(int codigo) throws Exception {
        Optional<Paciente> buscado = pacienteRepo.findById(codigo);
        if(buscado.isEmpty() ){
            throw new Exception("El código "+codigo+" no existe");
        }
        Paciente obtenido = buscado.get();

        PacienteDTO pacienteDTO = new PacienteDTO(
                //se debe manejar el orden del constructor porque si no da error

                obtenido.getNombrePaciente(),
                obtenido.getApellidoPaciente(),
                obtenido.getCedula(),
                obtenido.getFecheNacimiento(),
                obtenido.getTelefonoPaciente(),
                obtenido.getEmaiLPaciente(),
                obtenido.getEpsPaciente(),
                obtenido.getTipoSangre(),
                obtenido.getCiudadPaciente(),
                obtenido.getFotoPaciente(),
                obtenido.getTipoAlergia(),
                obtenido.getContraseniaPaciente(),
                obtenido.getContraseniaPaciente()
                //new ArrayList<>

        );
        return pacienteDTO;
    }

    @Override
    public List<Ciudad> cargarCiudadesDesdeEnum() {
        List<Ciudad> ciudades = new ArrayList<>();

        for (Ciudad ciudadEnum : Ciudad.values()) {
            ciudades.add(ciudadEnum);
        }
        return ciudades;
    }

    @Override
    public List<EPS> cargarEPSDesdeEnum() {
        List<EPS> eps = new ArrayList<>();

        for (EPS epsEnum : EPS.values()) {
            eps.add(epsEnum);
        }
        return eps;
    }

    @Override
    public List<TipoSangre> cargarTipoSangreDesdeEnum() {
        List<TipoSangre> tipoSangre = new ArrayList<>();

        for (TipoSangre epsEnum : TipoSangre.values()) {
            tipoSangre.add(epsEnum);
        }
        return tipoSangre;
    }

    @Override
    public List<TipoAlergia> cargarTipoAlergiaDesdeEnum() {
        List<TipoAlergia> tipoAlergia = new ArrayList<>();

        for (TipoAlergia alergiaEnum : TipoAlergia.values()) {
            tipoAlergia.add(alergiaEnum);
        }
        return tipoAlergia;
    }




}


