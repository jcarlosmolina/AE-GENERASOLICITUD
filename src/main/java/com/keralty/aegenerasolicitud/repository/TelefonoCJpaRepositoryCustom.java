package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.TelefonoC;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface TelefonoCJpaRepositoryCustom {

    List<TelefonoC> solveQuery(SQLSelect query);
    
    List<TelefonoC> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<TelefonoC> executeQuery(String query);

    public List<TelefonoC> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
