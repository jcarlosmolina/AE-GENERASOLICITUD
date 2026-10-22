package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.GrupoAsignacion;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface GrupoAsignacionJpaRepositoryCustom {

    List<GrupoAsignacion> solveQuery(SQLSelect query);
    
    List<GrupoAsignacion> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<GrupoAsignacion> executeQuery(String query);

    public List<GrupoAsignacion> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
