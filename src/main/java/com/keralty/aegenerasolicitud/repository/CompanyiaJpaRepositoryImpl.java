package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.persistence.Companyia;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.TypedQueryBuilder;
import com.keralty.aegenerasolicitud.utils.Agent;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.List;

public class CompanyiaJpaRepositoryImpl extends AbstractRepository implements CompanyiaJpaRepositoryCustom {

    @Inject
    public CompanyiaJpaRepositoryImpl(Agent agent, UserFunctions userFunctionsSrv) {
        super(agent, userFunctionsSrv);
    }

    @Override
    public List<Companyia> executeQuery(String jpqlQuery) {
        return getQueryBuilder().build(jpqlQuery).execute();
    }
    
    @Override
    public List<Companyia> executeQuery(String jpqlQuery, List<SQLParam> params) {
        return getQueryBuilder().build(jpqlQuery).setParameters(params).execute();
    }
    
    public Collection<Companyia> executeQuery(SQLSelect select) {
        return getQueryBuilder().build(select).execute();
    }

    private TypedQueryBuilder<Companyia> getQueryBuilder() {
        return new TypedQueryBuilder<>(em, Companyia.class);
    }

    @Override	
    public List<Companyia> solveQuery(SQLSelect select) {
        return solveQuery(select, 0, 0);
    }

    @Override
    public List<Companyia> solveQuery(SQLSelect select, int pageSize, int pageNumber) {
        return getQueryBuilder().build(select, pageSize, pageNumber).execute();
    }

    @Override
    public void refreshEntity(Object object) {
        super.refreshEntity(object);
    }
}
