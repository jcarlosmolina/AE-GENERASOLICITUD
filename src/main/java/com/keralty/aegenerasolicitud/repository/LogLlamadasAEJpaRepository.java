package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.LogLlamadasAE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogLlamadasAEJpaRepository extends JpaRepository<LogLlamadasAE, Long>, LogLlamadasAEJpaRepositoryCustom {
}
