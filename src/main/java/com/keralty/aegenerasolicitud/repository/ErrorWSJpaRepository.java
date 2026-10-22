package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.ErrorWS;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorWSJpaRepository extends JpaRepository<ErrorWS, Long>, ErrorWSJpaRepositoryCustom {

    List<ErrorWS> findBySolicitud(Solicitud solicitud);
}
