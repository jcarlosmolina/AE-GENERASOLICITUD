package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.PreguntaP;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface PreguntaPJpaRepositoryCustom {

    List<PreguntaP> solveQuery(SQLSelect query);
    
    List<PreguntaP> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<PreguntaP> executeQuery(String query);

    public List<PreguntaP> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
