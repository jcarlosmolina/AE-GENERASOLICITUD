package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.global.AesolicitudesConstants;
import com.keralty.aegenerasolicitud.global.PreexistenciaConstants;
import com.keralty.aegenerasolicitud.global.SolicitudConstants;
import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.global.UsuarioConstants;
import com.keralty.aegenerasolicitud.persistence.Preexistencia;
import com.keralty.aegenerasolicitud.sqlutils.SQLMax;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
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
import org.slf4j.LoggerFactory;

public class PreexistenciaJpaRepositoryImpl extends AbstractRepository implements PreexistenciaJpaRepositoryCustom {

    @Inject
    public PreexistenciaJpaRepositoryImpl(Agent agent, UserFunctions userFunctionsSrv) {
        super(agent, userFunctionsSrv);
    }

    @Override
    public List<Preexistencia> executeQuery(String jpqlQuery) {
        return getQueryBuilder().build(jpqlQuery).execute();
    }
    
    @Override
    public List<Preexistencia> executeQuery(String jpqlQuery, List<SQLParam> params) {
        return getQueryBuilder().build(jpqlQuery).setParameters(params).execute();
    }
    
    public Collection<Preexistencia> executeQuery(SQLSelect select) {
        return getQueryBuilder().build(select).execute();
    }

    private TypedQueryBuilder<Preexistencia> getQueryBuilder() {
        return new TypedQueryBuilder<>(em, Preexistencia.class);
    }

    @Override	
    public List<Preexistencia> solveQuery(SQLSelect select) {
        return solveQuery(select, 0, 0);
    }

    @Override
    public List<Preexistencia> solveQuery(SQLSelect select, int pageSize, int pageNumber) {
        return getQueryBuilder().build(select, pageSize, pageNumber).execute();
    }

    @Override
    public Long nextPreexistenciaIdentificador(Long aesolicitudesidsolicitud, Long usuarioUsuarioIdentificador) {
        try {
            SQLMax sql = new SQLMax(null, PreexistenciaConstants.CLASS_NAME, PreexistenciaConstants.CLASS_NAME + "." + PreexistenciaConstants.ATTR_FIELD_PREEXISTENCIAIDENTIFICADOR);
            SQLWhereConjunction conjunction = new SQLWhereConjunction();

            SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
            comparison1.setFirstOperand(new SQLWhereOperandPathAttribute(PreexistenciaConstants.ROLE_NAME_USUARIO + "." + UsuarioConstants.ROLE_NAME_SOLICITUD + "." + SolicitudConstants.ROLE_NAME_AESOLICITUDES + "." + AesolicitudesConstants.ATTR_FIELD_AESOLICITUDESIDSOLICITUD, "Autonumerico"));
            comparison1.setOperator("=");
            comparison1.setSecondOperand(new SQLWhereOperandLiteral(aesolicitudesidsolicitud, "Autonumerico"));
            conjunction.addComparison(comparison1);

            SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
            comparison2.setFirstOperand(new SQLWhereOperandPathAttribute(PreexistenciaConstants.ROLE_NAME_USUARIO + "." + UsuarioConstants.ATTR_FIELD_USUARIOIDENTIFICADOR, "Autonumerico"));
            comparison2.setOperator("=");
            comparison2.setSecondOperand(new SQLWhereOperandLiteral(usuarioUsuarioIdentificador, "Autonumerico"));
            conjunction.addComparison(comparison2);

            sql.getWherePart().addConjunction(conjunction);

            Long tempResult = new TypedQueryBuilder<>(em, Long.class).build(sql).getSingleResult();
            return (tempResult != null ? tempResult + 1L : 1L);
        } catch (Exception e) {
            LoggerFactory.getLogger(Preexistencia.class).error(e.getMessage());
            return 1L;
        }
    }

    @Override
    public void refreshEntity(Object object) {
        super.refreshEntity(object);
    }
}
