package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.DocumentoG;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoGJpaRepository extends JpaRepository<DocumentoG, Long>, DocumentoGJpaRepositoryCustom {

    List<DocumentoG> findBySolicitud(Solicitud solicitud);

    List<DocumentoG> findByPersona(Persona persona);
}
