package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Funcionario;
import com.keralty.aegenerasolicitud.persistence.Periodicidad;
import com.keralty.aegenerasolicitud.persistence.Planes;
import com.keralty.aegenerasolicitud.persistence.SolicitudNovedad;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudNovedadJpaRepository extends JpaRepository<SolicitudNovedad, Long>, SolicitudNovedadJpaRepositoryCustom {

    List<SolicitudNovedad> findByPlanT(Planes planT);

    List<SolicitudNovedad> findByTipoIdentContratante(TipoIdentificacion tipoIdentContratante);

    List<SolicitudNovedad> findByPeriodicidad(Periodicidad periodicidad);

    List<SolicitudNovedad> findByAgente(Funcionario agente);

    List<SolicitudNovedad> findByPeriodicidadNueva(Periodicidad periodicidadNueva);

    List<SolicitudNovedad> findByUsrAfiliacion(Funcionario usrAfiliacion);
}
