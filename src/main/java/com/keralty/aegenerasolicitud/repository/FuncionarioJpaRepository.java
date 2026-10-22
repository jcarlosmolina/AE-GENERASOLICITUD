package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Funcionario;
import com.keralty.aegenerasolicitud.persistence.Rol;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioJpaRepository extends JpaRepository<Funcionario, Long>, FuncionarioJpaRepositoryCustom {

    List<Funcionario> findBySolicitudes(Solicitud solicitudes);

    List<Funcionario> findBySolicitudesAfiliacion(Solicitud solicitudesAfiliacion);

    List<Funcionario> findByRol(Rol rol);
}
