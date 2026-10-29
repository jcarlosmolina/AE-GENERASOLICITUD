package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.DirSucursalC;
import com.keralty.aegenerasolicitud.persistence.Regional;
import com.keralty.aegenerasolicitud.persistence.RespRegional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionalJpaRepository extends JpaRepository<Regional, Long>, RegionalJpaRepositoryCustom {

    List<Regional> findByRespRegional(RespRegional respRegional);

    List<Regional> findByDirSucursalC(DirSucursalC dirSucursalC);
}
