package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Planes;
import com.keralty.aegenerasolicitud.persistence.Productos;
import com.keralty.aegenerasolicitud.persistence.TipoIVA;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoIVAJpaRepository extends JpaRepository<TipoIVA, Long>, TipoIVAJpaRepositoryCustom {

    List<TipoIVA> findByProductoT(Productos productoT);

    List<TipoIVA> findByPlanT(Planes planT);
}
