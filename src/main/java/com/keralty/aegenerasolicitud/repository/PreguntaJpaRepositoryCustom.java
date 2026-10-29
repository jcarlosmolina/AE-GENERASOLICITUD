package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Pregunta;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface PreguntaJpaRepositoryCustom {

    List<Pregunta> solveQuery(SQLSelect query);
    
    List<Pregunta> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<Pregunta> executeQuery(String query);

    public List<Pregunta> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
