package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Funcionario;
import com.keralty.aegenerasolicitud.persistence.GrupoAsignacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoAsignacionJpaRepository extends JpaRepository<GrupoAsignacion, Long>, GrupoAsignacionJpaRepositoryCustom {

    List<GrupoAsignacion> findByFuncionario(Funcionario funcionario);
}
