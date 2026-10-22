package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Departamento;
import com.keralty.aegenerasolicitud.persistence.Direccion;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.Pais;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.TipoDireccion;
import com.keralty.aegenerasolicitud.persistence.Titular;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DireccionJpaRepository extends JpaRepository<Direccion, Long>, DireccionJpaRepositoryCustom {

    List<Direccion> findByPersona(Persona persona);

    List<Direccion> findByTipoDireccion(TipoDireccion tipoDireccion);

    List<Direccion> findByMunicipio(Municipio municipio);

    List<Direccion> findByDepartamento(Departamento departamento);

    List<Direccion> findByPais(Pais pais);

    List<Direccion> findByContrato(Solicitud contrato);

    List<Direccion> findByTitular(Titular titular);
}
