package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.BeneficiarioCConstants;
import com.keralty.aegenerasolicitud.global.ContratanteConstants;
import com.keralty.aegenerasolicitud.global.ErrorWSConstants;
import com.keralty.aegenerasolicitud.global.FamiliaCConstants;
import com.keralty.aegenerasolicitud.global.GlobalServiceConstants;
import com.keralty.aegenerasolicitud.global.PersonaCConstants;
import com.keralty.aegenerasolicitud.global.PersonaConstants;
import com.keralty.aegenerasolicitud.global.SolicitudConstants;
import com.keralty.aegenerasolicitud.global.TipoFacturaConstants;
import com.keralty.aegenerasolicitud.global.TitularConstants;
import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.global.UsuarioConstants;
import com.keralty.aegenerasolicitud.persistence.BeneficiarioC;
import com.keralty.aegenerasolicitud.persistence.Contratante;
import com.keralty.aegenerasolicitud.persistence.ErrorWS;
import com.keralty.aegenerasolicitud.persistence.FamiliaC;
import com.keralty.aegenerasolicitud.persistence.PersonaC;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.TipoFactura;
import com.keralty.aegenerasolicitud.persistence.Titular;
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
    // AssocOperator: AssocOperator110
	@Override
    public Boolean assocOperator110(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        try {
            // EXIST (ptpSolicitudAE.ErroresWS)
            return assocOperator110Range1(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator110, Method: assocOperator110";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Boolean assocOperator110Range1 (Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        SQLExists sql = assocOperator110Range1Query(oGENERARSOLICITUD2ptpSolicitudAE, null);
        return !new TypedQueryBuilder<ErrorWS>(em, ErrorWS.class).build(sql).execute().isEmpty();
    }


    public SQLExists assocOperator110Range1Query (Solicitud oGENERARSOLICITUD2ptpSolicitudAE, SQLSelect superQuery) throws SystemException {
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
            String exceptionMessage = "Assoc.Operator: AssocOperator110, Method: assocOperator110Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator111
	@Override
    public Boolean assocOperator111(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        try {
            // EXIST (ptpSolicitudAE.Contratante)
            return assocOperator111Range1(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator111, Method: assocOperator111";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Boolean assocOperator111Range1 (Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        SQLExists sql = assocOperator111Range1Query(oGENERARSOLICITUD2ptpSolicitudAE, null);
        return !new TypedQueryBuilder<Contratante>(em, Contratante.class).build(sql).execute().isEmpty();
    }


    public SQLExists assocOperator111Range1Query (Solicitud oGENERARSOLICITUD2ptpSolicitudAE, SQLSelect superQuery) throws SystemException {
        try {
            SQLExists sql;
            // The path starts with an object
            if (superQuery != null) {
                sql = new SQLExists(superQuery, "", GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + ContratanteConstants.CLASS_NAME, SolicitudConstants.CLASS_NAME, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
            } else {
                sql = new SQLExists("", GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + ContratanteConstants.CLASS_NAME, SolicitudConstants.CLASS_NAME, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
            }
            return sql;
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator111, Method: assocOperator111Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator112
	@Override
    public Boolean assocOperator112(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        try {
            // EXIST (ptpSolicitudAE.Titular)
            return assocOperator112Range1(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator112, Method: assocOperator112";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Boolean assocOperator112Range1 (Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        SQLExists sql = assocOperator112Range1Query(oGENERARSOLICITUD2ptpSolicitudAE, null);
        return !new TypedQueryBuilder<Titular>(em, Titular.class).build(sql).execute().isEmpty();
    }


    public SQLExists assocOperator112Range1Query (Solicitud oGENERARSOLICITUD2ptpSolicitudAE, SQLSelect superQuery) throws SystemException {
        try {
            SQLExists sql;
            // The path starts with an object
            if (superQuery != null) {
                sql = new SQLExists(superQuery, "", GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + TitularConstants.CLASS_NAME, SolicitudConstants.CLASS_NAME, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
            } else {
                sql = new SQLExists("", GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE + "." + TitularConstants.CLASS_NAME, SolicitudConstants.CLASS_NAME, oGENERARSOLICITUD2ptpSolicitudAE.getOid(), GlobalServiceConstants.GS_ARG_NAME_OGENERARSOLICITUD2_OGENERARSOLICITUD2PTPSOLICITUDAE);
            }
            return sql;
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator112, Method: assocOperator112Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator113
	@Override
    public Boolean assocOperator113(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        try {
            // EXIST (ptpSolicitudAE.ErroresWS)
            return assocOperator110(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator113, Method: assocOperator113";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator114
	@Override
    public Boolean assocOperator114(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        try {
            // EXIST (ptpSolicitudAE.TipoFactura)
            return assocOperator114Range1(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator114, Method: assocOperator114";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Boolean assocOperator114Range1 (Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        SQLExists sql = assocOperator114Range1Query(oGENERARSOLICITUD2ptpSolicitudAE, null);
        return !new TypedQueryBuilder<TipoFactura>(em, TipoFactura.class).build(sql).execute().isEmpty();
    }


    public SQLExists assocOperator114Range1Query (Solicitud oGENERARSOLICITUD2ptpSolicitudAE, SQLSelect superQuery) throws SystemException {
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
            String exceptionMessage = "Assoc.Operator: AssocOperator114, Method: assocOperator114Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator115
	@Override
    public Boolean assocOperator115(Solicitud oGENERARSOLICITUD2ptpSolicitudAE) throws SystemException {
        try {
            // EXIST (ptpSolicitudAE.ErroresWS)
            return assocOperator110(oGENERARSOLICITUD2ptpSolicitudAE);
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator115, Method: assocOperator115";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }

    // AssocOperator: AssocOperator123
	@Override
    public Collection<FamiliaC> assocOperator123() throws SystemException {
        try {
            // FOR ALL FamiliaC DO FamiliaC.TCREARACTBENEF(FamiliaC, NULL, NULL, fugetParentescoTitular(), NULL, "0", false, NULL, NULL, NULL, NULL, false, NULL, systemDate(), 0, fugetTipoIdentificacion("0"), "", "", 0)
            return assocOperator123Range1();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator123, Method: assocOperator123";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Collection<FamiliaC> assocOperator123Range1 () throws SystemException {

        SQLSelect sql = assocOperator123Range1Query();
        return new TypedQueryBuilder<FamiliaC>(em, FamiliaC.class).build(sql).execute();
    }


    public SQLPopulation assocOperator123Range1Query () throws SystemException {
        try {
            return new SQLPopulation("", FamiliaCConstants.CLASS_NAME);
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator123, Method: assocOperator123Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator124
	@Override
    public Collection<PersonaC> assocOperator124() throws SystemException {
        try {
            // FOR ALL PersonaC DO PreexistenciaC.crear(PersonaC, "", systemDate(), systemDate(), "")
            return assocOperator124Range1();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator124, Method: assocOperator124";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Collection<PersonaC> assocOperator124Range1 () throws SystemException {

        SQLSelect sql = assocOperator124Range1Query();
        return new TypedQueryBuilder<PersonaC>(em, PersonaC.class).build(sql).execute();
    }


    public SQLPopulation assocOperator124Range1Query () throws SystemException {
        try {
            return new SQLPopulation("", PersonaCConstants.CLASS_NAME);
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator124, Method: assocOperator124Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator125
	@Override
    public Collection<BeneficiarioC> assocOperator125() throws SystemException {
        try {
            // FOR ALL BeneficiarioC DO ConceptoC.crear(BeneficiarioC, "", "", 0, 0)
            return assocOperator125Range1();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator125, Method: assocOperator125";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Collection<BeneficiarioC> assocOperator125Range1 () throws SystemException {

        SQLSelect sql = assocOperator125Range1Query();
        return new TypedQueryBuilder<BeneficiarioC>(em, BeneficiarioC.class).build(sql).execute();
    }


    public SQLPopulation assocOperator125Range1Query () throws SystemException {
        try {
            return new SQLPopulation("", BeneficiarioCConstants.CLASS_NAME);
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator125, Method: assocOperator125Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }

    // AssocOperator: AssocOperator126
	@Override
    public Collection<FamiliaC> assocOperator126() throws SystemException {
        try {
            // FOR ALL FamiliaC DO FamiliaC.TCREARACTBENEF(FamiliaC, NULL, NULL, fugetParentescoTitular(), NULL, "0", false, NULL, NULL, NULL, NULL, false, NULL, systemDate(), 0, fugetTipoIdentificacion("0"), "", "", 0)
            return assocOperator123();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator126, Method: assocOperator126";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator127
	@Override
    public Collection<PersonaC> assocOperator127() throws SystemException {
        try {
            // FOR ALL PersonaC DO PreexistenciaC.crear(PersonaC, "", systemDate(), systemDate(), "")
            return assocOperator124();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator127, Method: assocOperator127";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator128
	@Override
    public Collection<BeneficiarioC> assocOperator128() throws SystemException {
        try {
            // FOR ALL BeneficiarioC DO ConceptoC.crear(BeneficiarioC, "", "", 0, 0)
            return assocOperator125();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator128, Method: assocOperator128";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }

    // AssocOperator: AssocOperator154
	@Override
    public Collection<Solicitud> assocOperator154() throws SystemException {
        try {
            // FOR ALL Solicitud DO Solicitud.TCAMBIARESTADO(Solicitud, 0, "", "")
            return assocOperator154Range1();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator154, Method: assocOperator154";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }



    public Collection<Solicitud> assocOperator154Range1 () throws SystemException {

        SQLSelect sql = assocOperator154Range1Query();
        return new TypedQueryBuilder<Solicitud>(em, Solicitud.class).build(sql).execute();
    }


    public SQLPopulation assocOperator154Range1Query () throws SystemException {
        try {
            return new SQLPopulation("", SolicitudConstants.CLASS_NAME);
        } catch (Exception e) {
            String exceptionMessage = "Assoc.Operator: AssocOperator154, Method: assocOperator154Range1Query";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator155
	@Override
    public Collection<Solicitud> assocOperator155() throws SystemException {
        try {
            // FOR ALL Solicitud DO ErrorWS.crear(Solicitud, "")
            return assocOperator154();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator155, Method: assocOperator155";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }

    // AssocOperator: AssocOperator156
	@Override
    public Collection<Solicitud> assocOperator156() throws SystemException {
        try {
            // FOR ALL Solicitud DO Solicitud.TCAMBIARESTADO(Solicitud, 0, "", "")
            return assocOperator154();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator156, Method: assocOperator156";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }
    // AssocOperator: AssocOperator157
	@Override
    public Collection<Solicitud> assocOperator157() throws SystemException {
        try {
            // FOR ALL Solicitud DO ErrorWS.crear(Solicitud, "")
            return assocOperator154();
        } catch (Exception e) {
            String exceptionMessage = "Definition Class: GLOBAL, Assoc.Operator: AssocOperator157, Method: assocOperator157";
            org.slf4j.LoggerFactory.getLogger(this.getClass()).error(exceptionMessage);
            throw new SystemException(e, exceptionMessage);
        }
    }

    @Override
    public void refreshEntity(Object object) {
        super.refreshEntity(object);
    }
}
