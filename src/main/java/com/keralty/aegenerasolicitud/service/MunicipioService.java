package com.keralty.aegenerasolicitud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralty.aegenerasolicitud.exception.ModelException;
import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.DepartamentoConstants;
import com.keralty.aegenerasolicitud.global.MunicipioConstants;
import com.keralty.aegenerasolicitud.global.PersonaConstants;
import com.keralty.aegenerasolicitud.global.SolicitudConstants;
import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.global.UsuarioConstants;
import com.keralty.aegenerasolicitud.persistence.AbstractAppEntity;
import com.keralty.aegenerasolicitud.persistence.Contratante;
import com.keralty.aegenerasolicitud.persistence.ContratoC;
import com.keralty.aegenerasolicitud.persistence.Departamento;
import com.keralty.aegenerasolicitud.persistence.Direccion;
import com.keralty.aegenerasolicitud.persistence.DireccionC;
import com.keralty.aegenerasolicitud.persistence.DirSucursalC;
import com.keralty.aegenerasolicitud.persistence.FamiliaC;
import com.keralty.aegenerasolicitud.persistence.InstitucionMatNeo;
import com.keralty.aegenerasolicitud.persistence.Municipio;
import com.keralty.aegenerasolicitud.persistence.oid.ContratanteOid;
import com.keralty.aegenerasolicitud.persistence.oid.ContratoCOid;
import com.keralty.aegenerasolicitud.persistence.oid.DepartamentoOid;
import com.keralty.aegenerasolicitud.persistence.oid.DireccionCOid;
import com.keralty.aegenerasolicitud.persistence.oid.DireccionOid;
import com.keralty.aegenerasolicitud.persistence.oid.DirSucursalCOid;
import com.keralty.aegenerasolicitud.persistence.oid.FamiliaCOid;
import com.keralty.aegenerasolicitud.persistence.oid.InstitucionMatNeoOid;
import com.keralty.aegenerasolicitud.persistence.oid.MunicipioOid;
import com.keralty.aegenerasolicitud.persistence.oid.PersonaCOid;
import com.keralty.aegenerasolicitud.persistence.oid.PersonaOid;
import com.keralty.aegenerasolicitud.persistence.oid.SolicitudOid;
import com.keralty.aegenerasolicitud.persistence.oid.TitularOid;
import com.keralty.aegenerasolicitud.persistence.oid.UsuarioOid;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.Titular;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import com.keralty.aegenerasolicitud.repository.MunicipioJpaRepository;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereComparison;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereConjunction;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereDisjunction;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereNegation;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandLiteral;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandNull;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandOVParameter;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandPathAttribute;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandPathInstance;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandSubQuery;
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
public class MunicipioService extends AbstractService {

    MunicipioJpaRepository repository;

    MunicipioService municipioSrv;

    DireccionCService direccionCSrv;

    ContratoCService contratoCSrv;

    FamiliaCService familiaCSrv;

    DepartamentoService departamentoSrv;

    SolicitudService solicitudSrv;

    UsuarioService usuarioSrv;

    PersonaService personaSrv;

    PersonaCService personaCSrv;

    DireccionService direccionSrv;

    ContratanteService contratanteSrv;

    DirSucursalCService dirSucursalCSrv;

    TitularService titularSrv;

    InstitucionMatNeoService institucionMatNeoSrv;

    UserFunctions userFunctionsSrv;

    @Inject
    public MunicipioService(
            MunicipioService municipioSrv,
            DireccionCService direccionCSrv,
            ContratoCService contratoCSrv,
            FamiliaCService familiaCSrv,
            DepartamentoService departamentoSrv,
            SolicitudService solicitudSrv,
            UsuarioService usuarioSrv,
            PersonaService personaSrv,
            PersonaCService personaCSrv,
            DireccionService direccionSrv,
            ContratanteService contratanteSrv,
            DirSucursalCService dirSucursalCSrv,
            TitularService titularSrv,
            InstitucionMatNeoService institucionMatNeoSrv,
            UserFunctions userFunctionsSrv,
            MunicipioJpaRepository repository) {
        this.municipioSrv = municipioSrv;
        this.direccionCSrv = direccionCSrv;
        this.contratoCSrv = contratoCSrv;
        this.familiaCSrv = familiaCSrv;
        this.departamentoSrv = departamentoSrv;
        this.solicitudSrv = solicitudSrv;
        this.usuarioSrv = usuarioSrv;
        this.personaSrv = personaSrv;
        this.personaCSrv = personaCSrv;
        this.direccionSrv = direccionSrv;
        this.contratanteSrv = contratanteSrv;
        this.dirSucursalCSrv = dirSucursalCSrv;
        this.titularSrv = titularSrv;
        this.institucionMatNeoSrv = institucionMatNeoSrv;
        this.userFunctionsSrv = userFunctionsSrv;
        this.repository = repository;
    }

    public List<Map<String, Object>> solveQuery(QueryRequest request) {
        return solveGenericQuery(request, MunicipioConstants.CLASS_NAME);
    }

    private List<Municipio> solveMunicipioTypedQuery(QueryRequest request) {
        return repository.solveQuery(processQueryRequest(request, MunicipioConstants.CLASS_NAME), request.getPageSize(), request.getPageNumber());
    }
    
    private int solveMunicipioTypedCountQuery(QueryRequest request) {
        return repository.solveCountQuery(processCountQueryRequest(request, MunicipioConstants.CLASS_NAME));
    }

    /*
     * Returns the population of class Municipio
     *
     * @return Population of class Municipio
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Municipio> get() {
        List<Municipio> results = new java.util.ArrayList<>();
        repository.findAll().forEach(results::add);
        return results;
    }
    
    /*
     * Gets the preloaded list of objects of class Municipio
     *
     * @param queryRequest Request object that dictates the search conditions to 
     * retrieve objects
     *
     * @return Preloaded list of objects of class Municipio
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PreloadViewModel> getPreload(QueryRequest queryRequest) {
        List<PreloadViewModel> results = new ArrayList<>();
        List<Municipio> instances = get(queryRequest);
        if(MunicipioConstants.DSICMUNICIPIO.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildICMunicipio())).collect(Collectors.toList());
        }
        if(MunicipioConstants.DSDSMUNICIPIOPIU.equals(queryRequest.getDisplaySetName())) {
            results = instances.stream().map(x -> new PreloadViewModel(x.getOid().toJson(), x.buildDSMunicipioPIU())).collect(Collectors.toList());
        }
        return results;
    }
    /*
     * Returns the population of class Municipio that matches certain search criteria
     *
     * @param queryRequest Search criteria
     *
     * @return Population of class Municipio matching the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Municipio> get(QueryRequest queryRequest) {
        return solveMunicipioTypedQuery(queryRequest);
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
            return new PopulationResponseViewModel(solveMunicipioTypedQuery(queryRequest).stream()
                    .map(instance -> buildDynamicObject(instance, queryRequest.getDisplaySetItems()))
                    .collect(Collectors.toList()), queryRequest.getPageSize() != 0 ? solveMunicipioTypedCountQuery(queryRequest) : 0);
        } else {
            return new PreloadResponseViewModel(solveMunicipioTypedQuery(queryRequest).stream()
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
        return new PopulationResponseViewModel(solveMunicipioTypedQuery(queryRequest).stream()
                .map(instance -> buildDynamicObject(instance, displaySetItems))
                .collect(Collectors.toList()));
    }

    /*
     * Returns an instance of Municipio by ID
     *
     * @param municipioCodigo Identification field municipioCodigo
     *
     * @return Instance of Municipio
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Municipio getById(Long municipioCodigo) {
        return getByOID(new MunicipioOid(municipioCodigo));
    }
    
    /*
     * Returns an instance of Municipio by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Municipio
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Municipio getByOID(MunicipioOid oid) {
        Optional<Municipio> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of Municipio by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of Municipio
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public Municipio getByOIDWithHV(MunicipioOid oid) {
        Optional<Municipio> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<Municipio> getInstanceByOid(MunicipioOid oid) {
        return oid != null && oid.getMunicipioCodigo() != null
               ? repository.findById(oid.getMunicipioCodigo())
               : Optional.empty();
    }

    /*
     * Returns an instance of Municipio by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of Municipio
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Municipio getByJsonOid(String jsonOid) {
        MunicipioOid oid = new MunicipioOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }

    /*
     * Returns the list of instances of Municipio related to a given instance through 
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Municipio
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Municipio> getByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        QueryRequest queryRequest = new QueryRequest(new FilterRequest(Constants.FILTER_BY_RELATED,
                Arrays.asList(new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_PATH, relatedInstancePath),
                        new FilterVariableRequest(Constants.FILTER_BY_RELATED_FVAR_OID, relatedInstance.getOid().toJson()))));
        return solveMunicipioTypedQuery(queryRequest);
    }

    /*
     * Returns the list of instances of Municipio related to a given instance through
     * a given path
     *
     * @param relatedInstance Related instance
     *
     * @param relatedInstancePath Related instance path
     *
     * @return List of instances of Municipio
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Municipio> getInstancesByRelated(AbstractAppEntity relatedInstance, String relatedInstancePath) {
        return getByRelated(relatedInstance, relatedInstancePath);
    }

    /*
     * Returns a set of displayset items for an instance of Municipio
     *
     * @param oid Object IDentifier
     *
     * @param displaySetItems List of comma-separated displayset items
     *
     * @return Set of displayset items for an instance of Municipio
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public InstanceResponseViewModel getMunicipioDynamic(MunicipioOid oid, String displaySetItems) {
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
    public String getSuppInfo(MunicipioOid oid, String dsName) {
        Municipio instance = getByOIDWithHV(oid);
        return (instance != null ? getSuppInfo(instance, dsName): "");
    }

    private String getSuppInfo(Municipio instance, String dsName) {
        if (instance == null || instance.isNull()) return "";
        if (MunicipioConstants.DSICMUNICIPIO.equals(dsName))
            return instance.buildICMunicipio();
        return dsName.isEmpty() ? instance.getOid().toJson() : "";
    }

    public static PreloadViewModel buildPreloadViewModel(Municipio instance, String dsName) {
        if (MunicipioConstants.DSICMUNICIPIO.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildICMunicipio());
        if (MunicipioConstants.DSDSMUNICIPIOPIU.equals(dsName))
            return new PreloadViewModel(instance.getOid().toJson(), instance.buildDSMunicipioPIU());
        return new PreloadViewModel(instance.getOid().toJson(), "");
    }


    @Override
    protected void applyQueryFilters(FilterRequest filterRequest, SQLSelect sql) throws SystemException {
        if (MunicipioConstants.FILTER_NAME_FMUNICIPIOVARIOS.equalsIgnoreCase(filterRequest.getName()))
            fMunicipioVarios(filterRequest, sql);
        if (MunicipioConstants.FILTER_NAME_IFMUNICIPIOCODIGO.equalsIgnoreCase(filterRequest.getName()))
            iFMunicipioCodigo(filterRequest, sql);
        if (MunicipioConstants.FILTER_NAME_IFMUNICIPIOCODIGOLEGAL.equalsIgnoreCase(filterRequest.getName()))
            iFMunicipioCodigoLegal(filterRequest, sql);
    }

    @Override
    protected void applyNavigationalFilters(FilterRequest filterRequest, SQLSelect sql) throws SystemException {
        if ("Persona_TADDUSUARIOASOL_pt_p_agrMunicipioNacimiento_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1431886233600805FiltNav12(filterRequest, sql);
        if ("Persona_TADDUSUARIOASOL_pt_p_agrMunicipio_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1431886233600805FiltNav11(filterRequest, sql);
        if ("Persona_TADDUSUARIOASOL_pt_p_agrMunicipio_NavFilter1".equalsIgnoreCase(filterRequest.getName()))
            clas1431886233600805FiltNav15(filterRequest, sql);
        if ("Solicitud_TINDICARCONTRATANTE2_pt_p_evcMunicipioNac_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav25(filterRequest, sql);
        if ("Solicitud_TINDICARCONTRATANTE2_pt_p_evcMunicipio_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav26(filterRequest, sql);
        if ("Solicitud_TINDICARCONTRATANTE2_pt_p_evcMunicipio_NavFilter1".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav33(filterRequest, sql);
        if ("Solicitud_TINDICARCONTRATANTE2_pt_pDCMunicipio_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav27(filterRequest, sql);
        if ("Solicitud_TINDICARTITULAR2_pt_p_agrMunicipioNacimiento_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav29(filterRequest, sql);
        if ("Solicitud_TINDICARTITULAR2_pt_p_agrMunicipio_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav28(filterRequest, sql);
        if ("Solicitud_TINDICARTITULAR2_pt_p_agrMunicipio_NavFilter1".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav34(filterRequest, sql);
        if ("Solicitud_TINDICARTITULAR2_ptDCMunicipio_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav35(filterRequest, sql);
        if ("Solicitud_TMODDIRSCONTRATANTE_pt_p_evcMunicipio_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav39(filterRequest, sql);
        if ("Solicitud_TMODDIRSCONTRATANTE_pt_p_evcMunicipio_NavFilter1".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav43(filterRequest, sql);
        if ("Solicitud_TMODDIRSCONTRATANTE_pt_pDCMunicipio_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav40(filterRequest, sql);
        if ("Solicitud_TMODDIRSCONTRATANTE_pt_pDCMunicipio_NavFilter1".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav44(filterRequest, sql);
        if ("Solicitud_TMODDIRSTITULAR_pt_p_agrMunicipio_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav41(filterRequest, sql);
        if ("Solicitud_TMODDIRSTITULAR_pt_p_agrMunicipio_NavFilter1".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav45(filterRequest, sql);
        if ("Solicitud_TMODDIRSTITULAR_ptDCMunicipio_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav42(filterRequest, sql);
        if ("Solicitud_TMODDIRSTITULAR_ptDCMunicipio_NavFilter1".equalsIgnoreCase(filterRequest.getName()))
            clas1431957012480960FiltNav46(filterRequest, sql);
        if ("Usuario_TMODUSUARIO_pt_p_evcMunicipioNac_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1432633737216826FiltNav9(filterRequest, sql);
        if ("Usuario_TMODUSUARIO_pt_p_evcMunicipio_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1432633737216826FiltNav8(filterRequest, sql);
        if ("Usuario_TMODUSUARIO_pt_p_evcMunicipio_NavFilter1".equalsIgnoreCase(filterRequest.getName()))
            clas1432633737216826FiltNav10(filterRequest, sql);
        if ("FamiliaNovedad_TSETDIRCORR_pt_p_agrMunicipio_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1581424574464890FiltNav6(filterRequest, sql);
        if ("FamiliaNovedad_TADDTITULAR_pt_p_agrMunicipioNacimiento_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1581424574464890FiltNav4(filterRequest, sql);
        if ("FamiliaNovedad_TADDTITULAR_pt_p_agrMunicipio_NavFilter0".equalsIgnoreCase(filterRequest.getName()))
            clas1581424574464890FiltNav3(filterRequest, sql);
    }

    @Override
    protected void applyServiceSIFilters(FilterRequest filterRequest, SQLSelect sql) {
        if ("SuppInfo_IC_Municipio".equals(filterRequest.getName()))
            suppInfo4ICMunicipio(filterRequest.getFilterVariable("searchText").toString(), sql);
    }


    private void fMunicipioVarios(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vDescripcion = filterRequest.hasFilterVariable(MunicipioConstants.VAR_NAME_FMUNICIPIOVARIOS_VDESCRIPCION) ? (String) filterRequest.getFilterVariable(MunicipioConstants.VAR_NAME_FMUNICIPIOVARIOS_VDESCRIPCION, FilterVariableRequest.STRING) : null;
        Boolean vEsValidoEPS = filterRequest.hasFilterVariable(MunicipioConstants.VAR_NAME_FMUNICIPIOVARIOS_VESVALIDOEPS) ? (Boolean) filterRequest.getFilterVariable(MunicipioConstants.VAR_NAME_FMUNICIPIOVARIOS_VESVALIDOEPS, FilterVariableRequest.BOOL) : null;
        Boolean vAplicaIVA = filterRequest.hasFilterVariable(MunicipioConstants.VAR_NAME_FMUNICIPIOVARIOS_VAPLICAIVA) ? (Boolean) filterRequest.getFilterVariable(MunicipioConstants.VAR_NAME_FMUNICIPIOVARIOS_VAPLICAIVA, FilterVariableRequest.BOOL) : null;
        Departamento voDepartamento = filterRequest.hasFilterVariable(MunicipioConstants.VAR_NAME_FMUNICIPIOVARIOS_VODEPARTAMENTO) ? departamentoSrv.getByOID(new DepartamentoOid().fromJsonString(toJson(filterRequest.getFilterVariable(MunicipioConstants.VAR_NAME_FMUNICIPIOVARIOS_VODEPARTAMENTO)))) : null;

        // Descripcion LIKE "%" + vDescripcion
        if (vDescripcion != null) {
            // 'like' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("like");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(MunicipioConstants.ATTR_NAME_MUNICIPIODESCRIPCION, Constants.Type.STRING.getTypeName());
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            comparison1.setSecondOperand(new SQLWhereOperandLiteral("%" + vDescripcion, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison1);

        }        

        // EsValidoEPS = vEsValidoEPS
        if (vEsValidoEPS != null) {
            // '=' Comparison
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand113 = new SQLWhereOperandPathAttribute(MunicipioConstants.ATTR_NAME_MUNICIPIOESVALIDOEPS, Constants.Type.BOOL.getTypeName());
            comparison2.setFirstOperand(operand113);
            
            // Operand 2
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(vEsValidoEPS, Constants.Type.BOOL.getTypeName()));
            conjunction.addOperand(comparison2);

        }        

        // AplicaIVA = vAplicaIVA
        if (vAplicaIVA != null) {
            // '=' Comparison
            SQLWhereComparison comparison3 = new SQLWhereComparison(sql);
            comparison3.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand114 = new SQLWhereOperandPathAttribute(MunicipioConstants.ATTR_NAME_MUNICIPIOAPLICAIVA, Constants.Type.BOOL.getTypeName());
            comparison3.setFirstOperand(operand114);
            
            // Operand 2
            comparison3.setSecondOperand(new SQLWhereOperandLiteral(vAplicaIVA, Constants.Type.BOOL.getTypeName()));
            conjunction.addOperand(comparison3);

        }        

        // Departamento = voDepartamento
        if (voDepartamento != null) {
            // '=' Comparison
            SQLWhereComparison comparison4 = new SQLWhereComparison(sql);
            comparison4.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand115 = new SQLWhereOperandPathInstance(MunicipioConstants.ROLE_NAME_DEPARTAMENTO, DepartamentoConstants.CLASS_NAME);
            comparison4.setFirstOperand(operand115);
            
            // Operand 2
            SQLWhereOperandOVParameter operand226 = new SQLWhereOperandOVParameter(MunicipioConstants.VAR_NAME_FMUNICIPIOVARIOS_VODEPARTAMENTO, DepartamentoConstants.CLASS_NAME, DepartamentoConstants.CLASS_NAME, voDepartamento.getOid(), MunicipioConstants.VAR_NAME_FMUNICIPIOVARIOS_VODEPARTAMENTO);
            comparison4.setSecondOperand(operand226);
            conjunction.addOperand(comparison4);

        }        

        sql.addConjunction(conjunction);
    }

    private void iFMunicipioCodigo(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Long vCodigo = filterRequest.hasFilterVariable(MunicipioConstants.VAR_NAME_IFMUNICIPIOCODIGO_VCODIGO) ? (Long) filterRequest.getFilterVariable(MunicipioConstants.VAR_NAME_IFMUNICIPIOCODIGO_VCODIGO, FilterVariableRequest.INT) : null;

        // Codigo = vCodigo
        if (vCodigo != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(MunicipioConstants.ATTR_NAME_MUNICIPIOCODIGO, Constants.Type.INT.getTypeName());
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(vCodigo, Constants.Type.INT.getTypeName()));
            conjunction.addOperand(comparison1);

        }        

        sql.addConjunction(conjunction);
    }

    private void iFMunicipioCodigoLegal(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        String vfCodLegal = filterRequest.hasFilterVariable(MunicipioConstants.VAR_NAME_IFMUNICIPIOCODIGOLEGAL_VFCODLEGAL) ? (String) filterRequest.getFilterVariable(MunicipioConstants.VAR_NAME_IFMUNICIPIOCODIGOLEGAL_VFCODLEGAL, FilterVariableRequest.STRING) : null;

        // CodigoLegal = vfCodLegal
        if (vfCodLegal != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(MunicipioConstants.ATTR_NAME_MUNICIPIOCODIGOLEGAL, Constants.Type.STRING.getTypeName());
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(vfCodLegal, Constants.Type.STRING.getTypeName()));
            conjunction.addOperand(comparison1);

        }        

        sql.addConjunction(conjunction);
    }

    private void clas1431886233600805FiltNav12(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Departamento personaTADDUSUARIOASOLptpagrDepartamentoNacimiento = filterRequest.hasFilterVariable(PersonaConstants.ARG_NAME_TADDUSUARIOASOL_PERSONATADDUSUARIOASOLPTPAGRDEPARTAMENTONACIMIENTO) ? departamentoSrv.getByOID(new DepartamentoOid().fromJsonString(toJson(filterRequest.getFilterVariable(PersonaConstants.ARG_NAME_TADDUSUARIOASOL_PERSONATADDUSUARIOASOLPTPAGRDEPARTAMENTONACIMIENTO)))) : null;

        // Departamento = ptpagrDepartamentoNacimiento
        if (personaTADDUSUARIOASOLptpagrDepartamentoNacimiento != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand112 = new SQLWhereOperandPathInstance(MunicipioConstants.ROLE_NAME_DEPARTAMENTO, DepartamentoConstants.CLASS_NAME);
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            SQLWhereOperandOVParameter operand223 = new SQLWhereOperandOVParameter(PersonaConstants.ARG_NAME_TADDUSUARIOASOL_PERSONATADDUSUARIOASOLPTPAGRDEPARTAMENTONACIMIENTO, DepartamentoConstants.CLASS_NAME, DepartamentoConstants.CLASS_NAME, personaTADDUSUARIOASOLptpagrDepartamentoNacimiento.getOid(), PersonaConstants.ARG_NAME_TADDUSUARIOASOL_PERSONATADDUSUARIOASOLPTPAGRDEPARTAMENTONACIMIENTO);
            comparison1.setSecondOperand(operand223);
            conjunction.addOperand(comparison1);

        }        
        sql.addConjunction(conjunction);
    }

    private void clas1431886233600805FiltNav11(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Departamento personaTADDUSUARIOASOLptpagrDepartamento = filterRequest.hasFilterVariable(PersonaConstants.ARG_NAME_TADDUSUARIOASOL_PERSONATADDUSUARIOASOLPTPAGRDEPARTAMENTO) ? departamentoSrv.getByOID(new DepartamentoOid().fromJsonString(toJson(filterRequest.getFilterVariable(PersonaConstants.ARG_NAME_TADDUSUARIOASOL_PERSONATADDUSUARIOASOLPTPAGRDEPARTAMENTO)))) : null;

        // Departamento = ptpagrDepartamento
        if (personaTADDUSUARIOASOLptpagrDepartamento != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand112 = new SQLWhereOperandPathInstance(MunicipioConstants.ROLE_NAME_DEPARTAMENTO, DepartamentoConstants.CLASS_NAME);
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            SQLWhereOperandOVParameter operand223 = new SQLWhereOperandOVParameter(PersonaConstants.ARG_NAME_TADDUSUARIOASOL_PERSONATADDUSUARIOASOLPTPAGRDEPARTAMENTO, DepartamentoConstants.CLASS_NAME, DepartamentoConstants.CLASS_NAME, personaTADDUSUARIOASOLptpagrDepartamento.getOid(), PersonaConstants.ARG_NAME_TADDUSUARIOASOL_PERSONATADDUSUARIOASOLPTPAGRDEPARTAMENTO);
            comparison1.setSecondOperand(operand223);
            conjunction.addOperand(comparison1);

        }        
        sql.addConjunction(conjunction);
    }

    private void clas1431886233600805FiltNav15(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Boolean personaTADDUSUARIOASOLptpTieneEPSSanitas = filterRequest.hasFilterVariable(PersonaConstants.ARG_NAME_TADDUSUARIOASOL_PERSONATADDUSUARIOASOLPTPTIENEEPSSANITAS) ? (Boolean) filterRequest.getFilterVariable(PersonaConstants.ARG_NAME_TADDUSUARIOASOL_PERSONATADDUSUARIOASOLPTPTIENEEPSSANITAS, FilterVariableRequest.BOOL) : null;

        // ptpTieneEPSSanitas = false OR EsValidoEPS = true
        if (personaTADDUSUARIOASOLptpTieneEPSSanitas != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison1 = new SQLWhereDisjunction();
        
            // Operand 1
                    // '=' Comparison
                    SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
                    comparison2.setOperator("=");
                    
                    // Operand 1
                    comparison2.setFirstOperand(new SQLWhereOperandLiteral(personaTADDUSUARIOASOLptpTieneEPSSanitas, Constants.Type.BOOL.getTypeName()));
                    
                    // Operand 2
                    comparison2.setSecondOperand(new SQLWhereOperandLiteral(Boolean.FALSE, Constants.Type.BOOL.getTypeName()));
                    comparison1.addOperand(comparison2);
        
            // Operand 2
                    // '=' Comparison
                    SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
                    comparison5.setOperator("=");
                    
                    // Operand 1
                    SQLWhereOperandPathAttribute operand116 = new SQLWhereOperandPathAttribute(MunicipioConstants.ATTR_NAME_MUNICIPIOESVALIDOEPS, Constants.Type.BOOL.getTypeName());
                    comparison5.setFirstOperand(operand116);
                    
                    // Operand 2
                    comparison5.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
                    comparison1.addOperand(comparison5);
            
            conjunction.addOperand(comparison1);
        }

        }        
        sql.addConjunction(conjunction);
    }

    private void clas1431957012480960FiltNav25(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Departamento solicitudTINDICARCONTRATANTE2ptpevcDepartamentoNac = filterRequest.hasFilterVariable(SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTPEVCDEPARTAMENTONAC) ? departamentoSrv.getByOID(new DepartamentoOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTPEVCDEPARTAMENTONAC)))) : null;

        // Departamento = ptpevcDepartamentoNac
        if (solicitudTINDICARCONTRATANTE2ptpevcDepartamentoNac != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand112 = new SQLWhereOperandPathInstance(MunicipioConstants.ROLE_NAME_DEPARTAMENTO, DepartamentoConstants.CLASS_NAME);
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            SQLWhereOperandOVParameter operand223 = new SQLWhereOperandOVParameter(SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTPEVCDEPARTAMENTONAC, DepartamentoConstants.CLASS_NAME, DepartamentoConstants.CLASS_NAME, solicitudTINDICARCONTRATANTE2ptpevcDepartamentoNac.getOid(), SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTPEVCDEPARTAMENTONAC);
            comparison1.setSecondOperand(operand223);
            conjunction.addOperand(comparison1);

        }        
        sql.addConjunction(conjunction);
    }

    private void clas1431957012480960FiltNav26(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Departamento solicitudTINDICARCONTRATANTE2ptpevcDepartamento = filterRequest.hasFilterVariable(SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTPEVCDEPARTAMENTO) ? departamentoSrv.getByOID(new DepartamentoOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTPEVCDEPARTAMENTO)))) : null;

        // Departamento = ptpevcDepartamento
        if (solicitudTINDICARCONTRATANTE2ptpevcDepartamento != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand112 = new SQLWhereOperandPathInstance(MunicipioConstants.ROLE_NAME_DEPARTAMENTO, DepartamentoConstants.CLASS_NAME);
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            SQLWhereOperandOVParameter operand223 = new SQLWhereOperandOVParameter(SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTPEVCDEPARTAMENTO, DepartamentoConstants.CLASS_NAME, DepartamentoConstants.CLASS_NAME, solicitudTINDICARCONTRATANTE2ptpevcDepartamento.getOid(), SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTPEVCDEPARTAMENTO);
            comparison1.setSecondOperand(operand223);
            conjunction.addOperand(comparison1);

        }        
        sql.addConjunction(conjunction);
    }

    private void clas1431957012480960FiltNav33(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Boolean solicitudTINDICARCONTRATANTE2pTitularBeneficios = filterRequest.hasFilterVariable(SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTITULARBENEFICIOS) ? (Boolean) filterRequest.getFilterVariable(SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTITULARBENEFICIOS, FilterVariableRequest.BOOL) : null;
        Boolean solicitudTINDICARCONTRATANTE2ptTieneEPSSanitas = filterRequest.hasFilterVariable(SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTTIENEEPSSANITAS) ? (Boolean) filterRequest.getFilterVariable(SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTTIENEEPSSANITAS, FilterVariableRequest.BOOL) : null;

        // pTitularBeneficios = false OR ptTieneEPSSanitas = false OR EsValidoEPS = true
        if (solicitudTINDICARCONTRATANTE2pTitularBeneficios != null && solicitudTINDICARCONTRATANTE2ptTieneEPSSanitas != null) {
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
                            comparison3.setFirstOperand(new SQLWhereOperandLiteral(solicitudTINDICARCONTRATANTE2pTitularBeneficios, Constants.Type.BOOL.getTypeName()));
                            
                            // Operand 2
                            comparison3.setSecondOperand(new SQLWhereOperandLiteral(Boolean.FALSE, Constants.Type.BOOL.getTypeName()));
                            comparison2.addOperand(comparison3);
                
                    // Operand 2
                            // '=' Comparison
                            SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
                            comparison6.setOperator("=");
                            
                            // Operand 1
                            comparison6.setFirstOperand(new SQLWhereOperandLiteral(solicitudTINDICARCONTRATANTE2ptTieneEPSSanitas, Constants.Type.BOOL.getTypeName()));
                            
                            // Operand 2
                            comparison6.setSecondOperand(new SQLWhereOperandLiteral(Boolean.FALSE, Constants.Type.BOOL.getTypeName()));
                            comparison2.addOperand(comparison6);
                    
                    comparison1.addOperand(comparison2);
                }
        
            // Operand 2
                    // '=' Comparison
                    SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
                    comparison8.setOperator("=");
                    
                    // Operand 1
                    SQLWhereOperandPathAttribute operand119 = new SQLWhereOperandPathAttribute(MunicipioConstants.ATTR_NAME_MUNICIPIOESVALIDOEPS, Constants.Type.BOOL.getTypeName());
                    comparison8.setFirstOperand(operand119);
                    
                    // Operand 2
                    comparison8.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
                    comparison1.addOperand(comparison8);
            
            conjunction.addOperand(comparison1);
        }

        }        
        sql.addConjunction(conjunction);
    }

    private void clas1431957012480960FiltNav27(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Departamento solicitudTINDICARCONTRATANTE2ptpDCDepartamento = filterRequest.hasFilterVariable(SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTPDCDEPARTAMENTO) ? departamentoSrv.getByOID(new DepartamentoOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTPDCDEPARTAMENTO)))) : null;

        // Departamento = ptpDCDepartamento
        if (solicitudTINDICARCONTRATANTE2ptpDCDepartamento != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand112 = new SQLWhereOperandPathInstance(MunicipioConstants.ROLE_NAME_DEPARTAMENTO, DepartamentoConstants.CLASS_NAME);
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            SQLWhereOperandOVParameter operand223 = new SQLWhereOperandOVParameter(SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTPDCDEPARTAMENTO, DepartamentoConstants.CLASS_NAME, DepartamentoConstants.CLASS_NAME, solicitudTINDICARCONTRATANTE2ptpDCDepartamento.getOid(), SolicitudConstants.ARG_NAME_TINDICARCONTRATANTE2_SOLICITUDTINDICARCONTRATANTE2PTPDCDEPARTAMENTO);
            comparison1.setSecondOperand(operand223);
            conjunction.addOperand(comparison1);

        }        
        sql.addConjunction(conjunction);
    }

    private void clas1431957012480960FiltNav29(FilterRequest filterRequest, SQLSelect sql) {
        clas1431886233600805FiltNav12(filterRequest, sql);
    }

    private void clas1431957012480960FiltNav28(FilterRequest filterRequest, SQLSelect sql) {
        clas1431886233600805FiltNav11(filterRequest, sql);
    }

    private void clas1431957012480960FiltNav34(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Boolean solicitudTINDICARTITULAR2ptpTitularBeneficios = filterRequest.hasFilterVariable(SolicitudConstants.ARG_NAME_TINDICARTITULAR2_SOLICITUDTINDICARTITULAR2PTPTITULARBENEFICIOS) ? (Boolean) filterRequest.getFilterVariable(SolicitudConstants.ARG_NAME_TINDICARTITULAR2_SOLICITUDTINDICARTITULAR2PTPTITULARBENEFICIOS, FilterVariableRequest.BOOL) : null;
        Boolean solicitudTINDICARTITULAR2ptpTieneEPSSanitas = filterRequest.hasFilterVariable(SolicitudConstants.ARG_NAME_TINDICARTITULAR2_SOLICITUDTINDICARTITULAR2PTPTIENEEPSSANITAS) ? (Boolean) filterRequest.getFilterVariable(SolicitudConstants.ARG_NAME_TINDICARTITULAR2_SOLICITUDTINDICARTITULAR2PTPTIENEEPSSANITAS, FilterVariableRequest.BOOL) : null;

        // ptpTitularBeneficios = false OR ptpTieneEPSSanitas = false OR EsValidoEPS = true
        if (solicitudTINDICARTITULAR2ptpTitularBeneficios != null && solicitudTINDICARTITULAR2ptpTieneEPSSanitas != null) {
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
                            comparison3.setFirstOperand(new SQLWhereOperandLiteral(solicitudTINDICARTITULAR2ptpTitularBeneficios, Constants.Type.BOOL.getTypeName()));
                            
                            // Operand 2
                            comparison3.setSecondOperand(new SQLWhereOperandLiteral(Boolean.FALSE, Constants.Type.BOOL.getTypeName()));
                            comparison2.addOperand(comparison3);
                
                    // Operand 2
                            // '=' Comparison
                            SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
                            comparison6.setOperator("=");
                            
                            // Operand 1
                            comparison6.setFirstOperand(new SQLWhereOperandLiteral(solicitudTINDICARTITULAR2ptpTieneEPSSanitas, Constants.Type.BOOL.getTypeName()));
                            
                            // Operand 2
                            comparison6.setSecondOperand(new SQLWhereOperandLiteral(Boolean.FALSE, Constants.Type.BOOL.getTypeName()));
                            comparison2.addOperand(comparison6);
                    
                    comparison1.addOperand(comparison2);
                }
        
            // Operand 2
                    // '=' Comparison
                    SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
                    comparison8.setOperator("=");
                    
                    // Operand 1
                    SQLWhereOperandPathAttribute operand119 = new SQLWhereOperandPathAttribute(MunicipioConstants.ATTR_NAME_MUNICIPIOESVALIDOEPS, Constants.Type.BOOL.getTypeName());
                    comparison8.setFirstOperand(operand119);
                    
                    // Operand 2
                    comparison8.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
                    comparison1.addOperand(comparison8);
            
            conjunction.addOperand(comparison1);
        }

        }        
        sql.addConjunction(conjunction);
    }

    private void clas1431957012480960FiltNav35(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Departamento solicitudTINDICARTITULAR2ptDCDepartamento = filterRequest.hasFilterVariable(SolicitudConstants.ARG_NAME_TINDICARTITULAR2_SOLICITUDTINDICARTITULAR2PTDCDEPARTAMENTO) ? departamentoSrv.getByOID(new DepartamentoOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.ARG_NAME_TINDICARTITULAR2_SOLICITUDTINDICARTITULAR2PTDCDEPARTAMENTO)))) : null;

        // Departamento = ptDCDepartamento
        if (solicitudTINDICARTITULAR2ptDCDepartamento != null) {
            // '=' Comparison
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            
            // Operand 1
            SQLWhereOperandPathInstance operand112 = new SQLWhereOperandPathInstance(MunicipioConstants.ROLE_NAME_DEPARTAMENTO, DepartamentoConstants.CLASS_NAME);
            comparison1.setFirstOperand(operand112);
            
            // Operand 2
            SQLWhereOperandOVParameter operand223 = new SQLWhereOperandOVParameter(SolicitudConstants.ARG_NAME_TINDICARTITULAR2_SOLICITUDTINDICARTITULAR2PTDCDEPARTAMENTO, DepartamentoConstants.CLASS_NAME, DepartamentoConstants.CLASS_NAME, solicitudTINDICARTITULAR2ptDCDepartamento.getOid(), SolicitudConstants.ARG_NAME_TINDICARTITULAR2_SOLICITUDTINDICARTITULAR2PTDCDEPARTAMENTO);
            comparison1.setSecondOperand(operand223);
            conjunction.addOperand(comparison1);

        }        
        sql.addConjunction(conjunction);
    }

    private void clas1431957012480960FiltNav39(FilterRequest filterRequest, SQLSelect sql) {
        clas1431957012480960FiltNav26(filterRequest, sql);
    }

    private void clas1431957012480960FiltNav43(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Solicitud solicitudTMODDIRSCONTRATANTEpthisSolicitud = filterRequest.hasFilterVariable(SolicitudConstants.ARG_NAME_TMODDIRSCONTRATANTE_SOLICITUDTMODDIRSCONTRATANTEPTHISSOLICITUD) ? solicitudSrv.getByOID(new SolicitudOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.ARG_NAME_TMODDIRSCONTRATANTE_SOLICITUDTMODDIRSCONTRATANTEPTHISSOLICITUD)))) : null;

        // pthisSolicitud.Contratante.TitularConBeneficios = false OR pthisSolicitud.ContratantePersona.TieneEPSSanitas = false OR EsValidoEPS = true
        if (solicitudTMODDIRSCONTRATANTEpthisSolicitud != null) {
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
                            if (solicitudTMODDIRSCONTRATANTEpthisSolicitud.getContratante().getContratanteTitularConBeneficios()!= null){
                            comparison3.setFirstOperand(new SQLWhereOperandLiteral(solicitudTMODDIRSCONTRATANTEpthisSolicitud.getContratante().getContratanteTitularConBeneficios(), Constants.Type.BOOL.getTypeName()));
                            } else {
                            comparison3.setFirstOperand(new SQLWhereOperandNull());
                            }
                            
                            // Operand 2
                            comparison3.setSecondOperand(new SQLWhereOperandLiteral(Boolean.FALSE, Constants.Type.BOOL.getTypeName()));
                            comparison2.addOperand(comparison3);
                
                    // Operand 2
                            // '=' Comparison
                            SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
                            comparison6.setOperator("=");
                            
                            // Operand 1
                            if (solicitudTMODDIRSCONTRATANTEpthisSolicitud.getContratantePersona().getPersonaTieneEPSSanitas()!= null){
                            comparison6.setFirstOperand(new SQLWhereOperandLiteral(solicitudTMODDIRSCONTRATANTEpthisSolicitud.getContratantePersona().getPersonaTieneEPSSanitas(), Constants.Type.BOOL.getTypeName()));
                            } else {
                            comparison6.setFirstOperand(new SQLWhereOperandNull());
                            }
                            
                            // Operand 2
                            comparison6.setSecondOperand(new SQLWhereOperandLiteral(Boolean.FALSE, Constants.Type.BOOL.getTypeName()));
                            comparison2.addOperand(comparison6);
                    
                    comparison1.addOperand(comparison2);
                }
        
            // Operand 2
                    // '=' Comparison
                    SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
                    comparison8.setOperator("=");
                    
                    // Operand 1
                    SQLWhereOperandPathAttribute operand119 = new SQLWhereOperandPathAttribute(MunicipioConstants.ATTR_NAME_MUNICIPIOESVALIDOEPS, Constants.Type.BOOL.getTypeName());
                    comparison8.setFirstOperand(operand119);
                    
                    // Operand 2
                    comparison8.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
                    comparison1.addOperand(comparison8);
            
            conjunction.addOperand(comparison1);
        }

        }        
        sql.addConjunction(conjunction);
    }

    private void clas1431957012480960FiltNav40(FilterRequest filterRequest, SQLSelect sql) {
        clas1431957012480960FiltNav27(filterRequest, sql);
    }

    private void clas1431957012480960FiltNav44(FilterRequest filterRequest, SQLSelect sql) {
        clas1431957012480960FiltNav43(filterRequest, sql);
    }

    private void clas1431957012480960FiltNav41(FilterRequest filterRequest, SQLSelect sql) {
        clas1431886233600805FiltNav11(filterRequest, sql);
    }

    private void clas1431957012480960FiltNav45(FilterRequest filterRequest, SQLSelect sql) {
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Solicitud solicitudTMODDIRSTITULARpthisSolicitud = filterRequest.hasFilterVariable(SolicitudConstants.ARG_NAME_TMODDIRSTITULAR_SOLICITUDTMODDIRSTITULARPTHISSOLICITUD) ? solicitudSrv.getByOID(new SolicitudOid().fromJsonString(toJson(filterRequest.getFilterVariable(SolicitudConstants.ARG_NAME_TMODDIRSTITULAR_SOLICITUDTMODDIRSTITULARPTHISSOLICITUD)))) : null;

        // pthisSolicitud.Titular.TitularConBeneficios = false OR pthisSolicitud.TitularPersona.TieneEPSSanitas = false OR EsValidoEPS = true
        if (solicitudTMODDIRSTITULARpthisSolicitud != null) {
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
                            if (solicitudTMODDIRSTITULARpthisSolicitud.getTitular().getTitularTitularConBeneficios()!= null){
                            comparison3.setFirstOperand(new SQLWhereOperandLiteral(solicitudTMODDIRSTITULARpthisSolicitud.getTitular().getTitularTitularConBeneficios(), Constants.Type.BOOL.getTypeName()));
                            } else {
                            comparison3.setFirstOperand(new SQLWhereOperandNull());
                            }
                            
                            // Operand 2
                            comparison3.setSecondOperand(new SQLWhereOperandLiteral(Boolean.FALSE, Constants.Type.BOOL.getTypeName()));
                            comparison2.addOperand(comparison3);
                
                    // Operand 2
                            // '=' Comparison
                            SQLWhereComparison comparison6 = new SQLWhereComparison(sql);
                            comparison6.setOperator("=");
                            
                            // Operand 1
                            if (solicitudTMODDIRSTITULARpthisSolicitud.getTitularPersona().getPersonaTieneEPSSanitas()!= null){
                            comparison6.setFirstOperand(new SQLWhereOperandLiteral(solicitudTMODDIRSTITULARpthisSolicitud.getTitularPersona().getPersonaTieneEPSSanitas(), Constants.Type.BOOL.getTypeName()));
                            } else {
                            comparison6.setFirstOperand(new SQLWhereOperandNull());
                            }
                            
                            // Operand 2
                            comparison6.setSecondOperand(new SQLWhereOperandLiteral(Boolean.FALSE, Constants.Type.BOOL.getTypeName()));
                            comparison2.addOperand(comparison6);
                    
                    comparison1.addOperand(comparison2);
                }
        
            // Operand 2
                    // '=' Comparison
                    SQLWhereComparison comparison8 = new SQLWhereComparison(sql);
                    comparison8.setOperator("=");
                    
                    // Operand 1
                    SQLWhereOperandPathAttribute operand119 = new SQLWhereOperandPathAttribute(MunicipioConstants.ATTR_NAME_MUNICIPIOESVALIDOEPS, Constants.Type.BOOL.getTypeName());
                    comparison8.setFirstOperand(operand119);
                    
                    // Operand 2
                    comparison8.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
                    comparison1.addOperand(comparison8);
            
            conjunction.addOperand(comparison1);
        }

        }        
        sql.addConjunction(conjunction);
    }

    private void clas1431957012480960FiltNav42(FilterRequest filterRequest, SQLSelect sql) {
        clas1431957012480960FiltNav35(filterRequest, sql);
    }

    private void clas1431957012480960FiltNav46(FilterRequest filterRequest, SQLSelect sql) {
        clas1431957012480960FiltNav45(filterRequest, sql);
    }

    private void clas1432633737216826FiltNav9(FilterRequest filterRequest, SQLSelect sql) {
        clas1431957012480960FiltNav25(filterRequest, sql);
    }

    private void clas1432633737216826FiltNav8(FilterRequest filterRequest, SQLSelect sql) {
        clas1431957012480960FiltNav26(filterRequest, sql);
    }

    private void clas1432633737216826FiltNav10(FilterRequest filterRequest, SQLSelect sql) throws SystemException{
        SQLWhereConjunction conjunction = new SQLWhereConjunction();
        Usuario usuarioTMODUSUARIOpthisUsuario = filterRequest.hasFilterVariable(UsuarioConstants.ARG_NAME_TMODUSUARIO_USUARIOTMODUSUARIOPTHISUSUARIO) ? usuarioSrv.getByOID(new UsuarioOid().fromJsonString(toJson(filterRequest.getFilterVariable(UsuarioConstants.ARG_NAME_TMODUSUARIO_USUARIOTMODUSUARIOPTHISUSUARIO)))) : null;

        // EXIST (pthisUsuario.UsuarioPersona) = false OR pthisUsuario.UsuarioPersona.TieneEPSSanitas = false OR EsValidoEPS = true
        if (usuarioTMODUSUARIOpthisUsuario != null) {
        {
            // 'or' Logical operator
            SQLWhereDisjunction comparison1 = new SQLWhereDisjunction();
        
            // Operand 1
                {
                    // 'or' Logical operator
                    SQLWhereDisjunction comparison2 = new SQLWhereDisjunction();
                
                    // Operand 1
                        {
                            // 'not' Logical operator
                            SQLWhereNegation comparison3 = new SQLWhereNegation(sql);
                        
                            // Operand 1
                            // 'exist' Association operator
                            SQLWhereOperandSubQuery operandSubQueryAssocOperator005 = new SQLWhereOperandSubQuery(repository.assocOperator005Range1Query(usuarioTMODUSUARIOpthisUsuario, sql));
                            comparison3.addOperand(operandSubQueryAssocOperator005);
                            
                            comparison2.addOperand(comparison3);
                        }
                
                    // Operand 2
                            // '=' Comparison
                            SQLWhereComparison comparison30 = new SQLWhereComparison(sql);
                            comparison30.setOperator("=");
                            
                            // Operand 1
                            if (usuarioTMODUSUARIOpthisUsuario.getUsuarioPersona().getPersonaTieneEPSSanitas()!= null){
                            comparison30.setFirstOperand(new SQLWhereOperandLiteral(usuarioTMODUSUARIOpthisUsuario.getUsuarioPersona().getPersonaTieneEPSSanitas(), Constants.Type.BOOL.getTypeName()));
                            } else {
                            comparison30.setFirstOperand(new SQLWhereOperandNull());
                            }
                            
                            // Operand 2
                            comparison30.setSecondOperand(new SQLWhereOperandLiteral(Boolean.FALSE, Constants.Type.BOOL.getTypeName()));
                            comparison2.addOperand(comparison30);
                    
                    comparison1.addOperand(comparison2);
                }
        
            // Operand 2
                    // '=' Comparison
                    SQLWhereComparison comparison5 = new SQLWhereComparison(sql);
                    comparison5.setOperator("=");
                    
                    // Operand 1
                    SQLWhereOperandPathAttribute operand116 = new SQLWhereOperandPathAttribute(MunicipioConstants.ATTR_NAME_MUNICIPIOESVALIDOEPS, Constants.Type.BOOL.getTypeName());
                    comparison5.setFirstOperand(operand116);
                    
                    // Operand 2
                    comparison5.setSecondOperand(new SQLWhereOperandLiteral(Boolean.TRUE, Constants.Type.BOOL.getTypeName()));
                    comparison1.addOperand(comparison5);
            
            conjunction.addOperand(comparison1);
        }

        }        
        sql.addConjunction(conjunction);
    }

    private void clas1581424574464890FiltNav6(FilterRequest filterRequest, SQLSelect sql) {
        clas1431886233600805FiltNav11(filterRequest, sql);
    }

    private void clas1581424574464890FiltNav4(FilterRequest filterRequest, SQLSelect sql) {
        clas1431886233600805FiltNav12(filterRequest, sql);
    }

    private void clas1581424574464890FiltNav3(FilterRequest filterRequest, SQLSelect sql) {
        clas1431886233600805FiltNav11(filterRequest, sql);
    }

    // IIU Methods


    /*
     * Solves the query needed for PIUMunicipio
     *
     * @param queryRequest Query request
     * @return List of viewmodels for Municipio using displayset DSMunicipioPIU that 
     * match the search criteria
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<com.keralty.aegenerasolicitud.viewmodel.municipio.ds.DSMunicipioPIU> piuPIUMunicipio(QueryRequest queryRequest) {
        return solveMunicipioTypedQuery(queryRequest).stream()
                .map(com.keralty.aegenerasolicitud.viewmodel.municipio.ds.DSMunicipioPIU::new)
                .collect(Collectors.toList());
    }

    /*
     * Solves the COUNT query needed for PIUMunicipio
     *
     * @param queryRequest Query request
     * @returns Count of instances for PIUMunicipio
     */
    public int getCount4PIUMunicipio(QueryRequest queryRequest) {
        return solveMunicipioTypedQuery(queryRequest).size();
    }

    // Service: InsMunicipioC

    /*
     * Entry point for service InsMunicipioC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse insMunicipioC(
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.InsMunicipioCRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = municipioSrv.insMunicipioCTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service InsMunicipioC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse insMunicipioCTxn(
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.InsMunicipioCRequest request) throws ModelException {
        request.setMunicipioInsMunicipioCpthisMunicipioInstance(municipioSrv.getByOIDWithHV(request.getMunicipioInsMunicipioCpthisMunicipio()));
        request.setMunicipioInsMunicipioCpevcDireccionCInstance(direccionCSrv.getByOIDWithHV(request.getMunicipioInsMunicipioCpevcDireccionC()));
        return municipioSrv.insMunicipioCImplementation(request, true);
    }

    /*
     * Implements service InsMunicipioC
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse insMunicipioCImplementation(
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.InsMunicipioCRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Municipio municipioInsMunicipioCpthisMunicipio = request.getMunicipioInsMunicipioCpthisMunicipioInstance();
        DireccionC municipioInsMunicipioCpevcDireccionC = request.getMunicipioInsMunicipioCpevcDireccionCInstance();

        request.checkArguments();
        Municipio instance = municipioInsMunicipioCpthisMunicipio;

        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.direccionc.siu.InsMunicipioCRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.direccionc.siu.InsMunicipioCRequest();
            relatedRequest.setDireccionCInsMunicipioCpthisDireccionCInstance(municipioInsMunicipioCpevcDireccionC);
            relatedRequest.setDireccionCInsMunicipioCpevcMunicipioInstance(municipioInsMunicipioCpthisMunicipio);
            direccionCSrv.insMunicipioCImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICMunicipio());

        return response;
    }

    // Service: DelMunicipioC

    /*
     * Entry point for service DelMunicipioC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse delMunicipioC(
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.DelMunicipioCRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = municipioSrv.delMunicipioCTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service DelMunicipioC
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse delMunicipioCTxn(
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.DelMunicipioCRequest request) throws ModelException {
        request.setMunicipioDelMunicipioCpthisMunicipioInstance(municipioSrv.getByOIDWithHV(request.getMunicipioDelMunicipioCpthisMunicipio()));
        request.setMunicipioDelMunicipioCpevcDireccionCInstance(direccionCSrv.getByOIDWithHV(request.getMunicipioDelMunicipioCpevcDireccionC()));
        return municipioSrv.delMunicipioCImplementation(request, true);
    }

    /*
     * Implements service DelMunicipioC
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse delMunicipioCImplementation(
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.DelMunicipioCRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Municipio municipioDelMunicipioCpthisMunicipio = request.getMunicipioDelMunicipioCpthisMunicipioInstance();
        DireccionC municipioDelMunicipioCpevcDireccionC = request.getMunicipioDelMunicipioCpevcDireccionCInstance();

        request.checkArguments();
        Municipio instance = municipioDelMunicipioCpthisMunicipio;

        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.direccionc.siu.DelMunicipioCRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.direccionc.siu.DelMunicipioCRequest();
            relatedRequest.setDireccionCDelMunicipioCpthisDireccionCInstance(municipioDelMunicipioCpevcDireccionC);
            relatedRequest.setDireccionCDelMunicipioCpevcMunicipioInstance(municipioDelMunicipioCpthisMunicipio);
            direccionCSrv.delMunicipioCImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICMunicipio());

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
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.InsMunicipioContratoRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = municipioSrv.insMunicipioContratoTxn(request);
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
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.InsMunicipioContratoRequest request) throws ModelException {
        request.setMunicipioInsMunicipioContratopthisMunicipioInstance(municipioSrv.getByOIDWithHV(request.getMunicipioInsMunicipioContratopthisMunicipio()));
        request.setMunicipioInsMunicipioContratopevcContratoCInstance(contratoCSrv.getByOIDWithHV(request.getMunicipioInsMunicipioContratopevcContratoC()));
        return municipioSrv.insMunicipioContratoImplementation(request, true);
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
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.InsMunicipioContratoRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Municipio municipioInsMunicipioContratopthisMunicipio = request.getMunicipioInsMunicipioContratopthisMunicipioInstance();
        ContratoC municipioInsMunicipioContratopevcContratoC = request.getMunicipioInsMunicipioContratopevcContratoCInstance();

        request.checkArguments();
        Municipio instance = municipioInsMunicipioContratopthisMunicipio;

        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsMunicipioContratoRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.InsMunicipioContratoRequest();
            relatedRequest.setContratoCInsMunicipioContratopthisContratoCInstance(municipioInsMunicipioContratopevcContratoC);
            relatedRequest.setContratoCInsMunicipioContratopevcMunicipioInstance(municipioInsMunicipioContratopthisMunicipio);
            contratoCSrv.insMunicipioContratoImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICMunicipio());

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
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.DelMunicipioContratoRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = municipioSrv.delMunicipioContratoTxn(request);
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
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.DelMunicipioContratoRequest request) throws ModelException {
        request.setMunicipioDelMunicipioContratopthisMunicipioInstance(municipioSrv.getByOIDWithHV(request.getMunicipioDelMunicipioContratopthisMunicipio()));
        request.setMunicipioDelMunicipioContratopevcContratoCInstance(contratoCSrv.getByOIDWithHV(request.getMunicipioDelMunicipioContratopevcContratoC()));
        return municipioSrv.delMunicipioContratoImplementation(request, true);
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
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.DelMunicipioContratoRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Municipio municipioDelMunicipioContratopthisMunicipio = request.getMunicipioDelMunicipioContratopthisMunicipioInstance();
        ContratoC municipioDelMunicipioContratopevcContratoC = request.getMunicipioDelMunicipioContratopevcContratoCInstance();

        request.checkArguments();
        Municipio instance = municipioDelMunicipioContratopthisMunicipio;

        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelMunicipioContratoRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.DelMunicipioContratoRequest();
            relatedRequest.setContratoCDelMunicipioContratopthisContratoCInstance(municipioDelMunicipioContratopevcContratoC);
            relatedRequest.setContratoCDelMunicipioContratopevcMunicipioInstance(municipioDelMunicipioContratopthisMunicipio);
            contratoCSrv.delMunicipioContratoImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICMunicipio());

        return response;
    }

    // Service: InsMunicipioFamilia

    /*
     * Entry point for service InsMunicipioFamilia
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse insMunicipioFamilia(
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.InsMunicipioFamiliaRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = municipioSrv.insMunicipioFamiliaTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service InsMunicipioFamilia
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse insMunicipioFamiliaTxn(
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.InsMunicipioFamiliaRequest request) throws ModelException {
        request.setMunicipioInsMunicipioFamiliapthisMunicipioInstance(municipioSrv.getByOIDWithHV(request.getMunicipioInsMunicipioFamiliapthisMunicipio()));
        request.setMunicipioInsMunicipioFamiliapevcFamiliaCInstance(familiaCSrv.getByOIDWithHV(request.getMunicipioInsMunicipioFamiliapevcFamiliaC()));
        return municipioSrv.insMunicipioFamiliaImplementation(request, true);
    }

    /*
     * Implements service InsMunicipioFamilia
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse insMunicipioFamiliaImplementation(
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.InsMunicipioFamiliaRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Municipio municipioInsMunicipioFamiliapthisMunicipio = request.getMunicipioInsMunicipioFamiliapthisMunicipioInstance();
        FamiliaC municipioInsMunicipioFamiliapevcFamiliaC = request.getMunicipioInsMunicipioFamiliapevcFamiliaCInstance();

        request.checkArguments();
        Municipio instance = municipioInsMunicipioFamiliapthisMunicipio;

        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.familiac.siu.InsMunicipioFamiliaRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.familiac.siu.InsMunicipioFamiliaRequest();
            relatedRequest.setFamiliaCInsMunicipioFamiliapthisFamiliaCInstance(municipioInsMunicipioFamiliapevcFamiliaC);
            relatedRequest.setFamiliaCInsMunicipioFamiliapevcMunicipioInstance(municipioInsMunicipioFamiliapthisMunicipio);
            familiaCSrv.insMunicipioFamiliaImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICMunicipio());

        return response;
    }

    // Service: DelMunicipioFamilia

    /*
     * Entry point for service DelMunicipioFamilia
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse delMunicipioFamilia(
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.DelMunicipioFamiliaRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = municipioSrv.delMunicipioFamiliaTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for service DelMunicipioFamilia
     *
     * @param request Service request viewmodel
     *
     * @returns Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse delMunicipioFamiliaTxn(
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.DelMunicipioFamiliaRequest request) throws ModelException {
        request.setMunicipioDelMunicipioFamiliapthisMunicipioInstance(municipioSrv.getByOIDWithHV(request.getMunicipioDelMunicipioFamiliapthisMunicipio()));
        request.setMunicipioDelMunicipioFamiliapevcFamiliaCInstance(familiaCSrv.getByOIDWithHV(request.getMunicipioDelMunicipioFamiliapevcFamiliaC()));
        return municipioSrv.delMunicipioFamiliaImplementation(request, true);
    }

    /*
     * Implements service DelMunicipioFamilia
     *
     * @param request Service request viewmodel
     *
     * @return Service response ViewModel encapsulating the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse delMunicipioFamiliaImplementation(
            com.keralty.aegenerasolicitud.viewmodel.municipio.siu.DelMunicipioFamiliaRequest request, boolean isEntryPoint) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Municipio municipioDelMunicipioFamiliapthisMunicipio = request.getMunicipioDelMunicipioFamiliapthisMunicipioInstance();
        FamiliaC municipioDelMunicipioFamiliapevcFamiliaC = request.getMunicipioDelMunicipioFamiliapevcFamiliaCInstance();

        request.checkArguments();
        Municipio instance = municipioDelMunicipioFamiliapthisMunicipio;

        if (isEntryPoint) {
            com.keralty.aegenerasolicitud.viewmodel.familiac.siu.DelMunicipioFamiliaRequest relatedRequest = new com.keralty.aegenerasolicitud.viewmodel.familiac.siu.DelMunicipioFamiliaRequest();
            relatedRequest.setFamiliaCDelMunicipioFamiliapthisFamiliaCInstance(municipioDelMunicipioFamiliapevcFamiliaC);
            relatedRequest.setFamiliaCDelMunicipioFamiliapevcMunicipioInstance(municipioDelMunicipioFamiliapthisMunicipio);
            familiaCSrv.delMunicipioFamiliaImplementation(relatedRequest, false);
        }


        response.setInstance(repository.save(instance));
        response.setInstanceSuppInfo(instance.buildICMunicipio());

        return response;
    }


    private void suppInfo4ICMunicipio(String searchText, SQLSelect select) {
        try {
            SQLWhereDisjunction disjunction = new SQLWhereDisjunction();

            SQLWhereComparison comparison1 = new SQLWhereComparison(select);
            comparison1.setOperator("LIKE");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(MunicipioConstants.ATTR_NAME_MUNICIPIODESCRIPCION, Constants.Type.STRING.getTypeName()));
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
        applyOCOCMunicipio(queryRequest, select);
        applyDSItemSort(queryRequest, select);
        applyDefaultOrderCriteria(queryRequest, select);
    }

    private void applyDefaultOrderCriteria(QueryRequest queryRequest, SQLSelect select) {
        if (queryRequest.getOrderCriteriaName() != null && !queryRequest.getOrderCriteriaName().isBlank())
            return;
        if (queryRequest.getDsItemSort() != null && !queryRequest.getDsItemSort().isBlank())
            return;
        select.getOrderByPart().addItem(Constants.normalizePath(MunicipioConstants.CLASS_NAME, MunicipioConstants.ATTR_FIELD_MUNICIPIOCODIGO, false), true);
    }


    private void applyOCOCMunicipio(QueryRequest queryRequest, SQLSelect select) {
        if ("OC_Municipio".equalsIgnoreCase(queryRequest.getOrderCriteriaName())) {
            select.getOrderByPart().addItem(Constants.normalizePath(MunicipioConstants.CLASS_NAME, MunicipioConstants.ATTR_NAME_MUNICIPIODESCRIPCION, false), true);
        }
    }
    
    @Override
    protected AbstractAppEntity buildRelatedInstance(String relatedInstanceJsonOid, String relatedInstancePathFirstItem, String remainingPath) {
        AbstractAppEntity relatedInstance = null;
        if (relatedInstancePathFirstItem.isEmpty()) {
            relatedInstance = buildMunicipioRelatedInstance(relatedInstanceJsonOid);
        }
        if (MunicipioConstants.ROLE_NAME_PERSONAS.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildMunicipioRelatedInstance4Persona(relatedInstanceJsonOid, remainingPath);
        }
        if (MunicipioConstants.ROLE_NAME_PERSONASC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildMunicipioRelatedInstance4PersonaC(relatedInstanceJsonOid, remainingPath);
        }
        if (MunicipioConstants.ROLE_NAME_DIRECCIONESC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildMunicipioRelatedInstance4DireccionC(relatedInstanceJsonOid, remainingPath);
        }
        if (MunicipioConstants.ROLE_NAME_DIRECCIONES.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildMunicipioRelatedInstance4Direccion(relatedInstanceJsonOid, remainingPath);
        }
        if (MunicipioConstants.ROLE_NAME_CONTRATANTES.equalsIgnoreCase(relatedInstancePathFirstItem) || MunicipioConstants.ROLE_NAME_CONTRATANTERES.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildMunicipioRelatedInstance4Contratante(relatedInstanceJsonOid, remainingPath);
        }
        if (MunicipioConstants.ROLE_NAME_CONTRATOSC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildMunicipioRelatedInstance4ContratoC(relatedInstanceJsonOid, remainingPath);
        }
        if (MunicipioConstants.ROLE_NAME_DIRSUCURSALC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildMunicipioRelatedInstance4DirSucursalC(relatedInstanceJsonOid, remainingPath);
        }
        if (MunicipioConstants.ROLE_NAME_FAMILIAC.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildMunicipioRelatedInstance4FamiliaC(relatedInstanceJsonOid, remainingPath);
        }
        if (MunicipioConstants.ROLE_NAME_TITULARES.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildMunicipioRelatedInstance4Titular(relatedInstanceJsonOid, remainingPath);
        }
        if (MunicipioConstants.ROLE_NAME_INSTITUCIONMATNEO.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildMunicipioRelatedInstance4InstitucionMatNeo(relatedInstanceJsonOid, remainingPath);
        }
        if (MunicipioConstants.ROLE_NAME_DEPARTAMENTO.equalsIgnoreCase(relatedInstancePathFirstItem)) {
            relatedInstance = buildMunicipioRelatedInstance4Departamento(relatedInstanceJsonOid, remainingPath);
        }
        return relatedInstance;
    }
    
    private AbstractAppEntity buildMunicipioRelatedInstance(String relatedInstanceJsonOid) {
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		return new Municipio(mapper.readValue(relatedInstanceJsonOid, MunicipioOid.class));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildMunicipioRelatedInstance4Persona(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Persona(mapper.readValue(relatedInstanceJsonOid, PersonaOid.class)): Persona.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildMunicipioRelatedInstance4PersonaC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new PersonaC(mapper.readValue(relatedInstanceJsonOid, PersonaCOid.class)): PersonaC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildMunicipioRelatedInstance4DireccionC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new DireccionC(mapper.readValue(relatedInstanceJsonOid, DireccionCOid.class)): DireccionC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildMunicipioRelatedInstance4Direccion(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Direccion(mapper.readValue(relatedInstanceJsonOid, DireccionOid.class)): Direccion.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildMunicipioRelatedInstance4Contratante(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Contratante(mapper.readValue(relatedInstanceJsonOid, ContratanteOid.class)): Contratante.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildMunicipioRelatedInstance4ContratoC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new ContratoC(mapper.readValue(relatedInstanceJsonOid, ContratoCOid.class)): ContratoC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildMunicipioRelatedInstance4DirSucursalC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new DirSucursalC(mapper.readValue(relatedInstanceJsonOid, DirSucursalCOid.class)): DirSucursalC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildMunicipioRelatedInstance4FamiliaC(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new FamiliaC(mapper.readValue(relatedInstanceJsonOid, FamiliaCOid.class)): FamiliaC.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildMunicipioRelatedInstance4Titular(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Titular(mapper.readValue(relatedInstanceJsonOid, TitularOid.class)): Titular.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildMunicipioRelatedInstance4InstitucionMatNeo(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new InstitucionMatNeo(mapper.readValue(relatedInstanceJsonOid, InstitucionMatNeoOid.class)): InstitucionMatNeo.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    private AbstractAppEntity buildMunicipioRelatedInstance4Departamento(String relatedInstanceJsonOid, String remainingPath) {
        ObjectMapper mapper = new ObjectMapper();
    	try {
            return (remainingPath.isEmpty() ? new Departamento(mapper.readValue(relatedInstanceJsonOid, DepartamentoOid.class)): Departamento.buildRelatedInstance(relatedInstanceJsonOid, remainingPath));
    	} catch (Exception e) {
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new SystemException(e.getMessage());
    	}
    }

    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            MunicipioOid oid = new MunicipioOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(MunicipioConstants.ATTR_FIELD_MUNICIPIOCODIGO, Constants.Type.INT.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getMunicipioCodigo(), Constants.Type.INT.getTypeName()));
            sql.addComparison(comparison1);
        } catch (Exception e) {
            LoggerFactory.getLogger(MunicipioService.class).error(e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

}
