package mx.com.gm.servicio;

import java.util.List;
import mx.com.gm.dao.TblExamenesPreguntasDao;
import mx.com.gm.domain.TblExamenesPreguntas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TblExamenesPreguntasServiceImpl implements TblExamenesPreguntasService {
    
    @Autowired
    private TblExamenesPreguntasDao examenesPreguntasDao;

    @Override
    @Transactional(readOnly = true)
    public List<TblExamenesPreguntas> listarExamenesPreguntas() {
        return (List<TblExamenesPreguntas>) examenesPreguntasDao.findAll();
    }

    @Override
    @Transactional
    public TblExamenesPreguntas guardar(TblExamenesPreguntas examenPregunta) {
        return examenesPreguntasDao.save(examenPregunta);
    }

    @Override
    @Transactional
    public void eliminar(TblExamenesPreguntas examenPregunta) {
        examenesPreguntasDao.delete(examenPregunta);
    }

    @Override
    @Transactional(readOnly = true)
    public TblExamenesPreguntas econtrarExamenPregunta(TblExamenesPreguntas examenPregunta) {
        return examenesPreguntasDao.findById(examenPregunta.getIdExamenPregunta()).orElse(null);
    }
    
}
