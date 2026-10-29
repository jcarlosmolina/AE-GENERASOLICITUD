package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Departamento;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface DepartamentoJpaRepositoryCustom {

    List<Departamento> solveQuery(SQLSelect query);
    
    List<Departamento> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<Departamento> executeQuery(String query);

    public List<Departamento> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
