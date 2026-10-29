package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperAdminJpaRepository extends JpaRepository<SuperAdmin, Long>, SuperAdminJpaRepositoryCustom {
}
