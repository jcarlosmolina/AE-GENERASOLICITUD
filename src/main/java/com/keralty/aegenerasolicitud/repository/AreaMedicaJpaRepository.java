package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.AreaMedica;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaMedicaJpaRepository extends JpaRepository<AreaMedica, Long>, AreaMedicaJpaRepositoryCustom {

    List<AreaMedica> findBySolicitudesArMe(Solicitud solicitudesArMe);
}
