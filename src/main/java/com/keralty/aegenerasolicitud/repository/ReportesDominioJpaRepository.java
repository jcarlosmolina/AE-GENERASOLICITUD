package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.oid.ReportesDominioOid;
import com.keralty.aegenerasolicitud.persistence.ReportesDominio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportesDominioJpaRepository extends JpaRepository<ReportesDominio, ReportesDominioOid>, ReportesDominioJpaRepositoryCustom {
}
