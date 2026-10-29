package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.AnexoC;
import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.Companyia;
import com.keralty.aegenerasolicitud.persistence.ConceptoC;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.Eps;
import com.keralty.aegenerasolicitud.persistence.EstadoBeneficiario;
import com.keralty.aegenerasolicitud.persistence.FamiliaC;
import com.keralty.aegenerasolicitud.persistence.oid.BeneficiarioCOid;
import com.keralty.aegenerasolicitud.persistence.Parentesco;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiarioCJpaRepository extends JpaRepository<BeneficiarioC, BeneficiarioCOid>, BeneficiarioCJpaRepositoryCustom {

    List<BeneficiarioC> findByPersonaC(PersonaC personaC);

    List<BeneficiarioC> findByAnexosC(AnexoC anexosC);

    List<BeneficiarioC> findByConceptosC(ConceptoC conceptosC);

    List<BeneficiarioC> findByContratoC(ContratoC contratoC);

    List<BeneficiarioC> findByFamiliasC(FamiliaC familiasC);

    List<BeneficiarioC> findByEPSC(Eps ePSC);

    List<BeneficiarioC> findByCompanyiaMPC(Companyia companyiaMPC);

    List<BeneficiarioC> findByParentescoC(Parentesco parentescoC);

    List<BeneficiarioC> findByEstadoBeneficiarioC(EstadoBeneficiario estadoBeneficiarioC);
}
