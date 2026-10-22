package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.EstadoPersona;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoPersonaJpaRepository extends JpaRepository<EstadoPersona, Long>, EstadoPersonaJpaRepositoryCustom {

    List<EstadoPersona> findByPersonas(Persona personas);

    List<EstadoPersona> findByPersonasC(PersonaC personasC);
}
