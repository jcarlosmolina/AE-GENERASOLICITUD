package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import com.keralty.aegenerasolicitud.sqlutils.SQLExists;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface MunicipioJpaRepositoryCustom {

    List<Municipio> solveQuery(SQLSelect query);
    
    List<Municipio> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    Boolean assocOperator005(Usuario usuarioTMODUSUARIOpthisUsuario) throws SystemException;

    Boolean assocOperator005Range1(Usuario usuarioTMODUSUARIOpthisUsuario) throws SystemException;
    
    SQLExists assocOperator005Range1Query(Usuario usuarioTMODUSUARIOpthisUsuario, SQLSelect superQuery) throws SystemException;

    public List<Municipio> executeQuery(String query);

    public List<Municipio> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
