package co.edu.uniquindio.proyectoAvanzada.repositorios;

import co.edu.uniquindio.proyectoAvanzada.modelo.entidades.Medico;
import co.edu.uniquindio.proyectoAvanzada.modelo.enums.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepo extends JpaRepository <Medico, Integer>{
    Optional<Medico> findByEmailMedico(String correo);

    Optional<Medico> findByCedula(int cedula);

    List<Medico> findAllByEspecilidadMedico(Especialidad nombre);
}
