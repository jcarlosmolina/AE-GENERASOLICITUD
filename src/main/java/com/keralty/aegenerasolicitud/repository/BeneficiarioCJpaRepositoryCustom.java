package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.ConceptoC;
import com.keralty.aegenerasolicitud.persistence.PreexistenciaC;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import com.keralty.aegenerasolicitud.sqlutils.SQLExists;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLPopulation;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.SQLSum;
import java.util.Collection;
import java.util.List;

public interface BeneficiarioCJpaRepositoryCustom {

    List<BeneficiarioC> solveQuery(SQLSelect query);
    
    List<BeneficiarioC> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);
    
    Long nextBeneficiarioCID(Long contratoCContratoCID);

    Double assocOperator000(BeneficiarioC instance) throws SystemException;

    Double assocOperator000Range1(BeneficiarioC instance) throws SystemException;
    
    SQLSum assocOperator000Range1Query(BeneficiarioC instance) throws SystemException;

    Double assocOperator001(BeneficiarioC instance) throws SystemException;

    Double assocOperator001Range1(BeneficiarioC instance) throws SystemException;
    
    SQLSum assocOperator001Range1Query(BeneficiarioC instance) throws SystemException;

    Double assocOperator002(BeneficiarioC instance) throws SystemException;

    Double assocOperator002Range1(BeneficiarioC instance) throws SystemException;
    
    SQLSum assocOperator002Range1Query(BeneficiarioC instance) throws SystemException;

    Double assocOperator003(BeneficiarioC instance) throws SystemException;

    Double assocOperator003Range1(BeneficiarioC instance) throws SystemException;
    
    SQLSum assocOperator003Range1Query(BeneficiarioC instance) throws SystemException;

    Double assocOperator004(BeneficiarioC instance) throws SystemException;

    Double assocOperator004Range1(BeneficiarioC instance) throws SystemException;
    
    SQLSum assocOperator004Range1Query(BeneficiarioC instance) throws SystemException;

    Double assocOperator005(BeneficiarioC instance) throws SystemException;

    Double assocOperator005Range1(BeneficiarioC instance) throws SystemException;
    
    SQLSum assocOperator005Range1Query(BeneficiarioC instance) throws SystemException;

    Double assocOperator006(BeneficiarioC instance) throws SystemException;

    Double assocOperator006Range1(BeneficiarioC instance) throws SystemException;
    
    SQLSum assocOperator006Range1Query(BeneficiarioC instance) throws SystemException;

    Double assocOperator007(BeneficiarioC instance) throws SystemException;

    Double assocOperator007Range1(BeneficiarioC instance) throws SystemException;
    
    SQLSum assocOperator007Range1Query(BeneficiarioC instance) throws SystemException;

    Boolean assocOperator008(BeneficiarioC instance) throws SystemException;

    Boolean assocOperator008Range1(BeneficiarioC instance) throws SystemException;
    
    SQLExists assocOperator008Range1Query(BeneficiarioC instance) throws SystemException;

    Boolean assocOperator009(BeneficiarioC instance) throws SystemException;

    Boolean assocOperator009Range1(BeneficiarioC instance) throws SystemException;
    
    SQLExists assocOperator009Range1Query(BeneficiarioC instance) throws SystemException;

    Boolean assocOperator022(Usuario beneficiarioCTNEWDESDEAEptUsuario) throws SystemException;

    Boolean assocOperator022Range1(Usuario beneficiarioCTNEWDESDEAEptUsuario) throws SystemException;
    
    SQLExists assocOperator022Range1Query(Usuario beneficiarioCTNEWDESDEAEptUsuario, SQLSelect superQuery) throws SystemException;

    Boolean assocOperator023(Usuario beneficiarioCTNEWDESDEAEptUsuario) throws SystemException;

    Boolean assocOperator023Range1(Usuario beneficiarioCTNEWDESDEAEptUsuario) throws SystemException;
    
    SQLExists assocOperator023Range1Query(Usuario beneficiarioCTNEWDESDEAEptUsuario, SQLSelect superQuery) throws SystemException;

    Collection<PreexistenciaC> assocOperator024(BeneficiarioC instance) throws SystemException;

    Collection<PreexistenciaC> assocOperator024Range1(BeneficiarioC instance) throws SystemException;
    
    SQLPopulation assocOperator024Range1Query(BeneficiarioC instance) throws SystemException;

    Collection<ConceptoC> assocOperator025(BeneficiarioC instance) throws SystemException;

    Collection<ConceptoC> assocOperator025Range1(BeneficiarioC instance) throws SystemException;
    
    SQLPopulation assocOperator025Range1Query(BeneficiarioC instance) throws SystemException;

    Boolean assocOperator029(BeneficiarioC instance) throws SystemException;

    Boolean assocOperator029Range1(BeneficiarioC instance) throws SystemException;
    
    SQLExists assocOperator029Range1Query(BeneficiarioC instance) throws SystemException;

    Boolean assocOperator030(BeneficiarioC instance) throws SystemException;

    Boolean assocOperator030Range1(BeneficiarioC instance) throws SystemException;
    
    SQLExists assocOperator030Range1Query(BeneficiarioC instance) throws SystemException;

    Boolean assocOperator031(BeneficiarioC instance) throws SystemException;

    Boolean assocOperator031Range1(BeneficiarioC instance) throws SystemException;
    
    SQLExists assocOperator031Range1Query(BeneficiarioC instance) throws SystemException;

    Boolean assocOperator033(BeneficiarioC instance) throws SystemException;

    Boolean assocOperator033Range1(BeneficiarioC instance) throws SystemException;
    
    SQLExists assocOperator033Range1Query(BeneficiarioC instance) throws SystemException;

    public List<BeneficiarioC> executeQuery(String query);

    public List<BeneficiarioC> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
