package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Novedad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovedadJpaRepository extends JpaRepository<Novedad, Long>, NovedadJpaRepositoryCustom {
}
