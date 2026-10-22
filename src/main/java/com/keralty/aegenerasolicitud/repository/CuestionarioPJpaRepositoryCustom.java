package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.persistence.CuestionarioP;
import com.keralty.aegenerasolicitud.persistence.PreguntaP;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLPopulation;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.Collection;
import java.util.List;

public interface CuestionarioPJpaRepositoryCustom {

    List<CuestionarioP> solveQuery(SQLSelect query);
    
    List<CuestionarioP> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    Collection<PreguntaP> assocOperator008(CuestionarioP instance) throws SystemException;

    Collection<PreguntaP> assocOperator008Range1(CuestionarioP instance) throws SystemException;
    
    SQLPopulation assocOperator008Range1Query(CuestionarioP instance) throws SystemException;

    public List<CuestionarioP> executeQuery(String query);

    public List<CuestionarioP> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
