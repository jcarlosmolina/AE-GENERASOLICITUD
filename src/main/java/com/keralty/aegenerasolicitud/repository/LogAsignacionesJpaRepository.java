package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.LogAsignaciones;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogAsignacionesJpaRepository extends JpaRepository<LogAsignaciones, Long>, LogAsignacionesJpaRepositoryCustom {

    List<LogAsignaciones> findBySolicitud(Solicitud solicitud);
}
