package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.LogLlamadasAE;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface LogLlamadasAEJpaRepositoryCustom {

    List<LogLlamadasAE> solveQuery(SQLSelect query);
    
    List<LogLlamadasAE> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<LogLlamadasAE> executeQuery(String query);

    public List<LogLlamadasAE> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
