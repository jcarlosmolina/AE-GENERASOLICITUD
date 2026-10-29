package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.Funcionario;
import com.keralty.aegenerasolicitud.persistence.Sucursal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucursalJpaRepository extends JpaRepository<Sucursal, Long>, SucursalJpaRepositoryCustom {

    List<Sucursal> findByAgentes(Funcionario agentes);

    List<Sucursal> findByContratosC(ContratoC contratosC);
}
