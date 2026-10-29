package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Productos;
import com.keralty.aegenerasolicitud.persistence.ProteccionDatos;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProteccionDatosJpaRepository extends JpaRepository<ProteccionDatos, Long>, ProteccionDatosJpaRepositoryCustom {

    List<ProteccionDatos> findByProductoT(Productos productoT);

    List<ProteccionDatos> findBySolicitud(Solicitud solicitud);
}
