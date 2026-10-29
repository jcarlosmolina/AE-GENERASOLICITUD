package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Preexistencia;
import com.keralty.aegenerasolicitud.sqlutils.SQLParam;
import com.keralty.aegenerasolicitud.sqlutils.SQLSelect;
import java.util.List;

public interface PreexistenciaJpaRepositoryCustom {

    List<Preexistencia> solveQuery(SQLSelect query);
    
    List<Preexistencia> solveQuery(SQLSelect query, int pageSize, int pageNumber);
    
    int solveCountQuery(SQLSelect query);
    
    Long nextPreexistenciaIdentificador(Long aesolicitudesidsolicitud, Long usuarioUsuarioIdentificador);

    public List<Preexistencia> executeQuery(String query);

    public List<Preexistencia> executeQuery(String query, List<SQLParam> params);

    void refreshEntity(Object object);
}
