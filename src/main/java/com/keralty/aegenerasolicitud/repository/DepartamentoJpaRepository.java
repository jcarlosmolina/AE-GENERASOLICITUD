package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Contratante;
import com.keralty.aegenerasolicitud.persistence.Departamento;
import com.keralty.aegenerasolicitud.persistence.DireccionC;
import com.keralty.aegenerasolicitud.persistence.DirSucursalC;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.Titular;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoJpaRepository extends JpaRepository<Departamento, Long>, DepartamentoJpaRepositoryCustom {

    List<Departamento> findByPersonas(Persona personas);

    List<Departamento> findByMunicipios(Municipio municipios);

    List<Departamento> findByPersonasC(PersonaC personasC);

    List<Departamento> findByDireccionesC(DireccionC direccionesC);

    List<Departamento> findByContratantes(Contratante contratantes);

    List<Departamento> findByContratantesRes(Contratante contratantesRes);

    List<Departamento> findByDirSucursalC(DirSucursalC dirSucursalC);

    List<Departamento> findByTitulares(Titular titulares);
}
