package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.ParentescoDom;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface ParentescoDomJpaRepositoryCustom {

    List<ParentescoDom> solveQuery(SQLSelect query);
    
    List<ParentescoDom> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);
    
    Long nextParentescoDomIdentificador(Long dominioNumero);

    public List<ParentescoDom> executeQuery(String query);

    public List<ParentescoDom> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
