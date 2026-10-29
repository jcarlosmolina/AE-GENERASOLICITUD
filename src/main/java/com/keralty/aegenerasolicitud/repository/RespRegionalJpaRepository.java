package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.RespRegional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespRegionalJpaRepository extends JpaRepository<RespRegional, Long>, RespRegionalJpaRepositoryCustom {
}
