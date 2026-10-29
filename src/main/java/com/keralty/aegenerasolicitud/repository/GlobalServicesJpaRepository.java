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

    Boolean assocOperator107(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException;

    Boolean assocOperator108(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException;

    Boolean assocOperator109(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException;

    Collection<FamiliaC> assocOperator117() throws SystemException;

    Collection<PersonaC> assocOperator118() throws SystemException;

    Collection<BeneficiarioC> assocOperator119() throws SystemException;

    Collection<FamiliaC> assocOperator120() throws SystemException;

    Collection<PersonaC> assocOperator121() throws SystemException;

    Collection<BeneficiarioC> assocOperator122() throws SystemException;

    Collection<Solicitud> assocOperator148() throws SystemException;

    Collection<Solicitud> assocOperator149() throws SystemException;

    Collection<Solicitud> assocOperator150() throws SystemException;

    Collection<Solicitud> assocOperator151() throws SystemException;

    void refreshEntity(Object object);
}
