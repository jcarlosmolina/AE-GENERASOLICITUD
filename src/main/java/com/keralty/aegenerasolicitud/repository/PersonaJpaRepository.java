package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Departamento;
import com.keralty.aegenerasolicitud.persistence.DocumentoG;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaJpaRepository extends JpaRepository<Persona, Long>, PersonaJpaRepositoryCustom {

    List<Persona> findBySolicitudesContratante(Solicitud solicitudesContratante);

    List<Persona> findBySolicitudesTitular(Solicitud solicitudesTitular);

    List<Persona> findByUsuarios(Usuario usuarios);

    List<Persona> findByDocumentosG(DocumentoG documentosG);

    List<Persona> findByTipoIdentificacion(TipoIdentificacion tipoIdentificacion);

    List<Persona> findByDepartamentoNacimiento(Departamento departamentoNacimiento);

    List<Persona> findByMunicipioNacimiento(Municipio municipioNacimiento);
}
