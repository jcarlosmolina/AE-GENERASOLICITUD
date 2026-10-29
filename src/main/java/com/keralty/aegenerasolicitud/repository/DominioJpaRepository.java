package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Dominio;
import com.keralty.aegenerasolicitud.persistence.Periodicidad;
import com.keralty.aegenerasolicitud.persistence.Planes;
import com.keralty.aegenerasolicitud.persistence.Productos;
import com.keralty.aegenerasolicitud.persistence.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DominioJpaRepository extends JpaRepository<Dominio, Long>, DominioJpaRepositoryCustom {

    List<Dominio> findByPlanT(Planes planT);

    List<Dominio> findByProductoT(Productos productoT);

    List<Dominio> findByPeriodicidades(Periodicidad periodicidades);

    List<Dominio> findByRoles(Rol roles);
}
