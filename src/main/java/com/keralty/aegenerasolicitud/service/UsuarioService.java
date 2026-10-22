package com.keralty.aegenerasolicitud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralty.aegenerasolicitud.exception.ModelException;
import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.AesolicitudesConstants;
import com.keralty.aegenerasolicitud.global.AsesorConstants;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.FuncionarioConstants;
import com.keralty.aegenerasolicitud.global.SolicitudConstants;
import com.keralty.aegenerasolicitud.global.TipoIdentificacionConstants;
import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.global.UsuarioConstants;
import com.keralty.aegenerasolicitud.persistence.AbstractAppEntity;
import com.keralty.aegenerasolicitud.persistence.AnexoMat;
import com.keralty.aegenerasolicitud.persistence.AnexoNeo;
import com.keralty.aegenerasolicitud.persistence.Asesor;
import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.Companyia;
import com.keralty.aegenerasolicitud.persistence.CuestionarioP;
import com.keralty.aegenerasolicitud.persistence.Eps;
import com.keralty.aegenerasolicitud.persistence.EstadoBeneficiario;
import com.keralty.aegenerasolicitud.persistence.oid.AnexoMatOid;
import com.keralty.aegenerasolicitud.persistence.oid.AnexoNeoOid;
import com.keralty.aegenerasolicitud.persistence.oid.BeneficiarioCOid;
import com.keralty.aegenerasolicitud.persistence.oid.CompanyiaOid;
import com.keralty.aegenerasolicitud.persistence.oid.CuestionarioPOid;
import com.keralty.aegenerasolicitud.persistence.oid.EpsOid;
import com.keralty.aegenerasolicitud.persistence.oid.EstadoBeneficiarioOid;
import com.keralty.aegenerasolicitud.persistence.oid.ParentescoOid;
import com.keralty.aegenerasolicitud.persistence.oid.PersonaOid;
import com.keralty.aegenerasolicitud.persistence.oid.PreexistenciaOid;
import com.keralty.aegenerasolicitud.persistence.oid.SolicitudOid;
import com.keralty.aegenerasolicitud.persistence.oid.TipoIdentificacionOid;
import com.keralty.aegenerasolicitud.persistence.oid.UsuarioOid;
import com.keralty.aegenerasolicitud.persistence.Parentesco;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.Preexistencia;
import com.keralty.aegenerasolicitud.persistence.PreexistenciaC;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.TipoIdentificacion;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import com.keralty.aegenerasolicitud.repository.UsuarioJpaRepository;
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
import com.keralty.aegenerasolicitud.viewmodel.PreloadViewModel;
import com.keralty.aegenerasolicitud.viewmodel.QueryRequest;
import com.keralty.aegenerasolicitud.viewmodel.QueryResponse;
import com.keralty.aegenerasolicitud.viewmodel.ServiceResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

@Transactional
@ApplicationScoped
public class UsuarioService extends AbstractService {

    UsuarioJpaRepository repository;

    UsuarioService usuarioSrv;

    EstadoBeneficiarioService estadoBeneficiarioSrv;

    BeneficiarioCService beneficiarioCSrv;

    PreexistenciaService preexistenciaSrv;

    PersonaCService personaCSrv;

    PreexistenciaCService preexistenciaCSrv;

    SolicitudService solicitudSrv;

    TipoIdentificacionService tipoIdentificacionSrv;

    CuestionarioPService cuestionarioPSrv;

    AnexoMatService anexoMatSrv;

    AnexoNeoService anexoNeoSrv;

    PersonaService personaSrv;

    EpsService epsSrv;

    ParentescoService parentescoSrv;

    CompanyiaService companyiaSrv;

    UserFunctions userFunctionsSrv;

    @Inject
    public UsuarioService(
            UsuarioService usuarioSrv,
            EstadoBeneficiarioService estadoBeneficiarioSrv,
            BeneficiarioCService beneficiarioCSrv,
            PreexistenciaService preexistenciaSrv,
            PersonaCService personaCSrv,
            PreexistenciaCService preexistenciaCSrv,
            SolicitudService solicitudSrv,
            TipoIdentificacionService tipoIdentificacionSrv,
            CuestionarioPService cuestionarioPSrv,
            AnexoMatService anexoMatSrv,
            AnexoNeoService anexoNeoSrv,
            PersonaService personaSrv,
            EpsService epsSrv,
            ParentescoService parentescoSrv,
            CompanyiaService companyiaSrv,
            UserFunctions userFunctionsSrv,
            UsuarioJpaRepository repository) {
        this.usuarioSrv = usuarioSrv;
        this.estadoBeneficiarioSrv = estadoBeneficiarioSrv;
        this.beneficiarioCSrv = beneficiarioCSrv;
        this.preexistenciaSrv = preexistenciaSrv;
        this.personaCSrv = personaCSrv;
        this.preexistenciaCSrv = preexistenciaCSrv;
        this.solicitudSrv = solicitudSrv;
        this.tipoIdentificacionSrv = tipoIdentificacionSrv;
        this.cuestionarioPSrv = cuestionarioPSrv;
        this.anexoMatSrv = anexoMatSrv;
        this.anexoNeoSrv = anexoNeoSrv;
        this.personaSrv = personaSrv;
        this.epsSrv = epsSrv;
        this.parentescoSrv = parentescoSrv;
        this.companyiaSrv = companyiaSrv;
        this.userFunctionsSrv = userFunctionsSrv;
        this.repository = repository;
    }

    public List<Map<String, Object>> solveQuery(QueryRequest request) {
        return solveGenericQuery(request, UsuarioConstants.CLASS_NAME);
    }

    private List<Usuario> solveUsuarioTypedQuery(QueryRequest request) {
        boolean hasInMemoryFilters = hasInMemoryFilters(request);
        int requestedPageSize = request.getPageSize();
        int pageSize = requestedPageSize >= 0
                && requestedPageSize <= Constants.MAX_BLOCKSIZE
                ? requestedPageSize
                : Constants.DEFAULT_BLOCKSIZE;
        int pageNumber = Math.max(1, request.getPageNumber());

    	List<Usuario> results = repository.solveQuery(processQueryRequest(request, UsuarioConstants.CLASS_NAME), (hasInMemoryFilters ? 0 : pageSize), (hasInMemoryFilters ? 1 : pageNumber)).stream()
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
    
    private int solveUsuarioTypedCountQuery(QueryRequest request) {
        return hasInMemoryFilters(request)
               ? repository.solveQuery(processQueryRequest(request, UsuarioConstants.CLASS_NAME)).stream().filter(instance -> applyMemoryFilters(request, instance)).collect(Collectors.toList()).size()
               : repository.solveCountQuery(processCountQueryRequest(request, UsuarioConstants.CLASS_NAME));
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
        if (UsuarioConstants.FILTER_NAME_IFUSUARIOSINCUESMED.equalsIgnoreCase(filterRequest.getName())) {
            return true;
        }
        return false;
    }

    /*
     * Returns the population of class Usuario
     *
     * @return Population of class Usuario
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Usuario> get() {
        List<Usuario> results = new java.util.ArrayList<>();
        repository.findAll().forEach(results::add);
        return results;
    }
    
    /*
     * Gets the preloaded list of objects of class Usuario
     *
     * @param queryRequest Request object that dictates the search conditions to 
     * retrieve objects
     *
     * @return Preloaded list of objects of class Usuario
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PreloadViewModel> getPreload(QueryRequest queryRequest) {
        List<PreloadViewModel> results = new ArrayList<>();
        List<Usuario> instances = get(queryRequest);
        if(UsuarioConstants.DSICUSUARIO.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildICUsuario())).collect(Collectors.toList());
        }
        if(UsuarioConstants.DSDSUSUARIOPIUUSUARIOSCONT.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSUsuarioPIUUsuariosCont())).collect(Collectors.toList());
        }
        if(UsuarioConstants.DSDSUSUARIOPIUDESTATUSSOL.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSUsuarioPIUDeStatusSol())).collect(Collectors.toList());
        }
        if(UsuarioConstants.DSDSUSUARIOPOLIZAINTER.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSUsuarioPolizaInter())).collect(Collectors.toList());
        }
        if(UsuarioConstants.DSDSUSUARIONULL.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSUsuarioNUll())).collect(Collectors.toList());
        }
        return results;
    }
    /*
     * Returns the population of class Usuario that matches certain search criteria
     *
     * @param queryRequest Search criteria
     *
     * @return Population of class Usuario matching the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Usuario> get(QueryRequest queryRequest) {
        return solveUsuarioTypedQuery(queryRequest);
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
            return new PopulationResponseViewModel(solveUsuarioTypedQuery(queryRequest).stream()
                    .map(instance -> buildDynamicObject(instance, queryRequest.getDisplaySetItems()))
                    .collect(Collectors.toList()), queryRequest.getPageSize() != 0 ? solveUsuarioTypedCountQuery(queryRequest) : 0);
        } else {
            return new PreloadResponseViewModel(solveUsuarioTypedQuery(queryRequest).stream()
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
        return new PopulationResponseViewModel(solveUsuarioTypedQuery(queryRequest).stream()
                .map(instance -> buildDynamicObject(instance, displaySetItems))
                .collect(Collectors.toList()));
    }

    /*
     * Returns an instance of Usuario by ID
     *
     * @param aesolicitudesidsolicitud Identification field aesolicitudesidsolicitud
     *
     * @param usuarioIdentificador Identification field usuarioIdentificador
     *
     * @return Instance of Usuario
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Usuario getById(Long aesolicitudesidsolicitud, Long usuarioIdentificador) {
        return getByOID(new UsuarioOid(aesolicitudesidsolicitud, usuarioIdentificador));
    }
    
    /*
     * Returns an instance of Usuario by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Usuario
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Usuario getByOID(UsuarioOid oid) {
        Optional<Usuario> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of Usuario by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Usuario
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public Usuario getByOIDWithHV(UsuarioOid oid) {
        Optional<Usuario> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<Usuario> getInstanceByOid(UsuarioOid oid) {
        return oid != null && oid.getAesolicitudesidsolicitud() != null
               ? repository.findById(oid)
               : Optional.empty();
    }

    /*
     * Returns an instance of Usuario by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of Usuario
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Usuario getByJsonOid(String jsonOid) {
        UsuarioOid oid = new UsuarioOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }

    /*
     * Returns the list of instances of Usuario related to a given instance through 
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Usuario
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Usuario> getByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        QueryRequest queryRequest = new QueryRequest(new FilterRequest(Constants.FILTER_BY_RELATED,
                Arrays.asList(new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_PATH, relatedInstancePath),
                        new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_OID, relatedInstance.getOid().toJson()))));
        return solveUsuarioTypedQuery(queryRequest);
    }

    /*
     * Returns the list of instances of Usuario related to a given instance through
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Usuario
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Usuario> getInstancesByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        return getByRelated(relatedInstance, relatedInstancePath);
    }

    /*
     * Returns a set of displayset items for an instance of Usuario
     *
     * @param oid Object IDentifier
     *
     * @param displaySetItems List of comma-separated displayset items
     *
     * @return Set of displayset items for an instance of Usuario
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public InstanceResponseViewModel getUsuarioDynamic(UsuarioOid oid, String displaySetItems) {
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
    public String getSuppInfo(UsuarioOid oid, String dsName) {
        Usuario instance = getByOIDWithHV(oid);
        return (instance != null ? getSuppInfo(instance, dsName): "");
    }

    private String getSuppInfo(Usuario instance, String dsName) {
        if (instance == null || instance.isNull()) return "";
        if (UsuarioConstants.DSICUSUARIO.equals(dsName))
            return instance.buildICUsuario();
        return dsName.isEmpty() ? instance.getOid().toJson() : "";
    }

    public static PreloadViewModel buildPreloadViewModel(Usuario instance, String dsName) {
        if (UsuarioConstants.DSICUSUARIO.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildICUsuario());
        if (UsuarioConstants.DSDSUSUARIOPIUUSUARIOSCONT.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSUsuarioPIUUsuariosCont());
        if (UsuarioConstants.DSDSUSUARIOPIUDESTATUSSOL.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSUsuarioPIUDeStatusSol());
        if (UsuarioConstants.DSDSUSUARIOPOLIZAINTER.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSUsuarioPolizaInter());
        if (UsuarioConstants.DSDSUSUARIONULL.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSUsuarioNUll());
        return new PreloadViewModel(instance.getOid().toJson(), "");
    }


    @Override
    protected void applyQueryFilters(FilterRequest filterRequest, SQLSelect sql) throws SystemException {
        if (UsuarioConstants.FILTER_NAME_IFUSUARIOSINCUESMED.equalsIgnoreCase(filterRequest.getName()))
            iFUsuarioSinCuesMed(filterRequest, sql);
        if (UsuarioConstants.FILTER_NAME_FUSUARIOSPOLIZAINTER.equalsIgnoreCase(filterRequest.getName()))
            fUsuariosPolizaInter(filterRequest, sql);
    }

    @Override
    protected void applyServiceSIFilters(FilterRequest filterRequest, SQLSelect sql) {
        if ("SuppInfo_IC_Usuario".equals(filterRequest.getName()))
            suppInfo4ICUsuario(filterRequest.getFilterVariable("searchText").toString(), sql);
    }

    private boolean applyMemoryFilters(QueryRequest queryRequest, Usuario instance) {
        for (FilterRequest filterRequest : queryRequest.getFilters()) {
            if (!applyMemoryFilter(filterRequest, instance))
                return false;
        }
        return true;
    }
    
    private boolean applyMemoryFilter(FilterRequest filterRequest, Usuario instance) {
        return applyMemoryQueryFilter(filterRequest, instance);
    }

    private boolean applyMemoryQueryFilter(FilterRequest filterRequest, Usuario entity) {
        if (UsuarioConstants.FILTER_NAME_IFUSUARIOSINCUESMED.equalsIgnoreCase(filterRequest.getName()))
            return inMemoryIFUsuarioSinCuesMed(entity);
        return true;
    }


    private void iFUsuarioSinCuesMed(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Solicitud voSolicitud = filterRequest.hasFilterVariable(UsuarioConstants.VAR_NAME_IFUSUARIOSINCUESMED_VOSOLICITUD) ? solicitudSrv.getByOID(new SolicitudOid().fromJsonString(toJson(filterRequest.getFilterVariable(UsuarioConstants.VAR_NAME_IFUSUARIOSINCUESMED_VOSOLICITUD)))) : null;

        // Solicitud = voSolicitud
        if (voSolicitud != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand112 = new SQLWhereOperandPathInstance(UsuarioConstants.ROLE_NAME_SOLICITUD, SolicitudConstants.CLASS_NAME);
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            SQLWhereOperandOVParameter operand223 = new SQLWhereOperandOVParameter(UsuarioConstants.VAR_NAME_IFUSUARIOSINCUESMED_VOSOLICITUD, SolicitudConstants.CLASS_NAME, SolicitudConstants.CLASS_NAME, voSolicitud.getOid(), UsuarioConstants.VAR_NAME_IFUSUARIOSINCUESMED_VOSOLICITUD);
            comparison1.setSecondOperand(operand223);
            conjunction.addOperand(comparison1);

        }        

        sql.addConjunction(conjunction);
    }

    private void fUsuariosPolizaInter(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Date vfDesde = filterRequest.hasFilterVariable(UsuarioConstants.VAR_NAME_FUSUARIOSPOLIZAINTER_VFDESDE) ? (Date) filterRequest.getFilterVariable(UsuarioConstants.VAR_NAME_FUSUARIOSPOLIZAINTER_VFDESDE, FilterVariableRequest.DATE) : null;
        Date vfHasta = filterRequest.hasFilterVariable(UsuarioConstants.VAR_NAME_FUSUARIOSPOLIZAINTER_VFHASTA) ? (Date) filterRequest.getFilterVariable(UsuarioConstants.VAR_NAME_FUSUARIOSPOLIZAINTER_VFHASTA, FilterVariableRequest.DATE) : null;
        TipoIdentificacion vfTipoDoc = filterRequest.hasFilterVariable(UsuarioConstants.VAR_NAME_FUSUARIOSPOLIZAINTER_VFTIPODOC) ? tipoIdentificacionSrv.getByOID(new TipoIdentificacionOid().fromJsonString(toJson(filterRequest.getFilterVariable(UsuarioConstants.VAR_NAME_FUSUARIOSPOLIZAINTER_VFTIPODOC)))) : null;
        String vfNumIdent = filterRequest.hasFilterVariable(UsuarioConstants.VAR_NAME_FUSUARIOSPOLIZAINTER_VFNUMIDENT) ? (String) filterRequest.getFilterVariable(UsuarioConstants.VAR_NAME_FUSUARIOSPOLIZAINTER_VFNUMIDENT, FilterVariableRequest.STRING) : null;

        // Solicitud.Estado <> 4
        // '<>' Comparison
        SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
        comparison1.setOperator("<>");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(UsuarioConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ATTR_NAME_SOLICITUDESTADO, Constants.Type.INT.getTypeName());
        comparison1.setFirstOperand(operand112);
        
        // Operand 2
        comparison1.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(4), Constants.Type.NAT.getTypeName()));
        conjunction.addOperand(comparison1);


        // PolizaInternacional = true
        // '=' Comparison
        SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
        comparison2.setOperator("=");
        
        // Operand 1
        SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(UsuarioConstants.ATTR_NAME_USUARIOPOLIZAINTERNACIONAL, Constants.Type.BOOL.getTypeName());
        comparison2.setFirstOperand(operand113);
        
        // Operand 2
        comparison2.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
        conjunction.addOperand(comparison2);


        // Solicitud.Aesolicitudes.Fechaasignacion >= vfDesde
        if (vfDesde != null) {
            // '>=' Comparison
            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
            comparison3.setOperator(">=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand114 = new SQLWhereOperandPathAttribute(UsuarioConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison3.setFirstOperand(operand114);
            
            // Operand 2
            comparison3.setSecondOperand(new SQLWhereOperandLiteral(vfDesde, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison3);

        }        

        // Solicitud.Aesolicitudes.Fechaasignacion <= vfHasta
        if (vfHasta != null) {
            // '<=' Comparison
            SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
            comparison4.setOperator("<=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand115 = new SQLWhereOperandPathAttribute(UsuarioConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_NAME_AESOLICITUDESFECHAASIGNACION, Constants.Type.DATE.getTypeName());
            comparison4.setFirstOperand(operand115);
            
            // Operand 2
            comparison4.setSecondOperand(new SQLWhereOperandLiteral(vfHasta, Constants.Type.DATE.getTypeName()));
            conjunction.addOperand(comparison4);

        }        

        // TipoIdentificacion = vfTipoDoc
        if (vfTipoDoc != null) {
            // '=' Comparison
            SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
            comparison5.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand116 = new SQLWhereOperandPathInstance(UsuarioConstants.ROLE_NAME_TIPOIDENTIFICACION, TipoIdentificacionConstants.CLASS_NAME);
            comparison5.setFirstOperand(operand116);
            
            // Operand 2
            SQLWhereOperandOVParameter operand227 = new SQLWhereOperandOVParameter(UsuarioConstants.VAR_NAME_FUSUARIOSPOLIZAINTER_VFTIPODOC, TipoIdentificacionConstants.CLASS_NAME, TipoIdentificacionConstants.CLASS_NAME, vfTipoDoc.getOid(), UsuarioConstants.VAR_NAME_FUSUARIOSPOLIZAINTER_VFTIPODOC);
            comparison5.setSecondOperand(operand227);
            conjunction.addOperand(comparison5);

        }        

        // NumIdentificacion LIKE "%" + vfNumIdent
        if (vfNumIdent != null) {
            // 'like' Comparison
            SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
            comparison6.setOperator("like");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand117 = new SQLWhereOperandPathAttribute(UsuarioConstants.ATTR_NAME_USUARIONUMIDENTIFICACION, Constants.Type.STRING.getTypeName());
            comparison6.setFirstOperand(operand117);
            
            // Operand 2
            comparison6.setSecondOperand(new SQLWhereOperandLiteral("%" + vfNumIdent, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison6);

        }        

        sql.addConjunction(conjunction);
    }

    private boolean inMemoryIFUsuarioSinCuesMed(Usuario entity) {
        boolean result = true;

        // drvCuesMedOK = false
        try {
            result = entity.getUsuariodrvCuesMedOK().equals(false);
        } catch(Exception e) {
           LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
        return result;
    }

    // IIU Methods


    /*
     * Solves the query needed for PIUUsuariosDeContrato
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Usuario using displayset DSUsuarioPIUUsuariosCont that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.usuario.ds.DSUsuarioPIUUsuariosCont> piuPIUUsuariosDeContrato(QueryRequest queryRequest) {
        return solveUsuarioTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.usuario.ds.DSUsuarioPIUUsuariosCont::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUUsuariosDeContrato
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUUsuariosDeContrato
     */
    public int getCount4PIUUsuariosDeContrato(QueryRequest queryRequest) {
        return solveUsuarioTypedQuery(queryRequest).size();
    }

    /*
     * Solves the query needed for PIUUsuarioDePIUStatusSol
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Usuario using displayset DSUsuarioPIUDeStatusSol that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.usuario.ds.DSUsuarioPIUDeStatusSol> piuPIUUsuarioDePIUStatusSol(QueryRequest queryRequest) {
        return solveUsuarioTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.usuario.ds.DSUsuarioPIUDeStatusSol::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUUsuarioDePIUStatusSol
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUUsuarioDePIUStatusSol
     */
    public int getCount4PIUUsuarioDePIUStatusSol(QueryRequest queryRequest) {
        return solveUsuarioTypedQuery(queryRequest).size();
    }

    /*
     * Solves the query needed for PIUUsuariosPolInternacional
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Usuario using displayset DSUsuarioPolizaInter that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.usuario.ds.DSUsuarioPolizaInter> piuPIUUsuariosPolInternacional(QueryRequest queryRequest) {
        return solveUsuarioTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.usuario.ds.DSUsuarioPolizaInter::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUUsuariosPolInternacional
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUUsuariosPolInternacional
     */
    public int getCount4PIUUsuariosPolInternacional(QueryRequest queryRequest) {
        return solveUsuarioTypedQuery(queryRequest).size();
    }

    /*
     * Solves the query needed for PIUUsuarioNull
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Usuario using displayset DSUsuarioNUll that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.usuario.ds.DSUsuarioNUll> piuPIUUsuarioNull(QueryRequest queryRequest) {
        return solveUsuarioTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.usuario.ds.DSUsuarioNUll::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUUsuarioNull
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUUsuarioNull
     */
    public int getCount4PIUUsuarioNull(QueryRequest queryRequest) {
        return solveUsuarioTypedQuery(queryRequest).size();
    }

    // Service: modificarAttr

    /*
     * Entry point for service modificarAttr
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse modificarAttr(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.ModificarAttrRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = usuarioSrv.modificarAttrTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service modificarAttr
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse modificarAttrTxn(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.ModificarAttrRequest request) throws ModelException {
        request.setUsuariomodificarAttrpthisAttrUsuarioInstance(usuarioSrv.getByOIDWithHV(request.getUsuariomodificarAttrpthisAttrUsuario()));
        return usuarioSrv.modificarAttrImplementation(request);
    }

    /*
     * Implements service modificarAttr
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse modificarAttrImplementation(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.ModificarAttrRequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Usuario usuariomodificarAttrpthisAttrUsuario = request.getUsuariomodificarAttrpthisAttrUsuarioInstance();
        Boolean usuariomodificarAttrpBebeGestante = request.getUsuariomodificarAttrpBebeGestante();
        String usuariomodificarAttrpcodigoBancoRep = request.getUsuariomodificarAttrpcodigoBancoRep();
        Date usuariomodificarAttrpFechaAntiguedad = request.getUsuariomodificarAttrpFechaAntiguedad();
        Boolean usuariomodificarAttrpePolizaInternacional = request.getUsuariomodificarAttrpePolizaInternacional();

        request.checkArguments();
        Usuario instance = usuariomodificarAttrpthisAttrUsuario;
        instance.setUsuarioBebeGestante(usuariomodificarAttrpBebeGestante);
        instance.setUsuariocodigoBancoRep(usuariomodificarAttrpcodigoBancoRep);
        instance.setUsuarioFechaAntiguedadBeneficiario(usuariomodificarAttrpFechaAntiguedad);
        instance.setUsuarioPolizaInternacional(usuariomodificarAttrpePolizaInternacional);


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICUsuario());

        return response;
    }

    // Service: InsEstadoBeneficiarioCORE

    /*
     * Entry point for service InsEstadoBeneficiarioCORE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse insEstadoBeneficiarioCORE(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.InsEstadoBeneficiarioCORERequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = usuarioSrv.insEstadoBeneficiarioCORETxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service InsEstadoBeneficiarioCORE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse insEstadoBeneficiarioCORETxn(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.InsEstadoBeneficiarioCORERequest request) throws ModelException {
        request.setUsuarioInsEstadoBeneficiarioCOREpthisUsuarioInstance(usuarioSrv.getByOIDWithHV(request.getUsuarioInsEstadoBeneficiarioCOREpthisUsuario()));
        request.setUsuarioInsEstadoBeneficiarioCOREpevcEstadoBeneficiarioInstance(estadoBeneficiarioSrv.getByOIDWithHV(request.getUsuarioInsEstadoBeneficiarioCOREpevcEstadoBeneficiario()));
        return usuarioSrv.insEstadoBeneficiarioCOREImplementation(request, true);
    }

    /*
     * Implements service InsEstadoBeneficiarioCORE
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse insEstadoBeneficiarioCOREImplementation(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.InsEstadoBeneficiarioCORERequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Usuario usuarioInsEstadoBeneficiarioCOREpthisUsuario = request.getUsuarioInsEstadoBeneficiarioCOREpthisUsuarioInstance();
        EstadoBeneficiario usuarioInsEstadoBeneficiarioCOREpevcEstadoBeneficiario = request.getUsuarioInsEstadoBeneficiarioCOREpevcEstadoBeneficiarioInstance();

        request.checkArguments();
        Usuario instance = usuarioInsEstadoBeneficiarioCOREpthisUsuario;

        instance.add2EstadoBeneficiario(usuarioInsEstadoBeneficiarioCOREpevcEstadoBeneficiario);
        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.estadobeneficiario.siu.InsEstadoBeneficiarioCORERequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.estadobeneficiario.siu.InsEstadoBeneficiarioCORERequest();
            relatedRequest.setEstadoBeneficiarioInsEstadoBeneficiarioCOREpthisEstadoBeneficiarioInstance(usuarioInsEstadoBeneficiarioCOREpevcEstadoBeneficiario);
            relatedRequest.setEstadoBeneficiarioInsEstadoBeneficiarioCOREpevcUsuarioInstance(usuarioInsEstadoBeneficiarioCOREpthisUsuario);
            estadoBeneficiarioSrv.insEstadoBeneficiarioCOREImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICUsuario());

        return response;
    }

    // Service: DelEstadoBeneficiarioCORE

    /*
     * Entry point for service DelEstadoBeneficiarioCORE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse delEstadoBeneficiarioCORE(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.DelEstadoBeneficiarioCORERequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = usuarioSrv.delEstadoBeneficiarioCORETxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service DelEstadoBeneficiarioCORE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse delEstadoBeneficiarioCORETxn(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.DelEstadoBeneficiarioCORERequest request) throws ModelException {
        request.setUsuarioDelEstadoBeneficiarioCOREpthisUsuarioInstance(usuarioSrv.getByOIDWithHV(request.getUsuarioDelEstadoBeneficiarioCOREpthisUsuario()));
        request.setUsuarioDelEstadoBeneficiarioCOREpevcEstadoBeneficiarioInstance(estadoBeneficiarioSrv.getByOIDWithHV(request.getUsuarioDelEstadoBeneficiarioCOREpevcEstadoBeneficiario()));
        return usuarioSrv.delEstadoBeneficiarioCOREImplementation(request, true);
    }

    /*
     * Implements service DelEstadoBeneficiarioCORE
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse delEstadoBeneficiarioCOREImplementation(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.DelEstadoBeneficiarioCORERequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Usuario usuarioDelEstadoBeneficiarioCOREpthisUsuario = request.getUsuarioDelEstadoBeneficiarioCOREpthisUsuarioInstance();
        EstadoBeneficiario usuarioDelEstadoBeneficiarioCOREpevcEstadoBeneficiario = request.getUsuarioDelEstadoBeneficiarioCOREpevcEstadoBeneficiarioInstance();

        request.checkArguments();
        Usuario instance = usuarioDelEstadoBeneficiarioCOREpthisUsuario;

        instance.add2EstadoBeneficiario(null);
        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.estadobeneficiario.siu.DelEstadoBeneficiarioCORERequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.estadobeneficiario.siu.DelEstadoBeneficiarioCORERequest();
            relatedRequest.setEstadoBeneficiarioDelEstadoBeneficiarioCOREpthisEstadoBeneficiarioInstance(usuarioDelEstadoBeneficiarioCOREpevcEstadoBeneficiario);
            relatedRequest.setEstadoBeneficiarioDelEstadoBeneficiarioCOREpevcUsuarioInstance(usuarioDelEstadoBeneficiarioCOREpthisUsuario);
            estadoBeneficiarioSrv.delEstadoBeneficiarioCOREImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICUsuario());

        return response;
    }

    // Service: registrarDatosCORE

    /*
     * Entry point for service registrarDatosCORE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse registrarDatosCORE(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.RegistrarDatosCORERequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = usuarioSrv.registrarDatosCORETxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service registrarDatosCORE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse registrarDatosCORETxn(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.RegistrarDatosCORERequest request) throws ModelException {
        request.setUsuarioregistrarDatosCOREpthisUsuarioInstance(usuarioSrv.getByOIDWithHV(request.getUsuarioregistrarDatosCOREpthisUsuario()));
        return usuarioSrv.registrarDatosCOREImplementation(request);
    }

    /*
     * Implements service registrarDatosCORE
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse registrarDatosCOREImplementation(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.RegistrarDatosCORERequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Usuario usuarioregistrarDatosCOREpthisUsuario = request.getUsuarioregistrarDatosCOREpthisUsuarioInstance();
        Long usuarioregistrarDatosCOREpNumUsuario = request.getUsuarioregistrarDatosCOREpNumUsuario();
        Boolean usuarioregistrarDatosCOREpRequierExamenMedico = request.getUsuarioregistrarDatosCOREpRequierExamenMedico();
        Long usuarioregistrarDatosCOREpEstadoMedico = request.getUsuarioregistrarDatosCOREpEstadoMedico();
        Double usuarioregistrarDatosCOREpValorCuotaInscripcion = request.getUsuarioregistrarDatosCOREpValorCuotaInscripcion();
        Boolean usuarioregistrarDatosCOREpTienePreexistencias = request.getUsuarioregistrarDatosCOREpTienePreexistencias();
        Date usuarioregistrarDatosCOREpFechaInicionVigencia = request.getUsuarioregistrarDatosCOREpFechaInicionVigencia();
        Date usuarioregistrarDatosCOREpFechaFinVigencia = request.getUsuarioregistrarDatosCOREpFechaFinVigencia();

        request.checkArguments();
        Usuario instance = usuarioregistrarDatosCOREpthisUsuario;
        instance.setUsuarioNumUsuario(usuarioregistrarDatosCOREpNumUsuario);
        instance.setUsuarioRequierExamenMedico(usuarioregistrarDatosCOREpRequierExamenMedico);
        instance.setUsuarioEstadoMedico(usuarioregistrarDatosCOREpEstadoMedico);
        instance.setUsuarioValorCuotaInscripcion(usuarioregistrarDatosCOREpValorCuotaInscripcion);
        instance.setUsuarioTienePreexistencias(usuarioregistrarDatosCOREpTienePreexistencias);
        instance.setUsuarioFechaInicioVigencia(usuarioregistrarDatosCOREpFechaInicionVigencia);
        instance.setUsuarioFechaFinVigencia(usuarioregistrarDatosCOREpFechaFinVigencia);


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICUsuario());

        return response;
    }

    // Service: TACTDESDECORE

    /*
     * Entry point for service TACTDESDECORE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tACTDESDECORE(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.TACTDESDECORERequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = usuarioSrv.tACTDESDECORETxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service TACTDESDECORE
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tACTDESDECORETxn(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.TACTDESDECORERequest request) throws ModelException, SystemException {
        request.setUsuarioTACTDESDECOREpthisUsuarioInstance(usuarioSrv.getByOIDWithHV(request.getUsuarioTACTDESDECOREpthisUsuario()));
        request.setUsuarioTACTDESDECOREpBeneficiarioCInstance(beneficiarioCSrv.getByOIDWithHV(request.getUsuarioTACTDESDECOREpBeneficiarioC()));
        return usuarioSrv.tACTDESDECOREImplementation(request);
    }

    /*
     * Implements service TACTDESDECORE
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tACTDESDECOREImplementation(
            com.keralty.aegenerasolicitud.viewmodel.usuario.siu.TACTDESDECORERequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        BeneficiarioC usuarioTACTDESDECOREpBeneficiarioC = request.getUsuarioTACTDESDECOREpBeneficiarioCInstance();
        Usuario usuarioTACTDESDECOREpthisUsuario = request.getUsuarioTACTDESDECOREpthisUsuarioInstance();

        request.checkArguments();
        Usuario instance = usuarioTACTDESDECOREpthisUsuario;

        for(Preexistencia step1Entity : repository.assocOperator069(usuarioTACTDESDECOREpthisUsuario)) {
            com.keralty.aegenerasolicitud.viewmodel.preexistencia.siu.EliminarRequest step1Request = new com.keralty.aegenerasolicitud.viewmodel.preexistencia.siu.EliminarRequest();
            step1Request.setPreexistenciaeliminarpthisPreexistenciaInstance(step1Entity);
            preexistenciaSrv.eliminarImplementation(step1Request);
        }

        for(PreexistenciaC step2Entity : repository.assocOperator070(usuarioTACTDESDECOREpthisUsuario, usuarioTACTDESDECOREpBeneficiarioC)) {
            com.keralty.aegenerasolicitud.viewmodel.preexistencia.siu.CrearRequest step2Request = new com.keralty.aegenerasolicitud.viewmodel.preexistencia.siu.CrearRequest();
            step2Request.setPreexistenciacrearpagrUsuarioInstance(usuarioTACTDESDECOREpthisUsuario);
            step2Request.setPreexistenciacrearpatrDescripcion(step2Entity.getPreexistenciaCDescripcion());
            step2Request.setPreexistenciacrearpatrFecInicio(step2Entity.getPreexistenciaCFecInicio());
            step2Request.setPreexistenciacrearpatrFecFin(step2Entity.getPreexistenciaCFecFin());
            step2Request.setPreexistenciacrearpatrObservacion(step2Entity.getPreexistenciaCObservacion());
            preexistenciaSrv.crearImplementation(step2Request);
        }

        /* Action 3: IF (EXIST (pBeneficiarioC.EstadoBeneficiarioC) = true) */
        boolean ifCondition3 = repository.assocOperator073(usuarioTACTDESDECOREpBeneficiarioC).equals(true);
        if (ifCondition3) {
            /* Action 30: IF (EstadoBeneficiario <> pBeneficiarioC.EstadoBeneficiarioC) */
            boolean ifCondition30 = !(usuarioTACTDESDECOREpthisUsuario.getEstadoBeneficiario().equalsObject(usuarioTACTDESDECOREpBeneficiarioC.getEstadoBeneficiarioC()));
            if (ifCondition30) {
                /* Action 300: IF (EXIST (EstadoBeneficiario) = true) */
                boolean ifCondition300 = repository.assocOperator074(usuarioTACTDESDECOREpthisUsuario).equals(true);
                if (ifCondition300) {
                    com.keralty.aegenerasolicitud.viewmodel.usuario.siu.DelEstadoBeneficiarioCORERequest step3000Request = new com.keralty.aegenerasolicitud.viewmodel.usuario.siu.DelEstadoBeneficiarioCORERequest();
                    step3000Request.setUsuarioDelEstadoBeneficiarioCOREpthisUsuarioInstance(usuarioTACTDESDECOREpthisUsuario);
                    step3000Request.setUsuarioDelEstadoBeneficiarioCOREpevcEstadoBeneficiarioInstance(usuarioTACTDESDECOREpthisUsuario.getEstadoBeneficiario());
                    usuarioSrv.delEstadoBeneficiarioCOREImplementation(step3000Request, true);
                }
                com.keralty.aegenerasolicitud.viewmodel.usuario.siu.InsEstadoBeneficiarioCORERequest step301Request = new com.keralty.aegenerasolicitud.viewmodel.usuario.siu.InsEstadoBeneficiarioCORERequest();
                step301Request.setUsuarioInsEstadoBeneficiarioCOREpthisUsuarioInstance(usuarioTACTDESDECOREpthisUsuario);
                step301Request.setUsuarioInsEstadoBeneficiarioCOREpevcEstadoBeneficiarioInstance(usuarioTACTDESDECOREpBeneficiarioC.getEstadoBeneficiarioC());
                usuarioSrv.insEstadoBeneficiarioCOREImplementation(step301Request, true);
            }
        }

        com.keralty.aegenerasolicitud.viewmodel.usuario.siu.ModificarAttrRequest step4Request = new com.keralty.aegenerasolicitud.viewmodel.usuario.siu.ModificarAttrRequest();
        step4Request.setUsuariomodificarAttrpthisAttrUsuarioInstance(usuarioTACTDESDECOREpthisUsuario);
        step4Request.setUsuariomodificarAttrpBebeGestante(usuarioTACTDESDECOREpBeneficiarioC.getBeneficiarioCIndicadorBBGestacion());
        step4Request.setUsuariomodificarAttrpcodigoBancoRep(usuarioTACTDESDECOREpBeneficiarioC.getBeneficiarioCCodUsuarioBancoRep());
        step4Request.setUsuariomodificarAttrpFechaAntiguedad(usuarioTACTDESDECOREpthisUsuario.getUsuarioFechaAntiguedadBeneficiario());
        step4Request.setUsuariomodificarAttrpePolizaInternacional(usuarioTACTDESDECOREpthisUsuario.getUsuarioPolizaInternacional());
        usuarioSrv.modificarAttrImplementation(step4Request);

        com.keralty.aegenerasolicitud.viewmodel.usuario.siu.RegistrarDatosCORERequest step5Request = new com.keralty.aegenerasolicitud.viewmodel.usuario.siu.RegistrarDatosCORERequest();
        step5Request.setUsuarioregistrarDatosCOREpthisUsuarioInstance(usuarioTACTDESDECOREpthisUsuario);
        step5Request.setUsuarioregistrarDatosCOREpNumUsuario(usuarioTACTDESDECOREpBeneficiarioC.getBeneficiarioCNumUsuario());
        step5Request.setUsuarioregistrarDatosCOREpRequierExamenMedico(usuarioTACTDESDECOREpBeneficiarioC.getBeneficiarioCRequiereExamenMedico());
        step5Request.setUsuarioregistrarDatosCOREpEstadoMedico(usuarioTACTDESDECOREpBeneficiarioC.getBeneficiarioCEstadoMedico());
        step5Request.setUsuarioregistrarDatosCOREpValorCuotaInscripcion(usuarioTACTDESDECOREpBeneficiarioC.getBeneficiarioCValorCuotaInscripcion());
        step5Request.setUsuarioregistrarDatosCOREpTienePreexistencias(usuarioTACTDESDECOREpBeneficiarioC.getBeneficiarioCTienePreexistencias());
        step5Request.setUsuarioregistrarDatosCOREpFechaInicionVigencia(usuarioTACTDESDECOREpBeneficiarioC.getBeneficiarioCFechaInicioVigencia());
        step5Request.setUsuarioregistrarDatosCOREpFechaFinVigencia(usuarioTACTDESDECOREpBeneficiarioC.getBeneficiarioCFechaFinVigencia());
        usuarioSrv.registrarDatosCOREImplementation(step5Request);

        response.setInstance(getByOID(instance.getOid()));
        response.setInstanceSuppInfo(instance.buildICUsuario());

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
            SQLWhereOperandPathInstance operand113 = new SQLWhereOperandPathInstance(UsuarioConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AGENTE, FuncionarioConstants.CLASS_NAME);
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
            SQLWhereOperandPathAttribute operand115 = new SQLWhereOperandPathAttribute(UsuarioConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AGENTE + "." + FuncionarioConstants.ATTR_NAME_FUNCIONARIOCODJEFE, Constants.Type.STRING.getTypeName());
            comparison4.setFirstOperand(operand115);
            
            // Operand 2
            comparison4.setSecondOperand(new SQLWhereOperandLiteral(agentInstance.getFuncionarioUsuario(), Constants.Type.STRING.getTypeName()));
            comparison1.addOperand(comparison4);
        
        disjunction.addOperand(comparison1);
    }

    }


    private void suppInfo4ICUsuario(String searchText, SQLSelect select) {
        try {
            SQLWhereDisjunction disjunction = new SQLWhereDisjunction();

            SQLWhereComparison comparison1 = new SQLWhereComparison(select);
            comparison1.setOperator("LIKE");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(UsuarioConstants.ROLE_NAME_TIPOIDENTIFICACION + "." + TipoIdentificacionConstants.ATTR_NAME_TIPOIDENTIFICACIONNOMBRECORTO, Constants.Type.STRING.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(searchText, Constants.Type.STRING.getTypeName()));
            disjunction.addComparison(comparison1);

            SQLWhereComparison comparison2 = new SQLWhereComparison(select);
            comparison2.setOperator("LIKE");
            comparison2.setFirstOperand(new SQLWhereOperandPathAttribute(UsuarioConstants.ATTR_NAME_USUARIONUMIDENTIFICACION, Constants.Type.STRING.getTypeName()));
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(searchText, Constants.Type.STRING.getTypeName()));
            disjunction.addComparison(comparison2);

            SQLWhereConjunction conjunction = new SQLWhereConjunction();
            conjunction.addDisjunction(disjunction);
            select.addConjunction(conjunction);
        } catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
        }
    }

    @Override
    protected void applyOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        applyOCOCPorId(queryRequest, select);
        applyDSItemSort(queryRequest, select);
        applyDefaultOrderCriteria(queryRequest, select);
    }

    private void applyDefaultOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        if (queryRequest.getOrderCriteriaName() != null && !queryRequest.getOrderCriteriaName().isBlank())
            return;
        if (queryRequest.getDsItemSort() != null && !queryRequest.getDsItemSort().isBlank())
            return;
        select.getOrderByPart().addItem(Constants.normalizePath(UsuarioConstants.CLASS_NAME, UsuarioConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_FIELD_AESOLICITUDESIDSOLICITUD, false), true);
        select.getOrderByPart().addItem(Constants.normalizePath(UsuarioConstants.CLASS_NAME, UsuarioConstants.ATTR_FIELD_USUARIOIDENTIFICADOR, false), true);
    }


    private void applyOCOCPorId(QueryRequest queryRequest, SQLSelect select) {
        if ("OC_PorId".equalsIgnoreCase(queryRequest.getOrderCriteriaName())) {
            select.getOrderByPart().addItem(Constants.normalizePath(UsuarioConstants.CLASS_NAME, UsuarioConstants.ATTR_NAME_USUARIOIDENTIFICADOR, false), true);
        }
    }
    
    @Override
    protected AbstractAppEntity buildRelatedInstance(String relatedInstanceJsonOid, String relatedInstancePathFirstItem, String remainingPath) {
        AbstractAppEntity relatedInstance = null;
        if (relatedInstancePathFirstItem.isEmpty()) {
            relatedInstance = buildUsuarioRelatedInstance(relatedInstanceJsonOid);
        }
        if (UsuarioConstants.ROLE_NAME_CUESTIONARIOP.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildUsuarioRelatedInstance4CuestionarioP(relatedInstanceJsonOid, remainingPath);
        }
        if (UsuarioConstants.ROLE_NAME_ANEXOMAT.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildUsuarioRelatedInstance4AnexoMat(relatedInstanceJsonOid, remainingPath);
        }
        if (UsuarioConstants.ROLE_NAME_ANEXONEO.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildUsuarioRelatedInstance4AnexoNeo(relatedInstanceJsonOid, remainingPath);
        }
        if (UsuarioConstants.ROLE_NAME_PREEXISTENCIAS.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildUsuarioRelatedInstance4Preexistencia(relatedInstanceJsonOid, remainingPath);
        }
        if (UsuarioConstants.ROLE_NAME_SOLICITUD.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildUsuarioRelatedInstance4Solicitud(relatedInstanceJsonOid, remainingPath);
        }
        if (UsuarioConstants.ROLE_NAME_USUARIOPERSONA.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildUsuarioRelatedInstance4Persona(relatedInstanceJsonOid, remainingPath);
        }
        if (UsuarioConstants.ROLE_NAME_EPS.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildUsuarioRelatedInstance4Eps(relatedInstanceJsonOid, remainingPath);
        }
        if (UsuarioConstants.ROLE_NAME_PARENTESCO.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildUsuarioRelatedInstance4Parentesco(relatedInstanceJsonOid, remainingPath);
        }
        if (UsuarioConstants.ROLE_NAME_COMPANYIA.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildUsuarioRelatedInstance4Companyia(relatedInstanceJsonOid, remainingPath);
        }
        if (UsuarioConstants.ROLE_NAME_TIPOIDENTIFICACION.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildUsuarioRelatedInstance4TipoIdentificacion(relatedInstanceJsonOid, remainingPath);
        }
        if (UsuarioConstants.ROLE_NAME_ESTADOBENEFICIARIO.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildUsuarioRelatedInstance4EstadoBeneficiario(relatedInstanceJsonOid, remainingPath);
        }
        return relatedInstance;
    }
    
    private AbstractAppEntity buildUsuarioRelatedInstance(String relatedInstanceJsonOid) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		return new Usuario(mapper.readValue(relatedInstanceJsonOid, UsuarioOid.class));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildUsuarioRelatedInstance4CuestionarioP(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new CuestionarioP(mapper.readValue(relatedInstanceJsonOid, CuestionarioPOid.class)): CuestionarioP.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildUsuarioRelatedInstance4AnexoMat(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new AnexoMat(mapper.readValue(relatedInstanceJsonOid, AnexoMatOid.class)): AnexoMat.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildUsuarioRelatedInstance4AnexoNeo(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new AnexoNeo(mapper.readValue(relatedInstanceJsonOid, AnexoNeoOid.class)): AnexoNeo.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildUsuarioRelatedInstance4Preexistencia(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Preexistencia(mapper.readValue(relatedInstanceJsonOid, PreexistenciaOid.class)): Preexistencia.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildUsuarioRelatedInstance4Solicitud(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Solicitud(mapper.readValue(relatedInstanceJsonOid, SolicitudOid.class)): Solicitud.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildUsuarioRelatedInstance4Persona(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Persona(mapper.readValue(relatedInstanceJsonOid, PersonaOid.class)): Persona.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildUsuarioRelatedInstance4Eps(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Eps(mapper.readValue(relatedInstanceJsonOid, EpsOid.class)): Eps.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildUsuarioRelatedInstance4Parentesco(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Parentesco(mapper.readValue(relatedInstanceJsonOid, ParentescoOid.class)): Parentesco.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildUsuarioRelatedInstance4Companyia(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Companyia(mapper.readValue(relatedInstanceJsonOid, CompanyiaOid.class)): Companyia.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildUsuarioRelatedInstance4TipoIdentificacion(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new TipoIdentificacion(mapper.readValue(relatedInstanceJsonOid, TipoIdentificacionOid.class)): TipoIdentificacion.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    private AbstractAppEntity buildUsuarioRelatedInstance4EstadoBeneficiario(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new EstadoBeneficiario(mapper.readValue(relatedInstanceJsonOid, EstadoBeneficiarioOid.class)): EstadoBeneficiario.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
    		return null;
    	}
    }

    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            UsuarioOid oid = new UsuarioOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(UsuarioConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_FIELD_AESOLICITUDESIDSOLICITUD, Constants.Type.AUTONUMERIC.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getAesolicitudesidsolicitud(), Constants.Type.AUTONUMERIC.getTypeName()));
            sql.addComparison(comparison1);
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator("=");
            comparison2.setFirstOperand(new SQLWhereOperandPathAttribute(UsuarioConstants.ATTR_FIELD_USUARIOIDENTIFICADOR, Constants.Type.AUTONUMERIC.getTypeName()));
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(oid.getUsuarioIdentificador(), Constants.Type.AUTONUMERIC.getTypeName()));
            sql.addComparison(comparison2);
        } catch (Exception e) {
            LoggerFactory.getLogger(UsuarioService.class).error(e.getMessage());
        }
    }

}
