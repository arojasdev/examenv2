package mx.com.gm.servicio;

import java.util.List;
import mx.com.gm.domain.TblExamenesPreguntas;

public interface TblExamenesPreguntasService {
    
    public List<TblExamenesPreguntas> listarExamenesPreguntas();
    
    public TblExamenesPreguntas guardar(TblExamenesPreguntas examenPregunta);
    
    public void eliminar(TblExamenesPreguntas examenPregunta);
    
    public TblExamenesPreguntas econtrarExamenPregunta(TblExamenesPreguntas examenPregunta);
    
}
