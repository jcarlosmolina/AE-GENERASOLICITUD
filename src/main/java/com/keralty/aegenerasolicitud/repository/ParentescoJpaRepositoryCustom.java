package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.persistence.Parentesco;
import com.keralty.aegenerasolicitud.persistence.Usuario;
import com.keralty.aegenerasolicitud.persistence.UsuarioNovedad;
import com.keralty.aegenerasolicitud.sqlutils.SQLExists;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface ParentescoJpaRepositoryCustom {

    List<Parentesco> solveQuery(SQLSelect query);
    
    List<Parentesco> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);

    Boolean assocOperator001(Usuario usuarioTMODIFICARATTRpthisAttrUsuario) throws SystemException;

    Boolean assocOperator001Range1(Usuario usuarioTMODIFICARATTRpthisAttrUsuario) throws SystemException;
    
    SQLExists assocOperator001Range1Query(Usuario usuarioTMODIFICARATTRpthisAttrUsuario, SQLSelect superQuery) throws SystemException;

    Boolean assocOperator002(UsuarioNovedad usuarioNovedadTCAMBIARPARENTESCOpthisUsuarioNovedad) throws SystemException;

    Boolean assocOperator002Range1(UsuarioNovedad usuarioNovedadTCAMBIARPARENTESCOpthisUsuarioNovedad) throws SystemException;
    
    SQLExists assocOperator002Range1Query(UsuarioNovedad usuarioNovedadTCAMBIARPARENTESCOpthisUsuarioNovedad, SQLSelect superQuery) throws SystemException;

    public List<Parentesco> executeQuery(String query);

    public List<Parentesco> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
