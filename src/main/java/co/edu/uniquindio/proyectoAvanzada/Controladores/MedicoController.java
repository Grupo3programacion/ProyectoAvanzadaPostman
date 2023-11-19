package co.edu.uniquindio.proyectoAvanzada.Controladores;

import co.edu.uniquindio.proyectoAvanzada.dto.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.Especialidad;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.MedicoServicios;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.PacienteServicios;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoServicios medicoServicios;

    @GetMapping("/listarCitasPendientes/{idMedico}")
    public ResponseEntity listarCitasPendientes(@PathVariable int idMedico) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, medicoServicios.listarCitasPendientes(idMedico)));
    }

    @GetMapping("/listarHistorialCitas/{idMedico}")
    public ResponseEntity listarHistorialCitas(@PathVariable int idMedico) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, medicoServicios.listarHistorialCitas(idMedico)));
    }

    @PostMapping("/atenderCita")
    public ResponseEntity atenderCita(@Valid @RequestBody AtenderCitaDTOMedico atenderCitaDTOMedico) throws Exception{
        medicoServicios.atenderCita(atenderCitaDTOMedico);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Cita atendida exitosamente") );
    }

    @PostMapping("/asignarMedicamentosOrden")
    public ResponseEntity asignarMedicamentosOrden(@Valid @RequestBody AsignacionMedicamentosDTO asignacionMedicamentosDTO) throws Exception{
        medicoServicios.asignarMedicamentosOrden(asignacionMedicamentosDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "medicamento asignado exitosamente") );
    }

    @PostMapping("/agendarDiaLibre")
    public ResponseEntity agendarDiaLibre(@Valid @RequestBody DiaLibereDTOMedico diaLibreDTOMedico) throws Exception{
        medicoServicios.agendarDiaLibre(diaLibreDTOMedico);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Dia libre asignado exitosamente") );
    }

    @GetMapping("/listarCitasRealizadasMedico/{idMedico}")
    public ResponseEntity listarCitasRealizadasMedico(@PathVariable int idMedico) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, medicoServicios.listarCitasRealizadasMedico(idMedico)));
    }

    @GetMapping("/verDetalleCitaAtendida/{idCita}")
    public ResponseEntity verDetalleCitaAtendida(@PathVariable int idCita) throws Exception{
        return ResponseEntity.ok().body( new MensajeDTO<>(false, medicoServicios.verDetalleCitaAtendida(idCita)));
    }


}
