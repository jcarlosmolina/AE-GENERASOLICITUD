package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.AnexoC;
import com.keralty.aegenerasolicitud.persistence.Asesor;
import com.keralty.aegenerasolicitud.persistence.Contratante;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import com.keralty.aegenerasolicitud.persistence.Titular;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoIdentificacionJpaRepository extends JpaRepository<TipoIdentificacion, Long>, TipoIdentificacionJpaRepositoryCustom {

    List<TipoIdentificacion> findByAsesores(Asesor asesores);

    List<TipoIdentificacion> findByPersonas(Persona personas);

    List<TipoIdentificacion> findByPersonasC(PersonaC personasC);

    List<TipoIdentificacion> findByUsuarioS(Usuario usuarioS);

    List<TipoIdentificacion> findByContratantes(Contratante contratantes);

    List<TipoIdentificacion> findByAnexosC(AnexoC anexosC);

    List<TipoIdentificacion> findByTitulares(Titular titulares);
}
