package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Pais;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface PaisJpaRepositoryCustom {

    List<Pais> solveQuery(SQLSelect query);
    
    List<Pais> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<Pais> executeQuery(String query);

    public List<Pais> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
