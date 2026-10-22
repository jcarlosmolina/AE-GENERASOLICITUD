package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.TipoInstitucionC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoInstitucionCJpaRepository extends JpaRepository<TipoInstitucionC, Long>, TipoInstitucionCJpaRepositoryCustom {

    List<TipoInstitucionC> findByPersonasC(PersonaC personasC);
}
