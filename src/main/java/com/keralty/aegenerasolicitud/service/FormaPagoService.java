package com.keralty.aegenerasolicitud.service;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.FormaPagoConstants;
import com.keralty.aegenerasolicitud.persistence.FormaPago;
import com.keralty.aegenerasolicitud.persistence.oid.FormaPagoOid;
import com.keralty.aegenerasolicitud.repository.FormaPagoJpaRepository;
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
public class FormaPagoService extends AbstractService {

    FormaPagoJpaRepository repository;

    FormaPagoService formaPagoSrv;

    @Inject
    public FormaPagoService(
            FormaPagoService formaPagoSrv,
            FormaPagoJpaRepository repository) {
        this.formaPagoSrv = formaPagoSrv;
        this.repository = repository;
    }


    /*
     * Returns an instance of FormaPago by ID
     *
     * @param formaPagoCodigo Identification field formaPagoCodigo
     *
     * @return Instance of FormaPago
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public FormaPago getById(Long formaPagoCodigo) {
        return getByOID(new FormaPagoOid(formaPagoCodigo));
    }
    
    /*
     * Returns an instance of FormaPago by OID
     *
     * @param oid Object IDentifier
     *
     * @return Instance of FormaPago
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public FormaPago getByOID(FormaPagoOid oid) {
        Optional<FormaPago> instance = getInstanceByOid(oid);
        return instance.isPresent() ? instance.get() : null;
    }
    
    /*
     * Returns an instance of FormaPago by OID applying horizontal visibility constraints
     *
     * @param oid Object IDentifier
     *
     * @return Instance of FormaPago
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public FormaPago getByOIDWithHV(FormaPagoOid oid) {
        Optional<FormaPago> instance = getInstanceByOid(oid);
    	return instance.isPresent() && instance.get().checkHorizontalVisibility(getAgentInstance()) 
    		 ? instance.get() 
    		 : null;
    }
    
    private Optional<FormaPago> getInstanceByOid(FormaPagoOid oid) {
        return oid != null && oid.getFormaPagoCodigo() != null
               ? repository.findById(oid.getFormaPagoCodigo())
               : Optional.empty();
    }

    /*
     * Returns an instance of FormaPago by OID in JSON format
     *
     * @param jsonOid Object IDentifier in JSON format
     *
     * @return Instance of FormaPago
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public FormaPago getByJsonOid(String jsonOid) {
        FormaPagoOid oid = new FormaPagoOid();
        oid.fromJsonString(jsonOid);
        return getByOIDWithHV(oid);
    }


    @Override
    protected void applyFilterByOid(String jsonOid, SQLSelect sql) {
        try {
            FormaPagoOid oid = new FormaPagoOid().fromJsonString(jsonOid);
            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setOperator("=");
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(FormaPagoConstants.ATTR_FIELD_FORMAPAGOCODIGO, Constants.Type.INT.getTypeName()));
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(oid.getFormaPagoCodigo(), Constants.Type.INT.getTypeName()));
            sql.addComparison(comparison1);
        } catch (Exception e) {
            LoggerFactory.getLogger(FormaPagoService.class).error(e.getMessage());
            throw new SystemException(e.getMessage());
        }
    }

}
