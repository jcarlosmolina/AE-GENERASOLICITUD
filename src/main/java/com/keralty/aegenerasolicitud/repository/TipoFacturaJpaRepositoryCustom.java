package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.TipoFactura;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface TipoFacturaJpaRepositoryCustom {

    List<TipoFactura> solveQuery(SQLSelect query);
    
    List<TipoFactura> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<TipoFactura> executeQuery(String query);

    public List<TipoFactura> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
