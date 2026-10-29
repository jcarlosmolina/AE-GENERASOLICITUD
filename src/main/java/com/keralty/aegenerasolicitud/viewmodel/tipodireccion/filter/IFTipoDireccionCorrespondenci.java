package com.keralty.aegenerasolicitud.viewmodel.tipodireccion.filter;


import com.keralty.aegenerasolicitud.global.TipoDireccionConstants;
import com.keralty.aegenerasolicitud.viewmodel.FilterRequest;

public class IFTipoDireccionCorrespondenci {

    public IFTipoDireccionCorrespondenci() {
        // Default constructor
    }

    public FilterRequest buildFilterRequest() {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setName(TipoDireccionConstants.FILTER_NAME_IFTIPODIRECCIONCORRESPONDENCI);
        return filterRequest;
    }
}
