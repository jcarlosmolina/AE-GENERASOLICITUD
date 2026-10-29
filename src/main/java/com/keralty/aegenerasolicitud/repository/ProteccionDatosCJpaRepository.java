package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.oid.ProteccionDatosCOid;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.Productos;
import com.keralty.aegenerasolicitud.persistence.ProteccionDatosC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProteccionDatosCJpaRepository extends JpaRepository<ProteccionDatosC, ProteccionDatosCOid>, ProteccionDatosCJpaRepositoryCustom {

    List<ProteccionDatosC> findByPersonaC(PersonaC personaC);

    List<ProteccionDatosC> findByProductoC(Productos productoC);
}
