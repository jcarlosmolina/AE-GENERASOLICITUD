package com.keralty.aegenerasolicitud.viewmodel.parentesco.filter;


import com.keralty.aegenerasolicitud.global.ParentescoConstants;
import com.keralty.aegenerasolicitud.viewmodel.FilterRequest;

public class IFParentescoTitular {

    public IFParentescoTitular() {
        // Default constructor
    }

    public FilterRequest buildFilterRequest() {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setName(ParentescoConstants.FILTER_NAME_IFPARENTESCOTITULAR);
        return filterRequest;
    }
}
