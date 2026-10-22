package com.keralty.aegenerasolicitud.viewmodel.periodicidad.filter;


import com.keralty.aegenerasolicitud.global.PeriodicidadConstants;
import com.keralty.aegenerasolicitud.viewmodel.FilterRequest;

public class IFPeriodicidadMensual {

    public IFPeriodicidadMensual() {
        // Default constructor
    }

    public FilterRequest buildFilterRequest() {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setName(PeriodicidadConstants.FILTER_NAME_IFPERIODICIDADMENSUAL);
        return filterRequest;
    }
}
