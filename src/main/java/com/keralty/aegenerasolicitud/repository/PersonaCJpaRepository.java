package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.Departamento;
import com.keralty.aegenerasolicitud.persistence.DireccionC;
import com.keralty.aegenerasolicitud.persistence.DirSucursalC;
import com.keralty.aegenerasolicitud.persistence.FamiliaC;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.PreexistenciaC;
import com.keralty.aegenerasolicitud.persistence.PregCuesMedC;
import com.keralty.aegenerasolicitud.persistence.ProteccionDatosC;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaCJpaRepository extends JpaRepository<PersonaC, Long>, PersonaCJpaRepositoryCustom {

    List<PersonaC> findByDireccionesC(DireccionC direccionesC);

    List<PersonaC> findByProteccionesDatosC(ProteccionDatosC proteccionesDatosC);

    List<PersonaC> findByPregsCuesMedC(PregCuesMedC pregsCuesMedC);

    List<PersonaC> findByDirSucursalesC(DirSucursalC dirSucursalesC);

    List<PersonaC> findByPreexistenciasC(PreexistenciaC preexistenciasC);

    List<PersonaC> findByTipoIdentificacionC(TipoIdentificacion tipoIdentificacionC);

    List<PersonaC> findByMunicipioCNac(Municipio municipioCNac);

    List<PersonaC> findByDepartamentoCNac(Departamento departamentoCNac);

    List<PersonaC> findByContratosC(ContratoC contratosC);

    List<PersonaC> findByFamiliasC(FamiliaC familiasC);

    List<PersonaC> findByBeneficiariosC(BeneficiarioC beneficiariosC);
}
