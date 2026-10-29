package com.keralty.aegenerasolicitud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralty.aegenerasolicitud.exception.ModelException;
import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.AesolicitudesConstants;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.SolicitudConstants;
import com.keralty.aegenerasolicitud.global.TitularConstants;
import com.keralty.aegenerasolicitud.persistence.AbstractAppEntity;
import com.keralty.aegenerasolicitud.persistence.Departamento;
import com.keralty.aegenerasolicitud.persistence.Direccion;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.oid.DepartamentoOid;
import com.keralty.aegenerasolicitud.persistence.oid.DireccionOid;
import com.keralty.aegenerasolicitud.persistence.oid.MunicipioOid;
import com.keralty.aegenerasolicitud.persistence.oid.SolicitudOid;
import com.keralty.aegenerasolicitud.persistence.oid.TipoIdentificacionOid;
import com.keralty.aegenerasolicitud.persistence.oid.TitularOid;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import com.keralty.aegenerasolicitud.persistence.Titular;
import com.keralty.aegenerasolicitud.repository.TitularJpaRepository;
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
public class TitularService extends AbstractService {

    TitularJpaRepository repository;

    TitularService titularSrv;

    DireccionService direccionSrv;

    SolicitudService solicitudSrv;

    TipoIdentificacionService tipoIdentificacionSrv;

    DepartamentoService departamentoSrv;

    MunicipioService municipioSrv;

    @Inject
    public TitularService(
            TitularService titularSrv,
            DireccionService direccionSrv,
            SolicitudService solicitudSrv,
            TipoIdentificacionService tipoIdentificacionSrv,
            DepartamentoService departamentoSrv,
            MunicipioService municipioSrv,
            TitularJpaRepository repository) {
        this.titularSrv = titularSrv;
        this.direccionSrv = direccionSrv;
        this.solicitudSrv = solicitudSrv;
        this.tipoIdentificacionSrv = tipoIdentificacionSrv;
        this.departamentoSrv = departamentoSrv;
        this.municipioSrv = municipioSrv;
        this.repository = repository;
    }

    public List<Map<String, Object>> solveQuery(QueryRequest request) {
        return solveGenericQuery(request, TitularConstants.CLASS_NAME);
    }

    private List<Titular> solveTitularTypedQuery(QueryRequest request) {
        return repository.solveQuery(processQueryRequest(request, TitularConstants.CLASS_NAME), request.getPageSize(), request.getPageNumber());
    }
    
    private int solveTitularTypedCountQuery(QueryRequest request) {
        return repository.solveCountQuery(processCountQueryRequest(request, TitularConstants.CLASS_NAME));
    }

    /*
     * Returns the population of class Titular
     *
     * @return Population of class Titular
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Titular> get() {
        List<Titular> results = new java.util.ArrayList<>();
        repository.findAll().forEach(results::add);
        return results;
    }
    /*
     * Returns the population of class Titular that matches certain search criteria
     *
     * @param queryRequest Search criteria
     *
     * @return Population of class Titular matching the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Titular> get(QueryRequest queryRequest) {
        return solveTitularTypedQuery(queryRequest);
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
            return new PopulationResponseViewModel(solveTitularTypedQuery(queryRequest).stream()
                    .map(instance -> buildDynamicObject(instance, queryRequest.getDisplaySetItems()))
                    .collect(Collectors.toList()), queryRequest.getPageSize() != 0 ? solveTitularTypedCountQuery(queryRequest) : 0);
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
        return new PopulationResponseViewModel(solveTitularTypedQuery(queryRequest).stream()
                .map(instance -> buildDynamicObject(instance, displaySetItems))
                .collect(Collectors.toList()));
    }

    /*
     * Returns an instance of Titular by ID
     *
     * @param aesolicitudesidsolicitud Identification field aesolicitudesidsolicitud
     *
     * @return Instance of Titular
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Titular getById(Long aesolicitudesidsolicitud) {
        return getByOID(new TitularOid(aesolicitudesidsolicitud));
    }
    
    /*
     * Returns an instance of Titular by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Titular
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Titular getByOID(TitularOid oid) {
        Optional<Titular> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of Titular by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Titular
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public Titular getByOIDWithHV(TitularOid oid) {
        Optional<Titular> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<Titular> getInstanceByOid(TitularOid oid) {
        return oid != null && oid.getAesolicitudesidsolicitud() != null
               ? repository.findById(oid.getAesolicitudesidsolicitud())
               : Optional.empty();
    }

    /*
     * Returns an instance of Titular by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of Titular
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Titular getByJsonOid(String jsonOid) {
        TitularOid oid = new TitularOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }

    /*
     * Returns the list of instances of Titular related to a given instance through 
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Titular
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Titular> getByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        QueryRequest queryRequest = new QueryRequest(new FilterRequest(Constants.FILTER_BY_RELATED,
                Arrays.asList(new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_PATH, relatedInstancePath),
                        new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_OID, relatedInstance.getOid().toJson()))));
        return solveTitularTypedQuery(queryRequest);
    }

    /*
     * Returns the list of instances of Titular related to a given instance through
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Titular
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Titular> getInstancesByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        return getByRelated(relatedInstance, relatedInstancePath);
    }

    /*
     * Returns a set of displayset items for an instance of Titular
     *
     * @param oid Object IDentifier
     *
     * @param displaySetItems List of comma-separated displayset items
     *
     * @return Set of displayset items for an instance of Titular
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public InstanceResponseViewModel getTitularDynamic(TitularOid oid, String displaySetItems) {
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
    public String getSuppInfo(TitularOid oid, String dsName) {
        Titular instance = getByOIDWithHV(oid);
        return (instance != null ? getSuppInfo(instance, dsName): "");
    }

    private String getSuppInfo(Titular instance, String dsName) {
        if (instance == null || instance.isNull()) return "";
        return dsName.isEmpty() ? instance.getOid().toJson() : "";
    }




    // IIU Methods


    /*
     * Solves the query needed for PIUTitularNull
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Titular using displayset DSTitularNull that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.titular.ds.DSTitularNull> piuPIUTitularNull(QueryRequest queryRequest) {
        return solveTitularTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.titular.ds.DSTitularNull::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUTitularNull
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUTitularNull
     */
    public int getCount4PIUTitularNull(QueryRequest queryRequest) {
        return solveTitularTypedQuery(queryRequest).size();
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
            com.keralty.aegenerasolicitud.viewmodel.titular.siu.ActualizarCodigoRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = titularSrv.actualizarCodigoTxn(request);
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
            com.keralty.aegenerasolicitud.viewmodel.titular.siu.ActualizarCodigoRequest request) throws ModelException {
        request.setTitularactualizarCodigopthisTitularInstance(titularSrv.getByOIDWithHV(request.getTitularactualizarCodigopthisTitular()));
        return titularSrv.actualizarCodigoImplementation(request);
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
            com.keralty.aegenerasolicitud.viewmodel.titular.siu.ActualizarCodigoRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Titular titularactualizarCodigopthisTitular = request.getTitularactualizarCodigopthisTitularInstance();
        Long titularactualizarCodigopCodigo = request.getTitularactualizarCodigopCodigo();

        request.checkArguments();
        Titular instance = titularactualizarCodigopthisTitular;
        instance.setTitularCodigo(titularactualizarCodigopCodigo);


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
        select.getOrderByPart().addItem(Constants.normalizePath(TitularConstants.CLASS_NAME, TitularConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_FIELD_AESOLICITUDESIDSOLICITUD, false), true);
    }
    
    @Override
    protected AbstractAppEntity buildRelatedInstance(String relatedInstanceJsonOid, String relatedInstancePathFirstItem, String remainingPath) {
        AbstractAppEntity relatedInstance = null;
        if (relatedInstancePathFirstItem.isEmpty()) {
            relatedInstance = buildTitularRelatedInstance(relatedInstanceJsonOid);
        }
        if (TitularConstants.ROLE_NAME_DIRECCION.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildTitularRelatedInstance4Direccion(relatedInstanceJsonOid, remainingPath);
        }
        if (TitularConstants.ROLE_NAME_SOLICITUD.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildTitularRelatedInstance4Solicitud(relatedInstanceJsonOid, remainingPath);
        }
        if (TitularConstants.ROLE_NAME_TIPOIDENTIFICACION.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildTitularRelatedInstance4TipoIdentificacion(relatedInstanceJsonOid, remainingPath);
        }
        if (TitularConstants.ROLE_NAME_DEPARTAMENTONAC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildTitularRelatedInstance4Departamento(relatedInstanceJsonOid, remainingPath);
        }
        if (TitularConstants.ROLE_NAME_MUNICIPIONAC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildTitularRelatedInstance4Municipio(relatedInstanceJsonOid, remainingPath);
        }
        return relatedInstance;
    }
    
    private AbstractAppEntity buildTitularRelatedInstance(String relatedInstanceJsonOid) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		return new Titular(mapper.readValue(relatedInstanceJsonOid, TitularOid.class));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildTitularRelatedInstance4Direccion(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Direccion(mapper.readValue(relatedInstanceJsonOid, DireccionOid.class)): Direccion.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildTitularRelatedInstance4Solicitud(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Solicitud(mapper.readValue(relatedInstanceJsonOid, SolicitudOid.class)): Solicitud.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildTitularRelatedInstance4TipoIdentificacion(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new TipoIdentificacion(mapper.readValue(relatedInstanceJsonOid, TipoIdentificacionOid.class)): TipoIdentificacion.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildTitularRelatedInstance4Departamento(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Departamento(mapper.readValue(relatedInstanceJsonOid, DepartamentoOid.class)): Departamento.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildTitularRelatedInstance4Municipio(String relatedInstanceJsonOid, String remainingPath) {
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
            TitularOid oid = new TitularOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(TitularConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_FIELD_AESOLICITUDESIDSOLICITUD, Constants.Type.AUTONUMERIC.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getAesolicitudesidsolicitud(), Constants.Type.AUTONUMERIC.getTypeName()));
            sql.addComparison(comparison1);
        } catch (Exception e) {
            LoggerFactory.getLogger(TitularService.class).error(e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

}
