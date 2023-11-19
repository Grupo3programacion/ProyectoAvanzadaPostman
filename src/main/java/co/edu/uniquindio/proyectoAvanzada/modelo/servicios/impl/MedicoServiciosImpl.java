package co.edu.uniquindio.proyectoAvanzada.modelo.servicios.impl;

import co.edu.uniquindio.proyectoAvanzada.dto.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.entidades.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.Especialidad;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.EstadoCita;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.TipoAlergia;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.MedicoServicios;
import co.edu.uniquindio.proyectoAvanzada.repositorios.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicoServiciosImpl implements MedicoServicios {

    private final CitaRepo citaRepo;
    private final MedicoRepo medicoRepo;
    private final PacienteRepo pacienteRepo;
    private final OrdenMedicaRepo ordenMedicaRepo;
    private final MedicamentoRepo medicamentoRepo;
    private final AdministradorRepo administradorRepo;
    private final AutorizacionMedicamentoRepo autorizacionMedicamentoRepo;
    @Override
    public List<CitasPendientesMedicoDetalleDTO> listarCitasPendientes(int idMedico) throws Exception {

        Optional<Medico> optionalMedico= medicoRepo.findById(idMedico);

        if(optionalMedico.isEmpty()){
            throw new Exception("El medico con id " + idMedico + " no esta registrado");
        }

        List<Cita> citas = citaRepo.findAllByEstadoCita(EstadoCita.ABIERTA);

        if (citas.isEmpty()) {
            throw new Exception("No hay citas registradas");
        }

        List<CitasPendientesMedicoDetalleDTO> citasPendientesMedicoDetalleDTO = new ArrayList<>();

        for (Cita c : citas) {
            if (c.getMedico().getIdMedico() == idMedico) {
                citasPendientesMedicoDetalleDTO.add(new CitasPendientesMedicoDetalleDTO(

                        c.getFechaCreacion(),
                        c.getMedico().getIdMedico(),
                        c.getIdCita(),
                        c.getPaciente().getNombrePaciente(),
                        c.getEstadoCita()
                ));
            }
        }

        return citasPendientesMedicoDetalleDTO;
    }

    @Override
    public List<CitasPendientesMedicoDetalleDTO> listarHistorialCitas(int idMedico) throws Exception {

        Optional<Medico> optionalMedico= medicoRepo.findById(idMedico);

        if(optionalMedico.isEmpty()){
            throw new Exception("El medico con id " + idMedico + " no esta registrado");
        }

        List<Cita> citas = citaRepo.findAllByEstadoCita(EstadoCita.CERRADO);

        if (citas.isEmpty()) {
            throw new Exception("No hay citas registradas");
        }

        List<CitasPendientesMedicoDetalleDTO> citasPendientesMedicoDetalleDTO = new ArrayList<>();

        for (Cita c : citas) {
            if (c.getMedico().getIdMedico() == idMedico) {
                citasPendientesMedicoDetalleDTO.add(new CitasPendientesMedicoDetalleDTO(

                        c.getFechaCreacion(),
                        c.getMedico().getIdMedico(),
                        c.getIdCita(),
                        c.getPaciente().getNombrePaciente(),
                        c.getEstadoCita()
                ));
            }
        }

        return citasPendientesMedicoDetalleDTO;
    }

    @Override
    public String atenderCita(AtenderCitaDTOMedico atenderCitaDTOMedico) throws Exception {

        Optional<Cita> optionalCita = citaRepo.findById( atenderCitaDTOMedico.idCita());

        if(optionalCita.isEmpty()){
            throw new Exception("El id de la cita no existe");
        }

        Optional<Paciente> optionalPaciente= pacienteRepo.findById( atenderCitaDTOMedico.idPaciente());

        if(optionalPaciente.isEmpty()){
            throw new Exception("El id del paciente no existe");
        }

        Optional<Medico> optionalMedico= medicoRepo.findById( atenderCitaDTOMedico.idMedico());

        if(optionalMedico.isEmpty()){
            throw new Exception("El id del medico no existe");
        }

        Cita cita = optionalCita.get();
        Paciente paciente = optionalPaciente.get();
        Medico medico = optionalMedico.get();
        cita.setEstadoCita(EstadoCita.CERRADO);
        citaRepo.save(cita);

        OrdenMedica ordenMedica = new OrdenMedica();

        ordenMedica.setPaciente(paciente);
        ordenMedica.setCitas(cita);
        ordenMedica.setNotaMedica(atenderCitaDTOMedico.notaMedica());
        ordenMedica.setDiagnostico(atenderCitaDTOMedico.diagnostico());
        ordenMedica.setTratamiento(atenderCitaDTOMedico.tratamiento());
        ordenMedica.setMedico(medico);
        ordenMedica.setSintomasPaciente(atenderCitaDTOMedico.sintomasPaciente());

        ordenMedicaRepo.save(ordenMedica);

        return "Cita atendida exitosamente";
    }
    @Override
    public String asignarMedicamentosOrden( AsignacionMedicamentosDTO asignacionMedicamentosDTO )throws Exception {

        Optional<OrdenMedica> optionalOrdenMedica = ordenMedicaRepo.findById(asignacionMedicamentosDTO.idOrdenMedica());
        if (optionalOrdenMedica.isEmpty()) {
            throw new Exception("El id de la cita no existe");
        }
        Optional<Administrador> administrador = administradorRepo.findById(15);
        Administrador admin = administrador.get();
        OrdenMedica om = optionalOrdenMedica.get();

        List<MedicamentoDTO> list = asignacionMedicamentosDTO.listaMedicamentos();

        for (MedicamentoDTO m : list) {

            Medicamento medi = new Medicamento();
            medi.setOrdenMedica(om);
            medi.setNombreMedicamento(m.nombre());
            medi.setCantidadMedicamentos(m.cantidad());
            medi.setDosis(m.dosis());

            medicamentoRepo.save(medi);

            AutorizacionMedicamento autorizacionMedicamento = new AutorizacionMedicamento();
            autorizacionMedicamento.setOrdenMedica(om);
            autorizacionMedicamento.setEstado(false);
            autorizacionMedicamento.setAdministrador(admin);
            autorizacionMedicamento.setPaciente(om.getPaciente());

            autorizacionMedicamentoRepo.save(autorizacionMedicamento);
        }
    return "Medicamentos asignados exitosamente";

    }

    @Override
    public String agendarDiaLibre (DiaLibereDTOMedico diaLibreDTOMedico) throws Exception {
        Optional<Medico> optionalDiaLibre = medicoRepo.findById(diaLibreDTOMedico.idMedico());

        if (optionalDiaLibre.isEmpty()) {
            throw new Exception("El código " + diaLibreDTOMedico.idMedico() + " no está asociado a ningún médico");
        }

        Medico medico = optionalDiaLibre.get();

        if( medico.getDiaLibre() != null){
            if( medico.getDiaLibre().isAfter(LocalDate.now() ) ){
                throw new Exception("No se puede sacar un día libre ya tienes asignado: " + medico.getDiaLibre());
            }else{
                medico.setDiaLibre(diaLibreDTOMedico.fecha());
            }
        }else{
            medico.setDiaLibre(diaLibreDTOMedico.fecha());
        }

        medicoRepo.save(medico);

        // Devolver un mensaje indicando que el día libre ha sido agendado exitosamente
        return "Día libre agendado exitosamente para el médico con código " + medico.getIdMedico();
    }

    @Override
    public List listarCitasRealizadasMedico(int idMedico) throws Exception {

        Optional<Medico> medicoOptional = medicoRepo.findById( idMedico );

        if (medicoOptional.isEmpty()) {
            throw new Exception("Médico con ID " + idMedico + " no encontrado");
        }

        List<Cita> citaOptional = citaRepo.findAll();

        if (citaOptional.isEmpty()) {
            throw new Exception("No hay citas registradas");
        }

        // for que recorra la lista de citas y que solo guarde las citas del medico buscado por ID cuya fecha sea anterior a la de hoy

        List<CitasRealizadasDTOMedico> citasRealizadas = new ArrayList<>();

        for (Cita cita : citaOptional) {
            if (cita.getMedico().getIdMedico() == idMedico) {
                citasRealizadas.add(new CitasRealizadasDTOMedico(
                        cita.getMedico().getIdMedico(),
                        cita.getFechaCreacion(),
                        cita.getIdCita(),
                        cita.getPaciente().getIdPaciente()
                ));
            }
        }
        return citasRealizadas;
    }

    @Override
    public List<Especialidad> cargarEspecialidadDesdeEnum() {
        List<Especialidad> especialidad = new ArrayList<>();

        for (Especialidad alergiaEnum : Especialidad.values()) {
            especialidad.add(alergiaEnum);
        }
        return especialidad;
    }

    @Override
    public List listarMedicosEspecialidad(Especialidad nombre) throws Exception {
        List<Medico> medicoOptional = medicoRepo.findAllByEspecilidadMedico( nombre );
        if (medicoOptional.isEmpty()) {
            throw new Exception("No existen medicos con esta especialidad");
        }
        List<MedicoCrearCitaDTO> medicoCrearCita = new ArrayList<>();
        for (Medico medico : medicoOptional) {
            medicoCrearCita.add(new MedicoCrearCitaDTO(
                    medico.getIdMedico(),
                    medico.getNombreMedico() + " " + medico.getApellidoMedico()
            ));
        }
        return medicoCrearCita;
    }



    public AtenderCitaDTOMedico verDetalleCitaAtendida(int idCita) throws Exception {
        Optional<Cita> opcionalCita = citaRepo.findById(idCita);
        Optional<OrdenMedica> opcionalMedica = ordenMedicaRepo.findByCitas_IdCita(idCita);

        if (opcionalCita.isEmpty()) {
            throw new Exception("El código " + idCita+ " no está asociado al paciente");
        }
        //filtrar las citas del paciante por medico
        Cita obtenido = opcionalCita.get();
        OrdenMedica ordenMedica = opcionalMedica.get();
        AtenderCitaDTOMedico atenderCitaDTOMedico = new AtenderCitaDTOMedico(
                obtenido.getPaciente().getIdPaciente(),
                obtenido.getIdCita(),
                        obtenido.getMedico().getIdMedico(),
                        ordenMedica.getDiagnostico(),
                        ordenMedica.getTratamiento(),
                        ordenMedica.getSintomasPaciente(),
                        ordenMedica.getNotaMedica()
        );
        return atenderCitaDTOMedico;
    }


}


