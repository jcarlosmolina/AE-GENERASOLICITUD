package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Aesolicitudes;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.EstadoContrato;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoContratoJpaRepository extends JpaRepository<EstadoContrato, Long>, EstadoContratoJpaRepositoryCustom {

    List<EstadoContrato> findByAESOLICITUDES(Aesolicitudes aESOLICITUDES);

    List<EstadoContrato> findByContratosC(ContratoC contratosC);
}
