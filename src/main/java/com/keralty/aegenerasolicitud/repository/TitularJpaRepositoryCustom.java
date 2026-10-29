package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Titular;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface TitularJpaRepositoryCustom {

    List<Titular> solveQuery(SQLSelect query);
    
    List<Titular> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<Titular> executeQuery(String query);

    public List<Titular> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
