package com.keralty.aegenerasolicitud.service;

import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.EstadoCivilConstants;
import com.keralty.aegenerasolicitud.persistence.EstadoCivil;
import com.keralty.aegenerasolicitud.persistence.oid.EstadoCivilOid;
import com.keralty.aegenerasolicitud.repository.EstadoCivilJpaRepository;
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
public class EstadoCivilService extends AbstractService {

    EstadoCivilJpaRepository repository;

    EstadoCivilService estadoCivilSrv;

    @Inject
    public EstadoCivilService(
            EstadoCivilService estadoCivilSrv,
            EstadoCivilJpaRepository repository) {
        this.estadoCivilSrv = estadoCivilSrv;
        this.repository = repository;
    }


    /*
     * Returns an instance of EstadoCivil by ID
     *
     * @param estadoCivilCodigo Identification field estadoCivilCodigo
     *
     * @return Instance of EstadoCivil
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public EstadoCivil getById(Long estadoCivilCodigo) {
        return getByOID(new EstadoCivilOid(estadoCivilCodigo));
    }
    
    /*
     * Returns an instance of EstadoCivil by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of EstadoCivil
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public EstadoCivil getByOID(EstadoCivilOid oid) {
        Optional<EstadoCivil> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of EstadoCivil by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of EstadoCivil
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public EstadoCivil getByOIDWithHV(EstadoCivilOid oid) {
        Optional<EstadoCivil> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<EstadoCivil> getInstanceByOid(EstadoCivilOid oid) {
        return oid != null && oid.getEstadoCivilCodigo() != null
               ? repository.findById(oid.getEstadoCivilCodigo())
               : Optional.empty();
    }

    /*
     * Returns an instance of EstadoCivil by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of EstadoCivil
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public EstadoCivil getByJsonOid(String jsonOid) {
        EstadoCivilOid oid = new EstadoCivilOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }


    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            EstadoCivilOid oid = new EstadoCivilOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(EstadoCivilConstants.ATTR_FIELD_ESTADOCIVILCODIGO, Constants.Type.INT.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getEstadoCivilCodigo(), Constants.Type.INT.getTypeName()));
            sql.addComparison(comparison1);
        } catch (Exception e) {
            LoggerFactory.getLogger(EstadoCivilService.class).error(e.getMessage());
        }
    }

}
