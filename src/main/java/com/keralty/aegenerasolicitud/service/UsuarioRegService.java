package com.keralty.aegenerasolicitud.service;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.AesolicitudesConstants;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.SolicitudConstants;
import com.keralty.aegenerasolicitud.global.UsuarioRegConstants;
import com.keralty.aegenerasolicitud.persistence.oid.UsuarioRegOid;
import com.keralty.aegenerasolicitud.persistence.UsuarioReg;
import com.keralty.aegenerasolicitud.repository.UsuarioRegJpaRepository;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereComparison;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandLiteral;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandPathAttribute;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.slf4j.LoggerFactory;

@Transactional
@ApplicationScoped
public class UsuarioRegService extends AbstractService {

    UsuarioRegJpaRepository repository;

    UsuarioRegService usuarioRegSrv;

    @Inject
    public UsuarioRegService(
            UsuarioRegService usuarioRegSrv,
            UsuarioRegJpaRepository repository) {
        this.usuarioRegSrv = usuarioRegSrv;
        this.repository = repository;
    }


    /*
     * Returns an instance of UsuarioReg by ID
     *
     * @param aesolicitudesidsolicitud Identification field aesolicitudesidsolicitud
     *
     * @param usuarioRegIdentificador Identification field usuarioRegIdentificador
     *
     * @return Instance of UsuarioReg
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public UsuarioReg getById(Long aesolicitudesidsolicitud, Long usuarioRegIdentificador) {
        return getByOID(new UsuarioRegOid(aesolicitudesidsolicitud, usuarioRegIdentificador));
    }
    
    /*
     * Returns an instance of UsuarioReg by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of UsuarioReg
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public UsuarioReg getByOID(UsuarioRegOid oid) {
        Optional<UsuarioReg> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of UsuarioReg by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of UsuarioReg
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public UsuarioReg getByOIDWithHV(UsuarioRegOid oid) {
        Optional<UsuarioReg> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<UsuarioReg> getInstanceByOid(UsuarioRegOid oid) {
        return oid != null && oid.getAesolicitudesidsolicitud() != null
               ? repository.findById(oid)
               : Optional.empty();
    }

    /*
     * Returns an instance of UsuarioReg by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of UsuarioReg
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public UsuarioReg getByJsonOid(String jsonOid) {
        UsuarioRegOid oid = new UsuarioRegOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }


    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            UsuarioRegOid oid = new UsuarioRegOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(UsuarioRegConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_FIELD_AESOLICITUDESIDSOLICITUD, Constants.Type.AUTONUMERIC.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getAesolicitudesidsolicitud(), Constants.Type.AUTONUMERIC.getTypeName()));
            sql.addComparison(comparison1);
            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setOperator("=");
            comparison2.setFirstOperand(new SQLWhereOperandPathAttribute(UsuarioRegConstants.ATTR_FIELD_USUARIOREGIDENTIFICADOR, Constants.Type.AUTONUMERIC.getTypeName()));
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(oid.getUsuarioRegIdentificador(), Constants.Type.AUTONUMERIC.getTypeName()));
            sql.addComparison(comparison2);
        } catch (Exception e) {
            LoggerFactory.getLogger(UsuarioRegService.class).error(e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

}
