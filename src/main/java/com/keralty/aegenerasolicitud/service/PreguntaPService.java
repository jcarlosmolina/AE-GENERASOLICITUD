package com.keralty.aegenerasolicitud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralty.aegenerasolicitud.exception.ModelException;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.PreguntaPConstants;
import com.keralty.aegenerasolicitud.global.SeccionPConstants;
import com.keralty.aegenerasolicitud.persistence.AbstractAppEntity;
import com.keralty.aegenerasolicitud.persistence.oid.PreguntaOid;
import com.keralty.aegenerasolicitud.persistence.oid.PreguntaPOid;
import com.keralty.aegenerasolicitud.persistence.oid.SeccionPOid;
import com.keralty.aegenerasolicitud.persistence.Pregunta;
import com.keralty.aegenerasolicitud.persistence.PreguntaP;
import com.keralty.aegenerasolicitud.persistence.SeccionP;
import com.keralty.aegenerasolicitud.repository.PreguntaPJpaRepository;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereComparison;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandLiteral;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandPathAttribute;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

@Transactional
@ApplicationScoped
public class PreguntaPService extends AbstractService {

    PreguntaPJpaRepository repository;

    PreguntaPService preguntaPSrv;

    SeccionPService seccionPSrv;

    PreguntaService preguntaSrv;

    @Inject
    public PreguntaPService(
            PreguntaPService preguntaPSrv,
            SeccionPService seccionPSrv,
            PreguntaService preguntaSrv,
            PreguntaPJpaRepository repository) {
        this.preguntaPSrv = preguntaPSrv;
        this.seccionPSrv = seccionPSrv;
        this.preguntaSrv = preguntaSrv;
        this.repository = repository;
    }

    public List<Map<String, Object>> solveQuery(QueryRequest request) {
        return solveGenericQuery(request, PreguntaPConstants.CLASS_NAME);
    }

    private List<PreguntaP> solvePreguntaPTypedQuery(QueryRequest request) {
        return repository.solveQuery(processQueryRequest(request, PreguntaPConstants.CLASS_NAME), request.getPageSize(), request.getPageNumber());
    }
    
    private int solvePreguntaPTypedCountQuery(QueryRequest request) {
        return repository.solveCountQuery(processCountQueryRequest(request, PreguntaPConstants.CLASS_NAME));
    }

    /*
     * Returns the population of class PreguntaP
     *
     * @return Population of class PreguntaP
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PreguntaP> get() {
        List<PreguntaP> results = new java.util.ArrayList<>();
        repository.findAll().forEach(results::add);
        return results;
    }
    /*
     * Returns the population of class PreguntaP that matches certain search criteria
     *
     * @param queryRequest Search criteria
     *
     * @return Population of class PreguntaP matching the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PreguntaP> get(QueryRequest queryRequest) {
        return solvePreguntaPTypedQuery(queryRequest);
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
            return new PopulationResponseViewModel(solvePreguntaPTypedQuery(queryRequest).stream()
                    .map(instance -> buildDynamicObject(instance, queryRequest.getDisplaySetItems()))
                    .collect(Collectors.toList()), queryRequest.getPageSize() != 0 ? solvePreguntaPTypedCountQuery(queryRequest) : 0);
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
        return new PopulationResponseViewModel(solvePreguntaPTypedQuery(queryRequest).stream()
                .map(instance -> buildDynamicObject(instance, displaySetItems))
                .collect(Collectors.toList()));
    }

    /*
     * Returns an instance of PreguntaP by ID
     *
     * @param preguntaPIdentificador Identification field preguntaPIdentificador
     *
     * @return Instance of PreguntaP
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public PreguntaP getById(Long preguntaPIdentificador) {
        return getByOID(new PreguntaPOid(preguntaPIdentificador));
    }
    
    /*
     * Returns an instance of PreguntaP by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of PreguntaP
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public PreguntaP getByOID(PreguntaPOid oid) {
        Optional<PreguntaP> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of PreguntaP by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of PreguntaP
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public PreguntaP getByOIDWithHV(PreguntaPOid oid) {
        Optional<PreguntaP> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<PreguntaP> getInstanceByOid(PreguntaPOid oid) {
        return oid != null && oid.getPreguntaPIdentificador() != null
               ? repository.findById(oid.getPreguntaPIdentificador())
               : Optional.empty();
    }

    /*
     * Returns an instance of PreguntaP by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of PreguntaP
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public PreguntaP getByJsonOid(String jsonOid) {
        PreguntaPOid oid = new PreguntaPOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }

    /*
     * Returns the list of instances of PreguntaP related to a given instance through 
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of PreguntaP
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PreguntaP> getByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        QueryRequest queryRequest = new QueryRequest(new FilterRequest(Constants.FILTER_BY_RELATED,
                Arrays.asList(new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_PATH, relatedInstancePath),
                        new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_OID, relatedInstance.getOid().toJson()))));
        return solvePreguntaPTypedQuery(queryRequest);
    }

    /*
     * Returns the list of instances of PreguntaP related to a given instance through
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of PreguntaP
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PreguntaP> getInstancesByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        return getByRelated(relatedInstance, relatedInstancePath);
    }

    /*
     * Returns a set of displayset items for an instance of PreguntaP
     *
     * @param oid Object IDentifier
     *
     * @param displaySetItems List of comma-separated displayset items
     *
     * @return Set of displayset items for an instance of PreguntaP
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public InstanceResponseViewModel getPreguntaPDynamic(PreguntaPOid oid, String displaySetItems) {
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
    public String getSuppInfo(PreguntaPOid oid, String dsName) {
        PreguntaP instance = getByOIDWithHV(oid);
        return (instance != null ? getSuppInfo(instance, dsName): "");
    }

    private String getSuppInfo(PreguntaP instance, String dsName) {
        if (instance == null || instance.isNull()) return "";
        return dsName.isEmpty() ? instance.getOid().toJson() : "";
    }




    // IIU Methods


    /*
     * Solves the query needed for PIUPreguntaPNull
     *
     * @param queryRequest Query request
     * @return List of viewmodels for PreguntaP using displayset DSPreguntaPNull that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.preguntap.ds.DSPreguntaPNull> piuPIUPreguntaPNull(QueryRequest queryRequest) {
        return solvePreguntaPTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.preguntap.ds.DSPreguntaPNull::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUPreguntaPNull
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUPreguntaPNull
     */
    public int getCount4PIUPreguntaPNull(QueryRequest queryRequest) {
        return solvePreguntaPTypedQuery(queryRequest).size();
    }

    // Service: marcarRespuesta

    /*
     * Entry point for service marcarRespuesta
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse marcarRespuesta(
            com.keralty.aegenerasolicitud.viewmodel.preguntap.siu.MarcarRespuestaRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = preguntaPSrv.marcarRespuestaTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service marcarRespuesta
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse marcarRespuestaTxn(
            com.keralty.aegenerasolicitud.viewmodel.preguntap.siu.MarcarRespuestaRequest request) throws ModelException {
        request.setPreguntaPmarcarRespuestapthisPreguntaPInstance(preguntaPSrv.getByOIDWithHV(request.getPreguntaPmarcarRespuestapthisPreguntaP()));
        return preguntaPSrv.marcarRespuestaImplementation(request);
    }

    /*
     * Implements service marcarRespuesta
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse marcarRespuestaImplementation(
            com.keralty.aegenerasolicitud.viewmodel.preguntap.siu.MarcarRespuestaRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        PreguntaP preguntaPmarcarRespuestapthisPreguntaP = request.getPreguntaPmarcarRespuestapthisPreguntaPInstance();
        String preguntaPmarcarRespuestapRespuestaObs = request.getPreguntaPmarcarRespuestapRespuestaObs();
        String preguntaPmarcarRespuestapeValoresRespuestas = request.getPreguntaPmarcarRespuestapeValoresRespuestas();
        Boolean preguntaPmarcarRespuestapRespuestaSiNo = request.getPreguntaPmarcarRespuestapRespuestaSiNo();

        request.checkArguments();
        PreguntaP instance = preguntaPmarcarRespuestapthisPreguntaP;
        if ((preguntaPmarcarRespuestapRespuestaObs != null && (preguntaPmarcarRespuestapRespuestaObs).length() != 0) || (preguntaPmarcarRespuestapeValoresRespuestas != null && (preguntaPmarcarRespuestapeValoresRespuestas).length() != 0)) {
            instance.setPreguntaPRespuestaSiNo(Boolean.TRUE);
        } else {
            instance.setPreguntaPRespuestaSiNo(preguntaPmarcarRespuestapRespuestaSiNo);
        }
        instance.setPreguntaPRespuestaObs(preguntaPmarcarRespuestapRespuestaObs);
        instance.setPreguntaPValoresRespuestas(preguntaPmarcarRespuestapeValoresRespuestas);


        response.setInstance(repository.save(instance));

        return response;
    }

    @Override
    protected void applyOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        applyOCOCPreguntaP(queryRequest, select);
        applyDSItemSort(queryRequest, select);
        applyDefaultOrderCriteria(queryRequest, select);
    }

    private void applyDefaultOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        if (queryRequest.getOrderCriteriaName() != null && !queryRequest.getOrderCriteriaName().isBlank())
            return;
        if (queryRequest.getDsItemSort() != null && !queryRequest.getDsItemSort().isBlank())
            return;
        select.getOrderByPart().addItem(Constants.normalizePath(PreguntaPConstants.CLASS_NAME, PreguntaPConstants.ATTR_FIELD_PREGUNTAPIDENTIFICADOR, false), true);
    }


    private void applyOCOCPreguntaP(QueryRequest queryRequest, SQLSelect select) {
        if ("OC_PreguntaP".equalsIgnoreCase(queryRequest.getOrderCriteriaName())) {
            select.getOrderByPart().addItem(Constants.normalizePath(PreguntaPConstants.CLASS_NAME, PreguntaPConstants.ROLE_NAME_SECCION + "." + SeccionPConstants.ATTR_NAME_SECCIONPNUMORDEN, false), true);
            select.getOrderByPart().addItem(Constants.normalizePath(PreguntaPConstants.CLASS_NAME, PreguntaPConstants.ATTR_NAME_PREGUNTAPNUMORDEN, false), true);
        }
    }
    
    @Override
    protected AbstractAppEntity buildRelatedInstance(String relatedInstanceJsonOid, String relatedInstancePathFirstItem, String remainingPath) {
        AbstractAppEntity relatedInstance = null;
        if (relatedInstancePathFirstItem.isEmpty()) {
            relatedInstance = buildPreguntaPRelatedInstance(relatedInstanceJsonOid);
        }
        if (PreguntaPConstants.ROLE_NAME_SECCION.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildPreguntaPRelatedInstance4SeccionP(relatedInstanceJsonOid, remainingPath);
        }
        if (PreguntaPConstants.ROLE_NAME_PREGUNTA.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildPreguntaPRelatedInstance4Pregunta(relatedInstanceJsonOid, remainingPath);
        }
        return relatedInstance;
    }
    
    private AbstractAppEntity buildPreguntaPRelatedInstance(String relatedInstanceJsonOid) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		return new PreguntaP(mapper.readValue(relatedInstanceJsonOid, PreguntaPOid.class));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildPreguntaPRelatedInstance4SeccionP(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new SeccionP(mapper.readValue(relatedInstanceJsonOid, SeccionPOid.class)): SeccionP.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildPreguntaPRelatedInstance4Pregunta(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Pregunta(mapper.readValue(relatedInstanceJsonOid, PreguntaOid.class)): Pregunta.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            PreguntaPOid oid = new PreguntaPOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(PreguntaPConstants.ATTR_FIELD_PREGUNTAPIDENTIFICADOR, Constants.Type.AUTONUMERIC.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getPreguntaPIdentificador(), Constants.Type.AUTONUMERIC.getTypeName()));
            sql.addComparison(comparison1);
        } catch (Exception e) {
            LoggerFactory.getLogger(PreguntaPService.class).error(e.getMessage());
        }
    }

}
