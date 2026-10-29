package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Gestor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GestorJpaRepository extends JpaRepository<Gestor, Long>, GestorJpaRepositoryCustom {
}
