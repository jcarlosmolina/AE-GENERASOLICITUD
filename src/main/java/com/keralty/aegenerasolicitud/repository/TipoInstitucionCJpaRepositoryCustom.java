package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.TipoInstitucionC;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface TipoInstitucionCJpaRepositoryCustom {

    List<TipoInstitucionC> solveQuery(SQLSelect query);
    
    List<TipoInstitucionC> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<TipoInstitucionC> executeQuery(String query);

    public List<TipoInstitucionC> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
