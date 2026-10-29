package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.ConceptoC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptoCJpaRepository extends JpaRepository<ConceptoC, Long>, ConceptoCJpaRepositoryCustom {

    List<ConceptoC> findByBeneficiarioC(BeneficiarioC beneficiarioC);
}
