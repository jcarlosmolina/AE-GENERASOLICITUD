package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Regional;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface RegionalJpaRepositoryCustom {

    List<Regional> solveQuery(SQLSelect query);
    
    List<Regional> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<Regional> executeQuery(String query);

    public List<Regional> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
