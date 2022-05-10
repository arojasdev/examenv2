package mx.com.gm.dao;

import mx.com.gm.domain.TblPreguntas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblPreguntasDao extends JpaRepository <TblPreguntas, Long> {
    
}
