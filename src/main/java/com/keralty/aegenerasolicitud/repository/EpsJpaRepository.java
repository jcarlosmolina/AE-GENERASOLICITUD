package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.Eps;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpsJpaRepository extends JpaRepository<Eps, Long>, EpsJpaRepositoryCustom {

    List<Eps> findByAtributosUsuario(Usuario atributosUsuario);

    List<Eps> findByBeneficiariosC(BeneficiarioC beneficiariosC);
}
