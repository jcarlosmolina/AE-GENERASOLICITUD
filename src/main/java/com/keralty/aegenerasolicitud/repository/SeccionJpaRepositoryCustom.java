package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Seccion;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface SeccionJpaRepositoryCustom {

    List<Seccion> solveQuery(SQLSelect query);
    
    List<Seccion> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<Seccion> executeQuery(String query);

    public List<Seccion> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
