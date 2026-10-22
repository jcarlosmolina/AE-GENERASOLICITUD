package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.persistence.ErrorWS;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.TypedQueryBuilder;
import com.keralty.aegenerasolicitud.utils.Agent;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.List;

public class ErrorWSJpaRepositoryImpl extends AbstractRepository implements ErrorWSJpaRepositoryCustom {

    @Inject
    public ErrorWSJpaRepositoryImpl(Agent agent, UserFunctions userFunctionsSrv) {
        super(agent, userFunctionsSrv);
    }

    @Override
    public List<ErrorWS> executeQuery(String jpqlQuery) {
        return getQueryBuilder().build(jpqlQuery).execute();
    }
    
    @Override
    public List<ErrorWS> executeQuery(String jpqlQuery, List<SQLParam> params) {
        return getQueryBuilder().build(jpqlQuery).setParameters(params).execute();
    }
    
    public Collection<ErrorWS> executeQuery(SQLSelect select) {
        return getQueryBuilder().build(select).execute();
    }

    private TypedQueryBuilder<ErrorWS> getQueryBuilder() {
        return new TypedQueryBuilder<>(em, ErrorWS.class);
    }

    @Override	
    public List<ErrorWS> solveQuery(SQLSelect select) {
        return solveQuery(select, 0, 0);
    }

    @Override
    public List<ErrorWS> solveQuery(SQLSelect select, int pageSize, int pageNumber) {
        return getQueryBuilder().build(select, pageSize, pageNumber).execute();
    }

    @Override
    public void refreshEntity(Object object) {
        super.refreshEntity(object);
    }
}
