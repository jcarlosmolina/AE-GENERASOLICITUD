package com.keralty.aegenerasolicitud.repository;

import com.keralty.aegenerasolicitud.persistence.Pregunta;
import com.keralty.aegenerasolicitud.persistence.PreguntaP;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreguntaJpaRepository extends JpaRepository<Pregunta, Long>, PreguntaJpaRepositoryCustom {

    List<Pregunta> findByPreguntasPersona(PreguntaP preguntasPersona);
}
