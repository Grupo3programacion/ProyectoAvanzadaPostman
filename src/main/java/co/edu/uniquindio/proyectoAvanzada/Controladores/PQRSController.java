package co.edu.uniquindio.proyectoAvanzada.Controladores;


import co.edu.uniquindio.proyectoAvanzada.dto.*;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.PQRServicios;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/PQRS")
@RequiredArgsConstructor
public class PQRSController {


    private final PQRServicios pqrServicios;

    @PostMapping("/crearPQR")
    public ResponseEntity crearPQR(@Valid @RequestBody CrearPQRSDTO crearPQRSDTO) throws Exception{
        pqrServicios.crearPQR(crearPQRSDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "PQRS creada correctamete") );
    }

    @PostMapping("/cambiarEstadoPQRS")
    public ResponseEntity<MensajeDTO<String>> cambiarEstadoPQRS(@RequestBody Map<String, String> requestBody) throws Exception {
        int codigo = Integer.parseInt(requestBody.get("codigo"));
        String estado = requestBody.get("estado");
        pqrServicios.cambiarEstadoPQRS(codigo, estado);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, " Estado PQRS modificado correctamente"));
    }

    @PostMapping("/responderPQRSAdmin")
    public ResponseEntity responderPQRSAdmin(@Valid @RequestBody RegistroRespuestaDTO registroRespuestaDTO) throws Exception{
        pqrServicios.responderPQRSAdmin(registroRespuestaDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Mensaje de PQRS enviado correctamete por el administrador") );
    }

    @PostMapping("/responderPQRSPaciente")
    public ResponseEntity responderPQRSPaciente(@Valid @RequestBody RegistroRespuestaPacienteDTO registroRespuestaPacienteDTO) throws Exception {
        pqrServicios.responderPQRSPaciente(registroRespuestaPacienteDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Mensaje de PQRS enviado correctamente por el paciente"));
    }


    @GetMapping("/listarPQRSPaciente/{codigoPaciente}")
    public ResponseEntity listarPQRSPaciente(@PathVariable int codigoPaciente) throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, pqrServicios.listarPQRSPaciente(codigoPaciente)));
    }



    @GetMapping("/verDetallePQRS/{codigo}")
    public ResponseEntity verDetallePQRS(@PathVariable int codigo) throws Exception {
        return ResponseEntity.ok().body( new MensajeDTO<>(false, pqrServicios.verDetallePQRS(codigo)));
    }


}
