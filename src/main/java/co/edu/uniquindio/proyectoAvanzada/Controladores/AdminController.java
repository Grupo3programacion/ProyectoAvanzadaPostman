package co.edu.uniquindio.proyectoAvanzada.Controladores;

import co.edu.uniquindio.proyectoAvanzada.dto.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.entidades.Administrador;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.AdministradorServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdministradorServicio administradorServicio;


    @PostMapping("/crearMedico")
    public ResponseEntity crearMedico(@Valid @RequestBody MedicoDTO medicoDTO) throws Exception{
        administradorServicio.crearMedico(medicoDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Medico creado exitosamente") );
    }

    @PutMapping("/actualizarMedico")
    public ResponseEntity actualizarMedico(@Valid @RequestBody DetalleMedicoDTO medicoDTO) throws Exception{
        administradorServicio.actualizarMedico(medicoDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Medico actualizado exitosamente") );
    }

    @DeleteMapping("/eliminarMedico/{codigo}")
    public ResponseEntity<MensajeDTO<String>> eliminarMedico(@PathVariable int codigo) throws Exception {
        administradorServicio.eliminarMedico(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Medico eliminado correctamente"));
    }

    @GetMapping("/obtenerMedico/{codigo}")
    public ResponseEntity obtenerMedico(@PathVariable int codigo) throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, administradorServicio.obtenerMedico(codigo)));
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<MensajeDTO<List<ItemMedicoDTO>>> listarTodos() throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, administradorServicio.listarMedicos()));
    }

    @GetMapping("/listarPQRS")
    public ResponseEntity listarPQRS() throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, administradorServicio.listarPQRS()));
    }

    @GetMapping("/listarCitas")
    public ResponseEntity<MensajeDTO<List<CitaDTOAdmin>>> listarCitas() throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, administradorServicio.listarCitas()));
    }

    @PostMapping("/responderPQRS")
    public ResponseEntity responderPQRS(@Valid @RequestBody RegistroRespuestaDTO registroRespuestaDTO) throws Exception{
        administradorServicio.responderPQRS(registroRespuestaDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "PQRS respondida exitosamente") );
    }

    @GetMapping("/verDetallePQRS/{codigo}")
    public ResponseEntity verDetallePQRS(@PathVariable int codigo) throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, administradorServicio.verDetallePQRS(codigo)));
    }

    @PostMapping("/autorizacionMedicamentos")
    public ResponseEntity autorizacionMedicamentos(@Valid @RequestBody AutorizacionDTOAdmin autorizacionDTOAdmin) throws Exception{
        administradorServicio.autorizacionMedicamentos(autorizacionDTOAdmin);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Medicamento autorizado exitosamente") );
    }

    @PostMapping("/cambiarEstadoPQRS/{codigoPQRS}")
    public ResponseEntity<MensajeDTO<String>> cambiarEstadoPQRS(@PathVariable int codigoPQRS, String estadoPQRS) throws Exception {
        administradorServicio.cambiarEstadoPQRS(codigoPQRS, estadoPQRS);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Estado PQRS cambiado correctamente"));
    }

}
