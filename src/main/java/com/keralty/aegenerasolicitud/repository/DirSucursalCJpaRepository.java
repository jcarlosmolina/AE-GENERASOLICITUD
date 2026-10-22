package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Departamento;
import com.keralty.aegenerasolicitud.persistence.DirSucursalC;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.Pais;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.TelefonoC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirSucursalCJpaRepository extends JpaRepository<DirSucursalC, Long>, DirSucursalCJpaRepositoryCustom {

    List<DirSucursalC> findByTelefonosC(TelefonoC telefonosC);

    List<DirSucursalC> findByPersonaC(PersonaC personaC);

    List<DirSucursalC> findByMunicipioC(Municipio municipioC);

    List<DirSucursalC> findByDepartamentoC(Departamento departamentoC);

    List<DirSucursalC> findByPaisC(Pais paisC);
}
