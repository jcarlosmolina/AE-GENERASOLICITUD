package com.keralty.aegenerasolicitud.service;

import com.keralty.aegenerasolicitud.exception.ModelException;
import com.keralty.aegenerasolicitud.exception.PreconditionException;
import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.STDFunctions;
import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.ErrorWS;
import com.keralty.aegenerasolicitud.persistence.FamiliaC;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.ProteccionDatos;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import com.keralty.aegenerasolicitud.repository.GlobalServicesJpaRepository;
import com.keralty.aegenerasolicitud.utils.Utilities;
import com.keralty.aegenerasolicitud.viewmodel.ServiceResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import com.keralty.aegenerasolicitud.persistence.Direccion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keralty.aegenerasolicitud.global.SolicitudConstants;
import com.keralty.aegenerasolicitud.viewmodel.FilterRequest;
import com.keralty.aegenerasolicitud.viewmodel.FilterVariableRequest;
import com.keralty.aegenerasolicitud.viewmodel.QueryRequest;
import com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest;
import com.keralty.aegenerasolicitud.viewmodel.logllamadasae.siu.CreateinstanceRequest;
import com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest;
import com.keralty.integraciones.contract.ContratoMPRestUtils;
import com.keralty.integraciones.contract.DatoLogLlamadaBH;
import com.keralty.integraciones.contract.DatoRespuestaContrato;
import com.keralty.integraciones.contractresponse.request.ContractResponseRequest;
import com.keralty.integraciones.contractresponse.response.ContractResponseResponse;
import com.keralty.integraciones.patient.CrearActualizarPersonaRequest;
import com.keralty.integraciones.patient.CrearActualizarPersonaResponse;
import com.keralty.integraciones.patient.CrearActualizarCuestionarioRequest;
import com.keralty.integraciones.patient.RespuestaCuestionario;
import com.keralty.aegenerasolicitud.persistence.PreguntaP;
import com.keralty.aegenerasolicitud.persistence.SeccionP;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Transactional
@ApplicationScoped
public class GlobalServicesService extends AbstractService {

    GlobalServicesService globalServicesSrv;

    UserFunctions userFunctionsSrv;

    SolicitudService solicitudSrv;

    ErrorWSService errorWSSrv;

    PersonaService personaSrv;

    ProteccionDatosService proteccionDatosSrv;

    RolService rolSrv;

    ContratoCService contratoCSrv;

    FamiliaCService familiaCSrv;

    PreexistenciaCService preexistenciaCSrv;

    ConceptoCService conceptoCSrv;

    LogLlamadasAEService logLlamadasAESrv;

    LogLlamadasBHService logLlamadasBHSrv;

    GlobalServicesJpaRepository globalServicesRepository;

    ContratoMPRestUtils contratoMPRestUtils;
    
    @RestClient
    BhCallService bhCallService;
    
    @Inject
    public GlobalServicesService(
            SolicitudService solicitudSrv,
            ErrorWSService errorWSSrv,
            PersonaService personaSrv,
            ProteccionDatosService proteccionDatosSrv,
            RolService rolSrv,
            ContratoCService contratoCSrv,
            FamiliaCService familiaCSrv,
            PreexistenciaCService preexistenciaCSrv,
            ConceptoCService conceptoCSrv,
            LogLlamadasAEService logLlamadasAESrv,
            LogLlamadasBHService logLlamadasBHSrv,
            GlobalServicesJpaRepository globalServicesRepository,
            UserFunctions userFunctionsSrv,
            GlobalServicesService globalServicesSrv,
            ContratoMPRestUtils contratoMPRestUtils) {
        this.contratoMPRestUtils = contratoMPRestUtils;
        this.solicitudSrv = solicitudSrv;
        this.errorWSSrv = errorWSSrv;
        this.personaSrv = personaSrv;
        this.proteccionDatosSrv = proteccionDatosSrv;
        this.rolSrv = rolSrv;
        this.contratoCSrv = contratoCSrv;
        this.familiaCSrv = familiaCSrv;
        this.preexistenciaCSrv = preexistenciaCSrv;
        this.conceptoCSrv = conceptoCSrv;
        this.logLlamadasAESrv = logLlamadasAESrv;
        this.logLlamadasBHSrv = logLlamadasBHSrv;
        this.globalServicesRepository = globalServicesRepository;
        this.userFunctionsSrv = userFunctionsSrv;
        this.globalServicesSrv = globalServicesSrv;
    }

    /*
     * Entry point for global service OGENERARSOLICITUD2
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Response oGENERARSOLICITUD2(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Request request) {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Response response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Response();
        try {
            response = globalServicesSrv.oGENERARSOLICITUD2Txn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for global service OGENERARSOLICITUD2
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Response oGENERARSOLICITUD2Txn(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Request request) throws ModelException, SystemException {
        request.setOGENERARSOLICITUD2ptpSolicitudAEInstance(solicitudSrv.getByOIDWithHV(request.getOGENERARSOLICITUD2ptpSolicitudAE()));
        return globalServicesSrv.oGENERARSOLICITUD2Implementation(request);
    }

    /*
     * Implements global service OGENERARSOLICITUD2
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Response oGENERARSOLICITUD2Implementation(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Request request) throws ModelException, SystemException {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Response response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Response();
        Solicitud oGENERARSOLICITUD2ptpSolicitudAE = request.getOGENERARSOLICITUD2ptpSolicitudAEInstance();

        request.checkArguments();

        oGENERARSOLICITUD2Preconditions(request);

        response.setOGENERARSOLICITUD2psError(Boolean.FALSE);

        try {
            /* Action 1: IF (ptpSolicitudAE.Tramite = 2 AND ptpSolicitudAE.tipoContrato = 3) */
            boolean ifCondition1 = (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTramite()).equals(Long.valueOf(2)) && (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudtipoContrato()).equals(Long.valueOf(3));
            if (ifCondition1) {
                    com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarDatosPagoOKRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarDatosPagoOKRequest();
                    step10Request.setSolicitudmarcarDatosPagoOKpthisSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                    solicitudSrv.marcarDatosPagoOKTxn(step10Request);
                    globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
            }
        } catch (Exception e) {
        	throw e;
        }

        try {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest step2Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest();
            step2Request.setSolicitudTMARCARCOMPLETADApthisSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADAResponse step2Response = solicitudSrv.tMARCARCOMPLETADATxn(step2Request);
            response.setOGENERARSOLICITUD2ptpsNumSolicitud(step2Response.getSolicitudTMARCARCOMPLETADAptpsNumSolicitud());
            globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) {
        	throw e;
        }

        try {
            /* Action 3: IF (ptpSolicitudAE.Tramite <> 2 OR ptpSolicitudAE.tipoContrato <> 3 OR ptpSolicitudAE.NumFamilia <> NULL) */
            boolean ifCondition3 = !(oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTramite()).equals(Long.valueOf(2)) || !(oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudtipoContrato()).equals(Long.valueOf(3)) || oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudNumFamilia() != null;
            if (ifCondition3) {
                    com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.SetCodTarifaRequest step30Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.SetCodTarifaRequest();
                    step30Request.setSolicitudsetCodTarifapthisSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                    step30Request.setSolicitudsetCodTarifapeCodTarifa(null);
                    solicitudSrv.setCodTarifaTxn(step30Request);
                    globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
            }
        } catch (Exception e) { response.setOGENERARSOLICITUD2ptpsErrorWS(e.getMessage()); }

        try {
            for(ErrorWS step4Entity : globalServicesRepository.assocOperator102(oGENERARSOLICITUD2ptpSolicitudAE)) {
                try {
                    com.keralty.aegenerasolicitud.viewmodel.errorws.siu.EliminarRequest step4Request = new com.keralty.aegenerasolicitud.viewmodel.errorws.siu.EliminarRequest();
                    step4Request.setErrorWSeliminarpthisErrorWSInstance(step4Entity);
                    errorWSSrv.eliminarTxn(step4Request);
        } catch (Exception e) { response.setOGENERARSOLICITUD2ptpsErrorWS(e.getMessage()); }
                globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
            }
        } catch (Exception e) { response.setOGENERARSOLICITUD2ptpsErrorWS(e.getMessage()); }

        try {
            /* Action 5: IF (ptpSolicitudAE.UsuariosValidados = true) */
            boolean ifCondition5 = oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudUsuariosValidados().equals(true);
            if (ifCondition5) {
                /* Action 50: IF (ptpSolicitudAE.Tramite = 1) */
                boolean ifCondition50 = (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTramite()).equals(Long.valueOf(1));
                if (ifCondition50) {
                        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACORERequest step500Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACORERequest();
                        step500Request.setTGCREARACTPERSONACOREptSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                        step500Request.setTGCREARACTPERSONACOREptPersonaInstance(oGENERARSOLICITUD2ptpSolicitudAE.getContratantePersona());
                        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACOREResponse step500Response = globalServicesSrv.tGCREARACTPERSONACORETxn(step500Request);
                        response.setOGENERARSOLICITUD2ptpsErrorWS(step500Response.getTGCREARACTPERSONACOREpsSalida());
                        
                        globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                }
                /* Action 51: IF (ptpSolicitudAE.Tramite = 2) */
                boolean ifCondition51 = (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTramite()).equals(Long.valueOf(2));
                if (ifCondition51) {
                        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACORERequest step510Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACORERequest();
                        step510Request.setTGCREARACTPERSONACOREptSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                        step510Request.setTGCREARACTPERSONACOREptPersonaInstance(oGENERARSOLICITUD2ptpSolicitudAE.getTitularPersona());
                        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACOREResponse step510Response = globalServicesSrv.tGCREARACTPERSONACORETxn(step510Request);
                        response.setOGENERARSOLICITUD2ptpsErrorWS(step510Response.getTGCREARACTPERSONACOREpsSalida());
                        
                        globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                }
                for(Usuario step5052Entity : globalServicesRepository.assocOperator103(oGENERARSOLICITUD2ptpSolicitudAE)) {
                        try {
                            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACORERequest step52Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACORERequest();
                            step52Request.setTGCREARACTPERSONACOREptSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                            step52Request.setTGCREARACTPERSONACOREptPersonaInstance(step5052Entity.getUsuarioPersona());
                            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACOREResponse step52Response = globalServicesSrv.tGCREARACTPERSONACORETxn(step52Request);
                            response.setOGENERARSOLICITUD2ptpsErrorWS(step52Response.getTGCREARACTPERSONACOREpsSalida());
                            
        } catch (Exception e) { response.setOGENERARSOLICITUD2ptpsErrorWS(e.getMessage()); }
                        globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                }
                /* Action 53: IF (EXIST (ptpSolicitudAE.ErroresWS) = false AND ptpSolicitudAE.Tramite = 2 AND ptpSolicitudAE.tipoContrato = 1 AND ptpSolicitudAE.TipoFacturaCORE <> NULL AND EXIST (ptpSolicitudAE.TipoFactura) = true AND ptpSolicitudAE.TipoFacturaCORE <> ptpSolicitudAE.TipoFactura.Codigo) */
                boolean ifCondition53 = globalServicesRepository.assocOperator107(oGENERARSOLICITUD2ptpSolicitudAE).equals(false) && (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTramite()).equals(Long.valueOf(2)) && (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudtipoContrato()).equals(Long.valueOf(1)) && oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTipoFacturaCORE() != null && globalServicesRepository.assocOperator108(oGENERARSOLICITUD2ptpSolicitudAE).equals(true) && !STDFunctions.areEqual(oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTipoFacturaCORE(),oGENERARSOLICITUD2ptpSolicitudAE.getTipoFactura().getTipoFacturaCodigo());
                if (ifCondition53) {
                        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTTIPOFACTURARequest step530Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTTIPOFACTURARequest();
                        step530Request.setTGACTTIPOFACTURAptSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                        globalServicesSrv.tGACTTIPOFACTURATxn(step530Request);
                        globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                }
                /* Action 54: IF (EXIST (ptpSolicitudAE.ErroresWS) = false) */
                boolean ifCondition54 = globalServicesRepository.assocOperator109(oGENERARSOLICITUD2ptpSolicitudAE).equals(false);
                if (ifCondition54) {
                    /* Action 540: IF (ptpSolicitudAE.Tramite = 1) */
                    boolean ifCondition540 = (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTramite()).equals(Long.valueOf(1));
                    if (ifCondition540) {
                            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCORERequest step5400Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCORERequest();
                            step5400Request.setTGCREARCONTRATOCOREptSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCOREResponse step5400Response = globalServicesSrv.tGCREARCONTRATOCORETxn(step5400Request);
                            response.setOGENERARSOLICITUD2psError(step5400Response.getTGCREARCONTRATOCOREpsError());
                            globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                    } else {
                        /* Action 5401: IF (ptpSolicitudAE.EstadoDoc = 2) */
                        boolean ifCondition5401 = (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudEstadoDoc()).equals(Long.valueOf(2));
                        if (ifCondition5401) {
                            /* Action 54010: IF (ptpSolicitudAE.NumFamilia = NULL) */
                            boolean ifCondition54010 = oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudNumFamilia() == null;
                            if (ifCondition54010) {
                                    com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCORERequest step540100Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCORERequest();
                                    step540100Request.setTGCREARCONTRATOCOREptSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                                    com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCOREResponse step540100Response = globalServicesSrv.tGCREARCONTRATOCORETxn(step540100Request);
                                    response.setOGENERARSOLICITUD2psError(step540100Response.getTGCREARCONTRATOCOREpsError());
                                    globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                            } else {
                                    com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCORERequest step540101Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCORERequest();
                                    step540101Request.setTGINCLUSIONCONTRATOCOREptSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                                    com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCOREResponse step540101Response = globalServicesSrv.tGINCLUSIONCONTRATOCORETxn(step540101Request);
                                    response.setOGENERARSOLICITUD2psError(step540101Response.getTGINCLUSIONCONTRATOCOREpsError());
                                    globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                            }
                        }
                    }
                    /* Action 541: IF (psError = false) */
                    boolean ifCondition541 = response.getOGENERARSOLICITUD2psError().equals(false);
                    if (ifCondition541) {
                            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TPOSTGENERAREXITORequest step5410Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TPOSTGENERAREXITORequest();
                            step5410Request.setSolicitudTPOSTGENERAREXITOpthisSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                            solicitudSrv.tPOSTGENERAREXITOTxn(step5410Request);
                            globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                    }
                } else {
                        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarNoCompletadaRequest step542Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarNoCompletadaRequest();
                        step542Request.setSolicitudmarcarNoCompletadapthisSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                        solicitudSrv.marcarNoCompletadaTxn(step542Request);
                        globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                }
            } else {
                    com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarNoCompletadaRequest step55Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarNoCompletadaRequest();
                    step55Request.setSolicitudmarcarNoCompletadapthisSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                    solicitudSrv.marcarNoCompletadaTxn(step55Request);
                    globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
            }
        } catch (Exception e) { response.setOGENERARSOLICITUD2ptpsErrorWS(e.getMessage()); }

        try {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSRequest step6Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSRequest();
            step6Request.setSolicituddummyDevolverErroresWSpthisSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSResponse step6Response = solicitudSrv.dummyDevolverErroresWSTxn(step6Request);
            response.setOGENERARSOLICITUD2ptpsErrorWS(step6Response.getSolicituddummyDevolverErroresWSpsErroresWS());
            globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) { response.setOGENERARSOLICITUD2ptpsErrorWS(e.getMessage()); }
        return response;
    }

    /*
     * Checks service OGENERARSOLICITUD2 preconditions individually
     * @param request Service request viewmodel
     * @returns Service response viewmodel with the execution results
     */
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Response oGENERARSOLICITUD2CheckPreconditions(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Request request) {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Response response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Response();
        request.setOGENERARSOLICITUD2ptpSolicitudAEInstance(solicitudSrv.getByOIDWithHV(request.getOGENERARSOLICITUD2ptpSolicitudAE()));
        try {
            if("IGl_1462185885696764Pre_14".equals(request.getPreconditionId()))
                checkIGl1462185885696764Pre14(request);
            if("IGl_1462185885696764Pre_15".equals(request.getPreconditionId()))
                checkIGl1462185885696764Pre15(request);
            if("IGl_1462185885696764Pre_12".equals(request.getPreconditionId()))
                checkIGl1462185885696764Pre12(request);
            if("IGl_1462185885696764Pre_13".equals(request.getPreconditionId()))
                checkIGl1462185885696764Pre13(request);
            if("IGl_1462185885696764Pre_16".equals(request.getPreconditionId()))
                checkIGl1462185885696764Pre16(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    private void oGENERARSOLICITUD2Preconditions(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Request request) 
            throws ModelException {
        checkIGl1462185885696764Pre14(request);
        checkIGl1462185885696764Pre15(request);
        checkIGl1462185885696764Pre12(request);
        checkIGl1462185885696764Pre13(request);
        checkIGl1462185885696764Pre16(request);
    }

    private void checkIGl1462185885696764Pre14 (
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Request request) 
            throws ModelException {
        // pt_p_SolicitudAE.Estado = 1

        Solicitud oGENERARSOLICITUD2ptpSolicitudAE = request.getOGENERARSOLICITUD2ptpSolicitudAEInstance();
        boolean preconditionHolds = (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudEstado()).equals(Long.valueOf(1));
        if (!preconditionHolds) throw new PreconditionException("La solicitud ya ha sido generada");
    }

    private void checkIGl1462185885696764Pre15 (
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Request request) 
            throws ModelException {
        // pt_p_SolicitudAE.Estado <> 8

        Solicitud oGENERARSOLICITUD2ptpSolicitudAE = request.getOGENERARSOLICITUD2ptpSolicitudAEInstance();
        boolean preconditionHolds = !(oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudEstado()).equals(Long.valueOf(8));
        if (!preconditionHolds) throw new PreconditionException("La solicitud ya ha sido enviada a BH");
    }

    private void checkIGl1462185885696764Pre12 (
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Request request) 
            throws ModelException {
        // pt_p_SolicitudAE.Ae_solicitudes.PlanT.EsVoluntario = false OR pt_p_SolicitudAE.EstadoDoc = 2

        Solicitud oGENERARSOLICITUD2ptpSolicitudAE = request.getOGENERARSOLICITUD2ptpSolicitudAEInstance();
        boolean preconditionHolds = oGENERARSOLICITUD2ptpSolicitudAE.getAesolicitudes().getPlanT().getPlanesEsVoluntario().equals(false) || (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudEstadoDoc()).equals(Long.valueOf(2));
        if (!preconditionHolds) throw new PreconditionException("La documentación de un plan voluntario debe estar aprobada para poder generar el contrato");
    }

    private void checkIGl1462185885696764Pre13 (
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Request request) 
            throws ModelException {
        // pt_p_SolicitudAE.Tramite = 1 OR  pt_p_SolicitudAE.EstadoDoc = 2

        Solicitud oGENERARSOLICITUD2ptpSolicitudAE = request.getOGENERARSOLICITUD2ptpSolicitudAEInstance();
        boolean preconditionHolds = (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTramite()).equals(Long.valueOf(1)) || (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudEstadoDoc()).equals(Long.valueOf(2));
        if (!preconditionHolds) throw new PreconditionException("La documentación de un contrato de inclusión debe estar aprobada para poder generar el contrato");
    }

    private void checkIGl1462185885696764Pre16 (
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Request request) 
            throws ModelException {
        // pt_p_SolicitudAE.drvPuedeReplicar = TRUE

        Solicitud oGENERARSOLICITUD2ptpSolicitudAE = request.getOGENERARSOLICITUD2ptpSolicitudAEInstance();
        boolean preconditionHolds = oGENERARSOLICITUD2ptpSolicitudAE.getSolicituddrvPuedeReplicar().equals(true);
        if (!preconditionHolds) throw new PreconditionException("Deben aprobarse todos los documentos antes de generar la solicitud");
    }

    /*
     * Entry point for global service TGCREARPERSONACORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACOREResponse tGCREARPERSONACORE(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACORERequest request) {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACOREResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACOREResponse();
        try {
            response = globalServicesSrv.tGCREARPERSONACORETxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGCREARPERSONACORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACOREResponse tGCREARPERSONACORETxn(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACORERequest request) throws ModelException, SystemException {
        request.setTGCREARPERSONACOREptPersonaInstance(personaSrv.getByOIDWithHV(request.getTGCREARPERSONACOREptPersona()));
        request.setTGCREARPERSONACOREptProtDatosInstance(proteccionDatosSrv.getByOIDWithHV(request.getTGCREARPERSONACOREptProtDatos()));
        return globalServicesSrv.tGCREARPERSONACOREImplementation(request);
    }

    /*
     * Implements global service TGCREARPERSONACORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACOREResponse tGCREARPERSONACOREImplementation(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACORERequest request) throws ModelException, SystemException {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACOREResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACOREResponse();
        Persona tGCREARPERSONACOREptPersona = request.getTGCREARPERSONACOREptPersonaInstance();
        ProteccionDatos tGCREARPERSONACOREptProtDatos = request.getTGCREARPERSONACOREptProtDatosInstance();

        CrearActualizarPersonaRequest requestCrearPersona = new CrearActualizarPersonaRequest();
        if (request.getTGCREARPERSONACOREptPersonaInstance().getUsuarios() != null && !request.getTGCREARPERSONACOREptPersonaInstance().getUsuarios().isNull()) {
        	requestCrearPersona.setNumSolicitud( request.getTGCREARPERSONACOREptPersonaInstance().getUsuarios().getSolicitud().getSolicitudNumRadicado().toString());	
        }
        if (tGCREARPERSONACOREptProtDatos != null && !tGCREARPERSONACOREptProtDatos.isNull() 
        		&& tGCREARPERSONACOREptProtDatos.getSolicitud() != null && !tGCREARPERSONACOREptProtDatos.getSolicitud().isNull()) {
        	requestCrearPersona.setNumSolicitud(tGCREARPERSONACOREptProtDatos.getSolicitud().getSolicitudNumRadicado().toString());
        }
        requestCrearPersona.setTipoIdentificacion(request.getTGCREARPERSONACOREptPersonaInstance().getTipoIdentificacion().getTipoIdentificacionNombreCorto());
        requestCrearPersona.setNumeroIdentificacion(request.getTGCREARPERSONACOREptPersonaInstance().getPersonaNumIdentificacion());
        requestCrearPersona.setFechaNacimiento(request.getTGCREARPERSONACOREptPersonaInstance().getPersonaFechaNacimiento());
        requestCrearPersona.setGenero(request.getTGCREARPERSONACOREptPersonaInstance().getPersonaGenero());
        requestCrearPersona.setEstadoCivil(request.getTGCREARPERSONACOREptPersonaInstance().getEstadoCivil().getEstadoCivilCodigo().toString());
        requestCrearPersona.setPrimerNombre(request.getTGCREARPERSONACOREptPersonaInstance().getPersonaPrimerNombre());
        requestCrearPersona.setSegundoNombre(request.getTGCREARPERSONACOREptPersonaInstance().getPersonaSegundoNombre());
        requestCrearPersona.setPrimerApellido(request.getTGCREARPERSONACOREptPersonaInstance().getPersonaPrimerApellido());
        requestCrearPersona.setSegundoApellido(request.getTGCREARPERSONACOREptPersonaInstance().getPersonaSegundoApellido());
        requestCrearPersona.setCodigoMunicioNacimiento(
        		request.getTGCREARPERSONACOREptPersonaInstance().getMunicipioNacimiento() != null && !request.getTGCREARPERSONACOREptPersonaInstance().getMunicipioNacimiento().isNull() ? 
				request.getTGCREARPERSONACOREptPersonaInstance().getMunicipioNacimiento().getMunicipioCodigoLegal() : null);
        requestCrearPersona.setCodigoDepartamentoNacimiento(
        		request.getTGCREARPERSONACOREptPersonaInstance().getDepartamentoNacimiento() != null && !request.getTGCREARPERSONACOREptPersonaInstance().getDepartamentoNacimiento().isNull() ?
        		request.getTGCREARPERSONACOREptPersonaInstance().getDepartamentoNacimiento().getDepartamentoCodigoLegal() : null);
        requestCrearPersona.setForeing(request.getTGCREARPERSONACOREptPersonaInstance().getPersonaIndicadorExtranjero());

        // Direcciones
        boolean tieneDirResidencia = false;
        boolean tieneDirCorrespondencia = false;
        for (Direccion dirPersona : request.getTGCREARPERSONACOREptPersonaInstance().getDirecciones()) {
        	if (dirPersona.getTipoDireccion().getTipoDireccionEsResidencia()) {
        		requestCrearPersona.setDireccionResidencia(dirPersona.getDireccionDireccionCompleta());
        		requestCrearPersona.setMunicipioResidencia(dirPersona.getMunicipio().getMunicipioCodigoLegal());
        		requestCrearPersona.setDepartamentoResidencia(dirPersona.getDepartamento().getDepartamentoCodigoLegal());
        		if (dirPersona.getPais() != null && !dirPersona.getPais().isNull()) {
        			requestCrearPersona.setPaisResidencia(dirPersona.getPais().getPaisCodigo());
        		}
        		tieneDirResidencia = true;
        	} else {
        		requestCrearPersona.setDireccionCorrespondencia(dirPersona.getDireccionDireccionCompleta());
        		requestCrearPersona.setMunicipioCorrespondencia(dirPersona.getMunicipio().getMunicipioCodigoLegal());
        		requestCrearPersona.setDepartamentoCorrespondencia(dirPersona.getDepartamento().getDepartamentoCodigoLegal());
        		if (dirPersona.getPais() != null && !dirPersona.getPais().isNull()) {
        			requestCrearPersona.setPaisCorrespondencia(dirPersona.getPais().getPaisCodigo());
        		}
        		tieneDirCorrespondencia = true;
        	}
        }
        
        // Telefonos, solo si tiene la direccin correspondiente. Para evitar errores en BH
        if (tieneDirResidencia) {
	        requestCrearPersona.setTelefonoFijoResidencia(request.getTGCREARPERSONACOREptPersonaInstance().getPersonaTelefonoFijo());
	        requestCrearPersona.setTelefonoCelularResidencia(request.getTGCREARPERSONACOREptPersonaInstance().getPersonaTelefonoCelular());
        }
        requestCrearPersona.setCorreoElectronico(request.getTGCREARPERSONACOREptPersonaInstance().getPersonaCorreoElectronico());

        if (tieneDirCorrespondencia) {
	        if (request.getTGCREARPERSONACOREptPersonaInstance().getSolicitudesContratante() != null &&
	        		!request.getTGCREARPERSONACOREptPersonaInstance().getSolicitudesContratante().isEmpty()) {
	        	for (Solicitud solicitud : request.getTGCREARPERSONACOREptPersonaInstance().getSolicitudesContratante()) {
	        		if (solicitud.getContratante() != null && !solicitud.getContratante().isNull()) {
	        			requestCrearPersona.setTelefonoFijoCorrespondencia(solicitud.getContratante().getContratanteTelefonoFijo());
	        			requestCrearPersona.setTelefonoCelularCorrespondencia(solicitud.getContratante().getContratanteTelefonoCelular());
	        		}
	        	}
	        }
	
	        if (request.getTGCREARPERSONACOREptPersonaInstance().getSolicitudesTitular() != null &&
	        		!request.getTGCREARPERSONACOREptPersonaInstance().getSolicitudesTitular().isEmpty()) {
	        	for (Solicitud solicitud : request.getTGCREARPERSONACOREptPersonaInstance().getSolicitudesTitular()) {
	        		if (solicitud.getTitular() != null && !solicitud.getTitular().isNull()) {
	        			requestCrearPersona.setTelefonoFijoCorrespondencia(solicitud.getTitular().getTitularTelefonoFijoDC());
	        			requestCrearPersona.setTelefonoCelularCorrespondencia(solicitud.getTitular().getTitularTelefonoCelularDC());
	        		}
	        	}
	        }
        }
        
        CrearActualizarPersonaResponse crearPersonaResponse = bhCallService.crearPersona(requestCrearPersona );
		
		String result = "";
		if (crearPersonaResponse.isExito()) {
	        // Retornamos el codigo asignado por el CORE a la nueva persona
			result = "OK" + crearPersonaResponse.getCodigoPersona();
		} else {
			result = crearPersonaResponse.getTextoError();
		}
        

        response.setTGCREARPERSONACOREpsSalida(result);
        return response;
    }

    /*
     * Entry point for global service TGACTPERSONAENCORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCOREResponse tGACTPERSONAENCORE(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCORERequest request) {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCOREResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCOREResponse();
        try {
            response = globalServicesSrv.tGACTPERSONAENCORETxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGACTPERSONAENCORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCOREResponse tGACTPERSONAENCORETxn(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCORERequest request) throws ModelException, SystemException {
        request.setTGACTPERSONAENCOREptPersonaInstance(personaSrv.getByOIDWithHV(request.getTGACTPERSONAENCOREptPersona()));
        request.setTGACTPERSONAENCOREptPrtoDatosInstance(proteccionDatosSrv.getByOIDWithHV(request.getTGACTPERSONAENCOREptPrtoDatos()));
        request.setTGACTPERSONAENCOREptSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getTGACTPERSONAENCOREptSolicitud()));
        return globalServicesSrv.tGACTPERSONAENCOREImplementation(request);
    }

    /*
     * Implements global service TGACTPERSONAENCORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCOREResponse tGACTPERSONAENCOREImplementation(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCORERequest request) throws ModelException, SystemException {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCOREResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCOREResponse();
        Persona tGACTPERSONAENCOREptPersona = request.getTGACTPERSONAENCOREptPersonaInstance();
        ProteccionDatos tGACTPERSONAENCOREptPrtoDatos = request.getTGACTPERSONAENCOREptPrtoDatosInstance();
        Solicitud tGACTPERSONAENCOREptSolicitud = request.getTGACTPERSONAENCOREptSolicitudInstance();

        CrearActualizarPersonaRequest requestActualizar = new CrearActualizarPersonaRequest();
        requestActualizar.setNumSolicitud(tGACTPERSONAENCOREptSolicitud.getSolicitudNumRadicado().toString());
        requestActualizar.setCodigo(tGACTPERSONAENCOREptPersona.getPersonaCodigo().toString());
        requestActualizar.setTipoIdentificacion(tGACTPERSONAENCOREptPersona.getTipoIdentificacion().getTipoIdentificacionNombreCorto());
        requestActualizar.setNumeroIdentificacion(tGACTPERSONAENCOREptPersona.getPersonaNumIdentificacion());
        requestActualizar.setFechaNacimiento(tGACTPERSONAENCOREptPersona.getPersonaFechaNacimiento());
        requestActualizar.setGenero(tGACTPERSONAENCOREptPersona.getPersonaGenero());
        requestActualizar.setEstadoCivil(tGACTPERSONAENCOREptPersona.getEstadoCivil() != null ? 
        		tGACTPERSONAENCOREptPersona.getEstadoCivil().getEstadoCivilCodigo().toString() : null);
        requestActualizar.setPrimerNombre(tGACTPERSONAENCOREptPersona.getPersonaPrimerNombre());
        requestActualizar.setSegundoNombre(tGACTPERSONAENCOREptPersona.getPersonaSegundoNombre());
        requestActualizar.setPrimerApellido(tGACTPERSONAENCOREptPersona.getPersonaPrimerApellido());
        requestActualizar.setSegundoApellido(tGACTPERSONAENCOREptPersona.getPersonaSegundoApellido());
        requestActualizar.setCodigoMunicioNacimiento(tGACTPERSONAENCOREptPersona.getMunicipioNacimiento() != null ?
        		tGACTPERSONAENCOREptPersona.getMunicipioNacimiento().getMunicipioCodigoLegal() : null);
        requestActualizar.setCodigoDepartamentoNacimiento(tGACTPERSONAENCOREptPersona.getDepartamentoNacimiento() != null ?
        		tGACTPERSONAENCOREptPersona.getDepartamentoNacimiento().getDepartamentoCodigoLegal() : null);
        requestActualizar.setForeing(tGACTPERSONAENCOREptPersona.getPersonaIndicadorExtranjero());

        requestActualizar.setCorreoElectronico(tGACTPERSONAENCOREptPersona.getPersonaCorreoElectronico());
        
        // Direcciones
        boolean tieneDirResidencia = false;
        boolean tieneDirCorrespondencia = false;
        for (Direccion dir : tGACTPERSONAENCOREptPersona.getDirecciones()) {
        	if (dir.getTipoDireccion().getTipoDireccionEsResidencia()) {
        		requestActualizar.setDepartamentoResidencia(dir.getDepartamento() != null && !dir.getDepartamento().isNull() ? dir.getDepartamento().getDepartamentoCodigoLegal() : null);
        		requestActualizar.setMunicipioResidencia(dir.getMunicipio() != null && !dir.getMunicipio().isNull() ? dir.getMunicipio().getMunicipioCodigoLegal() : null);
        		//requestActualizar.setPaisResidencia("");
        		requestActualizar.setDireccionResidencia(dir.getDireccionDireccionCompleta());
        		tieneDirResidencia = true;
        	}
        	if (dir.getTipoDireccion().getTipoDireccionEsCorrespondencia()) {
        		requestActualizar.setDepartamentoCorrespondencia(dir.getDepartamento() != null && !dir.getDepartamento().isNull() ? dir.getDepartamento().getDepartamentoCodigoLegal() : null);
        		requestActualizar.setMunicipioCorrespondencia(dir.getMunicipio() != null && !dir.getMunicipio().isNull() ? dir.getMunicipio().getMunicipioCodigoLegal() : null);
        		//requestActualizar.setPaisResidencia("");
        		requestActualizar.setDireccionCorrespondencia(dir.getDireccionDireccionCompleta());
        		tieneDirCorrespondencia = true;
        	}
        }
        
        // Telefonos de residencia
        if (tieneDirResidencia) {
			requestActualizar.setTelefonoFijoResidencia(tGACTPERSONAENCOREptPersona.getPersonaTelefonoFijo());
			requestActualizar.setTelefonoCelularResidencia(tGACTPERSONAENCOREptPersona.getPersonaTelefonoCelular());
        }
        
        // Telefonos de correspondencia
        if (tieneDirCorrespondencia) {
			// Si es el contratante
			if (tGACTPERSONAENCOREptSolicitud.getContratante() != null && !tGACTPERSONAENCOREptSolicitud.getContratante().isNull() && 
					tGACTPERSONAENCOREptSolicitud.getContratante().getContratanteNumIdentificacion().equalsIgnoreCase(tGACTPERSONAENCOREptPersona.getPersonaNumIdentificacion()) &&
					tGACTPERSONAENCOREptSolicitud.getContratante().getTipoIdentificacion().getTipoIdentificacionCodigo().equals(tGACTPERSONAENCOREptPersona.getTipoIdentificacion().getTipoIdentificacionCodigo() )) {
				requestActualizar.setTelefonoFijoCorrespondencia(tGACTPERSONAENCOREptSolicitud.getContratante().getContratanteTelefonoFijo() );
				requestActualizar.setTelefonoCelularCorrespondencia( tGACTPERSONAENCOREptSolicitud.getContratante().getContratanteTelefonoCelular());
			}
			// Si es el Titular
			if (tGACTPERSONAENCOREptSolicitud.getTitular() != null && !tGACTPERSONAENCOREptSolicitud.getTitular().isNull() && 
					tGACTPERSONAENCOREptSolicitud.getTitular().getTitularNumIdentificacion().equalsIgnoreCase(tGACTPERSONAENCOREptPersona.getPersonaNumIdentificacion()) &&
					tGACTPERSONAENCOREptSolicitud.getTitular().getTipoIdentificacion().getTipoIdentificacionCodigo().equals(tGACTPERSONAENCOREptPersona.getTipoIdentificacion().getTipoIdentificacionCodigo() )) {
				requestActualizar.setTelefonoFijoCorrespondencia(tGACTPERSONAENCOREptSolicitud.getTitular().getTitularTelefonoFijoDC() );
				requestActualizar.setTelefonoCelularCorrespondencia( tGACTPERSONAENCOREptSolicitud.getTitular().getTitularTelefonoCelularDC());
			}
        }
        
        CrearActualizarPersonaResponse crearPersonaResponse = bhCallService.actualizarPersona(requestActualizar );
        
		response.setTGACTPERSONAENCOREpsSalida(crearPersonaResponse.getTextoError());
        return response;
    }

    /*
     * Entry point for global service TGCREARCONTRATOCORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCOREResponse tGCREARCONTRATOCORE(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCORERequest request) {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCOREResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCOREResponse();
        try {
            response = globalServicesSrv.tGCREARCONTRATOCORETxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGCREARCONTRATOCORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCOREResponse tGCREARCONTRATOCORETxn(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCORERequest request) throws ModelException {
        request.setTGCREARCONTRATOCOREptSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getTGCREARCONTRATOCOREptSolicitud()));
        return globalServicesSrv.tGCREARCONTRATOCOREImplementation(request);
    }

    /*
     * Implements global service TGCREARCONTRATOCORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCOREResponse tGCREARCONTRATOCOREImplementation(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCORERequest request) throws ModelException {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCOREResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCOREResponse();
        Solicitud tGCREARCONTRATOCOREptSolicitud = request.getTGCREARCONTRATOCOREptSolicitudInstance();

        // Crear contrato
        Boolean psError = Boolean.FALSE;
        String usuario = getAgentFuncionario().getFuncionarioUsuario();
        DatoRespuestaContrato rptaCrearContrato = null;
        try {
            rptaCrearContrato = contratoMPRestUtils.crearContratoMP(tGCREARCONTRATOCOREptSolicitud, usuario, this.userFunctionsSrv);
                	
            if (rptaCrearContrato.getErrorCode() != null && !rptaCrearContrato.getErrorCode().isEmpty()) {
            	psError = Boolean.TRUE;
            	// Grabamos los errores en ErroresWS
        		for (String msgError : rptaCrearContrato.getMensajesDeError()){
        			guardarErrorWS(tGCREARCONTRATOCOREptSolicitud, msgError);
        		}
            } else {
            	psError= Boolean.FALSE;
            }
        } catch(ModelException | SystemException e) {
        	psError = Boolean.TRUE;
			org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e.getMessage());
			guardarErrorWS(tGCREARCONTRATOCOREptSolicitud, SystemException.GENERIC_EXCEPTION_MESSAGE);
		} catch(Exception e) {
			org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e.getMessage());
			guardarErrorWS(tGCREARCONTRATOCOREptSolicitud, SystemException.GENERIC_EXCEPTION_MESSAGE);
        	psError = Boolean.TRUE;
		}

        // Guardamos las llamadas realizadas a BH
        guardarLlamadasBH(rptaCrearContrato);
        
        response.setTGCREARCONTRATOCOREpsError(psError);
        return response;
    }

    private void guardarLlamadasBH(DatoRespuestaContrato rptaCrearContrato) {
    	if (rptaCrearContrato != null) {
        	for (DatoLogLlamadaBH llamada : rptaCrearContrato.getDatosLlamadasBH()) {
        		try {
	        		com.keralty.aegenerasolicitud.viewmodel.logllamadasbh.siu.CreateinstanceRequest request = new com.keralty.aegenerasolicitud.viewmodel.logllamadasbh.siu.CreateinstanceRequest();
	        		request.setLogLlamadasBHcreateinstancepatrCapacidad(llamada.getCapacidad());
	        		request.setLogLlamadasBHcreateinstancepatrFechaHora(llamada.getFechaHora());
	        		request.setLogLlamadasBHcreateinstancepatrPeticion(llamada.getPeticion());
	        		request.setLogLlamadasBHcreateinstancepatrRespuesta(llamada.getRespuesta());
	        		request.setLogLlamadasBHcreateinstancepatrServicio(llamada.getServicio());
	        		request.setLogLlamadasBHcreateinstancepatrSolicitud(llamada.getSolicitud());
	        		request.setLogLlamadasBHcreateinstancepatrTextoError(llamada.getTextoError());
	        		request.setLogLlamadasBHcreateinstancepatrTiempoEjecucion(llamada.getTiempoEjecucion());
	        		request.setLogLlamadasBHcreateinstancepatrTipoServicio(llamada.getTipoServicio());
	        		request.setLogLlamadasBHcreateinstancepatrUsuario(llamada.getUsuario());
					logLlamadasBHSrv.createinstanceImplementation(request );
        		} catch(Exception e) {
        			org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error("ERR Guardando llamada BH. " + e.getMessage());
        		}
        	}
        }
	}

	private void guardarErrorWS(Solicitud solicitud, String msgError) {
		CrearRequest reqCrear = new CrearRequest();
		reqCrear.setErrorWScrearpagrSolicitudInstance(solicitud);
		reqCrear.setErrorWScrearpatrDescripcion(msgError);
		try {
			ServiceResponse resp = errorWSSrv.crearImplementation(reqCrear);
			if (!"000".equalsIgnoreCase(resp.getResultCode())) {
				org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(resp.getResultDescription());
			}
		} catch(Exception e) {
			org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e.getMessage());
		}
    }

    /*
     * Entry point for global service TGINCLUSIONCONTRATOCORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCOREResponse tGINCLUSIONCONTRATOCORE(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCORERequest request) {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCOREResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCOREResponse();
        try {
            response = globalServicesSrv.tGINCLUSIONCONTRATOCORETxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGINCLUSIONCONTRATOCORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCOREResponse tGINCLUSIONCONTRATOCORETxn(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCORERequest request) throws ModelException {
        request.setTGINCLUSIONCONTRATOCOREptSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getTGINCLUSIONCONTRATOCOREptSolicitud()));
        return globalServicesSrv.tGINCLUSIONCONTRATOCOREImplementation(request);
    }

    /*
     * Implements global service TGINCLUSIONCONTRATOCORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCOREResponse tGINCLUSIONCONTRATOCOREImplementation(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCORERequest request) throws ModelException {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCOREResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCOREResponse();
        Solicitud tGINCLUSIONCONTRATOCOREptSolicitud = request.getTGINCLUSIONCONTRATOCOREptSolicitudInstance();

        Boolean psError = Boolean.FALSE;
        String usuario = getAgentFuncionario().getFuncionarioUsuario();
        DatoRespuestaContrato rptaCrearContrato = null;
        try {
        	rptaCrearContrato = contratoMPRestUtils.inclusionBeneficiario(tGINCLUSIONCONTRATOCOREptSolicitud, usuario, this.userFunctionsSrv);
                	
            if (rptaCrearContrato.getErrorCode() != null && !rptaCrearContrato.getErrorCode().isEmpty()) {
            	psError = Boolean.TRUE;
            	// Grabamos los errores en ErroresWS
        		for (String msgError : rptaCrearContrato.getMensajesDeError()){
        			guardarErrorWS(tGINCLUSIONCONTRATOCOREptSolicitud, msgError);
        		}
            } else {
            	psError= Boolean.FALSE;
            }
        } catch(ModelException | SystemException e) {
        	psError = Boolean.TRUE;
			org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e.getMessage());
			guardarErrorWS(tGINCLUSIONCONTRATOCOREptSolicitud, SystemException.GENERIC_EXCEPTION_MESSAGE);
		} catch(Exception e) {
			org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e.getMessage());
			guardarErrorWS(tGINCLUSIONCONTRATOCOREptSolicitud, SystemException.GENERIC_EXCEPTION_MESSAGE);
        	psError = Boolean.TRUE;
		}

        // Guardamos las llamadas realizadas a BH
        guardarLlamadasBH(rptaCrearContrato);

        response.setTGINCLUSIONCONTRATOCOREpsError(psError);
        return response;        
    }

    /*
     * Entry point for global service TGNOTIFICARCREAR
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tGNOTIFICARCREAR(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGNOTIFICARCREARRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = globalServicesSrv.tGNOTIFICARCREARTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGNOTIFICARCREAR
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tGNOTIFICARCREARTxn(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGNOTIFICARCREARRequest request) throws ModelException, SystemException {
        return globalServicesSrv.tGNOTIFICARCREARImplementation(request);
    }

    /*
     * Implements global service TGNOTIFICARCREAR
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tGNOTIFICARCREARImplementation(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGNOTIFICARCREARRequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        String tGNOTIFICARCREARptDatosContrato = request.getTGNOTIFICARCREARptDatosContrato();

        request.checkArguments();

        /* Action 1: IF (1 > 2) */
        boolean ifCondition1 = Long.valueOf(1) > Long.valueOf(2);
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAERequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAERequest();
            step10Request.setContratoCTNEWDESDEAEptSolicitudInstance(userFunctionsSrv.fugetSolicitudByNumeroFun("0"));
            step10Request.setContratoCTNEWDESDEAEptNumFamiliaCORE("0");
            contratoCSrv.tNEWDESDEAEImplementation(step10Request);
            
            
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TPOSTNOTICREARRequest step11Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TPOSTNOTICREARRequest();
            step11Request.setContratoCTPOSTNOTICREARpthisContratoCInstance(userFunctionsSrv.fugetSolicitudByNumeroFun("0").getContratoC());
            step11Request.setContratoCTPOSTNOTICREARptpeNumContrato("0");
            step11Request.setContratoCTPOSTNOTICREARptpeFechaIniVigencia(STDFunctions.systemDateFun());
            step11Request.setContratoCTPOSTNOTICREARptpeFechaFinVigencia(STDFunctions.systemDateFun());
            step11Request.setContratoCTPOSTNOTICREARptNumFamilia("0");
            step11Request.setContratoCTPOSTNOTICREARptAsesorSolicitudInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptPeriodicidadInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptProductoInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptPlanInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptCodTipoContrato(Long.valueOf(0));
            step11Request.setContratoCTPOSTNOTICREARptDireccionCompletaContrato("");
            step11Request.setContratoCTPOSTNOTICREARptMunicipioDireccionInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptTelefonoContratante(null);
            step11Request.setContratoCTPOSTNOTICREARptCelularContratante(null);
            step11Request.setContratoCTPOSTNOTICREARptTipoIdContratanteInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptNumIdentificacionContratante("");
            step11Request.setContratoCTPOSTNOTICREARptNombreCompletoContratante("");
            step11Request.setContratoCTPOSTNOTICREARptDireccionCompletaTitular("");
            step11Request.setContratoCTPOSTNOTICREARptTipoIdTitularInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptNumIdentificacionTitular("");
            step11Request.setContratoCTPOSTNOTICREARptNombreCompletoTitular("");
            step11Request.setContratoCTPOSTNOTICREARptMunicipioDireccionTitularInstance(null);
            contratoCSrv.tPOSTNOTICREARImplementation(step11Request);
            for(FamiliaC step1012Entity : globalServicesRepository.assocOperator117()) {
                com.keralty.aegenerasolicitud.viewmodel.familiac.siu.TCREARACTBENEFRequest step12Request = new com.keralty.aegenerasolicitud.viewmodel.familiac.siu.TCREARACTBENEFRequest();
                step12Request.setFamiliaCTCREARACTBENEFpthisFamiliaCInstance(step1012Entity);
                step12Request.setFamiliaCTCREARACTBENEFptpagrEPSCInstance(null);
                step12Request.setFamiliaCTCREARACTBENEFptpagrCompanyiaMPCInstance(null);
                step12Request.setFamiliaCTCREARACTBENEFptpagrParentescoCInstance(userFunctionsSrv.fugetParentescoTitularFun());
                step12Request.setFamiliaCTCREARACTBENEFptpagrEstadoBeneficiarioCInstance(null);
                step12Request.setFamiliaCTCREARACTBENEFptpatrCodigoTipoUsuario("0");
                step12Request.setFamiliaCTCREARACTBENEFptpatrIndicadorBBGestacion(Boolean.FALSE);
                step12Request.setFamiliaCTCREARACTBENEFptpatrCodUsuarioBancoRep(null);
                step12Request.setFamiliaCTCREARACTBENEFptpatrFechaAntiguedad(null);
                step12Request.setFamiliaCTCREARACTBENEFptpatrFechaAntiguedadRealOSI(null);
                step12Request.setFamiliaCTCREARACTBENEFptpatrEstadoMedico(null);
                step12Request.setFamiliaCTCREARACTBENEFptpatrTienePreexistencias(Boolean.FALSE);
                step12Request.setFamiliaCTCREARACTBENEFptpatrFechaInicioVigencia(null);
                step12Request.setFamiliaCTCREARACTBENEFptpatrFechaFinVigencia(STDFunctions.systemDateFun());
                step12Request.setFamiliaCTCREARACTBENEFptpatrNumUsuario(Long.valueOf(0));
                step12Request.setFamiliaCTCREARACTBENEFptptTipoIdInstance(userFunctionsSrv.fugetTipoIdentificacionFun("0"));
                step12Request.setFamiliaCTCREARACTBENEFptptNumIdentificacion("");
                step12Request.setFamiliaCTCREARACTBENEFptptNombreCompleto("");
                step12Request.setFamiliaCTCREARACTBENEFptptCodigoCORE(Long.valueOf(0));
                familiaCSrv.tCREARACTBENEFImplementation(step12Request);
            }
            for(PersonaC step1013Entity : globalServicesRepository.assocOperator118()) {
                com.keralty.aegenerasolicitud.viewmodel.preexistenciac.siu.CrearRequest step13Request = new com.keralty.aegenerasolicitud.viewmodel.preexistenciac.siu.CrearRequest();
                step13Request.setPreexistenciaCcrearpagrPersonaCInstance(step1013Entity);
                step13Request.setPreexistenciaCcrearpatrDescripcion("");
                step13Request.setPreexistenciaCcrearpatrFecInicio(STDFunctions.systemDateFun());
                step13Request.setPreexistenciaCcrearpatrFecFin(STDFunctions.systemDateFun());
                step13Request.setPreexistenciaCcrearpatrObservacion("");
                preexistenciaCSrv.crearImplementation(step13Request);
            }
            for(BeneficiarioC step1014Entity : globalServicesRepository.assocOperator119()) {
                com.keralty.aegenerasolicitud.viewmodel.conceptoc.siu.CrearRequest step14Request = new com.keralty.aegenerasolicitud.viewmodel.conceptoc.siu.CrearRequest();
                step14Request.setConceptoCcrearpagrBeneficiarioCInstance(step1014Entity);
                step14Request.setConceptoCcrearpatrcodConceptoContrato("");
                step14Request.setConceptoCcrearpatrnombreConcepto("");
                step14Request.setConceptoCcrearpatrporcentajeConcepto(Utilities.convertToDouble(Long.valueOf(0)));
                step14Request.setConceptoCcrearpatrvalorConcepto(Utilities.convertToDouble(Long.valueOf(0)));
                conceptoCSrv.crearImplementation(step14Request);
            }
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARCREARRequest step15Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARCREARRequest();
            step15Request.setSolicitudTNOTIFICARCREARpthisSolicitudInstance(userFunctionsSrv.fugetSolicitudByNumeroFun("0"));
            step15Request.setSolicitudTNOTIFICARCREARptConfiCierreSolicitud(Boolean.FALSE);
            step15Request.setSolicitudTNOTIFICARCREARptNumFamiliaCORE(null);
            solicitudSrv.tNOTIFICARCREARImplementation(step15Request);
        }
        return response;
    }

    /*
     * Entry point for global service TGNOTIFICARINCLUSION
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tGNOTIFICARINCLUSION(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGNOTIFICARINCLUSIONRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = globalServicesSrv.tGNOTIFICARINCLUSIONTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGNOTIFICARINCLUSION
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tGNOTIFICARINCLUSIONTxn(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGNOTIFICARINCLUSIONRequest request) throws ModelException, SystemException {
        return globalServicesSrv.tGNOTIFICARINCLUSIONImplementation(request);
    }

    /*
     * Implements global service TGNOTIFICARINCLUSION
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tGNOTIFICARINCLUSIONImplementation(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGNOTIFICARINCLUSIONRequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        String tGNOTIFICARINCLUSIONptDatosInclusion = request.getTGNOTIFICARINCLUSIONptDatosInclusion();

        request.checkArguments();

        /* Action 1: IF (1 > 2) */
        boolean ifCondition1 = Long.valueOf(1) > Long.valueOf(2);
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAERequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TNEWDESDEAERequest();
            step10Request.setContratoCTNEWDESDEAEptSolicitudInstance(userFunctionsSrv.fugetSolicitudByNumeroFun("0"));
            step10Request.setContratoCTNEWDESDEAEptNumFamiliaCORE("0");
            contratoCSrv.tNEWDESDEAEImplementation(step10Request);
            
            
            com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TPOSTNOTICREARRequest step11Request = new com.keralty.aegenerasolicitud.viewmodel.contratoc.siu.TPOSTNOTICREARRequest();
            step11Request.setContratoCTPOSTNOTICREARpthisContratoCInstance(userFunctionsSrv.fugetSolicitudByNumeroFun("0").getContratoC());
            step11Request.setContratoCTPOSTNOTICREARptpeNumContrato("0");
            step11Request.setContratoCTPOSTNOTICREARptpeFechaIniVigencia(STDFunctions.systemDateFun());
            step11Request.setContratoCTPOSTNOTICREARptpeFechaFinVigencia(STDFunctions.systemDateFun());
            step11Request.setContratoCTPOSTNOTICREARptNumFamilia("0");
            step11Request.setContratoCTPOSTNOTICREARptAsesorSolicitudInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptPeriodicidadInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptProductoInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptPlanInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptCodTipoContrato(Long.valueOf(0));
            step11Request.setContratoCTPOSTNOTICREARptDireccionCompletaContrato("");
            step11Request.setContratoCTPOSTNOTICREARptMunicipioDireccionInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptTelefonoContratante(null);
            step11Request.setContratoCTPOSTNOTICREARptCelularContratante(null);
            step11Request.setContratoCTPOSTNOTICREARptTipoIdContratanteInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptNumIdentificacionContratante("");
            step11Request.setContratoCTPOSTNOTICREARptNombreCompletoContratante("");
            step11Request.setContratoCTPOSTNOTICREARptDireccionCompletaTitular("");
            step11Request.setContratoCTPOSTNOTICREARptTipoIdTitularInstance(null);
            step11Request.setContratoCTPOSTNOTICREARptNumIdentificacionTitular("");
            step11Request.setContratoCTPOSTNOTICREARptNombreCompletoTitular("");
            step11Request.setContratoCTPOSTNOTICREARptMunicipioDireccionTitularInstance(null);
            contratoCSrv.tPOSTNOTICREARImplementation(step11Request);
            for(FamiliaC step1012Entity : globalServicesRepository.assocOperator120()) {
                com.keralty.aegenerasolicitud.viewmodel.familiac.siu.TCREARACTBENEFRequest step12Request = new com.keralty.aegenerasolicitud.viewmodel.familiac.siu.TCREARACTBENEFRequest();
                step12Request.setFamiliaCTCREARACTBENEFpthisFamiliaCInstance(step1012Entity);
                step12Request.setFamiliaCTCREARACTBENEFptpagrEPSCInstance(null);
                step12Request.setFamiliaCTCREARACTBENEFptpagrCompanyiaMPCInstance(null);
                step12Request.setFamiliaCTCREARACTBENEFptpagrParentescoCInstance(userFunctionsSrv.fugetParentescoTitularFun());
                step12Request.setFamiliaCTCREARACTBENEFptpagrEstadoBeneficiarioCInstance(null);
                step12Request.setFamiliaCTCREARACTBENEFptpatrCodigoTipoUsuario("0");
                step12Request.setFamiliaCTCREARACTBENEFptpatrIndicadorBBGestacion(Boolean.FALSE);
                step12Request.setFamiliaCTCREARACTBENEFptpatrCodUsuarioBancoRep(null);
                step12Request.setFamiliaCTCREARACTBENEFptpatrFechaAntiguedad(null);
                step12Request.setFamiliaCTCREARACTBENEFptpatrFechaAntiguedadRealOSI(null);
                step12Request.setFamiliaCTCREARACTBENEFptpatrEstadoMedico(null);
                step12Request.setFamiliaCTCREARACTBENEFptpatrTienePreexistencias(Boolean.FALSE);
                step12Request.setFamiliaCTCREARACTBENEFptpatrFechaInicioVigencia(null);
                step12Request.setFamiliaCTCREARACTBENEFptpatrFechaFinVigencia(STDFunctions.systemDateFun());
                step12Request.setFamiliaCTCREARACTBENEFptpatrNumUsuario(Long.valueOf(0));
                step12Request.setFamiliaCTCREARACTBENEFptptTipoIdInstance(userFunctionsSrv.fugetTipoIdentificacionFun("0"));
                step12Request.setFamiliaCTCREARACTBENEFptptNumIdentificacion("");
                step12Request.setFamiliaCTCREARACTBENEFptptNombreCompleto("");
                step12Request.setFamiliaCTCREARACTBENEFptptCodigoCORE(Long.valueOf(0));
                familiaCSrv.tCREARACTBENEFImplementation(step12Request);
            }
            for(PersonaC step1013Entity : globalServicesRepository.assocOperator121()) {
                com.keralty.aegenerasolicitud.viewmodel.preexistenciac.siu.CrearRequest step13Request = new com.keralty.aegenerasolicitud.viewmodel.preexistenciac.siu.CrearRequest();
                step13Request.setPreexistenciaCcrearpagrPersonaCInstance(step1013Entity);
                step13Request.setPreexistenciaCcrearpatrDescripcion("");
                step13Request.setPreexistenciaCcrearpatrFecInicio(STDFunctions.systemDateFun());
                step13Request.setPreexistenciaCcrearpatrFecFin(STDFunctions.systemDateFun());
                step13Request.setPreexistenciaCcrearpatrObservacion("");
                preexistenciaCSrv.crearImplementation(step13Request);
            }
            for(BeneficiarioC step1014Entity : globalServicesRepository.assocOperator122()) {
                com.keralty.aegenerasolicitud.viewmodel.conceptoc.siu.CrearRequest step14Request = new com.keralty.aegenerasolicitud.viewmodel.conceptoc.siu.CrearRequest();
                step14Request.setConceptoCcrearpagrBeneficiarioCInstance(step1014Entity);
                step14Request.setConceptoCcrearpatrcodConceptoContrato("");
                step14Request.setConceptoCcrearpatrnombreConcepto("");
                step14Request.setConceptoCcrearpatrporcentajeConcepto(Utilities.convertToDouble(Long.valueOf(0)));
                step14Request.setConceptoCcrearpatrvalorConcepto(Utilities.convertToDouble(Long.valueOf(0)));
                conceptoCSrv.crearImplementation(step14Request);
            }
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARINCLUSIONRequest step15Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARINCLUSIONRequest();
            step15Request.setSolicitudTNOTIFICARINCLUSIONpthisSolicitudInstance(userFunctionsSrv.fugetSolicitudByNumeroFun("0"));
            step15Request.setSolicitudTNOTIFICARINCLUSIONptConfiCierreSolicitud(Boolean.FALSE);
            step15Request.setSolicitudTNOTIFICARINCLUSIONptNumFamiliaCORE(null);
            solicitudSrv.tNOTIFICARINCLUSIONImplementation(step15Request);
        }
        return response;
    }

    /*
     * Entry point for global service TGNOTIFICARANULAR
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tGNOTIFICARANULAR(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGNOTIFICARANULARRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = globalServicesSrv.tGNOTIFICARANULARTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGNOTIFICARANULAR
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tGNOTIFICARANULARTxn(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGNOTIFICARANULARRequest request) throws ModelException, SystemException {
        return globalServicesSrv.tGNOTIFICARANULARImplementation(request);
    }

    /*
     * Implements global service TGNOTIFICARANULAR
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tGNOTIFICARANULARImplementation(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGNOTIFICARANULARRequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        String tGNOTIFICARANULARptDatoAnulacion = request.getTGNOTIFICARANULARptDatoAnulacion();

        request.checkArguments();

        /* Action 1: IF (1 > 2) */
        boolean ifCondition1 = Long.valueOf(1) > Long.valueOf(2);
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARANULACIONRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TNOTIFICARANULACIONRequest();
            step10Request.setSolicitudTNOTIFICARANULACIONpthisSolicitudInstance(userFunctionsSrv.fugetSolicitudByNumeroFun("0"));
            solicitudSrv.tNOTIFICARANULACIONImplementation(step10Request);
        }
        return response;
    }

    /*
     * Entry point for global service TGCREARACTPERSONACORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACOREResponse tGCREARACTPERSONACORE(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACORERequest request) {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACOREResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACOREResponse();
        try {
            response = globalServicesSrv.tGCREARACTPERSONACORETxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGCREARACTPERSONACORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACOREResponse tGCREARACTPERSONACORETxn(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACORERequest request) throws ModelException, SystemException {
        request.setTGCREARACTPERSONACOREptSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getTGCREARACTPERSONACOREptSolicitud()));
        request.setTGCREARACTPERSONACOREptPersonaInstance(personaSrv.getByOIDWithHV(request.getTGCREARACTPERSONACOREptPersona()));
        return globalServicesSrv.tGCREARACTPERSONACOREImplementation(request);
    }

    /*
     * Implements global service TGCREARACTPERSONACORE
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACOREResponse tGCREARACTPERSONACOREImplementation(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACORERequest request) throws ModelException, SystemException {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACOREResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACOREResponse();
        Solicitud tGCREARACTPERSONACOREptSolicitud = request.getTGCREARACTPERSONACOREptSolicitudInstance();
        Persona tGCREARACTPERSONACOREptPersona = request.getTGCREARACTPERSONACOREptPersonaInstance();

        request.checkArguments();

        /* Action 1: IF (ptPersona.TipoIdentificacion.NIT = false) */
        boolean personaVieneDeCORE = tGCREARACTPERSONACOREptPersona.getPersonadrvVieneDeCORE();
        boolean ifCondition1 = tGCREARACTPERSONACOREptPersona.getTipoIdentificacion().getTipoIdentificacionNIT().equals(false);
        if (ifCondition1) {
            /* Action 10: IF (ptPersona.drvVieneDeCORE = true) */
            boolean ifCondition10 = tGCREARACTPERSONACOREptPersona.getPersonadrvVieneDeCORE().equals(true);
            if (ifCondition10) {
                com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCORERequest step100Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCORERequest();
                step100Request.setTGACTPERSONAENCOREptPersonaInstance(tGCREARACTPERSONACOREptPersona);
                step100Request.setTGACTPERSONAENCOREptPrtoDatosInstance(tGCREARACTPERSONACOREptSolicitud.getProteccionDatos());
                step100Request.setTGACTPERSONAENCOREptSolicitudInstance(tGCREARACTPERSONACOREptSolicitud);
                com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCOREResponse step100Response = globalServicesSrv.tGACTPERSONAENCOREImplementation(step100Request);
                response.setTGCREARACTPERSONACOREpsSalida(step100Response.getTGACTPERSONAENCOREpsSalida());
                /* Action 101: IF (psSalida <> "") */
                boolean ifCondition101 = (response.getTGCREARACTPERSONACOREpsSalida()).length() != 0;
                if (ifCondition101) {
                    com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest step1010Request = new com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest();
                    step1010Request.setErrorWScrearpagrSolicitudInstance(tGCREARACTPERSONACOREptSolicitud);
                    step1010Request.setErrorWScrearpatrDescripcion(response.getTGCREARACTPERSONACOREpsSalida());
                    errorWSSrv.crearImplementation(step1010Request);
                }
            } else {
                com.keralty.aegenerasolicitud.viewmodel.persona.siu.DummyGetNumCodCORERequest step102Request = new com.keralty.aegenerasolicitud.viewmodel.persona.siu.DummyGetNumCodCORERequest();
                step102Request.setPersonadummyGetNumCodCOREpthisPersonaInstance(tGCREARACTPERSONACOREptPersona);
                step102Request.setPersonadummyGetNumCodCOREpSolicitudInstance(tGCREARACTPERSONACOREptSolicitud);
                com.keralty.aegenerasolicitud.viewmodel.persona.siu.DummyGetNumCodCOREResponse step102Response = personaSrv.dummyGetNumCodCOREImplementation(step102Request);
                response.setTGCREARACTPERSONACOREpsCodigoPersona(step102Response.getPersonadummyGetNumCodCOREpsCodigo());
                /* Action 103: IF (psCodigoPersona <> NULL) */
                boolean ifCondition103 = response.getTGCREARACTPERSONACOREpsCodigoPersona() != null;
                if (ifCondition103) {
                    com.keralty.aegenerasolicitud.viewmodel.persona.siu.TACTCODIGORequest step1030Request = new com.keralty.aegenerasolicitud.viewmodel.persona.siu.TACTCODIGORequest();
                    step1030Request.setPersonaTACTCODIGOpthisPersonaInstance(tGCREARACTPERSONACOREptPersona);
                    step1030Request.setPersonaTACTCODIGOptpCodigo(response.getTGCREARACTPERSONACOREpsCodigoPersona());
                    personaSrv.tACTCODIGOImplementation(step1030Request);
                    com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCORERequest step1031Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCORERequest();
                    step1031Request.setTGACTPERSONAENCOREptPersonaInstance(tGCREARACTPERSONACOREptPersona);
                    step1031Request.setTGACTPERSONAENCOREptPrtoDatosInstance(tGCREARACTPERSONACOREptSolicitud.getProteccionDatos());
                    step1031Request.setTGACTPERSONAENCOREptSolicitudInstance(tGCREARACTPERSONACOREptSolicitud);
                    com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCOREResponse step1031Response = globalServicesSrv.tGACTPERSONAENCOREImplementation(step1031Request);
                    response.setTGCREARACTPERSONACOREpsSalida(step1031Response.getTGACTPERSONAENCOREpsSalida());
                    /* Action 1032: IF (psSalida <> "") */
                    boolean ifCondition1032 = (response.getTGCREARACTPERSONACOREpsSalida()).length() != 0;
                    if (ifCondition1032) {
                        com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest step10320Request = new com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest();
                        step10320Request.setErrorWScrearpagrSolicitudInstance(tGCREARACTPERSONACOREptSolicitud);
                        step10320Request.setErrorWScrearpatrDescripcion(response.getTGCREARACTPERSONACOREpsSalida());
                        errorWSSrv.crearImplementation(step10320Request);
                    }
                } else {
                    com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACORERequest step1033Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACORERequest();
                    step1033Request.setTGCREARPERSONACOREptPersonaInstance(tGCREARACTPERSONACOREptPersona);
                    step1033Request.setTGCREARPERSONACOREptProtDatosInstance(tGCREARACTPERSONACOREptSolicitud.getProteccionDatos());
                    com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACOREResponse step1033Response = globalServicesSrv.tGCREARPERSONACOREImplementation(step1033Request);
                    response.setTGCREARACTPERSONACOREpsSalida(step1033Response.getTGCREARPERSONACOREpsSalida());
                    /* Action 1034: IF (psSalida <> "" AND strStartsWith(psSalida, "OK") = false) */
                    boolean ifCondition1034 = (response.getTGCREARACTPERSONACOREpsSalida()).length() != 0 && STDFunctions.strStartsWithFun(response.getTGCREARACTPERSONACOREpsSalida(), "OK").equals(false);
                    if (ifCondition1034) {
                        com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest step10340Request = new com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest();
                        step10340Request.setErrorWScrearpagrSolicitudInstance(tGCREARACTPERSONACOREptSolicitud);
                        step10340Request.setErrorWScrearpatrDescripcion(response.getTGCREARACTPERSONACOREpsSalida());
                        errorWSSrv.crearImplementation(step10340Request);
                    } else {
                        com.keralty.aegenerasolicitud.viewmodel.persona.siu.TACTCODIGORequest step10341Request = new com.keralty.aegenerasolicitud.viewmodel.persona.siu.TACTCODIGORequest();
                        step10341Request.setPersonaTACTCODIGOpthisPersonaInstance(tGCREARACTPERSONACOREptPersona);
                        step10341Request.setPersonaTACTCODIGOptpCodigo(STDFunctions.strToIntFun(STDFunctions.strReplaceFun(response.getTGCREARACTPERSONACOREpsSalida(), "OK", "")));
                        personaSrv.tACTCODIGOImplementation(step10341Request);
                    }
                }
            }
        }

        // Creamos el cuestionario medico

        if (tGCREARACTPERSONACOREptPersona.getUsuarios() != null && !tGCREARACTPERSONACOREptPersona.getUsuarios().isNull()) {
        	if( tGCREARACTPERSONACOREptPersona.getUsuarios().getCuestionarioP() != null && 
        		!tGCREARACTPERSONACOREptPersona.getUsuarios().getCuestionarioP().isNull()) {
        		
        		// Si tiene cuestionario medico, lo enviamos a BH
	        	CrearActualizarCuestionarioRequest cuestionarioRequest = new CrearActualizarCuestionarioRequest();
	        	cuestionarioRequest.setNumSolicitud(tGCREARACTPERSONACOREptSolicitud.getSolicitudNumRadicado().toString());
	        	cuestionarioRequest.setTipoIdentificacion(tGCREARACTPERSONACOREptPersona.getTipoIdentificacion().getTipoIdentificacionNombreCorto());
	        	cuestionarioRequest.setNumeroIdentificacion(tGCREARACTPERSONACOREptPersona.getPersonaNumIdentificacion());
	        	cuestionarioRequest.setRespuestas(new ArrayList<RespuestaCuestionario>());
	        	RespuestaCuestionario respuesta = null;
	        	for (SeccionP seccion : tGCREARACTPERSONACOREptPersona.getUsuarios().getCuestionarioP().getSecciones()) {
	        		for (PreguntaP resp :  seccion.getPreguntas()) {
	        			if (resp.getPreguntaPSeEnviaACORE()) {
	        				respuesta = new RespuestaCuestionario();
	        				respuesta.setPregunta(resp.getPreguntaPNumero().toString());
	        				respuesta.setRespuestaBool(resp.getPreguntaPRespuestaSiNo());
	        				respuesta.setRespuestaString(resp.getPreguntaPdrvRespuesta());
	        				cuestionarioRequest.addRespuesta(respuesta);
	        			}
	        		}
	        	}
	        	String resultado = bhCallService.crearActualizarCuestionarioMedico(cuestionarioRequest);
	        	if (resultado != null && !resultado.isEmpty()) {
	                com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest errorRequest = new com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest();
	                errorRequest.setErrorWScrearpagrSolicitudInstance(tGCREARACTPERSONACOREptSolicitud);
	                errorRequest.setErrorWScrearpatrDescripcion("Error en cuestionario medico de la persona " + 
		                tGCREARACTPERSONACOREptPersona.getTipoIdentificacion().getTipoIdentificacionNombreCorto() + 
		                tGCREARACTPERSONACOREptPersona.getPersonaNumIdentificacion());
	                errorWSSrv.crearImplementation(errorRequest);
	        	}
        	} else if (!personaVieneDeCORE &&
        			    tGCREARACTPERSONACOREptSolicitud.getSolicituddrvReqCuesMed() &&
				        tGCREARACTPERSONACOREptPersona.getUsuarios().getTipoIdentificacion().getTipoIdentificacionMenorSinIdentificar()) {

				// Si no tiene cuestionario medico
        		// Para personas que no existan en BH.
        		// Si el plan indica que debe tenerlo 
				//  mandamos al CORE la pregunta 108 con el texto que corresponda segun parametrizacion

        		String textoComentarios = userFunctionsSrv.fugetValorParametroConfFun("COMENTARIOSPREGUNTA108");
	        	CrearActualizarCuestionarioRequest cuestionarioRequest = new CrearActualizarCuestionarioRequest();
	        	cuestionarioRequest.setNumSolicitud(tGCREARACTPERSONACOREptSolicitud.getSolicitudNumRadicado().toString());
	        	cuestionarioRequest.setTipoIdentificacion(tGCREARACTPERSONACOREptPersona.getTipoIdentificacion().getTipoIdentificacionNombreCorto());
	        	cuestionarioRequest.setNumeroIdentificacion(tGCREARACTPERSONACOREptPersona.getPersonaNumIdentificacion());
	        	cuestionarioRequest.setRespuestas(new ArrayList<RespuestaCuestionario>());
	        	RespuestaCuestionario respuesta = new RespuestaCuestionario();
				respuesta.setPregunta("108");
				respuesta.setRespuestaBool(true);
				respuesta.setRespuestaString(textoComentarios);
				cuestionarioRequest.addRespuesta(respuesta);

	        	String resultado = bhCallService.crearActualizarCuestionarioMedico(cuestionarioRequest);
	        	if (resultado != null && !resultado.isEmpty()) {
	                com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest errorRequest = new com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest();
	                errorRequest.setErrorWScrearpagrSolicitudInstance(tGCREARACTPERSONACOREptSolicitud);
	                errorRequest.setErrorWScrearpatrDescripcion("Error en cuestionario medico de la persona " + 
		                tGCREARACTPERSONACOREptPersona.getTipoIdentificacion().getTipoIdentificacionNombreCorto() + 
		                tGCREARACTPERSONACOREptPersona.getPersonaNumIdentificacion());
	                errorWSSrv.crearImplementation(errorRequest);
	        	}
        	}
        }
        
        return response;
    }

    /*
     * Entry point for global service TGACTTIPOFACTURA
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tGACTTIPOFACTURA(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTTIPOFACTURARequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = globalServicesSrv.tGACTTIPOFACTURATxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGACTTIPOFACTURA
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tGACTTIPOFACTURATxn(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTTIPOFACTURARequest request) throws ModelException {
        request.setTGACTTIPOFACTURAptSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getTGACTTIPOFACTURAptSolicitud()));
        return globalServicesSrv.tGACTTIPOFACTURAImplementation(request);
    }

    /*
     * Implements global service TGACTTIPOFACTURA
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tGACTTIPOFACTURAImplementation(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTTIPOFACTURARequest request) throws ModelException {
        ServiceResponse response = new ServiceResponse();
        Solicitud tGACTTIPOFACTURAptSolicitud = request.getTGACTTIPOFACTURAptSolicitudInstance();

        request.checkArguments();

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringRequest step1Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringRequest();
        step1Request.setSolicituddummyDevolverIntStringpthisSolicitudInstance(tGACTTIPOFACTURAptSolicitud);
        solicitudSrv.dummyDevolverIntStringImplementation(step1Request);
        
        
        return response;
    }

    /*
     * Entry point for global service TGRPTACTRNEW
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ContractResponseResponse tGRPTACTRNEW(
    		ContractResponseRequest request) {
    	ContractResponseResponse response = new ContractResponseResponse();
        try {
            response = globalServicesSrv.tGRPTACTRNEWTxn(request);
        } catch (Exception e) {
        	response.addError("A0010", e.getMessage());
            
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGRPTACTRNEW
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ContractResponseResponse tGRPTACTRNEWTxn(
    		ContractResponseRequest request) throws ModelException, SystemException {
        return globalServicesSrv.tGRPTACTRNEWImplementation(request);
    }

    /*
     * Implements global service TGRPTACTRNEW
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ContractResponseResponse tGRPTACTRNEWImplementation(
    		ContractResponseRequest request) throws ModelException, SystemException {
    	ContractResponseResponse response = new ContractResponseResponse();
    	try {
	    	// Convertimos los datos recibidos a un String Json
	    	String peticionTxt = "";
			ObjectMapper mapper= new ObjectMapper();
			if (request != null) {
				try {
					peticionTxt = mapper.writeValueAsString(request);
				} catch (JsonProcessingException e) {
		    		org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e.getMessage());
				}
			}
	
	
			response = procesarNotificacion(request, peticionTxt, false);
    	} catch (Exception e) {
			org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error("Respuesta contrato New\n" + e.getMessage());
    		response.addError("RCN010", "Error procesando peticion");
    	}
    	
    	return response;
        
    }
	
	private ContractResponseResponse procesarNotificacion(ContractResponseRequest request, String peticionTxt,
				boolean inclusion) {

		ContractResponseResponse response = new ContractResponseResponse();
		Double tiempoEjecucion = 0.0;
		Timestamp fechaHora = STDFunctions.systemDateTimeFun();
		long startTime = System.currentTimeMillis();
		long endTime;
		String numSolicitud = "";

		String respuestaTxt = "";

		// Validaciones
		boolean error = false;
		if (request == null) {
			response.addError("E001", "Request vacia");
			error = true;
			endTime = System.currentTimeMillis();
			tiempoEjecucion = (double) ((endTime - startTime) / 1000.0f);
		} else {

			if (request.getNumeroSolicitud() == null || request.getNumeroSolicitud().isEmpty()) {
				response.addError("E002", "Numero de solicitud no recibido");
				error = true;
				endTime = System.currentTimeMillis();
				tiempoEjecucion = (double) ((endTime - startTime) / 1000.0f);
			} else {
				numSolicitud = request.getNumeroSolicitud();
			}

			if (request.getResultadoValidacion() == null) {
				response.addError("E003", "Resultado de la validacion no recibido");
				error = true;
				endTime = System.currentTimeMillis();
				tiempoEjecucion = (double) ((endTime - startTime) / 1000.0f);
			}

			if (request.getContract() .getCloseRequest() == null) {
				response.addError("E004", "Campo closeRequest no recibido");
				error = true;
				endTime = System.currentTimeMillis();
				tiempoEjecucion = (double) ((endTime - startTime) / 1000.0f);
			}
		}
		// Si las validaciones han ido bien, proesamos la peticion

		// La buscamos por numero
        List<FilterVariableRequest> filterVariables = new ArrayList<>();
        filterVariables.add(new FilterVariableRequest(SolicitudConstants.VAR_NAME_IFSOLICITUDNUMSOLICITUD_VNUMSOLICITUD, numSolicitud));
        FilterRequest filterRequest = new FilterRequest(SolicitudConstants.FILTER_NAME_IFSOLICITUDNUMSOLICITUD, filterVariables);
        QueryRequest queryRequest = new QueryRequest(filterRequest);
        List<Solicitud> results = solicitudSrv.get(queryRequest);
        
        Solicitud solicitud = results != null && !results.isEmpty() ? results.get(0) : null;

		if (solicitud == null || solicitud.isNull()) {
			response.addError("E010", "Solicitud numero: " + numSolicitud + " no encontrada");
			return response;
		}

		
		String textoErroresPeticion = "";
		if (!error) {
			// Ajuste aprobado el 28/07/2026 por correo con asunto: Re: AfiliaciÃ³n Electronica Renovacion: Servicios de NotificaciÃ³n y Respuesta Contrato
			//   Si la validacion de datos de la solicitud ha sido correcta, no hacemos nada
			if (request.getResultadoValidacion()) {
				// Solo tomamos tiempos finales
				endTime = System.currentTimeMillis();
				tiempoEjecucion = (double) ((endTime - startTime) / 1000.0f);
				
			} else {
				textoErroresPeticion = request.formarTextoErrores();
				procesarPeticion(request, response, solicitud);
				// Tomamos tiempos finales
				endTime = System.currentTimeMillis();
				tiempoEjecucion = (double) ((endTime - startTime) / 1000.0f);
			}
		}

		// GdC57 tris: En cualqueir caso, guardamos los errores enviados por BH
		if (request != null) {
			for (String err : request.getListaErrores()) {
				CrearRequest reqCrear = new CrearRequest();
				reqCrear.setErrorWScrearpagrSolicitudInstance(solicitud);
				reqCrear.setErrorWScrearpatrDescripcion(err);
				ServiceResponse resp = errorWSSrv.crearImplementation(reqCrear);
				if (!"000".equalsIgnoreCase(resp.getResultCode())) {
					throw new SystemException(resp.getResultCode() + "\n" + resp.getResultDescription());
				}
			}
		}
		
		// Guardamos la llamada al WS
		ObjectMapper mapper= new ObjectMapper();
		if (request != null) {
			try {
				respuestaTxt = mapper.writeValueAsString(response);
			} catch (JsonProcessingException e) {
				org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e.getMessage());
			}
			String capacidad = "registerContractResponse";
			if (inclusion) {
				capacidad = "includeBeneficiaryResponse";
			}
			
			guardarLlamadaAE(fechaHora, 
					capacidad, 
					"contratoResponse", 
					numSolicitud, 
					peticionTxt, 
					respuestaTxt,
					"REST Service", 
					tiempoEjecucion, 
					textoErroresPeticion + response.formarTextoErrores());
		}

		return response;
	}
	
	private void procesarPeticion(ContractResponseRequest request , ContractResponseResponse response, Solicitud solicitud ) {
		try {
			// Comprobamos que se encuentra en estado "Recibido"
			if (solicitud.getSolicitudEstado() != 8L) {
				response.addError("E013",
						"La solicitud numero: " + solicitud.getSolicitudNumRadicado().toString() + " no se encuentra en estado Recibido");
				return;
			}

			// Cambiamos estado en funcion de la informacion recibida

			// GdC-37 bis 3: Si ha fallado la validacion, closeRequest = "N" y requiere
			// examen medico
			if (!request.getResultadoValidacion() && "N".equals(request.getContract().getCloseRequest())
					&& request.getRequiereExamenMedico()) {
				// PENDIENTE MEDICO
				llamarCambioEstado(solicitud, 5L, null, null);
			} else {
				// GdC-37 bis 2
				if (!request.getResultadoValidacion()) {
					// Cambiamos la solicitud a estado Error y guardamos los errores
					llamarCambioEstado(solicitud,  0L, null, null);
				} else {

					// GdC-37bis2. Determinamos el estado en el que debe quedarse la solicitud
					if ("S".equalsIgnoreCase(request.getContract().getCloseRequest())) {
						if (request.getTienePreexistencias()) {
							// TRAMITADO CON PREEXISTENCIAS
							llamarCambioEstado(solicitud, 3L, null, null);
						} else {
							// TRAMITADO
							llamarCambioEstado(solicitud, 2L, null, null);
						}
					} else {
						if (request.getRequiereExamenMedico()) {
							// PENDIENTE MEDICO
							llamarCambioEstado(solicitud, 5L, null, null);
						} else {
							// RADICADO
							llamarCambioEstado(solicitud, 1L, null, null);
						}
					}
				}
			}
		} catch (Exception e) {
			org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e.getMessage());
			response.addError("E020", e.getMessage());
		}
	}

	private void llamarCambioEstado(Solicitud solicitud, Long estado, String observaciones, String motivoDevolucion) throws SystemException {
		TCAMBIARESTADORequest reqCamEstado = new TCAMBIARESTADORequest();
		reqCamEstado.setSolicitudTCAMBIARESTADOpthisSolicitudInstance(solicitud);
		reqCamEstado.setSolicitudTCAMBIARESTADOptpeEstado(estado);
		reqCamEstado.setSolicitudTCAMBIARESTADOptObservaciones(observaciones);
		reqCamEstado.setSolicitudTCAMBIARESTADOptMotDevolucion(motivoDevolucion);
		// Cambiamos el estado de la solicitud a ERROR
		ServiceResponse resp = solicitudSrv.tCAMBIARESTADOImplementation(reqCamEstado );
		if (!"000".equalsIgnoreCase(resp.getResultCode())) {
			String errTxt = resp.getResultCode() + " - " + resp.getResultDescription();
			throw new SystemException(errTxt);
		}
	}
	
	private void guardarLlamadaAE(Timestamp p_atrFechaHoraArg, String p_atrCapacidadArg,
			String p_atrServicioArg, String p_atrSolicitudArg, String p_atrPeticionArg, String p_atrRespuestaArg,
			String p_atrTipoServicioArg, Double p_atrTiempoEjecucionArg, String p_atrTextoErrorArg) {
		
		// Directamente invocamos al servicio que se encarga de registrar la llamada
		try {
			CreateinstanceRequest reqCrear = new CreateinstanceRequest();
			reqCrear.setLogLlamadasAEcreateinstancepatrCapacidad(p_atrCapacidadArg);
			reqCrear.setLogLlamadasAEcreateinstancepatrDatosEnviados(p_atrPeticionArg);
			reqCrear.setLogLlamadasAEcreateinstancepatrDatosRecibidos(p_atrRespuestaArg);
			reqCrear.setLogLlamadasAEcreateinstancepatrFechaHora(p_atrFechaHoraArg);
			reqCrear.setLogLlamadasAEcreateinstancepatrServicio(p_atrServicioArg);
			reqCrear.setLogLlamadasAEcreateinstancepatrSolicitud(p_atrSolicitudArg);
			reqCrear.setLogLlamadasAEcreateinstancepatrTextoError(p_atrTextoErrorArg);
			reqCrear.setLogLlamadasAEcreateinstancepatrTiempoEjecucion(p_atrTiempoEjecucionArg);
			reqCrear.setLogLlamadasAEcreateinstancepatrTipoServicio(p_atrTipoServicioArg);
			logLlamadasAESrv.createinstanceImplementation(reqCrear );
		} catch (Exception e) {
			// Si se produce algun error tratando de registrar la llamada lo tiramos al log
			org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error("E100: Guardando llamada AE" + e.getMessage());
		}
	}

    /*
     * Entry point for global service TGRPTACTRINC
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ContractResponseResponse tGRPTACTRINC(
    		ContractResponseRequest request) {
    	ContractResponseResponse response = new ContractResponseResponse();
        try {
            response = globalServicesSrv.tGRPTACTRINCTxn(request);
        } catch (Exception e) {
        	response.addError("A0020", e.getMessage());
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGRPTACTRINC
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ContractResponseResponse tGRPTACTRINCTxn(
    		ContractResponseRequest request) throws ModelException, SystemException {
        return globalServicesSrv.tGRPTACTRINCImplementation(request);
    }

    /*
     * Implements global service TGRPTACTRINC
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ContractResponseResponse tGRPTACTRINCImplementation(
    		ContractResponseRequest request) throws ModelException, SystemException {
    	ContractResponseResponse response = new ContractResponseResponse();
        
    	try {
	    	// Convertimos los datos recibidos a un String Json
	    	String peticionTxt = "";
			ObjectMapper mapper= new ObjectMapper();
			if (request != null) {
				try {
					peticionTxt = mapper.writeValueAsString(request);
				} catch (JsonProcessingException e) {
		    		org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e.getMessage());
				}
			}
	
	
			response = procesarNotificacion(request, peticionTxt, true);
    	} catch (Exception e) {
			org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error("Respuesta contrato Inclusion\n" + e.getMessage());
    		response.addError("RCN020", "Error procesando peticion");
    	}
    	
    	return response;
    }
}
