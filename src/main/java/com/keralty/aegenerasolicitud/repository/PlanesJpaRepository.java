package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Aesolicitudes;
import com.keralty.aegenerasolicitud.persistence.Afiliaciones;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.Planes;
import com.keralty.aegenerasolicitud.persistence.Productos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanesJpaRepository extends JpaRepository<Planes, Long>, PlanesJpaRepositoryCustom {

    List<Planes> findByAESOLICITUDES(Aesolicitudes aESOLICITUDES);

    List<Planes> findByContratosC(ContratoC contratosC);

    List<Planes> findByAfiliaciones(Afiliaciones afiliaciones);

    List<Planes> findByProductoT(Productos productoT);
}
