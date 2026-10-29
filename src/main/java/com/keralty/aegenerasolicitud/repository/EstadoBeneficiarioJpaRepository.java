package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.EstadoBeneficiario;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoBeneficiarioJpaRepository extends JpaRepository<EstadoBeneficiario, Long>, EstadoBeneficiarioJpaRepositoryCustom {

    List<EstadoBeneficiario> findByUsuarioS(Usuario usuarioS);

    List<EstadoBeneficiario> findByBeneficiariosC(BeneficiarioC beneficiariosC);
}
