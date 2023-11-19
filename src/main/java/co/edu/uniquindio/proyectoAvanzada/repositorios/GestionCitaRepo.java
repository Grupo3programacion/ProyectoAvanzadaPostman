package co.edu.uniquindio.proyectoAvanzada.repositorios;

import co.edu.uniquindio.proyectoAvanzada.modelo.entidades.Cita;
import co.edu.uniquindio.proyectoAvanzada.modelo.entidades.Medico;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GestionCitaRepo extends JpaRepository<Cita, Integer> {

    List<Cita> findAllByPacienteIdPaciente(int idPaciente);
}
