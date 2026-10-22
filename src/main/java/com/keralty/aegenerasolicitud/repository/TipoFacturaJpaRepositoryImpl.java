package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.persistence.TipoFactura;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.TypedQueryBuilder;
import com.keralty.aegenerasolicitud.utils.Agent;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.List;

public class TipoFacturaJpaRepositoryImpl extends AbstractRepository implements TipoFacturaJpaRepositoryCustom {

    @Inject
    public TipoFacturaJpaRepositoryImpl(Agent agent, UserFunctions userFunctionsSrv) {
        super(agent, userFunctionsSrv);
    }

    @Override
    public List<TipoFactura> executeQuery(String jpqlQuery) {
        return getQueryBuilder().build(jpqlQuery).execute();
    }
    
    @Override
    public List<TipoFactura> executeQuery(String jpqlQuery, List<SQLParam> params) {
        return getQueryBuilder().build(jpqlQuery).setParameters(params).execute();
    }
    
    public Collection<TipoFactura> executeQuery(SQLSelect select) {
        return getQueryBuilder().build(select).execute();
    }

    private TypedQueryBuilder<TipoFactura> getQueryBuilder() {
        return new TypedQueryBuilder<>(em, TipoFactura.class);
    }

    @Override	
    public List<TipoFactura> solveQuery(SQLSelect select) {
        return solveQuery(select, 0, 0);
    }

    @Override
    public List<TipoFactura> solveQuery(SQLSelect select, int pageSize, int pageNumber) {
        return getQueryBuilder().build(select, pageSize, pageNumber).execute();
    }

    @Override
    public void refreshEntity(Object object) {
        super.refreshEntity(object);
    }
}
