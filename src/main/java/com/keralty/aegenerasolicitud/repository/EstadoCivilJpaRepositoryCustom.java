package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.EstadoCivil;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface EstadoCivilJpaRepositoryCustom {

    List<EstadoCivil> solveQuery(SQLSelect query);
    
    List<EstadoCivil> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<EstadoCivil> executeQuery(String query);

    public List<EstadoCivil> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
