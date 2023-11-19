package co.edu.uniquindio.proyectoAvanzada.Controladores.Excepciones;

import co.edu.uniquindio.proyectoAvanzada.dto.ImagenDTO;
import co.edu.uniquindio.proyectoAvanzada.dto.MensajeDTO;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.ImagenesServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
@RestController
@RequestMapping("/api/imagenes")
@RequiredArgsConstructor
public class ImagenesController {
    private final ImagenesServicio imagenesServicio;
    @PostMapping("/subir")
    public ResponseEntity<MensajeDTO<Map>> subir(@RequestParam("file") MultipartFile imagen)
            throws Exception{
        Map respuesta = imagenesServicio.subirImagen(imagen);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, respuesta ));
    }
    @DeleteMapping("/eliminar")
    public ResponseEntity<MensajeDTO<Map>> eliminar(@RequestBody ImagenDTO imagenDTO) throws
            Exception{
        Map respuesta = imagenesServicio.eliminarImagen( imagenDTO.idImagen() );
        return ResponseEntity.ok().body(new MensajeDTO<>(false, respuesta ));
    }
}