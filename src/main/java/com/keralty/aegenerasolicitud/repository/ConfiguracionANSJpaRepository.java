package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.ConfiguracionANS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfiguracionANSJpaRepository extends JpaRepository<ConfiguracionANS, Long>, ConfiguracionANSJpaRepositoryCustom {
}
