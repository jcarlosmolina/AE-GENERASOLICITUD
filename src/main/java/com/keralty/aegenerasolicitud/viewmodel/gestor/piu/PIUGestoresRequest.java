package com.keralty.aegenerasolicitud.viewmodel.gestor.piu;

import com.keralty.aegenerasolicitud.viewmodel.PopulationRequestViewModel;
import com.keralty.aegenerasolicitud.viewmodel.QueryRequest;
import com.keralty.aegenerasolicitud.viewmodel.gestor.filter.FGestor;

public class PIUGestoresRequest extends PopulationRequestViewModel{

    private FGestor fGestor;
    
    public void setFGestor(FGestor fGestor) {
        this.fGestor = fGestor;
    }
    
    public FGestor getFGestor() {
        return fGestor;
    }

    @Override
    public QueryRequest buildQueryRequest() {
        QueryRequest queryRequest = super.buildQueryRequest();

        if (fGestor != null)
            queryRequest.addFilterRequest(fGestor.buildFilterRequest());
        return queryRequest;
    }
}
