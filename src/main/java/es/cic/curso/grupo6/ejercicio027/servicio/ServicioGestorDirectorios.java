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

	/**
	 * Añade un nuevo directorio al sistema. Dos directorios no pueden tener una
	 * misma ruta.
	 * 
	 * @param directorio
	 *            Nuevo directorio que se añade al sistema
	 * @throws IllegalArgumentException
	 *             Si el directorio que se intenta añadir al sistema tiene la
	 *             misma ruta de un directorio ya existente
	 */
	void agregaDirectorio(Directorio directorio);

	/**
	 * Retorna el directorio registrado en el sistema que se corresponde con el
	 * identificador pasado como parámetro.
	 * 
	 * @param idDirectorio
	 *            Identificador del directorio
	 * @return Directorio que se corresponde con el identificador dado
	 */
	Directorio obtenDirectorio(Long idDirectorio);

	/**
	 * Reemplaza en el sistema el directorio que se corresponde con el
	 * identificador pasado como parámetro por el nuevo directorio indicado.
	 * 
	 * @param idDirectorio
	 *            Identificador del directorio
	 * @param directorio
	 *            Directorio por el que se reemplaza el directorio original
	 * @return Directorio original registrado en el sistema antes del reemplazo
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún
	 *             directorio registrado en el sistema
	 * @throws IllegalStateException
	 *             Si existen ficheros que cuelgan del directorio dado
	 */
	Directorio modificaDirectorio(Long idDirectorio, Directorio directorio);

	/**
	 * Elimina del sistema el directorio que se corresponde con el identificador
	 * pasado como parámetro.
	 * 
	 * @param idDirectorio
	 *            Identificador del directorio
	 * @return Directorio que se elimina del sistema
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún
	 *             directorio registrado en el sistema
	 */
	Directorio eliminaDirectorio(Long idDirectorio);

	/**
	 * Retorna una lista con todos los directorios registrados en el sistema.
	 * 
	 * @return Lista con todos los directorios registrados en el sistema
	 */
	List<Directorio> listaDirectorios();

}
