package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.AnexoC;
import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.Periodicidad;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnexoCJpaRepository extends JpaRepository<AnexoC, Long>, AnexoCJpaRepositoryCustom {

    List<AnexoC> findByBeneficiarioC(BeneficiarioC beneficiarioC);

    List<AnexoC> findByPeriodicidadC(Periodicidad periodicidadC);

    List<AnexoC> findByTipoIdentificacionC(TipoIdentificacion tipoIdentificacionC);
}
