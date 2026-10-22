package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Asesor;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsesorJpaRepository extends JpaRepository<Asesor, Long>, AsesorJpaRepositoryCustom {

    List<Asesor> findByContratosC(ContratoC contratosC);

    List<Asesor> findByTipoIdentificacion(TipoIdentificacion tipoIdentificacion);
}
