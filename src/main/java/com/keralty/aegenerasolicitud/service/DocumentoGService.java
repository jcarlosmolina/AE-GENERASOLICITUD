package com.keralty.aegenerasolicitud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralty.aegenerasolicitud.exception.ModelException;
import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.AsesorConstants;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.DocumentoGConstants;
import com.keralty.aegenerasolicitud.global.FuncionarioConstants;
import com.keralty.aegenerasolicitud.global.SolicitudConstants;
import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.persistence.AbstractAppEntity;
import com.keralty.aegenerasolicitud.persistence.Asesor;
import com.keralty.aegenerasolicitud.persistence.DocumentoG;
import com.keralty.aegenerasolicitud.persistence.oid.DocumentoGOid;
import com.keralty.aegenerasolicitud.persistence.oid.PersonaOid;
import com.keralty.aegenerasolicitud.persistence.oid.SolicitudOid;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.repository.DocumentoGJpaRepository;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereComparison;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereConjunction;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereDisjunction;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandLiteral;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandOVParameter;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandPathAttribute;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandPathInstance;
import com.keralty.aegenerasolicitud.viewmodel.FilterRequest;
import com.keralty.aegenerasolicitud.viewmodel.FilterVariableRequest;
import com.keralty.aegenerasolicitud.viewmodel.InstanceResponseViewModel;
import com.keralty.aegenerasolicitud.viewmodel.PopulationResponseViewModel;
import com.keralty.aegenerasolicitud.viewmodel.PreloadResponseViewModel;
import com.keralty.aegenerasolicitud.viewmodel.QueryRequest;
import com.keralty.aegenerasolicitud.viewmodel.QueryResponse;
import com.keralty.aegenerasolicitud.viewmodel.ServiceResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

@Transactional
@ApplicationScoped
public class DocumentoGService extends AbstractService {

    DocumentoGJpaRepository repository;

    DocumentoGService documentoGSrv;

    SolicitudService solicitudSrv;

    PersonaService personaSrv;

    UserFunctions userFunctionsSrv;

    @Inject
    public DocumentoGService(
            DocumentoGService documentoGSrv,
            SolicitudService solicitudSrv,
            PersonaService personaSrv,
            UserFunctions userFunctionsSrv,
            DocumentoGJpaRepository repository) {
        this.documentoGSrv = documentoGSrv;
        this.solicitudSrv = solicitudSrv;
        this.personaSrv = personaSrv;
        this.userFunctionsSrv = userFunctionsSrv;
        this.repository = repository;
    }

    public List<Map<String, Object>> solveQuery(QueryRequest request) {
        return solveGenericQuery(request, DocumentoGConstants.CLASS_NAME);
    }

    private List<DocumentoG> solveDocumentoGTypedQuery(QueryRequest request) {
        return repository.solveQuery(processQueryRequest(request, DocumentoGConstants.CLASS_NAME), request.getPageSize(), request.getPageNumber());
    }
    
    private int solveDocumentoGTypedCountQuery(QueryRequest request) {
        return repository.solveCountQuery(processCountQueryRequest(request, DocumentoGConstants.CLASS_NAME));
    }

    /*
     * Returns the population of class DocumentoG
     *
     * @return Population of class DocumentoG
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<DocumentoG> get() {
        List<DocumentoG> results = new java.util.ArrayList<>();
        repository.findAll().forEach(results::add);
        return results;
    }
    /*
     * Returns the population of class DocumentoG that matches certain search criteria
     *
     * @param queryRequest Search criteria
     *
     * @return Population of class DocumentoG matching the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<DocumentoG> get(QueryRequest queryRequest) {
        return solveDocumentoGTypedQuery(queryRequest);
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
            return new PopulationResponseViewModel(solveDocumentoGTypedQuery(queryRequest).stream()
                    .map(instance -> buildDynamicObject(instance, queryRequest.getDisplaySetItems()))
                    .collect(Collectors.toList()), queryRequest.getPageSize() != 0 ? solveDocumentoGTypedCountQuery(queryRequest) : 0);
        } else {
            return new PreloadResponseViewModel();
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
        return new PopulationResponseViewModel(solveDocumentoGTypedQuery(queryRequest).stream()
                .map(instance -> buildDynamicObject(instance, displaySetItems))
                .collect(Collectors.toList()));
    }

    /*
     * Returns an instance of DocumentoG by ID
     *
     * @param documentoGIdentificador Identification field documentoGIdentificador
     *
     * @return Instance of DocumentoG
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public DocumentoG getById(Long documentoGIdentificador) {
        return getByOID(new DocumentoGOid(documentoGIdentificador));
    }
    
    /*
     * Returns an instance of DocumentoG by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of DocumentoG
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public DocumentoG getByOID(DocumentoGOid oid) {
        Optional<DocumentoG> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of DocumentoG by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of DocumentoG
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public DocumentoG getByOIDWithHV(DocumentoGOid oid) {
        Optional<DocumentoG> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<DocumentoG> getInstanceByOid(DocumentoGOid oid) {
        return oid != null && oid.getDocumentoGIdentificador() != null
               ? repository.findById(oid.getDocumentoGIdentificador())
               : Optional.empty();
    }

    /*
     * Returns an instance of DocumentoG by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of DocumentoG
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public DocumentoG getByJsonOid(String jsonOid) {
        DocumentoGOid oid = new DocumentoGOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }

    /*
     * Returns the list of instances of DocumentoG related to a given instance through 
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of DocumentoG
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<DocumentoG> getByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        QueryRequest queryRequest = new QueryRequest(new FilterRequest(Constants.FILTER_BY_RELATED,
                Arrays.asList(new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_PATH, relatedInstancePath),
                        new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_OID, relatedInstance.getOid().toJson()))));
        return solveDocumentoGTypedQuery(queryRequest);
    }

    /*
     * Returns the list of instances of DocumentoG related to a given instance through
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of DocumentoG
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<DocumentoG> getInstancesByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        return getByRelated(relatedInstance, relatedInstancePath);
    }

    /*
     * Returns a set of displayset items for an instance of DocumentoG
     *
     * @param oid Object IDentifier
     *
     * @param displaySetItems List of comma-separated displayset items
     *
     * @return Set of displayset items for an instance of DocumentoG
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public InstanceResponseViewModel getDocumentoGDynamic(DocumentoGOid oid, String displaySetItems) {
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
    public String getSuppInfo(DocumentoGOid oid, String dsName) {
        DocumentoG instance = getByOIDWithHV(oid);
        return (instance != null ? getSuppInfo(instance, dsName): "");
    }

    private String getSuppInfo(DocumentoG instance, String dsName) {
        if (instance == null || instance.isNull()) return "";
        return dsName.isEmpty() ? instance.getOid().toJson() : "";
    }




    // IIU Methods

    /*
     * Returns a viewmodel for class DocumentoG with displayset DSDocumentoGIIU for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class DocumentoG with displayset DSDocumentoGIIU
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.documentog.ds.DSDocumentoGIIU iIUDocumentoGMaestro(DocumentoGOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.documentog.ds.DSDocumentoGIIU(getByOIDWithHV(oid));
    }


    /*
     * Solves the query needed for PIUDocumentoG
     *
     * @param queryRequest Query request
     * @return List of viewmodels for DocumentoG using displayset DSDocumentoGPIU that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.documentog.ds.DSDocumentoGPIU> piuPIUDocumentoG(QueryRequest queryRequest) {
        return solveDocumentoGTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.documentog.ds.DSDocumentoGPIU::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUDocumentoG
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUDocumentoG
     */
    public int getCount4PIUDocumentoG(QueryRequest queryRequest) {
        return solveDocumentoGTypedQuery(queryRequest).size();
    }

    // Service: setFechaSubida

    /*
     * Entry point for service setFechaSubida
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse setFechaSubida(
            com.keralty.aegenerasolicitud.viewmodel.documentog.siu.SetFechaSubidaRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = documentoGSrv.setFechaSubidaTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service setFechaSubida
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse setFechaSubidaTxn(
            com.keralty.aegenerasolicitud.viewmodel.documentog.siu.SetFechaSubidaRequest request) throws ModelException {
        request.setDocumentoGsetFechaSubidapthisDocumentoGInstance(documentoGSrv.getByOIDWithHV(request.getDocumentoGsetFechaSubidapthisDocumentoG()));
        return documentoGSrv.setFechaSubidaImplementation(request);
    }

    /*
     * Implements service setFechaSubida
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse setFechaSubidaImplementation(
            com.keralty.aegenerasolicitud.viewmodel.documentog.siu.SetFechaSubidaRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        DocumentoG documentoGsetFechaSubidapthisDocumentoG = request.getDocumentoGsetFechaSubidapthisDocumentoGInstance();
        Timestamp documentoGsetFechaSubidapeFechaHora = request.getDocumentoGsetFechaSubidapeFechaHora();

        request.checkArguments();
        DocumentoG instance = documentoGsetFechaSubidapthisDocumentoG;
        instance.setDocumentoGFechaSubida(documentoGsetFechaSubidapeFechaHora);


        response.setInstance(repository.save(instance));

        return response;
    }


    @Override
    protected void applyHVFilter(SQLSelect sql) throws SystemException {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        SQLWhereDisjunction disjunction = new SQLWhereDisjunction();
        if (getAgentRoles().contains(AsesorConstants.CLASS_NAME)) applyHVFilter4Asesor(sql, disjunction);
        conjunction.addDisjunction(disjunction);
        sql.addConjunction(conjunction);
    }

    private void applyHVFilter4Asesor(SQLSelect sql, SQLWhereDisjunction disjunction) {
        // Solicitud.Agente = agent OR Solicitud.Agente.CodJefe = Agent.Usuario
        Asesor agentInstance = getAgentAsesor();
    {
        // 'or' Logical operator
        SQLWhereDisjunction comparison1 = new SQLWhereDisjunction();
    
        // Operand 1
            // '=' Comparison
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand113 = new SQLWhereOperandPathInstance(DocumentoGConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AGENTE, FuncionarioConstants.CLASS_NAME);
            comparison2.setFirstOperand(operand113);
            
            // Operand 2
            SQLWhereOperandOVParameter operand224 = new SQLWhereOperandOVParameter(Constants.AGENTKEYWORD, AsesorConstants.CLASS_NAME, AsesorConstants.CLASS_NAME, agentInstance.getOid(), Constants.AGENTKEYWORD);
            comparison2.setSecondOperand(operand224);
            comparison1.addOperand(comparison2);
    
        // Operand 2
            // '=' Comparison
            SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
            comparison4.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand115 = new SQLWhereOperandPathAttribute(DocumentoGConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ATTR_NAME_FUNCIONARIOCODJEFE, Constants.Type.STRING.getTypeName());
            comparison4.setFirstOperand(operand115);
            
            // Operand 2
            comparison4.setSecondOperand(new SQLWhereOperandLiteral(agentInstance.getFuncionarioUsuario(), Constants.Type.STRING.getTypeName()));
            comparison1.addOperand(comparison4);
        
        disjunction.addOperand(comparison1);
    }

    }

    @Override
    protected void applyOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        applyOCOCDocumentoG(queryRequest, select);
        applyDSItemSort(queryRequest, select);
        applyDefaultOrderCriteria(queryRequest, select);
    }

    private void applyDefaultOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        if (queryRequest.getOrderCriteriaName() != null && !queryRequest.getOrderCriteriaName().isBlank())
            return;
        if (queryRequest.getDsItemSort() != null && !queryRequest.getDsItemSort().isBlank())
            return;
        select.getOrderByPart().addItem(Constants.normalizePath(DocumentoGConstants.CLASS_NAME, DocumentoGConstants.ATTR_FIELD_DOCUMENTOGIDENTIFICADOR, false), true);
    }


    private void applyOCOCDocumentoG(QueryRequest queryRequest, SQLSelect select) {
        if ("OC_DocumentoG".equalsIgnoreCase(queryRequest.getOrderCriteriaName())) {
            select.getOrderByPart().addItem(Constants.normalizePath(DocumentoGConstants.CLASS_NAME, DocumentoGConstants.ATTR_NAME_DOCUMENTOGFECHASUBIDA, false), false);
        }
    }
    
    @Override
    protected AbstractAppEntity buildRelatedInstance(String relatedInstanceJsonOid, String relatedInstancePathFirstItem, String remainingPath) {
        AbstractAppEntity relatedInstance = null;
        if (relatedInstancePathFirstItem.isEmpty()) {
            relatedInstance = buildDocumentoGRelatedInstance(relatedInstanceJsonOid);
        }
        if (DocumentoGConstants.ROLE_NAME_SOLICITUD.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildDocumentoGRelatedInstance4Solicitud(relatedInstanceJsonOid, remainingPath);
        }
        if (DocumentoGConstants.ROLE_NAME_PERSONA.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildDocumentoGRelatedInstance4Persona(relatedInstanceJsonOid, remainingPath);
        }
        return relatedInstance;
    }
    
    private AbstractAppEntity buildDocumentoGRelatedInstance(String relatedInstanceJsonOid) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		return new DocumentoG(mapper.readValue(relatedInstanceJsonOid, DocumentoGOid.class));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildDocumentoGRelatedInstance4Solicitud(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Solicitud(mapper.readValue(relatedInstanceJsonOid, SolicitudOid.class)): Solicitud.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildDocumentoGRelatedInstance4Persona(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Persona(mapper.readValue(relatedInstanceJsonOid, PersonaOid.class)): Persona.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            DocumentoGOid oid = new DocumentoGOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(DocumentoGConstants.ATTR_FIELD_DOCUMENTOGIDENTIFICADOR, Constants.Type.AUTONUMERIC.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getDocumentoGIdentificador(), Constants.Type.AUTONUMERIC.getTypeName()));
            sql.addComparison(comparison1);
        } catch (Exception e) {
            LoggerFactory.getLogger(DocumentoGService.class).error(e.getMessage());
        }
    }

}
