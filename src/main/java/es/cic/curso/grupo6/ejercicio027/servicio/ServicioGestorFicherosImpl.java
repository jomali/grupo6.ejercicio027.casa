package es.cic.curso.grupo6.ejercicio027.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;
import es.cic.curso.grupo6.ejercicio027.repositorio.RepositorioFichero;

/**
 * Implementación de la interfaz <code>ServicioGestorFicheros</code>.
 * 
 * 
 * @author J. Francisco Martín
 * @author José María Cagigas
 * @serial 1.0
 * @version 2017/02/24
 *
 */
@Service
@Transactional
public class ServicioGestorFicherosImpl implements ServicioGestorFicheros {

	private static final String ERROR_ID_FICHERO = "No existe ningún fichero en BB.DD. con ese ID";
	
	@Autowired
	private ServicioGestorDirectorios servicioGestorDirectorios;
	
	@Autowired
	private RepositorioFichero repositorioFichero;

	@Override
	public void agregaFichero(Long idDirectorio, Fichero fichero) {
		Directorio directorio = servicioGestorDirectorios.obtenDirectorio(idDirectorio);
		fichero.setDirectorio(directorio);
		repositorioFichero.create(fichero);
	}

	@Override
	public Fichero obtenFichero(Long idFichero) {
		Fichero fichero = repositorioFichero.read(idFichero);
		if (fichero == null) {
			throw new IllegalArgumentException(ERROR_ID_FICHERO + ": " + idFichero);
		}
		return fichero;
	}

	@Override
	public Fichero modificaFichero(Long idFichero, Fichero fichero) {
		Fichero original = obtenFichero(idFichero);
		fichero.setId(idFichero);
		repositorioFichero.update(fichero);
		return original;
	}

	@Override
	public Fichero eliminaFichero(Long idFichero) {
		Fichero fichero = obtenFichero(idFichero);
		repositorioFichero.delete(fichero);
		return fichero;
	}

	@Override
	public List<Fichero> eliminaFicherosPorDirectorio(Long idDirectorio) {
		// Comprueba que el directorio esté registrado en el sistema
		servicioGestorDirectorios.obtenDirectorio(idDirectorio);
		// Elimina y retorna los ficheros que cuelgan del directorio dado
		return repositorioFichero.deleteByDirectory(idDirectorio);
	}

	@Override
	public List<Fichero> listaFicheros() {
		return repositorioFichero.list();
	}

	@Override
	public List<Fichero> listaFicherosPorDirectorio(Long idDirectorio) {
		// Comprueba que el directorio esté registrado en el sistema
		servicioGestorDirectorios.obtenDirectorio(idDirectorio);
		// Retorna la lista de ficheros para el directorio dado
		return repositorioFichero.listByDirectory(idDirectorio);
	}

}
