package com.keralty.aegenerasolicitud.viewmodel.solicitud.piu;

import com.keralty.aegenerasolicitud.viewmodel.PopulationRequestViewModel;
import com.keralty.aegenerasolicitud.viewmodel.QueryRequest;
import com.keralty.aegenerasolicitud.viewmodel.solicitud.filter.FSolicitudConsultaRadicado;

public class PIUConsultarRadicadoRequest extends PopulationRequestViewModel{

    private FSolicitudConsultaRadicado fSolicitudConsultaRadicado;
    
    public void setFSolicitudConsultaRadicado(FSolicitudConsultaRadicado fSolicitudConsultaRadicado) {
        this.fSolicitudConsultaRadicado = fSolicitudConsultaRadicado;
    }
    
    public FSolicitudConsultaRadicado getFSolicitudConsultaRadicado() {
        return fSolicitudConsultaRadicado;
    }

    @Override
    public QueryRequest buildQueryRequest() {
        QueryRequest queryRequest = super.buildQueryRequest();

        if (fSolicitudConsultaRadicado != null)
            queryRequest.addFilterRequest(fSolicitudConsultaRadicado.buildFilterRequest());
        return queryRequest;
    }
}
