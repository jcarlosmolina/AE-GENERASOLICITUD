package com.keralty.aegenerasolicitud.viewmodel.pais.ds;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.keralty.aegenerasolicitud.persistence.Pais;
public class ICPais {

    @JsonProperty("descripcion")
    private String iCPaisDescripcion;

    public ICPais() {
    
    }

    public ICPais(Pais instance) {
        if (instance != null) {
            this.iCPaisDescripcion = instance.getPaisDescripcion();
        }
    }

    @JsonIgnore
    public String getICPaisDescripcion() {
        return this.iCPaisDescripcion;
    }
    
    public void setICPaisDescripcion(String iCPaisDescripcion) {
        this.iCPaisDescripcion = iCPaisDescripcion;
    }
}
