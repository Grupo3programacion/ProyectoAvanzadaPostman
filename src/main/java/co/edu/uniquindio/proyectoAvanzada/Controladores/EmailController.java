package co.edu.uniquindio.proyectoAvanzada.Controladores;

import co.edu.uniquindio.proyectoAvanzada.dto.EmailDTO;
import co.edu.uniquindio.proyectoAvanzada.dto.MedicoDTO;
import co.edu.uniquindio.proyectoAvanzada.dto.MensajeDTO;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.EmailServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/correo")
@RequiredArgsConstructor
public class EmailController {

    private final EmailServicio emailServicio;


    @PostMapping("/enviarCorreo")
    public ResponseEntity enviarCorreo(@Valid @RequestBody EmailDTO emailDTO) throws Exception{
        emailServicio.enviarCorreo(emailDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "email enviado exitosamente") );
    }
}
