package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.EstadoBeneficiario;
import com.keralty.aegenerasolicitud.persistence.oid.UsuarioRegOid;
import com.keralty.aegenerasolicitud.persistence.Parentesco;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.UsuarioReg;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRegJpaRepository extends JpaRepository<UsuarioReg, UsuarioRegOid>, UsuarioRegJpaRepositoryCustom {

    List<UsuarioReg> findBySolicitud(Solicitud solicitud);

    List<UsuarioReg> findByEstadoBeneficiario(EstadoBeneficiario estadoBeneficiario);

    List<UsuarioReg> findByParentesco(Parentesco parentesco);
}
