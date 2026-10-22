package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Companyia;
import com.keralty.aegenerasolicitud.persistence.CuestionarioP;
import com.keralty.aegenerasolicitud.persistence.Eps;
import com.keralty.aegenerasolicitud.persistence.EstadoBeneficiario;
import com.keralty.aegenerasolicitud.persistence.oid.UsuarioOid;
import com.keralty.aegenerasolicitud.persistence.Parentesco;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.Preexistencia;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository<Usuario, UsuarioOid>, UsuarioJpaRepositoryCustom {

    List<Usuario> findByCuestionarioP(CuestionarioP cuestionarioP);

    List<Usuario> findByPreexistencias(Preexistencia preexistencias);

    List<Usuario> findBySolicitud(Solicitud solicitud);

    List<Usuario> findByUsuarioPersona(Persona usuarioPersona);

    List<Usuario> findByEps(Eps eps);

    List<Usuario> findByParentesco(Parentesco parentesco);

    List<Usuario> findByCompanyia(Companyia companyia);

    List<Usuario> findByTipoIdentificacion(TipoIdentificacion tipoIdentificacion);

    List<Usuario> findByEstadoBeneficiario(EstadoBeneficiario estadoBeneficiario);
}
