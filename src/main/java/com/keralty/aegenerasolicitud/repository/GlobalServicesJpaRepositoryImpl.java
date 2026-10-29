package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.BeneficiarioCConstants;
import com.keralty.aegenerasolicitud.global.ErrorWSConstants;
import com.keralty.aegenerasolicitud.global.FamiliaCConstants;
import com.keralty.aegenerasolicitud.global.GlobalServiceConstants;
import com.keralty.aegenerasolicitud.global.PersonaCConstants;
import com.keralty.aegenerasolicitud.global.PersonaConstants;
import com.keralty.aegenerasolicitud.global.SolicitudConstants;
import com.keralty.aegenerasolicitud.global.TipoFacturaConstants;
import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.global.UsuarioConstants;
import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.ErrorWS;
import com.keralty.aegenerasolicitud.persistence.FamiliaC;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.TipoFactura;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import com.keralty.aegenerasolicitud.sqlutils.SQLExists;
import com.keralty.aegenerasolicitud.sqlutils.SQLPopulation;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereComparison;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereConjunction;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandNull;
import com.keralty.aegenerasolicitud.sqlutils.SQLWhereOperandOVParameter;
import com.keralty.aegenerasolicitud.sqlutils.TypedQueryBuilder;
import com.keralty.aegenerasolicitud.utils.Agent;
import jakarta.inject.Inject;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class GlobalServicesJpaRepositoryImpl extends AbstractRepository implements GlobalServicesJpaRepository {

    @Inject
    public GlobalServicesJpaRepositoryImpl(Agent agent, UserFunctions userFunctionsSrv) {
        super(agent, userFunctionsSrv);
    }

    // AssocOperator: AssocOperator102
	@Override
    public Collection<ErrorWS> assocOperator102(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        try {
            // FOR ALL ptpSolicitudAE.ErroresWS DO ErrorWS.eliminar(ptpSolicitudAE.ErroresWS)
            return assocOperator102Range1(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator102, Method: assocOperator102";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Collection<ErrorWS> assocOperator102Range1 (Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {

        SQLSelect sql = assocOperator102Range1Query(oGENERARSOLICITUD2ptpSolicitudAE);
        return new TypedQueryBuilder<ErrorWS>(em, ErrorWS.class).build(sql).execute();
    }


    public SQLPopulation assocOperator102Range1Query (Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        try {
            return new SQLPopulation("", ErrorWSConstants.CLASS_NAME, GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + SolicitudConstants.ROLE_NAME_ERRORESWS, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator102, Method: assocOperator102Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator103
	@Override
    public Collection<Usuario> assocOperator103(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        try {
            // FOR ALL ptpSolicitudAE.Usuarios WHERE (ptpSolicitudAE.Usuarios.UsuarioPersona <> ptpSolicitudAE.ContratantePersona AND ptpSolicitudAE.Usuarios.UsuarioPersona <> ptpSolicitudAE.TitularPersona) DO TGCREARACTPERSONACORE(ptpSolicitudAE, ptpSolicitudAE.Usuarios.UsuarioPersona; ptpsErrorWS, null)
            return assocOperator103Range1(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator103, Method: assocOperator103";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Collection<Usuario> assocOperator103Range1 (Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {

        SQLSelect sql = assocOperator103Range1Query(oGENERARSOLICITUD2ptpSolicitudAE);
        return new TypedQueryBuilder<Usuario>(em, Usuario.class).build(sql).execute();
    }


    public SQLPopulation assocOperator103Range1Query (Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        try {
            SQLPopulation sql = new SQLPopulation("", UsuarioConstants.CLASS_NAME, GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + SolicitudConstants.ROLE_NAME_USUARIOS, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
            SQLWhereConjunction conjunction = new SQLWhereConjunction();

            // ptpSolicitudAE.Usuarios.UsuarioPersona <> ptpSolicitudAE.ContratantePersona
                    // '<>' Comparison
                    SQLWhereComparison comparison1 = new SQLWhereComparison(sql);
                    comparison1.setOperator("<>");
                    
                    // Operand 1
                    SQLWhereOperandOVParameter operand112 = new SQLWhereOperandOVParameter(GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + SolicitudConstants.ROLE_NAME_USUARIOS + "." + UsuarioConstants.ROLE_NAME_USUARIOPERSONA, SolicitudConstants.CLASS_NAME, PersonaConstants.CLASS_NAME, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
                    comparison1.setFirstOperand(operand112);
                    
                    // Operand 2
                    if (oGENERARSOLICITUD2ptpSolicitudAE.isNull() || oGENERARSOLICITUD2ptpSolicitudAE.getContratantePersona().isNull())
                    {
                    comparison1.setSecondOperand(new SQLWhereOperandNull());
                    }
                    else
                    {
                    SQLWhereOperandOVParameter operand223 = new SQLWhereOperandOVParameter(GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + SolicitudConstants.ROLE_NAME_CONTRATANTEPERSONA, SolicitudConstants.CLASS_NAME, PersonaConstants.CLASS_NAME, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
                    comparison1.setSecondOperand(operand223);
                    }
                    conjunction.addOperand(comparison1);


            // ptpSolicitudAE.Usuarios.UsuarioPersona <> ptpSolicitudAE.TitularPersona
                    // '<>' Comparison
                    SQLWhereComparison comparison2 = new SQLWhereComparison(sql);
                    comparison2.setOperator("<>");
                    
                    // Operand 1
                    SQLWhereOperandOVParameter operand113 = new SQLWhereOperandOVParameter(GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + SolicitudConstants.ROLE_NAME_USUARIOS + "." + UsuarioConstants.ROLE_NAME_USUARIOPERSONA, SolicitudConstants.CLASS_NAME, PersonaConstants.CLASS_NAME, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
                    comparison2.setFirstOperand(operand113);
                    
                    // Operand 2
                    if (oGENERARSOLICITUD2ptpSolicitudAE.isNull() || oGENERARSOLICITUD2ptpSolicitudAE.getTitularPersona().isNull())
                    {
                    comparison2.setSecondOperand(new SQLWhereOperandNull());
                    }
                    else
                    {
                    SQLWhereOperandOVParameter operand224 = new SQLWhereOperandOVParameter(GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + SolicitudConstants.ROLE_NAME_TITULARPERSONA, SolicitudConstants.CLASS_NAME, PersonaConstants.CLASS_NAME, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
                    comparison2.setSecondOperand(operand224);
                    }
                    conjunction.addOperand(comparison2);

            sql.addOperand(conjunction);
            return sql;
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator103, Method: assocOperator103Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator107
	@Override
    public Boolean assocOperator107(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        try {
            // EXIST (ptpSolicitudAE.ErroresWS)
            return assocOperator107Range1(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator107, Method: assocOperator107";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Boolean assocOperator107Range1 (Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        SQLExists sql = assocOperator107Range1Query(oGENERARSOLICITUD2ptpSolicitudAE, null);
        return !new TypedQueryBuilder<ErrorWS>(em, ErrorWS.class).build(sql).execute().isEmpty();
    }


    public SQLExists assocOperator107Range1Query (Solicitud oGENERARSOLICITUD2ptpSolicitudAE, SQLSelect superQuery) throws SystemException {
        try {
            SQLExists sql;
            // The path starts with an object
            if (superQuery != null) {
                sql = new SQLExists(superQuery, "", GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + SolicitudConstants.ROLE_NAME_ERRORESWS, SolicitudConstants.CLASS_NAME, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
            } else {
                sql = new SQLExists("", GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + SolicitudConstants.ROLE_NAME_ERRORESWS, SolicitudConstants.CLASS_NAME, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
            }
            return sql;
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator107, Method: assocOperator107Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator108
	@Override
    public Boolean assocOperator108(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        try {
            // EXIST (ptpSolicitudAE.TipoFactura)
            return assocOperator108Range1(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator108, Method: assocOperator108";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Boolean assocOperator108Range1 (Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        SQLExists sql = assocOperator108Range1Query(oGENERARSOLICITUD2ptpSolicitudAE, null);
        return !new TypedQueryBuilder<TipoFactura>(em, TipoFactura.class).build(sql).execute().isEmpty();
    }


    public SQLExists assocOperator108Range1Query (Solicitud oGENERARSOLICITUD2ptpSolicitudAE, SQLSelect superQuery) throws SystemException {
        try {
            SQLExists sql;
            // The path starts with an object
            if (superQuery != null) {
                sql = new SQLExists(superQuery, "", GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + TipoFacturaConstants.CLASS_NAME, SolicitudConstants.CLASS_NAME, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
            } else {
                sql = new SQLExists("", GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + TipoFacturaConstants.CLASS_NAME, SolicitudConstants.CLASS_NAME, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
            }
            return sql;
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator108, Method: assocOperator108Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator109
	@Override
    public Boolean assocOperator109(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        try {
            // EXIST (ptpSolicitudAE.ErroresWS)
            return assocOperator107(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator109, Method: assocOperator109";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }

    // AssocOperator: AssocOperator117
	@Override
    public Collection<FamiliaC> assocOperator117() throws SystemException {
        try {
            // FOR ALL FamiliaC DO FamiliaC.TCREARACTBENEF(FamiliaC, NULL, NULL, fugetParentescoTitular(), NULL, "0", false, NULL, NULL, NULL, NULL, false, NULL, systemDate(), 0, fugetTipoIdentificacion("0"), "", "", 0)
            return assocOperator117Range1();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator117, Method: assocOperator117";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Collection<FamiliaC> assocOperator117Range1 () throws SystemException {

        SQLSelect sql = assocOperator117Range1Query();
        return new TypedQueryBuilder<FamiliaC>(em, FamiliaC.class).build(sql).execute();
    }


    public SQLPopulation assocOperator117Range1Query () throws SystemException {
        try {
            return new SQLPopulation("", FamiliaCConstants.CLASS_NAME);
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator117, Method: assocOperator117Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator118
	@Override
    public Collection<PersonaC> assocOperator118() throws SystemException {
        try {
            // FOR ALL PersonaC DO PreexistenciaC.crear(PersonaC, "", systemDate(), systemDate(), "")
            return assocOperator118Range1();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator118, Method: assocOperator118";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Collection<PersonaC> assocOperator118Range1 () throws SystemException {

        SQLSelect sql = assocOperator118Range1Query();
        return new TypedQueryBuilder<PersonaC>(em, PersonaC.class).build(sql).execute();
    }


    public SQLPopulation assocOperator118Range1Query () throws SystemException {
        try {
            return new SQLPopulation("", PersonaCConstants.CLASS_NAME);
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator118, Method: assocOperator118Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator119
	@Override
    public Collection<BeneficiarioC> assocOperator119() throws SystemException {
        try {
            // FOR ALL BeneficiarioC DO ConceptoC.crear(BeneficiarioC, "", "", 0, 0)
            return assocOperator119Range1();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator119, Method: assocOperator119";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Collection<BeneficiarioC> assocOperator119Range1 () throws SystemException {

        SQLSelect sql = assocOperator119Range1Query();
        return new TypedQueryBuilder<BeneficiarioC>(em, BeneficiarioC.class).build(sql).execute();
    }


    public SQLPopulation assocOperator119Range1Query () throws SystemException {
        try {
            return new SQLPopulation("", BeneficiarioCConstants.CLASS_NAME);
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator119, Method: assocOperator119Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }

    // AssocOperator: AssocOperator120
	@Override
    public Collection<FamiliaC> assocOperator120() throws SystemException {
        try {
            // FOR ALL FamiliaC DO FamiliaC.TCREARACTBENEF(FamiliaC, NULL, NULL, fugetParentescoTitular(), NULL, "0", false, NULL, NULL, NULL, NULL, false, NULL, systemDate(), 0, fugetTipoIdentificacion("0"), "", "", 0)
            return assocOperator117();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator120, Method: assocOperator120";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator121
	@Override
    public Collection<PersonaC> assocOperator121() throws SystemException {
        try {
            // FOR ALL PersonaC DO PreexistenciaC.crear(PersonaC, "", systemDate(), systemDate(), "")
            return assocOperator118();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator121, Method: assocOperator121";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator122
	@Override
    public Collection<BeneficiarioC> assocOperator122() throws SystemException {
        try {
            // FOR ALL BeneficiarioC DO ConceptoC.crear(BeneficiarioC, "", "", 0, 0)
            return assocOperator119();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator122, Method: assocOperator122";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }

    // AssocOperator: AssocOperator148
	@Override
    public Collection<Solicitud> assocOperator148() throws SystemException {
        try {
            // FOR ALL Solicitud DO Solicitud.TCAMBIARESTADO(Solicitud, 0, "", "")
            return assocOperator148Range1();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator148, Method: assocOperator148";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Collection<Solicitud> assocOperator148Range1 () throws SystemException {

        SQLSelect sql = assocOperator148Range1Query();
        return new TypedQueryBuilder<Solicitud>(em, Solicitud.class).build(sql).execute();
    }


    public SQLPopulation assocOperator148Range1Query () throws SystemException {
        try {
            return new SQLPopulation("", SolicitudConstants.CLASS_NAME);
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator148, Method: assocOperator148Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator149
	@Override
    public Collection<Solicitud> assocOperator149() throws SystemException {
        try {
            // FOR ALL Solicitud DO ErrorWS.crear(Solicitud, "")
            return assocOperator148();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator149, Method: assocOperator149";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }

    // AssocOperator: AssocOperator150
	@Override
    public Collection<Solicitud> assocOperator150() throws SystemException {
        try {
            // FOR ALL Solicitud DO Solicitud.TCAMBIARESTADO(Solicitud, 0, "", "")
            return assocOperator148();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator150, Method: assocOperator150";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator151
	@Override
    public Collection<Solicitud> assocOperator151() throws SystemException {
        try {
            // FOR ALL Solicitud DO ErrorWS.crear(Solicitud, "")
            return assocOperator148();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator151, Method: assocOperator151";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }

    @Override
    public void refreshEntity(Object object) {
        super.refreshEntity(object);
    }
}
