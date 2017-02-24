package es.cic.curso.grupo6.ejercicio027.servicio;

import java.util.List;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;

/**
 * Define operaciones CRUD sobre los directorios.
 * 
 * 
 * @author J. Francisco Martín
 * @author José María Cagigas
 * @serial 1.0
 * @version 2017/02/24
 *
 */
public interface ServicioGestorDirectorios {
	
	void agregaDirectorio(Directorio directorio);
	
	Directorio obtenEntradaDirectorio(Long id);
	
	Directorio eliminarDirectorio(Long id);
	
	Directorio modificaDirectirio(Long id, Directorio directorio);
	
	List<Directorio> listaEntradasPorDirectorio(Long idFichero);
	

}
