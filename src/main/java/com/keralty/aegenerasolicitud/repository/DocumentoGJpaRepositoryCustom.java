package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.DocumentoG;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface DocumentoGJpaRepositoryCustom {

    List<DocumentoG> solveQuery(SQLSelect query);
    
    List<DocumentoG> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<DocumentoG> executeQuery(String query);

    public List<DocumentoG> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
