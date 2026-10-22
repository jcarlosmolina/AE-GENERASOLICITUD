package com.keralty.aegenerasolicitud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralty.aegenerasolicitud.exception.ModelException;
import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.ContratoCConstants;
import com.keralty.aegenerasolicitud.global.STDFunctions;
import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.persistence.AbstractAppEntity;
import com.keralty.aegenerasolicitud.persistence.Asesor;
import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.DireccionC;
import com.keralty.aegenerasolicitud.persistence.EstadoContrato;
import com.keralty.aegenerasolicitud.persistence.FamiliaC;
import com.keralty.aegenerasolicitud.persistence.FormaPago;
import com.keralty.aegenerasolicitud.persistence.GrupoAsociado;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.ObservacionesC;
import com.keralty.aegenerasolicitud.persistence.oid.AsesorOid;
import com.keralty.aegenerasolicitud.persistence.oid.BeneficiarioCOid;
import com.keralty.aegenerasolicitud.persistence.oid.ContratoCOid;
import com.keralty.aegenerasolicitud.persistence.oid.DireccionCOid;
import com.keralty.aegenerasolicitud.persistence.oid.EstadoContratoOid;
import com.keralty.aegenerasolicitud.persistence.oid.FamiliaCOid;
import com.keralty.aegenerasolicitud.persistence.oid.FormaPagoOid;
import com.keralty.aegenerasolicitud.persistence.oid.GrupoAsociadoOid;
import com.keralty.aegenerasolicitud.persistence.oid.MunicipioOid;
import com.keralty.aegenerasolicitud.persistence.oid.ObservacionesCOid;
import com.keralty.aegenerasolicitud.persistence.oid.PeriodicidadOid;
import com.keralty.aegenerasolicitud.persistence.oid.PersonaCOid;
import com.keralty.aegenerasolicitud.persistence.oid.PlanesOid;
import com.keralty.aegenerasolicitud.persistence.oid.ProductosOid;
import com.keralty.aegenerasolicitud.persistence.oid.SolicitudOid;
import com.keralty.aegenerasolicitud.persistence.oid.SucursalOid;
import com.keralty.aegenerasolicitud.persistence.oid.TarifaOid;
import com.keralty.aegenerasolicitud.persistence.oid.TipoIdentificacionOid;
import com.keralty.aegenerasolicitud.persistence.Periodicidad;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.Planes;
import com.keralty.aegenerasolicitud.persistence.Productos;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.Sucursal;
import com.keralty.aegenerasolicitud.persistence.Tarifa;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import com.keralty.aegenerasolicitud.repository.ContratoCJpaRepository;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereComparison;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandLiteral;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandPathAttribute;
import com.keralty.aegenerasolicitud.utils.Utilities;
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
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

@Transactional
@ApplicationScoped
public class ContratoCService extends AbstractService {

    ContratoCJpaRepository repository;

    ContratoCService contratoCSrv;

    PersonaCService personaCSrv;

    PeriodicidadService periodicidadSrv;

    FormaPagoService formaPagoSrv;

    EstadoContratoService estadoContratoSrv;

    ProductosService productosSrv;

    PlanesService planesSrv;

    TarifaService tarifaSrv;

    GrupoAsociadoService grupoAsociadoSrv;

    MunicipioService municipioSrv;

    SucursalService sucursalSrv;

    SolicitudService solicitudSrv;

    ObservacionesCService observacionesCSrv;

    DireccionService direccionSrv;

    DireccionCService direccionCSrv;

    TipoDireccionService tipoDireccionSrv;

    DepartamentoService departamentoSrv;

    PaisService paisSrv;

    ContratanteService contratanteSrv;

    AesolicitudesService aesolicitudesSrv;

    FamiliaCService familiaCSrv;

    TipoIdentificacionService tipoIdentificacionSrv;

    BeneficiarioCService beneficiarioCSrv;

    UserFunctions userFunctionsSrv;

    @Inject
    public ContratoCService(
            ContratoCService contratoCSrv,
            PersonaCService personaCSrv,
            PeriodicidadService periodicidadSrv,
            FormaPagoService formaPagoSrv,
            EstadoContratoService estadoContratoSrv,
            ProductosService productosSrv,
            PlanesService planesSrv,
            TarifaService tarifaSrv,
            GrupoAsociadoService grupoAsociadoSrv,
            MunicipioService municipioSrv,
            SucursalService sucursalSrv,
            SolicitudService solicitudSrv,
            ObservacionesCService observacionesCSrv,
            DireccionService direccionSrv,
            DireccionCService direccionCSrv,
            TipoDireccionService tipoDireccionSrv,
            DepartamentoService departamentoSrv,
            PaisService paisSrv,
            ContratanteService contratanteSrv,
            AesolicitudesService aesolicitudesSrv,
            FamiliaCService familiaCSrv,
            TipoIdentificacionService tipoIdentificacionSrv,
            BeneficiarioCService beneficiarioCSrv,
            UserFunctions userFunctionsSrv,
            ContratoCJpaRepository repository) {
        this.contratoCSrv = contratoCSrv;
        this.personaCSrv = personaCSrv;
        this.periodicidadSrv = periodicidadSrv;
        this.formaPagoSrv = formaPagoSrv;
        this.estadoContratoSrv = estadoContratoSrv;
        this.productosSrv = productosSrv;
        this.planesSrv = planesSrv;
        this.tarifaSrv = tarifaSrv;
        this.grupoAsociadoSrv = grupoAsociadoSrv;
        this.municipioSrv = municipioSrv;
        this.sucursalSrv = sucursalSrv;
        this.solicitudSrv = solicitudSrv;
        this.observacionesCSrv = observacionesCSrv;
        this.direccionSrv = direccionSrv;
        this.direccionCSrv = direccionCSrv;
        this.tipoDireccionSrv = tipoDireccionSrv;
        this.departamentoSrv = departamentoSrv;
        this.paisSrv = paisSrv;
        this.contratanteSrv = contratanteSrv;
        this.aesolicitudesSrv = aesolicitudesSrv;
        this.familiaCSrv = familiaCSrv;
        this.tipoIdentificacionSrv = tipoIdentificacionSrv;
        this.beneficiarioCSrv = beneficiarioCSrv;
        this.userFunctionsSrv = userFunctionsSrv;
        this.repository = repository;
    }

    public List<Map<String, Object>> solveQuery(QueryRequest request) {
        return solveGenericQuery(request, ContratoCConstants.CLASS_NAME);
    }

    private List<ContratoC> solveContratoCTypedQuery(QueryRequest request) {
        return repository.solveQuery(processQueryRequest(request, ContratoCConstants.CLASS_NAME), request.getPageSize(), request.getPageNumber());
    }
    
    private int solveContratoCTypedCountQuery(QueryRequest request) {
        return repository.solveCountQuery(processCountQueryRequest(request, ContratoCConstants.CLASS_NAME));
    }

    /*
     * Returns the population of class ContratoC
     *
     * @return Population of class ContratoC
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ContratoC> get() {
        List<ContratoC> results = new java.util.ArrayList<>();
        repository.findAll().forEach(results::add);
        return results;
    }
    /*
     * Returns the population of class ContratoC that matches certain search criteria
     *
     * @param queryRequest Search criteria
     *
     * @return Population of class ContratoC matching the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ContratoC> get(QueryRequest queryRequest) {
        return solveContratoCTypedQuery(queryRequest);
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
            return new PopulationResponseViewModel(solveContratoCTypedQuery(queryRequest).stream()
                    .map(instance -> buildDynamicObject(instance, queryRequest.getDisplaySetItems()))
                    .collect(Collectors.toList()), queryRequest.getPageSize() != 0 ? solveContratoCTypedCountQuery(queryRequest) : 0);
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
        return new PopulationResponseViewModel(solveContratoCTypedQuery(queryRequest).stream()
                .map(instance -> buildDynamicObject(instance, displaySetItems))
                .collect(Collectors.toList()));
    }

    /*
     * Returns an instance of ContratoC by ID
     *
     * @param contratoCID Identification field contratoCID
     *
     * @return Instance of ContratoC
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ContratoC getById(Long contratoCID) {
        return getByOID(new ContratoCOid(contratoCID));
    }
    
    /*
     * Returns an instance of ContratoC by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of ContratoC
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ContratoC getByOID(ContratoCOid oid) {
        Optional<ContratoC> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of ContratoC by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of ContratoC
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public ContratoC getByOIDWithHV(ContratoCOid oid) {
        Optional<ContratoC> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<ContratoC> getInstanceByOid(ContratoCOid oid) {
        return oid != null && oid.getContratoCID() != null
               ? repository.findById(oid.getContratoCID())
               : Optional.empty();
    }

    /*
     * Returns an instance of ContratoC by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of ContratoC
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public ContratoC getByJsonOid(String jsonOid) {
        ContratoCOid oid = new ContratoCOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }

    /*
     * Returns the list of instances of ContratoC related to a given instance through 
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of ContratoC
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ContratoC> getByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        QueryRequest queryRequest = new QueryRequest(new FilterRequest(Constants.FILTER_BY_RELATED,
                Arrays.asList(new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_PATH, relatedInstancePath),
                        new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_OID, relatedInstance.getOid().toJson()))));
        return solveContratoCTypedQuery(queryRequest);
    }

    /*
     * Returns the list of instances of ContratoC related to a given instance through
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of ContratoC
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ContratoC> getInstancesByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        return getByRelated(relatedInstance, relatedInstancePath);
    }

    /*
     * Returns a set of displayset items for an instance of ContratoC
     *
     * @param oid Object IDentifier
     *
     * @param displaySetItems List of comma-separated displayset items
     *
     * @return Set of displayset items for an instance of ContratoC
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public InstanceResponseViewModel getContratoCDynamic(ContratoCOid oid, String displaySetItems) {
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
    public String getSuppInfo(ContratoCOid oid, String dsName) {
        ContratoC instance = getByOIDWithHV(oid);
        return (instance != null ? getSuppInfo(instance, dsName): "");
    }

    private String getSuppInfo(ContratoC instance, String dsName) {
        if (instance == null || instance.isNull()) return "";
        return dsName.isEmpty() ? instance.getOid().toJson() : "";
    }




    // IIU Methods



    // Service: crear

    /*
     * Entry point for service crear
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse crear(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CrearRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.crearTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service crear
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse crearTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CrearRequest request) throws ModelException {
        request.setContratoCcrearpagrPersonaCContratanteInstance(personaCSrv.getByOIDWithHV(request.getContratoCcrearpagrPersonaCContratante()));
        request.setContratoCcrearpagrPeriodicidadCInstance(periodicidadSrv.getByOIDWithHV(request.getContratoCcrearpagrPeriodicidadC()));
        request.setContratoCcrearpagrFormaPagoCInstance(formaPagoSrv.getByOIDWithHV(request.getContratoCcrearpagrFormaPagoC()));
        request.setContratoCcrearpagrEstadoContratoCInstance(estadoContratoSrv.getByOIDWithHV(request.getContratoCcrearpagrEstadoContratoC()));
        request.setContratoCcrearpagrProductoCInstance(productosSrv.getByOIDWithHV(request.getContratoCcrearpagrProductoC()));
        request.setContratoCcrearpagrPlanCInstance(planesSrv.getByOIDWithHV(request.getContratoCcrearpagrPlanC()));
        request.setContratoCcrearpagrTarifaCInstance(tarifaSrv.getByOIDWithHV(request.getContratoCcrearpagrTarifaC()));
        request.setContratoCcrearpagrGrupoAsociadoCInstance(grupoAsociadoSrv.getByOIDWithHV(request.getContratoCcrearpagrGrupoAsociadoC()));
        request.setContratoCcrearpagrMunicipioCInstance(municipioSrv.getByOIDWithHV(request.getContratoCcrearpagrMunicipioC()));
        request.setContratoCcrearpagrSucursalCInstance(sucursalSrv.getByOIDWithHV(request.getContratoCcrearpagrSucursalC()));
        request.setContratoCcrearpagrAsesorCInstance(asesorSrv.getByOIDWithHV(request.getContratoCcrearpagrAsesorC()));
        return contratoCSrv.crearImplementation(request);
    }

    /*
     * Implements service crear
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse crearImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CrearRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Periodicidad contratoCcrearpagrPeriodicidadC = request.getContratoCcrearpagrPeriodicidadCInstance();
        FormaPago contratoCcrearpagrFormaPagoC = request.getContratoCcrearpagrFormaPagoCInstance();
        EstadoContrato contratoCcrearpagrEstadoContratoC = request.getContratoCcrearpagrEstadoContratoCInstance();
        Productos contratoCcrearpagrProductoC = request.getContratoCcrearpagrProductoCInstance();
        Planes contratoCcrearpagrPlanC = request.getContratoCcrearpagrPlanCInstance();
        Tarifa contratoCcrearpagrTarifaC = request.getContratoCcrearpagrTarifaCInstance();
        GrupoAsociado contratoCcrearpagrGrupoAsociadoC = request.getContratoCcrearpagrGrupoAsociadoCInstance();
        Municipio contratoCcrearpagrMunicipioC = request.getContratoCcrearpagrMunicipioCInstance();
        Sucursal contratoCcrearpagrSucursalC = request.getContratoCcrearpagrSucursalCInstance();
        Asesor contratoCcrearpagrAsesorC = request.getContratoCcrearpagrAsesorCInstance();
        String contratoCcrearpatrNumSolicitud = request.getContratoCcrearpatrNumSolicitud();
        Date contratoCcrearpatrFechaSolicitud = request.getContratoCcrearpatrFechaSolicitud();
        Long contratoCcrearpatrTipoContrato = request.getContratoCcrearpatrTipoContrato();
        Date contratoCcrearpatrFechaIniVigencia = request.getContratoCcrearpatrFechaIniVigencia();
        String contratoCcrearpatrDireccion = request.getContratoCcrearpatrDireccion();
        String contratoCcrearpatrcorreoContratante = request.getContratoCcrearpatrcorreoContratante();
        String contratoCcrearpatrnumTelContratante = request.getContratoCcrearpatrnumTelContratante();
        String contratoCcrearpatrnumCelContratante = request.getContratoCcrearpatrnumCelContratante();
        String contratoCcrearpatrexentoIVA = request.getContratoCcrearpatrexentoIVA();
        Boolean contratoCcrearpatrContratoConFirma = request.getContratoCcrearpatrContratoConFirma();
        String contratoCcrearpatrNumContrato = request.getContratoCcrearpatrNumContrato();
        Date contratoCcrearpatrFechaFinVigencia = request.getContratoCcrearpatrFechaFinVigencia();
        Timestamp contratoCcrearpatrUltimaActualizacion = request.getContratoCcrearpatrUltimaActualizacion();
        String contratoCcrearpatrCodTarifa = request.getContratoCcrearpatrCodTarifa();

        request.checkArguments();
        ContratoC instance = new ContratoC();

        if (contratoCcrearpagrPeriodicidadC != null && !contratoCcrearpagrPeriodicidadC.isNull()) {
            instance.add2PeriodicidadC(contratoCcrearpagrPeriodicidadC);
        }

        if (contratoCcrearpagrFormaPagoC != null && !contratoCcrearpagrFormaPagoC.isNull()) {
            instance.add2FormaPagoC(contratoCcrearpagrFormaPagoC);
        }

        if (contratoCcrearpagrEstadoContratoC != null && !contratoCcrearpagrEstadoContratoC.isNull()) {
            instance.add2EstadoContratoC(contratoCcrearpagrEstadoContratoC);
        }

        instance.add2ProductoC(contratoCcrearpagrProductoC);

        instance.add2PlanC(contratoCcrearpagrPlanC);

        if (contratoCcrearpagrTarifaC != null && !contratoCcrearpagrTarifaC.isNull()) {
            instance.add2TarifaC(contratoCcrearpagrTarifaC);
        }

        if (contratoCcrearpagrGrupoAsociadoC != null && !contratoCcrearpagrGrupoAsociadoC.isNull()) {
            instance.add2GrupoAsociadoC(contratoCcrearpagrGrupoAsociadoC);
        }

        if (contratoCcrearpagrMunicipioC != null && !contratoCcrearpagrMunicipioC.isNull()) {
            instance.add2MunicipioC(contratoCcrearpagrMunicipioC);
        }

        if (contratoCcrearpagrSucursalC != null && !contratoCcrearpagrSucursalC.isNull()) {
            instance.add2SucursalC(contratoCcrearpagrSucursalC);
        }

        if (contratoCcrearpagrAsesorC != null && !contratoCcrearpagrAsesorC.isNull()) {
            instance.add2AsesorC(contratoCcrearpagrAsesorC);
        }

        instance.setContratoCID(Long.valueOf(0));
        instance.setContratoCvalorCuotaPeriodo(Utilities.convertToDouble(Long.valueOf(0)));
        instance.setContratoCdescuentoComercial(Utilities.convertToDouble(Long.valueOf(0)));
        instance.setContratoCIVA(Utilities.convertToDouble(Long.valueOf(0)));
        instance.setContratoCdescuentoFinanciero(Utilities.convertToDouble(Long.valueOf(0)));
        instance.setContratoCcontratoNotificado(Boolean.FALSE);
        instance.setContratoCNumSolicitud(contratoCcrearpatrNumSolicitud);
        instance.setContratoCFechaSolicitud(contratoCcrearpatrFechaSolicitud);
        instance.setContratoCTipoContrato(contratoCcrearpatrTipoContrato);
        instance.setContratoCFechaIniVigencia(contratoCcrearpatrFechaIniVigencia);
        instance.setContratoCDireccion(contratoCcrearpatrDireccion);
        instance.setContratoCcorreoContratante(contratoCcrearpatrcorreoContratante);
        instance.setContratoCnumTelContratante(contratoCcrearpatrnumTelContratante);
        instance.setContratoCnumCelContratante(contratoCcrearpatrnumCelContratante);
        instance.setContratoCexentoIVA(contratoCcrearpatrexentoIVA);
        instance.setContratoCContratoConFirma(contratoCcrearpatrContratoConFirma);
        instance.setContratoCNumContrato(contratoCcrearpatrNumContrato);
        instance.setContratoCFechaFinVigencia(contratoCcrearpatrFechaFinVigencia);
        instance.setContratoCUltimaActualizacion(contratoCcrearpatrUltimaActualizacion);
        instance.setContratoCCodTarifa(contratoCcrearpatrCodTarifa);

        response.setInstance(repository.save(instance));

        return response;
    }

    // Service: CalcularLiquidacion

    /*
     * Entry point for service CalcularLiquidacion
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse calcularLiquidacion(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CalcularLiquidacionRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.calcularLiquidacionTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service CalcularLiquidacion
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse calcularLiquidacionTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CalcularLiquidacionRequest request) throws ModelException, SystemException {
        request.setContratoCCalcularLiquidacionpthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCCalcularLiquidacionpthisContratoC()));
        return contratoCSrv.calcularLiquidacionImplementation(request);
    }

    /*
     * Implements service CalcularLiquidacion
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse calcularLiquidacionImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CalcularLiquidacionRequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        ContratoC contratoCCalcularLiquidacionpthisContratoC = request.getContratoCCalcularLiquidacionpthisContratoCInstance();

        request.checkArguments();
        ContratoC instance = contratoCCalcularLiquidacionpthisContratoC;
        instance.setContratoCvalorCuotaPeriodo(contratoCCalcularLiquidacionpthisContratoC.getContratoCdrvValorCuotaAfiliacion());
        instance.setContratoCdescuentoComercial(repository.assocOperator007(contratoCCalcularLiquidacionpthisContratoC));
        instance.setContratoCdescuentoFinanciero(repository.assocOperator008(contratoCCalcularLiquidacionpthisContratoC));


        response.setInstance(repository.save(instance));

        return response;
    }

    // Service: InsContratoCASolicitud

    /*
     * Entry point for service InsContratoCASolicitud
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse insContratoCASolicitud(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsContratoCASolicitudRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.insContratoCASolicitudTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service InsContratoCASolicitud
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse insContratoCASolicitudTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsContratoCASolicitudRequest request) throws ModelException {
        request.setContratoCInsContratoCASolicitudpthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCInsContratoCASolicitudpthisContratoC()));
        request.setContratoCInsContratoCASolicitudpevcSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getContratoCInsContratoCASolicitudpevcSolicitud()));
        return contratoCSrv.insContratoCASolicitudImplementation(request, true);
    }

    /*
     * Implements service InsContratoCASolicitud
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse insContratoCASolicitudImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsContratoCASolicitudRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        ContratoC contratoCInsContratoCASolicitudpthisContratoC = request.getContratoCInsContratoCASolicitudpthisContratoCInstance();
        Solicitud contratoCInsContratoCASolicitudpevcSolicitud = request.getContratoCInsContratoCASolicitudpevcSolicitudInstance();

        request.checkArguments();
        ContratoC instance = contratoCInsContratoCASolicitudpthisContratoC;

        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsContratoCASolicitudRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsContratoCASolicitudRequest();
            relatedRequest.setSolicitudInsContratoCASolicitudpthisSolicitudInstance(contratoCInsContratoCASolicitudpevcSolicitud);
            relatedRequest.setSolicitudInsContratoCASolicitudpevcContratoCInstance(contratoCInsContratoCASolicitudpthisContratoC);
            solicitudSrv.insContratoCASolicitudImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));

        return response;
    }

    // Service: InsPeriodicidad

    /*
     * Entry point for service InsPeriodicidad
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse insPeriodicidad(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsPeriodicidadRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.insPeriodicidadTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service InsPeriodicidad
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse insPeriodicidadTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsPeriodicidadRequest request) throws ModelException {
        request.setContratoCInsPeriodicidadpthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCInsPeriodicidadpthisContratoC()));
        request.setContratoCInsPeriodicidadpevcPeriodicidadInstance(periodicidadSrv.getByOIDWithHV(request.getContratoCInsPeriodicidadpevcPeriodicidad()));
        return contratoCSrv.insPeriodicidadImplementation(request, true);
    }

    /*
     * Implements service InsPeriodicidad
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse insPeriodicidadImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsPeriodicidadRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        ContratoC contratoCInsPeriodicidadpthisContratoC = request.getContratoCInsPeriodicidadpthisContratoCInstance();
        Periodicidad contratoCInsPeriodicidadpevcPeriodicidad = request.getContratoCInsPeriodicidadpevcPeriodicidadInstance();

        request.checkArguments();
        ContratoC instance = contratoCInsPeriodicidadpthisContratoC;

        instance.add2PeriodicidadC(contratoCInsPeriodicidadpevcPeriodicidad);
        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.periodicidad.siu.InsPeriodicidadRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.periodicidad.siu.InsPeriodicidadRequest();
            relatedRequest.setPeriodicidadInsPeriodicidadpthisPeriodicidadInstance(contratoCInsPeriodicidadpevcPeriodicidad);
            relatedRequest.setPeriodicidadInsPeriodicidadpevcContratoCInstance(contratoCInsPeriodicidadpthisContratoC);
            periodicidadSrv.insPeriodicidadImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));

        return response;
    }

    // Service: DelPeriodicidad

    /*
     * Entry point for service DelPeriodicidad
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse delPeriodicidad(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelPeriodicidadRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.delPeriodicidadTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service DelPeriodicidad
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse delPeriodicidadTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelPeriodicidadRequest request) throws ModelException {
        request.setContratoCDelPeriodicidadpthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCDelPeriodicidadpthisContratoC()));
        request.setContratoCDelPeriodicidadpevcPeriodicidadInstance(periodicidadSrv.getByOIDWithHV(request.getContratoCDelPeriodicidadpevcPeriodicidad()));
        return contratoCSrv.delPeriodicidadImplementation(request, true);
    }

    /*
     * Implements service DelPeriodicidad
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse delPeriodicidadImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelPeriodicidadRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        ContratoC contratoCDelPeriodicidadpthisContratoC = request.getContratoCDelPeriodicidadpthisContratoCInstance();
        Periodicidad contratoCDelPeriodicidadpevcPeriodicidad = request.getContratoCDelPeriodicidadpevcPeriodicidadInstance();

        request.checkArguments();
        ContratoC instance = contratoCDelPeriodicidadpthisContratoC;

        instance.add2PeriodicidadC(null);
        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.periodicidad.siu.DelPeriodicidadRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.periodicidad.siu.DelPeriodicidadRequest();
            relatedRequest.setPeriodicidadDelPeriodicidadpthisPeriodicidadInstance(contratoCDelPeriodicidadpevcPeriodicidad);
            relatedRequest.setPeriodicidadDelPeriodicidadpevcContratoCInstance(contratoCDelPeriodicidadpthisContratoC);
            periodicidadSrv.delPeriodicidadImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));

        return response;
    }

    // Service: InsMunicipioContrato

    /*
     * Entry point for service InsMunicipioContrato
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse insMunicipioContrato(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsMunicipioContratoRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.insMunicipioContratoTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service InsMunicipioContrato
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse insMunicipioContratoTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsMunicipioContratoRequest request) throws ModelException {
        request.setContratoCInsMunicipioContratopthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCInsMunicipioContratopthisContratoC()));
        request.setContratoCInsMunicipioContratopevcMunicipioInstance(municipioSrv.getByOIDWithHV(request.getContratoCInsMunicipioContratopevcMunicipio()));
        return contratoCSrv.insMunicipioContratoImplementation(request, true);
    }

    /*
     * Implements service InsMunicipioContrato
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse insMunicipioContratoImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsMunicipioContratoRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        ContratoC contratoCInsMunicipioContratopthisContratoC = request.getContratoCInsMunicipioContratopthisContratoCInstance();
        Municipio contratoCInsMunicipioContratopevcMunicipio = request.getContratoCInsMunicipioContratopevcMunicipioInstance();

        request.checkArguments();
        ContratoC instance = contratoCInsMunicipioContratopthisContratoC;

        instance.add2MunicipioC(contratoCInsMunicipioContratopevcMunicipio);
        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.InsMunicipioContratoRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.municipio.siu.InsMunicipioContratoRequest();
            relatedRequest.setMunicipioInsMunicipioContratopthisMunicipioInstance(contratoCInsMunicipioContratopevcMunicipio);
            relatedRequest.setMunicipioInsMunicipioContratopevcContratoCInstance(contratoCInsMunicipioContratopthisContratoC);
            municipioSrv.insMunicipioContratoImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));

        return response;
    }

    // Service: DelMunicipioContrato

    /*
     * Entry point for service DelMunicipioContrato
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse delMunicipioContrato(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelMunicipioContratoRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.delMunicipioContratoTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service DelMunicipioContrato
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse delMunicipioContratoTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelMunicipioContratoRequest request) throws ModelException {
        request.setContratoCDelMunicipioContratopthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCDelMunicipioContratopthisContratoC()));
        request.setContratoCDelMunicipioContratopevcMunicipioInstance(municipioSrv.getByOIDWithHV(request.getContratoCDelMunicipioContratopevcMunicipio()));
        return contratoCSrv.delMunicipioContratoImplementation(request, true);
    }

    /*
     * Implements service DelMunicipioContrato
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse delMunicipioContratoImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelMunicipioContratoRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        ContratoC contratoCDelMunicipioContratopthisContratoC = request.getContratoCDelMunicipioContratopthisContratoCInstance();
        Municipio contratoCDelMunicipioContratopevcMunicipio = request.getContratoCDelMunicipioContratopevcMunicipioInstance();

        request.checkArguments();
        ContratoC instance = contratoCDelMunicipioContratopthisContratoC;

        instance.add2MunicipioC(null);
        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.DelMunicipioContratoRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.municipio.siu.DelMunicipioContratoRequest();
            relatedRequest.setMunicipioDelMunicipioContratopthisMunicipioInstance(contratoCDelMunicipioContratopevcMunicipio);
            relatedRequest.setMunicipioDelMunicipioContratopevcContratoCInstance(contratoCDelMunicipioContratopthisContratoC);
            municipioSrv.delMunicipioContratoImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));

        return response;
    }

    // Service: InsAsesorC

    /*
     * Entry point for service InsAsesorC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse insAsesorC(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsAsesorCRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.insAsesorCTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service InsAsesorC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse insAsesorCTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsAsesorCRequest request) throws ModelException {
        request.setContratoCInsAsesorCpthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCInsAsesorCpthisContratoC()));
        request.setContratoCInsAsesorCpevcAsesorInstance(asesorSrv.getByOIDWithHV(request.getContratoCInsAsesorCpevcAsesor()));
        return contratoCSrv.insAsesorCImplementation(request, true);
    }

    /*
     * Implements service InsAsesorC
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse insAsesorCImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsAsesorCRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        ContratoC contratoCInsAsesorCpthisContratoC = request.getContratoCInsAsesorCpthisContratoCInstance();
        Asesor contratoCInsAsesorCpevcAsesor = request.getContratoCInsAsesorCpevcAsesorInstance();

        request.checkArguments();
        ContratoC instance = contratoCInsAsesorCpthisContratoC;

        instance.add2AsesorC(contratoCInsAsesorCpevcAsesor);
        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.asesor.siu.InsAsesorCRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.asesor.siu.InsAsesorCRequest();
            relatedRequest.setAsesorInsAsesorCpthisAsesorInstance(contratoCInsAsesorCpevcAsesor);
            relatedRequest.setAsesorInsAsesorCpevcContratoCInstance(contratoCInsAsesorCpthisContratoC);
            asesorSrv.insAsesorCImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));

        return response;
    }

    // Service: DelAsesorC

    /*
     * Entry point for service DelAsesorC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse delAsesorC(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelAsesorCRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.delAsesorCTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service DelAsesorC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse delAsesorCTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelAsesorCRequest request) throws ModelException {
        request.setContratoCDelAsesorCpthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCDelAsesorCpthisContratoC()));
        request.setContratoCDelAsesorCpevcAsesorInstance(asesorSrv.getByOIDWithHV(request.getContratoCDelAsesorCpevcAsesor()));
        return contratoCSrv.delAsesorCImplementation(request, true);
    }

    /*
     * Implements service DelAsesorC
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse delAsesorCImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelAsesorCRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        ContratoC contratoCDelAsesorCpthisContratoC = request.getContratoCDelAsesorCpthisContratoCInstance();
        Asesor contratoCDelAsesorCpevcAsesor = request.getContratoCDelAsesorCpevcAsesorInstance();

        request.checkArguments();
        ContratoC instance = contratoCDelAsesorCpthisContratoC;

        instance.add2AsesorC(null);
        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.asesor.siu.DelAsesorCRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.asesor.siu.DelAsesorCRequest();
            relatedRequest.setAsesorDelAsesorCpthisAsesorInstance(contratoCDelAsesorCpevcAsesor);
            relatedRequest.setAsesorDelAsesorCpevcContratoCInstance(contratoCDelAsesorCpthisContratoC);
            asesorSrv.delAsesorCImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));

        return response;
    }

    // Service: chgProductoC

    /*
     * Entry point for service chgProductoC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse chgProductoC(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.ChgProductoCRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.chgProductoCTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service chgProductoC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse chgProductoCTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.ChgProductoCRequest request) throws ModelException {
        request.setContratoCchgProductoCpthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCchgProductoCpthisContratoC()));
        request.setContratoCchgProductoCpevcProductosInstance(productosSrv.getByOIDWithHV(request.getContratoCchgProductoCpevcProductos()));
        return contratoCSrv.chgProductoCImplementation(request, true);
    }

    /*
     * Implements service chgProductoC
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse chgProductoCImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.ChgProductoCRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        ContratoC contratoCchgProductoCpthisContratoC = request.getContratoCchgProductoCpthisContratoCInstance();
        Productos contratoCchgProductoCpevcProductos = request.getContratoCchgProductoCpevcProductosInstance();

        request.checkArguments();
        ContratoC instance = contratoCchgProductoCpthisContratoC;

        instance.add2ProductoC(contratoCchgProductoCpevcProductos);
        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.productos.siu.ChgProductoCRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.productos.siu.ChgProductoCRequest();
            relatedRequest.setProductoschgProductoCpthisProductosInstance(contratoCchgProductoCpevcProductos);
            relatedRequest.setProductoschgProductoCpevcContratoCInstance(contratoCchgProductoCpthisContratoC);
            productosSrv.chgProductoCImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));

        return response;
    }

    // Service: chgPlanC

    /*
     * Entry point for service chgPlanC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse chgPlanC(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.ChgPlanCRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.chgPlanCTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service chgPlanC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse chgPlanCTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.ChgPlanCRequest request) throws ModelException {
        request.setContratoCchgPlanCpthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCchgPlanCpthisContratoC()));
        request.setContratoCchgPlanCpevcPlanesInstance(planesSrv.getByOIDWithHV(request.getContratoCchgPlanCpevcPlanes()));
        return contratoCSrv.chgPlanCImplementation(request, true);
    }

    /*
     * Implements service chgPlanC
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse chgPlanCImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.ChgPlanCRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        ContratoC contratoCchgPlanCpthisContratoC = request.getContratoCchgPlanCpthisContratoCInstance();
        Planes contratoCchgPlanCpevcPlanes = request.getContratoCchgPlanCpevcPlanesInstance();

        request.checkArguments();
        ContratoC instance = contratoCchgPlanCpthisContratoC;

        instance.add2PlanC(contratoCchgPlanCpevcPlanes);
        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.planes.siu.ChgPlanCRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.planes.siu.ChgPlanCRequest();
            relatedRequest.setPlaneschgPlanCpthisPlanesInstance(contratoCchgPlanCpevcPlanes);
            relatedRequest.setPlaneschgPlanCpevcContratoCInstance(contratoCchgPlanCpthisContratoC);
            planesSrv.chgPlanCImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));

        return response;
    }

    // Service: evalPostNotifCrear

    /*
     * Entry point for service evalPostNotifCrear
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse evalPostNotifCrear(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.EvalPostNotifCrearRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.evalPostNotifCrearTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service evalPostNotifCrear
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse evalPostNotifCrearTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.EvalPostNotifCrearRequest request) throws ModelException {
        request.setContratoCevalPostNotifCrearpthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCevalPostNotifCrearpthisContratoC()));
        return contratoCSrv.evalPostNotifCrearImplementation(request);
    }

    /*
     * Implements service evalPostNotifCrear
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse evalPostNotifCrearImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.EvalPostNotifCrearRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        ContratoC contratoCevalPostNotifCrearpthisContratoC = request.getContratoCevalPostNotifCrearpthisContratoCInstance();
        String contratoCevalPostNotifCrearpNumContrato = request.getContratoCevalPostNotifCrearpNumContrato();
        Long contratoCevalPostNotifCrearpTipoContrato = request.getContratoCevalPostNotifCrearpTipoContrato();
        Date contratoCevalPostNotifCrearpFechaIniVigencia = request.getContratoCevalPostNotifCrearpFechaIniVigencia();
        Date contratoCevalPostNotifCrearpFechaFinVigencia = request.getContratoCevalPostNotifCrearpFechaFinVigencia();
        String contratoCevalPostNotifCrearpDireccion = request.getContratoCevalPostNotifCrearpDireccion();
        String contratoCevalPostNotifCrearpnumTelContratante = request.getContratoCevalPostNotifCrearpnumTelContratante();
        String contratoCevalPostNotifCrearpnumCelContratante = request.getContratoCevalPostNotifCrearpnumCelContratante();

        request.checkArguments();
        ContratoC instance = contratoCevalPostNotifCrearpthisContratoC;
        instance.setContratoCNumContrato(contratoCevalPostNotifCrearpNumContrato);
        instance.setContratoCTipoContrato(contratoCevalPostNotifCrearpTipoContrato);
        instance.setContratoCFechaIniVigencia(contratoCevalPostNotifCrearpFechaIniVigencia);
        instance.setContratoCFechaFinVigencia(contratoCevalPostNotifCrearpFechaFinVigencia);
        instance.setContratoCDireccion(contratoCevalPostNotifCrearpDireccion);
        instance.setContratoCnumTelContratante(contratoCevalPostNotifCrearpnumTelContratante);
        instance.setContratoCnumCelContratante(contratoCevalPostNotifCrearpnumCelContratante);


        response.setInstance(repository.save(instance));

        return response;
    }

    // Service: TCREAROBS100

    /*
     * Entry point for service TCREAROBS100
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tCREAROBS100(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TCREAROBS100Request request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.tCREAROBS100Txn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TCREAROBS100
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tCREAROBS100Txn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TCREAROBS100Request request) throws ModelException {
        request.setContratoCTCREAROBS100pthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCTCREAROBS100pthisContratoC()));
        return contratoCSrv.tCREAROBS100Implementation(request);
    }

    /*
     * Implements service TCREAROBS100
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tCREAROBS100Implementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TCREAROBS100Request request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        String contratoCTCREAROBS100pObsText = request.getContratoCTCREAROBS100pObsText();
        ContratoC contratoCTCREAROBS100pthisContratoC = request.getContratoCTCREAROBS100pthisContratoCInstance();

        request.checkArguments();
        ContratoC instance = contratoCTCREAROBS100pthisContratoC;

        /* Action 1: IF (length(pObsText) > 100) */
        boolean ifCondition1 = STDFunctions.lengthFun(contratoCTCREAROBS100pObsText) > Long.valueOf(100);
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.observacionesc.siu.CrearRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.observacionesc.siu.CrearRequest();
            step10Request.setObservacionesCcrearpagrContratoCInstance(contratoCTCREAROBS100pthisContratoC);
            step10Request.setObservacionesCcrearpatrCodTipoObservacion("2");
            step10Request.setObservacionesCcrearpatrTipoObservacion("OBSERVACIONES MANUALES");
            step10Request.setObservacionesCcrearpatrObservacions(STDFunctions.leftSubstringFun(contratoCTCREAROBS100pObsText, Long.valueOf(100)));
            observacionesCSrv.crearImplementation(step10Request);
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TCREAROBS100Request step11Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TCREAROBS100Request();
            step11Request.setContratoCTCREAROBS100pthisContratoCInstance(contratoCTCREAROBS100pthisContratoC);
            step11Request.setContratoCTCREAROBS100pObsText(STDFunctions.substringFun(contratoCTCREAROBS100pObsText, Long.valueOf(100), STDFunctions.lengthFun(contratoCTCREAROBS100pObsText) - Long.valueOf(100)));
            contratoCSrv.tCREAROBS100Implementation(step11Request);
        } else {
            com.keralty.aegenerasolicitud.viewmodel.observacionesc.siu.CrearRequest step12Request = new com.keralty.aegenerasolicitud.viewmodel.observacionesc.siu.CrearRequest();
            step12Request.setObservacionesCcrearpagrContratoCInstance(contratoCTCREAROBS100pthisContratoC);
            step12Request.setObservacionesCcrearpatrCodTipoObservacion("2");
            step12Request.setObservacionesCcrearpatrTipoObservacion("OBSERVACIONES MANUALES");
            step12Request.setObservacionesCcrearpatrObservacions(contratoCTCREAROBS100pObsText);
            observacionesCSrv.crearImplementation(step12Request);
        }

        response.setInstance(getByOID(instance.getOid()));

        return response;
    }

    // Service: TCREAROMODDIRDEAE

    /*
     * Entry point for service TCREAROMODDIRDEAE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tCREAROMODDIRDEAE(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TCREAROMODDIRDEAERequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.tCREAROMODDIRDEAETxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TCREAROMODDIRDEAE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tCREAROMODDIRDEAETxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TCREAROMODDIRDEAERequest request) throws ModelException, SystemException {
        request.setContratoCTCREAROMODDIRDEAEpthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCTCREAROMODDIRDEAEpthisContratoC()));
        request.setContratoCTCREAROMODDIRDEAEpSolicitudAEInstance(solicitudSrv.getByOIDWithHV(request.getContratoCTCREAROMODDIRDEAEpSolicitudAE()));
        return contratoCSrv.tCREAROMODDIRDEAEImplementation(request);
    }

    /*
     * Implements service TCREAROMODDIRDEAE
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tCREAROMODDIRDEAEImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TCREAROMODDIRDEAERequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        Solicitud contratoCTCREAROMODDIRDEAEpSolicitudAE = request.getContratoCTCREAROMODDIRDEAEpSolicitudAEInstance();
        ContratoC contratoCTCREAROMODDIRDEAEpthisContratoC = request.getContratoCTCREAROMODDIRDEAEpthisContratoCInstance();

        request.checkArguments();
        ContratoC instance = contratoCTCREAROMODDIRDEAEpthisContratoC;

        /* Action 1: IF (EXIST (pSolicitudAE.Direccion) = true) */
        boolean ifCondition1 = repository.assocOperator020(contratoCTCREAROMODDIRDEAEpSolicitudAE).equals(true);
        if (ifCondition1) {
            for(DireccionC step1010Entity : repository.assocOperator018(contratoCTCREAROMODDIRDEAEpthisContratoC, contratoCTCREAROMODDIRDEAEpSolicitudAE)) {
                com.keralty.aegenerasolicitud.viewmodel.direccionc.siu.TMODIFICARRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.direccionc.siu.TMODIFICARRequest();
                step10Request.setDireccionCTMODIFICARpthisDireccionCInstance(step1010Entity);
                step10Request.setDireccionCTMODIFICARptpDireccionCompleta(contratoCTCREAROMODDIRDEAEpSolicitudAE.getDireccion().getDireccionDireccionCompleta());
                step10Request.setDireccionCTMODIFICARptpevcDepartamentoCInstance(contratoCTCREAROMODDIRDEAEpSolicitudAE.getDireccion().getDepartamento());
                step10Request.setDireccionCTMODIFICARptpevcMunicipioCInstance(contratoCTCREAROMODDIRDEAEpSolicitudAE.getDireccion().getMunicipio());
                step10Request.setDireccionCTMODIFICARptpevcPaisCInstance(contratoCTCREAROMODDIRDEAEpSolicitudAE.getDireccion().getPais());
                step10Request.setDireccionCTMODIFICARptpevcTipoDireccionCInstance(step1010Entity.getTipoDireccionC());
                step10Request.setDireccionCTMODIFICARptpLocalidad(step1010Entity.getDireccionCLocalidad());
                direccionCSrv.tMODIFICARImplementation(step10Request);
            }
            /* Action 11: IF (EXIST (DireccionesC) WHERE (DireccionesC.TipoDireccionC = pSolicitudAE.Direccion.TipoDireccion) = false) */
            boolean ifCondition11 = repository.assocOperator021(contratoCTCREAROMODDIRDEAEpthisContratoC, contratoCTCREAROMODDIRDEAEpSolicitudAE).equals(false);
            if (ifCondition11) {
                com.keralty.aegenerasolicitud.viewmodel.direccionc.siu.TCREARRequest step110Request = new com.keralty.aegenerasolicitud.viewmodel.direccionc.siu.TCREARRequest();
                step110Request.setDireccionCTCREARpagrDepartamentoCInstance(contratoCTCREAROMODDIRDEAEpSolicitudAE.getDireccion().getDepartamento());
                step110Request.setDireccionCTCREARpagrMunicipioCInstance(contratoCTCREAROMODDIRDEAEpSolicitudAE.getDireccion().getMunicipio());
                step110Request.setDireccionCTCREARpagrTipoDireccionCInstance(contratoCTCREAROMODDIRDEAEpSolicitudAE.getDireccion().getTipoDireccion());
                step110Request.setDireccionCTCREARpagrPaisCInstance(contratoCTCREAROMODDIRDEAEpSolicitudAE.getDireccion().getPais());
                step110Request.setDireccionCTCREARpagrPersonaCInstance(null);
                step110Request.setDireccionCTCREARpatrDireccionCompleta(contratoCTCREAROMODDIRDEAEpSolicitudAE.getDireccion().getDireccionDireccionCompleta());
                step110Request.setDireccionCTCREARpatrLocalidad(null);
                step110Request.setDireccionCTCREARpTelefonoFijo(contratoCTCREAROMODDIRDEAEpSolicitudAE.getContratante().getContratanteTelefonoFijo());
                step110Request.setDireccionCTCREARpTelefonoCelular(contratoCTCREAROMODDIRDEAEpSolicitudAE.getContratante().getContratanteTelefonoCelular());
                step110Request.setDireccionCTCREARpagrContratoCInstance(contratoCTCREAROMODDIRDEAEpthisContratoC);
                direccionCSrv.tCREARImplementation(step110Request);
            }
        }

        response.setInstance(getByOID(instance.getOid()));

        return response;
    }

    // Service: TNEWDESDEAE

    /*
     * Entry point for service TNEWDESDEAE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAEResponse tNEWDESDEAE(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAERequest request) {
        com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAEResponse response = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAEResponse();
        try {
            response = contratoCSrv.tNEWDESDEAETxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TNEWDESDEAE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAEResponse tNEWDESDEAETxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAERequest request) throws ModelException, SystemException {
        request.setContratoCTNEWDESDEAEptSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getContratoCTNEWDESDEAEptSolicitud()));
        return contratoCSrv.tNEWDESDEAEImplementation(request);
    }

    /*
     * Implements service TNEWDESDEAE
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAEResponse tNEWDESDEAEImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAERequest request) throws ModelException, SystemException {
        com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAEResponse response = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAEResponse();
        Solicitud contratoCTNEWDESDEAEptSolicitud = request.getContratoCTNEWDESDEAEptSolicitudInstance();
        String contratoCTNEWDESDEAEptNumFamiliaCORE = request.getContratoCTNEWDESDEAEptNumFamiliaCORE();

        request.checkArguments();
        ContratoC instance;

        /* Action 1: IF (ptSolicitud.Aesolicitudes.Tiposolicitud = "E") */
        boolean ifCondition1 = (contratoCTNEWDESDEAEptSolicitud.getAesolicitudes().getAesolicitudesTiposolicitud()).equals("E");
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CrearRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CrearRequest();
            step10Request.setContratoCcrearpagrPersonaCContratanteInstance(null);
            step10Request.setContratoCcrearpagrPeriodicidadCInstance(contratoCTNEWDESDEAEptSolicitud.getPeriodicidad());
            step10Request.setContratoCcrearpagrFormaPagoCInstance(contratoCTNEWDESDEAEptSolicitud.getFormaPago());
            step10Request.setContratoCcrearpagrEstadoContratoCInstance(null);
            step10Request.setContratoCcrearpagrProductoCInstance(contratoCTNEWDESDEAEptSolicitud.getAesolicitudes().getProductoT());
            step10Request.setContratoCcrearpagrPlanCInstance(contratoCTNEWDESDEAEptSolicitud.getAesolicitudes().getPlanT());
            step10Request.setContratoCcrearpagrTarifaCInstance(null);
            step10Request.setContratoCcrearpagrGrupoAsociadoCInstance(contratoCTNEWDESDEAEptSolicitud.getGrupoAsociado());
            step10Request.setContratoCcrearpagrMunicipioCInstance(contratoCTNEWDESDEAEptSolicitud.getDireccion().getMunicipio());
            step10Request.setContratoCcrearpagrSucursalCInstance(contratoCTNEWDESDEAEptSolicitud.getAgente().getSucursal());
            step10Request.setContratoCcrearpagrAsesorCInstance(contratoCTNEWDESDEAEptSolicitud.getAgente().getAsesorFacet());
            step10Request.setContratoCcrearpatrNumSolicitud(STDFunctions.intToStrFun(contratoCTNEWDESDEAEptSolicitud.getSolicitudNumRadicado()));
            step10Request.setContratoCcrearpatrFechaSolicitud(contratoCTNEWDESDEAEptSolicitud.getAesolicitudes().getAesolicitudesFechaasignacion());
            step10Request.setContratoCcrearpatrTipoContrato(contratoCTNEWDESDEAEptSolicitud.getSolicitudtipoContrato());
            step10Request.setContratoCcrearpatrFechaIniVigencia(contratoCTNEWDESDEAEptSolicitud.getSolicitudFInicioContrato());
            step10Request.setContratoCcrearpatrDireccion(contratoCTNEWDESDEAEptSolicitud.getDireccion().getDireccionDireccionCompleta());
            step10Request.setContratoCcrearpatrcorreoContratante(contratoCTNEWDESDEAEptSolicitud.getContratante().getContratanteCorreoElectronico());
            step10Request.setContratoCcrearpatrnumTelContratante(contratoCTNEWDESDEAEptSolicitud.getContratante().getContratanteTelefonoFijo());
            step10Request.setContratoCcrearpatrnumCelContratante(contratoCTNEWDESDEAEptSolicitud.getContratante().getContratanteTelefonoCelular());
            step10Request.setContratoCcrearpatrexentoIVA(contratoCTNEWDESDEAEptSolicitud.getSolicitudexentoIVA());
            step10Request.setContratoCcrearpatrContratoConFirma(contratoCTNEWDESDEAEptSolicitud.getSolicitudcontratoConFirma());
            step10Request.setContratoCcrearpatrNumContrato(null);
            step10Request.setContratoCcrearpatrFechaFinVigencia(null);
            step10Request.setContratoCcrearpatrUltimaActualizacion(STDFunctions.systemDateTimeFun());
            step10Request.setContratoCcrearpatrCodTarifa(userFunctionsSrv.fuGetCodTarifaProdPlanAnyoFun(contratoCTNEWDESDEAEptSolicitud.getAesolicitudes().getProductoT(), contratoCTNEWDESDEAEptSolicitud.getAesolicitudes().getPlanT(), STDFunctions.getYearFun(contratoCTNEWDESDEAEptSolicitud.getSolicitudFInicioContrato()), contratoCTNEWDESDEAEptSolicitud.getSolicitudtipoContrato()));
            instance = (ContratoC) contratoCSrv.crearImplementation(step10Request).getInstance();
        } else {
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CrearRequest step11Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CrearRequest();
            step11Request.setContratoCcrearpagrPersonaCContratanteInstance(null);
            step11Request.setContratoCcrearpagrPeriodicidadCInstance(null);
            step11Request.setContratoCcrearpagrFormaPagoCInstance(null);
            step11Request.setContratoCcrearpagrEstadoContratoCInstance(null);
            step11Request.setContratoCcrearpagrProductoCInstance(contratoCTNEWDESDEAEptSolicitud.getAesolicitudes().getProductoT());
            step11Request.setContratoCcrearpagrPlanCInstance(contratoCTNEWDESDEAEptSolicitud.getAesolicitudes().getPlanT());
            step11Request.setContratoCcrearpagrTarifaCInstance(null);
            step11Request.setContratoCcrearpagrGrupoAsociadoCInstance(null);
            step11Request.setContratoCcrearpagrMunicipioCInstance(null);
            step11Request.setContratoCcrearpagrSucursalCInstance(null);
            step11Request.setContratoCcrearpagrAsesorCInstance(null);
            step11Request.setContratoCcrearpatrNumSolicitud(contratoCTNEWDESDEAEptSolicitud.getAesolicitudes().getAesolicitudesNumerosolicitud());
            step11Request.setContratoCcrearpatrFechaSolicitud(null);
            step11Request.setContratoCcrearpatrTipoContrato(null);
            step11Request.setContratoCcrearpatrFechaIniVigencia(null);
            step11Request.setContratoCcrearpatrDireccion(null);
            step11Request.setContratoCcrearpatrcorreoContratante(null);
            step11Request.setContratoCcrearpatrnumTelContratante(null);
            step11Request.setContratoCcrearpatrnumCelContratante(null);
            step11Request.setContratoCcrearpatrexentoIVA(null);
            step11Request.setContratoCcrearpatrContratoConFirma(null);
            step11Request.setContratoCcrearpatrNumContrato(null);
            step11Request.setContratoCcrearpatrFechaFinVigencia(null);
            step11Request.setContratoCcrearpatrUltimaActualizacion(STDFunctions.systemDateTimeFun());
            step11Request.setContratoCcrearpatrCodTarifa(null);
            instance = (ContratoC) contratoCSrv.crearImplementation(step11Request).getInstance();
        }

        com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsContratoCASolicitudRequest step2Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsContratoCASolicitudRequest();
        step2Request.setContratoCInsContratoCASolicitudpthisContratoCInstance(instance);
        step2Request.setContratoCInsContratoCASolicitudpevcSolicitudInstance(contratoCTNEWDESDEAEptSolicitud);
        contratoCSrv.insContratoCASolicitudImplementation(step2Request, true);

        /* Action 3: IF (ptSolicitud.Aesolicitudes.Tiposolicitud = "E") */
        boolean ifCondition3 = (contratoCTNEWDESDEAEptSolicitud.getAesolicitudes().getAesolicitudesTiposolicitud()).equals("E");
        if (ifCondition3) {
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TCREAROMODDIRDEAERequest step30Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TCREAROMODDIRDEAERequest();
            step30Request.setContratoCTCREAROMODDIRDEAEpthisContratoCInstance(instance);
            step30Request.setContratoCTCREAROMODDIRDEAEpSolicitudAEInstance(contratoCTNEWDESDEAEptSolicitud);
            contratoCSrv.tCREAROMODDIRDEAEImplementation(step30Request);
            com.keralty.aegenerasolicitud.viewmodel.personac.siu.TNEWCONTRADESDEAERequest step31Request = new com.keralty.aegenerasolicitud.viewmodel.personac.siu.TNEWCONTRADESDEAERequest();
            step31Request.setPersonaCTNEWCONTRADESDEAEptSolicitudInstance(contratoCTNEWDESDEAEptSolicitud);
            step31Request.setPersonaCTNEWCONTRADESDEAEptContratoInstance(instance);
            personaCSrv.tNEWCONTRADESDEAEImplementation(step31Request);
            /* Action 32: IF (ptSolicitud.observacions <> NULL) */
            boolean ifCondition32 = contratoCTNEWDESDEAEptSolicitud.getSolicitudobservacions() != null;
            if (ifCondition32) {
                com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TCREAROBS100Request step320Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TCREAROBS100Request();
                step320Request.setContratoCTCREAROBS100pthisContratoCInstance(instance);
                step320Request.setContratoCTCREAROBS100pObsText(contratoCTNEWDESDEAEptSolicitud.getSolicitudobservacions());
                contratoCSrv.tCREAROBS100Implementation(step320Request);
            }
            com.keralty.aegenerasolicitud.viewmodel.familiac.siu.TNEWDESDEAERequest step33Request = new com.keralty.aegenerasolicitud.viewmodel.familiac.siu.TNEWDESDEAERequest();
            step33Request.setFamiliaCTNEWDESDEAEptContratoCInstance(instance);
            step33Request.setFamiliaCTNEWDESDEAEptSolicitudInstance(contratoCTNEWDESDEAEptSolicitud);
            step33Request.setFamiliaCTNEWDESDEAEptNumFamiliaCORE(contratoCTNEWDESDEAEptNumFamiliaCORE);
            familiaCSrv.tNEWDESDEAEImplementation(step33Request);
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CalcularLiquidacionRequest step34Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CalcularLiquidacionRequest();
            step34Request.setContratoCCalcularLiquidacionpthisContratoCInstance(instance);
            contratoCSrv.calcularLiquidacionImplementation(step34Request);
        } else {
            com.keralty.aegenerasolicitud.viewmodel.familiac.siu.CrearRequest step35Request = new com.keralty.aegenerasolicitud.viewmodel.familiac.siu.CrearRequest();
            step35Request.setFamiliaCcrearpagrPersonaCTitularInstance(null);
            step35Request.setFamiliaCcrearpagrContratoCInstance(instance);
            step35Request.setFamiliaCcrearpagrMunicipioCInstance(null);
            step35Request.setFamiliaCcrearpatrTitularConBeneficios(Boolean.FALSE);
            step35Request.setFamiliaCcrearpatrdireccionCorrespondencia(null);
            step35Request.setFamiliaCcrearpatrEstadoTitularFamilia(null);
            step35Request.setFamiliaCcrearpatrFechaFinVigencia(null);
            step35Request.setFamiliaCcrearpatrNumFamilia(null);
            familiaCSrv.crearImplementation(step35Request);
            
        }

        response.setInstance(getByOID(instance.getOid()));

        // Value expressions for outgoing argument 'contratoCTNEWDESDEAEpsContratoC'
        // DEFAULT  : THIS
        response.setContratoCTNEWDESDEAEpsContratoCInstance(instance);


        // Value expressions for outgoing argument 'contratoCTNEWDESDEAEpsNumContrato'
        // CONDITION: NumContrato <> NULL
        // EFFECT   : NumContrato
        if (instance.getContratoCNumContrato() != null) {
            response.setContratoCTNEWDESDEAEpsNumContrato(instance.getContratoCNumContrato());
        }
         else {
            // DEFAULT  : "0"
            response.setContratoCTNEWDESDEAEpsNumContrato("0");
        }


        return response;
    }

    // Service: TPOSTNOTICREAR

    /*
     * Entry point for service TPOSTNOTICREAR
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tPOSTNOTICREAR(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TPOSTNOTICREARRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = contratoCSrv.tPOSTNOTICREARTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TPOSTNOTICREAR
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tPOSTNOTICREARTxn(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TPOSTNOTICREARRequest request) throws ModelException, SystemException {
        request.setContratoCTPOSTNOTICREARpthisContratoCInstance(contratoCSrv.getByOIDWithHV(request.getContratoCTPOSTNOTICREARpthisContratoC()));
        request.setContratoCTPOSTNOTICREARptAsesorSolicitudInstance(asesorSrv.getByOIDWithHV(request.getContratoCTPOSTNOTICREARptAsesorSolicitud()));
        request.setContratoCTPOSTNOTICREARptPeriodicidadInstance(periodicidadSrv.getByOIDWithHV(request.getContratoCTPOSTNOTICREARptPeriodicidad()));
        request.setContratoCTPOSTNOTICREARptProductoInstance(productosSrv.getByOIDWithHV(request.getContratoCTPOSTNOTICREARptProducto()));
        request.setContratoCTPOSTNOTICREARptPlanInstance(planesSrv.getByOIDWithHV(request.getContratoCTPOSTNOTICREARptPlan()));
        request.setContratoCTPOSTNOTICREARptMunicipioDireccionInstance(municipioSrv.getByOIDWithHV(request.getContratoCTPOSTNOTICREARptMunicipioDireccion()));
        request.setContratoCTPOSTNOTICREARptTipoIdContratanteInstance(tipoIdentificacionSrv.getByOIDWithHV(request.getContratoCTPOSTNOTICREARptTipoIdContratante()));
        request.setContratoCTPOSTNOTICREARptTipoIdTitularInstance(tipoIdentificacionSrv.getByOIDWithHV(request.getContratoCTPOSTNOTICREARptTipoIdTitular()));
        request.setContratoCTPOSTNOTICREARptMunicipioDireccionTitularInstance(municipioSrv.getByOIDWithHV(request.getContratoCTPOSTNOTICREARptMunicipioDireccionTitular()));
        return contratoCSrv.tPOSTNOTICREARImplementation(request);
    }

    /*
     * Implements service TPOSTNOTICREAR
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tPOSTNOTICREARImplementation(
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TPOSTNOTICREARRequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        String contratoCTPOSTNOTICREARptpeNumContrato = request.getContratoCTPOSTNOTICREARptpeNumContrato();
        Long contratoCTPOSTNOTICREARptCodTipoContrato = request.getContratoCTPOSTNOTICREARptCodTipoContrato();
        Date contratoCTPOSTNOTICREARptpeFechaIniVigencia = request.getContratoCTPOSTNOTICREARptpeFechaIniVigencia();
        String contratoCTPOSTNOTICREARptDireccionCompletaContrato = request.getContratoCTPOSTNOTICREARptDireccionCompletaContrato();
        String contratoCTPOSTNOTICREARptTelefonoContratante = request.getContratoCTPOSTNOTICREARptTelefonoContratante();
        String contratoCTPOSTNOTICREARptCelularContratante = request.getContratoCTPOSTNOTICREARptCelularContratante();
        Date contratoCTPOSTNOTICREARptpeFechaFinVigencia = request.getContratoCTPOSTNOTICREARptpeFechaFinVigencia();
        TipoIdentificacion contratoCTPOSTNOTICREARptTipoIdContratante = request.getContratoCTPOSTNOTICREARptTipoIdContratanteInstance();
        String contratoCTPOSTNOTICREARptNumIdentificacionContratante = request.getContratoCTPOSTNOTICREARptNumIdentificacionContratante();
        String contratoCTPOSTNOTICREARptNombreCompletoContratante = request.getContratoCTPOSTNOTICREARptNombreCompletoContratante();
        Municipio contratoCTPOSTNOTICREARptMunicipioDireccion = request.getContratoCTPOSTNOTICREARptMunicipioDireccionInstance();
        Asesor contratoCTPOSTNOTICREARptAsesorSolicitud = request.getContratoCTPOSTNOTICREARptAsesorSolicitudInstance();
        Periodicidad contratoCTPOSTNOTICREARptPeriodicidad = request.getContratoCTPOSTNOTICREARptPeriodicidadInstance();
        Productos contratoCTPOSTNOTICREARptProducto = request.getContratoCTPOSTNOTICREARptProductoInstance();
        Planes contratoCTPOSTNOTICREARptPlan = request.getContratoCTPOSTNOTICREARptPlanInstance();
        String contratoCTPOSTNOTICREARptNumFamilia = request.getContratoCTPOSTNOTICREARptNumFamilia();
        String contratoCTPOSTNOTICREARptDireccionCompletaTitular = request.getContratoCTPOSTNOTICREARptDireccionCompletaTitular();
        Municipio contratoCTPOSTNOTICREARptMunicipioDireccionTitular = request.getContratoCTPOSTNOTICREARptMunicipioDireccionTitularInstance();
        TipoIdentificacion contratoCTPOSTNOTICREARptTipoIdTitular = request.getContratoCTPOSTNOTICREARptTipoIdTitularInstance();
        String contratoCTPOSTNOTICREARptNumIdentificacionTitular = request.getContratoCTPOSTNOTICREARptNumIdentificacionTitular();
        String contratoCTPOSTNOTICREARptNombreCompletoTitular = request.getContratoCTPOSTNOTICREARptNombreCompletoTitular();
        ContratoC contratoCTPOSTNOTICREARpthisContratoC = request.getContratoCTPOSTNOTICREARpthisContratoCInstance();

        request.checkArguments();
        ContratoC instance = contratoCTPOSTNOTICREARpthisContratoC;

        com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.EvalPostNotifCrearRequest step1Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.EvalPostNotifCrearRequest();
        step1Request.setContratoCevalPostNotifCrearpthisContratoCInstance(contratoCTPOSTNOTICREARpthisContratoC);
        step1Request.setContratoCevalPostNotifCrearpNumContrato(contratoCTPOSTNOTICREARptpeNumContrato);
        step1Request.setContratoCevalPostNotifCrearpTipoContrato(contratoCTPOSTNOTICREARptCodTipoContrato);
        step1Request.setContratoCevalPostNotifCrearpFechaIniVigencia(contratoCTPOSTNOTICREARptpeFechaIniVigencia);
        step1Request.setContratoCevalPostNotifCrearpDireccion(contratoCTPOSTNOTICREARptDireccionCompletaContrato);
        step1Request.setContratoCevalPostNotifCrearpnumTelContratante(contratoCTPOSTNOTICREARptTelefonoContratante);
        step1Request.setContratoCevalPostNotifCrearpnumCelContratante(contratoCTPOSTNOTICREARptCelularContratante);
        step1Request.setContratoCevalPostNotifCrearpFechaFinVigencia(contratoCTPOSTNOTICREARptpeFechaFinVigencia);
        contratoCSrv.evalPostNotifCrearImplementation(step1Request);

        /* Action 2: IF (ptTipoIdContratante <> NULL AND ptNumIdentificacionContratante <> NULL) */
        boolean ifCondition2 = (contratoCTPOSTNOTICREARptTipoIdContratante != null && !contratoCTPOSTNOTICREARptTipoIdContratante.isNull()) && contratoCTPOSTNOTICREARptNumIdentificacionContratante != null;
        if (ifCondition2) {
            /* Action 20: IF (EXIST (PersonaCContratante) = true) */
            boolean ifCondition20 = repository.assocOperator033(contratoCTPOSTNOTICREARpthisContratoC).equals(true);
            if (ifCondition20) {
                for(PersonaC step2200Entity : repository.assocOperator028(contratoCTPOSTNOTICREARpthisContratoC)) {
                    com.keralty.aegenerasolicitud.viewmodel.personac.siu.TMODIFICARRequest step200Request = new com.keralty.aegenerasolicitud.viewmodel.personac.siu.TMODIFICARRequest();
                    step200Request.setPersonaCTMODIFICARpthisPersonaCInstance(step2200Entity);
                    step200Request.setPersonaCTMODIFICARptpeNombreCompleto(contratoCTPOSTNOTICREARptNombreCompletoContratante);
                    step200Request.setPersonaCTMODIFICARptpeNumIdent(contratoCTPOSTNOTICREARptNumIdentificacionContratante);
                    step200Request.setPersonaCTMODIFICARptpevcTipoIdentificacionInstance(contratoCTPOSTNOTICREARptTipoIdContratante);
                    personaCSrv.tMODIFICARImplementation(step200Request);
                }
            } else {
                com.keralty.aegenerasolicitud.viewmodel.personac.siu.TNEWRequest step201Request = new com.keralty.aegenerasolicitud.viewmodel.personac.siu.TNEWRequest();
                step201Request.setPersonaCTNEWptpagrTipoInstitucionCInstance(null);
                step201Request.setPersonaCTNEWptpagrTipoEntidadCInstance(null);
                step201Request.setPersonaCTNEWptpagrEstadoCivilCInstance(null);
                step201Request.setPersonaCTNEWptpagrEstadoPersonaCInstance(null);
                step201Request.setPersonaCTNEWptpagrTipoIdentificacionCInstance(contratoCTPOSTNOTICREARptTipoIdContratante);
                step201Request.setPersonaCTNEWptpagrMunicipioCNacInstance(null);
                step201Request.setPersonaCTNEWptpagrDepartamentoCNacInstance(null);
                step201Request.setPersonaCTNEWptpagrContratosCInstance(contratoCTPOSTNOTICREARpthisContratoC);
                step201Request.setPersonaCTNEWptpagrFamiliasCInstance(null);
                step201Request.setPersonaCTNEWptpagrBeneficiariosCInstance(null);
                step201Request.setPersonaCTNEWptpatrNumIdentificacion(contratoCTPOSTNOTICREARptNumIdentificacionContratante);
                step201Request.setPersonaCTNEWptpatrMail(null);
                step201Request.setPersonaCTNEWptpatrPrimerNombre(null);
                step201Request.setPersonaCTNEWptpatrSegundoNombre(null);
                step201Request.setPersonaCTNEWptpatrPrimerApellido(null);
                step201Request.setPersonaCTNEWptpatrSegundoApellido(null);
                step201Request.setPersonaCTNEWptpatrFechaNacimiento(null);
                step201Request.setPersonaCTNEWptpatrGenero(null);
                step201Request.setPersonaCTNEWptpatrIndicadorExtranjero(Boolean.FALSE);
                step201Request.setPersonaCTNEWptpatrRazonSocial(null);
                step201Request.setPersonaCTNEWptpatrTieneEPSSanitas(Boolean.FALSE);
                step201Request.setPersonaCTNEWptpatrRechazadoAfiliacion(null);
                step201Request.setPersonaCTNEWptpatrNombreCompleto(contratoCTPOSTNOTICREARptNombreCompletoContratante);
                step201Request.setPersonaCTNEWptpatrCodigo(null);
                personaCSrv.tNEWImplementation(step201Request);
            }
        }

        /* Action 3: IF (MunicipioC <> ptMunicipioDireccion) */
        boolean ifCondition3 = !(contratoCTPOSTNOTICREARpthisContratoC.getMunicipioC().equalsObject(contratoCTPOSTNOTICREARptMunicipioDireccion));
        if (ifCondition3) {
            /* Action 30: IF (ptMunicipioDireccion <> NULL) */
            boolean ifCondition30 = (contratoCTPOSTNOTICREARptMunicipioDireccion != null && !contratoCTPOSTNOTICREARptMunicipioDireccion.isNull());
            if (ifCondition30) {
                /* Action 300: IF (EXIST (MunicipioC) = true) */
                boolean ifCondition300 = repository.assocOperator034(contratoCTPOSTNOTICREARpthisContratoC).equals(true);
                if (ifCondition300) {
                    com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelMunicipioContratoRequest step3000Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelMunicipioContratoRequest();
                    step3000Request.setContratoCDelMunicipioContratopthisContratoCInstance(contratoCTPOSTNOTICREARpthisContratoC);
                    step3000Request.setContratoCDelMunicipioContratopevcMunicipioInstance(contratoCTPOSTNOTICREARpthisContratoC.getMunicipioC());
                    contratoCSrv.delMunicipioContratoImplementation(step3000Request, true);
                }
                com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsMunicipioContratoRequest step301Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsMunicipioContratoRequest();
                step301Request.setContratoCInsMunicipioContratopthisContratoCInstance(contratoCTPOSTNOTICREARpthisContratoC);
                step301Request.setContratoCInsMunicipioContratopevcMunicipioInstance(contratoCTPOSTNOTICREARptMunicipioDireccion);
                contratoCSrv.insMunicipioContratoImplementation(step301Request, true);
            }
        }

        /* Action 4: IF (AsesorC <> ptAsesorSolicitud) */
        boolean ifCondition4 = !(contratoCTPOSTNOTICREARpthisContratoC.getAsesorC().equalsObject(contratoCTPOSTNOTICREARptAsesorSolicitud));
        if (ifCondition4) {
            /* Action 40: IF (ptAsesorSolicitud <> NULL) */
            boolean ifCondition40 = (contratoCTPOSTNOTICREARptAsesorSolicitud != null && !contratoCTPOSTNOTICREARptAsesorSolicitud.isNull());
            if (ifCondition40) {
                /* Action 400: IF (EXIST (AsesorC) = true) */
                boolean ifCondition400 = repository.assocOperator035(contratoCTPOSTNOTICREARpthisContratoC).equals(true);
                if (ifCondition400) {
                    com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelAsesorCRequest step4000Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelAsesorCRequest();
                    step4000Request.setContratoCDelAsesorCpthisContratoCInstance(contratoCTPOSTNOTICREARpthisContratoC);
                    step4000Request.setContratoCDelAsesorCpevcAsesorInstance(contratoCTPOSTNOTICREARpthisContratoC.getAsesorC());
                    contratoCSrv.delAsesorCImplementation(step4000Request, true);
                }
                com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsAsesorCRequest step401Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsAsesorCRequest();
                step401Request.setContratoCInsAsesorCpthisContratoCInstance(contratoCTPOSTNOTICREARpthisContratoC);
                step401Request.setContratoCInsAsesorCpevcAsesorInstance(contratoCTPOSTNOTICREARptAsesorSolicitud);
                contratoCSrv.insAsesorCImplementation(step401Request, true);
            }
        }

        /* Action 5: IF (PeriodicidadC <> ptPeriodicidad) */
        boolean ifCondition5 = !(contratoCTPOSTNOTICREARpthisContratoC.getPeriodicidadC().equalsObject(contratoCTPOSTNOTICREARptPeriodicidad));
        if (ifCondition5) {
            /* Action 50: IF (ptPeriodicidad <> NULL) */
            boolean ifCondition50 = (contratoCTPOSTNOTICREARptPeriodicidad != null && !contratoCTPOSTNOTICREARptPeriodicidad.isNull());
            if (ifCondition50) {
                /* Action 500: IF (EXIST (PeriodicidadC) = true) */
                boolean ifCondition500 = repository.assocOperator036(contratoCTPOSTNOTICREARpthisContratoC).equals(true);
                if (ifCondition500) {
                    com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelPeriodicidadRequest step5000Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelPeriodicidadRequest();
                    step5000Request.setContratoCDelPeriodicidadpthisContratoCInstance(contratoCTPOSTNOTICREARpthisContratoC);
                    step5000Request.setContratoCDelPeriodicidadpevcPeriodicidadInstance(contratoCTPOSTNOTICREARpthisContratoC.getPeriodicidadC());
                    contratoCSrv.delPeriodicidadImplementation(step5000Request, true);
                }
                com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsPeriodicidadRequest step501Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsPeriodicidadRequest();
                step501Request.setContratoCInsPeriodicidadpthisContratoCInstance(contratoCTPOSTNOTICREARpthisContratoC);
                step501Request.setContratoCInsPeriodicidadpevcPeriodicidadInstance(contratoCTPOSTNOTICREARptPeriodicidad);
                contratoCSrv.insPeriodicidadImplementation(step501Request, true);
            }
        }

        /* Action 6: IF (ProductoC <> ptProducto) */
        boolean ifCondition6 = !(contratoCTPOSTNOTICREARpthisContratoC.getProductoC().equalsObject(contratoCTPOSTNOTICREARptProducto));
        if (ifCondition6) {
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.ChgProductoCRequest step60Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.ChgProductoCRequest();
            step60Request.setContratoCchgProductoCpthisContratoCInstance(contratoCTPOSTNOTICREARpthisContratoC);
            step60Request.setContratoCchgProductoCpevcProductosInstance(contratoCTPOSTNOTICREARptProducto);
            contratoCSrv.chgProductoCImplementation(step60Request, true);
        }

        /* Action 7: IF (PlanC <> ptPlan) */
        boolean ifCondition7 = !(contratoCTPOSTNOTICREARpthisContratoC.getPlanC().equalsObject(contratoCTPOSTNOTICREARptPlan));
        if (ifCondition7) {
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.ChgPlanCRequest step70Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.ChgPlanCRequest();
            step70Request.setContratoCchgPlanCpthisContratoCInstance(contratoCTPOSTNOTICREARpthisContratoC);
            step70Request.setContratoCchgPlanCpevcPlanesInstance(contratoCTPOSTNOTICREARptPlan);
            contratoCSrv.chgPlanCImplementation(step70Request, true);
        }

        for(FamiliaC step8Entity : repository.assocOperator032(contratoCTPOSTNOTICREARpthisContratoC)) {
            com.keralty.aegenerasolicitud.viewmodel.familiac.siu.TPOSTNOTICREARRequest step8Request = new com.keralty.aegenerasolicitud.viewmodel.familiac.siu.TPOSTNOTICREARRequest();
            step8Request.setFamiliaCTPOSTNOTICREARpthisFamiliaCInstance(step8Entity);
            step8Request.setFamiliaCTPOSTNOTICREARptpeNumFamilia(contratoCTPOSTNOTICREARptNumFamilia);
            step8Request.setFamiliaCTPOSTNOTICREARptpeFechaFinVigencia(contratoCTPOSTNOTICREARptpeFechaFinVigencia);
            step8Request.setFamiliaCTPOSTNOTICREARptpeDirCorresp(contratoCTPOSTNOTICREARptDireccionCompletaTitular);
            step8Request.setFamiliaCTPOSTNOTICREARptMunicipioDireccionInstance(contratoCTPOSTNOTICREARptMunicipioDireccionTitular);
            step8Request.setFamiliaCTPOSTNOTICREARptTipoIdentTitularInstance(contratoCTPOSTNOTICREARptTipoIdTitular);
            step8Request.setFamiliaCTPOSTNOTICREARptNumIdentTitular(contratoCTPOSTNOTICREARptNumIdentificacionTitular);
            step8Request.setFamiliaCTPOSTNOTICREARptNombreCompletoTitular(contratoCTPOSTNOTICREARptNombreCompletoTitular);
            familiaCSrv.tPOSTNOTICREARImplementation(step8Request);
        }

        response.setInstance(getByOID(instance.getOid()));

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
        select.getOrderByPart().addItem(Constants.normalizePath(ContratoCConstants.CLASS_NAME, ContratoCConstants.ATTR_FIELD_CONTRATOCID, false), true);
    }
    
    @Override
    protected AbstractAppEntity buildRelatedInstance(String relatedInstanceJsonOid, String relatedInstancePathFirstItem, String remainingPath) {
        AbstractAppEntity relatedInstance = null;
        if (relatedInstancePathFirstItem.isEmpty()) {
            relatedInstance = buildContratoCRelatedInstance(relatedInstanceJsonOid);
        }
        if (ContratoCConstants.ROLE_NAME_SOLICITUD.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4Solicitud(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_PERSONACCONTRATANTE.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4PersonaC(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_DIRECCIONESC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4DireccionC(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_OBSERVACIONESC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4ObservacionesC(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_BENEFICIARIOSC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4BeneficiarioC(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_FAMILIASC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4FamiliaC(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_PERIODICIDADC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4Periodicidad(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_FORMAPAGOC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4FormaPago(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_ESTADOCONTRATOC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4EstadoContrato(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_PRODUCTOC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4Productos(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_PLANC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4Planes(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_TARIFAC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4Tarifa(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_GRUPOASOCIADOC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4GrupoAsociado(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_MUNICIPIOC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4Municipio(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_SUCURSALC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4Sucursal(relatedInstanceJsonOid, remainingPath);
        }
        if (ContratoCConstants.ROLE_NAME_ASESORC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildContratoCRelatedInstance4Asesor(relatedInstanceJsonOid, remainingPath);
        }
        return relatedInstance;
    }
    
    private AbstractAppEntity buildContratoCRelatedInstance(String relatedInstanceJsonOid) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		return new ContratoC(mapper.readValue(relatedInstanceJsonOid, ContratoCOid.class));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4Solicitud(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Solicitud(mapper.readValue(relatedInstanceJsonOid, SolicitudOid.class)): Solicitud.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4PersonaC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new PersonaC(mapper.readValue(relatedInstanceJsonOid, PersonaCOid.class)): PersonaC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4DireccionC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new DireccionC(mapper.readValue(relatedInstanceJsonOid, DireccionCOid.class)): DireccionC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4ObservacionesC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new ObservacionesC(mapper.readValue(relatedInstanceJsonOid, ObservacionesCOid.class)): ObservacionesC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4BeneficiarioC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new BeneficiarioC(mapper.readValue(relatedInstanceJsonOid, BeneficiarioCOid.class)): BeneficiarioC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4FamiliaC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new FamiliaC(mapper.readValue(relatedInstanceJsonOid, FamiliaCOid.class)): FamiliaC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4Periodicidad(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Periodicidad(mapper.readValue(relatedInstanceJsonOid, PeriodicidadOid.class)): Periodicidad.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4FormaPago(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new FormaPago(mapper.readValue(relatedInstanceJsonOid, FormaPagoOid.class)): FormaPago.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4EstadoContrato(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new EstadoContrato(mapper.readValue(relatedInstanceJsonOid, EstadoContratoOid.class)): EstadoContrato.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4Productos(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Productos(mapper.readValue(relatedInstanceJsonOid, ProductosOid.class)): Productos.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4Planes(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Planes(mapper.readValue(relatedInstanceJsonOid, PlanesOid.class)): Planes.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4Tarifa(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Tarifa(mapper.readValue(relatedInstanceJsonOid, TarifaOid.class)): Tarifa.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4GrupoAsociado(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new GrupoAsociado(mapper.readValue(relatedInstanceJsonOid, GrupoAsociadoOid.class)): GrupoAsociado.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4Municipio(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Municipio(mapper.readValue(relatedInstanceJsonOid, MunicipioOid.class)): Municipio.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4Sucursal(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Sucursal(mapper.readValue(relatedInstanceJsonOid, SucursalOid.class)): Sucursal.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildContratoCRelatedInstance4Asesor(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Asesor(mapper.readValue(relatedInstanceJsonOid, AsesorOid.class)): Asesor.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            ContratoCOid oid = new ContratoCOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(ContratoCConstants.ATTR_FIELD_CONTRATOCID, Constants.Type.AUTONUMERIC.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getContratoCID(), Constants.Type.AUTONUMERIC.getTypeName()));
            sql.addComparison(comparison1);
        } catch (Exception e) {
            LoggerFactory.getLogger(ContratoCService.class).error(e.getMessage());
        }
    }

}
