package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.ObservacionesC;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservacionesCJpaRepository extends JpaRepository<ObservacionesC, Long>, ObservacionesCJpaRepositoryCustom {

    List<ObservacionesC> findByContratoC(ContratoC contratoC);
}
