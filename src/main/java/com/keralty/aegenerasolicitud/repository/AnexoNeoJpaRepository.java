package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.AnexoNeo;
import com.keralty.aegenerasolicitud.persistence.Periodicidad;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnexoNeoJpaRepository extends JpaRepository<AnexoNeo, Long>, AnexoNeoJpaRepositoryCustom {

    List<AnexoNeo> findByUsuario(Usuario usuario);

    List<AnexoNeo> findByPeriodicidad(Periodicidad periodicidad);
}
