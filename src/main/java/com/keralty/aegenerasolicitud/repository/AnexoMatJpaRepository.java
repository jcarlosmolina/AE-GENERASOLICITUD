package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.AnexoMat;
import com.keralty.aegenerasolicitud.persistence.Periodicidad;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnexoMatJpaRepository extends JpaRepository<AnexoMat, Long>, AnexoMatJpaRepositoryCustom {

    List<AnexoMat> findByUsuario(Usuario usuario);

    List<AnexoMat> findByPeriodicidad(Periodicidad periodicidad);
}
