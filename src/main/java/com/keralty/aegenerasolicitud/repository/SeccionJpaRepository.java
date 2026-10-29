package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeccionJpaRepository extends JpaRepository<Seccion, Long>, SeccionJpaRepositoryCustom {
}
