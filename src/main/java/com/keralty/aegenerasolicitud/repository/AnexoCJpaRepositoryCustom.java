package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.AnexoC;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface AnexoCJpaRepositoryCustom {

    List<AnexoC> solveQuery(SQLSelect query);
    
    List<AnexoC> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<AnexoC> executeQuery(String query);

    public List<AnexoC> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
