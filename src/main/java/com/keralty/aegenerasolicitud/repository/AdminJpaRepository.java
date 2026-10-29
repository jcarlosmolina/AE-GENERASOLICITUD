package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminJpaRepository extends JpaRepository<Admin, Long>, AdminJpaRepositoryCustom {
}
