package es.cic.curso.grupo6.ejercicio027.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.repositorio.RepositorioDirectorio;

public class ServicioGestorDirectoriosImpl implements ServicioGestorDirectorios {
	
	private static final String ERROR_DIRECTORIO_ID = "No existe ningún directorio en BB.DD. con ese ID";
	private static final String RUTA_SECUNDARIA = "/prueba/directorio";

	@Autowired
	private RepositorioDirectorio repositorioDirectorio;
	
	@Autowired
	private ServicioGestorFicheros servicioGestorFicheros;
	
	@Override
	public void agregaDirectorio(Directorio directorio) {
		
		repositorioDirectorio.create(directorio);
		
	
	}

	@Override
	public Directorio obtenDirectorio(Long id) {
		
		Directorio directorio = repositorioDirectorio.read(id);
		if (directorio == null) {
			throw new IllegalArgumentException(ERROR_DIRECTORIO_ID + ": " + id);
		}
		return directorio;
	}



	@Override
	public Directorio modificaDirectorio(Long id, Directorio directorio) {
		Directorio directorioInicial = eliminarDirectorio(id);
		
		String rutaNueva = directorioInicial.getRuta();
		if (servicioGestorFicheros.listaFicherosPorDirectorio(idDirectorio)) {
			throw new IllegalArgumentException("ERROR BUSCANDO DIRECTORIOS");
		}
		
		directorioInicial.setRuta(RUTA_SECUNDARIA);
		repositorioDirectorio.update(directorioInicial);
		return directorioInicial;
	}
	
	@Override
	public Directorio eliminarDirectorio(Long id) {
		
		Directorio directorio = repositorioDirectorio.delete(id);
		
		return directorio;
	}

	@Override
	public List<Directorio> listaEntradasPorDirectorio(Long id) {
		
		return repositorioDirectorio.list();
	}

}
