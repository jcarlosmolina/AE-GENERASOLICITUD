package com.keralty.aegenerasolicitud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralty.aegenerasolicitud.exception.ModelException;
import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.DepartamentoConstants;
import com.keralty.aegenerasolicitud.persistence.AbstractAppEntity;
import com.keralty.aegenerasolicitud.persistence.Contratante;
import com.keralty.aegenerasolicitud.persistence.Departamento;
import com.keralty.aegenerasolicitud.persistence.Direccion;
import com.keralty.aegenerasolicitud.persistence.DireccionC;
import com.keralty.aegenerasolicitud.persistence.DirSucursalC;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.oid.ContratanteOid;
import com.keralty.aegenerasolicitud.persistence.oid.DepartamentoOid;
import com.keralty.aegenerasolicitud.persistence.oid.DireccionCOid;
import com.keralty.aegenerasolicitud.persistence.oid.DireccionOid;
import com.keralty.aegenerasolicitud.persistence.oid.DirSucursalCOid;
import com.keralty.aegenerasolicitud.persistence.oid.MunicipioOid;
import com.keralty.aegenerasolicitud.persistence.oid.PersonaCOid;
import com.keralty.aegenerasolicitud.persistence.oid.PersonaOid;
import com.keralty.aegenerasolicitud.persistence.oid.TitularOid;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.Titular;
import com.keralty.aegenerasolicitud.repository.DepartamentoJpaRepository;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereComparison;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereConjunction;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereDisjunction;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandLiteral;
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
public class DepartamentoService extends AbstractService {

    DepartamentoJpaRepository repository;

    DepartamentoService departamentoSrv;

    DireccionCService direccionCSrv;

    PersonaService personaSrv;

    MunicipioService municipioSrv;

    PersonaCService personaCSrv;

    DireccionService direccionSrv;

    ContratanteService contratanteSrv;

    DirSucursalCService dirSucursalCSrv;

    TitularService titularSrv;

    @Inject
    public DepartamentoService(
            DepartamentoService departamentoSrv,
            DireccionCService direccionCSrv,
            PersonaService personaSrv,
            MunicipioService municipioSrv,
            PersonaCService personaCSrv,
            DireccionService direccionSrv,
            ContratanteService contratanteSrv,
            DirSucursalCService dirSucursalCSrv,
            TitularService titularSrv,
            DepartamentoJpaRepository repository) {
        this.departamentoSrv = departamentoSrv;
        this.direccionCSrv = direccionCSrv;
        this.personaSrv = personaSrv;
        this.municipioSrv = municipioSrv;
        this.personaCSrv = personaCSrv;
        this.direccionSrv = direccionSrv;
        this.contratanteSrv = contratanteSrv;
        this.dirSucursalCSrv = dirSucursalCSrv;
        this.titularSrv = titularSrv;
        this.repository = repository;
    }

    public List<Map<String, Object>> solveQuery(QueryRequest request) {
        return solveGenericQuery(request, DepartamentoConstants.CLASS_NAME);
    }

    private List<Departamento> solveDepartamentoTypedQuery(QueryRequest request) {
        return repository.solveQuery(processQueryRequest(request, DepartamentoConstants.CLASS_NAME), request.getPageSize(), request.getPageNumber());
    }
    
    private int solveDepartamentoTypedCountQuery(QueryRequest request) {
        return repository.solveCountQuery(processCountQueryRequest(request, DepartamentoConstants.CLASS_NAME));
    }

    /*
     * Returns the population of class Departamento
     *
     * @return Population of class Departamento
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Departamento> get() {
        List<Departamento> results = new java.util.ArrayList<>();
        repository.findAll().forEach(results::add);
        return results;
    }
    
    /*
     * Gets the preloaded list of objects of class Departamento
     *
     * @param queryRequest Request object that dictates the search conditions to 
     * retrieve objects
     *
     * @return Preloaded list of objects of class Departamento
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PreloadViewModel> getPreload(QueryRequest queryRequest) {
        List<PreloadViewModel> results = new ArrayList<>();
        List<Departamento> instances = get(queryRequest);
        if(DepartamentoConstants.DSICDEPARTAMENTO.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildICDepartamento())).collect(Collectors.toList());
        }
        return results;
    }
    /*
     * Returns the population of class Departamento that matches certain search criteria
     *
     * @param queryRequest Search criteria
     *
     * @return Population of class Departamento matching the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Departamento> get(QueryRequest queryRequest) {
        return solveDepartamentoTypedQuery(queryRequest);
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
            return new PopulationResponseViewModel(solveDepartamentoTypedQuery(queryRequest).stream()
                    .map(instance -> buildDynamicObject(instance, queryRequest.getDisplaySetItems()))
                    .collect(Collectors.toList()), queryRequest.getPageSize() != 0 ? solveDepartamentoTypedCountQuery(queryRequest) : 0);
        } else {
            return new PreloadResponseViewModel(solveDepartamentoTypedQuery(queryRequest).stream()
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
        return new PopulationResponseViewModel(solveDepartamentoTypedQuery(queryRequest).stream()
                .map(instance -> buildDynamicObject(instance, displaySetItems))
                .collect(Collectors.toList()));
    }

    /*
     * Returns an instance of Departamento by ID
     *
     * @param departamentoCodigo Identification field departamentoCodigo
     *
     * @return Instance of Departamento
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Departamento getById(Long departamentoCodigo) {
        return getByOID(new DepartamentoOid(departamentoCodigo));
    }
    
    /*
     * Returns an instance of Departamento by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Departamento
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Departamento getByOID(DepartamentoOid oid) {
        Optional<Departamento> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of Departamento by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Departamento
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public Departamento getByOIDWithHV(DepartamentoOid oid) {
        Optional<Departamento> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<Departamento> getInstanceByOid(DepartamentoOid oid) {
        return oid != null && oid.getDepartamentoCodigo() != null
               ? repository.findById(oid.getDepartamentoCodigo())
               : Optional.empty();
    }

    /*
     * Returns an instance of Departamento by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of Departamento
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Departamento getByJsonOid(String jsonOid) {
        DepartamentoOid oid = new DepartamentoOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }

    /*
     * Returns the list of instances of Departamento related to a given instance through 
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Departamento
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Departamento> getByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        QueryRequest queryRequest = new QueryRequest(new FilterRequest(Constants.FILTER_BY_RELATED,
                Arrays.asList(new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_PATH, relatedInstancePath),
                        new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_OID, relatedInstance.getOid().toJson()))));
        return solveDepartamentoTypedQuery(queryRequest);
    }

    /*
     * Returns the list of instances of Departamento related to a given instance through
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Departamento
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Departamento> getInstancesByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        return getByRelated(relatedInstance, relatedInstancePath);
    }

    /*
     * Returns a set of displayset items for an instance of Departamento
     *
     * @param oid Object IDentifier
     *
     * @param displaySetItems List of comma-separated displayset items
     *
     * @return Set of displayset items for an instance of Departamento
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public InstanceResponseViewModel getDepartamentoDynamic(DepartamentoOid oid, String displaySetItems) {
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
    public String getSuppInfo(DepartamentoOid oid, String dsName) {
        Departamento instance = getByOIDWithHV(oid);
        return (instance != null ? getSuppInfo(instance, dsName): "");
    }

    private String getSuppInfo(Departamento instance, String dsName) {
        if (instance == null || instance.isNull()) return "";
        if (DepartamentoConstants.DSICDEPARTAMENTO.equals(dsName))
            return instance.buildICDepartamento();
        return dsName.isEmpty() ? instance.getOid().toJson() : "";
    }

    public static PreloadViewModel buildPreloadViewModel(Departamento instance, String dsName) {
        if (DepartamentoConstants.DSICDEPARTAMENTO.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildICDepartamento());
        return new PreloadViewModel(instance.getOid().toJson(), "");
    }


    @Override
    protected void applyQueryFilters(FilterRequest filterRequest, SQLSelect sql) throws SystemException {
        if (DepartamentoConstants.FILTER_NAME_IFDEPARTAMENTOCODIGO.equalsIgnoreCase(filterRequest.getName()))
            iFDepartamentoCodigo(filterRequest, sql);
        if (DepartamentoConstants.FILTER_NAME_FDEPARTAMENTOCODIGOLEGAL.equalsIgnoreCase(filterRequest.getName()))
            fDepartamentoCodigoLegal(filterRequest, sql);
    }

    @Override
    protected void applyServiceSIFilters(FilterRequest filterRequest, SQLSelect sql) {
        if ("SuppInfo_IC_Departamento".equals(filterRequest.getName()))
            suppInfo4ICDepartamento(filterRequest.getFilterVariable("searchText").toString(), sql);
    }


    private void iFDepartamentoCodigo(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Long vCodigo = filterRequest.hasFilterVariable(DepartamentoConstants.VAR_NAME_IFDEPARTAMENTOCODIGO_VCODIGO) ? (Long) filterRequest.getFilterVariable(DepartamentoConstants.VAR_NAME_IFDEPARTAMENTOCODIGO_VCODIGO, FilterVariableRequest.INT) : null;

        // Codigo = vCodigo
        if (vCodigo != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(DepartamentoConstants.ATTR_NAME_DEPARTAMENTOCODIGO, Constants.Type.INT.getTypeName());
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(vCodigo, Constants.Type.INT.getTypeName()));
            conjunction.addOperand(comparison1);

        }        

        sql.addConjunction(conjunction);
    }

    private void fDepartamentoCodigoLegal(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vfCodLegal = filterRequest.hasFilterVariable(DepartamentoConstants.VAR_NAME_FDEPARTAMENTOCODIGOLEGAL_VFCODLEGAL) ? (String) filterRequest.getFilterVariable(DepartamentoConstants.VAR_NAME_FDEPARTAMENTOCODIGOLEGAL_VFCODLEGAL, FilterVariableRequest.STRING) : null;

        // CodigoLegal = vfCodLegal
        if (vfCodLegal != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(DepartamentoConstants.ATTR_NAME_DEPARTAMENTOCODIGOLEGAL, Constants.Type.STRING.getTypeName());
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(vfCodLegal, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison1);

        }        

        sql.addConjunction(conjunction);
    }

    // IIU Methods


    /*
     * Solves the query needed for PIUDepartamento
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Departamento using displayset AutoDS that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.departamento.ds.AutoDS> piuPIUDepartamento(QueryRequest queryRequest) {
        return solveDepartamentoTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.departamento.ds.AutoDS::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUDepartamento
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUDepartamento
     */
    public int getCount4PIUDepartamento(QueryRequest queryRequest) {
        return solveDepartamentoTypedQuery(queryRequest).size();
    }

    // Service: InsDepartamentoC

    /*
     * Entry point for service InsDepartamentoC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse insDepartamentoC(
            com.keralty.aegenerasolicitud.viewmodel.departamento.siu.InsDepartamentoCRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = departamentoSrv.insDepartamentoCTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service InsDepartamentoC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse insDepartamentoCTxn(
            com.keralty.aegenerasolicitud.viewmodel.departamento.siu.InsDepartamentoCRequest request) throws ModelException {
        request.setDepartamentoInsDepartamentoCpthisDepartamentoInstance(departamentoSrv.getByOIDWithHV(request.getDepartamentoInsDepartamentoCpthisDepartamento()));
        request.setDepartamentoInsDepartamentoCpevcDireccionCInstance(direccionCSrv.getByOIDWithHV(request.getDepartamentoInsDepartamentoCpevcDireccionC()));
        return departamentoSrv.insDepartamentoCImplementation(request, true);
    }

    /*
     * Implements service InsDepartamentoC
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse insDepartamentoCImplementation(
            com.keralty.aegenerasolicitud.viewmodel.departamento.siu.InsDepartamentoCRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Departamento departamentoInsDepartamentoCpthisDepartamento = request.getDepartamentoInsDepartamentoCpthisDepartamentoInstance();
        DireccionC departamentoInsDepartamentoCpevcDireccionC = request.getDepartamentoInsDepartamentoCpevcDireccionCInstance();

        request.checkArguments();
        Departamento instance = departamentoInsDepartamentoCpthisDepartamento;

        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.direccionc.siu.InsDepartamentoCRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.direccionc.siu.InsDepartamentoCRequest();
            relatedRequest.setDireccionCInsDepartamentoCpthisDireccionCInstance(departamentoInsDepartamentoCpevcDireccionC);
            relatedRequest.setDireccionCInsDepartamentoCpevcDepartamentoInstance(departamentoInsDepartamentoCpthisDepartamento);
            direccionCSrv.insDepartamentoCImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICDepartamento());

        return response;
    }

    // Service: DelDepartamentoC

    /*
     * Entry point for service DelDepartamentoC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse delDepartamentoC(
            com.keralty.aegenerasolicitud.viewmodel.departamento.siu.DelDepartamentoCRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = departamentoSrv.delDepartamentoCTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service DelDepartamentoC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse delDepartamentoCTxn(
            com.keralty.aegenerasolicitud.viewmodel.departamento.siu.DelDepartamentoCRequest request) throws ModelException {
        request.setDepartamentoDelDepartamentoCpthisDepartamentoInstance(departamentoSrv.getByOIDWithHV(request.getDepartamentoDelDepartamentoCpthisDepartamento()));
        request.setDepartamentoDelDepartamentoCpevcDireccionCInstance(direccionCSrv.getByOIDWithHV(request.getDepartamentoDelDepartamentoCpevcDireccionC()));
        return departamentoSrv.delDepartamentoCImplementation(request, true);
    }

    /*
     * Implements service DelDepartamentoC
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse delDepartamentoCImplementation(
            com.keralty.aegenerasolicitud.viewmodel.departamento.siu.DelDepartamentoCRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Departamento departamentoDelDepartamentoCpthisDepartamento = request.getDepartamentoDelDepartamentoCpthisDepartamentoInstance();
        DireccionC departamentoDelDepartamentoCpevcDireccionC = request.getDepartamentoDelDepartamentoCpevcDireccionCInstance();

        request.checkArguments();
        Departamento instance = departamentoDelDepartamentoCpthisDepartamento;

        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.direccionc.siu.DelDepartamentoCRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.direccionc.siu.DelDepartamentoCRequest();
            relatedRequest.setDireccionCDelDepartamentoCpthisDireccionCInstance(departamentoDelDepartamentoCpevcDireccionC);
            relatedRequest.setDireccionCDelDepartamentoCpevcDepartamentoInstance(departamentoDelDepartamentoCpthisDepartamento);
            direccionCSrv.delDepartamentoCImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICDepartamento());

        return response;
    }


    private void suppInfo4ICDepartamento(String searchText, SQLSelect select) {
        try {
            SQLWhereDisjunction disjunction = new SQLWhereDisjunction();

            SQLWhereComparison comparison1 = new SQLWhereComparison(select);
            comparison1.setOperator("LIKE");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(DepartamentoConstants.ATTR_NAME_DEPARTAMENTODESCRIPCION, Constants.Type.STRING.getTypeName()));
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
        applyOCOCDepartamento(queryRequest, select);
        applyDSItemSort(queryRequest, select);
        applyDefaultOrderCriteria(queryRequest, select);
    }

    private void applyDefaultOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        if (queryRequest.getOrderCriteriaName() != null && !queryRequest.getOrderCriteriaName().isBlank())
            return;
        if (queryRequest.getDsItemSort() != null && !queryRequest.getDsItemSort().isBlank())
            return;
        select.getOrderByPart().addItem(Constants.normalizePath(DepartamentoConstants.CLASS_NAME, DepartamentoConstants.ATTR_FIELD_DEPARTAMENTOCODIGO, false), true);
    }


    private void applyOCOCDepartamento(QueryRequest queryRequest, SQLSelect select) {
        if ("OC_Departamento".equalsIgnoreCase(queryRequest.getOrderCriteriaName())) {
            select.getOrderByPart().addItem(Constants.normalizePath(DepartamentoConstants.CLASS_NAME, DepartamentoConstants.ATTR_NAME_DEPARTAMENTODESCRIPCION, false), true);
        }
    }
    
    @Override
    protected AbstractAppEntity buildRelatedInstance(String relatedInstanceJsonOid, String relatedInstancePathFirstItem, String remainingPath) {
        AbstractAppEntity relatedInstance = null;
        if (relatedInstancePathFirstItem.isEmpty()) {
            relatedInstance = buildDepartamentoRelatedInstance(relatedInstanceJsonOid);
        }
        if (DepartamentoConstants.ROLE_NAME_PERSONAS.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildDepartamentoRelatedInstance4Persona(relatedInstanceJsonOid, remainingPath);
        }
        if (DepartamentoConstants.ROLE_NAME_MUNICIPIOS.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildDepartamentoRelatedInstance4Municipio(relatedInstanceJsonOid, remainingPath);
        }
        if (DepartamentoConstants.ROLE_NAME_PERSONASC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildDepartamentoRelatedInstance4PersonaC(relatedInstanceJsonOid, remainingPath);
        }
        if (DepartamentoConstants.ROLE_NAME_DIRECCIONESC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildDepartamentoRelatedInstance4DireccionC(relatedInstanceJsonOid, remainingPath);
        }
        if (DepartamentoConstants.ROLE_NAME_DIRECCIONES.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildDepartamentoRelatedInstance4Direccion(relatedInstanceJsonOid, remainingPath);
        }
        if (DepartamentoConstants.ROLE_NAME_CONTRATANTES.equalsIgnoreCase(relatedInstancePathFirstItem) || DepartamentoConstants.ROLE_NAME_CONTRATANTESRES.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildDepartamentoRelatedInstance4Contratante(relatedInstanceJsonOid, remainingPath);
        }
        if (DepartamentoConstants.ROLE_NAME_DIRSUCURSALC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildDepartamentoRelatedInstance4DirSucursalC(relatedInstanceJsonOid, remainingPath);
        }
        if (DepartamentoConstants.ROLE_NAME_TITULARES.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildDepartamentoRelatedInstance4Titular(relatedInstanceJsonOid, remainingPath);
        }
        return relatedInstance;
    }
    
    private AbstractAppEntity buildDepartamentoRelatedInstance(String relatedInstanceJsonOid) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		return new Departamento(mapper.readValue(relatedInstanceJsonOid, DepartamentoOid.class));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildDepartamentoRelatedInstance4Persona(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Persona(mapper.readValue(relatedInstanceJsonOid, PersonaOid.class)): Persona.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildDepartamentoRelatedInstance4Municipio(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Municipio(mapper.readValue(relatedInstanceJsonOid, MunicipioOid.class)): Municipio.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildDepartamentoRelatedInstance4PersonaC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new PersonaC(mapper.readValue(relatedInstanceJsonOid, PersonaCOid.class)): PersonaC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildDepartamentoRelatedInstance4DireccionC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new DireccionC(mapper.readValue(relatedInstanceJsonOid, DireccionCOid.class)): DireccionC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildDepartamentoRelatedInstance4Direccion(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Direccion(mapper.readValue(relatedInstanceJsonOid, DireccionOid.class)): Direccion.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildDepartamentoRelatedInstance4Contratante(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Contratante(mapper.readValue(relatedInstanceJsonOid, ContratanteOid.class)): Contratante.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildDepartamentoRelatedInstance4DirSucursalC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new DirSucursalC(mapper.readValue(relatedInstanceJsonOid, DirSucursalCOid.class)): DirSucursalC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildDepartamentoRelatedInstance4Titular(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Titular(mapper.readValue(relatedInstanceJsonOid, TitularOid.class)): Titular.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            DepartamentoOid oid = new DepartamentoOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(DepartamentoConstants.ATTR_FIELD_DEPARTAMENTOCODIGO, Constants.Type.INT.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getDepartamentoCodigo(), Constants.Type.INT.getTypeName()));
            sql.addComparison(comparison1);
        } catch (Exception e) {
            LoggerFactory.getLogger(DepartamentoService.class).error(e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

}
