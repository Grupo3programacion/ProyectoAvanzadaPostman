package co.edu.uniquindio.proyectoAvanzada.Controladores;

import co.edu.uniquindio.proyectoAvanzada.dto.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.Especialidad;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.MedicoServicios;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.PQRServicios;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.PacienteServicios;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteServicios pacienteServicios;
    private final MedicoServicios medicoServicios;
    private final PQRServicios pQRServicios;

    @PutMapping("/editarPerfil")
    public ResponseEntity editarPerfil(@Valid @RequestBody  PacienteDTO pacienteDTO) throws Exception{
        pacienteServicios.editarPerfil(pacienteDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Paciente actualizado correctamete") );
    }

    @PutMapping("/recuperarPassword")
    public ResponseEntity<MensajeDTO<String>> recuperarPassword(@Valid @RequestBody RecuperarPasswordDTO dto) throws Exception {
        pacienteServicios.recuperarPassword(dto);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Contraseña actualizada correctamente"));
    }

    @DeleteMapping("/eliminar/{idPaciente}")
    public ResponseEntity<MensajeDTO<String>> eliminarCuenta(@PathVariable int idPaciente) throws Exception {
        pacienteServicios.eliminarCuenta(idPaciente);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Paciente eliminado correctamente"));
    }

    @PostMapping("/enviarLinkRecuperacion")
    public ResponseEntity<MensajeDTO<String>> enviarLinkRecuperacion(@RequestBody Map<String, String> requestBody) throws Exception {
        String emailPaciente = requestBody.get("emailPaciente");
        pacienteServicios.enviarLinkRecuperacion(emailPaciente);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Email enviado correctamente"));
    }

    @PostMapping("/agendarCita")
    public ResponseEntity agendarCita(@Valid @RequestBody CitaPacienteDTO citaPacienteDTO) throws Exception {
        pacienteServicios.agendarCita(citaPacienteDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Cita agendada exitosamente"));
    }

    @PutMapping("/cambiarPassword")
    public ResponseEntity cambiarPassword(@RequestBody Map<String, String> requestBody) throws Exception {
        int idPaciente = Integer.parseInt(requestBody.get("idPaciente"));
        String nuevaPassword = requestBody.get("nuevaPassword");
        pacienteServicios.cambiarPassword(idPaciente, nuevaPassword);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Contraseña cambiada correctamente"));
    }

    @PostMapping("/responderPQRS")
    public ResponseEntity responderPQRS(@Valid @RequestBody GestionDTOPQRSPaciente gestionDTOPQRSPacient) throws Exception {
        pacienteServicios.responderPQRS(gestionDTOPQRSPacient);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "respuesta generada exitosamente"));
    }

    @GetMapping("/listarCitasPaciente/{idPaciente}")
    public ResponseEntity listarCitasPaciente(@PathVariable int idPaciente) throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, pacienteServicios.listarCitasPaciente(idPaciente)));
    }

    @GetMapping("/filtrarCitaPorMedico")
    public ResponseEntity filtrarCitaPorMedico(@RequestBody Map<String, String> requestBody) throws Exception {
        int idPaciente = Integer.parseInt(requestBody.get("idPaciente"));
        int idMedico = Integer.parseInt(requestBody.get("idMedico"));
        return ResponseEntity.ok().body( new MensajeDTO<>(false, pacienteServicios.filtrarCitaPorMedico (idPaciente, idMedico)));
    }

    @GetMapping("/filtrarCitaPorFecha")
    public ResponseEntity filtrarCitaPorFecha(@Valid @RequestBody  FiltrarFechaPacienteDTO filtrarFechaPacienteDTO) throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, pacienteServicios.filtrarCitaPorFecha (filtrarFechaPacienteDTO)));
    }

    @GetMapping("/verDetalleCita/{idCita}")
    public ResponseEntity verDetalleCita(@PathVariable int idCita) throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, pacienteServicios.verDetalleCita(idCita)));
    }

    @GetMapping("/consultaMedicamentos/{idMedicamento}")
    public ResponseEntity consultaMedicamentos(@PathVariable int idMedicamento) throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, pacienteServicios.consultaMedicamentos (idMedicamento)));
    }


    @GetMapping("/detalle/{idPaciente}")
    public ResponseEntity obtenerPaciente(@PathVariable int idPaciente) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicios.obtenerPaciente(idPaciente)));

    }

    @GetMapping("/listarMedicosEspecialidad/{nombre}")
    public ResponseEntity listarMedicosEspecialidad(@PathVariable Especialidad nombre) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, medicoServicios.listarMedicosEspecialidad(nombre)));
    }


    @GetMapping("/listarCitasPqrs/{idPaciente}")
    public ResponseEntity listarCitasPqrs(@PathVariable int idPaciente) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, pQRServicios.listarCitasPqrs(idPaciente)));
    }
}