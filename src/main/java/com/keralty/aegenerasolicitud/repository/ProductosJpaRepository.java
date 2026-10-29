package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Aesolicitudes;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.Planes;
import com.keralty.aegenerasolicitud.persistence.Productos;
import com.keralty.aegenerasolicitud.persistence.ProteccionDatosC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductosJpaRepository extends JpaRepository<Productos, Long>, ProductosJpaRepositoryCustom {

    List<Productos> findByPlanes(Planes planes);

    List<Productos> findByAESOLICITUDES(Aesolicitudes aESOLICITUDES);

    List<Productos> findByProteccionDatosC(ProteccionDatosC proteccionDatosC);

    List<Productos> findByContratosC(ContratoC contratosC);
}
