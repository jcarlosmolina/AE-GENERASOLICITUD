package com.keralty.aegenerasolicitud.viewmodel.parentesco.filter;


import com.keralty.aegenerasolicitud.global.ParentescoConstants;
import com.keralty.aegenerasolicitud.viewmodel.FilterRequest;

public class IFParentescoConyuge {

    public IFParentescoConyuge() {
        // Default constructor
    }

    public FilterRequest buildFilterRequest() {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setName(ParentescoConstants.FILTER_NAME_IFPARENTESCOCONYUGE);
        return filterRequest;
    }
}
