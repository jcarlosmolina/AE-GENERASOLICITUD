package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.TipoFactura;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoFacturaJpaRepository extends JpaRepository<TipoFactura, String>, TipoFacturaJpaRepositoryCustom {

    List<TipoFactura> findBySolicitudes(Solicitud solicitudes);
}
