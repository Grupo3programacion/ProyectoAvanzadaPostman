package co.edu.uniquindio.proyectoAvanzada.Controladores;

import co.edu.uniquindio.proyectoAvanzada.dto.LoginDTO;
import co.edu.uniquindio.proyectoAvanzada.dto.MensajeDTO;
import co.edu.uniquindio.proyectoAvanzada.dto.PacienteDTO;
import co.edu.uniquindio.proyectoAvanzada.dto.TokenDTO;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.impl.MedicoServiciosImpl;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.AutenticacionServicio;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.MedicoServicios;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.PQRServicios;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.PacienteServicios;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacionController {
    private final AutenticacionServicio autenticacionServicio;
    private final PacienteServicios pacienteServicios;
    private final MedicoServicios medicoServicios;
    private final PQRServicios pQRServicios;

    @PostMapping("/registrarsePaciente")
    public ResponseEntity registrarse(@Valid @RequestBody PacienteDTO pacienteDTO) throws Exception{
        pacienteServicios.registrarse(pacienteDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Paciente creado correctamete") );
    }

    @PostMapping("/login")
    public ResponseEntity<MensajeDTO<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO)
            throws Exception {
        TokenDTO tokenDTO = autenticacionServicio.login(loginDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, tokenDTO));
    }
    @GetMapping("/listarCiudades")
    public ResponseEntity listarCiudades() throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, pacienteServicios.cargarCiudadesDesdeEnum()));
    }

    @GetMapping("/listarEPS")
    public ResponseEntity listarEPS() throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, pacienteServicios.cargarEPSDesdeEnum()));
    }

    @GetMapping("/listarTipoSangre")
    public ResponseEntity listarTipoSangre() throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, pacienteServicios.cargarTipoSangreDesdeEnum()));
    }

    @GetMapping("/listarTipoAlergia")
    public ResponseEntity listarTipoAlergia() throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, pacienteServicios.cargarTipoAlergiaDesdeEnum()));
    }

    @GetMapping("/listarEspecialidad")
    public ResponseEntity listarEspecialidad() throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, medicoServicios.cargarEspecialidadDesdeEnum()));
    }

    @GetMapping("/listarTiposPqrs")
    public ResponseEntity listarTiposPqrs() throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, pQRServicios.listarTiposPqrs()));
    }


}