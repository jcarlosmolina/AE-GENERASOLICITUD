package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.oid.ParentescoDomOid;
import com.keralty.aegenerasolicitud.persistence.Parentesco;
import com.keralty.aegenerasolicitud.persistence.ParentescoDom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentescoDomJpaRepository extends JpaRepository<ParentescoDom, ParentescoDomOid>, ParentescoDomJpaRepositoryCustom {

    List<ParentescoDom> findByParentesco(Parentesco parentesco);
}
