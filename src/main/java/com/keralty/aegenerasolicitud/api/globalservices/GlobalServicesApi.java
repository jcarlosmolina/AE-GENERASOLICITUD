package com.keralty.aegenerasolicitud.api.globalservices;

import com.keralty.aegenerasolicitud.service.GlobalServicesService;
import com.keralty.aegenerasolicitud.utils.TokenUtilities;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Request;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.OGENERARSOLICITUD2Response;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTDIRECCIONESRequest;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCORERequest;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTPERSONAENCOREResponse;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGACTTIPOFACTURARequest;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACORERequest;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARACTPERSONACOREResponse;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCORERequest;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARCONTRATOCOREResponse;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACORERequest;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGCREARPERSONACOREResponse;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCORERequest;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGINCLUSIONCONTRATOCOREResponse;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGNOTIFICARANULARRequest;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGNOTIFICARCREARRequest;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGNOTIFICARINCLUSIONRequest;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCRequest;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRINCResponse;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWRequest;
import com.keralty.aegenerasolicitud.viewmodel.globalservices.siu.TGRPTACTRNEWResponse;
import com.keralty.aegenerasolicitud.viewmodel.ServiceResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/api/GlobalServicesApi")
@RequestScoped
public class GlobalServicesApi {

    GlobalServicesService service;

    public GlobalServicesApi(GlobalServicesService service, JsonWebToken jwt) {
        this.service = service;
        TokenUtilities.getInstance().setJsonWebToken(jwt);
    }

    @POST
    @Path(value = "/OGENERARSOLICITUD2")
    @RolesAllowed({"Admin","SuperAdmin","Asesor","AreaMedica","Afiliaciones","MesaDeApoyo"})
    public OGENERARSOLICITUD2Response oGENERARSOLICITUD2(OGENERARSOLICITUD2Request request) {
        return service.oGENERARSOLICITUD2(request);
    }

    @POST
    @Path(value = "/OGENERARSOLICITUD2CheckPreconditions")
    @RolesAllowed({"Admin","SuperAdmin","Asesor","AreaMedica","Afiliaciones","MesaDeApoyo"})
    public OGENERARSOLICITUD2Response oGENERARSOLICITUD2CheckPreconditions(OGENERARSOLICITUD2Request request) {
        return service.oGENERARSOLICITUD2CheckPreconditions(request);
    }

    @POST
    @Path(value = "/TGCREARPERSONACORE")
    @RolesAllowed({"Admin","SuperAdmin","Asesor","Afiliaciones"})
    public TGCREARPERSONACOREResponse tGCREARPERSONACORE(TGCREARPERSONACORERequest request) {
        return service.tGCREARPERSONACORE(request);
    }

    @POST
    @Path(value = "/TGACTPERSONAENCORE")
    @RolesAllowed({"Admin","SuperAdmin","Asesor","AreaMedica","Afiliaciones"})
    public TGACTPERSONAENCOREResponse tGACTPERSONAENCORE(TGACTPERSONAENCORERequest request) {
        return service.tGACTPERSONAENCORE(request);
    }

    @POST
    @Path(value = "/TGCREARCONTRATOCORE")
    @RolesAllowed({"Admin","SuperAdmin","Asesor","AreaMedica","Afiliaciones"})
    public TGCREARCONTRATOCOREResponse tGCREARCONTRATOCORE(TGCREARCONTRATOCORERequest request) {
        return service.tGCREARCONTRATOCORE(request);
    }

    @POST
    @Path(value = "/TGINCLUSIONCONTRATOCORE")
    @RolesAllowed({"Admin","SuperAdmin","Asesor","AreaMedica","Afiliaciones"})
    public TGINCLUSIONCONTRATOCOREResponse tGINCLUSIONCONTRATOCORE(TGINCLUSIONCONTRATOCORERequest request) {
        return service.tGINCLUSIONCONTRATOCORE(request);
    }

    @POST
    @Path(value = "/TGNOTIFICARCREAR")
    @RolesAllowed({"SuperAdmin","UsuarioSistemaExterno"})
    public ServiceResponse tGNOTIFICARCREAR(TGNOTIFICARCREARRequest request) {
        return service.tGNOTIFICARCREAR(request);
    }

    @POST
    @Path(value = "/TGNOTIFICARINCLUSION")
    @RolesAllowed({"SuperAdmin","UsuarioSistemaExterno"})
    public ServiceResponse tGNOTIFICARINCLUSION(TGNOTIFICARINCLUSIONRequest request) {
        return service.tGNOTIFICARINCLUSION(request);
    }

    @POST
    @Path(value = "/TGNOTIFICARANULAR")
    @RolesAllowed({"SuperAdmin","UsuarioSistemaExterno"})
    public ServiceResponse tGNOTIFICARANULAR(TGNOTIFICARANULARRequest request) {
        return service.tGNOTIFICARANULAR(request);
    }

    @POST
    @Path(value = "/TGCREARACTPERSONACORE")
    @RolesAllowed({"Admin","SuperAdmin","Asesor","AreaMedica","Afiliaciones"})
    public TGCREARACTPERSONACOREResponse tGCREARACTPERSONACORE(TGCREARACTPERSONACORERequest request) {
        return service.tGCREARACTPERSONACORE(request);
    }

    @POST
    @Path(value = "/TGACTDIRECCIONES")
    @RolesAllowed({"Funcionario","Admin","SuperAdmin","Asesor","AreaMedica","Afiliaciones","RespRegional","Gestor","MesaDeApoyo"})
    public ServiceResponse tGACTDIRECCIONES(TGACTDIRECCIONESRequest request) {
        return service.tGACTDIRECCIONES(request);
    }

    @POST
    @Path(value = "/TGACTTIPOFACTURA")
    @RolesAllowed({"Funcionario","Admin","SuperAdmin","Asesor","AreaMedica","Afiliaciones","RespRegional","Gestor","MesaDeApoyo"})
    public ServiceResponse tGACTTIPOFACTURA(TGACTTIPOFACTURARequest request) {
        return service.tGACTTIPOFACTURA(request);
    }

    @POST
    @Path(value = "/TGRPTACTRNEW")
    @RolesAllowed({"Admin","SuperAdmin","UsuarioSistemaExterno"})
    public TGRPTACTRNEWResponse tGRPTACTRNEW(TGRPTACTRNEWRequest request) {
        return service.tGRPTACTRNEW(request);
    }

    @POST
    @Path(value = "/TGRPTACTRINC")
    @RolesAllowed({"Admin","SuperAdmin","UsuarioSistemaExterno"})
    public TGRPTACTRINCResponse tGRPTACTRINC(TGRPTACTRINCRequest request) {
        return service.tGRPTACTRINC(request);
    }
}
