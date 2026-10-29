package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Dominio;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface DominioJpaRepositoryCustom {

    List<Dominio> solveQuery(SQLSelect query);
    
    List<Dominio> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<Dominio> executeQuery(String query);

    public List<Dominio> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
