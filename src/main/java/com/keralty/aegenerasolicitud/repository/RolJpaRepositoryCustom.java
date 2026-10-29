package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.persistence.Dominio;
import com.keralty.aegenerasolicitud.persistence.Rol;
import com.keralty.aegenerasolicitud.sqlutils.SQLExists;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface RolJpaRepositoryCustom {

    List<Rol> solveQuery(SQLSelect query);
    
    List<Rol> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    Boolean assocOperator001(Dominio dominioInsRolDominiopthisDominio) throws SystemException;

    Boolean assocOperator001Range1(Dominio dominioInsRolDominiopthisDominio) throws SystemException;
    
    SQLExists assocOperator001Range1Query(Dominio dominioInsRolDominiopthisDominio, SQLSelect superQuery) throws SystemException;

    Boolean assocOperator002(Dominio dominioDelRollDominiopthisDominio) throws SystemException;

    Boolean assocOperator002Range1(Dominio dominioDelRollDominiopthisDominio) throws SystemException;
    
    SQLExists assocOperator002Range1Query(Dominio dominioDelRollDominiopthisDominio, SQLSelect superQuery) throws SystemException;

    public List<Rol> executeQuery(String query);

    public List<Rol> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
