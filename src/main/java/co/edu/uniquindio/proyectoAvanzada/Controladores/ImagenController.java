package co.edu.uniquindio.proyectoAvanzada.Controladores;

import co.edu.uniquindio.proyectoAvanzada.dto.MensajeDTO;
import co.edu.uniquindio.proyectoAvanzada.modelo.servicios.interfaces.ImagenesServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/imagen")
@RequiredArgsConstructor
public class ImagenController {

    private final ImagenesServicio imagenesServicio;

    @PostMapping("/subirImagen")
    public ResponseEntity<MensajeDTO<Map>> subirImagen(@RequestParam("imagen") MultipartFile imagen) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, imagenesServicio.subirImagen(imagen)));
    }

    @DeleteMapping("/eliminarImagen/{imagen}")
    public ResponseEntity<MensajeDTO<String>> eliminarImagen(@PathVariable String imagen) throws Exception {
        imagenesServicio.eliminarImagen(imagen);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "imagen eliminada correctamente"));
    }

}
