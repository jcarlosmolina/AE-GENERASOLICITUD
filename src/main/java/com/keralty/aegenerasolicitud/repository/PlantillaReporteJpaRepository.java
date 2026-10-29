package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.PlantillaReporte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantillaReporteJpaRepository extends JpaRepository<PlantillaReporte, Long>, PlantillaReporteJpaRepositoryCustom {
}
