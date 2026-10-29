package com.keralty.aegenerasolicitud.service;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.keralty.integraciones.patient.CrearActualizarCuestionarioRequest;
import com.keralty.integraciones.patient.CrearActualizarPersonaRequest;
import com.keralty.integraciones.patient.CrearActualizarPersonaResponse;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/api/bhcall")
@RegisterRestClient(configKey="ae-apigateway")
@ClientHeaderParam(name = "Authorization", value = "Bearer {com.keralty.aegenerasolicitud.utils.TokenUtilities.getToken4aeapigateway}")
public interface BhCallService {

    @POST
    @Path("/crearPersona")
    CrearActualizarPersonaResponse crearPersona(CrearActualizarPersonaRequest request);
    
    @POST
    @Path("/actualizarPersona")
    CrearActualizarPersonaResponse actualizarPersona(CrearActualizarPersonaRequest request);
    
    @POST
    @Path("/crearActualizarCuestionarioMedico")
    String crearActualizarCuestionarioMedico(CrearActualizarCuestionarioRequest request);
}
