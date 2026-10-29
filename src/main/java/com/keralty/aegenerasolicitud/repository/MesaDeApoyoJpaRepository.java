package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.MesaDeApoyo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesaDeApoyoJpaRepository extends JpaRepository<MesaDeApoyo, Long>, MesaDeApoyoJpaRepositoryCustom {
}
