package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Aesolicitudes;
import com.keralty.aegenerasolicitud.persistence.Planes;
import com.keralty.aegenerasolicitud.persistence.Productos;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AesolicitudesJpaRepository extends JpaRepository<Aesolicitudes, Long>, AesolicitudesJpaRepositoryCustom {

    List<Aesolicitudes> findBySolicitud(Solicitud solicitud);

    List<Aesolicitudes> findByProductoT(Productos productoT);

    List<Aesolicitudes> findByPlanT(Planes planT);
}
