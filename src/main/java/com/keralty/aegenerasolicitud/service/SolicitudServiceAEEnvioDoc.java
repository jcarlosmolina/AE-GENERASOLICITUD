package com.keralty.aegenerasolicitud.service;


import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.keralty.aegenerasolicitud.viewmodel.ServiceResponse;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/api/SolicitudApi")
@RegisterRestClient(configKey="ae-enviodoc")
@ClientHeaderParam(name = "Authorization", value = "Bearer {com.keralty.aegenerasolicitud.utils.TokenUtilities.getToken4aeapigateway}")
public interface SolicitudServiceAEEnvioDoc {

    @POST
    @Path("/TENVIARDOCAUTOAUX")
    ServiceResponse tENVIARDOCAUTOAUX(@RequestBody com.keralty.aegenerasolicitud.viewmodel.solicitud.siu.TENVIARDOCAUTOAUXRequest request);
}
