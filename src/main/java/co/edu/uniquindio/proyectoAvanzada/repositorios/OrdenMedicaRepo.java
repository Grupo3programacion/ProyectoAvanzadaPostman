package co.edu.uniquindio.proyectoAvanzada.repositorios;

import co.edu.uniquindio.proyectoAvanzada.modelo.entidades.Cita;
import co.edu.uniquindio.proyectoAvanzada.modelo.entidades.OrdenMedica;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenMedicaRepo extends JpaRepository<OrdenMedica, Integer> {
    Optional<OrdenMedica> findByCitas_IdCita(int idCita);
}
