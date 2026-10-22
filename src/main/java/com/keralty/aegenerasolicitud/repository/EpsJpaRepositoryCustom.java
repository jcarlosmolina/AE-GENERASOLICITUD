package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Eps;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface EpsJpaRepositoryCustom {

    List<Eps> solveQuery(SQLSelect query);
    
    List<Eps> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<Eps> executeQuery(String query);

    public List<Eps> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
