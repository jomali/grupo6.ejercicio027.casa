package es.cic.curso.grupo6.ejercicio027.servicio;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.repositorio.RepositorioDirectorio;

/**
 * Implementación de la interfaz <code>ServicioGestorDirectorios</code>.
 * 
 * 
 * @author J. Francisco Martín
 * @author José María Cagigas
 * @serial 1.0
 * @version 2017/02/25
 *
 */
@Service
@Transactional
public class ServicioGestorDirectoriosImpl implements ServicioGestorDirectorios {

	private static final String ERROR_ID_DIRECTORIO = "No existe ningún directorio en BB.DD. con ese ID";
	private static final String ERROR_RUTA_DIRECTORIO = "Ya existe un directorio con esa ruta";
	private static final String ERROR_ESTADO_DIRECTORIO = "Existen ficheros colgando del directorio";

	@Autowired
	private RepositorioDirectorio repositorioDirectorio;

	@Autowired
	private ServicioGestorFicheros servicioGestorFicheros;

	@Override
	public void agregaDirectorio(Directorio directorio) {
		Path ruta = Paths.get(DIRECTORIO_BASE + directorio.getRuta());
		try {
			Files.createDirectory(ruta);
			repositorioDirectorio.create(directorio);
		} catch (FileAlreadyExistsException faee) {
			throw new IllegalArgumentException(ERROR_RUTA_DIRECTORIO);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	@Override
	public Directorio obtenDirectorio(Long idDirectorio) {
		Directorio directorio = repositorioDirectorio.read(idDirectorio);
		if (directorio == null) {
			throw new IllegalArgumentException(ERROR_ID_DIRECTORIO + ": " + idDirectorio);
		}
		return directorio;
	}

	@Override
	public Directorio modificaDirectorio(Long idDirectorio, Directorio directorio) {
		Directorio directorioOriginal = obtenDirectorio(idDirectorio);
		if (!servicioGestorFicheros.listaFicherosPorDirectorio(idDirectorio).isEmpty()) {
			throw new IllegalStateException(ERROR_ESTADO_DIRECTORIO);
		}
		directorio.setId(idDirectorio);
		repositorioDirectorio.update(directorio);
		return directorioOriginal;
	}

	@Override
	public Directorio eliminaDirectorio(Long idDirectorio) {
		Directorio directorio = obtenDirectorio(idDirectorio);
		servicioGestorFicheros.eliminaFicherosPorDirectorio(idDirectorio);
		repositorioDirectorio.delete(directorio);
		return directorio;
	}

	@Override
	public List<Directorio> listaDirectorios() {
		return repositorioDirectorio.list();
	}

}
