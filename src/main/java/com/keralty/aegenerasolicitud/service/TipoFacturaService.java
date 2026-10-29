package com.keralty.aegenerasolicitud.service;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.TipoFacturaConstants;
import com.keralty.aegenerasolicitud.persistence.oid.TipoFacturaOid;
import com.keralty.aegenerasolicitud.persistence.TipoFactura;
import com.keralty.aegenerasolicitud.repository.TipoFacturaJpaRepository;
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
public class TipoFacturaService extends AbstractService {

    TipoFacturaJpaRepository repository;

    TipoFacturaService tipoFacturaSrv;

    @Inject
    public TipoFacturaService(
            TipoFacturaService tipoFacturaSrv,
            TipoFacturaJpaRepository repository) {
        this.tipoFacturaSrv = tipoFacturaSrv;
        this.repository = repository;
    }


    /*
     * Returns an instance of TipoFactura by ID
     *
     * @param tipoFacturaCodigo Identification field tipoFacturaCodigo
     *
     * @return Instance of TipoFactura
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public TipoFactura getById(String tipoFacturaCodigo) {
        return getByOID(new TipoFacturaOid(tipoFacturaCodigo));
    }
    
    /*
     * Returns an instance of TipoFactura by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of TipoFactura
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public TipoFactura getByOID(TipoFacturaOid oid) {
        Optional<TipoFactura> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of TipoFactura by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of TipoFactura
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public TipoFactura getByOIDWithHV(TipoFacturaOid oid) {
        Optional<TipoFactura> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<TipoFactura> getInstanceByOid(TipoFacturaOid oid) {
        return oid != null && oid.getTipoFacturaCodigo() != null
               ? repository.findById(oid.getTipoFacturaCodigo())
               : Optional.empty();
    }

    /*
     * Returns an instance of TipoFactura by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of TipoFactura
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public TipoFactura getByJsonOid(String jsonOid) {
        TipoFacturaOid oid = new TipoFacturaOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }


    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            TipoFacturaOid oid = new TipoFacturaOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(TipoFacturaConstants.ATTR_FIELD_TIPOFACTURACODIGO, Constants.Type.STRING.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getTipoFacturaCodigo(), Constants.Type.STRING.getTypeName()));
            sql.addComparison(comparison1);
        } catch (Exception e) {
            LoggerFactory.getLogger(TipoFacturaService.class).error(e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

}
