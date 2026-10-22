package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Contratante;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.Departamento;
import com.keralty.aegenerasolicitud.persistence.DireccionC;
import com.keralty.aegenerasolicitud.persistence.DirSucursalC;
import com.keralty.aegenerasolicitud.persistence.FamiliaC;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.Titular;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipioJpaRepository extends JpaRepository<Municipio, Long>, MunicipioJpaRepositoryCustom {

    List<Municipio> findByPersonas(Persona personas);

    List<Municipio> findByPersonasC(PersonaC personasC);

    List<Municipio> findByDireccionesC(DireccionC direccionesC);

    List<Municipio> findByContratantes(Contratante contratantes);

    List<Municipio> findByContratanteRes(Contratante contratanteRes);

    List<Municipio> findByContratosC(ContratoC contratosC);

    List<Municipio> findByDirSucursalC(DirSucursalC dirSucursalC);

    List<Municipio> findByFamiliaC(FamiliaC familiaC);

    List<Municipio> findByTitulares(Titular titulares);

    List<Municipio> findByDepartamento(Departamento departamento);
}
