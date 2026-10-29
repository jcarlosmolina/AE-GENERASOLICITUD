package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.FamiliaNovedad;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamiliaNovedadJpaRepository extends JpaRepository<FamiliaNovedad, Long>, FamiliaNovedadJpaRepositoryCustom {

    List<FamiliaNovedad> findByTipoIdentificacion(TipoIdentificacion tipoIdentificacion);
}
