package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.DireccionC;
import com.keralty.aegenerasolicitud.persistence.DirSucursalC;
import com.keralty.aegenerasolicitud.persistence.Pais;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisJpaRepository extends JpaRepository<Pais, String>, PaisJpaRepositoryCustom {

    List<Pais> findByDireccionesC(DireccionC direccionesC);

    List<Pais> findByDirSucursalC(DirSucursalC dirSucursalC);
}
