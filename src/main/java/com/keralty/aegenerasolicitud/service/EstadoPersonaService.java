package com.keralty.aegenerasolicitud.service;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.EstadoPersonaConstants;
import com.keralty.aegenerasolicitud.persistence.EstadoPersona;
import com.keralty.aegenerasolicitud.persistence.oid.EstadoPersonaOid;
import com.keralty.aegenerasolicitud.repository.EstadoPersonaJpaRepository;
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
public class EstadoPersonaService extends AbstractService {

    EstadoPersonaJpaRepository repository;

    EstadoPersonaService estadoPersonaSrv;

    @Inject
    public EstadoPersonaService(
            EstadoPersonaService estadoPersonaSrv,
            EstadoPersonaJpaRepository repository) {
        this.estadoPersonaSrv = estadoPersonaSrv;
        this.repository = repository;
    }


    /*
     * Returns an instance of EstadoPersona by ID
     *
     * @param estadoPersonaCodigo Identification field estadoPersonaCodigo
     *
     * @return Instance of EstadoPersona
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public EstadoPersona getById(Long estadoPersonaCodigo) {
        return getByOID(new EstadoPersonaOid(estadoPersonaCodigo));
    }
    
    /*
     * Returns an instance of EstadoPersona by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of EstadoPersona
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public EstadoPersona getByOID(EstadoPersonaOid oid) {
        Optional<EstadoPersona> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of EstadoPersona by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of EstadoPersona
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public EstadoPersona getByOIDWithHV(EstadoPersonaOid oid) {
        Optional<EstadoPersona> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<EstadoPersona> getInstanceByOid(EstadoPersonaOid oid) {
        return oid != null && oid.getEstadoPersonaCodigo() != null
               ? repository.findById(oid.getEstadoPersonaCodigo())
               : Optional.empty();
    }

    /*
     * Returns an instance of EstadoPersona by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of EstadoPersona
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public EstadoPersona getByJsonOid(String jsonOid) {
        EstadoPersonaOid oid = new EstadoPersonaOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }


    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            EstadoPersonaOid oid = new EstadoPersonaOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(EstadoPersonaConstants.ATTR_FIELD_ESTADOPERSONACODIGO, Constants.Type.INT.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getEstadoPersonaCodigo(), Constants.Type.INT.getTypeName()));
            sql.addComparison(comparison1);
        } catch (Exception e) {
            LoggerFactory.getLogger(EstadoPersonaService.class).error(e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

}
