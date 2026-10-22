package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.oid.PreexistenciaOid;
import com.keralty.aegenerasolicitud.persistence.Preexistencia;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreexistenciaJpaRepository extends JpaRepository<Preexistencia, PreexistenciaOid>, PreexistenciaJpaRepositoryCustom {

    List<Preexistencia> findByUsuario(Usuario usuario);
}
