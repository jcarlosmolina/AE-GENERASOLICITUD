package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.FormaPago;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface FormaPagoJpaRepositoryCustom {

    List<FormaPago> solveQuery(SQLSelect query);
    
    List<FormaPago> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<FormaPago> executeQuery(String query);

    public List<FormaPago> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
