package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.LogEstado;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogEstadoJpaRepository extends JpaRepository<LogEstado, Long>, LogEstadoJpaRepositoryCustom {

    List<LogEstado> findBySolicitud(Solicitud solicitud);
}
