package com.keralty.aegenerasolicitud.viewmodel.globalservices.siu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.keralty.aegenerasolicitud.viewmodel.ServiceResponse;
import java.util.HashMap;
import java.util.Map;

public class TGCREARCONTRATOCOREResponse extends ServiceResponse {

    @JsonProperty("pserror")
    private Boolean tGCREARCONTRATOCOREpsError;

    public TGCREARCONTRATOCOREResponse() {
        super();
    }

    @JsonIgnore
    public Boolean getTGCREARCONTRATOCOREpsError() {
        return this.tGCREARCONTRATOCOREpsError;
    }

    public void setTGCREARCONTRATOCOREpsError(Boolean tGCREARCONTRATOCOREpsError) {
        this.tGCREARCONTRATOCOREpsError = tGCREARCONTRATOCOREpsError;
    }

    @Override
    public Map<String, Object> buildOutArgs() {
        Map<String, Object> outArgs = new HashMap<>();
        outArgs.put("tGCREARCONTRATOCOREpsError", tGCREARCONTRATOCOREpsError);
        return outArgs;
    }
}
