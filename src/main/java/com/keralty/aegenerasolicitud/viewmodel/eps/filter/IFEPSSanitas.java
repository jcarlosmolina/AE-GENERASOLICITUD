package com.keralty.aegenerasolicitud.viewmodel.eps.filter;


import com.keralty.aegenerasolicitud.global.EpsConstants;
import com.keralty.aegenerasolicitud.viewmodel.FilterRequest;

public class IFEPSSanitas {

    public IFEPSSanitas() {
        // Default constructor
    }

    public FilterRequest buildFilterRequest() {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setName(EpsConstants.FILTER_NAME_IFEPSSANITAS);
        return filterRequest;
    }
}
