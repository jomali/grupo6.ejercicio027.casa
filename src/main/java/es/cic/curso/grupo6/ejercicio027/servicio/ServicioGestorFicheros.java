package es.cic.curso.grupo6.ejercicio027.servicio;

import java.util.List;

import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

/**
 * Define operaciones CRUD sobre los ficheros.
 * 
 * 
 * @author J. Francisco Martín
 * @author José María Cagigas
 * @serial 1.0
 * @version 2017/02/24
 *
 */
public interface ServicioGestorFicheros {

	/**
	 * Añade un nuevo fichero al sistema que cuelgue del directorio que se
	 * corresponda con el identificador pasado como parámetro.
	 * 
	 * @param idDirectorio
	 *            Identificador del directorio del que cuelga el nuevo fichero
	 * @param fichero
	 *            Nuevo fichero que se añade al sistema
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún
	 *             directorio registrado en el sistema, o si ya existe un
	 *             fichero en el sistema con el mismo nombre
	 */
	void agregaFichero(Long idDirectorio, Fichero fichero);

	/**
	 * Retorna el fichero registrado en el sistema que se corresponde con el
	 * identificador pasado como parámetro.
	 * 
	 * @param idFichero
	 *            Identificador del fichero
	 * @return Fichero que se corresponde con el identificador dado
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún fichero
	 *             registrado en el sistema
	 */
	Fichero obtenFichero(Long idFichero);

	/**
	 * Reemplaza en el sistema el fichero que se corresponde con el
	 * identificador pasado como parámetro por el nuevo fichero indicado.
	 * 
	 * @param idFichero
	 *            Identificador del fichero
	 * @param fichero
	 *            Fichero por el que se reemplaza el fichero original
	 * @return Fichero original registrado en el sistema antes del reemplazo
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún fichero
	 *             registrado en el sistema o si ya existe un fichero en el
	 *             sistema con el mismo nombre y en el mismo directorio del
	 *             fichero que se pretende modificar
	 */
	Fichero modificaFichero(Long idFichero, Fichero fichero);

	/**
	 * Elimina del sistema el fichero que se corresponde con el identificador
	 * pasado como parámetro.
	 * 
	 * @param idFichero
	 *            Identificador del fichero
	 * @return Fichero que se elimina del sistema
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún fichero
	 *             registrado en el sistema
	 */
	Fichero eliminaFichero(Long idFichero);

	/**
	 * Elimina del sistema todos los ficheros que cuelguen del directorio que se
	 * corresponde con el identificador pasado como parámetro.
	 * 
	 * @param idDirectorio
	 *            Identificador del directorio
	 * @return Lista con con todos los ficheros que se eliminan del sistema
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún
	 *             directorio registrado en el sistema
	 */
	List<Fichero> eliminaFicherosPorDirectorio(Long idDirectorio);

	/**
	 * Retorna una lista con todos los ficheros registrados en el sistema.
	 * 
	 * @return Lista con todos los ficheros registrados en el sistema
	 */
	List<Fichero> listaFicheros();

	/**
	 * Retorna una lista con todos los ficheros registrados en el sistema que
	 * cuelguen del directorio que se corresponde con el identificador pasado
	 * como parámetro.
	 * 
	 * @param idDirectorio
	 *            Identificador del directorio
	 * @return Lista con todos los ficheros que cuelgan del directorio dado
	 * @throws IllegalArgumentException
	 *             Si el identificador dado no se corresponde con ningún
	 *             directorio registrado en el sistema
	 */
	List<Fichero> listaFicherosPorDirectorio(Long idDirectorio);

}
