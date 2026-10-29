package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.DireccionC;
import com.keralty.aegenerasolicitud.persistence.TipoDireccion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDireccionJpaRepository extends JpaRepository<TipoDireccion, Long>, TipoDireccionJpaRepositoryCustom {

    List<TipoDireccion> findByDireccionesC(DireccionC direccionesC);
}
