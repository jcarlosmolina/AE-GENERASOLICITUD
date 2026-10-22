package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.DetalleTarifa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleTarifaJpaRepository extends JpaRepository<DetalleTarifa, Long>, DetalleTarifaJpaRepositoryCustom {
}
