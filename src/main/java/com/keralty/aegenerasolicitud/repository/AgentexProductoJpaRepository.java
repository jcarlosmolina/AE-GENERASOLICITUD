package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.AgentexProducto;
import com.keralty.aegenerasolicitud.persistence.Asesor;
import com.keralty.aegenerasolicitud.persistence.oid.AgentexProductoOid;
import com.keralty.aegenerasolicitud.persistence.Productos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentexProductoJpaRepository extends JpaRepository<AgentexProducto, AgentexProductoOid>, AgentexProductoJpaRepositoryCustom {

    List<AgentexProducto> findByProductoT(Productos productoT);

    List<AgentexProducto> findByAsesor(Asesor asesor);
}
