package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.ObservacionesC;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface ObservacionesCJpaRepositoryCustom {

    List<ObservacionesC> solveQuery(SQLSelect query);
    
    List<ObservacionesC> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<ObservacionesC> executeQuery(String query);

    public List<ObservacionesC> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
