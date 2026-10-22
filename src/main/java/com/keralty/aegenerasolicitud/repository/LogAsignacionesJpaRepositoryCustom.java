package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.LogAsignaciones;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface LogAsignacionesJpaRepositoryCustom {

    List<LogAsignaciones> solveQuery(SQLSelect query);
    
    List<LogAsignaciones> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<LogAsignaciones> executeQuery(String query);

    public List<LogAsignaciones> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
