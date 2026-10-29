package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.GrupoAsociado;
import com.keralty.aegenerasolicitud.persistence.Planes;
import com.keralty.aegenerasolicitud.persistence.Productos;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoAsociadoJpaRepository extends JpaRepository<GrupoAsociado, Long>, GrupoAsociadoJpaRepositoryCustom {

    List<GrupoAsociado> findBySolicitudes(Solicitud solicitudes);

    List<GrupoAsociado> findByContratosC(ContratoC contratosC);

    List<GrupoAsociado> findByProductoT(Productos productoT);

    List<GrupoAsociado> findByPlanT(Planes planT);
}
