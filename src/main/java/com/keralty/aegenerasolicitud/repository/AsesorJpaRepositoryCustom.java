package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Asesor;
import com.keralty.aegenerasolicitud.persistence.Funcionario;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface AsesorJpaRepositoryCustom {

    List<Asesor> solveQuery(SQLSelect query);
    
    List<Asesor> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    Funcionario findParent(Long oid);

    public List<Asesor> executeQuery(String query);

    public List<Asesor> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
