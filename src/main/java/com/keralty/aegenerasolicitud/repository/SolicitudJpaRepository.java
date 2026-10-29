package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Aesolicitudes;
import com.keralty.aegenerasolicitud.persistence.AreaMedica;
import com.keralty.aegenerasolicitud.persistence.Contratante;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.DocumentoG;
import com.keralty.aegenerasolicitud.persistence.ErrorWS;
import com.keralty.aegenerasolicitud.persistence.Funcionario;
import com.keralty.aegenerasolicitud.persistence.LogAsignaciones;
import com.keralty.aegenerasolicitud.persistence.LogEstado;
import com.keralty.aegenerasolicitud.persistence.Periodicidad;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.Titular;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudJpaRepository extends JpaRepository<Solicitud, Long>, SolicitudJpaRepositoryCustom {

    List<Solicitud> findByUsuarios(Usuario usuarios);

    List<Solicitud> findByContratante(Contratante contratante);

    List<Solicitud> findByTitular(Titular titular);

    List<Solicitud> findByDocumentosGestor(DocumentoG documentosGestor);

    List<Solicitud> findByErroresWS(ErrorWS erroresWS);

    List<Solicitud> findByLogEstado(LogEstado logEstado);

    List<Solicitud> findByLogAsignaciones(LogAsignaciones logAsignaciones);

    List<Solicitud> findByAesolicitudes(Aesolicitudes aesolicitudes);

    List<Solicitud> findByContratantePersona(Persona contratantePersona);

    List<Solicitud> findByTitularPersona(Persona titularPersona);

    List<Solicitud> findByAgente(Funcionario agente);

    List<Solicitud> findByPeriodicidad(Periodicidad periodicidad);

    List<Solicitud> findByContratoC(ContratoC contratoC);

    List<Solicitud> findByUsrAreaMedica(AreaMedica usrAreaMedica);

    List<Solicitud> findByUsrAfiliacion(Funcionario usrAfiliacion);
}
