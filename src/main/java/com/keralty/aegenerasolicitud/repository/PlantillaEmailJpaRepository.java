package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.PlantillaEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantillaEmailJpaRepository extends JpaRepository<PlantillaEmail, Long>, PlantillaEmailJpaRepositoryCustom {
}
