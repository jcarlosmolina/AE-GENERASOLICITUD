package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.AnexoC;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.Periodicidad;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodicidadJpaRepository extends JpaRepository<Periodicidad, Long>, PeriodicidadJpaRepositoryCustom {

    List<Periodicidad> findBySolicitudes(Solicitud solicitudes);

    List<Periodicidad> findByContratosC(ContratoC contratosC);

    List<Periodicidad> findByAnexosC(AnexoC anexosC);
}
