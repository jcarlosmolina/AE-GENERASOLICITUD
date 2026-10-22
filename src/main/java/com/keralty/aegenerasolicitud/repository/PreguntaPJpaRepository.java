package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.PreguntaP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreguntaPJpaRepository extends JpaRepository<PreguntaP, Long>, PreguntaPJpaRepositoryCustom {
}
