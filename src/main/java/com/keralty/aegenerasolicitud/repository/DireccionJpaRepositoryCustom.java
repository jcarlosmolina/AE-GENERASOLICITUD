package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Direccion;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface DireccionJpaRepositoryCustom {

    List<Direccion> solveQuery(SQLSelect query);
    
    List<Direccion> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<Direccion> executeQuery(String query);

    public List<Direccion> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
