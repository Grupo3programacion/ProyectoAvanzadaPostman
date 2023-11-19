package co.edu.uniquindio.proyectoAvanzada.repositorios;

import co.edu.uniquindio.proyectoAvanzada.modelo.entidades.Cita;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepo extends JpaRepository<Cita, Integer> {
    List<Cita> findAllByPacienteIdPaciente(int idPaciente);
    List<Cita> findAllByEstadoCita(EstadoCita estado);


}
