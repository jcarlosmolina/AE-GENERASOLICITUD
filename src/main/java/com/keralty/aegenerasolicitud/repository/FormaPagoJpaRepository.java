package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.FormaPago;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormaPagoJpaRepository extends JpaRepository<FormaPago, Long>, FormaPagoJpaRepositoryCustom {

    List<FormaPago> findBySolicitudes(Solicitud solicitudes);

    List<FormaPago> findByContratosC(ContratoC contratosC);
}
