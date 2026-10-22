package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.Departamento;
import com.keralty.aegenerasolicitud.persistence.DireccionC;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.Pais;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.TelefonoC;
import com.keralty.aegenerasolicitud.persistence.TipoDireccion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DireccionCJpaRepository extends JpaRepository<DireccionC, Long>, DireccionCJpaRepositoryCustom {

    List<DireccionC> findByTelefonosC(TelefonoC telefonosC);

    List<DireccionC> findByPersonaC(PersonaC personaC);

    List<DireccionC> findByContratoC(ContratoC contratoC);

    List<DireccionC> findByTipoDireccionC(TipoDireccion tipoDireccionC);

    List<DireccionC> findByDepartamentoC(Departamento departamentoC);

    List<DireccionC> findByMunicipioC(Municipio municipioC);

    List<DireccionC> findByPaisC(Pais paisC);
}
