package com.keralty.aegenerasolicitud.viewmodel.globalservices.siu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.keralty.aegenerasolicitud.exception.NotNullArgumentException;
import com.keralty.aegenerasolicitud.global.GlobalServiceConstants;
import com.keralty.aegenerasolicitud.persistence.oid.SolicitudOid;
import com.keralty.aegenerasolicitud.persistence.Solicitud;

public class TGINCLUSIONCONTRATOCORERequest {

    @JsonProperty("ptsolicitud_oid")
    private SolicitudOid tGINCLUSIONCONTRATOCOREptSolicitud;
    
    @JsonIgnore
    private Solicitud tGINCLUSIONCONTRATOCOREptSolicitudInstance;

    public void checkArguments() throws NotNullArgumentException {
        checkTGINCLUSIONCONTRATOCOREptSolicitud();
    }

    @JsonIgnore
    public SolicitudOid getTGINCLUSIONCONTRATOCOREptSolicitud() {
        return this.tGINCLUSIONCONTRATOCOREptSolicitud;
    }
    
    public void setTGINCLUSIONCONTRATOCOREptSolicitud(SolicitudOid tGINCLUSIONCONTRATOCOREptSolicitud) {
        this.tGINCLUSIONCONTRATOCOREptSolicitud = tGINCLUSIONCONTRATOCOREptSolicitud;
    }

    @JsonIgnore
    public Solicitud getTGINCLUSIONCONTRATOCOREptSolicitudInstance() {
        return this.tGINCLUSIONCONTRATOCOREptSolicitudInstance;
    }
    
    public void setTGINCLUSIONCONTRATOCOREptSolicitudInstance(Solicitud tGINCLUSIONCONTRATOCOREptSolicitud) {
        this.tGINCLUSIONCONTRATOCOREptSolicitudInstance = tGINCLUSIONCONTRATOCOREptSolicitud;
        this.tGINCLUSIONCONTRATOCOREptSolicitud = tGINCLUSIONCONTRATOCOREptSolicitud != null ? tGINCLUSIONCONTRATOCOREptSolicitud.getOid() : null;
    }

    private void checkTGINCLUSIONCONTRATOCOREptSolicitud() throws NotNullArgumentException {
        if(this.getTGINCLUSIONCONTRATOCOREptSolicitudInstance() == null || this.getTGINCLUSIONCONTRATOCOREptSolicitudInstance().isNull()) {
            throw new NotNullArgumentException(GlobalServiceConstants.G_SERV_ID_TGINCLUSIONCONTRATOCORE, GlobalServiceConstants.GS_ARG_ID_TGINCLUSIONCONTRATOCORE_TGINCLUSIONCONTRATOCOREPTSOLICITUD, GlobalServiceConstants.G_SERV_ALIAS_TGINCLUSIONCONTRATOCORE, GlobalServiceConstants.GS_ARG_ALIAS_TGINCLUSIONCONTRATOCORE_TGINCLUSIONCONTRATOCOREPTSOLICITUD);
        }
    }
}
