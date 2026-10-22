package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.persistence.Admin;
import com.keralty.aegenerasolicitud.persistence.Afiliaciones;
import com.keralty.aegenerasolicitud.persistence.AreaMedica;
import com.keralty.aegenerasolicitud.persistence.Asesor;
import com.keralty.aegenerasolicitud.persistence.Funcionario;
import com.keralty.aegenerasolicitud.persistence.Gestor;
import com.keralty.aegenerasolicitud.persistence.GrupoAsignacion;
import com.keralty.aegenerasolicitud.persistence.MesaDeApoyo;
import com.keralty.aegenerasolicitud.persistence.RespRegional;
import com.keralty.aegenerasolicitud.persistence.SuperAdmin;
import com.keralty.aegenerasolicitud.sqlutils.SQLCount;
import com.keralty.aegenerasolicitud.sqlutils.SQLExists;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface FuncionarioJpaRepositoryCustom {

    List<Funcionario> solveQuery(SQLSelect query);
    
    List<Funcionario> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    Admin findChildAdmin(Long oid);

    SuperAdmin findChildSuperAdmin(Long oid);

    Asesor findChildAsesor(Long oid);

    AreaMedica findChildAreaMedica(Long oid);

    Afiliaciones findChildAfiliaciones(Long oid);

    RespRegional findChildRespRegional(Long oid);

    Gestor findChildGestor(Long oid);

    MesaDeApoyo findChildMesaDeApoyo(Long oid);

    Long assocOperator000(Funcionario instance) throws SystemException;

    Long assocOperator000Range1(Funcionario instance) throws SystemException;
    
    SQLCount assocOperator000Range1Query(Funcionario instance) throws SystemException;

    Boolean assocOperator002(GrupoAsignacion grupoAsignacionTADDUSUARIOpthisGrupoAsignacion) throws SystemException;

    Boolean assocOperator002Range1(GrupoAsignacion grupoAsignacionTADDUSUARIOpthisGrupoAsignacion) throws SystemException;
    
    SQLExists assocOperator002Range1Query(GrupoAsignacion grupoAsignacionTADDUSUARIOpthisGrupoAsignacion, SQLSelect superQuery) throws SystemException;

    Boolean assocOperator003(GrupoAsignacion grupoAsignacionTDELUSUARIOpthisGrupoAsignacion) throws SystemException;

    Boolean assocOperator003Range1(GrupoAsignacion grupoAsignacionTDELUSUARIOpthisGrupoAsignacion) throws SystemException;
    
    SQLExists assocOperator003Range1Query(GrupoAsignacion grupoAsignacionTDELUSUARIOpthisGrupoAsignacion, SQLSelect superQuery) throws SystemException;

    public List<Funcionario> executeQuery(String query);

    public List<Funcionario> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
