package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.Constants;
import com.keralty.aegenerasolicitud.global.CuestionarioPConstants;
import com.keralty.aegenerasolicitud.global.PreguntaPConstants;
import com.keralty.aegenerasolicitud.global.SeccionPConstants;
import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.persistence.CuestionarioP;
import com.keralty.aegenerasolicitud.persistence.PreguntaP;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLPopulation;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereComparison;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereConjunction;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandLiteral;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandPathAttribute;
import com.keralty.aegenerasolicitud.sqlutils.TypedQueryBuilder;
import com.keralty.aegenerasolicitud.utils.Agent;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.List;

public class CuestionarioPJpaRepositoryImpl extends AbstractRepository implements CuestionarioPJpaRepositoryCustom {

    @Inject
    public CuestionarioPJpaRepositoryImpl(Agent agent, UserFunctions userFunctionsSrv) {
        super(agent, userFunctionsSrv);
    }

    @Override
    public List<CuestionarioP> executeQuery(String jpqlQuery) {
        return getQueryBuilder().build(jpqlQuery).execute();
    }
    
    @Override
    public List<CuestionarioP> executeQuery(String jpqlQuery, List<SQLParam> params) {
        return getQueryBuilder().build(jpqlQuery).setParameters(params).execute();
    }
    
    public Collection<CuestionarioP> executeQuery(SQLSelect select) {
        return getQueryBuilder().build(select).execute();
    }

    private TypedQueryBuilder<CuestionarioP> getQueryBuilder() {
        return new TypedQueryBuilder<>(em, CuestionarioP.class);
    }

    @Override	
    public List<CuestionarioP> solveQuery(SQLSelect select) {
        return solveQuery(select, 0, 0);
    }

    @Override
    public List<CuestionarioP> solveQuery(SQLSelect select, int pageSize, int pageNumber) {
        return getQueryBuilder().build(select, pageSize, pageNumber).execute();
    }

    // AssocOperator: AssocOperator008
	@Override
    public Collection<PreguntaP> assocOperator008(CuestionarioP instance) throws SystemException {
        try {
            // FOR ALL Secciones.Preguntas WHERE (Secciones.Preguntas.Numero = 108) DO Secciones.Preguntas.marcarRespuesta(Secciones.Preguntas, true, fugetValorParametroConf("COMENTARIOSPREGUNTA108"), "")
        return assocOperator008Range1(instance);
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: CuestionarioP, Assoc.Operator: AssocOperator008, Method: assocOperator008";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }

    // Range 1 for AssocOperator AssocOperator008
	@Override
    public Collection<PreguntaP> assocOperator008Range1 (CuestionarioP instance) throws SystemException {

        SQLSelect sql = assocOperator008Range1Query(instance);
        return new TypedQueryBuilder<PreguntaP>(em, PreguntaP.class).build(sql).execute();
    }

    // Range Query 1 for AssocOperator AssocOperator008
	@Override
    public SQLPopulation assocOperator008Range1Query (CuestionarioP instance) throws SystemException {
        try {
            SQLPopulation sql = new SQLPopulation(CuestionarioPConstants.CLASS_NAME, PreguntaPConstants.CLASS_NAME, instance.getOid(), CuestionarioPConstants.ROLE_NAME_SECCIONES + "." + SeccionPConstants.ROLE_NAME_PREGUNTAS);
            SQLWhereConjunction conjunction = new SQLWhereConjunction();

            // Secciones.Preguntas.Numero = 108
                    // '=' Comparison
                    SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
                    comparison1.setOperator("=");
                    
                    // Operand 1
                    SQLWhereOperandPathAttribute operand112 = new SQLWhereOperandPathAttribute(CuestionarioPConstants.ROLE_NAME_SECCIONES + "." + SeccionPConstants.ROLE_NAME_PREGUNTAS + "." + PreguntaPConstants.ATTR_NAME_PREGUNTAPNUMERO, Constants.Type.INT.getTypeName());
                    comparison1.setFirstOperand(operand112);
                    
                    // Operand 2
                    comparison1.setSecondOperand(new SQLWhereOperandLiteral(Long.valueOf(108), Constants.Type.NAT.getTypeName()));
                    conjunction.addOperand(comparison1);

            sql.addOperand(conjunction);
            return sql;
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: CuestionarioP, Assoc.Operator: AssocOperator008, Method: assocOperator008Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }

    @Override
    public void refreshEntity(Object object) {
        super.refreshEntity(object);
    }
}
