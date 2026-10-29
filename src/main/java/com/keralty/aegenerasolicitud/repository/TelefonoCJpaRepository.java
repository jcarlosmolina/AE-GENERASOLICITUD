package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.DireccionC;
import com.keralty.aegenerasolicitud.persistence.DirSucursalC;
import com.keralty.aegenerasolicitud.persistence.TelefonoC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefonoCJpaRepository extends JpaRepository<TelefonoC, Long>, TelefonoCJpaRepositoryCustom {

    List<TelefonoC> findByDirSucursalC(DirSucursalC dirSucursalC);

    List<TelefonoC> findByDireccionC(DireccionC direccionC);
}
