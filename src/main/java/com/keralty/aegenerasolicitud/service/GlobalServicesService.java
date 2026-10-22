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
            GlobalServicesService globalServicesSrv) {
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

        try {
            /* Action 1: IF (ptpSolicitudAE.Tramite = 2 AND ptpSolicitudAE.tipoContrato = 3) */
            boolean ifCondition1 = (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTramite()).equals(Long.valueOf(2)) && (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudtipoContrato()).equals(Long.valueOf(3));
            if (ifCondition1) {
                    com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarDatosPagoOKRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarDatosPagoOKRequest();
                    step10Request.setSolicitudmarcarDatosPagoOKpthisSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                    solicitudSrv.marcarDatosPagoOKTxn(step10Request);
                    globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
            }
        } catch (Exception e) { }

        try {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest step2Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADARequest();
            step2Request.setSolicitudTMARCARCOMPLETADApthisSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TMARCARCOMPLETADAResponse step2Response = solicitudSrv.tMARCARCOMPLETADATxn(step2Request);
            response.setOGENERARSOLICITUD2ptpsNumSolicitud(step2Response.getSolicitudTMARCARCOMPLETADAptpsNumSolicitud());
            globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) { }

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
        } catch (Exception e) { }

        try {
            for(ErrorWS step4Entity : globalServicesRepository.assocOperator102(oGENERARSOLICITUD2ptpSolicitudAE)) {
                try {
                    com.keralty.aegenerasolicitud.viewmodel.errorws.siu.EliminarRequest step4Request = new com.keralty.aegenerasolicitud.viewmodel.errorws.siu.EliminarRequest();
                    step4Request.setErrorWSeliminarpthisErrorWSInstance(step4Entity);
                    errorWSSrv.eliminarTxn(step4Request);
                } catch (Exception e) { }
                globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
            }
        } catch (Exception e) { }

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
                            
                        } catch (Exception e) { }
                        globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                }
                /* Action 53: IF (EXIST (ptpSolicitudAE.ErroresWS) = false) */
                boolean ifCondition53 = globalServicesRepository.assocOperator110(oGENERARSOLICITUD2ptpSolicitudAE).equals(false);
                if (ifCondition53) {
                    /* Action 530: IF (EXIST (ptpSolicitudAE.Contratante) = true AND ptpSolicitudAE.Contratante.DireccionesModificadas = true) */
                    boolean ifCondition530 = globalServicesRepository.assocOperator111(oGENERARSOLICITUD2ptpSolicitudAE).equals(true) && oGENERARSOLICITUD2ptpSolicitudAE.getContratante().getContratanteDireccionesModificadas().equals(true);
                    if (ifCondition530) {
                            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTDIRECCIONESRequest step5300Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTDIRECCIONESRequest();
                            step5300Request.setTGACTDIRECCIONESptSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                            step5300Request.setTGACTDIRECCIONESptTipo(Long.valueOf(1));
                            globalServicesSrv.tGACTDIRECCIONESTxn(step5300Request);
                            globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                    }
                    /* Action 531: IF (EXIST (ptpSolicitudAE.Titular) = true AND ptpSolicitudAE.Titular.DireccionesModificadas = true) */
                    boolean ifCondition531 = globalServicesRepository.assocOperator112(oGENERARSOLICITUD2ptpSolicitudAE).equals(true) && oGENERARSOLICITUD2ptpSolicitudAE.getTitular().getTitularDireccionesModificadas().equals(true);
                    if (ifCondition531) {
                            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTDIRECCIONESRequest step5310Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTDIRECCIONESRequest();
                            step5310Request.setTGACTDIRECCIONESptSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                            step5310Request.setTGACTDIRECCIONESptTipo(Long.valueOf(2));
                            globalServicesSrv.tGACTDIRECCIONESTxn(step5310Request);
                            globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                    }
                }
                /* Action 54: IF (EXIST (ptpSolicitudAE.ErroresWS) = false AND ptpSolicitudAE.Tramite = 2 AND ptpSolicitudAE.tipoContrato = 1 AND ptpSolicitudAE.TipoFacturaCORE <> NULL AND EXIST (ptpSolicitudAE.TipoFactura) = true AND ptpSolicitudAE.TipoFacturaCORE <> ptpSolicitudAE.TipoFactura.Codigo) */
                boolean ifCondition54 = globalServicesRepository.assocOperator113(oGENERARSOLICITUD2ptpSolicitudAE).equals(false) && (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTramite()).equals(Long.valueOf(2)) && (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudtipoContrato()).equals(Long.valueOf(1)) && oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTipoFacturaCORE() != null && globalServicesRepository.assocOperator114(oGENERARSOLICITUD2ptpSolicitudAE).equals(true) && !STDFunctions.areEqual(oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTipoFacturaCORE(),oGENERARSOLICITUD2ptpSolicitudAE.getTipoFactura().getTipoFacturaCodigo());
                if (ifCondition54) {
                        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTTIPOFACTURARequest step540Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTTIPOFACTURARequest();
                        step540Request.setTGACTTIPOFACTURAptSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                        globalServicesSrv.tGACTTIPOFACTURATxn(step540Request);
                        globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                }
                /* Action 55: IF (EXIST (ptpSolicitudAE.ErroresWS) = false) */
                boolean ifCondition55 = globalServicesRepository.assocOperator115(oGENERARSOLICITUD2ptpSolicitudAE).equals(false);
                if (ifCondition55) {
                    /* Action 550: IF (ptpSolicitudAE.Tramite = 1) */
                    boolean ifCondition550 = (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudTramite()).equals(Long.valueOf(1));
                    if (ifCondition550) {
                            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCORERequest step5500Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCORERequest();
                            step5500Request.setTGCREARCONTRATOCOREptSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCOREResponse step5500Response = globalServicesSrv.tGCREARCONTRATOCORETxn(step5500Request);
                            response.setOGENERARSOLICITUD2psError(step5500Response.getTGCREARCONTRATOCOREpsError());
                            globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                    } else {
                        /* Action 5501: IF (ptpSolicitudAE.EstadoDoc = 2) */
                        boolean ifCondition5501 = (oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudEstadoDoc()).equals(Long.valueOf(2));
                        if (ifCondition5501) {
                            /* Action 55010: IF (ptpSolicitudAE.NumFamilia = NULL) */
                            boolean ifCondition55010 = oGENERARSOLICITUD2ptpSolicitudAE.getSolicitudNumFamilia() == null;
                            if (ifCondition55010) {
                                    com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCORERequest step550100Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCORERequest();
                                    step550100Request.setTGCREARCONTRATOCOREptSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                                    com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCOREResponse step550100Response = globalServicesSrv.tGCREARCONTRATOCORETxn(step550100Request);
                                    response.setOGENERARSOLICITUD2psError(step550100Response.getTGCREARCONTRATOCOREpsError());
                                    globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                            } else {
                                    com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCORERequest step550101Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCORERequest();
                                    step550101Request.setTGINCLUSIONCONTRATOCOREptSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                                    com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCOREResponse step550101Response = globalServicesSrv.tGINCLUSIONCONTRATOCORETxn(step550101Request);
                                    response.setOGENERARSOLICITUD2psError(step550101Response.getTGINCLUSIONCONTRATOCOREpsError());
                                    globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                            }
                        }
                    }
                    /* Action 551: IF (psError = false) */
                    boolean ifCondition551 = response.getOGENERARSOLICITUD2psError().equals(false);
                    if (ifCondition551) {
                            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TPOSTGENERAREXITORequest step5510Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TPOSTGENERAREXITORequest();
                            step5510Request.setSolicitudTPOSTGENERAREXITOpthisSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                            solicitudSrv.tPOSTGENERAREXITOTxn(step5510Request);
                            globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                    }
                } else {
                        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarNoCompletadaRequest step552Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarNoCompletadaRequest();
                        step552Request.setSolicitudmarcarNoCompletadapthisSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                        solicitudSrv.marcarNoCompletadaTxn(step552Request);
                        globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
                }
            } else {
                    com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarNoCompletadaRequest step56Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.MarcarNoCompletadaRequest();
                    step56Request.setSolicitudmarcarNoCompletadapthisSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
                    solicitudSrv.marcarNoCompletadaTxn(step56Request);
                    globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
            }
        } catch (Exception e) { }

        try {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSRequest step6Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSRequest();
            step6Request.setSolicituddummyDevolverErroresWSpthisSolicitudInstance(oGENERARSOLICITUD2ptpSolicitudAE);
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverErroresWSResponse step6Response = solicitudSrv.dummyDevolverErroresWSTxn(step6Request);
            response.setOGENERARSOLICITUD2ptpsErrorWS(step6Response.getSolicituddummyDevolverErroresWSpsErroresWS());
            globalServicesRepository.refreshEntity(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) { }
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

        request.checkArguments();

        /* Action 1: IF (1 > 2) */
        boolean ifCondition1 = Long.valueOf(1) > Long.valueOf(2);
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.rol.siu.CrearRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.rol.siu.CrearRequest();
            step10Request.setRolcrearpatrNombre(response.getTGCREARPERSONACOREpsSalida());
            rolSrv.crearImplementation(step10Request);
        }
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

        request.checkArguments();

        /* Action 1: IF (1 > 2) */
        boolean ifCondition1 = Long.valueOf(1) > Long.valueOf(2);
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.rol.siu.CrearRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.rol.siu.CrearRequest();
            step10Request.setRolcrearpatrNombre(response.getTGACTPERSONAENCOREpsSalida());
            rolSrv.crearImplementation(step10Request);
        }
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

        request.checkArguments();

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringRequest step1Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringRequest();
        step1Request.setSolicituddummyDevolverIntStringpthisSolicitudInstance(tGCREARCONTRATOCOREptSolicitud);
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringResponse step1Response = solicitudSrv.dummyDevolverIntStringImplementation(step1Request);
        response.setTGCREARCONTRATOCOREpsError(step1Response.getSolicituddummyDevolverIntStringpsBool());
        
        return response;
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

        request.checkArguments();

        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringRequest step1Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringRequest();
        step1Request.setSolicituddummyDevolverIntStringpthisSolicitudInstance(tGINCLUSIONCONTRATOCOREptSolicitud);
        com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringResponse step1Response = solicitudSrv.dummyDevolverIntStringImplementation(step1Request);
        response.setTGINCLUSIONCONTRATOCOREpsError(step1Response.getSolicituddummyDevolverIntStringpsBool());
        
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
            for(FamiliaC step1012Entity : globalServicesRepository.assocOperator123()) {
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
            for(PersonaC step1013Entity : globalServicesRepository.assocOperator124()) {
                com.keralty.aegenerasolicitud.viewmodel.preexistenciac.siu.CrearRequest step13Request = new com.keralty.aegenerasolicitud.viewmodel.preexistenciac.siu.CrearRequest();
                step13Request.setPreexistenciaCcrearpagrPersonaCInstance(step1013Entity);
                step13Request.setPreexistenciaCcrearpatrDescripcion("");
                step13Request.setPreexistenciaCcrearpatrFecInicio(STDFunctions.systemDateFun());
                step13Request.setPreexistenciaCcrearpatrFecFin(STDFunctions.systemDateFun());
                step13Request.setPreexistenciaCcrearpatrObservacion("");
                preexistenciaCSrv.crearImplementation(step13Request);
            }
            for(BeneficiarioC step1014Entity : globalServicesRepository.assocOperator125()) {
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
            for(FamiliaC step1012Entity : globalServicesRepository.assocOperator126()) {
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
            for(PersonaC step1013Entity : globalServicesRepository.assocOperator127()) {
                com.keralty.aegenerasolicitud.viewmodel.preexistenciac.siu.CrearRequest step13Request = new com.keralty.aegenerasolicitud.viewmodel.preexistenciac.siu.CrearRequest();
                step13Request.setPreexistenciaCcrearpagrPersonaCInstance(step1013Entity);
                step13Request.setPreexistenciaCcrearpatrDescripcion("");
                step13Request.setPreexistenciaCcrearpatrFecInicio(STDFunctions.systemDateFun());
                step13Request.setPreexistenciaCcrearpatrFecFin(STDFunctions.systemDateFun());
                step13Request.setPreexistenciaCcrearpatrObservacion("");
                preexistenciaCSrv.crearImplementation(step13Request);
            }
            for(BeneficiarioC step1014Entity : globalServicesRepository.assocOperator128()) {
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
        return response;
    }

    /*
     * Entry point for global service TGACTDIRECCIONES
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public ServiceResponse tGACTDIRECCIONES(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTDIRECCIONESRequest request) {
        ServiceResponse response = new ServiceResponse();
        try {
            response = globalServicesSrv.tGACTDIRECCIONESTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGACTDIRECCIONES
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ServiceResponse tGACTDIRECCIONESTxn(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTDIRECCIONESRequest request) throws ModelException, SystemException {
        request.setTGACTDIRECCIONESptSolicitudInstance(solicitudSrv.getByOIDWithHV(request.getTGACTDIRECCIONESptSolicitud()));
        return globalServicesSrv.tGACTDIRECCIONESImplementation(request);
    }

    /*
     * Implements global service TGACTDIRECCIONES
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public ServiceResponse tGACTDIRECCIONESImplementation(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTDIRECCIONESRequest request) throws ModelException, SystemException {
        ServiceResponse response = new ServiceResponse();
        Solicitud tGACTDIRECCIONESptSolicitud = request.getTGACTDIRECCIONESptSolicitudInstance();
        Long tGACTDIRECCIONESptTipo = request.getTGACTDIRECCIONESptTipo();

        request.checkArguments();

        /* Action 1: IF (1 > 2) */
        boolean ifCondition1 = Long.valueOf(1) > Long.valueOf(2);
        if (ifCondition1) {
            com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringRequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.DummyDevolverIntStringRequest();
            step10Request.setSolicituddummyDevolverIntStringpthisSolicitudInstance(tGACTDIRECCIONESptSolicitud);
            solicitudSrv.dummyDevolverIntStringImplementation(step10Request);
            
            
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
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWResponse tGRPTACTRNEW(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWRequest request) {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWResponse();
        try {
            response = globalServicesSrv.tGRPTACTRNEWTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGRPTACTRNEW
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWResponse tGRPTACTRNEWTxn(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWRequest request) throws ModelException, SystemException {
        return globalServicesSrv.tGRPTACTRNEWImplementation(request);
    }

    /*
     * Implements global service TGRPTACTRNEW
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWResponse tGRPTACTRNEWImplementation(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWRequest request) throws ModelException, SystemException {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWResponse();
        String tGRPTACTRNEWargumentos = request.getTGRPTACTRNEWargumentos();

        request.checkArguments();

        /* Action 1: IF (1 > 2) */
        boolean ifCondition1 = Long.valueOf(1) > Long.valueOf(2);
        if (ifCondition1) {
            for(Solicitud step1010Entity : globalServicesRepository.assocOperator154()) {
                com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest();
                step10Request.setSolicitudTCAMBIARESTADOpthisSolicitudInstance(step1010Entity);
                step10Request.setSolicitudTCAMBIARESTADOptpeEstado(Long.valueOf(0));
                step10Request.setSolicitudTCAMBIARESTADOptObservaciones("");
                step10Request.setSolicitudTCAMBIARESTADOptMotDevolucion("");
                solicitudSrv.tCAMBIARESTADOImplementation(step10Request);
            }
            for(Solicitud step1011Entity : globalServicesRepository.assocOperator155()) {
                com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest step11Request = new com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest();
                step11Request.setErrorWScrearpagrSolicitudInstance(step1011Entity);
                step11Request.setErrorWScrearpatrDescripcion("");
                errorWSSrv.crearImplementation(step11Request);
            }
            com.keralty.aegenerasolicitud.viewmodel.logllamadasae.siu.CreateinstanceRequest step12Request = new com.keralty.aegenerasolicitud.viewmodel.logllamadasae.siu.CreateinstanceRequest();
            step12Request.setLogLlamadasAEcreateinstancepatrFechaHora(STDFunctions.systemDateTimeFun());
            step12Request.setLogLlamadasAEcreateinstancepatrCapacidad("");
            step12Request.setLogLlamadasAEcreateinstancepatrServicio("");
            step12Request.setLogLlamadasAEcreateinstancepatrSolicitud("");
            step12Request.setLogLlamadasAEcreateinstancepatrDatosRecibidos("");
            step12Request.setLogLlamadasAEcreateinstancepatrDatosEnviados("");
            step12Request.setLogLlamadasAEcreateinstancepatrTipoServicio("");
            step12Request.setLogLlamadasAEcreateinstancepatrTiempoEjecucion(Utilities.convertToDouble(Long.valueOf(0)));
            step12Request.setLogLlamadasAEcreateinstancepatrTextoError("");
            logLlamadasAESrv.createinstanceImplementation(step12Request);
            com.keralty.aegenerasolicitud.viewmodel.logllamadasbh.siu.CreateinstanceRequest step13Request = new com.keralty.aegenerasolicitud.viewmodel.logllamadasbh.siu.CreateinstanceRequest();
            step13Request.setLogLlamadasBHcreateinstancepatrFechaHora(STDFunctions.systemDateTimeFun());
            step13Request.setLogLlamadasBHcreateinstancepatrUsuario("");
            step13Request.setLogLlamadasBHcreateinstancepatrCapacidad("");
            step13Request.setLogLlamadasBHcreateinstancepatrServicio("");
            step13Request.setLogLlamadasBHcreateinstancepatrSolicitud("");
            step13Request.setLogLlamadasBHcreateinstancepatrPeticion("");
            step13Request.setLogLlamadasBHcreateinstancepatrRespuesta("");
            step13Request.setLogLlamadasBHcreateinstancepatrTipoServicio("");
            step13Request.setLogLlamadasBHcreateinstancepatrTiempoEjecucion(Utilities.convertToDouble(Long.valueOf(0)));
            step13Request.setLogLlamadasBHcreateinstancepatrTextoError("");
            logLlamadasBHSrv.createinstanceImplementation(step13Request);
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWRequest step14Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWRequest();
            step14Request.setTGRPTACTRNEWargumentos(tGRPTACTRNEWargumentos);
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWResponse step14Response = globalServicesSrv.tGRPTACTRNEWImplementation(step14Request);
            response.setTGRPTACTRNEWrespuesta(step14Response.getTGRPTACTRNEWrespuesta());
        }
        return response;
    }

    /*
     * Entry point for global service TGRPTACTRINC
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCResponse tGRPTACTRINC(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCRequest request) {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCResponse();
        try {
            response = globalServicesSrv.tGRPTACTRINCTxn(request);
        } catch (Exception e) {
            response.setException(e);
        }
        return response;
    }

    /*
     * Transactional entry point for global service TGRPTACTRINC
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCResponse tGRPTACTRINCTxn(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCRequest request) throws ModelException, SystemException {
        return globalServicesSrv.tGRPTACTRINCImplementation(request);
    }

    /*
     * Implements global service TGRPTACTRINC
     * @param request Service request viewmodel
     * @return Service response viewmodel with the execution results
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCResponse tGRPTACTRINCImplementation(
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCRequest request) throws ModelException, SystemException {
        com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCResponse response = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCResponse();
        String tGRPTACTRINCargumentos = request.getTGRPTACTRINCargumentos();

        request.checkArguments();

        /* Action 1: IF (1 > 2) */
        boolean ifCondition1 = Long.valueOf(1) > Long.valueOf(2);
        if (ifCondition1) {
            for(Solicitud step1010Entity : globalServicesRepository.assocOperator156()) {
                com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest step10Request = new com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TCAMBIARESTADORequest();
                step10Request.setSolicitudTCAMBIARESTADOpthisSolicitudInstance(step1010Entity);
                step10Request.setSolicitudTCAMBIARESTADOptpeEstado(Long.valueOf(0));
                step10Request.setSolicitudTCAMBIARESTADOptObservaciones("");
                step10Request.setSolicitudTCAMBIARESTADOptMotDevolucion("");
                solicitudSrv.tCAMBIARESTADOImplementation(step10Request);
            }
            for(Solicitud step1011Entity : globalServicesRepository.assocOperator157()) {
                com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest step11Request = new com.keralty.aegenerasolicitud.viewmodel.errorws.siu.CrearRequest();
                step11Request.setErrorWScrearpagrSolicitudInstance(step1011Entity);
                step11Request.setErrorWScrearpatrDescripcion("");
                errorWSSrv.crearImplementation(step11Request);
            }
            com.keralty.aegenerasolicitud.viewmodel.logllamadasae.siu.CreateinstanceRequest step12Request = new com.keralty.aegenerasolicitud.viewmodel.logllamadasae.siu.CreateinstanceRequest();
            step12Request.setLogLlamadasAEcreateinstancepatrFechaHora(STDFunctions.systemDateTimeFun());
            step12Request.setLogLlamadasAEcreateinstancepatrCapacidad("");
            step12Request.setLogLlamadasAEcreateinstancepatrServicio("");
            step12Request.setLogLlamadasAEcreateinstancepatrSolicitud("");
            step12Request.setLogLlamadasAEcreateinstancepatrDatosRecibidos("");
            step12Request.setLogLlamadasAEcreateinstancepatrDatosEnviados("");
            step12Request.setLogLlamadasAEcreateinstancepatrTipoServicio("");
            step12Request.setLogLlamadasAEcreateinstancepatrTiempoEjecucion(Utilities.convertToDouble(Long.valueOf(0)));
            step12Request.setLogLlamadasAEcreateinstancepatrTextoError("");
            logLlamadasAESrv.createinstanceImplementation(step12Request);
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCRequest step13Request = new com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCRequest();
            step13Request.setTGRPTACTRINCargumentos(tGRPTACTRINCargumentos);
            com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCResponse step13Response = globalServicesSrv.tGRPTACTRINCImplementation(step13Request);
            response.setTGRPTACTRINCrespuesta(step13Response.getTGRPTACTRINCrespuesta());
        }
        return response;
    }
}
