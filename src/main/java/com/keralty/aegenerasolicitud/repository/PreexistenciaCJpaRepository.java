package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.oid.PreexistenciaCOid;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.PreexistenciaC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreexistenciaCJpaRepository extends JpaRepository<PreexistenciaC, PreexistenciaCOid>, PreexistenciaCJpaRepositoryCustom {

    List<PreexistenciaC> findByPersonaC(PersonaC personaC);
}
