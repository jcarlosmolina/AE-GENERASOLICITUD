package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.persistence.DirSucursalC;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.TypedQueryBuilder;
import com.keralty.aegenerasolicitud.utils.Agent;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.List;

public class DirSucursalCJpaRepositoryImpl extends AbstractRepository implements DirSucursalCJpaRepositoryCustom {

    @Inject
    public DirSucursalCJpaRepositoryImpl(Agent agent, UserFunctions userFunctionsSrv) {
        super(agent, userFunctionsSrv);
    }

    @Override
    public List<DirSucursalC> executeQuery(String jpqlQuery) {
        return getQueryBuilder().build(jpqlQuery).execute();
    }
    
    @Override
    public List<DirSucursalC> executeQuery(String jpqlQuery, List<SQLParam> params) {
        return getQueryBuilder().build(jpqlQuery).setParameters(params).execute();
    }
    
    public Collection<DirSucursalC> executeQuery(SQLSelect select) {
        return getQueryBuilder().build(select).execute();
    }

    private TypedQueryBuilder<DirSucursalC> getQueryBuilder() {
        return new TypedQueryBuilder<>(em, DirSucursalC.class);
    }

    @Override	
    public List<DirSucursalC> solveQuery(SQLSelect select) {
        return solveQuery(select, 0, 0);
    }

    @Override
    public List<DirSucursalC> solveQuery(SQLSelect select, int pageSize, int pageNumber) {
        return getQueryBuilder().build(select, pageSize, pageNumber).execute();
    }

    @Override
    public void refreshEntity(Object object) {
        super.refreshEntity(object);
    }
}
