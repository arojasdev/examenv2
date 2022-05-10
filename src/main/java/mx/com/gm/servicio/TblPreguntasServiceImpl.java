package mx.com.gm.servicio;

import java.util.List;
import mx.com.gm.dao.TblPreguntasDao;
import mx.com.gm.domain.TblPreguntas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TblPreguntasServiceImpl implements TblPreguntasService {
    
    @Autowired
    private TblPreguntasDao preguntasDao;

    @Override
    public List<TblPreguntas> listarPreguntas() {
        return (List<TblPreguntas>) preguntasDao.findAll();
    }
    
}
