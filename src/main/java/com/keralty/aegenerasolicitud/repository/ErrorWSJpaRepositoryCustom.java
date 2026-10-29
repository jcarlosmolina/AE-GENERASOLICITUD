package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.ErrorWS;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface ErrorWSJpaRepositoryCustom {

    List<ErrorWS> solveQuery(SQLSelect query);
    
    List<ErrorWS> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<ErrorWS> executeQuery(String query);

    public List<ErrorWS> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
