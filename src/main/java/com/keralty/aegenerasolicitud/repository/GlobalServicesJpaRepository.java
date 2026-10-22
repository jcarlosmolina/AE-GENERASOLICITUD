package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.ErrorWS;
import com.keralty.aegenerasolicitud.persistence.FamiliaC;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import java.util.Collection;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalServicesJpaRepository {

    Collection<ErrorWS> assocOperator102(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException;

    Collection<Usuario> assocOperator103(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException;

    Boolean assocOperator110(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException;

    Boolean assocOperator111(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException;

    Boolean assocOperator112(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException;

    Boolean assocOperator113(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException;

    Boolean assocOperator114(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException;

    Boolean assocOperator115(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException;

    Collection<FamiliaC> assocOperator123() throws SystemException;

    Collection<PersonaC> assocOperator124() throws SystemException;

    Collection<BeneficiarioC> assocOperator125() throws SystemException;

    Collection<FamiliaC> assocOperator126() throws SystemException;

    Collection<PersonaC> assocOperator127() throws SystemException;

    Collection<BeneficiarioC> assocOperator128() throws SystemException;

    Collection<Solicitud> assocOperator154() throws SystemException;

    Collection<Solicitud> assocOperator155() throws SystemException;

    Collection<Solicitud> assocOperator156() throws SystemException;

    Collection<Solicitud> assocOperator157() throws SystemException;

    void refreshEntity(Object object);
}
