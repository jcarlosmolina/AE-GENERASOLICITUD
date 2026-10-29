package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.oid.PregCuesMedCOid;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.PregCuesMedC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PregCuesMedCJpaRepository extends JpaRepository<PregCuesMedC, PregCuesMedCOid>, PregCuesMedCJpaRepositoryCustom {

    List<PregCuesMedC> findByPersonaC(PersonaC personaC);
}
