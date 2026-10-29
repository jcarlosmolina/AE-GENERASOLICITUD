package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.InstitucionMatNeo;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitucionMatNeoJpaRepository extends JpaRepository<InstitucionMatNeo, String>, InstitucionMatNeoJpaRepositoryCustom {

    List<InstitucionMatNeo> findByTipoIdentificacion(TipoIdentificacion tipoIdentificacion);

    List<InstitucionMatNeo> findByMunicipio(Municipio municipio);
}
