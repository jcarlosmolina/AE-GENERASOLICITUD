package com.keralty.aegenerasolicitud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralty.aegenerasolicitud.exception.ModelException;
import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.EpsConstants;
import com.keralty.aegenerasolicitud.global.UsuarioConstants;
import com.keralty.aegenerasolicitud.persistence.AbstractAppEntity;
import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.Eps;
import com.keralty.aegenerasolicitud.persistence.oid.BeneficiarioCOid;
import com.keralty.aegenerasolicitud.persistence.oid.EpsOid;
import com.keralty.aegenerasolicitud.persistence.oid.UsuarioOid;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import com.keralty.aegenerasolicitud.repository.EpsJpaRepository;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereComparison;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereConjunction;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereDisjunction;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandLiteral;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandNull;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandPathAttribute;
import com.keralty.aegenerasolicitud.viewmodel.FilterRequest;
import com.keralty.aegenerasolicitud.viewmodel.FilterVariableRequest;
import com.keralty.aegenerasolicitud.viewmodel.InstanceResponseViewModel;
import com.keralty.aegenerasolicitud.viewmodel.PopulationResponseViewModel;
import com.keralty.aegenerasolicitud.viewmodel.PreloadResponseViewModel;
import com.keralty.aegenerasolicitud.viewmodel.PreloadViewModel;
import com.keralty.aegenerasolicitud.viewmodel.QueryRequest;
import com.keralty.aegenerasolicitud.viewmodel.QueryResponse;
import com.keralty.aegenerasolicitud.viewmodel.ServiceResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

@Transactional
@ApplicationScoped
public class EpsService extends AbstractService {

    EpsJpaRepository repository;

    EpsService epsSrv;

    BeneficiarioCService beneficiarioCSrv;

    UsuarioService usuarioSrv;

    @Inject
    public EpsService(
            EpsService epsSrv,
            BeneficiarioCService beneficiarioCSrv,
            UsuarioService usuarioSrv,
            EpsJpaRepository repository) {
        this.epsSrv = epsSrv;
        this.beneficiarioCSrv = beneficiarioCSrv;
        this.usuarioSrv = usuarioSrv;
        this.repository = repository;
    }

    public List<Map<String, Object>> solveQuery(QueryRequest request) {
        return solveGenericQuery(request, EpsConstants.CLASS_NAME);
    }

    private List<Eps> solveEpsTypedQuery(QueryRequest request) {
        return repository.solveQuery(processQueryRequest(request, EpsConstants.CLASS_NAME), request.getPageSize(), request.getPageNumber());
    }
    
    private int solveEpsTypedCountQuery(QueryRequest request) {
        return repository.solveCountQuery(processCountQueryRequest(request, EpsConstants.CLASS_NAME));
    }

    /*
     * Returns the population of class Eps
     *
     * @return Population of class Eps
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Eps> get() {
        List<Eps> results = new java.util.ArrayList<>();
        repository.findAll().forEach(results::add);
        return results;
    }
    
    /*
     * Gets the preloaded list of objects of class Eps
     *
     * @param queryRequest Request object that dictates the search conditions to 
     * retrieve objects
     *
     * @return Preloaded list of objects of class Eps
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PreloadViewModel> getPreload(QueryRequest queryRequest) {
        List<PreloadViewModel> results = new ArrayList<>();
        List<Eps> instances = get(queryRequest);
        if(EpsConstants.DSICEPS.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildICEPS())).collect(Collectors.toList());
        }
        return results;
    }
    /*
     * Returns the population of class Eps that matches certain search criteria
     *
     * @param queryRequest Search criteria
     *
     * @return Population of class Eps matching the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Eps> get(QueryRequest queryRequest) {
        return solveEpsTypedQuery(queryRequest);
    }
    
    /*
     * Returns the list of instances that matches certain search criteria
     *
     * @param queryRequest Request object that dictates the search conditions to 
     * retrieve objects
     *
     * @return List of instances that matches certain search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public QueryResponse getDynamic(QueryRequest queryRequest) {
        if (queryRequest.getDisplaySetName() == null || queryRequest.getDisplaySetName().isEmpty()) {
            return new PopulationResponseViewModel(solveEpsTypedQuery(queryRequest).stream()
                    .map(instance -> buildDynamicObject(instance, queryRequest.getDisplaySetItems()))
                    .collect(Collectors.toList()), queryRequest.getPageSize() != 0 ? solveEpsTypedCountQuery(queryRequest) : 0);
        } else {
            return new PreloadResponseViewModel(solveEpsTypedQuery(queryRequest).stream()
                    .map(instance -> buildPreloadViewModel(instance, queryRequest.getDisplaySetName()))
                    .collect(Collectors.toList()));
        }
    }

    /*
     * Returns the population of a class with a certain structure
     *
     * @param relatedInstanceJsonOid Json Oid of the related instance
     *
     * @param relatedInstancePath Path to the related instance
     *
     * @param displaySetItems DisplaySet items for each returned instance
     *
     * @return List of instances
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public PopulationResponseViewModel getByRelatedDynamic(String relatedInstanceJsonOid, String relatedInstancePath, String displaySetItems) {
        QueryRequest queryRequest = new QueryRequest(new FilterRequest(Constants.FILTER_BY_RELATED,
                Arrays.asList(new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_PATH, relatedInstancePath),
                        new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_OID, relatedInstanceJsonOid))));
        return new PopulationResponseViewModel(solveEpsTypedQuery(queryRequest).stream()
                .map(instance -> buildDynamicObject(instance, displaySetItems))
                .collect(Collectors.toList()));
    }

    /*
     * Returns an instance of Eps by ID
     *
     * @param epsCodigo Identification field epsCodigo
     *
     * @return Instance of Eps
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Eps getById(Long epsCodigo) {
        return getByOID(new EpsOid(epsCodigo));
    }
    
    /*
     * Returns an instance of Eps by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Eps
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Eps getByOID(EpsOid oid) {
        Optional<Eps> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of Eps by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Eps
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public Eps getByOIDWithHV(EpsOid oid) {
        Optional<Eps> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<Eps> getInstanceByOid(EpsOid oid) {
        return oid != null && oid.getEpsCodigo() != null
               ? repository.findById(oid.getEpsCodigo())
               : Optional.empty();
    }

    /*
     * Returns an instance of Eps by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of Eps
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Eps getByJsonOid(String jsonOid) {
        EpsOid oid = new EpsOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }

    /*
     * Returns the list of instances of Eps related to a given instance through 
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Eps
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Eps> getByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        QueryRequest queryRequest = new QueryRequest(new FilterRequest(Constants.FILTER_BY_RELATED,
                Arrays.asList(new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_PATH, relatedInstancePath),
                        new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_OID, relatedInstance.getOid().toJson()))));
        return solveEpsTypedQuery(queryRequest);
    }

    /*
     * Returns the list of instances of Eps related to a given instance through
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Eps
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Eps> getInstancesByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        return getByRelated(relatedInstance, relatedInstancePath);
    }

    /*
     * Returns a set of displayset items for an instance of Eps
     *
     * @param oid Object IDentifier
     *
     * @param displaySetItems List of comma-separated displayset items
     *
     * @return Set of displayset items for an instance of Eps
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public InstanceResponseViewModel getEpsDynamic(EpsOid oid, String displaySetItems) {
        return new InstanceResponseViewModel(getByOIDWithHV(oid), displaySetItems);
    }

    /*
     * Returns the supplementary info for a given instance
     *
     * @param oid Instance OID
     *
     * @param dsName DisplaySet name
     *
     * @return Supplementary info for a given instance
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public String getSuppInfo(EpsOid oid, String dsName) {
        Eps instance = getByOIDWithHV(oid);
        return (instance != null ? getSuppInfo(instance, dsName): "");
    }

    private String getSuppInfo(Eps instance, String dsName) {
        if (instance == null || instance.isNull()) return "";
        if (EpsConstants.DSICEPS.equals(dsName))
            return instance.buildICEPS();
        return dsName.isEmpty() ? instance.getOid().toJson() : "";
    }

    public static PreloadViewModel buildPreloadViewModel(Eps instance, String dsName) {
        if (EpsConstants.DSICEPS.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildICEPS());
        return new PreloadViewModel(instance.getOid().toJson(), "");
    }


    @Override
    protected void applyQueryFilters(FilterRequest filterRequest, SQLSelect sql) throws SystemException {
        if (EpsConstants.FILTER_NAME_IFEPSSANITAS.equalsIgnoreCase(filterRequest.getName()))
            iFEPSSanitas(sql);
    }

    @Override
    protected void applyNavigationalFilters(FilterRequest filterRequest, SQLSelect sql) throws SystemException {
        if ("Usuario_TMODIFICARATTR_pt_p_evcEPS_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1432633737216826FiltNav13(filterRequest, sql);
    }

    @Override
    protected void applyServiceSIFilters(FilterRequest filterRequest, SQLSelect sql) {
        if ("SuppInfo_IC_EPS".equals(filterRequest.getName()))
            suppInfo4ICEPS(filterRequest.getFilterVariable("searchText").toString(), sql);
    }


    private void iFEPSSanitas(SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();

        // EpsSanitas = true
        // '=' Comparison
        SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
        comparison1.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(EpsConstants.ATTR_NAME_EPSEPSSANITAS, Constants.Type.BOOL.getTypeName());
        comparison1.setFirstOperand(operand112);
        
        // Operand 2
        comparison1.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
        conjunction.addOperand(comparison1);


        sql.addConjunction(conjunction);
    }

    private void clas1432633737216826FiltNav13(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Usuario usuarioTMODIFICARATTRpthisAttrUsuario = filterRequest.hasFilterVariable(UsuarioConstants.ARG_NAME_TMODIFICARATTR_USUARIOTMODIFICARATTRPTHISATTRUSUARIO) ? usuarioSrv.getByOID(new UsuarioOid().fromJsonString(toJson(filterRequest.getFilterVariable(UsuarioConstants.ARG_NAME_TMODIFICARATTR_USUARIOTMODIFICARATTRPTHISATTRUSUARIO)))) : null;

        // EpsSanitas = false OR pthisAttrUsuario.UsuarioPersona.TieneEPSSanitas = true OR pthisAttrUsuario.TipoIdentificacion.MenorSinIdentificar = true
        if (usuarioTMODIFICARATTRpthisAttrUsuario != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison1 = new SQLWhereDisjunction();
        
            // Operand 1
                {
                    // 'or' Logical operator
                    SQLWhereDisjunction comparison2 = new SQLWhereDisjunction();
                
                    // Operand 1
                            // '=' Comparison
                            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
                            comparison3.setOperator("=");
                            
                            // Operand 1
                            SQLWhereOperandPathAttribute operand114 = new SQLWhereOperandPathAttribute(EpsConstants.ATTR_NAME_EPSEPSSANITAS, Constants.Type.BOOL.getTypeName());
                            comparison3.setFirstOperand(operand114);
                            
                            // Operand 2
                            comparison3.setSecondOperand(new SQLWhereOperandLiteral(Boolean.FALSE, Constants.Type.BOOL.getTypeName()));
                            comparison2.addOperand(comparison3);
                
                    // Operand 2
                            // '=' Comparison
                            SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
                            comparison6.setOperator("=");
                            
                            // Operand 1
                            if (usuarioTMODIFICARATTRpthisAttrUsuario.getUsuarioPersona().getPersonaTieneEPSSanitas()!= null){
                            comparison6.setFirstOperand(new SQLWhereOperandLiteral(usuarioTMODIFICARATTRpthisAttrUsuario.getUsuarioPersona().getPersonaTieneEPSSanitas(), Constants.Type.BOOL.getTypeName()));
                            } else {
                            comparison6.setFirstOperand(new SQLWhereOperandNull());
                            }
                            
                            // Operand 2
                            comparison6.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
                            comparison2.addOperand(comparison6);
                    
                    comparison1.addOperand(comparison2);
                }
        
            // Operand 2
                    // '=' Comparison
                    SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
                    comparison8.setOperator("=");
                    
                    // Operand 1
                    comparison8.setFirstOperand(new SQLWhereOperandLiteral(usuarioTMODIFICARATTRpthisAttrUsuario.getTipoIdentificacion().getTipoIdentificacionMenorSinIdentificar(), Constants.Type.BOOL.getTypeName()));
                    
                    // Operand 2
                    comparison8.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
                    comparison1.addOperand(comparison8);
            
            conjunction.addOperand(comparison1);
        }

        }        
        sql.addConjunction(conjunction);
    }

    // IIU Methods


    /*
     * Solves the query needed for PIUEPS
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Eps using displayset AutoDS that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.eps.ds.AutoDS> piuPIUEPS(QueryRequest queryRequest) {
        return solveEpsTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.eps.ds.AutoDS::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUEPS
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUEPS
     */
    public int getCount4PIUEPS(QueryRequest queryRequest) {
        return solveEpsTypedQuery(queryRequest).size();
    }

    // Service: InsEPSC

    /*
     * Entry point for service InsEPSC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse insEPSC(
            com.keralty.aegenerasolicitud.viewmodel.eps.siu.InsEPSCRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = epsSrv.insEPSCTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service InsEPSC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse insEPSCTxn(
            com.keralty.aegenerasolicitud.viewmodel.eps.siu.InsEPSCRequest request) throws ModelException {
        request.setEpsInsEPSCpthisEPSInstance(epsSrv.getByOIDWithHV(request.getEpsInsEPSCpthisEPS()));
        request.setEpsInsEPSCpevcBeneficiarioCInstance(beneficiarioCSrv.getByOIDWithHV(request.getEpsInsEPSCpevcBeneficiarioC()));
        return epsSrv.insEPSCImplementation(request, true);
    }

    /*
     * Implements service InsEPSC
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse insEPSCImplementation(
            com.keralty.aegenerasolicitud.viewmodel.eps.siu.InsEPSCRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Eps epsInsEPSCpthisEPS = request.getEpsInsEPSCpthisEPSInstance();
        BeneficiarioC epsInsEPSCpevcBeneficiarioC = request.getEpsInsEPSCpevcBeneficiarioCInstance();

        request.checkArguments();
        Eps instance = epsInsEPSCpthisEPS;

        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.beneficiarioc.siu.InsEPSCRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.beneficiarioc.siu.InsEPSCRequest();
            relatedRequest.setBeneficiarioCInsEPSCpthisBeneficiarioCInstance(epsInsEPSCpevcBeneficiarioC);
            relatedRequest.setBeneficiarioCInsEPSCpevcEpsInstance(epsInsEPSCpthisEPS);
            beneficiarioCSrv.insEPSCImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICEPS());

        return response;
    }

    // Service: DelEPSC

    /*
     * Entry point for service DelEPSC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse delEPSC(
            com.keralty.aegenerasolicitud.viewmodel.eps.siu.DelEPSCRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = epsSrv.delEPSCTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service DelEPSC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse delEPSCTxn(
            com.keralty.aegenerasolicitud.viewmodel.eps.siu.DelEPSCRequest request) throws ModelException {
        request.setEpsDelEPSCpthisEPSInstance(epsSrv.getByOIDWithHV(request.getEpsDelEPSCpthisEPS()));
        request.setEpsDelEPSCpevcBeneficiarioCInstance(beneficiarioCSrv.getByOIDWithHV(request.getEpsDelEPSCpevcBeneficiarioC()));
        return epsSrv.delEPSCImplementation(request, true);
    }

    /*
     * Implements service DelEPSC
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse delEPSCImplementation(
            com.keralty.aegenerasolicitud.viewmodel.eps.siu.DelEPSCRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Eps epsDelEPSCpthisEPS = request.getEpsDelEPSCpthisEPSInstance();
        BeneficiarioC epsDelEPSCpevcBeneficiarioC = request.getEpsDelEPSCpevcBeneficiarioCInstance();

        request.checkArguments();
        Eps instance = epsDelEPSCpthisEPS;

        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.beneficiarioc.siu.DelEPSCRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.beneficiarioc.siu.DelEPSCRequest();
            relatedRequest.setBeneficiarioCDelEPSCpthisBeneficiarioCInstance(epsDelEPSCpevcBeneficiarioC);
            relatedRequest.setBeneficiarioCDelEPSCpevcEpsInstance(epsDelEPSCpthisEPS);
            beneficiarioCSrv.delEPSCImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICEPS());

        return response;
    }


    private void suppInfo4ICEPS(String searchText, SQLSelect select) {
        try {
            SQLWhereDisjunction disjunction = new SQLWhereDisjunction();

            SQLWhereComparison comparison1 = new SQLWhereComparison(select);
            comparison1.setOperator("LIKE");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(EpsConstants.ATTR_NAME_EPSDESCRIPCION, Constants.Type.STRING.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(searchText, Constants.Type.STRING.getTypeName()));
            disjunction.addComparison(comparison1);

            SQLWhereConjunction conjunction = new SQLWhereConjunction();
            conjunction.addDisjunction(disjunction);
            select.addConjunction(conjunction);
        } catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

    @Override
    protected void applyOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        applyOCOCEPS(queryRequest, select);
        applyDSItemSort(queryRequest, select);
        applyDefaultOrderCriteria(queryRequest, select);
    }

    private void applyDefaultOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        if (queryRequest.getOrderCriteriaName() != null && !queryRequest.getOrderCriteriaName().isBlank())
            return;
        if (queryRequest.getDsItemSort() != null && !queryRequest.getDsItemSort().isBlank())
            return;
        select.getOrderByPart().addItem(Constants.normalizePath(EpsConstants.CLASS_NAME, EpsConstants.ATTR_FIELD_EPSCODIGO, false), true);
    }


    private void applyOCOCEPS(QueryRequest queryRequest, SQLSelect select) {
        if ("OC_EPS".equalsIgnoreCase(queryRequest.getOrderCriteriaName())) {
            select.getOrderByPart().addItem(Constants.normalizePath(EpsConstants.CLASS_NAME, EpsConstants.ATTR_NAME_EPSDESCRIPCION, false), true);
        }
    }
    
    @Override
    protected AbstractAppEntity buildRelatedInstance(String relatedInstanceJsonOid, String relatedInstancePathFirstItem, String remainingPath) {
        AbstractAppEntity relatedInstance = null;
        if (relatedInstancePathFirstItem.isEmpty()) {
            relatedInstance = buildEpsRelatedInstance(relatedInstanceJsonOid);
        }
        if (EpsConstants.ROLE_NAME_ATRIBUTOSUSUARIO.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildEpsRelatedInstance4Usuario(relatedInstanceJsonOid, remainingPath);
        }
        if (EpsConstants.ROLE_NAME_BENEFICIARIOSC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildEpsRelatedInstance4BeneficiarioC(relatedInstanceJsonOid, remainingPath);
        }
        return relatedInstance;
    }
    
    private AbstractAppEntity buildEpsRelatedInstance(String relatedInstanceJsonOid) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		return new Eps(mapper.readValue(relatedInstanceJsonOid, EpsOid.class));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildEpsRelatedInstance4Usuario(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Usuario(mapper.readValue(relatedInstanceJsonOid, UsuarioOid.class)): Usuario.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildEpsRelatedInstance4BeneficiarioC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new BeneficiarioC(mapper.readValue(relatedInstanceJsonOid, BeneficiarioCOid.class)): BeneficiarioC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            EpsOid oid = new EpsOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(EpsConstants.ATTR_FIELD_EPSCODIGO, Constants.Type.INT.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getEpsCodigo(), Constants.Type.INT.getTypeName()));
            sql.addComparison(comparison1);
        } catch (Exception e) {
            LoggerFactory.getLogger(EpsService.class).error(e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

}
