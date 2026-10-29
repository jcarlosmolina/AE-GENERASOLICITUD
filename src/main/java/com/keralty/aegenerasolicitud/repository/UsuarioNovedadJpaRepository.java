package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.EstadoBeneficiario;
import com.keralty.aegenerasolicitud.persistence.Parentesco;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import com.keralty.aegenerasolicitud.persistence.UsuarioNovedad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioNovedadJpaRepository extends JpaRepository<UsuarioNovedad, Long>, UsuarioNovedadJpaRepositoryCustom {

    List<UsuarioNovedad> findByTipoIdentificacion(TipoIdentificacion tipoIdentificacion);

    List<UsuarioNovedad> findByParentesco(Parentesco parentesco);

    List<UsuarioNovedad> findByEstadoBeneficiario(EstadoBeneficiario estadoBeneficiario);

    List<UsuarioNovedad> findByParentescoNuevo(Parentesco parentescoNuevo);

    List<UsuarioNovedad> findByTipoIdentificacionCD(TipoIdentificacion tipoIdentificacionCD);
}
