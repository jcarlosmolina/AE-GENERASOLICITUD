package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.FamiliaC;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.oid.FamiliaCOid;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamiliaCJpaRepository extends JpaRepository<FamiliaC, FamiliaCOid>, FamiliaCJpaRepositoryCustom {

    List<FamiliaC> findByPersonaCTitular(PersonaC personaCTitular);

    List<FamiliaC> findByBeneficiariosC(BeneficiarioC beneficiariosC);

    List<FamiliaC> findByContratoC(ContratoC contratoC);

    List<FamiliaC> findByMunicipioC(Municipio municipioC);
}
