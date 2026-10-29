package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.LogLlamadasBH;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogLlamadasBHJpaRepository extends JpaRepository<LogLlamadasBH, Long>, LogLlamadasBHJpaRepositoryCustom {
}
