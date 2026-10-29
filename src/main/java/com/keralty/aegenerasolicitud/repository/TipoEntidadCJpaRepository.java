package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.TipoEntidadC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoEntidadCJpaRepository extends JpaRepository<TipoEntidadC, Long>, TipoEntidadCJpaRepositoryCustom {

    List<TipoEntidadC> findByPersonasC(PersonaC personasC);
}
