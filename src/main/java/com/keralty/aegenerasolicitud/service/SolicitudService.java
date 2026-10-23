package com.keralty.aegenerasolicitud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralty.aegenerasolicitud.exception.ModelException;
import com.keralty.aegenerasolicitud.exception.PreconditionException;
import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.AesolicitudesConstants;
import com.keralty.aegenerasolicitud.global.AfiliacionesConstants;
import com.keralty.aegenerasolicitud.global.AreaMedicaConstants;
import com.keralty.aegenerasolicitud.global.AsesorConstants;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.ContratanteConstants;
import com.keralty.aegenerasolicitud.global.FuncionarioConstants;
import com.keralty.aegenerasolicitud.global.MesaDeApoyoConstants;
import com.keralty.aegenerasolicitud.global.PersonaConstants;
import com.keralty.aegenerasolicitud.global.PlanesConstants;
import com.keralty.aegenerasolicitud.global.ProductosConstants;
import com.keralty.aegenerasolicitud.global.RegionalConstants;
import com.keralty.aegenerasolicitud.global.SolicitudConstants;
import com.keralty.aegenerasolicitud.global.STDFunctions;
import com.keralty.aegenerasolicitud.global.SucursalConstants;
import com.keralty.aegenerasolicitud.global.TipoIdentificacionConstants;
import com.keralty.aegenerasolicitud.global.TitularConstants;
import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.persistence.AbstractAppEntity;
import com.keralty.aegenerasolicitud.persistence.Aesolicitudes;
import com.keralty.aegenerasolicitud.persistence.Afiliaciones;
import com.keralty.aegenerasolicitud.persistence.AreaMedica;
import com.keralty.aegenerasolicitud.persistence.Asesor;
import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.Contratante;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.Direccion;
import com.keralty.aegenerasolicitud.persistence.DocumentoG;
import com.keralty.aegenerasolicitud.persistence.ErrorWS;
import com.keralty.aegenerasolicitud.persistence.Firma;
import com.keralty.aegenerasolicitud.persistence.FormaPago;
import com.keralty.aegenerasolicitud.persistence.Funcionario;
import com.keralty.aegenerasolicitud.persistence.GrupoAsociado;
import com.keralty.aegenerasolicitud.persistence.LogAsignaciones;
import com.keralty.aegenerasolicitud.persistence.LogEstado;
import com.keralty.aegenerasolicitud.persistence.MesaDeApoyo;
import com.keralty.aegenerasolicitud.persistence.oid.AesolicitudesOid;
import com.keralty.aegenerasolicitud.persistence.oid.AreaMedicaOid;
import com.keralty.aegenerasolicitud.persistence.oid.BeneficiarioCOid;
import com.keralty.aegenerasolicitud.persistence.oid.ContratanteOid;
import com.keralty.aegenerasolicitud.persistence.oid.ContratoCOid;
import com.keralty.aegenerasolicitud.persistence.oid.DireccionOid;
import com.keralty.aegenerasolicitud.persistence.oid.DocumentoGOid;
import com.keralty.aegenerasolicitud.persistence.oid.ErrorWSOid;
import com.keralty.aegenerasolicitud.persistence.oid.FirmaOid;
import com.keralty.aegenerasolicitud.persistence.oid.FormaPagoOid;
import com.keralty.aegenerasolicitud.persistence.oid.FuncionarioOid;
import com.keralty.aegenerasolicitud.persistence.oid.GrupoAsociadoOid;
import com.keralty.aegenerasolicitud.persistence.oid.LogAsignacionesOid;
import com.keralty.aegenerasolicitud.persistence.oid.LogEstadoOid;
import com.keralty.aegenerasolicitud.persistence.oid.PeriodicidadOid;
import com.keralty.aegenerasolicitud.persistence.oid.PersonaOid;
import com.keralty.aegenerasolicitud.persistence.oid.PlanesOid;
import com.keralty.aegenerasolicitud.persistence.oid.ProductosOid;
import com.keralty.aegenerasolicitud.persistence.oid.ProteccionDatosOid;
import com.keralty.aegenerasolicitud.persistence.oid.RegionalOid;
import com.keralty.aegenerasolicitud.persistence.oid.SolicitudOid;
import com.keralty.aegenerasolicitud.persistence.oid.TipoFacturaOid;
import com.keralty.aegenerasolicitud.persistence.oid.TipoIdentificacionOid;
import com.keralty.aegenerasolicitud.persistence.oid.TitularOid;
import com.keralty.aegenerasolicitud.persistence.oid.UsuarioOid;
import com.keralty.aegenerasolicitud.persistence.oid.UsuarioRegOid;
import com.keralty.aegenerasolicitud.persistence.Periodicidad;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.Planes;
import com.keralty.aegenerasolicitud.persistence.Productos;
import com.keralty.aegenerasolicitud.persistence.ProteccionDatos;
import com.keralty.aegenerasolicitud.persistence.Regional;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.TipoFactura;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import com.keralty.aegenerasolicitud.persistence.Titular;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import com.keralty.aegenerasolicitud.persistence.UsuarioReg;
import com.keralty.aegenerasolicitud.repository.SolicitudJpaRepository;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereComparison;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereConjunction;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereDisjunction;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandLiteral;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandNull;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandOVParameter;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandPathAttribute;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandPathInstance;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandSubQuery;
import com.keralty.aegenerasolicitud.utils.Utilities;
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
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.LoggerFactory;

@Transactional
@ApplicationScoped
public class SolicitudService extends AbstractService {

    SolicitudJpaRepository repository;

    SolicitudService solicitudSrv;

    ContratoCService contratoCSrv;

    AesolicitudesService aesolicitudesSrv;

    BeneficiarioCService beneficiarioCSrv;

    PersonaCService personaCSrv;

    EstadoBeneficiarioService estadoBeneficiarioSrv;

    UsuarioService usuarioSrv;

    TipoIdentificacionService tipoIdentificacionSrv;

    PlanesService planesSrv;

    DominioService dominioSrv;

    ProductosService productosSrv;

    CuestionarioPService cuestionarioPSrv;

    DocumentoGService documentoGSrv;

    LogEstadoService logEstadoSrv;

    LogAsignacionesService logAsignacionesSrv;

    RegionalService regionalSrv;

    PersonaService personaSrv;

    DireccionService direccionSrv;

    ProteccionDatosService proteccionDatosSrv;

    ContratanteService contratanteSrv;

    FirmaService firmaSrv;

    UsuarioRegService usuarioRegSrv;

    TitularService titularSrv;

    ErrorWSService errorWSSrv;

    GrupoAsociadoService grupoAsociadoSrv;

    FormaPagoService formaPagoSrv;

    PeriodicidadService periodicidadSrv;

    TipoFacturaService tipoFacturaSrv;

    UserFunctions userFunctionsSrv;

    @RestClient
    SolicitudServiceAEEnvioDoc solicitudServiceAEEnvioDoc;

    @Inject
    public SolicitudService(
            SolicitudService solicitudSrv,
            ContratoCService contratoCSrv,
            AesolicitudesService aesolicitudesSrv,
            BeneficiarioCService beneficiarioCSrv,
            PersonaCService personaCSrv,
            EstadoBeneficiarioService estadoBeneficiarioSrv,
            UsuarioService usuarioSrv,
            TipoIdentificacionService tipoIdentificacionSrv,
            PlanesService planesSrv,
            DominioService dominioSrv,
            ProductosService productosSrv,
            CuestionarioPService cuestionarioPSrv,
            DocumentoGService documentoGSrv,
            LogEstadoService logEstadoSrv,
            LogAsignacionesService logAsignacionesSrv,
            RegionalService regionalSrv,
            PersonaService personaSrv,
            DireccionService direccionSrv,
            ProteccionDatosService proteccionDatosSrv,
            ContratanteService contratanteSrv,
            FirmaService firmaSrv,
            UsuarioRegService usuarioRegSrv,
            TitularService titularSrv,
            ErrorWSService errorWSSrv,
            GrupoAsociadoService grupoAsociadoSrv,
            FormaPagoService formaPagoSrv,
            PeriodicidadService periodicidadSrv,
            TipoFacturaService tipoFacturaSrv,
            UserFunctions userFunctionsSrv,
            SolicitudJpaRepository repository) {
        this.solicitudSrv = solicitudSrv;
        this.contratoCSrv = contratoCSrv;
        this.aesolicitudesSrv = aesolicitudesSrv;
        this.beneficiarioCSrv = beneficiarioCSrv;
        this.personaCSrv = personaCSrv;
        this.estadoBeneficiarioSrv = estadoBeneficiarioSrv;
        this.usuarioSrv = usuarioSrv;
        this.tipoIdentificacionSrv = tipoIdentificacionSrv;
        this.planesSrv = planesSrv;
        this.dominioSrv = dominioSrv;
        this.productosSrv = productosSrv;
        this.cuestionarioPSrv = cuestionarioPSrv;
        this.documentoGSrv = documentoGSrv;
        this.logEstadoSrv = logEstadoSrv;
        this.logAsignacionesSrv = logAsignacionesSrv;
        this.regionalSrv = regionalSrv;
        this.personaSrv = personaSrv;
        this.direccionSrv = direccionSrv;
        this.proteccionDatosSrv = proteccionDatosSrv;
        this.contratanteSrv = contratanteSrv;
        this.firmaSrv = firmaSrv;
        this.usuarioRegSrv = usuarioRegSrv;
        this.titularSrv = titularSrv;
        this.errorWSSrv = errorWSSrv;
        this.grupoAsociadoSrv = grupoAsociadoSrv;
        this.formaPagoSrv = formaPagoSrv;
        this.periodicidadSrv = periodicidadSrv;
        this.tipoFacturaSrv = tipoFacturaSrv;
        this.userFunctionsSrv = userFunctionsSrv;
        this.repository = repository;
    }

    public List<Map<String, Object>> solveQuery(QueryRequest request) {
        return solveGenericQuery(request, SolicitudConstants.CLASS_NAME);
    }

    private List<Solicitud> solveSolicitudTypedQuery(QueryRequest request) {
        boolean hasInMemoryFilters = hasInMemoryFilters(request);
        int requestedPageSize = request.getPageSize();
        int pageSize = requestedPageSize >= 0
                && requestedPageSize <= Constants.MAX_BLOCKSIZE
                ? requestedPageSize
                : Constants.DEFAULT_BLOCKSIZE;
        int pageNumber = Math.max(1, request.getPageNumber());

    	List<Solicitud> results = repository.solveQuery(processQueryRequest(request, SolicitudConstants.CLASS_NAME), (hasInMemoryFilters ? 0 : pageSize), (hasInMemoryFilters ? 1 : pageNumber)).stream()
                .filter(instance -> hasInMemoryFilters ? applyMemoryFilters(request, instance) : true).collect(Collectors.toList()); 

        if (hasInMemoryFilters && results != null && !results.isEmpty()) {
            long fromInstance = ((long) pageNumber - 1) * pageSize;
            long toInstance = (long) pageNumber * pageSize;
            if (pageSize == 0) {
               fromInstance = 0;
               toInstance = results.size();
            }
            int from = (int) Math.min(fromInstance, results.size());
            int to = (int) Math.min(toInstance, results.size());
            results = (fromInstance > results.size() - 1)
                    ? new ArrayList<>()
                    : results.subList(from, Math.max(from, to));
        }

        return results;
    }
    
    private int solveSolicitudTypedCountQuery(QueryRequest request) {
        return hasInMemoryFilters(request)
               ? repository.solveQuery(processQueryRequest(request, SolicitudConstants.CLASS_NAME)).stream().filter(instance -> applyMemoryFilters(request, instance)).collect(Collectors.toList()).size()
               : repository.solveCountQuery(processCountQueryRequest(request, SolicitudConstants.CLASS_NAME));
    }

    private boolean hasInMemoryFilters(QueryRequest queryRequest) {
        for(FilterRequest filterRequest : queryRequest.getFilters()) {
            if (hasInMemoryConjunctions(filterRequest)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasInMemoryConjunctions(FilterRequest filterRequest) {
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDLEGALIZACOMISIONES.equalsIgnoreCase(filterRequest.getName())) {
            return true;
        }
        return false;
    }

    /*
     * Returns the population of class Solicitud
     *
     * @return Population of class Solicitud
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Solicitud> get() {
        List<Solicitud> results = new java.util.ArrayList<>();
        repository.findAll().forEach(results::add);
        return results;
    }
    
    /*
     * Gets the preloaded list of objects of class Solicitud
     *
     * @param queryRequest Request object that dictates the search conditions to 
     * retrieve objects
     *
     * @return Preloaded list of objects of class Solicitud
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PreloadViewModel> getPreload(QueryRequest queryRequest) {
        List<PreloadViewModel> results = new ArrayList<>();
        List<Solicitud> instances = get(queryRequest);
        if(SolicitudConstants.DSDSSOLICITUDPIURADICADO.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudPIURadicado())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDPIUCONSULTARAD.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudPIUConsultaRad())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDPIUENVIODOC.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudPIUEnvioDoc())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDIIUNUEVAAFIL.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudIIUNuevaAfil())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDIIUINFOCONTRATNAT.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudIIUInfoContratNAT())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDIIUINFOCONTRATJUR.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudIIUInfoContratJUR())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDIIUINFOOBSERVACION.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudIIUInfoObservacion())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDIIUINFOPAGO.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudIIUInfoPago())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSICSOLICITUD.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildICSolicitud())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDIIUINCLUSION.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudIIUInclusion())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDVACIA.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudVacia())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDIIUDIFLIQ.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudIIUDifLiq())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDIIUPREEXISTENCIAS.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudIIUPreexistencias())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDPIUSTATUSFULL.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudPIUStatusFull())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSIIUINFOTITULARINCFAM.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSIIUInfoTitularIncFam())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDPIUSTATUSFULLASE.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudPIUStatusFullAse())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDPIUSTATUSADMIN.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudPIUStatusAdmin())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDPIUSTATUSAFIL.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudPIUStatusAfil())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDPIUSTATUSAREAME.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudPIUStatusAreaMe())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDPIUSTATUSASESOR.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudPIUStatusAsesor())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLPIUSTATUSGESTORMESA.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolPIUStatusGestorMesa())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDPIUSTATUSGESTMESA.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudPIUStatusGestMesa())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDINFOCONTRATANTEID.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudInfoContratanteID())).collect(Collectors.toList());
        }
        if(SolicitudConstants.DSDSSOLICITUDIIUINFOCONTRACONTA.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSSolicitudIIUInfoContraConta())).collect(Collectors.toList());
        }
        return results;
    }
    /*
     * Returns the population of class Solicitud that matches certain search criteria
     *
     * @param queryRequest Search criteria
     *
     * @return Population of class Solicitud matching the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Solicitud> get(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest);
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
            return new PopulationResponseViewModel(solveSolicitudTypedQuery(queryRequest).stream()
                    .map(instance -> buildDynamicObject(instance, queryRequest.getDisplaySetItems()))
                    .collect(Collectors.toList()), queryRequest.getPageSize() != 0 ? solveSolicitudTypedCountQuery(queryRequest) : 0);
        } else {
            return new PreloadResponseViewModel(solveSolicitudTypedQuery(queryRequest).stream()
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
        return new PopulationResponseViewModel(solveSolicitudTypedQuery(queryRequest).stream()
                .map(instance -> buildDynamicObject(instance, displaySetItems))
                .collect(Collectors.toList()));
    }

    /*
     * Returns an instance of Solicitud by ID
     *
     * @param aesolicitudesidsolicitud Identification field aesolicitudesidsolicitud
     *
     * @return Instance of Solicitud
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Solicitud getById(Long aesolicitudesidsolicitud) {
        return getByOID(new SolicitudOid(aesolicitudesidsolicitud));
    }
    
    /*
     * Returns an instance of Solicitud by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Solicitud
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Solicitud getByOID(SolicitudOid oid) {
        Optional<Solicitud> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of Solicitud by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Solicitud
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public Solicitud getByOIDWithHV(SolicitudOid oid) {
        Optional<Solicitud> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<Solicitud> getInstanceByOid(SolicitudOid oid) {
        return oid != null && oid.getAesolicitudesidsolicitud() != null
               ? repository.findById(oid.getAesolicitudesidsolicitud())
               : Optional.empty();
    }

    /*
     * Returns an instance of Solicitud by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of Solicitud
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Solicitud getByJsonOid(String jsonOid) {
        SolicitudOid oid = new SolicitudOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }

    /*
     * Returns the list of instances of Solicitud related to a given instance through 
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Solicitud
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Solicitud> getByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        QueryRequest queryRequest = new QueryRequest(new FilterRequest(Constants.FILTER_BY_RELATED,
                Arrays.asList(new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_PATH, relatedInstancePath),
                        new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_OID, relatedInstance.getOid().toJson()))));
        return solveSolicitudTypedQuery(queryRequest);
    }

    /*
     * Returns the list of instances of Solicitud related to a given instance through
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Solicitud
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Solicitud> getInstancesByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        return getByRelated(relatedInstance, relatedInstancePath);
    }

    /*
     * Returns a set of displayset items for an instance of Solicitud
     *
     * @param oid Object IDentifier
     *
     * @param displaySetItems List of comma-separated displayset items
     *
     * @return Set of displayset items for an instance of Solicitud
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public InstanceResponseViewModel getSolicitudDynamic(SolicitudOid oid, String displaySetItems) {
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
    public String getSuppInfo(SolicitudOid oid, String dsName) {
        Solicitud instance = getByOIDWithHV(oid);
        return (instance != null ? getSuppInfo(instance, dsName): "");
    }

    private String getSuppInfo(Solicitud instance, String dsName) {
        if (instance == null || instance.isNull()) return "";
        if (SolicitudConstants.DSICSOLICITUD.equals(dsName))
            return instance.buildICSolicitud();
        if (SolicitudConstants.DSDSSOLICITUDIIUDIFLIQ.equals(dsName))
            return instance.buildDSSolicitudIIUDifLiq();
        return dsName.isEmpty() ? instance.getOid().toJson() : "";
    }

    public static PreloadViewModel buildPreloadViewModel(Solicitud instance, String dsName) {
        if (SolicitudConstants.DSDSSOLICITUDPIURADICADO.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudPIURadicado());
        if (SolicitudConstants.DSDSSOLICITUDPIUCONSULTARAD.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudPIUConsultaRad());
        if (SolicitudConstants.DSDSSOLICITUDPIUENVIODOC.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudPIUEnvioDoc());
        if (SolicitudConstants.DSDSSOLICITUDIIUNUEVAAFIL.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudIIUNuevaAfil());
        if (SolicitudConstants.DSDSSOLICITUDIIUINFOCONTRATNAT.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudIIUInfoContratNAT());
        if (SolicitudConstants.DSDSSOLICITUDIIUINFOCONTRATJUR.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudIIUInfoContratJUR());
        if (SolicitudConstants.DSDSSOLICITUDIIUINFOOBSERVACION.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudIIUInfoObservacion());
        if (SolicitudConstants.DSDSSOLICITUDIIUINFOPAGO.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudIIUInfoPago());
        if (SolicitudConstants.DSICSOLICITUD.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildICSolicitud());
        if (SolicitudConstants.DSDSSOLICITUDIIUINCLUSION.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudIIUInclusion());
        if (SolicitudConstants.DSDSSOLICITUDVACIA.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudVacia());
        if (SolicitudConstants.DSDSSOLICITUDIIUDIFLIQ.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudIIUDifLiq());
        if (SolicitudConstants.DSDSSOLICITUDIIUPREEXISTENCIAS.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudIIUPreexistencias());
        if (SolicitudConstants.DSDSSOLICITUDPIUSTATUSFULL.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudPIUStatusFull());
        if (SolicitudConstants.DSDSIIUINFOTITULARINCFAM.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSIIUInfoTitularIncFam());
        if (SolicitudConstants.DSDSSOLICITUDPIUSTATUSFULLASE.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudPIUStatusFullAse());
        if (SolicitudConstants.DSDSSOLICITUDPIUSTATUSADMIN.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudPIUStatusAdmin());
        if (SolicitudConstants.DSDSSOLICITUDPIUSTATUSAFIL.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudPIUStatusAfil());
        if (SolicitudConstants.DSDSSOLICITUDPIUSTATUSAREAME.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudPIUStatusAreaMe());
        if (SolicitudConstants.DSDSSOLICITUDPIUSTATUSASESOR.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudPIUStatusAsesor());
        if (SolicitudConstants.DSDSSOLPIUSTATUSGESTORMESA.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolPIUStatusGestorMesa());
        if (SolicitudConstants.DSDSSOLICITUDPIUSTATUSGESTMESA.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudPIUStatusGestMesa());
        if (SolicitudConstants.DSDSSOLICITUDINFOCONTRATANTEID.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudInfoContratanteID());
        if (SolicitudConstants.DSDSSOLICITUDIIUINFOCONTRACONTA.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSSolicitudIIUInfoContraConta());
        return new PreloadViewModel(instance.getOid().toJson(), "");
    }


    @Override
    protected void applyQueryFilters(FilterRequest filterRequest, SQLSelect sql) throws SystemException {
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDSOLICITUD.equalsIgnoreCase(filterRequest.getName()))
            fSolicitudSolicitud(filterRequest, sql);
        if (SolicitudConstants.FILTER_NAME_IFSOLICITUDNUMCONTRATO.equalsIgnoreCase(filterRequest.getName()))
            iFSolicitudNumContrato(filterRequest, sql);
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDSTATUSADMIN.equalsIgnoreCase(filterRequest.getName()))
            fSolicitudStatusAdmin(filterRequest, sql);
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDCONTRATANTETITULAR.equalsIgnoreCase(filterRequest.getName()))
            fSolicitudContratanteTitular(filterRequest, sql);
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDLEGALIZACOMISIONES.equalsIgnoreCase(filterRequest.getName()))
            fSolicitudLegalizaComisiones(filterRequest, sql);
        if (SolicitudConstants.FILTER_NAME_IFSOLICITUDNUMSOLICITUD.equalsIgnoreCase(filterRequest.getName()))
            iFSolicitudNumSolicitud(filterRequest, sql);
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDCONSULTARADICADO.equalsIgnoreCase(filterRequest.getName()))
            fSolicitudConsultaRadicado(filterRequest, sql);
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDSTATUSAFIL1.equalsIgnoreCase(filterRequest.getName()))
            fSolicitudStatusAfil1(filterRequest, sql);
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDSTATUSAFIL2.equalsIgnoreCase(filterRequest.getName()))
            fSolicitudStatusAfil2(filterRequest, sql);
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDSTATUSAFIL3.equalsIgnoreCase(filterRequest.getName()))
            fSolicitudStatusAfil3(filterRequest, sql);
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDESTATUSAREAMED1.equalsIgnoreCase(filterRequest.getName()))
            fSolicitudEstatusAreaMed1(filterRequest, sql);
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDESTATUSAREAMED2.equalsIgnoreCase(filterRequest.getName()))
            fSolicitudEstatusAreaMed2(filterRequest, sql);
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDESTATUSAREAMED3.equalsIgnoreCase(filterRequest.getName()))
            fSolicitudEstatusAreaMed3(filterRequest, sql);
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDSTATUSESTHOMO.equalsIgnoreCase(filterRequest.getName()))
            fSolicitudStatusEstHomo(filterRequest, sql);
    }

    @Override
    protected void applyServiceSIFilters(FilterRequest filterRequest, SQLSelect sql) {
        if ("SuppInfo_IC_Solicitud".equals(filterRequest.getName()))
            suppInfo4ICSolicitud(filterRequest.getFilterVariable("searchText").toString(), sql);
    }

    private boolean applyMemoryFilters(QueryRequest queryRequest, Solicitud instance) {
        for (FilterRequest filterRequest : queryRequest.getFilters()) {
            if (!applyMemoryFilter(filterRequest, instance))
                return false;
        }
        return true;
    }
    
    private boolean applyMemoryFilter(FilterRequest filterRequest, Solicitud instance) {
        return applyMemoryQueryFilter(filterRequest, instance);
    }

    private boolean applyMemoryQueryFilter(FilterRequest filterRequest, Solicitud entity) {
        if (SolicitudConstants.FILTER_NAME_FSOLICITUDLEGALIZACOMISIONES.equalsIgnoreCase(filterRequest.getName()))
            return inMemoryFSolicitudLegalizaComisiones(entity);
        return true;
    }


    private void fSolicitudSolicitud(FilterRequest filterRequest, SQLSelect sql) throws SystemException{
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vNumSolicitud = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSOLICITUD_VNUMSOLICITUD) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSOLICITUD_VNUMSOLICITUD, FilterVariableRequest.STRING) : null;
        Date vFIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSOLICITUD_VFINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSOLICITUD_VFINI, FilterVariableRequest.DATE) : null;
        Date vFFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSOLICITUD_VFFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSOLICITUD_VFFIN, FilterVariableRequest.DATE) : null;

        // Aesolicitudes.Numerosolicitud = vNumSolicitud
        if (vNumSolicitud != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESNUMEROSOLICITUD, Constants.Type.STRING.getTypeName());
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(vNumSolicitud, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison1);

        }        

        // Aesolicitudes.Fechaasignacion >= vFIni
        if (vFIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison2.setFirstOperand(operand113);
            
            // Operand 2
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(vFIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison2);

        }        

        // Aesolicitudes.Fechaasignacion <= vFFin
        if (vFFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
            comparison3.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand114 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison3.setFirstOperand(operand114);
            
            // Operand 2
            comparison3.setSecondOperand(new SQLWhereOperandLiteral(vFFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison3);

        }        

        // EXIST (ContratoC) = true
    // 'exist' Association operator
    SQLWhereOperandSubQuery operandSubQueryAssocOperator071 = new SQLWhereOperandSubQuery(repository.assocOperator071Range1Query(sql));
    conjunction.addOperand(operandSubQueryAssocOperator071);


        // Aesolicitudes.Tiposolicitud = "E"
        // '=' Comparison
        SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
        comparison5.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand116 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESTIPOSOLICITUD, Constants.Type.STRING.getTypeName());
        comparison5.setFirstOperand(operand116);
        
        // Operand 2
        comparison5.setSecondOperand(new SQLWhereOperandLiteral("E", Constants.Type.STRING.getTypeName()));
        conjunction.addOperand(comparison5);


        // (Tramite <> 2 OR COUNT (Usuarios) = COUNT (Usuarios) WHERE (Usuarios.EstadoBeneficiario.Confirmado = true))
    {
        // 'or' Logical operator
        SQLWhereDisjunction comparison6 = new SQLWhereDisjunction();
    
        // Operand 1
            // '<>' Comparison
            SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
            comparison7.setOperator("<>");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDTRAMITE, Constants.Type.NAT.getTypeName());
            comparison7.setFirstOperand(operand118);
            
            // Operand 2
            comparison7.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(2), Constants.Type.NAT.getTypeName()));
            comparison6.addOperand(comparison7);
    
        // Operand 2
            // '=' Comparison
            SQLWhereComparison comparison9 = new SQLWhereComparison(sql);
            comparison9.setOperator("=");
            
            // Operand 1
                // 'count' Association operator
                SQLWhereOperandSubQuery operandSubQueryAssocOperator072 = new SQLWhereOperandSubQuery(repository.assocOperator072Range1Query(sql));
            comparison9.setFirstOperand(operandSubQueryAssocOperator072);
            
            // Operand 2
                // 'count' Association operator
                SQLWhereOperandSubQuery operandSubQueryAssocOperator073 = new SQLWhereOperandSubQuery(repository.assocOperator073Range1Query(sql));
            comparison9.setSecondOperand(operandSubQueryAssocOperator073);
            comparison6.addOperand(comparison9);
        
        conjunction.addOperand(comparison6);
    }


        sql.addConjunction(conjunction);
    }

    private void iFSolicitudNumContrato(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vNumContrato = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_IFSOLICITUDNUMCONTRATO_VNUMCONTRATO) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_IFSOLICITUDNUMCONTRATO_VNUMCONTRATO, FilterVariableRequest.STRING) : null;

        // NumContrato = vNumContrato
        if (vNumContrato != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDNUMCONTRATO, Constants.Type.STRING.getTypeName());
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(vNumContrato, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison1);

        }        

        sql.addConjunction(conjunction);
    }

    private void fSolicitudStatusAdmin(FilterRequest filterRequest, SQLSelect sql) throws SystemException{
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vNumSolicitud = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VNUMSOLICITUD) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VNUMSOLICITUD, FilterVariableRequest.STRING) : null;
        Long vTramite = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VTRAMITE) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VTRAMITE, FilterVariableRequest.NAT) : null;
        Productos voProducto = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOPRODUCTO) ? productosSrv.getByOID(new ProductosOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOPRODUCTO)))) : null;
        Planes voPlan = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOPLAN) ? planesSrv.getByOID(new PlanesOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOPLAN)))) : null;
        Long voEstado = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOESTADO) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOESTADO, FilterVariableRequest.INT) : null;
        Date vFElabIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VFELABINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VFELABINI, FilterVariableRequest.DATE) : null;
        Date vFElabFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VFELABFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VFELABFIN, FilterVariableRequest.DATE) : null;
        Date vFContratoIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VFCONTRATOINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VFCONTRATOINI, FilterVariableRequest.DATE) : null;
        Date vFContratoFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VFCONTRATOFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VFCONTRATOFIN, FilterVariableRequest.DATE) : null;
        String vCodAsesor = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VCODASESOR) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VCODASESOR, FilterVariableRequest.STRING) : null;
        TipoIdentificacion vTipoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VTIPODOC) ? tipoIdentificacionSrv.getByOID(new TipoIdentificacionOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VTIPODOC)))) : null;
        String vNumDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VNUMDOC) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VNUMDOC, FilterVariableRequest.STRING) : null;
        Regional voRegional = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOREGIONAL) ? regionalSrv.getByOID(new RegionalOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOREGIONAL)))) : null;
        Long vEstadoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VESTADODOC) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VESTADODOC, FilterVariableRequest.NAT) : null;

        // Completada = true
        // '=' Comparison
        SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
        comparison1.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDCOMPLETADA, Constants.Type.BOOL.getTypeName());
        comparison1.setFirstOperand(operand112);
        
        // Operand 2
        comparison1.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
        conjunction.addOperand(comparison1);


        // Aesolicitudes.Numerosolicitud = vNumSolicitud
        if (vNumSolicitud != null) {
            // '=' Comparison
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESNUMEROSOLICITUD, Constants.Type.STRING.getTypeName());
            comparison2.setFirstOperand(operand113);
            
            // Operand 2
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(vNumSolicitud, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison2);

        }        

        // Tramite = vTramite
        if (vTramite != null) {
            // '=' Comparison
            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
            comparison3.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand114 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDTRAMITE, Constants.Type.NAT.getTypeName());
            comparison3.setFirstOperand(operand114);
            
            // Operand 2
            comparison3.setSecondOperand(new SQLWhereOperandLiteral(vTramite, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison3);

        }        

        // Aesolicitudes.ProductoT = voProducto
        if (voProducto != null) {
            // '=' Comparison
            SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
            comparison4.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand115 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PRODUCTOT, ProductosConstants.CLASS_NAME);
            comparison4.setFirstOperand(operand115);
            
            // Operand 2
            SQLWhereOperandOVParameter operand226 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOPRODUCTO, ProductosConstants.CLASS_NAME, ProductosConstants.CLASS_NAME, voProducto.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOPRODUCTO);
            comparison4.setSecondOperand(operand226);
            conjunction.addOperand(comparison4);

        }        

        // Aesolicitudes.PlanT = voPlan
        if (voPlan != null) {
            // '=' Comparison
            SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
            comparison5.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand116 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PLANT, PlanesConstants.CLASS_NAME);
            comparison5.setFirstOperand(operand116);
            
            // Operand 2
            SQLWhereOperandOVParameter operand227 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOPLAN, PlanesConstants.CLASS_NAME, PlanesConstants.CLASS_NAME, voPlan.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOPLAN);
            comparison5.setSecondOperand(operand227);
            conjunction.addOperand(comparison5);

        }        

        // Estado = voEstado
        if (voEstado != null) {
            // '=' Comparison
            SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
            comparison6.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
            comparison6.setFirstOperand(operand117);
            
            // Operand 2
            comparison6.setSecondOperand(new SQLWhereOperandLiteral(voEstado, Constants.Type.INT.getTypeName()));
            conjunction.addOperand(comparison6);

        }        

        // Aesolicitudes.Fechaasignacion >= vFElabIni
        if (vFElabIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
            comparison7.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison7.setFirstOperand(operand118);
            
            // Operand 2
            comparison7.setSecondOperand(new SQLWhereOperandLiteral(vFElabIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison7);

        }        

        // Aesolicitudes.Fechaasignacion <= vFElabFin
        if (vFElabFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
            comparison8.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand119 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison8.setFirstOperand(operand119);
            
            // Operand 2
            comparison8.setSecondOperand(new SQLWhereOperandLiteral(vFElabFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison8);

        }        

        // FInicioContrato >= vFContratoIni
        if (vFContratoIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison9 = new SQLWhereComparison(sql);
            comparison9.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1110 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison9.setFirstOperand(operand1110);
            
            // Operand 2
            comparison9.setSecondOperand(new SQLWhereOperandLiteral(vFContratoIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison9);

        }        

        // FInicioContrato <= vFContratoFin
        if (vFContratoFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison10 = new SQLWhereComparison(sql);
            comparison10.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1111 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison10.setFirstOperand(operand1111);
            
            // Operand 2
            comparison10.setSecondOperand(new SQLWhereOperandLiteral(vFContratoFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison10);

        }        

        // Agente.Usuario LIKE "%" + vCodAsesor
        if (vCodAsesor != null) {
            // 'like' Comparison
            SQLWhereComparison comparison11 = new SQLWhereComparison(sql);
            comparison11.setOperator("like");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ATTR_NAME_FUNCIONARIOUSUARIO, Constants.Type.STRING.getTypeName());
            comparison11.setFirstOperand(operand1112);
            
            // Operand 2
            comparison11.setSecondOperand(new SQLWhereOperandLiteral("%" + vCodAsesor, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison11);

        }        

        // ((Contratante.TipoIdentificacion = vTipoDoc AND Contratante.NumIdentificacion = vNumDoc) OR (Titular.TipoIdentificacion = vTipoDoc AND Titular.NumIdentificacion = vNumDoc) OR (EXIST (Usuarios) WHERE (Usuarios.TipoIdentificacion = vTipoDoc AND Usuarios.NumIdentificacion = vNumDoc) = true))
        if (vTipoDoc != null && vNumDoc != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison12 = new SQLWhereDisjunction();
        
            // Operand 1
                {
                    // 'or' Logical operator
                    SQLWhereDisjunction comparison13 = new SQLWhereDisjunction();
                
                    // Operand 1
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison14 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison15 = new SQLWhereComparison(sql);
                                    comparison15.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1116 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison15.setFirstOperand(operand1116);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2217 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VTIPODOC);
                                    comparison15.setSecondOperand(operand2217);
                                    comparison14.addOperand(comparison15);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison17 = new SQLWhereComparison(sql);
                                    comparison17.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ATTR_NAME_CONTRATANTENUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison17.setFirstOperand(operand1118);
                                    
                                    // Operand 2
                                    comparison17.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison14.addOperand(comparison17);
                            
                            comparison13.addOperand(comparison14);
                        }
                
                    // Operand 2
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison18 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison19 = new SQLWhereComparison(sql);
                                    comparison19.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1120 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison19.setFirstOperand(operand1120);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2221 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VTIPODOC);
                                    comparison19.setSecondOperand(operand2221);
                                    comparison18.addOperand(comparison19);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison21 = new SQLWhereComparison(sql);
                                    comparison21.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1122 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ATTR_NAME_TITULARNUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison21.setFirstOperand(operand1122);
                                    
                                    // Operand 2
                                    comparison21.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison18.addOperand(comparison21);
                            
                            comparison13.addOperand(comparison18);
                        }
                    
                    comparison12.addOperand(comparison13);
                }
        
            // Operand 2
            // 'exist' Association operator
            SQLWhereOperandSubQuery operandSubQueryAssocOperator074 = new SQLWhereOperandSubQuery(repository.assocOperator074Range1Query(vTipoDoc, vNumDoc, sql));
            comparison12.addOperand(operandSubQueryAssocOperator074);
            
            conjunction.addOperand(comparison12);
        }

        }        

        // Agente.Sucursal.Regional = voRegional
        if (voRegional != null) {
            // '=' Comparison
            SQLWhereComparison comparison13 = new SQLWhereComparison(sql);
            comparison13.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand1114 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ROLE_NAME_SUCURSAL + "." + SucursalConstants.ROLE_NAME_REGIONAL, RegionalConstants.CLASS_NAME);
            comparison13.setFirstOperand(operand1114);
            
            // Operand 2
            SQLWhereOperandOVParameter operand2215 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOREGIONAL, RegionalConstants.CLASS_NAME, RegionalConstants.CLASS_NAME, voRegional.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSADMIN_VOREGIONAL);
            comparison13.setSecondOperand(operand2215);
            conjunction.addOperand(comparison13);

        }        

        // EstadoDoc = vEstadoDoc
        if (vEstadoDoc != null) {
            // '=' Comparison
            SQLWhereComparison comparison14 = new SQLWhereComparison(sql);
            comparison14.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1115 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADODOC, Constants.Type.NAT.getTypeName());
            comparison14.setFirstOperand(operand1115);
            
            // Operand 2
            comparison14.setSecondOperand(new SQLWhereOperandLiteral(vEstadoDoc, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison14);

        }        

        // Aesolicitudes.Tiposolicitud = "E"
        // '=' Comparison
        SQLWhereComparison comparison15 = new SQLWhereComparison(sql);
        comparison15.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1116 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESTIPOSOLICITUD, Constants.Type.STRING.getTypeName());
        comparison15.setFirstOperand(operand1116);
        
        // Operand 2
        comparison15.setSecondOperand(new SQLWhereOperandLiteral("E", Constants.Type.STRING.getTypeName()));
        conjunction.addOperand(comparison15);


        sql.addConjunction(conjunction);
    }

    private void fSolicitudContratanteTitular(FilterRequest filterRequest, SQLSelect sql) throws SystemException{
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Persona voPersona = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONTRATANTETITULAR_VOPERSONA) ? personaSrv.getByOID(new PersonaOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONTRATANTETITULAR_VOPERSONA)))) : null;
        Date vFIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONTRATANTETITULAR_VFINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONTRATANTETITULAR_VFINI, FilterVariableRequest.DATE) : null;
        Date vFFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONTRATANTETITULAR_VFFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONTRATANTETITULAR_VFFIN, FilterVariableRequest.DATE) : null;

        // ((ContratantePersona.TipoIdentificacion = voPersona.TipoIdentificacion AND ContratantePersona.NumIdentificacion = voPersona.NumIdentificacion) OR (TitularPersona.TipoIdentificacion = voPersona.TipoIdentificacion AND TitularPersona.NumIdentificacion = voPersona.NumIdentificacion) OR (EXIST (Usuarios) WHERE (Usuarios.UsuarioPersona.TipoIdentificacion = voPersona.TipoIdentificacion AND Usuarios.UsuarioPersona.NumIdentificacion = voPersona.NumIdentificacion) = true))
        if (voPersona != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison1 = new SQLWhereDisjunction();
        
            // Operand 1
                {
                    // 'or' Logical operator
                    SQLWhereDisjunction comparison2 = new SQLWhereDisjunction();
                
                    // Operand 1
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison3 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
                                    comparison4.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand115 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_CONTRATANTEPERSONA + "." + PersonaConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison4.setFirstOperand(operand115);
                                    
                                    // Operand 2
                                    if (voPersona.isNull() || voPersona.getTipoIdentificacion().isNull())
                                    {
                                    comparison4.setSecondOperand(new SQLWhereOperandNull());
                                    }
                                    else
                                    {
                                    SQLWhereOperandOVParameter operand226 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDCONTRATANTETITULAR_VOPERSONA + "." + PersonaConstants.ROLE_NAME_TIPOIDENTIFICACION, PersonaConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, voPersona.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDCONTRATANTETITULAR_VOPERSONA);
                                    comparison4.setSecondOperand(operand226);
                                    }
                                    comparison3.addOperand(comparison4);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
                                    comparison6.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_CONTRATANTEPERSONA + "." + PersonaConstants.ATTR_NAME_PERSONANUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison6.setFirstOperand(operand117);
                                    
                                    // Operand 2
                                    if (voPersona.getPersonaNumIdentificacion()!= null){
                                    comparison6.setSecondOperand(new SQLWhereOperandLiteral(voPersona.getPersonaNumIdentificacion(), Constants.Type.STRING.getTypeName()));
                                    } else {
                                    comparison6.setSecondOperand(new SQLWhereOperandNull());
                                    }
                                    comparison3.addOperand(comparison6);
                            
                            comparison2.addOperand(comparison3);
                        }
                
                    // Operand 2
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison7 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
                                    comparison8.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand119 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_TITULARPERSONA + "." + PersonaConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison8.setFirstOperand(operand119);
                                    
                                    // Operand 2
                                    if (voPersona.isNull() || voPersona.getTipoIdentificacion().isNull())
                                    {
                                    comparison8.setSecondOperand(new SQLWhereOperandNull());
                                    }
                                    else
                                    {
                                    SQLWhereOperandOVParameter operand2210 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDCONTRATANTETITULAR_VOPERSONA + "." + PersonaConstants.ROLE_NAME_TIPOIDENTIFICACION, PersonaConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, voPersona.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDCONTRATANTETITULAR_VOPERSONA);
                                    comparison8.setSecondOperand(operand2210);
                                    }
                                    comparison7.addOperand(comparison8);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison10 = new SQLWhereComparison(sql);
                                    comparison10.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1111 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_TITULARPERSONA + "." + PersonaConstants.ATTR_NAME_PERSONANUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison10.setFirstOperand(operand1111);
                                    
                                    // Operand 2
                                    if (voPersona.getPersonaNumIdentificacion()!= null){
                                    comparison10.setSecondOperand(new SQLWhereOperandLiteral(voPersona.getPersonaNumIdentificacion(), Constants.Type.STRING.getTypeName()));
                                    } else {
                                    comparison10.setSecondOperand(new SQLWhereOperandNull());
                                    }
                                    comparison7.addOperand(comparison10);
                            
                            comparison2.addOperand(comparison7);
                        }
                    
                    comparison1.addOperand(comparison2);
                }
        
            // Operand 2
            // 'exist' Association operator
            SQLWhereOperandSubQuery operandSubQueryAssocOperator075 = new SQLWhereOperandSubQuery(repository.assocOperator075Range1Query(voPersona, sql));
            comparison1.addOperand(operandSubQueryAssocOperator075);
            
            conjunction.addOperand(comparison1);
        }

        }        

        // Aesolicitudes.Fesolici >= vFIni
        if (vFIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFESOLICI, Constants.Type.DATE.getTypeName());
            comparison2.setFirstOperand(operand113);
            
            // Operand 2
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(vFIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison2);

        }        

        // Aesolicitudes.Fesolici <= vFFin
        if (vFFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
            comparison3.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand114 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFESOLICI, Constants.Type.DATE.getTypeName());
            comparison3.setFirstOperand(operand114);
            
            // Operand 2
            comparison3.setSecondOperand(new SQLWhereOperandLiteral(vFFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison3);

        }        

        // EXIST (ContratoC) = true
    // 'exist' Association operator
    SQLWhereOperandSubQuery operandSubQueryAssocOperator076 = new SQLWhereOperandSubQuery(repository.assocOperator076Range1Query(sql));
    conjunction.addOperand(operandSubQueryAssocOperator076);


        // Aesolicitudes.Tiposolicitud = "E"
        // '=' Comparison
        SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
        comparison5.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand116 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESTIPOSOLICITUD, Constants.Type.STRING.getTypeName());
        comparison5.setFirstOperand(operand116);
        
        // Operand 2
        comparison5.setSecondOperand(new SQLWhereOperandLiteral("E", Constants.Type.STRING.getTypeName()));
        conjunction.addOperand(comparison5);


        // (Tramite <> 2 OR COUNT (Usuarios) = COUNT (Usuarios) WHERE (Usuarios.EstadoBeneficiario.Confirmado = true))
    {
        // 'or' Logical operator
        SQLWhereDisjunction comparison6 = new SQLWhereDisjunction();
    
        // Operand 1
            // '<>' Comparison
            SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
            comparison7.setOperator("<>");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDTRAMITE, Constants.Type.NAT.getTypeName());
            comparison7.setFirstOperand(operand118);
            
            // Operand 2
            comparison7.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(2), Constants.Type.NAT.getTypeName()));
            comparison6.addOperand(comparison7);
    
        // Operand 2
            // '=' Comparison
            SQLWhereComparison comparison9 = new SQLWhereComparison(sql);
            comparison9.setOperator("=");
            
            // Operand 1
                // 'count' Association operator
                SQLWhereOperandSubQuery operandSubQueryAssocOperator077 = new SQLWhereOperandSubQuery(repository.assocOperator077Range1Query(sql));
            comparison9.setFirstOperand(operandSubQueryAssocOperator077);
            
            // Operand 2
                // 'count' Association operator
                SQLWhereOperandSubQuery operandSubQueryAssocOperator078 = new SQLWhereOperandSubQuery(repository.assocOperator078Range1Query(sql));
            comparison9.setSecondOperand(operandSubQueryAssocOperator078);
            comparison6.addOperand(comparison9);
        
        conjunction.addOperand(comparison6);
    }


        sql.addConjunction(conjunction);
    }

    private void fSolicitudLegalizaComisiones(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Date vFInicio = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDLEGALIZACOMISIONES_VFINICIO) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDLEGALIZACOMISIONES_VFINICIO, FilterVariableRequest.DATE) : null;
        Date vFFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDLEGALIZACOMISIONES_VFFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDLEGALIZACOMISIONES_VFFIN, FilterVariableRequest.DATE) : null;

        // Tramite = 1
        // '=' Comparison
        SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
        comparison1.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDTRAMITE, Constants.Type.NAT.getTypeName());
        comparison1.setFirstOperand(operand112);
        
        // Operand 2
        comparison1.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(1), Constants.Type.NAT.getTypeName()));
        conjunction.addOperand(comparison1);


        // Aesolicitudes.Tiposolicitud <> "F"
        // '<>' Comparison
        SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
        comparison2.setOperator("<>");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESTIPOSOLICITUD, Constants.Type.STRING.getTypeName());
        comparison2.setFirstOperand(operand113);
        
        // Operand 2
        comparison2.setSecondOperand(new SQLWhereOperandLiteral("F", Constants.Type.STRING.getTypeName()));
        conjunction.addOperand(comparison2);


        // FInicioContrato >= vFInicio
        if (vFInicio != null) {
            // '>=' Comparison
            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
            comparison3.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand114 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison3.setFirstOperand(operand114);
            
            // Operand 2
            comparison3.setSecondOperand(new SQLWhereOperandLiteral(vFInicio, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison3);

        }        

        // FInicioContrato <= vFFin
        if (vFFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
            comparison4.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand115 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison4.setFirstOperand(operand115);
            
            // Operand 2
            comparison4.setSecondOperand(new SQLWhereOperandLiteral(vFFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison4);

        }        

        // EstadoDoc = 2
        // '=' Comparison
        SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
        comparison5.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand116 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADODOC, Constants.Type.NAT.getTypeName());
        comparison5.setFirstOperand(operand116);
        
        // Operand 2
        comparison5.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(2), Constants.Type.NAT.getTypeName()));
        conjunction.addOperand(comparison5);


        // (Estado = 2 OR Estado = 3)
    {
        // 'or' Logical operator
        SQLWhereDisjunction comparison6 = new SQLWhereDisjunction();
    
        // Operand 1
            // '=' Comparison
            SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
            comparison7.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
            comparison7.setFirstOperand(operand118);
            
            // Operand 2
            comparison7.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(2), Constants.Type.NAT.getTypeName()));
            comparison6.addOperand(comparison7);
    
        // Operand 2
            // '=' Comparison
            SQLWhereComparison comparison9 = new SQLWhereComparison(sql);
            comparison9.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1110 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
            comparison9.setFirstOperand(operand1110);
            
            // Operand 2
            comparison9.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(3), Constants.Type.NAT.getTypeName()));
            comparison6.addOperand(comparison9);
        
        conjunction.addOperand(comparison6);
    }


        sql.addConjunction(conjunction);
    }

    private void iFSolicitudNumSolicitud(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vNumSolicitud = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_IFSOLICITUDNUMSOLICITUD_VNUMSOLICITUD) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_IFSOLICITUDNUMSOLICITUD_VNUMSOLICITUD, FilterVariableRequest.STRING) : null;

        // Aesolicitudes.Numerosolicitud = vNumSolicitud
        if (vNumSolicitud != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESNUMEROSOLICITUD, Constants.Type.STRING.getTypeName());
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(vNumSolicitud, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison1);

        }        

        sql.addConjunction(conjunction);
    }

    private void fSolicitudConsultaRadicado(FilterRequest filterRequest, SQLSelect sql) throws SystemException{
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        TipoIdentificacion voTipoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONSULTARADICADO_VOTIPODOC) ? tipoIdentificacionSrv.getByOID(new TipoIdentificacionOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONSULTARADICADO_VOTIPODOC)))) : null;
        String vNumDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONSULTARADICADO_VNUMDOC) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONSULTARADICADO_VNUMDOC, FilterVariableRequest.STRING) : null;
        Date vfDesde = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONSULTARADICADO_VFDESDE) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONSULTARADICADO_VFDESDE, FilterVariableRequest.DATE) : null;
        Date vfHasta = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONSULTARADICADO_VFHASTA) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDCONSULTARADICADO_VFHASTA, FilterVariableRequest.DATE) : null;

        // ((EXIST (Contratante) WHERE (Contratante.TipoIdentificacion = voTipoDoc AND Contratante.NumIdentificacion = vNumDoc) = true) OR (EXIST (Titular) WHERE (Titular.TipoIdentificacion = voTipoDoc AND Titular.NumIdentificacion = vNumDoc) = true) OR (EXIST (Usuarios) WHERE (Usuarios.TipoIdentificacion = voTipoDoc AND Usuarios.NumIdentificacion = vNumDoc) = true))
        if (voTipoDoc != null && vNumDoc != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison1 = new SQLWhereDisjunction();
        
            // Operand 1
                {
                    // 'or' Logical operator
                    SQLWhereDisjunction comparison2 = new SQLWhereDisjunction();
                
                    // Operand 1
                    // 'exist' Association operator
                    SQLWhereOperandSubQuery operandSubQueryAssocOperator079 = new SQLWhereOperandSubQuery(repository.assocOperator079Range1Query(voTipoDoc, vNumDoc, sql));
                    comparison2.addOperand(operandSubQueryAssocOperator079);
                
                    // Operand 2
                    // 'exist' Association operator
                    SQLWhereOperandSubQuery operandSubQueryAssocOperator080 = new SQLWhereOperandSubQuery(repository.assocOperator080Range1Query(voTipoDoc, vNumDoc, sql));
                    comparison2.addOperand(operandSubQueryAssocOperator080);
                    
                    comparison1.addOperand(comparison2);
                }
        
            // Operand 2
            // 'exist' Association operator
            SQLWhereOperandSubQuery operandSubQueryAssocOperator081 = new SQLWhereOperandSubQuery(repository.assocOperator081Range1Query(voTipoDoc, vNumDoc, sql));
            comparison1.addOperand(operandSubQueryAssocOperator081);
            
            conjunction.addOperand(comparison1);
        }

        }        

        // Completada = false
        // '=' Comparison
        SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
        comparison2.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDCOMPLETADA, Constants.Type.BOOL.getTypeName());
        comparison2.setFirstOperand(operand113);
        
        // Operand 2
        comparison2.setSecondOperand(new SQLWhereOperandLiteral(Boolean.FALSE, Constants.Type.BOOL.getTypeName()));
        conjunction.addOperand(comparison2);


        // Aesolicitudes.Fechaasignacion >= dateAdd("d", -1 * StrToInt(fugetValorParametroConf("NUMDIASCONSULTARADICADO")), systemDate())
        // '>=' Comparison
        SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
        comparison3.setOperator(">=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand114 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
        comparison3.setFirstOperand(operand114);
        
        // Operand 2
                if (STDFunctions.dateAddFun("d", Long.valueOf(-1) * STDFunctions.strToIntFun(userFunctionsSrv.fugetValorParametroConfFun("NUMDIASCONSULTARADICADO")), STDFunctions.systemDateFun()) != null) {
                    comparison3.setSecondOperand(new SQLWhereOperandLiteral(STDFunctions.dateAddFun("d", Long.valueOf(-1) * STDFunctions.strToIntFun(userFunctionsSrv.fugetValorParametroConfFun("NUMDIASCONSULTARADICADO")), STDFunctions.systemDateFun()), Constants.Type.DATE.getTypeName()));
                } else {
                    comparison3.setSecondOperand(new SQLWhereOperandNull());
                    }
        conjunction.addOperand(comparison3);


        // Aesolicitudes.Tiposolicitud = "E"
        // '=' Comparison
        SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
        comparison4.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand115 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESTIPOSOLICITUD, Constants.Type.STRING.getTypeName());
        comparison4.setFirstOperand(operand115);
        
        // Operand 2
        comparison4.setSecondOperand(new SQLWhereOperandLiteral("E", Constants.Type.STRING.getTypeName()));
        conjunction.addOperand(comparison4);


        // Aesolicitudes.Fesolici >= vfDesde
        if (vfDesde != null) {
            // '>=' Comparison
            SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
            comparison5.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand116 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFESOLICI, Constants.Type.DATE.getTypeName());
            comparison5.setFirstOperand(operand116);
            
            // Operand 2
            comparison5.setSecondOperand(new SQLWhereOperandLiteral(vfDesde, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison5);

        }        

        // Aesolicitudes.Fesolici <= vfHasta
        if (vfHasta != null) {
            // '<=' Comparison
            SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
            comparison6.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFESOLICI, Constants.Type.DATE.getTypeName());
            comparison6.setFirstOperand(operand117);
            
            // Operand 2
            comparison6.setSecondOperand(new SQLWhereOperandLiteral(vfHasta, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison6);

        }        

        sql.addConjunction(conjunction);
    }

    private void fSolicitudStatusAfil1(FilterRequest filterRequest, SQLSelect sql) throws SystemException{
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vNumSolicitud = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VNUMSOLICITUD) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VNUMSOLICITUD, FilterVariableRequest.STRING) : null;
        Long vTramite = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VTRAMITE) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VTRAMITE, FilterVariableRequest.NAT) : null;
        Productos voProducto = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOPRODUCTO) ? productosSrv.getByOID(new ProductosOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOPRODUCTO)))) : null;
        Planes voPlan = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOPLAN) ? planesSrv.getByOID(new PlanesOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOPLAN)))) : null;
        Long voEstado = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOESTADO) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOESTADO, FilterVariableRequest.INT) : null;
        Date vFElabIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VFELABINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VFELABINI, FilterVariableRequest.DATE) : null;
        Date vFElabFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VFELABFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VFELABFIN, FilterVariableRequest.DATE) : null;
        Date vFContratoIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VFCONTRATOINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VFCONTRATOINI, FilterVariableRequest.DATE) : null;
        Date vFContratoFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VFCONTRATOFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VFCONTRATOFIN, FilterVariableRequest.DATE) : null;
        String vCodAsesor = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VCODASESOR) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VCODASESOR, FilterVariableRequest.STRING) : null;
        TipoIdentificacion vTipoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VTIPODOC) ? tipoIdentificacionSrv.getByOID(new TipoIdentificacionOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VTIPODOC)))) : null;
        String vNumDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VNUMDOC) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VNUMDOC, FilterVariableRequest.STRING) : null;
        Regional voRegional = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOREGIONAL) ? regionalSrv.getByOID(new RegionalOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOREGIONAL)))) : null;
        Long vEstadoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VESTADODOC) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VESTADODOC, FilterVariableRequest.NAT) : null;

        // Aesolicitudes.Numerosolicitud = vNumSolicitud
        if (vNumSolicitud != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESNUMEROSOLICITUD, Constants.Type.STRING.getTypeName());
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(vNumSolicitud, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison1);

        }        

        // Tramite = vTramite
        if (vTramite != null) {
            // '=' Comparison
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDTRAMITE, Constants.Type.NAT.getTypeName());
            comparison2.setFirstOperand(operand113);
            
            // Operand 2
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(vTramite, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison2);

        }        

        // Aesolicitudes.ProductoT = voProducto
        if (voProducto != null) {
            // '=' Comparison
            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
            comparison3.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand114 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PRODUCTOT, ProductosConstants.CLASS_NAME);
            comparison3.setFirstOperand(operand114);
            
            // Operand 2
            SQLWhereOperandOVParameter operand225 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOPRODUCTO, ProductosConstants.CLASS_NAME, ProductosConstants.CLASS_NAME, voProducto.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOPRODUCTO);
            comparison3.setSecondOperand(operand225);
            conjunction.addOperand(comparison3);

        }        

        // Aesolicitudes.PlanT = voPlan
        if (voPlan != null) {
            // '=' Comparison
            SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
            comparison4.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand115 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PLANT, PlanesConstants.CLASS_NAME);
            comparison4.setFirstOperand(operand115);
            
            // Operand 2
            SQLWhereOperandOVParameter operand226 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOPLAN, PlanesConstants.CLASS_NAME, PlanesConstants.CLASS_NAME, voPlan.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOPLAN);
            comparison4.setSecondOperand(operand226);
            conjunction.addOperand(comparison4);

        }        

        // (Estado = voEstado OR (Estado = 8 AND voEstado = 1))
        if (voEstado != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison5 = new SQLWhereDisjunction();
        
            // Operand 1
                    // '=' Comparison
                    SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
                    comparison6.setOperator("=");
                    
                    // Operand 1
                    SQLWhereOperandPathAttribute operand117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
                    comparison6.setFirstOperand(operand117);
                    
                    // Operand 2
                    comparison6.setSecondOperand(new SQLWhereOperandLiteral(voEstado, Constants.Type.INT.getTypeName()));
                    comparison5.addOperand(comparison6);
        
            // Operand 2
                {
                    // 'and' Logical operator
                    SQLWhereConjunction comparison8 = new SQLWhereConjunction();
                
                    // Operand 1
                            // '=' Comparison
                            SQLWhereComparison comparison9 = new SQLWhereComparison(sql);
                            comparison9.setOperator("=");
                            
                            // Operand 1
                            SQLWhereOperandPathAttribute operand1110 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
                            comparison9.setFirstOperand(operand1110);
                            
                            // Operand 2
                            comparison9.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(8), Constants.Type.NAT.getTypeName()));
                            comparison8.addOperand(comparison9);
                
                    // Operand 2
                            // '=' Comparison
                            SQLWhereComparison comparison11 = new SQLWhereComparison(sql);
                            comparison11.setOperator("=");
                            
                            // Operand 1
                            comparison11.setFirstOperand(new SQLWhereOperandLiteral(voEstado, Constants.Type.INT.getTypeName()));
                            
                            // Operand 2
                            comparison11.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(1), Constants.Type.NAT.getTypeName()));
                            comparison8.addOperand(comparison11);
                    
                    comparison5.addOperand(comparison8);
                }
            
            conjunction.addOperand(comparison5);
        }

        }        

        // Aesolicitudes.Fechaasignacion >= vFElabIni
        if (vFElabIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
            comparison6.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison6.setFirstOperand(operand117);
            
            // Operand 2
            comparison6.setSecondOperand(new SQLWhereOperandLiteral(vFElabIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison6);

        }        

        // Aesolicitudes.Fechaasignacion <= vFElabFin
        if (vFElabFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
            comparison7.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison7.setFirstOperand(operand118);
            
            // Operand 2
            comparison7.setSecondOperand(new SQLWhereOperandLiteral(vFElabFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison7);

        }        

        // FInicioContrato >= vFContratoIni
        if (vFContratoIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
            comparison8.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand119 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison8.setFirstOperand(operand119);
            
            // Operand 2
            comparison8.setSecondOperand(new SQLWhereOperandLiteral(vFContratoIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison8);

        }        

        // FInicioContrato <= vFContratoFin
        if (vFContratoFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison9 = new SQLWhereComparison(sql);
            comparison9.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1110 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison9.setFirstOperand(operand1110);
            
            // Operand 2
            comparison9.setSecondOperand(new SQLWhereOperandLiteral(vFContratoFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison9);

        }        

        // Agente.Usuario LIKE "%" + vCodAsesor
        if (vCodAsesor != null) {
            // 'like' Comparison
            SQLWhereComparison comparison10 = new SQLWhereComparison(sql);
            comparison10.setOperator("like");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1111 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ATTR_NAME_FUNCIONARIOUSUARIO, Constants.Type.STRING.getTypeName());
            comparison10.setFirstOperand(operand1111);
            
            // Operand 2
            comparison10.setSecondOperand(new SQLWhereOperandLiteral("%" + vCodAsesor, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison10);

        }        

        // ((Contratante.TipoIdentificacion = vTipoDoc AND Contratante.NumIdentificacion = vNumDoc) OR (Titular.TipoIdentificacion = vTipoDoc AND Titular.NumIdentificacion = vNumDoc) OR (EXIST (Usuarios) WHERE (Usuarios.TipoIdentificacion = vTipoDoc AND Usuarios.NumIdentificacion = vNumDoc) = true))
        if (vTipoDoc != null && vNumDoc != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison11 = new SQLWhereDisjunction();
        
            // Operand 1
                {
                    // 'or' Logical operator
                    SQLWhereDisjunction comparison12 = new SQLWhereDisjunction();
                
                    // Operand 1
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison13 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison14 = new SQLWhereComparison(sql);
                                    comparison14.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1115 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison14.setFirstOperand(operand1115);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2216 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VTIPODOC);
                                    comparison14.setSecondOperand(operand2216);
                                    comparison13.addOperand(comparison14);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison16 = new SQLWhereComparison(sql);
                                    comparison16.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ATTR_NAME_CONTRATANTENUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison16.setFirstOperand(operand1117);
                                    
                                    // Operand 2
                                    comparison16.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison13.addOperand(comparison16);
                            
                            comparison12.addOperand(comparison13);
                        }
                
                    // Operand 2
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison17 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison18 = new SQLWhereComparison(sql);
                                    comparison18.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1119 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison18.setFirstOperand(operand1119);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2220 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VTIPODOC);
                                    comparison18.setSecondOperand(operand2220);
                                    comparison17.addOperand(comparison18);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison20 = new SQLWhereComparison(sql);
                                    comparison20.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1121 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ATTR_NAME_TITULARNUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison20.setFirstOperand(operand1121);
                                    
                                    // Operand 2
                                    comparison20.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison17.addOperand(comparison20);
                            
                            comparison12.addOperand(comparison17);
                        }
                    
                    comparison11.addOperand(comparison12);
                }
        
            // Operand 2
            // 'exist' Association operator
            SQLWhereOperandSubQuery operandSubQueryAssocOperator082 = new SQLWhereOperandSubQuery(repository.assocOperator082Range1Query(vTipoDoc, vNumDoc, sql));
            comparison11.addOperand(operandSubQueryAssocOperator082);
            
            conjunction.addOperand(comparison11);
        }

        }        

        // Agente.Sucursal.Regional = voRegional
        if (voRegional != null) {
            // '=' Comparison
            SQLWhereComparison comparison12 = new SQLWhereComparison(sql);
            comparison12.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand1113 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ROLE_NAME_SUCURSAL + "." + SucursalConstants.ROLE_NAME_REGIONAL, RegionalConstants.CLASS_NAME);
            comparison12.setFirstOperand(operand1113);
            
            // Operand 2
            SQLWhereOperandOVParameter operand2214 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOREGIONAL, RegionalConstants.CLASS_NAME, RegionalConstants.CLASS_NAME, voRegional.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL1_VOREGIONAL);
            comparison12.setSecondOperand(operand2214);
            conjunction.addOperand(comparison12);

        }        

        // EstadoDoc = vEstadoDoc
        if (vEstadoDoc != null) {
            // '=' Comparison
            SQLWhereComparison comparison13 = new SQLWhereComparison(sql);
            comparison13.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1114 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADODOC, Constants.Type.NAT.getTypeName());
            comparison13.setFirstOperand(operand1114);
            
            // Operand 2
            comparison13.setSecondOperand(new SQLWhereOperandLiteral(vEstadoDoc, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison13);

        }        

        // Aesolicitudes.Tiposolicitud = "E"
        // '=' Comparison
        SQLWhereComparison comparison14 = new SQLWhereComparison(sql);
        comparison14.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1115 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESTIPOSOLICITUD, Constants.Type.STRING.getTypeName());
        comparison14.setFirstOperand(operand1115);
        
        // Operand 2
        comparison14.setSecondOperand(new SQLWhereOperandLiteral("E", Constants.Type.STRING.getTypeName()));
        conjunction.addOperand(comparison14);


        // ((EstadoDoc = 1 AND Estado <> 4 AND Completada = true AND FechaTerminada = NULL) OR (EstadoDoc = 2 AND (Estado = 1 OR Estado = 8 OR Estado = 0) AND Completada = true))
    {
        // 'or' Logical operator
        SQLWhereDisjunction comparison15 = new SQLWhereDisjunction();
    
        // Operand 1
        {
            // 'and' Logical operator
            SQLWhereConjunction comparison16 = new SQLWhereConjunction();
        
            // Operand 1
            {
                // 'and' Logical operator
                SQLWhereConjunction comparison17 = new SQLWhereConjunction();
            
                // Operand 1
                {
                    // 'and' Logical operator
                    SQLWhereConjunction comparison18 = new SQLWhereConjunction();
                
                    // Operand 1
                        // '=' Comparison
                        SQLWhereComparison comparison19 = new SQLWhereComparison(sql);
                        comparison19.setOperator("=");
                        
                        // Operand 1
                        SQLWhereOperandPathAttribute operand1120 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADODOC, Constants.Type.NAT.getTypeName());
                        comparison19.setFirstOperand(operand1120);
                        
                        // Operand 2
                        comparison19.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(1), Constants.Type.NAT.getTypeName()));
                        comparison18.addOperand(comparison19);
                
                    // Operand 2
                        // '<>' Comparison
                        SQLWhereComparison comparison21 = new SQLWhereComparison(sql);
                        comparison21.setOperator("<>");
                        
                        // Operand 1
                        SQLWhereOperandPathAttribute operand1122 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
                        comparison21.setFirstOperand(operand1122);
                        
                        // Operand 2
                        comparison21.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(4), Constants.Type.NAT.getTypeName()));
                        comparison18.addOperand(comparison21);
                    
                    comparison17.addOperand(comparison18);
                }
            
                // Operand 2
                    // '=' Comparison
                    SQLWhereComparison comparison22 = new SQLWhereComparison(sql);
                    comparison22.setOperator("=");
                    
                    // Operand 1
                    SQLWhereOperandPathAttribute operand1123 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDCOMPLETADA, Constants.Type.BOOL.getTypeName());
                    comparison22.setFirstOperand(operand1123);
                    
                    // Operand 2
                    comparison22.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
                    comparison17.addOperand(comparison22);
                
                comparison16.addOperand(comparison17);
            }
        
            // Operand 2
                // '=' Comparison
                SQLWhereComparison comparison24 = new SQLWhereComparison(sql);
                comparison24.setOperator("=");
                
                // Operand 1
                SQLWhereOperandPathAttribute operand1125 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFECHATERMINADA, Constants.Type.DATE.getTypeName());
                comparison24.setFirstOperand(operand1125);
                
                // Operand 2
                comparison24.setSecondOperand(new SQLWhereOperandNull());
                comparison16.addOperand(comparison24);
            
            comparison15.addOperand(comparison16);
        }
    
        // Operand 2
        {
            // 'and' Logical operator
            SQLWhereConjunction comparison240 = new SQLWhereConjunction();
        
            // Operand 1
            {
                // 'and' Logical operator
                SQLWhereConjunction comparison25 = new SQLWhereConjunction();
            
                // Operand 1
                    // '=' Comparison
                    SQLWhereComparison comparison26 = new SQLWhereComparison(sql);
                    comparison26.setOperator("=");
                    
                    // Operand 1
                    SQLWhereOperandPathAttribute operand1127 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADODOC, Constants.Type.NAT.getTypeName());
                    comparison26.setFirstOperand(operand1127);
                    
                    // Operand 2
                    comparison26.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(2), Constants.Type.NAT.getTypeName()));
                    comparison25.addOperand(comparison26);
            
                // Operand 2
                {
                    // 'or' Logical operator
                    SQLWhereDisjunction comparison28 = new SQLWhereDisjunction();
                
                    // Operand 1
                    {
                        // 'or' Logical operator
                        SQLWhereDisjunction comparison29 = new SQLWhereDisjunction();
                    
                        // Operand 1
                            // '=' Comparison
                            SQLWhereComparison comparison30 = new SQLWhereComparison(sql);
                            comparison30.setOperator("=");
                            
                            // Operand 1
                            SQLWhereOperandPathAttribute operand1131 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
                            comparison30.setFirstOperand(operand1131);
                            
                            // Operand 2
                            comparison30.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(1), Constants.Type.NAT.getTypeName()));
                            comparison29.addOperand(comparison30);
                    
                        // Operand 2
                            // '=' Comparison
                            SQLWhereComparison comparison32 = new SQLWhereComparison(sql);
                            comparison32.setOperator("=");
                            
                            // Operand 1
                            SQLWhereOperandPathAttribute operand1133 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
                            comparison32.setFirstOperand(operand1133);
                            
                            // Operand 2
                            comparison32.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(8), Constants.Type.NAT.getTypeName()));
                            comparison29.addOperand(comparison32);
                        
                        comparison28.addOperand(comparison29);
                    }
                
                    // Operand 2
                        // '=' Comparison
                        SQLWhereComparison comparison33 = new SQLWhereComparison(sql);
                        comparison33.setOperator("=");
                        
                        // Operand 1
                        SQLWhereOperandPathAttribute operand1134 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
                        comparison33.setFirstOperand(operand1134);
                        
                        // Operand 2
                        comparison33.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(0), Constants.Type.NAT.getTypeName()));
                        comparison28.addOperand(comparison33);
                    
                    comparison25.addOperand(comparison28);
                }
                
                comparison240.addOperand(comparison25);
            }
        
            // Operand 2
                // '=' Comparison
                SQLWhereComparison comparison330 = new SQLWhereComparison(sql);
                comparison330.setOperator("=");
                
                // Operand 1
                SQLWhereOperandPathAttribute operand1134 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDCOMPLETADA, Constants.Type.BOOL.getTypeName());
                comparison330.setFirstOperand(operand1134);
                
                // Operand 2
                comparison330.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
                comparison240.addOperand(comparison330);
            
            comparison15.addOperand(comparison240);
        }
        
        conjunction.addOperand(comparison15);
    }


        sql.addConjunction(conjunction);
    }

    private void fSolicitudStatusAfil2(FilterRequest filterRequest, SQLSelect sql) throws SystemException{
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vNumSolicitud = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VNUMSOLICITUD) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VNUMSOLICITUD, FilterVariableRequest.STRING) : null;
        Long vTramite = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VTRAMITE) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VTRAMITE, FilterVariableRequest.NAT) : null;
        Productos voProducto = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOPRODUCTO) ? productosSrv.getByOID(new ProductosOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOPRODUCTO)))) : null;
        Planes voPlan = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOPLAN) ? planesSrv.getByOID(new PlanesOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOPLAN)))) : null;
        Long voEstado = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOESTADO) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOESTADO, FilterVariableRequest.INT) : null;
        Date vFElabIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VFELABINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VFELABINI, FilterVariableRequest.DATE) : null;
        Date vFElabFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VFELABFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VFELABFIN, FilterVariableRequest.DATE) : null;
        Date vFContratoIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VFCONTRATOINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VFCONTRATOINI, FilterVariableRequest.DATE) : null;
        Date vFContratoFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VFCONTRATOFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VFCONTRATOFIN, FilterVariableRequest.DATE) : null;
        String vCodAsesor = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VCODASESOR) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VCODASESOR, FilterVariableRequest.STRING) : null;
        TipoIdentificacion vTipoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VTIPODOC) ? tipoIdentificacionSrv.getByOID(new TipoIdentificacionOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VTIPODOC)))) : null;
        String vNumDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VNUMDOC) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VNUMDOC, FilterVariableRequest.STRING) : null;
        Regional voRegional = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOREGIONAL) ? regionalSrv.getByOID(new RegionalOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOREGIONAL)))) : null;

        // Completada = true
        // '=' Comparison
        SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
        comparison1.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDCOMPLETADA, Constants.Type.BOOL.getTypeName());
        comparison1.setFirstOperand(operand112);
        
        // Operand 2
        comparison1.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
        conjunction.addOperand(comparison1);


        // Aesolicitudes.Numerosolicitud = vNumSolicitud
        if (vNumSolicitud != null) {
            // '=' Comparison
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESNUMEROSOLICITUD, Constants.Type.STRING.getTypeName());
            comparison2.setFirstOperand(operand113);
            
            // Operand 2
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(vNumSolicitud, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison2);

        }        

        // Tramite = vTramite
        if (vTramite != null) {
            // '=' Comparison
            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
            comparison3.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand114 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDTRAMITE, Constants.Type.NAT.getTypeName());
            comparison3.setFirstOperand(operand114);
            
            // Operand 2
            comparison3.setSecondOperand(new SQLWhereOperandLiteral(vTramite, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison3);

        }        

        // Aesolicitudes.ProductoT = voProducto
        if (voProducto != null) {
            // '=' Comparison
            SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
            comparison4.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand115 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PRODUCTOT, ProductosConstants.CLASS_NAME);
            comparison4.setFirstOperand(operand115);
            
            // Operand 2
            SQLWhereOperandOVParameter operand226 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOPRODUCTO, ProductosConstants.CLASS_NAME, ProductosConstants.CLASS_NAME, voProducto.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOPRODUCTO);
            comparison4.setSecondOperand(operand226);
            conjunction.addOperand(comparison4);

        }        

        // Aesolicitudes.PlanT = voPlan
        if (voPlan != null) {
            // '=' Comparison
            SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
            comparison5.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand116 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PLANT, PlanesConstants.CLASS_NAME);
            comparison5.setFirstOperand(operand116);
            
            // Operand 2
            SQLWhereOperandOVParameter operand227 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOPLAN, PlanesConstants.CLASS_NAME, PlanesConstants.CLASS_NAME, voPlan.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOPLAN);
            comparison5.setSecondOperand(operand227);
            conjunction.addOperand(comparison5);

        }        

        // (Estado = voEstado OR (Estado = 8 AND voEstado = 1))
        if (voEstado != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison6 = new SQLWhereDisjunction();
        
            // Operand 1
                    // '=' Comparison
                    SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
                    comparison7.setOperator("=");
                    
                    // Operand 1
                    SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
                    comparison7.setFirstOperand(operand118);
                    
                    // Operand 2
                    comparison7.setSecondOperand(new SQLWhereOperandLiteral(voEstado, Constants.Type.INT.getTypeName()));
                    comparison6.addOperand(comparison7);
        
            // Operand 2
                {
                    // 'and' Logical operator
                    SQLWhereConjunction comparison9 = new SQLWhereConjunction();
                
                    // Operand 1
                            // '=' Comparison
                            SQLWhereComparison comparison10 = new SQLWhereComparison(sql);
                            comparison10.setOperator("=");
                            
                            // Operand 1
                            SQLWhereOperandPathAttribute operand1111 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
                            comparison10.setFirstOperand(operand1111);
                            
                            // Operand 2
                            comparison10.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(8), Constants.Type.NAT.getTypeName()));
                            comparison9.addOperand(comparison10);
                
                    // Operand 2
                            // '=' Comparison
                            SQLWhereComparison comparison12 = new SQLWhereComparison(sql);
                            comparison12.setOperator("=");
                            
                            // Operand 1
                            comparison12.setFirstOperand(new SQLWhereOperandLiteral(voEstado, Constants.Type.INT.getTypeName()));
                            
                            // Operand 2
                            comparison12.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(1), Constants.Type.NAT.getTypeName()));
                            comparison9.addOperand(comparison12);
                    
                    comparison6.addOperand(comparison9);
                }
            
            conjunction.addOperand(comparison6);
        }

        }        

        // Aesolicitudes.Fechaasignacion >= vFElabIni
        if (vFElabIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
            comparison7.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison7.setFirstOperand(operand118);
            
            // Operand 2
            comparison7.setSecondOperand(new SQLWhereOperandLiteral(vFElabIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison7);

        }        

        // Aesolicitudes.Fechaasignacion <= vFElabFin
        if (vFElabFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
            comparison8.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand119 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison8.setFirstOperand(operand119);
            
            // Operand 2
            comparison8.setSecondOperand(new SQLWhereOperandLiteral(vFElabFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison8);

        }        

        // FInicioContrato >= vFContratoIni
        if (vFContratoIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison9 = new SQLWhereComparison(sql);
            comparison9.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1110 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison9.setFirstOperand(operand1110);
            
            // Operand 2
            comparison9.setSecondOperand(new SQLWhereOperandLiteral(vFContratoIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison9);

        }        

        // FInicioContrato <= vFContratoFin
        if (vFContratoFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison10 = new SQLWhereComparison(sql);
            comparison10.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1111 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison10.setFirstOperand(operand1111);
            
            // Operand 2
            comparison10.setSecondOperand(new SQLWhereOperandLiteral(vFContratoFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison10);

        }        

        // Agente.Usuario LIKE "%" + vCodAsesor
        if (vCodAsesor != null) {
            // 'like' Comparison
            SQLWhereComparison comparison11 = new SQLWhereComparison(sql);
            comparison11.setOperator("like");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ATTR_NAME_FUNCIONARIOUSUARIO, Constants.Type.STRING.getTypeName());
            comparison11.setFirstOperand(operand1112);
            
            // Operand 2
            comparison11.setSecondOperand(new SQLWhereOperandLiteral("%" + vCodAsesor, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison11);

        }        

        // ((Contratante.TipoIdentificacion = vTipoDoc AND Contratante.NumIdentificacion = vNumDoc) OR (Titular.TipoIdentificacion = vTipoDoc AND Titular.NumIdentificacion = vNumDoc) OR (EXIST (Usuarios) WHERE (Usuarios.TipoIdentificacion = vTipoDoc AND Usuarios.NumIdentificacion = vNumDoc) = true))
        if (vTipoDoc != null && vNumDoc != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison12 = new SQLWhereDisjunction();
        
            // Operand 1
                {
                    // 'or' Logical operator
                    SQLWhereDisjunction comparison13 = new SQLWhereDisjunction();
                
                    // Operand 1
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison14 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison15 = new SQLWhereComparison(sql);
                                    comparison15.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1116 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison15.setFirstOperand(operand1116);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2217 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VTIPODOC);
                                    comparison15.setSecondOperand(operand2217);
                                    comparison14.addOperand(comparison15);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison17 = new SQLWhereComparison(sql);
                                    comparison17.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ATTR_NAME_CONTRATANTENUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison17.setFirstOperand(operand1118);
                                    
                                    // Operand 2
                                    comparison17.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison14.addOperand(comparison17);
                            
                            comparison13.addOperand(comparison14);
                        }
                
                    // Operand 2
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison18 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison19 = new SQLWhereComparison(sql);
                                    comparison19.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1120 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison19.setFirstOperand(operand1120);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2221 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VTIPODOC);
                                    comparison19.setSecondOperand(operand2221);
                                    comparison18.addOperand(comparison19);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison21 = new SQLWhereComparison(sql);
                                    comparison21.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1122 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ATTR_NAME_TITULARNUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison21.setFirstOperand(operand1122);
                                    
                                    // Operand 2
                                    comparison21.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison18.addOperand(comparison21);
                            
                            comparison13.addOperand(comparison18);
                        }
                    
                    comparison12.addOperand(comparison13);
                }
        
            // Operand 2
            // 'exist' Association operator
            SQLWhereOperandSubQuery operandSubQueryAssocOperator083 = new SQLWhereOperandSubQuery(repository.assocOperator083Range1Query(vTipoDoc, vNumDoc, sql));
            comparison12.addOperand(operandSubQueryAssocOperator083);
            
            conjunction.addOperand(comparison12);
        }

        }        

        // Agente.Sucursal.Regional = voRegional
        if (voRegional != null) {
            // '=' Comparison
            SQLWhereComparison comparison13 = new SQLWhereComparison(sql);
            comparison13.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand1114 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ROLE_NAME_SUCURSAL + "." + SucursalConstants.ROLE_NAME_REGIONAL, RegionalConstants.CLASS_NAME);
            comparison13.setFirstOperand(operand1114);
            
            // Operand 2
            SQLWhereOperandOVParameter operand2215 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOREGIONAL, RegionalConstants.CLASS_NAME, RegionalConstants.CLASS_NAME, voRegional.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL2_VOREGIONAL);
            comparison13.setSecondOperand(operand2215);
            conjunction.addOperand(comparison13);

        }        

        // Aesolicitudes.Tiposolicitud = "E"
        // '=' Comparison
        SQLWhereComparison comparison14 = new SQLWhereComparison(sql);
        comparison14.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1115 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESTIPOSOLICITUD, Constants.Type.STRING.getTypeName());
        comparison14.setFirstOperand(operand1115);
        
        // Operand 2
        comparison14.setSecondOperand(new SQLWhereOperandLiteral("E", Constants.Type.STRING.getTypeName()));
        conjunction.addOperand(comparison14);


        // Estado <> 4
        // '<>' Comparison
        SQLWhereComparison comparison15 = new SQLWhereComparison(sql);
        comparison15.setOperator("<>");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1116 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
        comparison15.setFirstOperand(operand1116);
        
        // Operand 2
        comparison15.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(4), Constants.Type.NAT.getTypeName()));
        conjunction.addOperand(comparison15);


        // FechaTerminada = NULL
        // '=' Comparison
        SQLWhereComparison comparison16 = new SQLWhereComparison(sql);
        comparison16.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFECHATERMINADA, Constants.Type.DATE.getTypeName());
        comparison16.setFirstOperand(operand1117);
        
        // Operand 2
        comparison16.setSecondOperand(new SQLWhereOperandNull());
        conjunction.addOperand(comparison16);


        // EstadoDoc = 3
        // '=' Comparison
        SQLWhereComparison comparison17 = new SQLWhereComparison(sql);
        comparison17.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADODOC, Constants.Type.NAT.getTypeName());
        comparison17.setFirstOperand(operand1118);
        
        // Operand 2
        comparison17.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(3), Constants.Type.NAT.getTypeName()));
        conjunction.addOperand(comparison17);


        sql.addConjunction(conjunction);
    }

    private void fSolicitudStatusAfil3(FilterRequest filterRequest, SQLSelect sql) throws SystemException{
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vNumSolicitud = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VNUMSOLICITUD) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VNUMSOLICITUD, FilterVariableRequest.STRING) : null;
        Long vTramite = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VTRAMITE) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VTRAMITE, FilterVariableRequest.NAT) : null;
        Productos voProducto = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOPRODUCTO) ? productosSrv.getByOID(new ProductosOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOPRODUCTO)))) : null;
        Planes voPlan = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOPLAN) ? planesSrv.getByOID(new PlanesOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOPLAN)))) : null;
        Long voEstado = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOESTADO) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOESTADO, FilterVariableRequest.INT) : null;
        Date vFElabIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VFELABINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VFELABINI, FilterVariableRequest.DATE) : null;
        Date vFElabFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VFELABFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VFELABFIN, FilterVariableRequest.DATE) : null;
        Date vFContratoIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VFCONTRATOINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VFCONTRATOINI, FilterVariableRequest.DATE) : null;
        Date vFContratoFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VFCONTRATOFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VFCONTRATOFIN, FilterVariableRequest.DATE) : null;
        String vCodAsesor = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VCODASESOR) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VCODASESOR, FilterVariableRequest.STRING) : null;
        TipoIdentificacion vTipoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VTIPODOC) ? tipoIdentificacionSrv.getByOID(new TipoIdentificacionOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VTIPODOC)))) : null;
        String vNumDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VNUMDOC) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VNUMDOC, FilterVariableRequest.STRING) : null;
        Regional voRegional = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOREGIONAL) ? regionalSrv.getByOID(new RegionalOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOREGIONAL)))) : null;

        // Completada = true
        // '=' Comparison
        SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
        comparison1.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDCOMPLETADA, Constants.Type.BOOL.getTypeName());
        comparison1.setFirstOperand(operand112);
        
        // Operand 2
        comparison1.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
        conjunction.addOperand(comparison1);


        // Aesolicitudes.Numerosolicitud = vNumSolicitud
        if (vNumSolicitud != null) {
            // '=' Comparison
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESNUMEROSOLICITUD, Constants.Type.STRING.getTypeName());
            comparison2.setFirstOperand(operand113);
            
            // Operand 2
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(vNumSolicitud, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison2);

        }        

        // Tramite = vTramite
        if (vTramite != null) {
            // '=' Comparison
            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
            comparison3.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand114 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDTRAMITE, Constants.Type.NAT.getTypeName());
            comparison3.setFirstOperand(operand114);
            
            // Operand 2
            comparison3.setSecondOperand(new SQLWhereOperandLiteral(vTramite, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison3);

        }        

        // Aesolicitudes.ProductoT = voProducto
        if (voProducto != null) {
            // '=' Comparison
            SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
            comparison4.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand115 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PRODUCTOT, ProductosConstants.CLASS_NAME);
            comparison4.setFirstOperand(operand115);
            
            // Operand 2
            SQLWhereOperandOVParameter operand226 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOPRODUCTO, ProductosConstants.CLASS_NAME, ProductosConstants.CLASS_NAME, voProducto.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOPRODUCTO);
            comparison4.setSecondOperand(operand226);
            conjunction.addOperand(comparison4);

        }        

        // Aesolicitudes.PlanT = voPlan
        if (voPlan != null) {
            // '=' Comparison
            SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
            comparison5.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand116 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PLANT, PlanesConstants.CLASS_NAME);
            comparison5.setFirstOperand(operand116);
            
            // Operand 2
            SQLWhereOperandOVParameter operand227 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOPLAN, PlanesConstants.CLASS_NAME, PlanesConstants.CLASS_NAME, voPlan.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOPLAN);
            comparison5.setSecondOperand(operand227);
            conjunction.addOperand(comparison5);

        }        

        // (Estado = voEstado OR (Estado = 8 AND voEstado = 1))
        if (voEstado != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison6 = new SQLWhereDisjunction();
        
            // Operand 1
                    // '=' Comparison
                    SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
                    comparison7.setOperator("=");
                    
                    // Operand 1
                    SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
                    comparison7.setFirstOperand(operand118);
                    
                    // Operand 2
                    comparison7.setSecondOperand(new SQLWhereOperandLiteral(voEstado, Constants.Type.INT.getTypeName()));
                    comparison6.addOperand(comparison7);
        
            // Operand 2
                {
                    // 'and' Logical operator
                    SQLWhereConjunction comparison9 = new SQLWhereConjunction();
                
                    // Operand 1
                            // '=' Comparison
                            SQLWhereComparison comparison10 = new SQLWhereComparison(sql);
                            comparison10.setOperator("=");
                            
                            // Operand 1
                            SQLWhereOperandPathAttribute operand1111 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
                            comparison10.setFirstOperand(operand1111);
                            
                            // Operand 2
                            comparison10.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(8), Constants.Type.NAT.getTypeName()));
                            comparison9.addOperand(comparison10);
                
                    // Operand 2
                            // '=' Comparison
                            SQLWhereComparison comparison12 = new SQLWhereComparison(sql);
                            comparison12.setOperator("=");
                            
                            // Operand 1
                            comparison12.setFirstOperand(new SQLWhereOperandLiteral(voEstado, Constants.Type.INT.getTypeName()));
                            
                            // Operand 2
                            comparison12.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(1), Constants.Type.NAT.getTypeName()));
                            comparison9.addOperand(comparison12);
                    
                    comparison6.addOperand(comparison9);
                }
            
            conjunction.addOperand(comparison6);
        }

        }        

        // Aesolicitudes.Fechaasignacion >= vFElabIni
        if (vFElabIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
            comparison7.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison7.setFirstOperand(operand118);
            
            // Operand 2
            comparison7.setSecondOperand(new SQLWhereOperandLiteral(vFElabIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison7);

        }        

        // Aesolicitudes.Fechaasignacion <= vFElabFin
        if (vFElabFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
            comparison8.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand119 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison8.setFirstOperand(operand119);
            
            // Operand 2
            comparison8.setSecondOperand(new SQLWhereOperandLiteral(vFElabFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison8);

        }        

        // FInicioContrato >= vFContratoIni
        if (vFContratoIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison9 = new SQLWhereComparison(sql);
            comparison9.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1110 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison9.setFirstOperand(operand1110);
            
            // Operand 2
            comparison9.setSecondOperand(new SQLWhereOperandLiteral(vFContratoIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison9);

        }        

        // FInicioContrato <= vFContratoFin
        if (vFContratoFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison10 = new SQLWhereComparison(sql);
            comparison10.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1111 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison10.setFirstOperand(operand1111);
            
            // Operand 2
            comparison10.setSecondOperand(new SQLWhereOperandLiteral(vFContratoFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison10);

        }        

        // Agente.Usuario LIKE "%" + vCodAsesor
        if (vCodAsesor != null) {
            // 'like' Comparison
            SQLWhereComparison comparison11 = new SQLWhereComparison(sql);
            comparison11.setOperator("like");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ATTR_NAME_FUNCIONARIOUSUARIO, Constants.Type.STRING.getTypeName());
            comparison11.setFirstOperand(operand1112);
            
            // Operand 2
            comparison11.setSecondOperand(new SQLWhereOperandLiteral("%" + vCodAsesor, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison11);

        }        

        // ((Contratante.TipoIdentificacion = vTipoDoc AND Contratante.NumIdentificacion = vNumDoc) OR (Titular.TipoIdentificacion = vTipoDoc AND Titular.NumIdentificacion = vNumDoc) OR (EXIST (Usuarios) WHERE (Usuarios.TipoIdentificacion = vTipoDoc AND Usuarios.NumIdentificacion = vNumDoc) = true))
        if (vTipoDoc != null && vNumDoc != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison12 = new SQLWhereDisjunction();
        
            // Operand 1
                {
                    // 'or' Logical operator
                    SQLWhereDisjunction comparison13 = new SQLWhereDisjunction();
                
                    // Operand 1
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison14 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison15 = new SQLWhereComparison(sql);
                                    comparison15.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1116 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison15.setFirstOperand(operand1116);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2217 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VTIPODOC);
                                    comparison15.setSecondOperand(operand2217);
                                    comparison14.addOperand(comparison15);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison17 = new SQLWhereComparison(sql);
                                    comparison17.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ATTR_NAME_CONTRATANTENUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison17.setFirstOperand(operand1118);
                                    
                                    // Operand 2
                                    comparison17.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison14.addOperand(comparison17);
                            
                            comparison13.addOperand(comparison14);
                        }
                
                    // Operand 2
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison18 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison19 = new SQLWhereComparison(sql);
                                    comparison19.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1120 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison19.setFirstOperand(operand1120);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2221 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VTIPODOC);
                                    comparison19.setSecondOperand(operand2221);
                                    comparison18.addOperand(comparison19);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison21 = new SQLWhereComparison(sql);
                                    comparison21.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1122 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ATTR_NAME_TITULARNUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison21.setFirstOperand(operand1122);
                                    
                                    // Operand 2
                                    comparison21.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison18.addOperand(comparison21);
                            
                            comparison13.addOperand(comparison18);
                        }
                    
                    comparison12.addOperand(comparison13);
                }
        
            // Operand 2
            // 'exist' Association operator
            SQLWhereOperandSubQuery operandSubQueryAssocOperator084 = new SQLWhereOperandSubQuery(repository.assocOperator084Range1Query(vTipoDoc, vNumDoc, sql));
            comparison12.addOperand(operandSubQueryAssocOperator084);
            
            conjunction.addOperand(comparison12);
        }

        }        

        // Agente.Sucursal.Regional = voRegional
        if (voRegional != null) {
            // '=' Comparison
            SQLWhereComparison comparison13 = new SQLWhereComparison(sql);
            comparison13.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand1114 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ROLE_NAME_SUCURSAL + "." + SucursalConstants.ROLE_NAME_REGIONAL, RegionalConstants.CLASS_NAME);
            comparison13.setFirstOperand(operand1114);
            
            // Operand 2
            SQLWhereOperandOVParameter operand2215 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOREGIONAL, RegionalConstants.CLASS_NAME, RegionalConstants.CLASS_NAME, voRegional.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSAFIL3_VOREGIONAL);
            comparison13.setSecondOperand(operand2215);
            conjunction.addOperand(comparison13);

        }        

        // Aesolicitudes.Tiposolicitud = "E"
        // '=' Comparison
        SQLWhereComparison comparison14 = new SQLWhereComparison(sql);
        comparison14.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1115 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESTIPOSOLICITUD, Constants.Type.STRING.getTypeName());
        comparison14.setFirstOperand(operand1115);
        
        // Operand 2
        comparison14.setSecondOperand(new SQLWhereOperandLiteral("E", Constants.Type.STRING.getTypeName()));
        conjunction.addOperand(comparison14);


        // FechaTerminada <> NULL
        // '<>' Comparison
        SQLWhereComparison comparison15 = new SQLWhereComparison(sql);
        comparison15.setOperator("<>");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1116 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFECHATERMINADA, Constants.Type.DATE.getTypeName());
        comparison15.setFirstOperand(operand1116);
        
        // Operand 2
        comparison15.setSecondOperand(new SQLWhereOperandNull());
        conjunction.addOperand(comparison15);


        // EstadoDoc = 2
        // '=' Comparison
        SQLWhereComparison comparison16 = new SQLWhereComparison(sql);
        comparison16.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADODOC, Constants.Type.NAT.getTypeName());
        comparison16.setFirstOperand(operand1117);
        
        // Operand 2
        comparison16.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(2), Constants.Type.NAT.getTypeName()));
        conjunction.addOperand(comparison16);


        sql.addConjunction(conjunction);
    }

    private void fSolicitudEstatusAreaMed1(FilterRequest filterRequest, SQLSelect sql) throws SystemException{
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vNumSolicitud = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VNUMSOLICITUD) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VNUMSOLICITUD, FilterVariableRequest.STRING) : null;
        Long vTramite = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VTRAMITE) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VTRAMITE, FilterVariableRequest.NAT) : null;
        Productos voProducto = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOPRODUCTO) ? productosSrv.getByOID(new ProductosOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOPRODUCTO)))) : null;
        Planes voPlan = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOPLAN) ? planesSrv.getByOID(new PlanesOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOPLAN)))) : null;
        Long voEstadoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOESTADODOC) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOESTADODOC, FilterVariableRequest.NAT) : null;
        Date vFElabIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VFELABINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VFELABINI, FilterVariableRequest.DATE) : null;
        Date vFElabFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VFELABFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VFELABFIN, FilterVariableRequest.DATE) : null;
        Date vFContratoIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VFCONTRATOINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VFCONTRATOINI, FilterVariableRequest.DATE) : null;
        Date vFContratoFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VFCONTRATOFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VFCONTRATOFIN, FilterVariableRequest.DATE) : null;
        String vCodAsesor = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VCODASESOR) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VCODASESOR, FilterVariableRequest.STRING) : null;
        TipoIdentificacion vTipoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VTIPODOC) ? tipoIdentificacionSrv.getByOID(new TipoIdentificacionOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VTIPODOC)))) : null;
        String vNumDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VNUMDOC) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VNUMDOC, FilterVariableRequest.STRING) : null;
        Regional voRegional = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOREGIONAL) ? regionalSrv.getByOID(new RegionalOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOREGIONAL)))) : null;

        // Completada = true
        // '=' Comparison
        SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
        comparison1.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDCOMPLETADA, Constants.Type.BOOL.getTypeName());
        comparison1.setFirstOperand(operand112);
        
        // Operand 2
        comparison1.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
        conjunction.addOperand(comparison1);


        // Aesolicitudes.Numerosolicitud = vNumSolicitud
        if (vNumSolicitud != null) {
            // '=' Comparison
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESNUMEROSOLICITUD, Constants.Type.STRING.getTypeName());
            comparison2.setFirstOperand(operand113);
            
            // Operand 2
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(vNumSolicitud, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison2);

        }        

        // Tramite = vTramite
        if (vTramite != null) {
            // '=' Comparison
            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
            comparison3.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand114 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDTRAMITE, Constants.Type.NAT.getTypeName());
            comparison3.setFirstOperand(operand114);
            
            // Operand 2
            comparison3.setSecondOperand(new SQLWhereOperandLiteral(vTramite, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison3);

        }        

        // Aesolicitudes.ProductoT = voProducto
        if (voProducto != null) {
            // '=' Comparison
            SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
            comparison4.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand115 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PRODUCTOT, ProductosConstants.CLASS_NAME);
            comparison4.setFirstOperand(operand115);
            
            // Operand 2
            SQLWhereOperandOVParameter operand226 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOPRODUCTO, ProductosConstants.CLASS_NAME, ProductosConstants.CLASS_NAME, voProducto.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOPRODUCTO);
            comparison4.setSecondOperand(operand226);
            conjunction.addOperand(comparison4);

        }        

        // Aesolicitudes.PlanT = voPlan
        if (voPlan != null) {
            // '=' Comparison
            SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
            comparison5.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand116 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PLANT, PlanesConstants.CLASS_NAME);
            comparison5.setFirstOperand(operand116);
            
            // Operand 2
            SQLWhereOperandOVParameter operand227 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOPLAN, PlanesConstants.CLASS_NAME, PlanesConstants.CLASS_NAME, voPlan.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOPLAN);
            comparison5.setSecondOperand(operand227);
            conjunction.addOperand(comparison5);

        }        

        // EstadoDoc = voEstadoDoc
        if (voEstadoDoc != null) {
            // '=' Comparison
            SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
            comparison6.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADODOC, Constants.Type.NAT.getTypeName());
            comparison6.setFirstOperand(operand117);
            
            // Operand 2
            comparison6.setSecondOperand(new SQLWhereOperandLiteral(voEstadoDoc, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison6);

        }        

        // Aesolicitudes.Fechaasignacion >= vFElabIni
        if (vFElabIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
            comparison7.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison7.setFirstOperand(operand118);
            
            // Operand 2
            comparison7.setSecondOperand(new SQLWhereOperandLiteral(vFElabIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison7);

        }        

        // Aesolicitudes.Fechaasignacion <= vFElabFin
        if (vFElabFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
            comparison8.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand119 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison8.setFirstOperand(operand119);
            
            // Operand 2
            comparison8.setSecondOperand(new SQLWhereOperandLiteral(vFElabFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison8);

        }        

        // FInicioContrato >= vFContratoIni
        if (vFContratoIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison9 = new SQLWhereComparison(sql);
            comparison9.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1110 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison9.setFirstOperand(operand1110);
            
            // Operand 2
            comparison9.setSecondOperand(new SQLWhereOperandLiteral(vFContratoIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison9);

        }        

        // FInicioContrato <= vFContratoFin
        if (vFContratoFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison10 = new SQLWhereComparison(sql);
            comparison10.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1111 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison10.setFirstOperand(operand1111);
            
            // Operand 2
            comparison10.setSecondOperand(new SQLWhereOperandLiteral(vFContratoFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison10);

        }        

        // Agente.Usuario LIKE "%" + vCodAsesor
        if (vCodAsesor != null) {
            // 'like' Comparison
            SQLWhereComparison comparison11 = new SQLWhereComparison(sql);
            comparison11.setOperator("like");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ATTR_NAME_FUNCIONARIOUSUARIO, Constants.Type.STRING.getTypeName());
            comparison11.setFirstOperand(operand1112);
            
            // Operand 2
            comparison11.setSecondOperand(new SQLWhereOperandLiteral("%" + vCodAsesor, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison11);

        }        

        // ((Contratante.TipoIdentificacion = vTipoDoc AND Contratante.NumIdentificacion = vNumDoc) OR (Titular.TipoIdentificacion = vTipoDoc AND Titular.NumIdentificacion = vNumDoc) OR (EXIST (Usuarios) WHERE (Usuarios.TipoIdentificacion = vTipoDoc AND Usuarios.NumIdentificacion = vNumDoc) = true))
        if (vTipoDoc != null && vNumDoc != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison12 = new SQLWhereDisjunction();
        
            // Operand 1
                {
                    // 'or' Logical operator
                    SQLWhereDisjunction comparison13 = new SQLWhereDisjunction();
                
                    // Operand 1
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison14 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison15 = new SQLWhereComparison(sql);
                                    comparison15.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1116 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison15.setFirstOperand(operand1116);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2217 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VTIPODOC);
                                    comparison15.setSecondOperand(operand2217);
                                    comparison14.addOperand(comparison15);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison17 = new SQLWhereComparison(sql);
                                    comparison17.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ATTR_NAME_CONTRATANTENUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison17.setFirstOperand(operand1118);
                                    
                                    // Operand 2
                                    comparison17.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison14.addOperand(comparison17);
                            
                            comparison13.addOperand(comparison14);
                        }
                
                    // Operand 2
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison18 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison19 = new SQLWhereComparison(sql);
                                    comparison19.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1120 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison19.setFirstOperand(operand1120);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2221 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VTIPODOC);
                                    comparison19.setSecondOperand(operand2221);
                                    comparison18.addOperand(comparison19);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison21 = new SQLWhereComparison(sql);
                                    comparison21.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1122 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ATTR_NAME_TITULARNUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison21.setFirstOperand(operand1122);
                                    
                                    // Operand 2
                                    comparison21.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison18.addOperand(comparison21);
                            
                            comparison13.addOperand(comparison18);
                        }
                    
                    comparison12.addOperand(comparison13);
                }
        
            // Operand 2
            // 'exist' Association operator
            SQLWhereOperandSubQuery operandSubQueryAssocOperator085 = new SQLWhereOperandSubQuery(repository.assocOperator085Range1Query(vTipoDoc, vNumDoc, sql));
            comparison12.addOperand(operandSubQueryAssocOperator085);
            
            conjunction.addOperand(comparison12);
        }

        }        

        // Agente.Sucursal.Regional = voRegional
        if (voRegional != null) {
            // '=' Comparison
            SQLWhereComparison comparison13 = new SQLWhereComparison(sql);
            comparison13.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand1114 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ROLE_NAME_SUCURSAL + "." + SucursalConstants.ROLE_NAME_REGIONAL, RegionalConstants.CLASS_NAME);
            comparison13.setFirstOperand(operand1114);
            
            // Operand 2
            SQLWhereOperandOVParameter operand2215 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOREGIONAL, RegionalConstants.CLASS_NAME, RegionalConstants.CLASS_NAME, voRegional.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED1_VOREGIONAL);
            comparison13.setSecondOperand(operand2215);
            conjunction.addOperand(comparison13);

        }        

        // Aesolicitudes.Tiposolicitud = "E"
        // '=' Comparison
        SQLWhereComparison comparison14 = new SQLWhereComparison(sql);
        comparison14.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1115 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESTIPOSOLICITUD, Constants.Type.STRING.getTypeName());
        comparison14.setFirstOperand(operand1115);
        
        // Operand 2
        comparison14.setSecondOperand(new SQLWhereOperandLiteral("E", Constants.Type.STRING.getTypeName()));
        conjunction.addOperand(comparison14);


        // Estado = 5
        // '=' Comparison
        SQLWhereComparison comparison15 = new SQLWhereComparison(sql);
        comparison15.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1116 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
        comparison15.setFirstOperand(operand1116);
        
        // Operand 2
        comparison15.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(5), Constants.Type.NAT.getTypeName()));
        conjunction.addOperand(comparison15);


        // FechaTerminada = NULL
        // '=' Comparison
        SQLWhereComparison comparison16 = new SQLWhereComparison(sql);
        comparison16.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFECHATERMINADA, Constants.Type.DATE.getTypeName());
        comparison16.setFirstOperand(operand1117);
        
        // Operand 2
        comparison16.setSecondOperand(new SQLWhereOperandNull());
        conjunction.addOperand(comparison16);


        sql.addConjunction(conjunction);
    }

    private void fSolicitudEstatusAreaMed2(FilterRequest filterRequest, SQLSelect sql) throws SystemException{
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vNumSolicitud = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VNUMSOLICITUD) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VNUMSOLICITUD, FilterVariableRequest.STRING) : null;
        Long vTramite = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VTRAMITE) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VTRAMITE, FilterVariableRequest.NAT) : null;
        Productos voProducto = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOPRODUCTO) ? productosSrv.getByOID(new ProductosOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOPRODUCTO)))) : null;
        Planes voPlan = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOPLAN) ? planesSrv.getByOID(new PlanesOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOPLAN)))) : null;
        Long voEstadoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOESTADODOC) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOESTADODOC, FilterVariableRequest.NAT) : null;
        Date vFElabIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VFELABINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VFELABINI, FilterVariableRequest.DATE) : null;
        Date vFElabFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VFELABFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VFELABFIN, FilterVariableRequest.DATE) : null;
        Date vFContratoIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VFCONTRATOINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VFCONTRATOINI, FilterVariableRequest.DATE) : null;
        Date vFContratoFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VFCONTRATOFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VFCONTRATOFIN, FilterVariableRequest.DATE) : null;
        String vCodAsesor = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VCODASESOR) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VCODASESOR, FilterVariableRequest.STRING) : null;
        TipoIdentificacion vTipoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VTIPODOC) ? tipoIdentificacionSrv.getByOID(new TipoIdentificacionOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VTIPODOC)))) : null;
        String vNumDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VNUMDOC) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VNUMDOC, FilterVariableRequest.STRING) : null;
        Regional voRegional = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOREGIONAL) ? regionalSrv.getByOID(new RegionalOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOREGIONAL)))) : null;

        // Completada = true
        // '=' Comparison
        SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
        comparison1.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDCOMPLETADA, Constants.Type.BOOL.getTypeName());
        comparison1.setFirstOperand(operand112);
        
        // Operand 2
        comparison1.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
        conjunction.addOperand(comparison1);


        // Aesolicitudes.Numerosolicitud = vNumSolicitud
        if (vNumSolicitud != null) {
            // '=' Comparison
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESNUMEROSOLICITUD, Constants.Type.STRING.getTypeName());
            comparison2.setFirstOperand(operand113);
            
            // Operand 2
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(vNumSolicitud, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison2);

        }        

        // Tramite = vTramite
        if (vTramite != null) {
            // '=' Comparison
            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
            comparison3.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand114 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDTRAMITE, Constants.Type.NAT.getTypeName());
            comparison3.setFirstOperand(operand114);
            
            // Operand 2
            comparison3.setSecondOperand(new SQLWhereOperandLiteral(vTramite, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison3);

        }        

        // Aesolicitudes.ProductoT = voProducto
        if (voProducto != null) {
            // '=' Comparison
            SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
            comparison4.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand115 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PRODUCTOT, ProductosConstants.CLASS_NAME);
            comparison4.setFirstOperand(operand115);
            
            // Operand 2
            SQLWhereOperandOVParameter operand226 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOPRODUCTO, ProductosConstants.CLASS_NAME, ProductosConstants.CLASS_NAME, voProducto.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOPRODUCTO);
            comparison4.setSecondOperand(operand226);
            conjunction.addOperand(comparison4);

        }        

        // Aesolicitudes.PlanT = voPlan
        if (voPlan != null) {
            // '=' Comparison
            SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
            comparison5.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand116 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PLANT, PlanesConstants.CLASS_NAME);
            comparison5.setFirstOperand(operand116);
            
            // Operand 2
            SQLWhereOperandOVParameter operand227 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOPLAN, PlanesConstants.CLASS_NAME, PlanesConstants.CLASS_NAME, voPlan.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOPLAN);
            comparison5.setSecondOperand(operand227);
            conjunction.addOperand(comparison5);

        }        

        // EstadoDoc = voEstadoDoc
        if (voEstadoDoc != null) {
            // '=' Comparison
            SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
            comparison6.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADODOC, Constants.Type.NAT.getTypeName());
            comparison6.setFirstOperand(operand117);
            
            // Operand 2
            comparison6.setSecondOperand(new SQLWhereOperandLiteral(voEstadoDoc, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison6);

        }        

        // Aesolicitudes.Fechaasignacion >= vFElabIni
        if (vFElabIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
            comparison7.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison7.setFirstOperand(operand118);
            
            // Operand 2
            comparison7.setSecondOperand(new SQLWhereOperandLiteral(vFElabIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison7);

        }        

        // Aesolicitudes.Fechaasignacion <= vFElabFin
        if (vFElabFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
            comparison8.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand119 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison8.setFirstOperand(operand119);
            
            // Operand 2
            comparison8.setSecondOperand(new SQLWhereOperandLiteral(vFElabFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison8);

        }        

        // FInicioContrato >= vFContratoIni
        if (vFContratoIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison9 = new SQLWhereComparison(sql);
            comparison9.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1110 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison9.setFirstOperand(operand1110);
            
            // Operand 2
            comparison9.setSecondOperand(new SQLWhereOperandLiteral(vFContratoIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison9);

        }        

        // FInicioContrato <= vFContratoFin
        if (vFContratoFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison10 = new SQLWhereComparison(sql);
            comparison10.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1111 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison10.setFirstOperand(operand1111);
            
            // Operand 2
            comparison10.setSecondOperand(new SQLWhereOperandLiteral(vFContratoFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison10);

        }        

        // Agente.Usuario LIKE "%" + vCodAsesor
        if (vCodAsesor != null) {
            // 'like' Comparison
            SQLWhereComparison comparison11 = new SQLWhereComparison(sql);
            comparison11.setOperator("like");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ATTR_NAME_FUNCIONARIOUSUARIO, Constants.Type.STRING.getTypeName());
            comparison11.setFirstOperand(operand1112);
            
            // Operand 2
            comparison11.setSecondOperand(new SQLWhereOperandLiteral("%" + vCodAsesor, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison11);

        }        

        // ((Contratante.TipoIdentificacion = vTipoDoc AND Contratante.NumIdentificacion = vNumDoc) OR (Titular.TipoIdentificacion = vTipoDoc AND Titular.NumIdentificacion = vNumDoc) OR (EXIST (Usuarios) WHERE (Usuarios.TipoIdentificacion = vTipoDoc AND Usuarios.NumIdentificacion = vNumDoc) = true))
        if (vTipoDoc != null && vNumDoc != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison12 = new SQLWhereDisjunction();
        
            // Operand 1
                {
                    // 'or' Logical operator
                    SQLWhereDisjunction comparison13 = new SQLWhereDisjunction();
                
                    // Operand 1
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison14 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison15 = new SQLWhereComparison(sql);
                                    comparison15.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1116 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison15.setFirstOperand(operand1116);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2217 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VTIPODOC);
                                    comparison15.setSecondOperand(operand2217);
                                    comparison14.addOperand(comparison15);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison17 = new SQLWhereComparison(sql);
                                    comparison17.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ATTR_NAME_CONTRATANTENUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison17.setFirstOperand(operand1118);
                                    
                                    // Operand 2
                                    comparison17.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison14.addOperand(comparison17);
                            
                            comparison13.addOperand(comparison14);
                        }
                
                    // Operand 2
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison18 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison19 = new SQLWhereComparison(sql);
                                    comparison19.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1120 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison19.setFirstOperand(operand1120);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2221 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VTIPODOC);
                                    comparison19.setSecondOperand(operand2221);
                                    comparison18.addOperand(comparison19);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison21 = new SQLWhereComparison(sql);
                                    comparison21.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1122 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ATTR_NAME_TITULARNUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison21.setFirstOperand(operand1122);
                                    
                                    // Operand 2
                                    comparison21.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison18.addOperand(comparison21);
                            
                            comparison13.addOperand(comparison18);
                        }
                    
                    comparison12.addOperand(comparison13);
                }
        
            // Operand 2
            // 'exist' Association operator
            SQLWhereOperandSubQuery operandSubQueryAssocOperator086 = new SQLWhereOperandSubQuery(repository.assocOperator086Range1Query(vTipoDoc, vNumDoc, sql));
            comparison12.addOperand(operandSubQueryAssocOperator086);
            
            conjunction.addOperand(comparison12);
        }

        }        

        // Agente.Sucursal.Regional = voRegional
        if (voRegional != null) {
            // '=' Comparison
            SQLWhereComparison comparison13 = new SQLWhereComparison(sql);
            comparison13.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand1114 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ROLE_NAME_SUCURSAL + "." + SucursalConstants.ROLE_NAME_REGIONAL, RegionalConstants.CLASS_NAME);
            comparison13.setFirstOperand(operand1114);
            
            // Operand 2
            SQLWhereOperandOVParameter operand2215 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOREGIONAL, RegionalConstants.CLASS_NAME, RegionalConstants.CLASS_NAME, voRegional.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED2_VOREGIONAL);
            comparison13.setSecondOperand(operand2215);
            conjunction.addOperand(comparison13);

        }        

        // Aesolicitudes.Tiposolicitud = "E"
        // '=' Comparison
        SQLWhereComparison comparison14 = new SQLWhereComparison(sql);
        comparison14.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1115 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESTIPOSOLICITUD, Constants.Type.STRING.getTypeName());
        comparison14.setFirstOperand(operand1115);
        
        // Operand 2
        comparison14.setSecondOperand(new SQLWhereOperandLiteral("E", Constants.Type.STRING.getTypeName()));
        conjunction.addOperand(comparison14);


        // Estado = 6
        // '=' Comparison
        SQLWhereComparison comparison15 = new SQLWhereComparison(sql);
        comparison15.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1116 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
        comparison15.setFirstOperand(operand1116);
        
        // Operand 2
        comparison15.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(6), Constants.Type.NAT.getTypeName()));
        conjunction.addOperand(comparison15);


        // FechaTerminada = NULL
        // '=' Comparison
        SQLWhereComparison comparison16 = new SQLWhereComparison(sql);
        comparison16.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFECHATERMINADA, Constants.Type.DATE.getTypeName());
        comparison16.setFirstOperand(operand1117);
        
        // Operand 2
        comparison16.setSecondOperand(new SQLWhereOperandNull());
        conjunction.addOperand(comparison16);


        sql.addConjunction(conjunction);
    }

    private void fSolicitudEstatusAreaMed3(FilterRequest filterRequest, SQLSelect sql) throws SystemException{
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vNumSolicitud = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VNUMSOLICITUD) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VNUMSOLICITUD, FilterVariableRequest.STRING) : null;
        Long vTramite = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VTRAMITE) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VTRAMITE, FilterVariableRequest.NAT) : null;
        Productos voProducto = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOPRODUCTO) ? productosSrv.getByOID(new ProductosOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOPRODUCTO)))) : null;
        Planes voPlan = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOPLAN) ? planesSrv.getByOID(new PlanesOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOPLAN)))) : null;
        Long voEstadoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOESTADODOC) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOESTADODOC, FilterVariableRequest.NAT) : null;
        Date vFElabIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VFELABINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VFELABINI, FilterVariableRequest.DATE) : null;
        Date vFElabFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VFELABFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VFELABFIN, FilterVariableRequest.DATE) : null;
        Date vFContratoIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VFCONTRATOINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VFCONTRATOINI, FilterVariableRequest.DATE) : null;
        Date vFContratoFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VFCONTRATOFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VFCONTRATOFIN, FilterVariableRequest.DATE) : null;
        String vCodAsesor = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VCODASESOR) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VCODASESOR, FilterVariableRequest.STRING) : null;
        TipoIdentificacion vTipoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VTIPODOC) ? tipoIdentificacionSrv.getByOID(new TipoIdentificacionOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VTIPODOC)))) : null;
        String vNumDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VNUMDOC) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VNUMDOC, FilterVariableRequest.STRING) : null;
        Regional voRegional = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOREGIONAL) ? regionalSrv.getByOID(new RegionalOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOREGIONAL)))) : null;

        // Aesolicitudes.Numerosolicitud = vNumSolicitud
        if (vNumSolicitud != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESNUMEROSOLICITUD, Constants.Type.STRING.getTypeName());
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(vNumSolicitud, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison1);

        }        

        // Tramite = vTramite
        if (vTramite != null) {
            // '=' Comparison
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDTRAMITE, Constants.Type.NAT.getTypeName());
            comparison2.setFirstOperand(operand113);
            
            // Operand 2
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(vTramite, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison2);

        }        

        // Aesolicitudes.ProductoT = voProducto
        if (voProducto != null) {
            // '=' Comparison
            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
            comparison3.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand114 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PRODUCTOT, ProductosConstants.CLASS_NAME);
            comparison3.setFirstOperand(operand114);
            
            // Operand 2
            SQLWhereOperandOVParameter operand225 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOPRODUCTO, ProductosConstants.CLASS_NAME, ProductosConstants.CLASS_NAME, voProducto.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOPRODUCTO);
            comparison3.setSecondOperand(operand225);
            conjunction.addOperand(comparison3);

        }        

        // Aesolicitudes.PlanT = voPlan
        if (voPlan != null) {
            // '=' Comparison
            SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
            comparison4.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand115 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PLANT, PlanesConstants.CLASS_NAME);
            comparison4.setFirstOperand(operand115);
            
            // Operand 2
            SQLWhereOperandOVParameter operand226 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOPLAN, PlanesConstants.CLASS_NAME, PlanesConstants.CLASS_NAME, voPlan.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOPLAN);
            comparison4.setSecondOperand(operand226);
            conjunction.addOperand(comparison4);

        }        

        // EstadoDoc = voEstadoDoc
        if (voEstadoDoc != null) {
            // '=' Comparison
            SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
            comparison5.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand116 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADODOC, Constants.Type.NAT.getTypeName());
            comparison5.setFirstOperand(operand116);
            
            // Operand 2
            comparison5.setSecondOperand(new SQLWhereOperandLiteral(voEstadoDoc, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison5);

        }        

        // Aesolicitudes.Fechaasignacion >= vFElabIni
        if (vFElabIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
            comparison6.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison6.setFirstOperand(operand117);
            
            // Operand 2
            comparison6.setSecondOperand(new SQLWhereOperandLiteral(vFElabIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison6);

        }        

        // Aesolicitudes.Fechaasignacion <= vFElabFin
        if (vFElabFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
            comparison7.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison7.setFirstOperand(operand118);
            
            // Operand 2
            comparison7.setSecondOperand(new SQLWhereOperandLiteral(vFElabFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison7);

        }        

        // FInicioContrato >= vFContratoIni
        if (vFContratoIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
            comparison8.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand119 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison8.setFirstOperand(operand119);
            
            // Operand 2
            comparison8.setSecondOperand(new SQLWhereOperandLiteral(vFContratoIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison8);

        }        

        // FInicioContrato <= vFContratoFin
        if (vFContratoFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison9 = new SQLWhereComparison(sql);
            comparison9.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1110 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison9.setFirstOperand(operand1110);
            
            // Operand 2
            comparison9.setSecondOperand(new SQLWhereOperandLiteral(vFContratoFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison9);

        }        

        // Agente.Usuario LIKE "%" + vCodAsesor
        if (vCodAsesor != null) {
            // 'like' Comparison
            SQLWhereComparison comparison10 = new SQLWhereComparison(sql);
            comparison10.setOperator("like");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1111 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ATTR_NAME_FUNCIONARIOUSUARIO, Constants.Type.STRING.getTypeName());
            comparison10.setFirstOperand(operand1111);
            
            // Operand 2
            comparison10.setSecondOperand(new SQLWhereOperandLiteral("%" + vCodAsesor, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison10);

        }        

        // ((Contratante.TipoIdentificacion = vTipoDoc AND Contratante.NumIdentificacion = vNumDoc) OR (Titular.TipoIdentificacion = vTipoDoc AND Titular.NumIdentificacion = vNumDoc) OR (EXIST (Usuarios) WHERE (Usuarios.TipoIdentificacion = vTipoDoc AND Usuarios.NumIdentificacion = vNumDoc) = true))
        if (vTipoDoc != null && vNumDoc != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison11 = new SQLWhereDisjunction();
        
            // Operand 1
                {
                    // 'or' Logical operator
                    SQLWhereDisjunction comparison12 = new SQLWhereDisjunction();
                
                    // Operand 1
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison13 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison14 = new SQLWhereComparison(sql);
                                    comparison14.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1115 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison14.setFirstOperand(operand1115);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2216 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VTIPODOC);
                                    comparison14.setSecondOperand(operand2216);
                                    comparison13.addOperand(comparison14);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison16 = new SQLWhereComparison(sql);
                                    comparison16.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ATTR_NAME_CONTRATANTENUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison16.setFirstOperand(operand1117);
                                    
                                    // Operand 2
                                    comparison16.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison13.addOperand(comparison16);
                            
                            comparison12.addOperand(comparison13);
                        }
                
                    // Operand 2
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison17 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison18 = new SQLWhereComparison(sql);
                                    comparison18.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1119 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison18.setFirstOperand(operand1119);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2220 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VTIPODOC);
                                    comparison18.setSecondOperand(operand2220);
                                    comparison17.addOperand(comparison18);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison20 = new SQLWhereComparison(sql);
                                    comparison20.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1121 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ATTR_NAME_TITULARNUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison20.setFirstOperand(operand1121);
                                    
                                    // Operand 2
                                    comparison20.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison17.addOperand(comparison20);
                            
                            comparison12.addOperand(comparison17);
                        }
                    
                    comparison11.addOperand(comparison12);
                }
        
            // Operand 2
            // 'exist' Association operator
            SQLWhereOperandSubQuery operandSubQueryAssocOperator087 = new SQLWhereOperandSubQuery(repository.assocOperator087Range1Query(vTipoDoc, vNumDoc, sql));
            comparison11.addOperand(operandSubQueryAssocOperator087);
            
            conjunction.addOperand(comparison11);
        }

        }        

        // Agente.Sucursal.Regional = voRegional
        if (voRegional != null) {
            // '=' Comparison
            SQLWhereComparison comparison12 = new SQLWhereComparison(sql);
            comparison12.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand1113 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ROLE_NAME_SUCURSAL + "." + SucursalConstants.ROLE_NAME_REGIONAL, RegionalConstants.CLASS_NAME);
            comparison12.setFirstOperand(operand1113);
            
            // Operand 2
            SQLWhereOperandOVParameter operand2214 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOREGIONAL, RegionalConstants.CLASS_NAME, RegionalConstants.CLASS_NAME, voRegional.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDESTATUSAREAMED3_VOREGIONAL);
            comparison12.setSecondOperand(operand2214);
            conjunction.addOperand(comparison12);

        }        

        // Aesolicitudes.Tiposolicitud = "E"
        // '=' Comparison
        SQLWhereComparison comparison13 = new SQLWhereComparison(sql);
        comparison13.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1114 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESTIPOSOLICITUD, Constants.Type.STRING.getTypeName());
        comparison13.setFirstOperand(operand1114);
        
        // Operand 2
        comparison13.setSecondOperand(new SQLWhereOperandLiteral("E", Constants.Type.STRING.getTypeName()));
        conjunction.addOperand(comparison13);


        // Completada = true
        // '=' Comparison
        SQLWhereComparison comparison14 = new SQLWhereComparison(sql);
        comparison14.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1115 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDCOMPLETADA, Constants.Type.BOOL.getTypeName());
        comparison14.setFirstOperand(operand1115);
        
        // Operand 2
        comparison14.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
        conjunction.addOperand(comparison14);


        // (Estado = 7 OR FechaTerminada <> NULL)
    {
        // 'or' Logical operator
        SQLWhereDisjunction comparison15 = new SQLWhereDisjunction();
    
        // Operand 1
            // '=' Comparison
            SQLWhereComparison comparison16 = new SQLWhereComparison(sql);
            comparison16.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1117 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
            comparison16.setFirstOperand(operand1117);
            
            // Operand 2
            comparison16.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(7), Constants.Type.NAT.getTypeName()));
            comparison15.addOperand(comparison16);
    
        // Operand 2
            // '<>' Comparison
            SQLWhereComparison comparison18 = new SQLWhereComparison(sql);
            comparison18.setOperator("<>");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1119 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFECHATERMINADA, Constants.Type.DATE.getTypeName());
            comparison18.setFirstOperand(operand1119);
            
            // Operand 2
            comparison18.setSecondOperand(new SQLWhereOperandNull());
            comparison15.addOperand(comparison18);
        
        conjunction.addOperand(comparison15);
    }


        sql.addConjunction(conjunction);
    }

    private void fSolicitudStatusEstHomo(FilterRequest filterRequest, SQLSelect sql) throws SystemException{
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vNumSolicitud = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VNUMSOLICITUD) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VNUMSOLICITUD, FilterVariableRequest.STRING) : null;
        Long vTramite = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VTRAMITE) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VTRAMITE, FilterVariableRequest.NAT) : null;
        Productos voProducto = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOPRODUCTO) ? productosSrv.getByOID(new ProductosOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOPRODUCTO)))) : null;
        Planes voPlan = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOPLAN) ? planesSrv.getByOID(new PlanesOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOPLAN)))) : null;
        Long voEstado = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOESTADO) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOESTADO, FilterVariableRequest.INT) : null;
        Date vFElabIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VFELABINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VFELABINI, FilterVariableRequest.DATE) : null;
        Date vFElabFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VFELABFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VFELABFIN, FilterVariableRequest.DATE) : null;
        Date vFContratoIni = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VFCONTRATOINI) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VFCONTRATOINI, FilterVariableRequest.DATE) : null;
        Date vFContratoFin = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VFCONTRATOFIN) ? (Date) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VFCONTRATOFIN, FilterVariableRequest.DATE) : null;
        String vCodAsesor = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VCODASESOR) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VCODASESOR, FilterVariableRequest.STRING) : null;
        TipoIdentificacion vTipoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VTIPODOC) ? tipoIdentificacionSrv.getByOID(new TipoIdentificacionOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VTIPODOC)))) : null;
        String vNumDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VNUMDOC) ? (String) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VNUMDOC, FilterVariableRequest.STRING) : null;
        Regional voRegional = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOREGIONAL) ? regionalSrv.getByOID(new RegionalOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOREGIONAL)))) : null;
        Long vEstadoDoc = filterRequest.hasFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VESTADODOC) ? (Long) filterRequest.getFilterVariable(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VESTADODOC, FilterVariableRequest.NAT) : null;

        // Completada = true
        // '=' Comparison
        SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
        comparison1.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDCOMPLETADA, Constants.Type.BOOL.getTypeName());
        comparison1.setFirstOperand(operand112);
        
        // Operand 2
        comparison1.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
        conjunction.addOperand(comparison1);


        // Aesolicitudes.Numerosolicitud = vNumSolicitud
        if (vNumSolicitud != null) {
            // '=' Comparison
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESNUMEROSOLICITUD, Constants.Type.STRING.getTypeName());
            comparison2.setFirstOperand(operand113);
            
            // Operand 2
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(vNumSolicitud, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison2);

        }        

        // Tramite = vTramite
        if (vTramite != null) {
            // '=' Comparison
            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
            comparison3.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand114 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDTRAMITE, Constants.Type.NAT.getTypeName());
            comparison3.setFirstOperand(operand114);
            
            // Operand 2
            comparison3.setSecondOperand(new SQLWhereOperandLiteral(vTramite, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison3);

        }        

        // Aesolicitudes.ProductoT = voProducto
        if (voProducto != null) {
            // '=' Comparison
            SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
            comparison4.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand115 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PRODUCTOT, ProductosConstants.CLASS_NAME);
            comparison4.setFirstOperand(operand115);
            
            // Operand 2
            SQLWhereOperandOVParameter operand226 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOPRODUCTO, ProductosConstants.CLASS_NAME, ProductosConstants.CLASS_NAME, voProducto.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOPRODUCTO);
            comparison4.setSecondOperand(operand226);
            conjunction.addOperand(comparison4);

        }        

        // Aesolicitudes.PlanT = voPlan
        if (voPlan != null) {
            // '=' Comparison
            SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
            comparison5.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand116 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ROLE_NAME_PLANT, PlanesConstants.CLASS_NAME);
            comparison5.setFirstOperand(operand116);
            
            // Operand 2
            SQLWhereOperandOVParameter operand227 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOPLAN, PlanesConstants.CLASS_NAME, PlanesConstants.CLASS_NAME, voPlan.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOPLAN);
            comparison5.setSecondOperand(operand227);
            conjunction.addOperand(comparison5);

        }        

        // (Estado = voEstado OR (Estado = 8 AND voEstado = 1))
        if (voEstado != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison6 = new SQLWhereDisjunction();
        
            // Operand 1
                    // '=' Comparison
                    SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
                    comparison7.setOperator("=");
                    
                    // Operand 1
                    SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
                    comparison7.setFirstOperand(operand118);
                    
                    // Operand 2
                    comparison7.setSecondOperand(new SQLWhereOperandLiteral(voEstado, Constants.Type.INT.getTypeName()));
                    comparison6.addOperand(comparison7);
        
            // Operand 2
                {
                    // 'and' Logical operator
                    SQLWhereConjunction comparison9 = new SQLWhereConjunction();
                
                    // Operand 1
                            // '=' Comparison
                            SQLWhereComparison comparison10 = new SQLWhereComparison(sql);
                            comparison10.setOperator("=");
                            
                            // Operand 1
                            SQLWhereOperandPathAttribute operand1111 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
                            comparison10.setFirstOperand(operand1111);
                            
                            // Operand 2
                            comparison10.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(8), Constants.Type.NAT.getTypeName()));
                            comparison9.addOperand(comparison10);
                
                    // Operand 2
                            // '=' Comparison
                            SQLWhereComparison comparison12 = new SQLWhereComparison(sql);
                            comparison12.setOperator("=");
                            
                            // Operand 1
                            comparison12.setFirstOperand(new SQLWhereOperandLiteral(voEstado, Constants.Type.INT.getTypeName()));
                            
                            // Operand 2
                            comparison12.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(1), Constants.Type.NAT.getTypeName()));
                            comparison9.addOperand(comparison12);
                    
                    comparison6.addOperand(comparison9);
                }
            
            conjunction.addOperand(comparison6);
        }

        }        

        // Aesolicitudes.Fechaasignacion >= vFElabIni
        if (vFElabIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison7 = new SQLWhereComparison(sql);
            comparison7.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison7.setFirstOperand(operand118);
            
            // Operand 2
            comparison7.setSecondOperand(new SQLWhereOperandLiteral(vFElabIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison7);

        }        

        // Aesolicitudes.Fechaasignacion <= vFElabFin
        if (vFElabFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
            comparison8.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand119 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison8.setFirstOperand(operand119);
            
            // Operand 2
            comparison8.setSecondOperand(new SQLWhereOperandLiteral(vFElabFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison8);

        }        

        // FInicioContrato >= vFContratoIni
        if (vFContratoIni != null) {
            // '>=' Comparison
            SQLWhereComparison comparison9 = new SQLWhereComparison(sql);
            comparison9.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1110 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison9.setFirstOperand(operand1110);
            
            // Operand 2
            comparison9.setSecondOperand(new SQLWhereOperandLiteral(vFContratoIni, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison9);

        }        

        // FInicioContrato <= vFContratoFin
        if (vFContratoFin != null) {
            // '<=' Comparison
            SQLWhereComparison comparison10 = new SQLWhereComparison(sql);
            comparison10.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1111 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDFINICIOCONTRATO, Constants.Type.DATE.getTypeName());
            comparison10.setFirstOperand(operand1111);
            
            // Operand 2
            comparison10.setSecondOperand(new SQLWhereOperandLiteral(vFContratoFin, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison10);

        }        

        // Agente.Usuario LIKE "%" + vCodAsesor
        if (vCodAsesor != null) {
            // 'like' Comparison
            SQLWhereComparison comparison11 = new SQLWhereComparison(sql);
            comparison11.setOperator("like");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1112 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ATTR_NAME_FUNCIONARIOUSUARIO, Constants.Type.STRING.getTypeName());
            comparison11.setFirstOperand(operand1112);
            
            // Operand 2
            comparison11.setSecondOperand(new SQLWhereOperandLiteral("%" + vCodAsesor, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison11);

        }        

        // ((Contratante.TipoIdentificacion = vTipoDoc AND Contratante.NumIdentificacion = vNumDoc) OR (Titular.TipoIdentificacion = vTipoDoc AND Titular.NumIdentificacion = vNumDoc) OR (EXIST (Usuarios) WHERE (Usuarios.TipoIdentificacion = vTipoDoc AND Usuarios.NumIdentificacion = vNumDoc) = true))
        if (vTipoDoc != null && vNumDoc != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison12 = new SQLWhereDisjunction();
        
            // Operand 1
                {
                    // 'or' Logical operator
                    SQLWhereDisjunction comparison13 = new SQLWhereDisjunction();
                
                    // Operand 1
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison14 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison15 = new SQLWhereComparison(sql);
                                    comparison15.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1116 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison15.setFirstOperand(operand1116);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2217 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VTIPODOC);
                                    comparison15.setSecondOperand(operand2217);
                                    comparison14.addOperand(comparison15);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison17 = new SQLWhereComparison(sql);
                                    comparison17.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1118 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_CONTRATANTE + "." + ContratanteConstants.ATTR_NAME_CONTRATANTENUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison17.setFirstOperand(operand1118);
                                    
                                    // Operand 2
                                    comparison17.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison14.addOperand(comparison17);
                            
                            comparison13.addOperand(comparison14);
                        }
                
                    // Operand 2
                        {
                            // 'and' Logical operator
                            SQLWhereConjunction comparison18 = new SQLWhereConjunction();
                        
                            // Operand 1
                                    // '=' Comparison
                                    SQLWhereComparison comparison19 = new SQLWhereComparison(sql);
                                    comparison19.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathInstance operand1120 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
                                    comparison19.setFirstOperand(operand1120);
                                    
                                    // Operand 2
                                    SQLWhereOperandOVParameter operand2221 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vTipoDoc.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VTIPODOC);
                                    comparison19.setSecondOperand(operand2221);
                                    comparison18.addOperand(comparison19);
                        
                            // Operand 2
                                    // '=' Comparison
                                    SQLWhereComparison comparison21 = new SQLWhereComparison(sql);
                                    comparison21.setOperator("=");
                                    
                                    // Operand 1
                                    SQLWhereOperandPathAttribute operand1122 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_TITULAR + "." + TitularConstants.ATTR_NAME_TITULARNUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
                                    comparison21.setFirstOperand(operand1122);
                                    
                                    // Operand 2
                                    comparison21.setSecondOperand(new SQLWhereOperandLiteral(vNumDoc, Constants.Type.STRING.getTypeName()));
                                    comparison18.addOperand(comparison21);
                            
                            comparison13.addOperand(comparison18);
                        }
                    
                    comparison12.addOperand(comparison13);
                }
        
            // Operand 2
            // 'exist' Association operator
            SQLWhereOperandSubQuery operandSubQueryAssocOperator088 = new SQLWhereOperandSubQuery(repository.assocOperator088Range1Query(vTipoDoc, vNumDoc, sql));
            comparison12.addOperand(operandSubQueryAssocOperator088);
            
            conjunction.addOperand(comparison12);
        }

        }        

        // Agente.Sucursal.Regional = voRegional
        if (voRegional != null) {
            // '=' Comparison
            SQLWhereComparison comparison13 = new SQLWhereComparison(sql);
            comparison13.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand1114 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ROLE_NAME_SUCURSAL + "." + SucursalConstants.ROLE_NAME_REGIONAL, RegionalConstants.CLASS_NAME);
            comparison13.setFirstOperand(operand1114);
            
            // Operand 2
            SQLWhereOperandOVParameter operand2215 = new SQLWhereOperandOVParameter(SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOREGIONAL, RegionalConstants.CLASS_NAME, RegionalConstants.CLASS_NAME, voRegional.getOid(), SolicitudConstants.VAR_NAME_FSOLICITUDSTATUSESTHOMO_VOREGIONAL);
            comparison13.setSecondOperand(operand2215);
            conjunction.addOperand(comparison13);

        }        

        // EstadoDoc = vEstadoDoc
        if (vEstadoDoc != null) {
            // '=' Comparison
            SQLWhereComparison comparison14 = new SQLWhereComparison(sql);
            comparison14.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand1115 = new SQLWhereOperandPathAttribute(SolicitudConstants.ATTR_NAME_SOLICITUDESTADODOC, Constants.Type.NAT.getTypeName());
            comparison14.setFirstOperand(operand1115);
            
            // Operand 2
            comparison14.setSecondOperand(new SQLWhereOperandLiteral(vEstadoDoc, Constants.Type.NAT.getTypeName()));
            conjunction.addOperand(comparison14);

        }        

        // Aesolicitudes.Tiposolicitud = "E"
        // '=' Comparison
        SQLWhereComparison comparison15 = new SQLWhereComparison(sql);
        comparison15.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand1116 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESTIPOSOLICITUD, Constants.Type.STRING.getTypeName());
        comparison15.setFirstOperand(operand1116);
        
        // Operand 2
        comparison15.setSecondOperand(new SQLWhereOperandLiteral("E", Constants.Type.STRING.getTypeName()));
        conjunction.addOperand(comparison15);


        sql.addConjunction(conjunction);
    }

    private boolean inMemoryFSolicitudLegalizaComisiones(Solicitud entity) {
        boolean result = true;

        // (Estado <> 2 OR drvTienePrimeraFirma = true)
        try {
            result = result && (!(entity.getSolicitudEstado()).equals(Long.valueOf(2)) || entity.getSolicituddrvTienePrimeraFirma().equals(true));
        } catch(Exception e) {
           LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }

        // (Estado <> 3 OR (drvTienePrimeraFirma = true AND drvTieneSegundaFirma = true))
        try {
            result = result && (!(entity.getSolicitudEstado()).equals(Long.valueOf(3)) || (entity.getSolicituddrvTienePrimeraFirma().equals(true) && entity.getSolicituddrvTieneSegundaFirma().equals(true)));
        } catch(Exception e) {
           LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
        return result;
    }

    // IIU Methods

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudIIUNuevaAfil for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudIIUNuevaAfil
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUNuevaAfil iIUNuevaAfiliacion(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUNuevaAfil(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudIIUInfoContratJUR for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudIIUInfoContratJUR
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoContratJUR iIUInfoContratanteJUR(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoContratJUR(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudIIUInfoObservacion for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudIIUInfoObservacion
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoObservacion iIUInfoObservaciones(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoObservacion(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudIIUInfoPago for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudIIUInfoPago
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoPago iIUInfoPago(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoPago(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudIIUInclusion for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudIIUInclusion
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInclusion iIUInclusion(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInclusion(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudInfoContratanteID for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudInfoContratanteID
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudInfoContratanteID iIUInfoContratanteIDINCL(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudInfoContratanteID(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudIIUInfoContraConta for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudIIUInfoContraConta
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoContraConta iIUInfoContratanteContactoINC(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoContraConta(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudVacia for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudVacia
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudVacia iIUSolicitudVacia(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudVacia(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudIIUDifLiq for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudIIUDifLiq
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUDifLiq iIUDiferenciasLiquidacion(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUDifLiq(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudIIUPreexistencias for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudIIUPreexistencias
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUPreexistencias iIUSolicitudPreexistencias(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUPreexistencias(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset ICSolicitud for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset ICSolicitud
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.ICSolicitud iIUInfoContratanteEditable(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.ICSolicitud(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset ICSolicitud for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset ICSolicitud
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.ICSolicitud iIUInfoTitularEditable(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.ICSolicitud(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudIIUInfoContratNAT for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudIIUInfoContratNAT
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoContratNAT iIUInfoContratanteNAT(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoContratNAT(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudIIUInfoContratJUR for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudIIUInfoContratJUR
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoContratJUR iIUInfoContratanteJURCol(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoContratJUR(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudIIUInfoContratNAT for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudIIUInfoContratNAT
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoContratNAT iIUInfoContratanteNATCol(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudIIUInfoContratNAT(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudPIUStatusFull for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudPIUStatusFull
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusFull iIUSolicitudEstatusVerMas(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusFull(getByOIDWithHV(oid));
    }

    /*
     * Returns a viewmodel for class Solicitud with displayset DSSolicitudPIUStatusFullAse for the instance with a given OID
     * @param oid Class oid
     * @return Viewmodel for class class Solicitud with displayset DSSolicitudPIUStatusFullAse
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusFullAse iIUSolicitudEstatusVerMasAse(SolicitudOid oid) {
        return new com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusFullAse(getByOIDWithHV(oid));
    }


    /*
     * Solves the query needed for PIURadicadosObs
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Solicitud using displayset DSSolicitudPIURadicado that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIURadicado> piuPIURadicadosObs(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIURadicado::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIURadicadosObs
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIURadicadosObs
     */
    public int getCount4PIURadicadosObs(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).size();
    }

    /*
     * Solves the query needed for PIUConsultarRadicado
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Solicitud using displayset DSSolicitudPIUConsultaRad that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUConsultaRad> piuPIUConsultarRadicado(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUConsultaRad::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUConsultarRadicado
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUConsultarRadicado
     */
    public int getCount4PIUConsultarRadicado(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).size();
    }

    /*
     * Solves the query needed for PIUEnvioDoc
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Solicitud using displayset DSSolicitudPIUEnvioDoc that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUEnvioDoc> piuPIUEnvioDoc(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUEnvioDoc::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUEnvioDoc
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUEnvioDoc
     */
    public int getCount4PIUEnvioDoc(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).size();
    }

    /*
     * Solves the query needed for PIUEstatusSolicitudesAdmin
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Solicitud using displayset DSSolicitudPIUStatusAdmin that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusAdmin> piuPIUEstatusSolicitudesAdmin(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusAdmin::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUEstatusSolicitudesAdmin
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUEstatusSolicitudesAdmin
     */
    public int getCount4PIUEstatusSolicitudesAdmin(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).size();
    }

    /*
     * Solves the query needed for PIUEstatusSolicitudesAfil
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Solicitud using displayset DSSolicitudPIUStatusAfil that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusAfil> piuPIUEstatusSolicitudesAfil(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusAfil::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUEstatusSolicitudesAfil
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUEstatusSolicitudesAfil
     */
    public int getCount4PIUEstatusSolicitudesAfil(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).size();
    }

    /*
     * Solves the query needed for PIUEstatusSolicitudesAreaMed
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Solicitud using displayset DSSolicitudPIUStatusAreaMe that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusAreaMe> piuPIUEstatusSolicitudesAreaMed(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusAreaMe::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUEstatusSolicitudesAreaMed
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUEstatusSolicitudesAreaMed
     */
    public int getCount4PIUEstatusSolicitudesAreaMed(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).size();
    }

    /*
     * Solves the query needed for PIUEstatusSolicitudesAsesor
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Solicitud using displayset DSSolicitudPIUStatusAsesor that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusAsesor> piuPIUEstatusSolicitudesAsesor(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusAsesor::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUEstatusSolicitudesAsesor
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUEstatusSolicitudesAsesor
     */
    public int getCount4PIUEstatusSolicitudesAsesor(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).size();
    }

    /*
     * Solves the query needed for PIUEstatusSolGestorMesa
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Solicitud using displayset DSSolicitudPIUStatusGestMesa that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusGestMesa> piuPIUEstatusSolGestorMesa(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.solicitud.ds.DSSolicitudPIUStatusGestMesa::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUEstatusSolGestorMesa
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUEstatusSolGestorMesa
     */
    public int getCount4PIUEstatusSolGestorMesa(QueryRequest queryRequest) {
        return solveSolicitudTypedQuery(queryRequest).size();
    }

    // Service: marcarDatosPagoOK

    /*
     * Entry point for service marcarDatosPagoOK
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse marcarDatosPagoOK(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarDatosPagoOKRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.marcarDatosPagoOKTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service marcarDatosPagoOK
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse marcarDatosPagoOKTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarDatosPagoOKRequest request) throws ModelException {
        request.setSolicitudmarcarDatosPagoOKpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudmarcarDatosPagoOKpthisSolicitud()));
        return solicitudSrv.marcarDatosPagoOKImplementation(request);
    }

    /*
     * Implements service marcarDatosPagoOK
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse marcarDatosPagoOKImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarDatosPagoOKRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudmarcarDatosPagoOKpthisSolicitud = request.getSolicitudmarcarDatosPagoOKpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudmarcarDatosPagoOKpthisSolicitud;
        instance.setSolicituddatosPagoOK(Boolean.TRUE);


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: modificarDatosCORE

    /*
     * Entry point for service modificarDatosCORE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse modificarDatosCORE(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ModificarDatosCORERequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.modificarDatosCORETxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service modificarDatosCORE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse modificarDatosCORETxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ModificarDatosCORERequest request) throws ModelException {
        request.setSolicitudmodificarDatosCOREpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudmodificarDatosCOREpthisSolicitud()));
        return solicitudSrv.modificarDatosCOREImplementation(request);
    }

    /*
     * Implements service modificarDatosCORE
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse modificarDatosCOREImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ModificarDatosCORERequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudmodificarDatosCOREpthisSolicitud = request.getSolicitudmodificarDatosCOREpthisSolicitudInstance();
        String solicitudmodificarDatosCOREpNumContrato = request.getSolicitudmodificarDatosCOREpNumContrato();
        Double solicitudmodificarDatosCOREpvalorCuotaPeriodoC = request.getSolicitudmodificarDatosCOREpvalorCuotaPeriodoC();
        Double solicitudmodificarDatosCOREpdescuentoComercialC = request.getSolicitudmodificarDatosCOREpdescuentoComercialC();
        Double solicitudmodificarDatosCOREpIVAC = request.getSolicitudmodificarDatosCOREpIVAC();
        Double solicitudmodificarDatosCOREpdescuentoFinancieroC = request.getSolicitudmodificarDatosCOREpdescuentoFinancieroC();
        Double solicitudmodificarDatosCOREpvalorCuotaBrutaC = request.getSolicitudmodificarDatosCOREpvalorCuotaBrutaC();

        request.checkArguments();
        Solicitud instance = solicitudmodificarDatosCOREpthisSolicitud;
        instance.setSolicitudNumContrato(solicitudmodificarDatosCOREpNumContrato);
        instance.setSolicitudvalorCuotaPeriodoC(solicitudmodificarDatosCOREpvalorCuotaPeriodoC);
        instance.setSolicituddescuentoComercialC(solicitudmodificarDatosCOREpdescuentoComercialC);
        instance.setSolicitudIVAC(solicitudmodificarDatosCOREpIVAC);
        instance.setSolicituddescuentoFinancieroC(solicitudmodificarDatosCOREpdescuentoFinancieroC);
        instance.setSolicitudvalorCuotaBrutaC(solicitudmodificarDatosCOREpvalorCuotaBrutaC);


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: calcularDifLiquidacion

    /*
     * Entry point for service calcularDifLiquidacion
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse calcularDifLiquidacion(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcularDifLiquidacionRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.calcularDifLiquidacionTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service calcularDifLiquidacion
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse calcularDifLiquidacionTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcularDifLiquidacionRequest request) throws ModelException {
        request.setSolicitudcalcularDifLiquidacionpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudcalcularDifLiquidacionpthisSolicitud()));
        return solicitudSrv.calcularDifLiquidacionImplementation(request);
    }

    /*
     * Implements service calcularDifLiquidacion
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse calcularDifLiquidacionImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcularDifLiquidacionRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudcalcularDifLiquidacionpthisSolicitud = request.getSolicitudcalcularDifLiquidacionpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudcalcularDifLiquidacionpthisSolicitud;
        if (solicitudcalcularDifLiquidacionpthisSolicitud.getSolicitudvalorCuota() * (Long.valueOf(1) + Utilities.convertToDouble(STDFunctions.strToIntFun(userFunctionsSrv.fugetValorParametroConfFun("TOLERANCIADIFLIQ"))) / Long.valueOf(100)) < solicitudcalcularDifLiquidacionpthisSolicitud.getSolicitudvalorCuotaBrutaC()) {
            instance.setSolicitudTieneDifLiquidacion(Boolean.TRUE);
        } else {
            instance.setSolicitudTieneDifLiquidacion(Boolean.FALSE);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: validarAtributosUsuario

    /*
     * Entry point for service validarAtributosUsuario
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioResponse validarAtributosUsuario(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) {
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioResponse response = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioResponse();
        try {
            response = solicitudSrv.validarAtributosUsuarioTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service validarAtributosUsuario
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioResponse validarAtributosUsuarioTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) throws ModelException {
        request.setSolicitudvalidarAtributosUsuariopthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudvalidarAtributosUsuariopthisSolicitud()));
        return solicitudSrv.validarAtributosUsuarioImplementation(request);
    }

    /*
     * Implements service validarAtributosUsuario
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioResponse validarAtributosUsuarioImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) throws ModelException {
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioResponse response = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioResponse();
        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();

        request.checkArguments();

        validarAtributosUsuarioPreconditions(request);
        Solicitud instance = solicitudvalidarAtributosUsuariopthisSolicitud;
        instance.setSolicitudUsuariosValidados(Boolean.TRUE);


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        // Value expressions for outgoing argument 'solicitudvalidarAtributosUsuarioptpsNumSolicitud'
        // DEFAULT  : Aesolicitudes.Numerosolicitud
        response.setSolicitudvalidarAtributosUsuarioptpsNumSolicitud(solicitudvalidarAtributosUsuariopthisSolicitud.getAesolicitudes().getAesolicitudesNumerosolicitud());


        return response;
    }

    /*
     * Checks service validarAtributosUsuario preconditions individually
     *
     * @param request Service request viewmodel
     *
     * @return Service preconditions evaluation result
     */
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioResponse validarAtributosUsuarioCheckPreconditions(com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) {
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioResponse response = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioResponse();
        request.setSolicitudvalidarAtributosUsuariopthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudvalidarAtributosUsuariopthisSolicitud()));
        try {
            if("Clas_1431957012480960Pre_25".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre25(request);
            if("Clas_1431957012480960Pre_26".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre26(request);
            if("Clas_1431957012480960Pre_28".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre28(request);
            if("Clas_1431957012480960Pre_29".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre29(request);
            if("Clas_1431957012480960Pre_30".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre30(request);
            if("Clas_1431957012480960Pre_31".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre31(request);
            if("Clas_1431957012480960Pre_33".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre33(request);
            if("Clas_1431957012480960Pre_34".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre34(request);
            if("Clas_1431957012480960Pre_35".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre35(request);
            if("Clas_1431957012480960Pre_36".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre36(request);
            if("Clas_1431957012480960Pre_37".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre37(request);
            if("Clas_1431957012480960Pre_38".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre38(request);
            if("Clas_1431957012480960Pre_39".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre39(request);
            if("Clas_1431957012480960Pre_40".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre40(request);
            if("Clas_1431957012480960Pre_42".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre42(request);
            if("Clas_1431957012480960Pre_47".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre47(request);
            if("Clas_1431957012480960Pre_48".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre48(request);
            if("Clas_1431957012480960Pre_58".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre58(request);
            if("Clas_1431957012480960Pre_79".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre79(request);
            if("Clas_1431957012480960Pre_143".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre143(request);
            if("Clas_1431957012480960Pre_144".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre144(request);
            if("Clas_1431957012480960Pre_145".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre145(request);
            if("Clas_1431957012480960Pre_146".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre146(request);
            if("Clas_1431957012480960Pre_147".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre147(request);
            if("Clas_1431957012480960Pre_148".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre148(request);
            if("Clas_1431957012480960Pre_149".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre149(request);
            if("Clas_1431957012480960Pre_150".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre150(request);
            if("Clas_1431957012480960Pre_151".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre151(request);
            if("Clas_1431957012480960Pre_152".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre152(request);
            if("Clas_1431957012480960Pre_154".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre154(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    private void validarAtributosUsuarioPreconditions(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        checkClas1431957012480960Pre25(request);
        checkClas1431957012480960Pre26(request);
        checkClas1431957012480960Pre28(request);
        checkClas1431957012480960Pre29(request);
        checkClas1431957012480960Pre30(request);
        checkClas1431957012480960Pre31(request);
        checkClas1431957012480960Pre33(request);
        checkClas1431957012480960Pre34(request);
        checkClas1431957012480960Pre35(request);
        checkClas1431957012480960Pre36(request);
        checkClas1431957012480960Pre37(request);
        checkClas1431957012480960Pre38(request);
        checkClas1431957012480960Pre39(request);
        checkClas1431957012480960Pre40(request);
        checkClas1431957012480960Pre42(request);
        checkClas1431957012480960Pre47(request);
        checkClas1431957012480960Pre48(request);
        checkClas1431957012480960Pre58(request);
        checkClas1431957012480960Pre79(request);
        checkClas1431957012480960Pre143(request);
        checkClas1431957012480960Pre144(request);
        checkClas1431957012480960Pre145(request);
        checkClas1431957012480960Pre146(request);
        checkClas1431957012480960Pre147(request);
        checkClas1431957012480960Pre148(request);
        checkClas1431957012480960Pre149(request);
        checkClas1431957012480960Pre150(request);
        checkClas1431957012480960Pre151(request);
        checkClas1431957012480960Pre152(request);
        checkClas1431957012480960Pre154(request);
    }

    private void checkClas1431957012480960Pre25 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // (EXIST( Usuarios ) WHERE (Usuarios.drvEstaActivo = true) = true) OR (EXIST( UsuariosRegistrados ) WHERE (UsuariosRegistrados.EstadoBeneficiario.EsActivo = true) = true)

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = (repository.assocOperator114(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true)) || (repository.assocOperator115(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true));
            if (!preconditionHolds) throw new PreconditionException("La familia debe tener por lo menos un beneficiario en estado diferente a EXCLUIDO");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre26 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // (Contratante.TipoIdentificacion.NIT = false AND drvNumTitulares <= 1) OR (Contratante.TipoIdentificacion.NIT = true AND drvNumTitulares = 1) OR Tramite = 2

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        boolean preconditionHolds = (solicitudvalidarAtributosUsuariopthisSolicitud.getContratante().getTipoIdentificacion().getTipoIdentificacionNIT().equals(false) && solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvNumTitulares() <= Long.valueOf(1)) || (solicitudvalidarAtributosUsuariopthisSolicitud.getContratante().getTipoIdentificacion().getTipoIdentificacionNIT().equals(true) && (solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvNumTitulares()).equals(Long.valueOf(1))) || (solicitudvalidarAtributosUsuariopthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(2));
        if (!preconditionHolds) throw new PreconditionException("La familia sólo debe tener un beneficiario con parentesco TITULAR en estado activo");
    }

    private void checkClas1431957012480960Pre28 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // EXIST( Direccion ) WHERE (Direccion.TipoDireccion.EsCorrespondencia = TRUE) = TRUE

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = repository.assocOperator116(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("La familia debe tener dirección de correspondencia");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre29 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // (Contratante.TipoPersona = "N") OR (COUNT( Usuarios ) WHERE (Usuarios.Parentesco.EsTitular = true AND Usuarios.drvEstaActivo = true) + COUNT( UsuariosRegistrados ) WHERE (UsuariosRegistrados.Parentesco.EsTitular = true AND UsuariosRegistrados.EstadoBeneficiario.EsActivo = true) >= 1) OR Tramite = 2

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = ((solicitudvalidarAtributosUsuariopthisSolicitud.getContratante().getContratanteTipoPersona()).equals("N")) || (repository.assocOperator117(solicitudvalidarAtributosUsuariopthisSolicitud) + repository.assocOperator118(solicitudvalidarAtributosUsuariopthisSolicitud) >= Long.valueOf(1)) || (solicitudvalidarAtributosUsuariopthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(2));
            if (!preconditionHolds) throw new PreconditionException("Los contratos con contratante Jurídico deben tener al menos un beneficiario con parentesco TITULAR activo");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre30 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // FOR ALL Usuarios  WHERE (EXIST( Usuarios.UsuarioPersona.EstadoPersona ) = true)( Usuarios.UsuarioPersona.EstadoPersona.ProhibidoBeneficiarios = false ) = true

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = repository.assocOperator119(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("Alguno de los beneficiarios no tiene un estado válido");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre31 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // FOR ALL Usuarios  WHERE (Usuarios.BebeGestante = true AND Usuarios.UsuarioPersona.drvVieneDeCORE = false)( Usuarios.UsuarioPersona.FechaNacimiento <= pthisSolicitud.Aesolicitudes.Fechaasignacion ) = true

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = repository.assocOperator121(solicitudvalidarAtributosUsuariopthisSolicitud, solicitudvalidarAtributosUsuariopthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("Los beneficiarios marcados como BEBE GESTANTE deben tener como fecha de nacimiento la fecha de elaboración");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre33 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // drvTieneTitular = false OR (EXIST( Usuarios ) WHERE (Usuarios.Parentesco.EsPadre = true) = false) OR (FOR ALL Usuarios  WHERE (Usuarios.Parentesco.EsPadre = true)( Usuarios.drvEdad - drvEdadTitular > 10 ) = true)

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvTieneTitular().equals(false) || (repository.assocOperator122(solicitudvalidarAtributosUsuariopthisSolicitud).equals(false)) || (repository.assocOperator123(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true));
            if (!preconditionHolds) throw new PreconditionException("El titular debe tener por lo menos un diferencia de 10 años con la edad del beneficiario de parentesco PADRE");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre34 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // drvTieneTitular = false OR (EXIST( Usuarios ) WHERE (Usuarios.Parentesco.EsHijo = true) = false) OR (FOR ALL Usuarios  WHERE (Usuarios.Parentesco.EsHijo = true)( drvEdadTitular - Usuarios.drvEdad > 10 ) = true)

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvTieneTitular().equals(false) || (repository.assocOperator124(solicitudvalidarAtributosUsuariopthisSolicitud).equals(false)) || (repository.assocOperator125(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true));
            if (!preconditionHolds) throw new PreconditionException("El titular debe tener por lo menos un diferencia de 10 años con la edad del beneficiario de parentesco HIJO");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre35 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // drvTieneTitular = false OR (EXIST( Usuarios ) WHERE (Usuarios.Parentesco.EsConyuge = true) = false) OR (FOR ALL Usuarios  WHERE (Usuarios.Parentesco.EsConyuge = true)( Usuarios.UsuarioPersona.Genero <> drvGeneroTitular ) = true)

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvTieneTitular().equals(false) || (repository.assocOperator126(solicitudvalidarAtributosUsuariopthisSolicitud).equals(false)) || (repository.assocOperator127(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true));
            if (!preconditionHolds) throw new PreconditionException("Los beneficiarios con parentesco CONYUGE deben tener sexo distinto al del TITULAR");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre36 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // (EXIST( Usuarios ) WHERE (Usuarios.Parentesco.EsConyuge = true) = false) OR (FOR ALL Usuarios  WHERE (Usuarios.Parentesco.EsConyuge = true)( Usuarios.drvEdad >= 13 ) = true)

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = (repository.assocOperator128(solicitudvalidarAtributosUsuariopthisSolicitud).equals(false)) || (repository.assocOperator129(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true));
            if (!preconditionHolds) throw new PreconditionException("La edad de los beneficiarios con parentesco CONYUGE debe ser mayor o igual a 13 años");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre37 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // (COUNT( Usuarios ) WHERE (Usuarios.Parentesco.EsConyuge = true AND Usuarios.drvEstaActivo = true) + COUNT( UsuariosRegistrados ) WHERE (UsuariosRegistrados.Parentesco.EsConyuge = true AND UsuariosRegistrados.EstadoBeneficiario.EsActivo = true) <= 1)

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = (repository.assocOperator130(solicitudvalidarAtributosUsuariopthisSolicitud) + repository.assocOperator131(solicitudvalidarAtributosUsuariopthisSolicitud) <= Long.valueOf(1));
            if (!preconditionHolds) throw new PreconditionException("Sólo puede existir un beneficiario con parentesco CONYUGE en estado activo");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre38 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // (NumFamilia = NULL) OR (EXIST( Usuarios ) WHERE (Usuarios.Parentesco.EsConyuge = true) = false) OR (COUNT( UsuariosRegistrados ) WHERE (UsuariosRegistrados.Parentesco.EsConyuge = true AND UsuariosRegistrados.EstadoBeneficiario.EsActivo = false AND UsuariosRegistrados.FechaFinVigencia >= FInicioContrato) = 0)

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = (solicitudvalidarAtributosUsuariopthisSolicitud.getSolicitudNumFamilia() == null) || (repository.assocOperator132(solicitudvalidarAtributosUsuariopthisSolicitud).equals(false)) || ((repository.assocOperator133(solicitudvalidarAtributosUsuariopthisSolicitud)).equals(Long.valueOf(0)));
            if (!preconditionHolds) throw new PreconditionException("Si existe un conyuge no activo su vigencia no puede estar traslapada con el nuevo conyuge ");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre39 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // FOR ALL Usuarios  WHERE (Usuarios.FechaAntiguedadBeneficiario <> NULL)( Usuarios.FechaAntiguedadBeneficiario <= FInicioContrato ) = true

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = repository.assocOperator134(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("Si se encuentra diligencia la fecha de antigüedad de un usuario, esta debe ser inferior o igual al inicio de vigencia del beneficiario");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre40 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // FOR ALL Usuarios  WHERE (Usuarios.FechaAntiguedadBeneficiario <> NULL)( EXIST( Usuarios.Companyia ) = true ) = true

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = repository.assocOperator135(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("Si se encuentra diligencia la fecha de antigüedad de un usuario, se debe haber diligenciado la entidad anterior de MPP");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre42 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // FOR ALL Usuarios ( EXIST( Usuarios.UsuarioPersona.Direcciones ) WHERE (Usuarios.UsuarioPersona.Direcciones.TipoDireccion.EsResidencia = true) = true ) = true

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = repository.assocOperator137(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("Todos los afilliados tienen que tener asociada una dirección de tipo residencial");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre47 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // FOR ALL Usuarios ( Usuarios.drvEdad <= Usuarios.drvEdadMaxIngreso ) = true

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = repository.assocOperator139(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("La edad del beneficiario al inicio de vigencia debe ser inferior o igual a la edad máxima de ingreso que tenga configurado en el Plan");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre48 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // FOR ALL Usuarios ( Usuarios.drvEdad <= Usuarios.drvEdadMaxPerm ) = true

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = repository.assocOperator140(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("La edad del beneficiario al inicio de vigencia debe ser inferior o igual a la edad máxima de permanencia que tenga configurado en el Plan");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre58 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // drvCuesMedOK = TRUE

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        boolean preconditionHolds = solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvCuesMedOK().equals(true);
        if (!preconditionHolds) throw new PreconditionException("No se ha diligenciado el cuestionario médico para todos los usuarios");
    }

    private void checkClas1431957012480960Pre79 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // COUNT( Usuarios ) WHERE (Usuarios.BebeGestante = true AND Usuarios.UsuarioPersona.drvVieneDeCORE = false AND (Usuarios.TipoIdentificacion.NombreCorto = "MS" OR Usuarios.TipoIdentificacion.NombreCorto = "RC") AND Usuarios.FechaAntiguedadBeneficiario = NULL AND Usuarios.UsuarioPersona.FechaNacimiento <> pthisSolicitud.Aesolicitudes.Fechaasignacion) = 0

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = (repository.assocOperator141(solicitudvalidarAtributosUsuariopthisSolicitud, solicitudvalidarAtributosUsuariopthisSolicitud)).equals(Long.valueOf(0));
            if (!preconditionHolds) throw new PreconditionException("Los beneficiarios marcados como BEBE GESTANTE y tipo de identificación RC o MS deben tener como fecha de nacimiento la de elaboración o indicar fecha de antigüedad");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre143 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // drvValidarDatosContratante = false OR Aesolicitudes.PlanT.EsVoluntario = true OR (ContratantePersona.CorreoElectronico <> NULL AND ContratantePersona.CorreoElectronico <> "")

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        boolean preconditionHolds = solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvValidarDatosContratante().equals(false) || solicitudvalidarAtributosUsuariopthisSolicitud.getAesolicitudes().getPlanT().getPlanesEsVoluntario().equals(true) || (solicitudvalidarAtributosUsuariopthisSolicitud.getContratantePersona().getPersonaCorreoElectronico() != null && (solicitudvalidarAtributosUsuariopthisSolicitud.getContratantePersona().getPersonaCorreoElectronico()).length() != 0);
        if (!preconditionHolds) throw new PreconditionException("Debe indicarse el correo electrónico del contratante");
    }

    private void checkClas1431957012480960Pre144 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // drvValidarDatosContratante = false OR Aesolicitudes.PlanT.EsVoluntario = true OR (Contratante.DireccionRes <> NULL AND Contratante.DireccionRes <> "")

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        boolean preconditionHolds = solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvValidarDatosContratante().equals(false) || solicitudvalidarAtributosUsuariopthisSolicitud.getAesolicitudes().getPlanT().getPlanesEsVoluntario().equals(true) || (solicitudvalidarAtributosUsuariopthisSolicitud.getContratante().getContratanteDireccionRes() != null && (solicitudvalidarAtributosUsuariopthisSolicitud.getContratante().getContratanteDireccionRes()).length() != 0);
        if (!preconditionHolds) throw new PreconditionException("Debe indicar la dirección de residencia del contratante");
    }

    private void checkClas1431957012480960Pre145 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // drvValidarDatosContratante = false OR Aesolicitudes.PlanT.EsVoluntario = true OR EXIST( Contratante.DepartamentoRes ) = true

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvValidarDatosContratante().equals(false) || solicitudvalidarAtributosUsuariopthisSolicitud.getAesolicitudes().getPlanT().getPlanesEsVoluntario().equals(true) || repository.assocOperator142(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("Debe indicar el departamento de residencia del contratante");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre146 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // drvValidarDatosContratante = false OR Aesolicitudes.PlanT.EsVoluntario = true OR EXIST( Contratante.MunicipioRes ) = true

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvValidarDatosContratante().equals(false) || solicitudvalidarAtributosUsuariopthisSolicitud.getAesolicitudes().getPlanT().getPlanesEsVoluntario().equals(true) || repository.assocOperator143(solicitudvalidarAtributosUsuariopthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("Debe indicar el municipio de residencia del contratante");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre147 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // drvValidarDatosContratante = false OR Aesolicitudes.PlanT.EsVoluntario = true OR Contratante.RuralDirRes <> NULL

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        boolean preconditionHolds = solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvValidarDatosContratante().equals(false) || solicitudvalidarAtributosUsuariopthisSolicitud.getAesolicitudes().getPlanT().getPlanesEsVoluntario().equals(true) || solicitudvalidarAtributosUsuariopthisSolicitud.getContratante().getContratanteRuralDirRes() != null;
        if (!preconditionHolds) throw new PreconditionException("Debe indicar si la dirección de residencia del contratante es rural");
    }

    private void checkClas1431957012480960Pre148 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // drvValidarDatosContratante = false OR Aesolicitudes.PlanT.EsVoluntario = true OR ContratantePersona.TelefonoCelular <> NULL

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        boolean preconditionHolds = solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvValidarDatosContratante().equals(false) || solicitudvalidarAtributosUsuariopthisSolicitud.getAesolicitudes().getPlanT().getPlanesEsVoluntario().equals(true) || solicitudvalidarAtributosUsuariopthisSolicitud.getContratantePersona().getPersonaTelefonoCelular() != null;
        if (!preconditionHolds) throw new PreconditionException("Debe indicar el telefono celular del contratante");
    }

    private void checkClas1431957012480960Pre149 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // drvValidarDatosContratante = false OR Aesolicitudes.PlanT.EsVoluntario = false OR Tramite <> 1 OR (ContratantePersona.CorreoElectronico <> NULL AND ContratantePersona.CorreoElectronico <> "")

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        boolean preconditionHolds = solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvValidarDatosContratante().equals(false) || solicitudvalidarAtributosUsuariopthisSolicitud.getAesolicitudes().getPlanT().getPlanesEsVoluntario().equals(false) || !(solicitudvalidarAtributosUsuariopthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(1)) || (solicitudvalidarAtributosUsuariopthisSolicitud.getContratantePersona().getPersonaCorreoElectronico() != null && (solicitudvalidarAtributosUsuariopthisSolicitud.getContratantePersona().getPersonaCorreoElectronico()).length() != 0);
        if (!preconditionHolds) throw new PreconditionException("Debe indicarse el correo electrónico del contratante");
    }

    private void checkClas1431957012480960Pre150 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // drvValidarDatosContratante = false OR Aesolicitudes.PlanT.EsVoluntario = false OR Tramite <> 1 OR (ContratantePersona.TelefonoCelular <> NULL AND ContratantePersona.TelefonoCelular <> "")

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        boolean preconditionHolds = solicitudvalidarAtributosUsuariopthisSolicitud.getSolicituddrvValidarDatosContratante().equals(false) || solicitudvalidarAtributosUsuariopthisSolicitud.getAesolicitudes().getPlanT().getPlanesEsVoluntario().equals(false) || !(solicitudvalidarAtributosUsuariopthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(1)) || (solicitudvalidarAtributosUsuariopthisSolicitud.getContratantePersona().getPersonaTelefonoCelular() != null && (solicitudvalidarAtributosUsuariopthisSolicitud.getContratantePersona().getPersonaTelefonoCelular()).length() != 0);
        if (!preconditionHolds) throw new PreconditionException("Debe indicarse el teléfono celular del contratante");
    }

    private void checkClas1431957012480960Pre151 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // COUNT( Usuarios ) = COUNT( Usuarios.Eps )

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = (repository.assocOperator144(solicitudvalidarAtributosUsuariopthisSolicitud)).equals(repository.assocOperator145(solicitudvalidarAtributosUsuariopthisSolicitud));
            if (!preconditionHolds) throw new PreconditionException("Debe indicar EPS para todos los beneficiarios");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre152 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // COUNT(Usuarios) = COUNT(Usuarios.Parentesco)

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = (repository.assocOperator146(solicitudvalidarAtributosUsuariopthisSolicitud)).equals(repository.assocOperator147(solicitudvalidarAtributosUsuariopthisSolicitud));
            if (!preconditionHolds) throw new PreconditionException("Debe indicar el parentesco para todos los beneficiarios");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre154 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest request) 
            throws ModelException {
        // Aesolicitudes.PlanT.EsVoluntario = true OR COUNT( Usuarios ) WHERE (Usuarios.BebeGestante = true AND Usuarios.TipoIdentificacion.MenorSinIdentificar = false) = 0

        Solicitud solicitudvalidarAtributosUsuariopthisSolicitud = request.getSolicitudvalidarAtributosUsuariopthisSolicitudInstance();
        try {
            boolean preconditionHolds = solicitudvalidarAtributosUsuariopthisSolicitud.getAesolicitudes().getPlanT().getPlanesEsVoluntario().equals(true) || (repository.assocOperator148(solicitudvalidarAtributosUsuariopthisSolicitud)).equals(Long.valueOf(0));
            if (!preconditionHolds) throw new PreconditionException("Debe indicar bebe gestante si está marcado como MS");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
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
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsContratoCASolicitudRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.insContratoCASolicitudTxn(request);
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
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsContratoCASolicitudRequest request) throws ModelException {
        request.setSolicitudInsContratoCASolicitudpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudInsContratoCASolicitudpthisSolicitud()));
        request.setSolicitudInsContratoCASolicitudpevcContratoCInstance(contratoCSrv.getByOIDWithHV(request.getSolicitudInsContratoCASolicitudpevcContratoC()));
        return solicitudSrv.insContratoCASolicitudImplementation(request, true);
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
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsContratoCASolicitudRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudInsContratoCASolicitudpthisSolicitud = request.getSolicitudInsContratoCASolicitudpthisSolicitudInstance();
        ContratoC solicitudInsContratoCASolicitudpevcContratoC = request.getSolicitudInsContratoCASolicitudpevcContratoCInstance();

        request.checkArguments();
        Solicitud instance = solicitudInsContratoCASolicitudpthisSolicitud;

        instance.add2ContratoC(solicitudInsContratoCASolicitudpevcContratoC);
        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsContratoCASolicitudRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsContratoCASolicitudRequest();
            relatedRequest.setContratoCInsContratoCASolicitudpthisContratoCInstance(solicitudInsContratoCASolicitudpevcContratoC);
            relatedRequest.setContratoCInsContratoCASolicitudpevcSolicitudInstance(solicitudInsContratoCASolicitudpthisSolicitud);
            contratoCSrv.insContratoCASolicitudImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: setCodTarifa

    /*
     * Entry point for service setCodTarifa
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse setCodTarifa(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.SetCodTarifaRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.setCodTarifaTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service setCodTarifa
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse setCodTarifaTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.SetCodTarifaRequest request) throws ModelException, SystemException {
        request.setSolicitudsetCodTarifapthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudsetCodTarifapthisSolicitud()));
        return solicitudSrv.setCodTarifaImplementation(request);
    }

    /*
     * Implements service setCodTarifa
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse setCodTarifaImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.SetCodTarifaRequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudsetCodTarifapthisSolicitud = request.getSolicitudsetCodTarifapthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudsetCodTarifapthisSolicitud;
        if (solicitudsetCodTarifapthisSolicitud.getSolicitudEsTraslado().equals(true)) {
            instance.setSolicitudCodTarifa(userFunctionsSrv.fugetValorParametroConfFun("TARIFATRASLADO"));
        } else {
            instance.setSolicitudCodTarifa(repository.assocOperator068(solicitudsetCodTarifapthisSolicitud));
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: dummyDevolverIntString

    /*
     * Entry point for service dummyDevolverIntString
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringResponse dummyDevolverIntString(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringRequest request) {
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringResponse response = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringResponse();
        try {
            response = solicitudSrv.dummyDevolverIntStringTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service dummyDevolverIntString
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringResponse dummyDevolverIntStringTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringRequest request) throws ModelException {
        request.setSolicituddummyDevolverIntStringpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicituddummyDevolverIntStringpthisSolicitud()));
        return solicitudSrv.dummyDevolverIntStringImplementation(request);
    }

    /*
     * Implements service dummyDevolverIntString
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringResponse dummyDevolverIntStringImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringRequest request) throws ModelException {
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringResponse response = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringResponse();
        response.setSolicituddummyDevolverIntStringpsBool(Boolean.FALSE);
        Solicitud solicituddummyDevolverIntStringpthisSolicitud = request.getSolicituddummyDevolverIntStringpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicituddummyDevolverIntStringpthisSolicitud;


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        // Value expressions for outgoing argument 'solicituddummyDevolverIntStringpsBool'
        // DEFAULT  : FALSE
        response.setSolicituddummyDevolverIntStringpsBool(Boolean.FALSE);


        // Value expressions for outgoing argument 'solicituddummyDevolverIntStringpsEstadoSolicitud'
        // DEFAULT  : 1
        response.setSolicituddummyDevolverIntStringpsEstadoSolicitud(Long.valueOf(1));


        return response;
    }

    // Service: dummyDevolverErroresWS

    /*
     * Entry point for service dummyDevolverErroresWS
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSResponse dummyDevolverErroresWS(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSRequest request) {
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSResponse response = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSResponse();
        try {
            response = solicitudSrv.dummyDevolverErroresWSTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service dummyDevolverErroresWS
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSResponse dummyDevolverErroresWSTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSRequest request) throws ModelException, SystemException {
        request.setSolicituddummyDevolverErroresWSpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicituddummyDevolverErroresWSpthisSolicitud()));
        return solicitudSrv.dummyDevolverErroresWSImplementation(request);
    }

    /*
     * Implements service dummyDevolverErroresWS
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSResponse dummyDevolverErroresWSImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSRequest request) throws ModelException, SystemException {
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSResponse response = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSResponse();
        Solicitud solicituddummyDevolverErroresWSpthisSolicitud = request.getSolicituddummyDevolverErroresWSpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicituddummyDevolverErroresWSpthisSolicitud;


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        // Value expressions for outgoing argument 'solicituddummyDevolverErroresWSpsErroresWS'
        // CONDITION: COUNT(ErroresWS) > 0
        // EFFECT   : fugetListaErrores( THIS )
        if (repository.assocOperator152(solicituddummyDevolverErroresWSpthisSolicitud) > Long.valueOf(0)) {
            response.setSolicituddummyDevolverErroresWSpsErroresWS(userFunctionsSrv.fugetListaErroresFun(solicituddummyDevolverErroresWSpthisSolicitud));
        }
        // CONDITION: Aesolicitudes.PlanT.EsVoluntario = true
        // EFFECT   : "La solicitud ha sido recibida por el sistema Core Beyond Health, en unos minutos proceda a consultar los mensajes enviados por el servicio, en el módulo Estatus de solicitudes, opción ver Errores"
         else if (solicituddummyDevolverErroresWSpthisSolicitud.getAesolicitudes().getPlanT().getPlanesEsVoluntario().equals(true)) {
            response.setSolicituddummyDevolverErroresWSpsErroresWS("La solicitud ha sido recibida por el sistema Core Beyond Health, en unos minutos proceda a consultar los mensajes enviados por el servicio, en el módulo Estatus de solicitudes, opción ver Errores");
        }
        // CONDITION: AGENTAfiliaciones = NULL AND AGENTMesaDeApoyo = NULL
        // EFFECT   : "Sr(a) Asesor, la solicitud pasó a validación por parte del área de Afiliaciones"
         else if ((((Afiliaciones) getAgentInstance(AfiliacionesConstants.CLASS_NAME)) == null || ((Afiliaciones) getAgentInstance(AfiliacionesConstants.CLASS_NAME)).isNull()) && (((MesaDeApoyo) getAgentInstance(MesaDeApoyoConstants.CLASS_NAME)) == null || ((MesaDeApoyo) getAgentInstance(MesaDeApoyoConstants.CLASS_NAME)).isNull())) {
            response.setSolicituddummyDevolverErroresWSpsErroresWS("Sr(a) Asesor, la solicitud pasó a validación por parte del área de Afiliaciones");
        }
         else {
            // DEFAULT  : ""
            response.setSolicituddummyDevolverErroresWSpsErroresWS("");
        }


        return response;
    }

    // Service: marcarCompletada

    /*
     * Entry point for service marcarCompletada
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse marcarCompletada(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarCompletadaRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.marcarCompletadaTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service marcarCompletada
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse marcarCompletadaTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarCompletadaRequest request) throws ModelException {
        request.setSolicitudmarcarCompletadapthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudmarcarCompletadapthisSolicitud()));
        return solicitudSrv.marcarCompletadaImplementation(request);
    }

    /*
     * Implements service marcarCompletada
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse marcarCompletadaImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarCompletadaRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudmarcarCompletadapthisSolicitud = request.getSolicitudmarcarCompletadapthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudmarcarCompletadapthisSolicitud;
        instance.setSolicitudCompletada(Boolean.TRUE);
        if (solicitudmarcarCompletadapthisSolicitud.getSolicitudFechaCompletada() == null) {
            instance.setSolicitudFechaCompletada(STDFunctions.systemDateTimeFun());
        }
        if (solicitudmarcarCompletadapthisSolicitud.getSolicitudFechaCompletada() == null) {
            instance.setSolicitudFechaFA(STDFunctions.systemDateTimeFun());
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: cambiarEstado

    /*
     * Entry point for service cambiarEstado
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse cambiarEstado(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CambiarEstadoRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.cambiarEstadoTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service cambiarEstado
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse cambiarEstadoTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CambiarEstadoRequest request) throws ModelException {
        request.setSolicitudcambiarEstadopthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudcambiarEstadopthisSolicitud()));
        return solicitudSrv.cambiarEstadoImplementation(request);
    }

    /*
     * Implements service cambiarEstado
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse cambiarEstadoImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CambiarEstadoRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudcambiarEstadopthisSolicitud = request.getSolicitudcambiarEstadopthisSolicitudInstance();
        Long solicitudcambiarEstadopeEstado = request.getSolicitudcambiarEstadopeEstado();

        request.checkArguments();
        Solicitud instance = solicitudcambiarEstadopthisSolicitud;
        if ((solicitudcambiarEstadopeEstado).equals(Long.valueOf(3)) && solicitudcambiarEstadopthisSolicitud.getSolicituddrvIgnorarPreexistencias().equals(true)) {
            instance.setSolicitudEstado(Long.valueOf(2));
        } else {
            instance.setSolicitudEstado(solicitudcambiarEstadopeEstado);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: marcarNoCompletada

    /*
     * Entry point for service marcarNoCompletada
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse marcarNoCompletada(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarNoCompletadaRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.marcarNoCompletadaTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service marcarNoCompletada
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse marcarNoCompletadaTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarNoCompletadaRequest request) throws ModelException {
        request.setSolicitudmarcarNoCompletadapthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudmarcarNoCompletadapthisSolicitud()));
        return solicitudSrv.marcarNoCompletadaImplementation(request);
    }

    /*
     * Implements service marcarNoCompletada
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse marcarNoCompletadaImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarNoCompletadaRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudmarcarNoCompletadapthisSolicitud = request.getSolicitudmarcarNoCompletadapthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudmarcarNoCompletadapthisSolicitud;
        instance.setSolicitudCompletada(Boolean.FALSE);
        instance.setSolicitudFechaCompletada(null);


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: InsUsrAreaMedica

    /*
     * Entry point for service InsUsrAreaMedica
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse insUsrAreaMedica(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsUsrAreaMedicaRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.insUsrAreaMedicaTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service InsUsrAreaMedica
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse insUsrAreaMedicaTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsUsrAreaMedicaRequest request) throws ModelException {
        request.setSolicitudInsUsrAreaMedicapthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudInsUsrAreaMedicapthisSolicitud()));
        request.setSolicitudInsUsrAreaMedicapevcAreaMedicaInstance(areaMedicaSrv.getByOIDWithHV(request.getSolicitudInsUsrAreaMedicapevcAreaMedica()));
        return solicitudSrv.insUsrAreaMedicaImplementation(request, true);
    }

    /*
     * Implements service InsUsrAreaMedica
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse insUsrAreaMedicaImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsUsrAreaMedicaRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudInsUsrAreaMedicapthisSolicitud = request.getSolicitudInsUsrAreaMedicapthisSolicitudInstance();
        AreaMedica solicitudInsUsrAreaMedicapevcAreaMedica = request.getSolicitudInsUsrAreaMedicapevcAreaMedicaInstance();

        request.checkArguments();
        Solicitud instance = solicitudInsUsrAreaMedicapthisSolicitud;

        instance.add2UsrAreaMedica(solicitudInsUsrAreaMedicapevcAreaMedica);
        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.areamedica.siu.InsUsrAreaMedicaRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.areamedica.siu.InsUsrAreaMedicaRequest();
            relatedRequest.setAreaMedicaInsUsrAreaMedicapthisAreaMedicaInstance(solicitudInsUsrAreaMedicapevcAreaMedica);
            relatedRequest.setAreaMedicaInsUsrAreaMedicapevcSolicitudInstance(solicitudInsUsrAreaMedicapthisSolicitud);
            areaMedicaSrv.insUsrAreaMedicaImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: calcFechaIniContPlanVol

    /*
     * Entry point for service calcFechaIniContPlanVol
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse calcFechaIniContPlanVol(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcFechaIniContPlanVolRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.calcFechaIniContPlanVolTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service calcFechaIniContPlanVol
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse calcFechaIniContPlanVolTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcFechaIniContPlanVolRequest request) throws ModelException {
        request.setSolicitudcalcFechaIniContPlanVolpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudcalcFechaIniContPlanVolpthisSolicitud()));
        return solicitudSrv.calcFechaIniContPlanVolImplementation(request);
    }

    /*
     * Implements service calcFechaIniContPlanVol
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse calcFechaIniContPlanVolImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcFechaIniContPlanVolRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudcalcFechaIniContPlanVolpthisSolicitud = request.getSolicitudcalcFechaIniContPlanVolpthisSolicitudInstance();
        Date solicitudcalcFechaIniContPlanVolpFechaDeParametrizacion = request.getSolicitudcalcFechaIniContPlanVolpFechaDeParametrizacion();
        Date solicitudcalcFechaIniContPlanVolpFechaConfirmacion = request.getSolicitudcalcFechaIniContPlanVolpFechaConfirmacion();
        Long solicitudcalcFechaIniContPlanVolpDiaInicio = request.getSolicitudcalcFechaIniContPlanVolpDiaInicio();
        Long solicitudcalcFechaIniContPlanVolpDiaFin = request.getSolicitudcalcFechaIniContPlanVolpDiaFin();

        request.checkArguments();

        calcFechaIniContPlanVolPreconditions(request);
        Solicitud instance = solicitudcalcFechaIniContPlanVolpthisSolicitud;
        if (solicitudcalcFechaIniContPlanVolpFechaDeParametrizacion != null) {
            instance.setSolicitudFInicioContrato(solicitudcalcFechaIniContPlanVolpFechaDeParametrizacion);
        } else if (STDFunctions.getDayFun(solicitudcalcFechaIniContPlanVolpFechaConfirmacion) >= solicitudcalcFechaIniContPlanVolpDiaInicio && STDFunctions.getDayFun(solicitudcalcFechaIniContPlanVolpFechaConfirmacion) <= solicitudcalcFechaIniContPlanVolpDiaFin) {
            instance.setSolicitudFInicioContrato(STDFunctions.dateAddFun("m", Long.valueOf(1), STDFunctions.formatToDateFun(STDFunctions.getYearFun(solicitudcalcFechaIniContPlanVolpFechaConfirmacion), STDFunctions.getMonthFun(solicitudcalcFechaIniContPlanVolpFechaConfirmacion), Long.valueOf(1))));
        } else {
            instance.setSolicitudFInicioContrato(STDFunctions.dateAddFun("m", Long.valueOf(2), STDFunctions.formatToDateFun(STDFunctions.getYearFun(solicitudcalcFechaIniContPlanVolpFechaConfirmacion), STDFunctions.getMonthFun(solicitudcalcFechaIniContPlanVolpFechaConfirmacion), Long.valueOf(1))));
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    private void calcFechaIniContPlanVolPreconditions(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcFechaIniContPlanVolRequest request) 
            throws ModelException {
        checkClas1431957012480960Pre116(request);
    }

    private void checkClas1431957012480960Pre116 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcFechaIniContPlanVolRequest request) 
            throws ModelException {
        // COUNT( Aesolicitudes.PlanT.Dominios ) WHERE (Aesolicitudes.PlanT.Dominios.ProductoT = Aesolicitudes.ProductoT AND Aesolicitudes.PlanT.Dominios.DiaIniVigencia <> NULL AND Aesolicitudes.PlanT.Dominios.DiaFinVigencia <> NULL) > 0

        Solicitud solicitudcalcFechaIniContPlanVolpthisSolicitud = request.getSolicitudcalcFechaIniContPlanVolpthisSolicitudInstance();
        try {
            boolean preconditionHolds = repository.assocOperator150(solicitudcalcFechaIniContPlanVolpthisSolicitud) > Long.valueOf(0);
            if (!preconditionHolds) throw new PreconditionException("No se ha encontrado la parametrización en el Dominio del dia de inicio y fin de vigencia");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    // Service: dummyValidacionesFInicioCont

    /*
     * Entry point for service dummyValidacionesFInicioCont
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse dummyValidacionesFInicioCont(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyValidacionesFInicioContRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.dummyValidacionesFInicioContTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service dummyValidacionesFInicioCont
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse dummyValidacionesFInicioContTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyValidacionesFInicioContRequest request) throws ModelException {
        request.setSolicituddummyValidacionesFInicioContpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicituddummyValidacionesFInicioContpthisSolicitud()));
        return solicitudSrv.dummyValidacionesFInicioContImplementation(request);
    }

    /*
     * Implements service dummyValidacionesFInicioCont
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse dummyValidacionesFInicioContImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyValidacionesFInicioContRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicituddummyValidacionesFInicioContpthisSolicitud = request.getSolicituddummyValidacionesFInicioContpthisSolicitudInstance();

        request.checkArguments();

        dummyValidacionesFInicioContPreconditions(request);
        Solicitud instance = solicituddummyValidacionesFInicioContpthisSolicitud;


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    private void dummyValidacionesFInicioContPreconditions(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyValidacionesFInicioContRequest request) 
            throws ModelException {
        checkClas1431957012480960Pre122(request);
    }

    private void checkClas1431957012480960Pre122 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyValidacionesFInicioContRequest request) 
            throws ModelException {
        // (Tramite = 2 AND tipoContrato = 3 AND NumFamilia = NULL) OR EsTraslado = true OR EXIST( Aesolicitudes.ProductoT.Tarifas.DetallesTarifa ) WHERE (Aesolicitudes.ProductoT.Tarifas.DetallesTarifa.Tarifa.PlanT = Aesolicitudes.PlanT AND Aesolicitudes.ProductoT.Tarifas.DetallesTarifa.Anyo = getYear(FInicioContrato)) = true

        Solicitud solicituddummyValidacionesFInicioContpthisSolicitud = request.getSolicituddummyValidacionesFInicioContpthisSolicitudInstance();
        try {
            boolean preconditionHolds = ((solicituddummyValidacionesFInicioContpthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(2)) && (solicituddummyValidacionesFInicioContpthisSolicitud.getSolicitudtipoContrato()).equals(Long.valueOf(3)) && solicituddummyValidacionesFInicioContpthisSolicitud.getSolicitudNumFamilia() == null) || solicituddummyValidacionesFInicioContpthisSolicitud.getSolicitudEsTraslado().equals(true) || repository.assocOperator112(solicituddummyValidacionesFInicioContpthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("No existe la Tarifa para el Producto, Plan y Año");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    // Service: InsUsrAfiliacion

    /*
     * Entry point for service InsUsrAfiliacion
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse insUsrAfiliacion(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsUsrAfiliacionRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.insUsrAfiliacionTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service InsUsrAfiliacion
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse insUsrAfiliacionTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsUsrAfiliacionRequest request) throws ModelException {
        request.setSolicitudInsUsrAfiliacionpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudInsUsrAfiliacionpthisSolicitud()));
        request.setSolicitudInsUsrAfiliacionpevcFuncionarioInstance(funcionarioSrv.getByOIDWithHV(request.getSolicitudInsUsrAfiliacionpevcFuncionario()));
        return solicitudSrv.insUsrAfiliacionImplementation(request, true);
    }

    /*
     * Implements service InsUsrAfiliacion
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse insUsrAfiliacionImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsUsrAfiliacionRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudInsUsrAfiliacionpthisSolicitud = request.getSolicitudInsUsrAfiliacionpthisSolicitudInstance();
        Funcionario solicitudInsUsrAfiliacionpevcFuncionario = request.getSolicitudInsUsrAfiliacionpevcFuncionarioInstance();

        request.checkArguments();
        Solicitud instance = solicitudInsUsrAfiliacionpthisSolicitud;

        instance.add2UsrAfiliacion(solicitudInsUsrAfiliacionpevcFuncionario);
        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.funcionario.siu.InsUsrAfiliacionRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.funcionario.siu.InsUsrAfiliacionRequest();
            relatedRequest.setFuncionarioInsUsrAfiliacionpthisFuncionarioInstance(solicitudInsUsrAfiliacionpevcFuncionario);
            relatedRequest.setFuncionarioInsUsrAfiliacionpevcSolicitudInstance(solicitudInsUsrAfiliacionpthisSolicitud);
            funcionarioSrv.insUsrAfiliacionImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: asignarFechaTerminada

    /*
     * Entry point for service asignarFechaTerminada
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse asignarFechaTerminada(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFechaTerminadaRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.asignarFechaTerminadaTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service asignarFechaTerminada
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse asignarFechaTerminadaTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFechaTerminadaRequest request) throws ModelException {
        request.setSolicitudasignarFechaTerminadapthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudasignarFechaTerminadapthisSolicitud()));
        return solicitudSrv.asignarFechaTerminadaImplementation(request);
    }

    /*
     * Implements service asignarFechaTerminada
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse asignarFechaTerminadaImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFechaTerminadaRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudasignarFechaTerminadapthisSolicitud = request.getSolicitudasignarFechaTerminadapthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudasignarFechaTerminadapthisSolicitud;
        instance.setSolicitudFechaTerminada(STDFunctions.systemDateFun());
        instance.setSolicitudSemaforoGrabAse(userFunctionsSrv.fuCallSPgetsemaforograbadorFun(solicitudasignarFechaTerminadapthisSolicitud.getAesolicitudes().getAesolicitudesidsolicitud()));
        instance.setSolicitudSemaforoAreaMed(userFunctionsSrv.fuCallSPgetsemaforoareamedFun(solicitudasignarFechaTerminadapthisSolicitud.getAesolicitudes().getAesolicitudesidsolicitud()));


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: asignarPrioridad

    /*
     * Entry point for service asignarPrioridad
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse asignarPrioridad(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarPrioridadRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.asignarPrioridadTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service asignarPrioridad
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse asignarPrioridadTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarPrioridadRequest request) throws ModelException {
        request.setSolicitudasignarPrioridadpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudasignarPrioridadpthisSolicitud()));
        return solicitudSrv.asignarPrioridadImplementation(request);
    }

    /*
     * Implements service asignarPrioridad
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse asignarPrioridadImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarPrioridadRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudasignarPrioridadpthisSolicitud = request.getSolicitudasignarPrioridadpthisSolicitudInstance();
        Long solicitudasignarPrioridadpePrioridad = request.getSolicitudasignarPrioridadpePrioridad();

        request.checkArguments();
        Solicitud instance = solicitudasignarPrioridadpthisSolicitud;
        if (solicitudasignarPrioridadpePrioridad > Long.valueOf(0)) {
            instance.setSolicitudPrioridad(solicitudasignarPrioridadpePrioridad);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: asignarFAAM

    /*
     * Entry point for service asignarFAAM
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse asignarFAAM(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFAAMRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.asignarFAAMTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service asignarFAAM
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse asignarFAAMTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFAAMRequest request) throws ModelException {
        request.setSolicitudasignarFAAMpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudasignarFAAMpthisSolicitud()));
        return solicitudSrv.asignarFAAMImplementation(request);
    }

    /*
     * Implements service asignarFAAM
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse asignarFAAMImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFAAMRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudasignarFAAMpthisSolicitud = request.getSolicitudasignarFAAMpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudasignarFAAMpthisSolicitud;
        instance.setSolicitudFechaFAAM(STDFunctions.systemDateTimeFun());


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: asignarFUG

    /*
     * Entry point for service asignarFUG
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse asignarFUG(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFUGRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.asignarFUGTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service asignarFUG
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse asignarFUGTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFUGRequest request) throws ModelException {
        request.setSolicitudasignarFUGpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudasignarFUGpthisSolicitud()));
        return solicitudSrv.asignarFUGImplementation(request);
    }

    /*
     * Implements service asignarFUG
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse asignarFUGImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFUGRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudasignarFUGpthisSolicitud = request.getSolicitudasignarFUGpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudasignarFUGpthisSolicitud;
        instance.setSolicitudFechaFUG(STDFunctions.systemDateTimeFun());


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: enviarEmail

    /*
     * Entry point for service enviarEmail
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.EnviarEmailResponse enviarEmail(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.EnviarEmailRequest request) {
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.EnviarEmailResponse response = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.EnviarEmailResponse();
        try {
            response = solicitudSrv.enviarEmailTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service enviarEmail
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.EnviarEmailResponse enviarEmailTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.EnviarEmailRequest request) throws ModelException {
        request.setSolicitudenviarEmailpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudenviarEmailpthisSolicitud()));
        return solicitudSrv.enviarEmailImplementation(request);
    }

    /*
     * Implements service enviarEmail
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.EnviarEmailResponse enviarEmailImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.EnviarEmailRequest request) throws ModelException {
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.EnviarEmailResponse response = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.EnviarEmailResponse();
        String solicitudenviarEmailpeAsunto = request.getSolicitudenviarEmailpeAsunto();
        String solicitudenviarEmailpeDestinatario = request.getSolicitudenviarEmailpeDestinatario();
        String solicitudenviarEmailpeEmailConfirmacion = request.getSolicitudenviarEmailpeEmailConfirmacion();
        String solicitudenviarEmailpeCuerpo = request.getSolicitudenviarEmailpeCuerpo();
        String solicitudenviarEmailpeNombreAdjunto = request.getSolicitudenviarEmailpeNombreAdjunto();
        byte[] solicitudenviarEmailpeAdjunto = request.getSolicitudenviarEmailpeAdjunto();
        Solicitud solicitudenviarEmailpthisSolicitud = request.getSolicitudenviarEmailpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudenviarEmailpthisSolicitud;


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        // Value expressions for outgoing argument 'solicitudenviarEmailoaResultado'
        // DEFAULT  : fuEnviarEmail( peAsunto, peDestinatario,peEmailConfirmacion,peCuerpo, peNombreAdjunto, peAdjunto, Aesolicitudes.Numerosolicitud)
        response.setSolicitudenviarEmailoaResultado(userFunctionsSrv.fuEnviarEmailFun(solicitudenviarEmailpeAsunto, solicitudenviarEmailpeDestinatario, solicitudenviarEmailpeEmailConfirmacion, solicitudenviarEmailpeCuerpo, solicitudenviarEmailpeNombreAdjunto, solicitudenviarEmailpeAdjunto, solicitudenviarEmailpthisSolicitud.getAesolicitudes().getAesolicitudesNumerosolicitud()));


        return response;
    }

    // Service: TNOTIFICARANULACION

    /*
     * Entry point for service TNOTIFICARANULACION
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tNOTIFICARANULACION(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARANULACIONRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.tNOTIFICARANULACIONTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TNOTIFICARANULACION
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tNOTIFICARANULACIONTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARANULACIONRequest request) throws ModelException, SystemException {
        request.setSolicitudTNOTIFICARANULACIONpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTNOTIFICARANULACIONpthisSolicitud()));
        return solicitudSrv.tNOTIFICARANULACIONImplementation(request);
    }

    /*
     * Implements service TNOTIFICARANULACION
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tNOTIFICARANULACIONImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARANULACIONRequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudTNOTIFICARANULACIONpthisSolicitud = request.getSolicitudTNOTIFICARANULACIONpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudTNOTIFICARANULACIONpthisSolicitud;

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarCompletadaRequest step1Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarCompletadaRequest();
        step1Request.setSolicitudmarcarCompletadapthisSolicitudInstance(solicitudTNOTIFICARANULACIONpthisSolicitud);
        solicitudSrv.marcarCompletadaImplementation(step1Request);

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest step2Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest();
        step2Request.setSolicitudTCAMBIARESTADOpthisSolicitudInstance(solicitudTNOTIFICARANULACIONpthisSolicitud);
        step2Request.setSolicitudTCAMBIARESTADOptpeEstado(Long.valueOf(4));
        step2Request.setSolicitudTCAMBIARESTADOptObservaciones(null);
        step2Request.setSolicitudTCAMBIARESTADOptMotDevolucion(null);
        solicitudSrv.tCAMBIARESTADOImplementation(step2Request);

        com.keralty.aegenerasolicitud.viewmodel.aesolicitudes.siu.ModificarFechaElabRequest step3Request = new com.keralty.aegenerasolicitud.viewmodel.aesolicitudes.siu.ModificarFechaElabRequest();
        step3Request.setAesolicitudesmodificarFechaElabpthisAESOLICITUDESInstance(solicitudTNOTIFICARANULACIONpthisSolicitud.getAesolicitudes());
        step3Request.setAesolicitudesmodificarFechaElabpeFecha(STDFunctions.systemDateFun());
        aesolicitudesSrv.modificarFechaElabImplementation(step3Request);

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: TNOTIFICARCREAR

    /*
     * Entry point for service TNOTIFICARCREAR
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tNOTIFICARCREAR(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARCREARRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.tNOTIFICARCREARTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TNOTIFICARCREAR
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tNOTIFICARCREARTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARCREARRequest request) throws ModelException, SystemException {
        request.setSolicitudTNOTIFICARCREARpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTNOTIFICARCREARpthisSolicitud()));
        return solicitudSrv.tNOTIFICARCREARImplementation(request);
    }

    /*
     * Implements service TNOTIFICARCREAR
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tNOTIFICARCREARImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARCREARRequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        String solicitudTNOTIFICARCREARptNumFamiliaCORE = request.getSolicitudTNOTIFICARCREARptNumFamiliaCORE();
        Boolean solicitudTNOTIFICARCREARptConfiCierreSolicitud = request.getSolicitudTNOTIFICARCREARptConfiCierreSolicitud();
        Solicitud solicitudTNOTIFICARCREARpthisSolicitud = request.getSolicitudTNOTIFICARCREARpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudTNOTIFICARCREARpthisSolicitud;

        /* Action 1: IF (EXIST (ContratoC) = false) */
        boolean ifCondition1 = repository.assocOperator176(solicitudTNOTIFICARCREARpthisSolicitud).equals(false);
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAERequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAERequest();
            step10Request.setContratoCTNEWDESDEAEptSolicitudInstance(solicitudTNOTIFICARCREARpthisSolicitud);
            step10Request.setContratoCTNEWDESDEAEptNumFamiliaCORE(solicitudTNOTIFICARCREARptNumFamiliaCORE);
            contratoCSrv.tNEWDESDEAEImplementation(step10Request);
            
            
        }

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarCompletadaRequest step2Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarCompletadaRequest();
        step2Request.setSolicitudmarcarCompletadapthisSolicitudInstance(solicitudTNOTIFICARCREARpthisSolicitud);
        solicitudSrv.marcarCompletadaImplementation(step2Request);

        /* Action 3: IF (fugetValorParametroConf("MAXIMC") <> NULL AND fugetValorParametroConf("EDADIMC") <> NULL AND EXIST (ContratoC.BeneficiariosC.PersonaC) WHERE (ContratoC.BeneficiariosC.PersonaC.drvEdad >= StrToInt(fugetValorParametroConf("EDADIMC")) AND ContratoC.BeneficiariosC.PersonaC.drvIMC > StrToInt(fugetValorParametroConf("MAXIMC"))) = true) */
        boolean ifCondition3 = userFunctionsSrv.fugetValorParametroConfFun("MAXIMC") != null && userFunctionsSrv.fugetValorParametroConfFun("EDADIMC") != null && repository.assocOperator177(solicitudTNOTIFICARCREARpthisSolicitud).equals(true);
        if (ifCondition3) {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest step30Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest();
            step30Request.setSolicitudTCAMBIARESTADOpthisSolicitudInstance(solicitudTNOTIFICARCREARpthisSolicitud);
            step30Request.setSolicitudTCAMBIARESTADOptpeEstado(Long.valueOf(5));
            step30Request.setSolicitudTCAMBIARESTADOptObservaciones("IMC");
            step30Request.setSolicitudTCAMBIARESTADOptMotDevolucion(null);
            solicitudSrv.tCAMBIARESTADOImplementation(step30Request);
        } else {
            /* Action 31: IF (EXIST (ContratoC.BeneficiariosC) WHERE (ContratoC.BeneficiariosC.TienePreexistencias = true) = true) */
            boolean ifCondition31 = repository.assocOperator178(solicitudTNOTIFICARCREARpthisSolicitud).equals(true);
            if (ifCondition31) {
                com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest step310Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest();
                step310Request.setSolicitudTCAMBIARESTADOpthisSolicitudInstance(solicitudTNOTIFICARCREARpthisSolicitud);
                step310Request.setSolicitudTCAMBIARESTADOptpeEstado(Long.valueOf(3));
                step310Request.setSolicitudTCAMBIARESTADOptObservaciones(null);
                step310Request.setSolicitudTCAMBIARESTADOptMotDevolucion(null);
                solicitudSrv.tCAMBIARESTADOImplementation(step310Request);
            } else {
                com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest step311Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest();
                step311Request.setSolicitudTCAMBIARESTADOpthisSolicitudInstance(solicitudTNOTIFICARCREARpthisSolicitud);
                step311Request.setSolicitudTCAMBIARESTADOptpeEstado(Long.valueOf(2));
                step311Request.setSolicitudTCAMBIARESTADOptObservaciones(null);
                step311Request.setSolicitudTCAMBIARESTADOptMotDevolucion(null);
                solicitudSrv.tCAMBIARESTADOImplementation(step311Request);
            }
        }

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ModificarDatosCORERequest step4Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ModificarDatosCORERequest();
        step4Request.setSolicitudmodificarDatosCOREpthisSolicitudInstance(solicitudTNOTIFICARCREARpthisSolicitud);
        step4Request.setSolicitudmodificarDatosCOREpNumContrato(solicitudTNOTIFICARCREARpthisSolicitud.getContratoC().getContratoCNumContrato());
        step4Request.setSolicitudmodificarDatosCOREpvalorCuotaPeriodoC(solicitudTNOTIFICARCREARpthisSolicitud.getContratoC().getContratoCvalorCuotaPeriodo());
        step4Request.setSolicitudmodificarDatosCOREpdescuentoComercialC(solicitudTNOTIFICARCREARpthisSolicitud.getContratoC().getContratoCdescuentoComercial());
        step4Request.setSolicitudmodificarDatosCOREpIVAC(solicitudTNOTIFICARCREARpthisSolicitud.getContratoC().getContratoCIVA());
        step4Request.setSolicitudmodificarDatosCOREpdescuentoFinancieroC(solicitudTNOTIFICARCREARpthisSolicitud.getContratoC().getContratoCdescuentoFinanciero());
        step4Request.setSolicitudmodificarDatosCOREpvalorCuotaBrutaC(solicitudTNOTIFICARCREARpthisSolicitud.getContratoC().getContratoCdrvValorCuotaBruta());
        solicitudSrv.modificarDatosCOREImplementation(step4Request);

        for(BeneficiarioC step5Entity : repository.assocOperator175(solicitudTNOTIFICARCREARpthisSolicitud)) {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TUPDATEBENEFICIARIORequest step5Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TUPDATEBENEFICIARIORequest();
            step5Request.setSolicitudTUPDATEBENEFICIARIOpthisSolicitudInstance(solicitudTNOTIFICARCREARpthisSolicitud);
            step5Request.setSolicitudTUPDATEBENEFICIARIOpBeneficiarioCInstance(step5Entity);
            solicitudSrv.tUPDATEBENEFICIARIOImplementation(step5Request);
        }

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcularDifLiquidacionRequest step6Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcularDifLiquidacionRequest();
        step6Request.setSolicitudcalcularDifLiquidacionpthisSolicitudInstance(solicitudTNOTIFICARCREARpthisSolicitud);
        solicitudSrv.calcularDifLiquidacionImplementation(step6Request);

        com.keralty.aegenerasolicitud.viewmodel.aesolicitudes.siu.ModificarFechaElabRequest step7Request = new com.keralty.aegenerasolicitud.viewmodel.aesolicitudes.siu.ModificarFechaElabRequest();
        step7Request.setAesolicitudesmodificarFechaElabpthisAESOLICITUDESInstance(solicitudTNOTIFICARCREARpthisSolicitud.getAesolicitudes());
        step7Request.setAesolicitudesmodificarFechaElabpeFecha(STDFunctions.systemDateFun());
        aesolicitudesSrv.modificarFechaElabImplementation(step7Request);

        /* Action 8: IF (ptConfiCierreSolicitud = true) */
        boolean ifCondition8 = solicitudTNOTIFICARCREARptConfiCierreSolicitud.equals(true);
        if (ifCondition8) {
            com.keralty.aegenerasolicitud.viewmodel.aesolicitudes.siu.CambiarEstadoRequest step80Request = new com.keralty.aegenerasolicitud.viewmodel.aesolicitudes.siu.CambiarEstadoRequest();
            step80Request.setAesolicitudescambiarEstadopthisAESOLICITUDESInstance(solicitudTNOTIFICARCREARpthisSolicitud.getAesolicitudes());
            step80Request.setAesolicitudescambiarEstadopSWESTADO("N");
            aesolicitudesSrv.cambiarEstadoImplementation(step80Request);
        }

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TENVIARDOCAUTORequest step9Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TENVIARDOCAUTORequest();
        step9Request.setSolicitudTENVIARDOCAUTOpthisSolicitudInstance(solicitudTNOTIFICARCREARpthisSolicitud);
        solicitudSrv.tENVIARDOCAUTOImplementation(step9Request);

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: TUPDATEBENEFICIARIO

    /*
     * Entry point for service TUPDATEBENEFICIARIO
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tUPDATEBENEFICIARIO(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TUPDATEBENEFICIARIORequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.tUPDATEBENEFICIARIOTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TUPDATEBENEFICIARIO
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tUPDATEBENEFICIARIOTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TUPDATEBENEFICIARIORequest request) throws ModelException, SystemException {
        request.setSolicitudTUPDATEBENEFICIARIOpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTUPDATEBENEFICIARIOpthisSolicitud()));
        request.setSolicitudTUPDATEBENEFICIARIOpBeneficiarioCInstance(beneficiarioCSrv.getByOIDWithHV(request.getSolicitudTUPDATEBENEFICIARIOpBeneficiarioC()));
        return solicitudSrv.tUPDATEBENEFICIARIOImplementation(request);
    }

    /*
     * Implements service TUPDATEBENEFICIARIO
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tUPDATEBENEFICIARIOImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TUPDATEBENEFICIARIORequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        BeneficiarioC solicitudTUPDATEBENEFICIARIOpBeneficiarioC = request.getSolicitudTUPDATEBENEFICIARIOpBeneficiarioCInstance();
        Solicitud solicitudTUPDATEBENEFICIARIOpthisSolicitud = request.getSolicitudTUPDATEBENEFICIARIOpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudTUPDATEBENEFICIARIOpthisSolicitud;

        /* Action 1: IF (EXIST (pBeneficiarioC.EstadoBeneficiarioC) = false) */
        boolean ifCondition1 = repository.assocOperator182(solicitudTUPDATEBENEFICIARIOpBeneficiarioC).equals(false);
        if (ifCondition1) {
            for(BeneficiarioC step110Entity : repository.assocOperator180(solicitudTUPDATEBENEFICIARIOpthisSolicitud, solicitudTUPDATEBENEFICIARIOpBeneficiarioC)) {
                com.keralty.aegenerasolicitud.viewmodel.beneficiarioc.siu.TASGESTADOSINOTIENERequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.beneficiarioc.siu.TASGESTADOSINOTIENERequest();
                step10Request.setBeneficiarioCTASGESTADOSINOTIENEpthisBeneficiarioCInstance(step110Entity);
                step10Request.setBeneficiarioCTASGESTADOSINOTIENEptEstadoInstance(userFunctionsSrv.fuGetEstadoPersonaErrorFun());
                beneficiarioCSrv.tASGESTADOSINOTIENEImplementation(step10Request);
            }
        }

        for(Usuario step2Entity : repository.assocOperator181(solicitudTUPDATEBENEFICIARIOpthisSolicitud, solicitudTUPDATEBENEFICIARIOpBeneficiarioC)) {
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.TACTDESDECORERequest step2Request = new com.keralty.aegenerasolicitud.viewmodel.usuario.siu.TACTDESDECORERequest();
            step2Request.setUsuarioTACTDESDECOREpthisUsuarioInstance(step2Entity);
            step2Request.setUsuarioTACTDESDECOREpBeneficiarioCInstance(solicitudTUPDATEBENEFICIARIOpBeneficiarioC);
            usuarioSrv.tACTDESDECOREImplementation(step2Request);
        }

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: TNOTIFICARINCLUSION

    /*
     * Entry point for service TNOTIFICARINCLUSION
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tNOTIFICARINCLUSION(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARINCLUSIONRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.tNOTIFICARINCLUSIONTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TNOTIFICARINCLUSION
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tNOTIFICARINCLUSIONTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARINCLUSIONRequest request) throws ModelException, SystemException {
        request.setSolicitudTNOTIFICARINCLUSIONpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTNOTIFICARINCLUSIONpthisSolicitud()));
        return solicitudSrv.tNOTIFICARINCLUSIONImplementation(request);
    }

    /*
     * Implements service TNOTIFICARINCLUSION
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tNOTIFICARINCLUSIONImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARINCLUSIONRequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        String solicitudTNOTIFICARINCLUSIONptNumFamiliaCORE = request.getSolicitudTNOTIFICARINCLUSIONptNumFamiliaCORE();
        Boolean solicitudTNOTIFICARINCLUSIONptConfiCierreSolicitud = request.getSolicitudTNOTIFICARINCLUSIONptConfiCierreSolicitud();
        Solicitud solicitudTNOTIFICARINCLUSIONpthisSolicitud = request.getSolicitudTNOTIFICARINCLUSIONpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudTNOTIFICARINCLUSIONpthisSolicitud;

        /* Action 1: IF (EXIST (ContratoC) = false) */
        boolean ifCondition1 = repository.assocOperator186(solicitudTNOTIFICARINCLUSIONpthisSolicitud).equals(false);
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAERequest step1000Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAERequest();
            step1000Request.setContratoCTNEWDESDEAEptSolicitudInstance(solicitudTNOTIFICARINCLUSIONpthisSolicitud);
            step1000Request.setContratoCTNEWDESDEAEptNumFamiliaCORE(solicitudTNOTIFICARINCLUSIONptNumFamiliaCORE);
            contratoCSrv.tNEWDESDEAEImplementation(step1000Request);
            
            
        }

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarCompletadaRequest step2Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarCompletadaRequest();
        step2Request.setSolicitudmarcarCompletadapthisSolicitudInstance(solicitudTNOTIFICARINCLUSIONpthisSolicitud);
        solicitudSrv.marcarCompletadaImplementation(step2Request);

        /* Action 3: IF (ptConfiCierreSolicitud = true) */
        boolean ifCondition3 = solicitudTNOTIFICARINCLUSIONptConfiCierreSolicitud.equals(true);
        if (ifCondition3) {
            /* Action 30: IF (EXIST (ContratoC.BeneficiariosC) WHERE (ContratoC.BeneficiariosC.TienePreexistencias = true) = true) */
            boolean ifCondition30 = repository.assocOperator187(solicitudTNOTIFICARINCLUSIONpthisSolicitud).equals(true);
            if (ifCondition30) {
                com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest step300Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest();
                step300Request.setSolicitudTCAMBIARESTADOpthisSolicitudInstance(solicitudTNOTIFICARINCLUSIONpthisSolicitud);
                step300Request.setSolicitudTCAMBIARESTADOptpeEstado(Long.valueOf(3));
                step300Request.setSolicitudTCAMBIARESTADOptObservaciones(null);
                step300Request.setSolicitudTCAMBIARESTADOptMotDevolucion(null);
                solicitudSrv.tCAMBIARESTADOImplementation(step300Request);
            } else {
                com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest step301Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest();
                step301Request.setSolicitudTCAMBIARESTADOpthisSolicitudInstance(solicitudTNOTIFICARINCLUSIONpthisSolicitud);
                step301Request.setSolicitudTCAMBIARESTADOptpeEstado(Long.valueOf(2));
                step301Request.setSolicitudTCAMBIARESTADOptObservaciones(null);
                step301Request.setSolicitudTCAMBIARESTADOptMotDevolucion(null);
                solicitudSrv.tCAMBIARESTADOImplementation(step301Request);
            }
        }

        com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CalcularLiquidacionRequest step4Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.CalcularLiquidacionRequest();
        step4Request.setContratoCCalcularLiquidacionpthisContratoCInstance(solicitudTNOTIFICARINCLUSIONpthisSolicitud.getContratoC());
        contratoCSrv.calcularLiquidacionImplementation(step4Request);

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ModificarDatosCORERequest step5Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ModificarDatosCORERequest();
        step5Request.setSolicitudmodificarDatosCOREpthisSolicitudInstance(solicitudTNOTIFICARINCLUSIONpthisSolicitud);
        step5Request.setSolicitudmodificarDatosCOREpNumContrato(solicitudTNOTIFICARINCLUSIONpthisSolicitud.getContratoC().getContratoCNumContrato());
        step5Request.setSolicitudmodificarDatosCOREpvalorCuotaPeriodoC(solicitudTNOTIFICARINCLUSIONpthisSolicitud.getContratoC().getContratoCvalorCuotaPeriodo());
        step5Request.setSolicitudmodificarDatosCOREpdescuentoComercialC(solicitudTNOTIFICARINCLUSIONpthisSolicitud.getContratoC().getContratoCdescuentoComercial());
        step5Request.setSolicitudmodificarDatosCOREpIVAC(solicitudTNOTIFICARINCLUSIONpthisSolicitud.getContratoC().getContratoCIVA());
        step5Request.setSolicitudmodificarDatosCOREpdescuentoFinancieroC(solicitudTNOTIFICARINCLUSIONpthisSolicitud.getContratoC().getContratoCdescuentoFinanciero());
        step5Request.setSolicitudmodificarDatosCOREpvalorCuotaBrutaC(solicitudTNOTIFICARINCLUSIONpthisSolicitud.getContratoC().getContratoCdrvValorCuotaBruta());
        solicitudSrv.modificarDatosCOREImplementation(step5Request);

        for(BeneficiarioC step6Entity : repository.assocOperator185(solicitudTNOTIFICARINCLUSIONpthisSolicitud)) {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TUPDATEBENEFICIARIORequest step6Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TUPDATEBENEFICIARIORequest();
            step6Request.setSolicitudTUPDATEBENEFICIARIOpthisSolicitudInstance(solicitudTNOTIFICARINCLUSIONpthisSolicitud);
            step6Request.setSolicitudTUPDATEBENEFICIARIOpBeneficiarioCInstance(step6Entity);
            solicitudSrv.tUPDATEBENEFICIARIOImplementation(step6Request);
        }

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcularDifLiquidacionRequest step7Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcularDifLiquidacionRequest();
        step7Request.setSolicitudcalcularDifLiquidacionpthisSolicitudInstance(solicitudTNOTIFICARINCLUSIONpthisSolicitud);
        solicitudSrv.calcularDifLiquidacionImplementation(step7Request);

        com.keralty.aegenerasolicitud.viewmodel.aesolicitudes.siu.ModificarFechaElabRequest step8Request = new com.keralty.aegenerasolicitud.viewmodel.aesolicitudes.siu.ModificarFechaElabRequest();
        step8Request.setAesolicitudesmodificarFechaElabpthisAESOLICITUDESInstance(solicitudTNOTIFICARINCLUSIONpthisSolicitud.getAesolicitudes());
        step8Request.setAesolicitudesmodificarFechaElabpeFecha(STDFunctions.systemDateFun());
        aesolicitudesSrv.modificarFechaElabImplementation(step8Request);

        /* Action 9: IF (ptConfiCierreSolicitud = true) */
        boolean ifCondition9 = solicitudTNOTIFICARINCLUSIONptConfiCierreSolicitud.equals(true);
        if (ifCondition9) {
            com.keralty.aegenerasolicitud.viewmodel.aesolicitudes.siu.CambiarEstadoRequest step90Request = new com.keralty.aegenerasolicitud.viewmodel.aesolicitudes.siu.CambiarEstadoRequest();
            step90Request.setAesolicitudescambiarEstadopthisAESOLICITUDESInstance(solicitudTNOTIFICARINCLUSIONpthisSolicitud.getAesolicitudes());
            step90Request.setAesolicitudescambiarEstadopSWESTADO("N");
            aesolicitudesSrv.cambiarEstadoImplementation(step90Request);
        }

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TENVIARDOCAUTORequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TENVIARDOCAUTORequest();
        step10Request.setSolicitudTENVIARDOCAUTOpthisSolicitudInstance(solicitudTNOTIFICARINCLUSIONpthisSolicitud);
        solicitudSrv.tENVIARDOCAUTOImplementation(step10Request);

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: TENVIARDOCAUTO

    /*
     * Entry point for service TENVIARDOCAUTO
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tENVIARDOCAUTO(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TENVIARDOCAUTORequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.tENVIARDOCAUTOTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TENVIARDOCAUTO
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tENVIARDOCAUTOTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TENVIARDOCAUTORequest request) throws ModelException {
        request.setSolicitudTENVIARDOCAUTOpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTENVIARDOCAUTOpthisSolicitud()));
        return solicitudSrv.tENVIARDOCAUTOImplementation(request);
    }

    /*
     * Implements service TENVIARDOCAUTO
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tENVIARDOCAUTOImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TENVIARDOCAUTORequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudTENVIARDOCAUTOpthisSolicitud = request.getSolicitudTENVIARDOCAUTOpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudTENVIARDOCAUTOpthisSolicitud;

        /* Action 1: IF (drvEnviarEmailDoc = true) */
        boolean ifCondition1 = solicitudTENVIARDOCAUTOpthisSolicitud.getSolicituddrvEnviarEmailDoc().equals(true);
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TENVIARDOCAUTOAUXRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TENVIARDOCAUTOAUXRequest();
            step10Request.setSolicitudTENVIARDOCAUTOAUXpthisSolicitudInstance(solicitudTENVIARDOCAUTOpthisSolicitud);
            solicitudSrv.tENVIARDOCAUTOAUXImplementation(step10Request);
        }

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: TMARCARCOMPLETADA

    /*
     * Entry point for service TMARCARCOMPLETADA
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADAResponse tMARCARCOMPLETADA(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) {
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADAResponse response = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADAResponse();
        try {
            response = solicitudSrv.tMARCARCOMPLETADATxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TMARCARCOMPLETADA
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADAResponse tMARCARCOMPLETADATxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) throws ModelException, SystemException {
        request.setSolicitudTMARCARCOMPLETADApthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTMARCARCOMPLETADApthisSolicitud()));
        return solicitudSrv.tMARCARCOMPLETADAImplementation(request);
    }

    /*
     * Implements service TMARCARCOMPLETADA
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADAResponse tMARCARCOMPLETADAImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) throws ModelException, SystemException {
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADAResponse response = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADAResponse();
        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();

        request.checkArguments();

        tMARCARCOMPLETADAPreconditions(request);
        Solicitud instance = solicitudTMARCARCOMPLETADApthisSolicitud;

        /* Action 1: IF (Aesolicitudes.PlanT.EsVoluntario = true AND FInicioContrato = NULL) */
        boolean ifCondition1 = solicitudTMARCARCOMPLETADApthisSolicitud.getAesolicitudes().getPlanT().getPlanesEsVoluntario().equals(true) && solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudFInicioContrato() == null;
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcFechaIniContPlanVolRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CalcFechaIniContPlanVolRequest();
            step10Request.setSolicitudcalcFechaIniContPlanVolpthisSolicitudInstance(solicitudTMARCARCOMPLETADApthisSolicitud);
            step10Request.setSolicitudcalcFechaIniContPlanVolpDiaInicio(repository.assocOperator224(solicitudTMARCARCOMPLETADApthisSolicitud));
            step10Request.setSolicitudcalcFechaIniContPlanVolpDiaFin(repository.assocOperator225(solicitudTMARCARCOMPLETADApthisSolicitud));
            step10Request.setSolicitudcalcFechaIniContPlanVolpFechaConfirmacion(STDFunctions.systemDateFun());
            step10Request.setSolicitudcalcFechaIniContPlanVolpFechaDeParametrizacion(userFunctionsSrv.fuGetFInicioPlanVolFun(solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudTramite(), solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudtipoContrato(), STDFunctions.systemDateFun()));
            solicitudSrv.calcFechaIniContPlanVolImplementation(step10Request);
        }

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyValidacionesFInicioContRequest step2Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyValidacionesFInicioContRequest();
        step2Request.setSolicituddummyValidacionesFInicioContpthisSolicitudInstance(solicitudTMARCARCOMPLETADApthisSolicitud);
        solicitudSrv.dummyValidacionesFInicioContImplementation(step2Request);

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest step3Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioRequest();
        step3Request.setSolicitudvalidarAtributosUsuariopthisSolicitudInstance(solicitudTMARCARCOMPLETADApthisSolicitud);
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.ValidarAtributosUsuarioResponse step3Response = solicitudSrv.validarAtributosUsuarioImplementation(step3Request);
        response.setSolicitudTMARCARCOMPLETADAptpsNumSolicitud(step3Response.getSolicitudvalidarAtributosUsuarioptpsNumSolicitud());

        for(Usuario step4Entity : repository.assocOperator222(solicitudTMARCARCOMPLETADApthisSolicitud)) {
            com.keralty.aegenerasolicitud.viewmodel.cuestionariop.siu.TCAMBIARPREGUNTA108Request step4Request = new com.keralty.aegenerasolicitud.viewmodel.cuestionariop.siu.TCAMBIARPREGUNTA108Request();
            step4Request.setCuestionarioPTCAMBIARPREGUNTA108pthisCuestionarioPInstance(step4Entity.getCuestionarioP());
            cuestionarioPSrv.tCAMBIARPREGUNTA108Implementation(step4Request);
        }

        /* Action 5: IF (Completada = false) */
        boolean ifCondition5 = solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudCompletada().equals(false);
        if (ifCondition5) {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CambiarEstadoRequest step50Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CambiarEstadoRequest();
            step50Request.setSolicitudcambiarEstadopthisSolicitudInstance(solicitudTMARCARCOMPLETADApthisSolicitud);
            step50Request.setSolicitudcambiarEstadopeEstado(Long.valueOf(-1));
            solicitudSrv.cambiarEstadoImplementation(step50Request);
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest step51Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest();
            step51Request.setSolicitudTCAMBIARESTADOpthisSolicitudInstance(solicitudTMARCARCOMPLETADApthisSolicitud);
            step51Request.setSolicitudTCAMBIARESTADOptpeEstado(Long.valueOf(1));
            step51Request.setSolicitudTCAMBIARESTADOptObservaciones("");
            step51Request.setSolicitudTCAMBIARESTADOptMotDevolucion("");
            solicitudSrv.tCAMBIARESTADOImplementation(step51Request);
        }

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarCompletadaRequest step6Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarCompletadaRequest();
        step6Request.setSolicitudmarcarCompletadapthisSolicitudInstance(solicitudTMARCARCOMPLETADApthisSolicitud);
        solicitudSrv.marcarCompletadaImplementation(step6Request);

        for(DocumentoG step7Entity : repository.assocOperator223(solicitudTMARCARCOMPLETADApthisSolicitud)) {
            com.keralty.aegenerasolicitud.viewmodel.documentog.siu.SetFechaSubidaRequest step7Request = new com.keralty.aegenerasolicitud.viewmodel.documentog.siu.SetFechaSubidaRequest();
            step7Request.setDocumentoGsetFechaSubidapthisDocumentoGInstance(step7Entity);
            step7Request.setDocumentoGsetFechaSubidapeFechaHora(solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudFechaCompletada());
            documentoGSrv.setFechaSubidaImplementation(step7Request);
        }

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    /*
     * Checks service TMARCARCOMPLETADA preconditions individually
     *
     * @param request Service request viewmodel
     *
     * @return Service preconditions evaluation result
     */
    public com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADAResponse tMARCARCOMPLETADACheckPreconditions(com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) {
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADAResponse response = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADAResponse();
        request.setSolicitudTMARCARCOMPLETADApthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTMARCARCOMPLETADApthisSolicitud()));
        try {
            if("Clas_1431957012480960Pre_87".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre87(request);
            if("Clas_1431957012480960Pre_88".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre88(request);
            if("Clas_1431957012480960Pre_89".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre89(request);
            if("Clas_1431957012480960Pre_91".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre91(request);
            if("Clas_1431957012480960Pre_92".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre92(request);
            if("Clas_1431957012480960Pre_93".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre93(request);
            if("Clas_1431957012480960Pre_94".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre94(request);
            if("Clas_1431957012480960Pre_95".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre95(request);
            if("Clas_1431957012480960Pre_96".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre96(request);
            if("Clas_1431957012480960Pre_97".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre97(request);
            if("Clas_1431957012480960Pre_114".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre114(request);
            if("Clas_1431957012480960Pre_124".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre124(request);
            if("Clas_1431957012480960Pre_125".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre125(request);
            if("Clas_1431957012480960Pre_127".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre127(request);
            if("Clas_1431957012480960Pre_128".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre128(request);
            if("Clas_1431957012480960Pre_131".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre131(request);
            if("Clas_1431957012480960Pre_132".equals(request.getPreconditionId()))
                checkClas1431957012480960Pre132(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    private void tMARCARCOMPLETADAPreconditions(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        checkClas1431957012480960Pre87(request);
        checkClas1431957012480960Pre88(request);
        checkClas1431957012480960Pre89(request);
        checkClas1431957012480960Pre91(request);
        checkClas1431957012480960Pre92(request);
        checkClas1431957012480960Pre93(request);
        checkClas1431957012480960Pre94(request);
        checkClas1431957012480960Pre95(request);
        checkClas1431957012480960Pre96(request);
        checkClas1431957012480960Pre97(request);
        checkClas1431957012480960Pre114(request);
        checkClas1431957012480960Pre124(request);
        checkClas1431957012480960Pre125(request);
        checkClas1431957012480960Pre127(request);
        checkClas1431957012480960Pre128(request);
        checkClas1431957012480960Pre131(request);
        checkClas1431957012480960Pre132(request);
    }

    private void checkClas1431957012480960Pre87 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // NumContrato = NULL

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        boolean preconditionHolds = solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudNumContrato() == null;
        if (!preconditionHolds) throw new PreconditionException("La solicitud ya ha sido generada");
    }

    private void checkClas1431957012480960Pre88 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // datosPagoOK = TRUE

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        boolean preconditionHolds = solicitudTMARCARCOMPLETADApthisSolicitud.getSolicituddatosPagoOK().equals(true);
        if (!preconditionHolds) throw new PreconditionException("Debe completar los datos de la Solicitud. Indique datos de pago");
    }

    private void checkClas1431957012480960Pre89 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // drvYaTieneContratante = TRUE

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        boolean preconditionHolds = solicitudTMARCARCOMPLETADApthisSolicitud.getSolicituddrvYaTieneContratante().equals(true);
        if (!preconditionHolds) throw new PreconditionException("Debe completar los datos de la Solicitud. Indique contratante");
    }

    private void checkClas1431957012480960Pre91 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // EsTraslado = FALSE OR fugetValorParametroConf("TARIFATRASLADO") <> NULL
        // 

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        boolean preconditionHolds = solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudEsTraslado().equals(false) || userFunctionsSrv.fugetValorParametroConfFun("TARIFATRASLADO") != null;
        if (!preconditionHolds) throw new PreconditionException("No se ha encontrado el código de tarifa de traslado en la parametrización");
    }

    private void checkClas1431957012480960Pre92 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // fugetValorParametroConf("DOCUMENTOSOBLIGATORIOS") = "0" OR FOR ALL Usuarios ( Usuarios.drvTieneDocumentos = true )

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        try {
            boolean preconditionHolds = (userFunctionsSrv.fugetValorParametroConfFun("DOCUMENTOSOBLIGATORIOS")).equals("0") || repository.assocOperator089(solicitudTMARCARCOMPLETADApthisSolicitud);
            if (!preconditionHolds) throw new PreconditionException("Verifique que todos los usuarios de la solicitud tienen los documentos correspondientes" + userFunctionsSrv.fuformarMsgErrorUsrSinDocsFun(solicitudTMARCARCOMPLETADApthisSolicitud));
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre93 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // FOR ALL Usuarios ( Usuarios.drvTieneParentesco = true )

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        try {
            boolean preconditionHolds = repository.assocOperator090(solicitudTMARCARCOMPLETADApthisSolicitud);
            if (!preconditionHolds) throw new PreconditionException("Verifique que todos los usuarios de la solicitud tienen indicado el parentesco");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre94 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // (tipoContrato = 1 AND ContratantePersona.TipoPersona = "J" AND (COUNT( Usuarios ) WHERE (Usuarios.Parentesco.EsTitular = true) + COUNT( UsuariosRegistrados ) WHERE (UsuariosRegistrados.Parentesco.EsTitular = true)) = 1) OR (tipoContrato <> 1 OR ContratantePersona.TipoPersona <> "J" OR Tramite = 2)

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        try {
            boolean preconditionHolds = ((solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudtipoContrato()).equals(Long.valueOf(1)) && (solicitudTMARCARCOMPLETADApthisSolicitud.getContratantePersona().getPersonaTipoPersona()).equals("J") && Long.valueOf((repository.assocOperator091(solicitudTMARCARCOMPLETADApthisSolicitud) + repository.assocOperator092(solicitudTMARCARCOMPLETADApthisSolicitud))).equals(Long.valueOf(1))) || (!(solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudtipoContrato()).equals(Long.valueOf(1)) || !(solicitudTMARCARCOMPLETADApthisSolicitud.getContratantePersona().getPersonaTipoPersona()).equals("J") || (solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(2)));
            if (!preconditionHolds) throw new PreconditionException("Si el contrato es de tipo familiar y el contratante es persona jurídica, debe indicar un titular");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre95 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // (tipoContrato = 1 AND ContratantePersona.TipoPersona = "N" AND drvContratanteEsTitular = TRUE) OR (tipoContrato <> 1 OR ContratantePersona.TipoPersona <> "N" OR Tramite = 2)

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        boolean preconditionHolds = ((solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudtipoContrato()).equals(Long.valueOf(1)) && (solicitudTMARCARCOMPLETADApthisSolicitud.getContratantePersona().getPersonaTipoPersona()).equals("N") && solicitudTMARCARCOMPLETADApthisSolicitud.getSolicituddrvContratanteEsTitular().equals(true)) || (!(solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudtipoContrato()).equals(Long.valueOf(1)) || !(solicitudTMARCARCOMPLETADApthisSolicitud.getContratantePersona().getPersonaTipoPersona()).equals("N") || (solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(2)));
        if (!preconditionHolds) throw new PreconditionException("Si el contrato es de tipo familiar y el contratante es persona natural, el contratante debe coincidir con el titular");
    }

    private void checkClas1431957012480960Pre96 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // fugetValorParametroConf( "DOCUMENTOSOBLIGATORIOS" ) = "0" OR 
        // 
        // EXIST(ContratantePersona) = FALSE OR ContratantePersona.drvVieneDeCORE = TRUE OR EXIST(ContratantePersona.DocumentosG) WHERE (ContratantePersona.DocumentosG.Tipo = 1) = TRUE

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        try {
            boolean preconditionHolds = (userFunctionsSrv.fugetValorParametroConfFun("DOCUMENTOSOBLIGATORIOS")).equals("0") || repository.assocOperator093(solicitudTMARCARCOMPLETADApthisSolicitud).equals(false) || solicitudTMARCARCOMPLETADApthisSolicitud.getContratantePersona().getPersonadrvVieneDeCORE().equals(true) || repository.assocOperator094(solicitudTMARCARCOMPLETADApthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("Verifique que el contratante tiene documento de identificación");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre97 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // fugetValorParametroConf( "DOCUMENTOSOBLIGATORIOS" ) = "0" OR 
        // EXIST(TitularPersona) = FALSE OR TitularPersona.drvVieneDeCORE = TRUE OR EXIST(TitularPersona.DocumentosG) WHERE (TitularPersona.DocumentosG.Tipo = 1) = TRUE

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        try {
            boolean preconditionHolds = (userFunctionsSrv.fugetValorParametroConfFun("DOCUMENTOSOBLIGATORIOS")).equals("0") || repository.assocOperator095(solicitudTMARCARCOMPLETADApthisSolicitud).equals(false) || solicitudTMARCARCOMPLETADApthisSolicitud.getTitularPersona().getPersonadrvVieneDeCORE().equals(true) || repository.assocOperator096(solicitudTMARCARCOMPLETADApthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("Verifique que el titular tiene documento de identificación");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre114 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // fugetValorParametroConf( "DOCUMENTOSOBLIGATORIOS" ) = "0" 
        // OR 
        // COUNT(DocumentosGestor) WHERE (DocumentosGestor.Tipo = 2) > 0 
        // OR
        // ( Tramite = 2 AND NumContratoInclusion <> NULL AND fuContratoExentoDocSol( NumContratoInclusion ) = TRUE )

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        try {
            boolean preconditionHolds = (userFunctionsSrv.fugetValorParametroConfFun("DOCUMENTOSOBLIGATORIOS")).equals("0") || repository.assocOperator097(solicitudTMARCARCOMPLETADApthisSolicitud) > Long.valueOf(0) || ((solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(2)) && solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudNumContratoInclusion() != null && userFunctionsSrv.fuContratoExentoDocSolFun(solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudNumContratoInclusion()).equals(true));
            if (!preconditionHolds) throw new PreconditionException("Se debe anexar documento de solicitud para al menos un usuario de la solicitud");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre124 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // fugetValorParametroConf("DOCUMENTOSOBLIGATORIOS") = "0" OR Tramite = 1 OR TipoContrato <> 3 OR NumFamilia <> NULL OR EXIST( Titular ) = false OR Titular.TipoIdentificacion.NIT = true OR Titular.TitularConBeneficios = true OR EXIST( TitularPersona ) = false OR EXIST( TitularPersona.DocumentosG ) WHERE (TitularPersona.DocumentosG.Tipo = 1) = true

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        try {
            boolean preconditionHolds = (userFunctionsSrv.fugetValorParametroConfFun("DOCUMENTOSOBLIGATORIOS")).equals("0") || (solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(1)) || !(solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudtipoContrato()).equals(Long.valueOf(3)) || solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudNumFamilia() != null || repository.assocOperator098(solicitudTMARCARCOMPLETADApthisSolicitud).equals(false) || solicitudTMARCARCOMPLETADApthisSolicitud.getTitular().getTipoIdentificacion().getTipoIdentificacionNIT().equals(true) || solicitudTMARCARCOMPLETADApthisSolicitud.getTitular().getTitularTitularConBeneficios().equals(true) || repository.assocOperator099(solicitudTMARCARCOMPLETADApthisSolicitud).equals(false) || repository.assocOperator100(solicitudTMARCARCOMPLETADApthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("Debe adjuntar el documento de identificación del titular");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre125 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // fugetValorParametroConf("DOCUMENTOSOBLIGATORIOS") = "0" OR Tramite = 1 OR TipoContrato <> 1 OR EXIST( Titular ) = false OR Titular.TipoIdentificacion.NIT = true OR Titular.TitularConBeneficios = true OR EXIST( TitularPersona ) = false OR EXIST( TitularPersona.DocumentosG ) WHERE (TitularPersona.DocumentosG.Tipo = 1) = true

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        try {
            boolean preconditionHolds = (userFunctionsSrv.fugetValorParametroConfFun("DOCUMENTOSOBLIGATORIOS")).equals("0") || (solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(1)) || !(solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudtipoContrato()).equals(Long.valueOf(1)) || repository.assocOperator101(solicitudTMARCARCOMPLETADApthisSolicitud).equals(false) || solicitudTMARCARCOMPLETADApthisSolicitud.getTitular().getTipoIdentificacion().getTipoIdentificacionNIT().equals(true) || solicitudTMARCARCOMPLETADApthisSolicitud.getTitular().getTitularTitularConBeneficios().equals(true) || repository.assocOperator102(solicitudTMARCARCOMPLETADApthisSolicitud).equals(false) || repository.assocOperator103(solicitudTMARCARCOMPLETADApthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("Debe adjuntar el documento de identificación del titular");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre127 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // fugetValorParametroConf("DOCUMENTOSOBLIGATORIOS") = "0" OR Tramite = 1 OR TipoContrato <> 1 OR ContratantePersona.TipoIdentificacion.NIT = false OR EXIST( ContratantePersona ) = false OR EXIST( ContratantePersona.DocumentosG ) WHERE (ContratantePersona.DocumentosG.Tipo = 1) = true

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        try {
            boolean preconditionHolds = (userFunctionsSrv.fugetValorParametroConfFun("DOCUMENTOSOBLIGATORIOS")).equals("0") || (solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(1)) || !(solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudtipoContrato()).equals(Long.valueOf(1)) || solicitudTMARCARCOMPLETADApthisSolicitud.getContratantePersona().getTipoIdentificacion().getTipoIdentificacionNIT().equals(false) || repository.assocOperator104(solicitudTMARCARCOMPLETADApthisSolicitud).equals(false) || repository.assocOperator105(solicitudTMARCARCOMPLETADApthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("Debe aduntar el documento documento de identificación (Certificado de cámara y comercio no mayor a 60 días de expedido)");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre128 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // fugetValorParametroConf( "DOCUMENTOSOBLIGATORIOS" ) = "0" OR 
        // Tramite = 1 OR 
        // TipoContrato <> 3 OR 
        // NumFamilia = NULL OR 
        // EXIST(Titular) = FALSE OR
        // Titular.TitularConBeneficiosCORE = TRUE OR
        // Titular.TitularConBeneficios = FALSE OR
        // EXIST(TitularPersona.DocumentosG) WHERE (TitularPersona.DocumentosG.Tipo = 1) =  TRUE

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        try {
            boolean preconditionHolds = (userFunctionsSrv.fugetValorParametroConfFun("DOCUMENTOSOBLIGATORIOS")).equals("0") || (solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(1)) || !(solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudtipoContrato()).equals(Long.valueOf(3)) || solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudNumFamilia() == null || repository.assocOperator106(solicitudTMARCARCOMPLETADApthisSolicitud).equals(false) || solicitudTMARCARCOMPLETADApthisSolicitud.getTitular().getTitularTitularConBeneficiosCORE().equals(true) || solicitudTMARCARCOMPLETADApthisSolicitud.getTitular().getTitularTitularConBeneficios().equals(false) || repository.assocOperator107(solicitudTMARCARCOMPLETADApthisSolicitud).equals(true);
            if (!preconditionHolds) throw new PreconditionException("Verifique que el titular tiene documento de identificación");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre131 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // Tramite = 2 OR EXIST(TipoFactura) = FALSE  OR 
        // TipoFactura.EsCelular = FALSE OR 
        // (EXIST( TitularPersona ) = TRUE AND TitularPersona.TelefonoCelular <> NULL)

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        try {
            boolean preconditionHolds = (solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(2)) || repository.assocOperator108(solicitudTMARCARCOMPLETADApthisSolicitud).equals(false) || solicitudTMARCARCOMPLETADApthisSolicitud.getTipoFactura().getTipoFacturaEsCelular().equals(false) || (repository.assocOperator109(solicitudTMARCARCOMPLETADApthisSolicitud).equals(true) && solicitudTMARCARCOMPLETADApthisSolicitud.getTitularPersona().getPersonaTelefonoCelular() != null);
            if (!preconditionHolds) throw new PreconditionException("Debe diligenciar el teléfono celular del titular para poder seleccionar el tipo de factura 'Teléfono móvil'");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    private void checkClas1431957012480960Pre132 (
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest request) 
            throws ModelException {
        // Tramite = 2 OR EXIST(TipoFactura) = FALSE  OR 
        // TipoFactura.EsEmail = FALSE OR 
        // (EXIST(TitularPersona) = TRUE AND TitularPersona.CorreoElectronico <> NULL)

        Solicitud solicitudTMARCARCOMPLETADApthisSolicitud = request.getSolicitudTMARCARCOMPLETADApthisSolicitudInstance();
        try {
            boolean preconditionHolds = (solicitudTMARCARCOMPLETADApthisSolicitud.getSolicitudTramite()).equals(Long.valueOf(2)) || repository.assocOperator110(solicitudTMARCARCOMPLETADApthisSolicitud).equals(false) || solicitudTMARCARCOMPLETADApthisSolicitud.getTipoFactura().getTipoFacturaEsEmail().equals(false) || (repository.assocOperator111(solicitudTMARCARCOMPLETADApthisSolicitud).equals(true) && solicitudTMARCARCOMPLETADApthisSolicitud.getTitularPersona().getPersonaCorreoElectronico() != null);
            if (!preconditionHolds) throw new PreconditionException("Debe diligenciar el correo electrónico del titular para poder seleccionar el tipo de factura 'Correo electrónico'");
        } catch (SystemException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    // Service: TCAMBIARESTADO

    /*
     * Entry point for service TCAMBIARESTADO
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tCAMBIARESTADO(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.tCAMBIARESTADOTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TCAMBIARESTADO
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tCAMBIARESTADOTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest request) throws ModelException, SystemException {
        request.setSolicitudTCAMBIARESTADOpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTCAMBIARESTADOpthisSolicitud()));
        return solicitudSrv.tCAMBIARESTADOImplementation(request);
    }

    /*
     * Implements service TCAMBIARESTADO
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tCAMBIARESTADOImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        Long solicitudTCAMBIARESTADOptpeEstado = request.getSolicitudTCAMBIARESTADOptpeEstado();
        String solicitudTCAMBIARESTADOptObservaciones = request.getSolicitudTCAMBIARESTADOptObservaciones();
        String solicitudTCAMBIARESTADOptMotDevolucion = request.getSolicitudTCAMBIARESTADOptMotDevolucion();
        Solicitud solicitudTCAMBIARESTADOpthisSolicitud = request.getSolicitudTCAMBIARESTADOpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudTCAMBIARESTADOpthisSolicitud;

        /* Action 1: IF (Estado <> ptpeEstado) */
        boolean ifCondition1 = !(solicitudTCAMBIARESTADOpthisSolicitud.getSolicitudEstado()).equals(solicitudTCAMBIARESTADOptpeEstado);
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFUGRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFUGRequest();
            step10Request.setSolicitudasignarFUGpthisSolicitudInstance(solicitudTCAMBIARESTADOpthisSolicitud);
            solicitudSrv.asignarFUGImplementation(step10Request);
            /* Action 11: IF (ptpeEstado = 5) */
            boolean ifCondition11 = (solicitudTCAMBIARESTADOptpeEstado).equals(Long.valueOf(5));
            if (ifCondition11) {
                com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFAAMRequest step110Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFAAMRequest();
                step110Request.setSolicitudasignarFAAMpthisSolicitudInstance(solicitudTCAMBIARESTADOpthisSolicitud);
                solicitudSrv.asignarFAAMImplementation(step110Request);
            }
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CambiarEstadoRequest step12Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.CambiarEstadoRequest();
            step12Request.setSolicitudcambiarEstadopthisSolicitudInstance(solicitudTCAMBIARESTADOpthisSolicitud);
            step12Request.setSolicitudcambiarEstadopeEstado(solicitudTCAMBIARESTADOptpeEstado);
            solicitudSrv.cambiarEstadoImplementation(step12Request);
            /* Action 13: IF (ptpeEstado <> 8 AND Agente.Email <> NULL) */
            boolean ifCondition13 = !(solicitudTCAMBIARESTADOptpeEstado).equals(Long.valueOf(8)) && solicitudTCAMBIARESTADOpthisSolicitud.getAgente().getFuncionarioEmail() != null;
            if (ifCondition13) {
                com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.EnviarEmailRequest step130Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.EnviarEmailRequest();
                step130Request.setSolicitudenviarEmailpthisSolicitudInstance(solicitudTCAMBIARESTADOpthisSolicitud);
                step130Request.setSolicitudenviarEmailpeDestinatario(solicitudTCAMBIARESTADOpthisSolicitud.getAgente().getFuncionarioEmail());
                step130Request.setSolicitudenviarEmailpeEmailConfirmacion("");
                step130Request.setSolicitudenviarEmailpeAsunto("Cambio de estado solicitud en Afiliación Electrónica. " + solicitudTCAMBIARESTADOpthisSolicitud.getAesolicitudes().getAesolicitudesNumerosolicitud() + ". " + userFunctionsSrv.fuEstadoSolicitudFun(solicitudTCAMBIARESTADOpthisSolicitud.getSolicitudEstado()));
                step130Request.setSolicitudenviarEmailpeCuerpo("El día " + STDFunctions.intToStrFun(STDFunctions.getDayFun(STDFunctions.systemDateFun())) + "/" + STDFunctions.intToStrFun(STDFunctions.getMonthFun(STDFunctions.systemDateFun())) + "/" + STDFunctions.intToStrFun(STDFunctions.getYearFun(STDFunctions.systemDateFun())) + " la solicitud de número " + solicitudTCAMBIARESTADOpthisSolicitud.getAesolicitudes().getAesolicitudesNumerosolicitud() + "  ha cambiado de estado. Su nuevo estado es " + userFunctionsSrv.fuEstadoSolicitudFun(solicitudTCAMBIARESTADOpthisSolicitud.getSolicitudEstado()));
                step130Request.setSolicitudenviarEmailpeNombreAdjunto(null);
                step130Request.setSolicitudenviarEmailpeAdjunto(null);
                solicitudSrv.enviarEmailImplementation(step130Request);
                
            }
            com.keralty.aegenerasolicitud.viewmodel.logestado.siu.TCREARRequest step14Request = new com.keralty.aegenerasolicitud.viewmodel.logestado.siu.TCREARRequest();
            step14Request.setLogEstadoTCREARptpagrSolicitudInstance(solicitudTCAMBIARESTADOpthisSolicitud);
            step14Request.setLogEstadoTCREARptpatrObservacion(solicitudTCAMBIARESTADOptObservaciones);
            step14Request.setLogEstadoTCREARptpatrMotDevolucion(solicitudTCAMBIARESTADOptMotDevolucion);
            step14Request.setLogEstadoTCREARptpatrEstado(solicitudTCAMBIARESTADOpthisSolicitud.getSolicitudEstado());
            step14Request.setLogEstadoTCREARptpatrEstadoDoc(solicitudTCAMBIARESTADOpthisSolicitud.getSolicitudEstadoDoc());
            logEstadoSrv.tCREARImplementation(step14Request);
            /* Action 15: IF (EXIST (UsrAfiliacion) = false AND (Estado = 0 OR Estado = 1 OR Estado = 2 OR Estado = 3 OR Estado = 5)) */
            boolean ifCondition15 = repository.assocOperator228(solicitudTCAMBIARESTADOpthisSolicitud).equals(false) && ((solicitudTCAMBIARESTADOpthisSolicitud.getSolicitudEstado()).equals(Long.valueOf(0)) || (solicitudTCAMBIARESTADOpthisSolicitud.getSolicitudEstado()).equals(Long.valueOf(1)) || (solicitudTCAMBIARESTADOpthisSolicitud.getSolicitudEstado()).equals(Long.valueOf(2)) || (solicitudTCAMBIARESTADOpthisSolicitud.getSolicitudEstado()).equals(Long.valueOf(3)) || (solicitudTCAMBIARESTADOpthisSolicitud.getSolicitudEstado()).equals(Long.valueOf(5)));
            if (ifCondition15) {
                com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGAUTOUSRAFILIRequest step150Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGAUTOUSRAFILIRequest();
                step150Request.setSolicitudTASGAUTOUSRAFILIpthisSolicitudInstance(solicitudTCAMBIARESTADOpthisSolicitud);
                step150Request.setSolicitudTASGAUTOUSRAFILIptFuncionarioInstance(userFunctionsSrv.fugetFuncionarioPorIdFun(userFunctionsSrv.fuCallSPgetusugrabadorFun(solicitudTCAMBIARESTADOpthisSolicitud.getAesolicitudes().getAesolicitudesidsolicitud())));
                solicitudSrv.tASGAUTOUSRAFILIImplementation(step150Request);
            }
            /* Action 16: IF (EXIST (UsrAreaMedica) = false AND Estado = 5) */
            boolean ifCondition16 = repository.assocOperator229(solicitudTCAMBIARESTADOpthisSolicitud).equals(false) && (solicitudTCAMBIARESTADOpthisSolicitud.getSolicitudEstado()).equals(Long.valueOf(5));
            if (ifCondition16) {
                com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGAUTOUSRMEDICORequest step160Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGAUTOUSRMEDICORequest();
                step160Request.setSolicitudTASGAUTOUSRMEDICOpthisSolicitudInstance(solicitudTCAMBIARESTADOpthisSolicitud);
                step160Request.setSolicitudTASGAUTOUSRMEDICOptAreaMedicaInstance(userFunctionsSrv.fugetAreaMedicaPorIdFun(userFunctionsSrv.fuCallSPgetusuareamedFun(solicitudTCAMBIARESTADOpthisSolicitud.getAesolicitudes().getAesolicitudesidsolicitud())));
                solicitudSrv.tASGAUTOUSRMEDICOImplementation(step160Request);
            }
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARTERMINADARequest step17Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARTERMINADARequest();
            step17Request.setSolicitudTMARCARTERMINADApthisSolicitudInstance(solicitudTCAMBIARESTADOpthisSolicitud);
            solicitudSrv.tMARCARTERMINADAImplementation(step17Request);
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarPrioridadRequest step18Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarPrioridadRequest();
            step18Request.setSolicitudasignarPrioridadpthisSolicitudInstance(solicitudTCAMBIARESTADOpthisSolicitud);
            step18Request.setSolicitudasignarPrioridadpePrioridad(userFunctionsSrv.fugetPrioridadFun(solicitudTCAMBIARESTADOpthisSolicitud.getSolicitudEstado(), solicitudTCAMBIARESTADOpthisSolicitud.getSolicitudEstadoDoc()));
            solicitudSrv.asignarPrioridadImplementation(step18Request);
        }

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: TASGUSRAFILI

    /*
     * Entry point for service TASGUSRAFILI
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tASGUSRAFILI(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGUSRAFILIRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.tASGUSRAFILITxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TASGUSRAFILI
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tASGUSRAFILITxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGUSRAFILIRequest request) throws ModelException {
        request.setSolicitudTASGUSRAFILIpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTASGUSRAFILIpthisSolicitud()));
        request.setSolicitudTASGUSRAFILIptAfiliacionInstance(funcionarioSrv.getByOIDWithHV(request.getSolicitudTASGUSRAFILIptAfiliacion()));
        return solicitudSrv.tASGUSRAFILIImplementation(request);
    }

    /*
     * Implements service TASGUSRAFILI
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tASGUSRAFILIImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGUSRAFILIRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Funcionario solicitudTASGUSRAFILIptAfiliacion = request.getSolicitudTASGUSRAFILIptAfiliacionInstance();
        Solicitud solicitudTASGUSRAFILIpthisSolicitud = request.getSolicitudTASGUSRAFILIpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudTASGUSRAFILIpthisSolicitud;

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsUsrAfiliacionRequest step1Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsUsrAfiliacionRequest();
        step1Request.setSolicitudInsUsrAfiliacionpthisSolicitudInstance(solicitudTASGUSRAFILIpthisSolicitud);
        step1Request.setSolicitudInsUsrAfiliacionpevcFuncionarioInstance(solicitudTASGUSRAFILIptAfiliacion);
        solicitudSrv.insUsrAfiliacionImplementation(step1Request, true);

        /* Action 2: IF (FechaTerminada = NULL) */
        boolean ifCondition2 = solicitudTASGUSRAFILIpthisSolicitud.getSolicitudFechaTerminada() == null;
        if (ifCondition2) {
            com.keralty.aegenerasolicitud.viewmodel.funcionario.siu.SolicitudAsignadaRequest step20Request = new com.keralty.aegenerasolicitud.viewmodel.funcionario.siu.SolicitudAsignadaRequest();
            step20Request.setFuncionariosolicitudAsignadapthisFuncionarioInstance(solicitudTASGUSRAFILIpthisSolicitud.getUsrAfiliacion());
            funcionarioSrv.solicitudAsignadaImplementation(step20Request);
        }

        com.keralty.aegenerasolicitud.viewmodel.logasignaciones.siu.TCREARRequest step3Request = new com.keralty.aegenerasolicitud.viewmodel.logasignaciones.siu.TCREARRequest();
        step3Request.setLogAsignacionesTCREARptpagrSolicitudInstance(solicitudTASGUSRAFILIpthisSolicitud);
        step3Request.setLogAsignacionesTCREARptpatrTipo("Asignación Afiliaciones");
        step3Request.setLogAsignacionesTCREARptpatrUsuario(solicitudTASGUSRAFILIptAfiliacion.getFuncionarioUsuarioLDAP());
        logAsignacionesSrv.tCREARImplementation(step3Request);

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: TASGUSRARME

    /*
     * Entry point for service TASGUSRARME
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tASGUSRARME(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGUSRARMERequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.tASGUSRARMETxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TASGUSRARME
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tASGUSRARMETxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGUSRARMERequest request) throws ModelException {
        request.setSolicitudTASGUSRARMEpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTASGUSRARMEpthisSolicitud()));
        request.setSolicitudTASGUSRARMEptAreaMedicaInstance(areaMedicaSrv.getByOIDWithHV(request.getSolicitudTASGUSRARMEptAreaMedica()));
        return solicitudSrv.tASGUSRARMEImplementation(request);
    }

    /*
     * Implements service TASGUSRARME
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tASGUSRARMEImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGUSRARMERequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        AreaMedica solicitudTASGUSRARMEptAreaMedica = request.getSolicitudTASGUSRARMEptAreaMedicaInstance();
        Solicitud solicitudTASGUSRARMEpthisSolicitud = request.getSolicitudTASGUSRARMEpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudTASGUSRARMEpthisSolicitud;

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsUsrAreaMedicaRequest step1Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.InsUsrAreaMedicaRequest();
        step1Request.setSolicitudInsUsrAreaMedicapthisSolicitudInstance(solicitudTASGUSRARMEpthisSolicitud);
        step1Request.setSolicitudInsUsrAreaMedicapevcAreaMedicaInstance(solicitudTASGUSRARMEptAreaMedica);
        solicitudSrv.insUsrAreaMedicaImplementation(step1Request, true);

        /* Action 2: IF (FechaTerminada = NULL) */
        boolean ifCondition2 = solicitudTASGUSRARMEpthisSolicitud.getSolicitudFechaTerminada() == null;
        if (ifCondition2) {
            com.keralty.aegenerasolicitud.viewmodel.funcionario.siu.SolicitudAsignadaRequest step20Request = new com.keralty.aegenerasolicitud.viewmodel.funcionario.siu.SolicitudAsignadaRequest();
            step20Request.setFuncionariosolicitudAsignadapthisFuncionarioInstance(solicitudTASGUSRARMEpthisSolicitud.getUsrAreaMedica().getFuncionarioFacet());
            funcionarioSrv.solicitudAsignadaImplementation(step20Request);
        }

        com.keralty.aegenerasolicitud.viewmodel.logasignaciones.siu.TCREARRequest step3Request = new com.keralty.aegenerasolicitud.viewmodel.logasignaciones.siu.TCREARRequest();
        step3Request.setLogAsignacionesTCREARptpagrSolicitudInstance(solicitudTASGUSRARMEpthisSolicitud);
        step3Request.setLogAsignacionesTCREARptpatrTipo("Asignación Area Medica");
        step3Request.setLogAsignacionesTCREARptpatrUsuario(solicitudTASGUSRARMEptAreaMedica.getFuncionarioUsuarioLDAP());
        logAsignacionesSrv.tCREARImplementation(step3Request);

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: TPOSTGENERAREXITO

    /*
     * Entry point for service TPOSTGENERAREXITO
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tPOSTGENERAREXITO(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TPOSTGENERAREXITORequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.tPOSTGENERAREXITOTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TPOSTGENERAREXITO
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tPOSTGENERAREXITOTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TPOSTGENERAREXITORequest request) throws ModelException, SystemException {
        request.setSolicitudTPOSTGENERAREXITOpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTPOSTGENERAREXITOpthisSolicitud()));
        return solicitudSrv.tPOSTGENERAREXITOImplementation(request);
    }

    /*
     * Implements service TPOSTGENERAREXITO
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tPOSTGENERAREXITOImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TPOSTGENERAREXITORequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudTPOSTGENERAREXITOpthisSolicitud = request.getSolicitudTPOSTGENERAREXITOpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudTPOSTGENERAREXITOpthisSolicitud;

        com.keralty.aegenerasolicitud.viewmodel.aesolicitudes.siu.ModificarFechaElabRequest step1Request = new com.keralty.aegenerasolicitud.viewmodel.aesolicitudes.siu.ModificarFechaElabRequest();
        step1Request.setAesolicitudesmodificarFechaElabpthisAESOLICITUDESInstance(solicitudTPOSTGENERAREXITOpthisSolicitud.getAesolicitudes());
        step1Request.setAesolicitudesmodificarFechaElabpeFecha(STDFunctions.systemDateFun());
        aesolicitudesSrv.modificarFechaElabImplementation(step1Request);

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest step2Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest();
        step2Request.setSolicitudTCAMBIARESTADOpthisSolicitudInstance(solicitudTPOSTGENERAREXITOpthisSolicitud);
        step2Request.setSolicitudTCAMBIARESTADOptpeEstado(Long.valueOf(8));
        step2Request.setSolicitudTCAMBIARESTADOptObservaciones(null);
        step2Request.setSolicitudTCAMBIARESTADOptMotDevolucion(null);
        solicitudSrv.tCAMBIARESTADOImplementation(step2Request);

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: TASGAUTOUSRAFILI

    /*
     * Entry point for service TASGAUTOUSRAFILI
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tASGAUTOUSRAFILI(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGAUTOUSRAFILIRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.tASGAUTOUSRAFILITxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TASGAUTOUSRAFILI
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tASGAUTOUSRAFILITxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGAUTOUSRAFILIRequest request) throws ModelException {
        request.setSolicitudTASGAUTOUSRAFILIpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTASGAUTOUSRAFILIpthisSolicitud()));
        request.setSolicitudTASGAUTOUSRAFILIptFuncionarioInstance(funcionarioSrv.getByOIDWithHV(request.getSolicitudTASGAUTOUSRAFILIptFuncionario()));
        return solicitudSrv.tASGAUTOUSRAFILIImplementation(request);
    }

    /*
     * Implements service TASGAUTOUSRAFILI
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tASGAUTOUSRAFILIImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGAUTOUSRAFILIRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Funcionario solicitudTASGAUTOUSRAFILIptFuncionario = request.getSolicitudTASGAUTOUSRAFILIptFuncionarioInstance();
        Solicitud solicitudTASGAUTOUSRAFILIpthisSolicitud = request.getSolicitudTASGAUTOUSRAFILIpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudTASGAUTOUSRAFILIpthisSolicitud;

        /* Action 1: IF (ptFuncionario <> NULL) */
        boolean ifCondition1 = (solicitudTASGAUTOUSRAFILIptFuncionario != null && !solicitudTASGAUTOUSRAFILIptFuncionario.isNull());
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGUSRAFILIRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGUSRAFILIRequest();
            step10Request.setSolicitudTASGUSRAFILIpthisSolicitudInstance(solicitudTASGAUTOUSRAFILIpthisSolicitud);
            step10Request.setSolicitudTASGUSRAFILIptAfiliacionInstance(solicitudTASGAUTOUSRAFILIptFuncionario);
            solicitudSrv.tASGUSRAFILIImplementation(step10Request);
        }

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: TASGAUTOUSRMEDICO

    /*
     * Entry point for service TASGAUTOUSRMEDICO
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tASGAUTOUSRMEDICO(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGAUTOUSRMEDICORequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.tASGAUTOUSRMEDICOTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TASGAUTOUSRMEDICO
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tASGAUTOUSRMEDICOTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGAUTOUSRMEDICORequest request) throws ModelException {
        request.setSolicitudTASGAUTOUSRMEDICOpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTASGAUTOUSRMEDICOpthisSolicitud()));
        request.setSolicitudTASGAUTOUSRMEDICOptAreaMedicaInstance(areaMedicaSrv.getByOIDWithHV(request.getSolicitudTASGAUTOUSRMEDICOptAreaMedica()));
        return solicitudSrv.tASGAUTOUSRMEDICOImplementation(request);
    }

    /*
     * Implements service TASGAUTOUSRMEDICO
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tASGAUTOUSRMEDICOImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGAUTOUSRMEDICORequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        AreaMedica solicitudTASGAUTOUSRMEDICOptAreaMedica = request.getSolicitudTASGAUTOUSRMEDICOptAreaMedicaInstance();
        Solicitud solicitudTASGAUTOUSRMEDICOpthisSolicitud = request.getSolicitudTASGAUTOUSRMEDICOpthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudTASGAUTOUSRMEDICOpthisSolicitud;

        /* Action 1: IF (ptAreaMedica <> NULL) */
        boolean ifCondition1 = (solicitudTASGAUTOUSRMEDICOptAreaMedica != null && !solicitudTASGAUTOUSRMEDICOptAreaMedica.isNull());
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGUSRARMERequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TASGUSRARMERequest();
            step10Request.setSolicitudTASGUSRARMEpthisSolicitudInstance(solicitudTASGAUTOUSRMEDICOpthisSolicitud);
            step10Request.setSolicitudTASGUSRARMEptAreaMedicaInstance(solicitudTASGAUTOUSRMEDICOptAreaMedica);
            solicitudSrv.tASGUSRARMEImplementation(step10Request);
        }

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: TMARCARTERMINADA

    /*
     * Entry point for service TMARCARTERMINADA
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tMARCARTERMINADA(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARTERMINADARequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.tMARCARTERMINADATxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TMARCARTERMINADA
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tMARCARTERMINADATxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARTERMINADARequest request) throws ModelException, SystemException {
        request.setSolicitudTMARCARTERMINADApthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTMARCARTERMINADApthisSolicitud()));
        return solicitudSrv.tMARCARTERMINADAImplementation(request);
    }

    /*
     * Implements service TMARCARTERMINADA
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tMARCARTERMINADAImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARTERMINADARequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        Solicitud solicitudTMARCARTERMINADApthisSolicitud = request.getSolicitudTMARCARTERMINADApthisSolicitudInstance();

        request.checkArguments();
        Solicitud instance = solicitudTMARCARTERMINADApthisSolicitud;

        /* Action 1: IF (FechaTerminada = NULL AND Estado = 4 OR (EstadoDoc = 2 AND (Estado = 2 OR Estado = 3))) */
        boolean ifCondition1 = solicitudTMARCARTERMINADApthisSolicitud.getSolicitudFechaTerminada() == null && (solicitudTMARCARTERMINADApthisSolicitud.getSolicitudEstado()).equals(Long.valueOf(4)) || ((solicitudTMARCARTERMINADApthisSolicitud.getSolicitudEstadoDoc()).equals(Long.valueOf(2)) && ((solicitudTMARCARTERMINADApthisSolicitud.getSolicitudEstado()).equals(Long.valueOf(2)) || (solicitudTMARCARTERMINADApthisSolicitud.getSolicitudEstado()).equals(Long.valueOf(3))));
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFechaTerminadaRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.AsignarFechaTerminadaRequest();
            step10Request.setSolicitudasignarFechaTerminadapthisSolicitudInstance(solicitudTMARCARTERMINADApthisSolicitud);
            solicitudSrv.asignarFechaTerminadaImplementation(step10Request);
            /* Action 11: IF (EXIST (UsrAfiliacion) = true) */
            boolean ifCondition11 = repository.assocOperator264(solicitudTMARCARTERMINADApthisSolicitud).equals(true);
            if (ifCondition11) {
                com.keralty.aegenerasolicitud.viewmodel.funcionario.siu.SolicitudTerminadaRequest step110Request = new com.keralty.aegenerasolicitud.viewmodel.funcionario.siu.SolicitudTerminadaRequest();
                step110Request.setFuncionariosolicitudTerminadapthisFuncionarioInstance(solicitudTMARCARTERMINADApthisSolicitud.getUsrAfiliacion());
                funcionarioSrv.solicitudTerminadaImplementation(step110Request);
            }
            /* Action 12: IF (EXIST (UsrAreaMedica) = true) */
            boolean ifCondition12 = repository.assocOperator265(solicitudTMARCARTERMINADApthisSolicitud).equals(true);
            if (ifCondition12) {
                com.keralty.aegenerasolicitud.viewmodel.funcionario.siu.SolicitudTerminadaRequest step120Request = new com.keralty.aegenerasolicitud.viewmodel.funcionario.siu.SolicitudTerminadaRequest();
                step120Request.setFuncionariosolicitudTerminadapthisFuncionarioInstance(solicitudTMARCARTERMINADApthisSolicitud.getUsrAreaMedica().getFuncionarioFacet());
                funcionarioSrv.solicitudTerminadaImplementation(step120Request);
            }
        }

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICSolicitud());

        return response;
    }

    // Service: TENVIARDOCAUTOAUX

    /*
     * Entry point for service TENVIARDOCAUTOAUX
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tENVIARDOCAUTOAUX(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TENVIARDOCAUTOAUXRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = solicitudSrv.tENVIARDOCAUTOAUXTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TENVIARDOCAUTOAUX
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tENVIARDOCAUTOAUXTxn(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TENVIARDOCAUTOAUXRequest request) throws ModelException {
        request.setSolicitudTENVIARDOCAUTOAUXpthisSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getSolicitudTENVIARDOCAUTOAUXpthisSolicitud()));
        return solicitudSrv.tENVIARDOCAUTOAUXImplementation(request);
    }

    /*
     * Implements service TENVIARDOCAUTOAUX
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tENVIARDOCAUTOAUXImplementation(
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TENVIARDOCAUTOAUXRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        solicitudServiceAEEnvioDoc.tENVIARDOCAUTOAUX(request);
        return response;
    }


    @Override
    protected void applyHVFilter(SQLSelect sql) throws SystemException {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        SQLWhereDisjunction disjunction = new SQLWhereDisjunction();
        if (getAgentRoles().contains(AsesorConstants.CLASS_NAME)) applyHVFilter4Asesor(sql, disjunction);
        if (getAgentRoles().contains(AreaMedicaConstants.CLASS_NAME)) applyHVFilter4AreaMedica(sql, disjunction);
        if (getAgentRoles().contains(AfiliacionesConstants.CLASS_NAME)) applyHVFilter4Afiliaciones(sql, disjunction);
        conjunction.addDisjunction(disjunction);
        sql.addConjunction(conjunction);
    }

    private void applyHVFilter4Asesor(SQLSelect sql, SQLWhereDisjunction disjunction) throws SystemException{
        // Agente = agent OR Agente.CodJefe = Agent.Usuario OR EXIST (UsrAfiliacion) WHERE (UsrAfiliacion = agent) = true
        Asesor agentInstance = getAgentAsesor();
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
                SQLWhereOperandPathInstance operand114 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_AGENTE, FuncionarioConstants.CLASS_NAME);
                comparison3.setFirstOperand(operand114);
                
                // Operand 2
                SQLWhereOperandOVParameter operand225 = new SQLWhereOperandOVParameter(Constants.AGENTKEYWORD, AsesorConstants.CLASS_NAME, AsesorConstants.CLASS_NAME, agentInstance.getOid(), Constants.AGENTKEYWORD);
                comparison3.setSecondOperand(operand225);
                comparison2.addOperand(comparison3);
        
            // Operand 2
                // '=' Comparison
                SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
                comparison5.setOperator("=");
                
                // Operand 1
                SQLWhereOperandPathAttribute operand116 = new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ATTR_NAME_FUNCIONARIOCODJEFE, Constants.Type.STRING.getTypeName());
                comparison5.setFirstOperand(operand116);
                
                // Operand 2
                comparison5.setSecondOperand(new SQLWhereOperandLiteral(agentInstance.getFuncionarioUsuario(), Constants.Type.STRING.getTypeName()));
                comparison2.addOperand(comparison5);
            
            comparison1.addOperand(comparison2);
        }
    
        // Operand 2
        // 'exist' Association operator
        SQLWhereOperandSubQuery operandSubQueryAssocOperator000 = new SQLWhereOperandSubQuery(repository.assocOperator000Range1Query(sql));
        comparison1.addOperand(operandSubQueryAssocOperator000);
        
        disjunction.addOperand(comparison1);
    }

    }

    private void applyHVFilter4AreaMedica(SQLSelect sql, SQLWhereDisjunction disjunction) {
        // UsrAreaMedica = agent
        AreaMedica agentInstance = getAgentAreaMedica();
        // '=' Comparison
        SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
        comparison1.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathInstance operand112 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_USRAREAMEDICA, AreaMedicaConstants.CLASS_NAME);
        comparison1.setFirstOperand(operand112);
        
        // Operand 2
        SQLWhereOperandOVParameter operand223 = new SQLWhereOperandOVParameter(Constants.AGENTKEYWORD, AreaMedicaConstants.CLASS_NAME, AreaMedicaConstants.CLASS_NAME, agentInstance.getOid(), Constants.AGENTKEYWORD);
        comparison1.setSecondOperand(operand223);
        disjunction.addOperand(comparison1);

    }

    private void applyHVFilter4Afiliaciones(SQLSelect sql, SQLWhereDisjunction disjunction) throws SystemException{
        // UsrAfiliacion = agent AND COUNT (Aesolicitudes.PlanT.Afiliaciones) WHERE (Aesolicitudes.PlanT.Afiliaciones = agent) > 0
        Afiliaciones agentInstance = getAgentAfiliaciones();
    {
        // 'and' Logical operator
        SQLWhereConjunction comparison1 = new SQLWhereConjunction();
    
        // Operand 1
            // '=' Comparison
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand113 = new SQLWhereOperandPathInstance(SolicitudConstants.ROLE_NAME_USRAFILIACION, FuncionarioConstants.CLASS_NAME);
            comparison2.setFirstOperand(operand113);
            
            // Operand 2
            SQLWhereOperandOVParameter operand224 = new SQLWhereOperandOVParameter(Constants.AGENTKEYWORD, AfiliacionesConstants.CLASS_NAME, AfiliacionesConstants.CLASS_NAME, agentInstance.getOid(), Constants.AGENTKEYWORD);
            comparison2.setSecondOperand(operand224);
            comparison1.addOperand(comparison2);
    
        // Operand 2
            // '>' Comparison
            SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
            comparison4.setOperator(">");
            
            // Operand 1
                // 'count' Association operator
                SQLWhereOperandSubQuery operandSubQueryAssocOperator001 = new SQLWhereOperandSubQuery(repository.assocOperator001Range1Query(sql));
            comparison4.setFirstOperand(operandSubQueryAssocOperator001);
            
            // Operand 2
            comparison4.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(0), Constants.Type.NAT.getTypeName()));
            comparison1.addOperand(comparison4);
        
        disjunction.addOperand(comparison1);
    }

    }


    private void suppInfo4ICSolicitud(String searchText, SQLSelect select) {
        try {
            SQLWhereDisjunction disjunction = new SQLWhereDisjunction();

            SQLWhereComparison comparison1 = new SQLWhereComparison(select);
            comparison1.setOperator("LIKE");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESNUMEROSOLICITUD, Constants.Type.STRING.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(searchText, Constants.Type.STRING.getTypeName()));
            disjunction.addComparison(comparison1);

            SQLWhereConjunction conjunction = new SQLWhereConjunction();
            conjunction.addDisjunction(disjunction);
            select.addConjunction(conjunction);
        } catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    @Override
    protected void applyOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        applyOCOCSolicitudPrioridadFechas(queryRequest, select);
        applyOCOCSolicitudFechaFAPrioridad(queryRequest, select);
        applyOCOCSolicitudFechaCreacion(queryRequest, select);
        applyDSItemSort(queryRequest, select);
        applyDefaultOrderCriteria(queryRequest, select);
    }

    private void applyDefaultOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        if (queryRequest.getOrderCriteriaName() != null && !queryRequest.getOrderCriteriaName().isBlank())
            return;
        if (queryRequest.getDsItemSort() != null && !queryRequest.getDsItemSort().isBlank())
            return;
        select.getOrderByPart().addItem(Constants.normalizePath(SolicitudConstants.CLASS_NAME, SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_FIELD_AESOLICITUDESIDSOLICITUD, false), true);
    }


    private void applyOCOCSolicitudPrioridadFechas(QueryRequest queryRequest, SQLSelect select) {
        if ("OC_Solicitud_PrioridadFechas".equalsIgnoreCase(queryRequest.getOrderCriteriaName())) {
            select.getOrderByPart().addItem(Constants.normalizePath(SolicitudConstants.CLASS_NAME, SolicitudConstants.ATTR_NAME_SOLICITUDPRIORIDAD, false), true);
            select.getOrderByPart().addItem(Constants.normalizePath(SolicitudConstants.CLASS_NAME, SolicitudConstants.ATTR_NAME_SOLICITUDFECHACOMPLETADA, false), true);
            select.getOrderByPart().addItem(Constants.normalizePath(SolicitudConstants.CLASS_NAME, SolicitudConstants.ATTR_NAME_SOLICITUDFECHAULTIMODOC, false), true);
        }
    }


    private void applyOCOCSolicitudFechaFAPrioridad(QueryRequest queryRequest, SQLSelect select) {
        if ("OC_Solicitud_FechaFA_Prioridad".equalsIgnoreCase(queryRequest.getOrderCriteriaName())) {
            select.getOrderByPart().addItem(Constants.normalizePath(SolicitudConstants.CLASS_NAME, SolicitudConstants.ATTR_NAME_SOLICITUDFECHAFA, false), true);
            select.getOrderByPart().addItem(Constants.normalizePath(SolicitudConstants.CLASS_NAME, SolicitudConstants.ATTR_NAME_SOLICITUDPRIORIDAD, false), true);
        }
    }


    private void applyOCOCSolicitudFechaCreacion(QueryRequest queryRequest, SQLSelect select) {
        if ("OC_SolicitudFechaCreacion".equalsIgnoreCase(queryRequest.getOrderCriteriaName())) {
            select.getOrderByPart().addItem(Constants.normalizePath(SolicitudConstants.CLASS_NAME, SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFESOLICI, false), false);
        }
    }
    
    @Override
    protected AbstractAppEntity buildRelatedInstance(String relatedInstanceJsonOid, String relatedInstancePathFirstItem, String remainingPath) {
        AbstractAppEntity relatedInstance = null;
        if (relatedInstancePathFirstItem.isEmpty()) {
            relatedInstance = buildSolicitudRelatedInstance(relatedInstanceJsonOid);
        }
        if (SolicitudConstants.ROLE_NAME_DIRECCION.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4Direccion(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_PROTECCIONDATOS.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4ProteccionDatos(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_USUARIOS.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4Usuario(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_CONTRATANTE.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4Contratante(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_FIRMA.equalsIgnoreCase(relatedInstancePathFirstItem) || SolicitudConstants.ROLE_NAME_SEGUNDAFIRMA.equalsIgnoreCase(relatedInstancePathFirstItem) || SolicitudConstants.ROLE_NAME_FIRMACONTRATANTEINCL.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4Firma(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_USUARIOSREGISTRADOS.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4UsuarioReg(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_TITULAR.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4Titular(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_DOCUMENTOSGESTOR.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4DocumentoG(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_ERRORESWS.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4ErrorWS(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_LOGESTADO.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4LogEstado(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_LOGASIGNACIONES.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4LogAsignaciones(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_AESOLICITUDES.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4Aesolicitudes(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_CONTRATANTEPERSONA.equalsIgnoreCase(relatedInstancePathFirstItem) || SolicitudConstants.ROLE_NAME_TITULARPERSONA.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4Persona(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_GRUPOASOCIADO.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4GrupoAsociado(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_AGENTE.equalsIgnoreCase(relatedInstancePathFirstItem) || SolicitudConstants.ROLE_NAME_USRAFILIACION.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4Funcionario(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_FORMAPAGO.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4FormaPago(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_PERIODICIDAD.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4Periodicidad(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_CONTRATOC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4ContratoC(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_USRAREAMEDICA.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4AreaMedica(relatedInstanceJsonOid, remainingPath);
        }
        if (SolicitudConstants.ROLE_NAME_TIPOFACTURA.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildSolicitudRelatedInstance4TipoFactura(relatedInstanceJsonOid, remainingPath);
        }
        return relatedInstance;
    }
    
    private AbstractAppEntity buildSolicitudRelatedInstance(String relatedInstanceJsonOid) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		return new Solicitud(mapper.readValue(relatedInstanceJsonOid, SolicitudOid.class));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4Direccion(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Direccion(mapper.readValue(relatedInstanceJsonOid, DireccionOid.class)): Direccion.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4ProteccionDatos(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new ProteccionDatos(mapper.readValue(relatedInstanceJsonOid, ProteccionDatosOid.class)): ProteccionDatos.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4Usuario(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Usuario(mapper.readValue(relatedInstanceJsonOid, UsuarioOid.class)): Usuario.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4Contratante(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Contratante(mapper.readValue(relatedInstanceJsonOid, ContratanteOid.class)): Contratante.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4Firma(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Firma(mapper.readValue(relatedInstanceJsonOid, FirmaOid.class)): Firma.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4UsuarioReg(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new UsuarioReg(mapper.readValue(relatedInstanceJsonOid, UsuarioRegOid.class)): UsuarioReg.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4Titular(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Titular(mapper.readValue(relatedInstanceJsonOid, TitularOid.class)): Titular.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4DocumentoG(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new DocumentoG(mapper.readValue(relatedInstanceJsonOid, DocumentoGOid.class)): DocumentoG.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4ErrorWS(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new ErrorWS(mapper.readValue(relatedInstanceJsonOid, ErrorWSOid.class)): ErrorWS.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4LogEstado(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new LogEstado(mapper.readValue(relatedInstanceJsonOid, LogEstadoOid.class)): LogEstado.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4LogAsignaciones(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new LogAsignaciones(mapper.readValue(relatedInstanceJsonOid, LogAsignacionesOid.class)): LogAsignaciones.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4Aesolicitudes(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Aesolicitudes(mapper.readValue(relatedInstanceJsonOid, AesolicitudesOid.class)): Aesolicitudes.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4Persona(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Persona(mapper.readValue(relatedInstanceJsonOid, PersonaOid.class)): Persona.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4GrupoAsociado(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new GrupoAsociado(mapper.readValue(relatedInstanceJsonOid, GrupoAsociadoOid.class)): GrupoAsociado.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4Funcionario(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Funcionario(mapper.readValue(relatedInstanceJsonOid, FuncionarioOid.class)): Funcionario.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4FormaPago(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new FormaPago(mapper.readValue(relatedInstanceJsonOid, FormaPagoOid.class)): FormaPago.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4Periodicidad(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Periodicidad(mapper.readValue(relatedInstanceJsonOid, PeriodicidadOid.class)): Periodicidad.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4ContratoC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new ContratoC(mapper.readValue(relatedInstanceJsonOid, ContratoCOid.class)): ContratoC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4AreaMedica(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new AreaMedica(mapper.readValue(relatedInstanceJsonOid, AreaMedicaOid.class)): AreaMedica.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildSolicitudRelatedInstance4TipoFactura(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new TipoFactura(mapper.readValue(relatedInstanceJsonOid, TipoFacturaOid.class)): TipoFactura.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            SolicitudOid oid = new SolicitudOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_FIELD_AESOLICITUDESIDSOLICITUD, Constants.Type.AUTONUMERIC.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getAesolicitudesidsolicitud(), Constants.Type.AUTONUMERIC.getTypeName()));
            sql.addComparison(comparison1);
        } catch (Exception e) {
            LoggerFactory.getLogger(SolicitudService.class).error(e.getMessage());
        }
    }

}
