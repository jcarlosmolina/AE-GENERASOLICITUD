package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.LogLlamadasBH;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface LogLlamadasBHJpaRepositoryCustom {

    List<LogLlamadasBH> solveQuery(SQLSelect query);
    
    List<LogLlamadasBH> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<LogLlamadasBH> executeQuery(String query);

    public List<LogLlamadasBH> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
