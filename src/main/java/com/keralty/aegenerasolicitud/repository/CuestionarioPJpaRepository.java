package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.CuestionarioP;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuestionarioPJpaRepository extends JpaRepository<CuestionarioP, Long>, CuestionarioPJpaRepositoryCustom {

    List<CuestionarioP> findByUsuario(Usuario usuario);
}
