package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.Planes;
import com.keralty.aegenerasolicitud.persistence.Productos;
import com.keralty.aegenerasolicitud.persistence.Tarifa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarifaJpaRepository extends JpaRepository<Tarifa, Long>, TarifaJpaRepositoryCustom {

    List<Tarifa> findByContratosC(ContratoC contratosC);

    List<Tarifa> findByProductoT(Productos productoT);

    List<Tarifa> findByPlanT(Planes planT);
}
