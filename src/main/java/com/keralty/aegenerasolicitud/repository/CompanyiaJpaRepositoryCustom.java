package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Companyia;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface CompanyiaJpaRepositoryCustom {

    List<Companyia> solveQuery(SQLSelect query);
    
    List<Companyia> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<Companyia> executeQuery(String query);

    public List<Companyia> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
