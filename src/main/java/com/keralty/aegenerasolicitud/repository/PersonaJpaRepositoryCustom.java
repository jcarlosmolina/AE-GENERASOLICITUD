package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.persistence.Contratante;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.Titular;
import com.keralty.aegenerasolicitud.sqlutils.SQLExists;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLPopulation;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.Collection;
import java.util.List;

public interface PersonaJpaRepositoryCustom {

    List<Persona> solveQuery(SQLSelect query);
    
    List<Persona> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    Boolean assocOperator000(Persona instance) throws SystemException;

    Boolean assocOperator000Range1(Persona instance) throws SystemException;
    
    SQLExists assocOperator000Range1Query(Persona instance) throws SystemException;

    Boolean assocOperator001(Persona instance) throws SystemException;

    Boolean assocOperator001Range1(Persona instance) throws SystemException;
    
    SQLExists assocOperator001Range1Query(Persona instance) throws SystemException;

    Boolean assocOperator002(Persona instance) throws SystemException;

    Boolean assocOperator002Range1(Persona instance) throws SystemException;
    
    SQLExists assocOperator002Range1Query(Persona instance) throws SystemException;

    Boolean assocOperator003(Persona instance) throws SystemException;

    Boolean assocOperator003Range1(Persona instance) throws SystemException;
    
    SQLExists assocOperator003Range1Query(Persona instance) throws SystemException;

    Boolean assocOperator004(Persona instance) throws SystemException;

    Boolean assocOperator004Range1(Persona instance) throws SystemException;
    
    SQLExists assocOperator004Range1Query(Persona instance) throws SystemException;

    Collection<Contratante> assocOperator068(Persona instance) throws SystemException;

    Collection<Contratante> assocOperator068Range1(Persona instance) throws SystemException;
    
    SQLPopulation assocOperator068Range1Query(Persona instance) throws SystemException;

    Collection<Titular> assocOperator069(Persona instance) throws SystemException;

    Collection<Titular> assocOperator069Range1(Persona instance) throws SystemException;
    
    SQLPopulation assocOperator069Range1Query(Persona instance) throws SystemException;

    public List<Persona> executeQuery(String query);

    public List<Persona> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
