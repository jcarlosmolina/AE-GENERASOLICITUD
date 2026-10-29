package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Asesor;
import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.DireccionC;
import com.keralty.aegenerasolicitud.persistence.FamiliaC;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.ObservacionesC;
import com.keralty.aegenerasolicitud.persistence.Periodicidad;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.Planes;
import com.keralty.aegenerasolicitud.persistence.Productos;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoCJpaRepository extends JpaRepository<ContratoC, Long>, ContratoCJpaRepositoryCustom {

    List<ContratoC> findBySolicitud(Solicitud solicitud);

    List<ContratoC> findByPersonaCContratante(PersonaC personaCContratante);

    List<ContratoC> findByDireccionesC(DireccionC direccionesC);

    List<ContratoC> findByObservacionesC(ObservacionesC observacionesC);

    List<ContratoC> findByBeneficiariosC(BeneficiarioC beneficiariosC);

    List<ContratoC> findByFamiliasC(FamiliaC familiasC);

    List<ContratoC> findByPeriodicidadC(Periodicidad periodicidadC);

    List<ContratoC> findByProductoC(Productos productoC);

    List<ContratoC> findByPlanC(Planes planC);

    List<ContratoC> findByMunicipioC(Municipio municipioC);

    List<ContratoC> findByAsesorC(Asesor asesorC);
}
