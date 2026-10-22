package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.UsuarioSistemaExterno;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface UsuarioSistemaExternoJpaRepositoryCustom {

    List<UsuarioSistemaExterno> solveQuery(SQLSelect query);
    
    List<UsuarioSistemaExterno> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    public List<UsuarioSistemaExterno> executeQuery(String query);

    public List<UsuarioSistemaExterno> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
