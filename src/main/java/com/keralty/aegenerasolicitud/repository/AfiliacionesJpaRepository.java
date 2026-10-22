package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Afiliaciones;
import com.keralty.aegenerasolicitud.persistence.Planes;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AfiliacionesJpaRepository extends JpaRepository<Afiliaciones, Long>, AfiliacionesJpaRepositoryCustom {

    List<Afiliaciones> findByPlanes(Planes planes);
}
