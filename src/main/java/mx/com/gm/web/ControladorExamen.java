package mx.com.gm.web;

import java.util.Date;
import java.util.List;
import java.util.Stack;
import javax.validation.Valid;
import mx.com.gm.domain.TblBitacoras;
import mx.com.gm.domain.TblExamenes;
import mx.com.gm.domain.TblExamenesPreguntas;
import mx.com.gm.servicio.TblBitacorasService;
import mx.com.gm.servicio.TblExamenesPreguntasService;
import mx.com.gm.servicio.TblExamenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/examen")
public class ControladorExamen {
    
    @Autowired
    private TblExamenesService examenService;
    
    @Autowired
    private TblExamenesPreguntasService examPreguntasService;
    
    @Autowired
    private TblBitacorasService bitacoraService;
    
    //Lista todos los examenes
    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public ResponseEntity<TblExamenes> list() {
        List<TblExamenes> examenes = examenService.listarExamenes();
        
        TblBitacoras bitacora = new TblBitacoras();
        bitacora.setIdAccion(2L);
        bitacora.setFechaMovimiento(new Date());
        bitacora.setFechaActualizacion(new Date());
        bitacora.setObservaciones("Lista examenes");
        bitacoraService.guardar(bitacora);
        
        return new ResponseEntity(examenes, HttpStatus.OK);
    }
    
    //Busca examen por id
    @RequestMapping(value = "/buscar", method = RequestMethod.GET)
    public ResponseEntity<TblExamenes> userById(@RequestParam(value = "id") long id) {
        TblExamenes examen = new TblExamenes();
        examen.setIdExamen(id);
        examen = examenService.econtrarExamen(examen);
        
        TblBitacoras bitacora = new TblBitacoras();
        bitacora.setIdAccion(3L);
        bitacora.setFechaMovimiento(new Date());
        bitacora.setFechaActualizacion(new Date());
        bitacora.setObservaciones("Busca examen por id");
        bitacoraService.guardar(bitacora);
        
        return new ResponseEntity(examen, HttpStatus.OK);
    }
    
    //Elimina examen por id
    @DeleteMapping("/eliminar")
    public void delete(@RequestParam(value = "id") long id) {
        TblExamenes examen = new TblExamenes();
        examen.setIdExamen(id);
        
        TblBitacoras bitacora = new TblBitacoras();
        bitacora.setIdAccion(4L);
        bitacora.setFechaMovimiento(new Date());
        bitacora.setFechaActualizacion(new Date());
        bitacora.setObservaciones("Elimina examen");
        bitacoraService.guardar(bitacora);
        
        examenService.eliminar(examen);
    }
    
    //Actualiza el examen
    @PutMapping("/actualiza")
    public ResponseEntity<TblExamenes> update(@Valid @RequestBody TblExamenes examen) {
        TblExamenes examenCreado = examenService.guardar(examen);
        
        TblBitacoras bitacora = new TblBitacoras();
        bitacora.setIdAccion(5L);
        bitacora.setFechaMovimiento(new Date());
        bitacora.setFechaActualizacion(new Date());
        bitacora.setObservaciones("Actualiza examen");
        bitacoraService.guardar(bitacora);
        
        return new ResponseEntity(examenCreado, HttpStatus.CREATED);
    }
    
    //Crea un nuevo examen
    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public ResponseEntity<TblExamenes> create(@Valid @RequestBody TblExamenes examen) {
        TblExamenes examenCreado = examenService.guardar(examen);
        //Inserta en la tabla examenpregunta
        generaExamenPregunta(examenCreado);
        
        TblBitacoras bitacora = new TblBitacoras();
        bitacora.setIdAccion(6L);
        bitacora.setFechaMovimiento(new Date());
        bitacora.setFechaActualizacion(new Date());
        bitacora.setObservaciones("Genera un nuevo examen");
        bitacoraService.guardar(bitacora);
        
        return new ResponseEntity(examenCreado, HttpStatus.CREATED);
    }
    
    //Recorre el arreglo de preguntas posibles y las inserta en la tabla examenespreguntas
    private void generaExamenPregunta(TblExamenes examen) {
        
        //Llama al metodo para obtener la preguntas al azar
        Stack<Integer> listIdPreguntas = randomArray();
        
        for(Integer idPregunta: listIdPreguntas) {
            
            TblExamenesPreguntas examPregunta = new TblExamenesPreguntas();
            //Id del examen guardado
            examPregunta.setIdExamen(examen.getIdExamen());
            //Id de la pregunta aleatoria
            examPregunta.setIdPregunta(new Long(idPregunta) + 1);
            examPregunta.setActivo(1);
            examPregunta.setFechaRegistro(new Date());
            examPregunta.setFechaActualizacion(new Date());
            examPreguntasService.guardar(examPregunta);
            examPregunta = null;
            
        }
        
    }
    
    //Genera un arreglo con los id de la lista de preguntas sin repetir (4 preguntas por examen guardado) (7 preguntas posibles)
    private Stack randomArray() {
        int idPregunta;
        int totalPreguntas = 7;
        Stack<Integer> listIdPreguntas = new Stack<Integer>();
        for (int i = 0; i < 4; i++) {
            idPregunta = (int) Math.floor(Math.random() * totalPreguntas);
            while (listIdPreguntas.contains(idPregunta)) {
                idPregunta = (int) Math.floor(Math.random() * totalPreguntas);
            }
            listIdPreguntas.push(idPregunta);
        }
        return listIdPreguntas;
    }

}
