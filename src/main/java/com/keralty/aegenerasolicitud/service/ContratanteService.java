package com.keralty.aegenerasolicitud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralty.aegenerasolicitud.exception.ModelException;
import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.AesolicitudesConstants;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.ContratanteConstants;
import com.keralty.aegenerasolicitud.global.SolicitudConstants;
import com.keralty.aegenerasolicitud.persistence.AbstractAppEntity;
import com.keralty.aegenerasolicitud.persistence.Contratante;
import com.keralty.aegenerasolicitud.persistence.Departamento;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.oid.ContratanteOid;
import com.keralty.aegenerasolicitud.persistence.oid.DepartamentoOid;
import com.keralty.aegenerasolicitud.persistence.oid.MunicipioOid;
import com.keralty.aegenerasolicitud.persistence.oid.SolicitudOid;
import com.keralty.aegenerasolicitud.persistence.oid.TipoIdentificacionOid;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import com.keralty.aegenerasolicitud.repository.ContratanteJpaRepository;
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
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

@Transactional
@ApplicationScoped
public class ContratanteService extends AbstractService {

    ContratanteJpaRepository repository;

    ContratanteService contratanteSrv;

    SolicitudService solicitudSrv;

    TipoIdentificacionService tipoIdentificacionSrv;

    DepartamentoService departamentoSrv;

    MunicipioService municipioSrv;

    @Inject
    public ContratanteService(
            ContratanteService contratanteSrv,
            SolicitudService solicitudSrv,
            TipoIdentificacionService tipoIdentificacionSrv,
            DepartamentoService departamentoSrv,
            MunicipioService municipioSrv,
            ContratanteJpaRepository repository) {
        this.contratanteSrv = contratanteSrv;
        this.solicitudSrv = solicitudSrv;
        this.tipoIdentificacionSrv = tipoIdentificacionSrv;
        this.departamentoSrv = departamentoSrv;
        this.municipioSrv = municipioSrv;
        this.repository = repository;
    }

    public List<Map<String, Object>> solveQuery(QueryRequest request) {
        return solveGenericQuery(request, ContratanteConstants.CLASS_NAME);
    }

    private List<Contratante> solveContratanteTypedQuery(QueryRequest request) {
        return repository.solveQuery(processQueryRequest(request, ContratanteConstants.CLASS_NAME), request.getPageSize(), request.getPageNumber());
    }
    
    private int solveContratanteTypedCountQuery(QueryRequest request) {
        return repository.solveCountQuery(processCountQueryRequest(request, ContratanteConstants.CLASS_NAME));
    }

    /*
     * Returns the population of class Contratante
     *
     * @return Population of class Contratante
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Contratante> get() {
        List<Contratante> results = new java.util.ArrayList<>();
        repository.findAll().forEach(results::add);
        return results;
    }
    /*
     * Returns the population of class Contratante that matches certain search criteria
     *
     * @param queryRequest Search criteria
     *
     * @return Population of class Contratante matching the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Contratante> get(QueryRequest queryRequest) {
        return solveContratanteTypedQuery(queryRequest);
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
            return new PopulationResponseViewModel(solveContratanteTypedQuery(queryRequest).stream()
                    .map(instance -> buildDynamicObject(instance, queryRequest.getDisplaySetItems()))
                    .collect(Collectors.toList()), queryRequest.getPageSize() != 0 ? solveContratanteTypedCountQuery(queryRequest) : 0);
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
        return new PopulationResponseViewModel(solveContratanteTypedQuery(queryRequest).stream()
                .map(instance -> buildDynamicObject(instance, displaySetItems))
                .collect(Collectors.toList()));
    }

    /*
     * Returns an instance of Contratante by ID
     *
     * @param aesolicitudesidsolicitud Identification field aesolicitudesidsolicitud
     *
     * @return Instance of Contratante
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Contratante getById(Long aesolicitudesidsolicitud) {
        return getByOID(new ContratanteOid(aesolicitudesidsolicitud));
    }
    
    /*
     * Returns an instance of Contratante by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Contratante
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Contratante getByOID(ContratanteOid oid) {
        Optional<Contratante> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of Contratante by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Contratante
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public Contratante getByOIDWithHV(ContratanteOid oid) {
        Optional<Contratante> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<Contratante> getInstanceByOid(ContratanteOid oid) {
        return oid != null && oid.getAesolicitudesidsolicitud() != null
               ? repository.findById(oid.getAesolicitudesidsolicitud())
               : Optional.empty();
    }

    /*
     * Returns an instance of Contratante by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of Contratante
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Contratante getByJsonOid(String jsonOid) {
        ContratanteOid oid = new ContratanteOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }

    /*
     * Returns the list of instances of Contratante related to a given instance through 
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Contratante
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Contratante> getByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        QueryRequest queryRequest = new QueryRequest(new FilterRequest(Constants.FILTER_BY_RELATED,
                Arrays.asList(new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_PATH, relatedInstancePath),
                        new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_OID, relatedInstance.getOid().toJson()))));
        return solveContratanteTypedQuery(queryRequest);
    }

    /*
     * Returns the list of instances of Contratante related to a given instance through
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Contratante
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Contratante> getInstancesByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        return getByRelated(relatedInstance, relatedInstancePath);
    }

    /*
     * Returns a set of displayset items for an instance of Contratante
     *
     * @param oid Object IDentifier
     *
     * @param displaySetItems List of comma-separated displayset items
     *
     * @return Set of displayset items for an instance of Contratante
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public InstanceResponseViewModel getContratanteDynamic(ContratanteOid oid, String displaySetItems) {
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
    public String getSuppInfo(ContratanteOid oid, String dsName) {
        Contratante instance = getByOIDWithHV(oid);
        return (instance != null ? getSuppInfo(instance, dsName): "");
    }

    private String getSuppInfo(Contratante instance, String dsName) {
        if (instance == null || instance.isNull()) return "";
        return dsName.isEmpty() ? instance.getOid().toJson() : "";
    }




    // IIU Methods


    /*
     * Solves the query needed for PIUContratanteNull
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Contratante using displayset DSContratanteNull that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.contratante.ds.DSContratanteNull> piuPIUContratanteNull(QueryRequest queryRequest) {
        return solveContratanteTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.contratante.ds.DSContratanteNull::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUContratanteNull
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUContratanteNull
     */
    public int getCount4PIUContratanteNull(QueryRequest queryRequest) {
        return solveContratanteTypedQuery(queryRequest).size();
    }

    // Service: actualizarCodigo

    /*
     * Entry point for service actualizarCodigo
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse actualizarCodigo(
            com.keralty.aegenerasolicitud.viewmodel.contratante.siu.ActualizarCodigoRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratanteSrv.actualizarCodigoTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service actualizarCodigo
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse actualizarCodigoTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratante.siu.ActualizarCodigoRequest request) throws ModelException {
        request.setContratanteactualizarCodigopthisContratanteInstance(contratanteSrv.getByOIDWithHV(request.getContratanteactualizarCodigopthisContratante()));
        return contratanteSrv.actualizarCodigoImplementation(request);
    }

    /*
     * Implements service actualizarCodigo
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse actualizarCodigoImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratante.siu.ActualizarCodigoRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Contratante contratanteactualizarCodigopthisContratante = request.getContratanteactualizarCodigopthisContratanteInstance();
        Long contratanteactualizarCodigopCodigo = request.getContratanteactualizarCodigopCodigo();

        request.checkArguments();
        Contratante instance = contratanteactualizarCodigopthisContratante;
        instance.setContratanteCodigo(contratanteactualizarCodigopCodigo);


        response.setInstance(repository.save(instance));

        return response;
    }

    @Override
    protected void applyOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        applyDSItemSort(queryRequest, select);
        applyDefaultOrderCriteria(queryRequest, select);
    }

    private void applyDefaultOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        if (queryRequest.getOrderCriteriaName() != null && !queryRequest.getOrderCriteriaName().isBlank())
            return;
        if (queryRequest.getDsItemSort() != null && !queryRequest.getDsItemSort().isBlank())
            return;
        select.getOrderByPart().addItem(Constants.normalizePath(ContratanteConstants.CLASS_NAME, ContratanteConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_FIELD_AESOLICITUDESIDSOLICITUD, false), true);
    }
    
    @Override
    protected AbstractAppEntity buildRelatedInstance(String relatedInstanceJsonOid, String relatedInstancePathFirstItem, String remainingPath) {
        AbstractAppEntity relatedInstance = null;
        if (relatedInstancePathFirstItem.isEmpty()) {
            relatedInstance = buildContratanteRelatedInstance(relatedInstanceJsonOid);
        }
        if (ContratanteConstants.ROLE_NAME_SOLICITUD.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratanteRelatedInstance4Solicitud(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratanteConstants.ROLE_NAME_TIPOIDENTIFICACION.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratanteRelatedInstance4TipoIdentificacion(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratanteConstants.ROLE_NAME_DEPARTAMENTONAC.equalsIgnoreCase(relatedInstancePathFirstItem) || ContratanteConstants.ROLE_NAME_DEPARTAMENTORES.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratanteRelatedInstance4Departamento(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratanteConstants.ROLE_NAME_MUNICIPIONAC.equalsIgnoreCase(relatedInstancePathFirstItem) || ContratanteConstants.ROLE_NAME_MUNICIPIORES.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratanteRelatedInstance4Municipio(relatedInstanceJsonOid, remainingPath);
        }
        return relatedInstance;
    }
    
    private AbstractAppEntity buildContratanteRelatedInstance(String relatedInstanceJsonOid) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		return new Contratante(mapper.readValue(relatedInstanceJsonOid, ContratanteOid.class));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildContratanteRelatedInstance4Solicitud(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Solicitud(mapper.readValue(relatedInstanceJsonOid, SolicitudOid.class)): Solicitud.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildContratanteRelatedInstance4TipoIdentificacion(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new TipoIdentificacion(mapper.readValue(relatedInstanceJsonOid, TipoIdentificacionOid.class)): TipoIdentificacion.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildContratanteRelatedInstance4Departamento(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Departamento(mapper.readValue(relatedInstanceJsonOid, DepartamentoOid.class)): Departamento.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildContratanteRelatedInstance4Municipio(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Municipio(mapper.readValue(relatedInstanceJsonOid, MunicipioOid.class)): Municipio.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            ContratanteOid oid = new ContratanteOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(ContratanteConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_FIELD_AESOLICITUDESIDSOLICITUD, Constants.Type.AUTONUMERIC.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getAesolicitudesidsolicitud(), Constants.Type.AUTONUMERIC.getTypeName()));
            sql.addComparison(comparison1);
        } catch (Exception e) {
            LoggerFactory.getLogger(ContratanteService.class).error(e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

}
