package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.Parentesco;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentescoJpaRepository extends JpaRepository<Parentesco, Long>, ParentescoJpaRepositoryCustom {

    List<Parentesco> findByAtributosUsuario(Usuario atributosUsuario);

    List<Parentesco> findByBeneficiariosC(BeneficiarioC beneficiariosC);
}
