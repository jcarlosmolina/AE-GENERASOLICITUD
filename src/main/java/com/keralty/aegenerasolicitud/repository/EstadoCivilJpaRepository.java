package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.EstadoCivil;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoCivilJpaRepository extends JpaRepository<EstadoCivil, Long>, EstadoCivilJpaRepositoryCustom {

    List<EstadoCivil> findByPersonas(Persona personas);

    List<EstadoCivil> findByPersonasC(PersonaC personasC);
}
