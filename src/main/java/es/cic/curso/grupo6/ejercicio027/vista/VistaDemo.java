package es.cic.curso.grupo6.ejercicio027.vista;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.springframework.web.context.ContextLoader;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorDirectorios;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class VistaDemo extends VerticalLayout implements View {
	private static final long serialVersionUID = -8229167069516384540L;

	/** Número de directorios que se crean para la demo. */
	public static final int NUM_DIRECTORIOS = 4;

	/** Número de ficheros que se crean por directorio para la demo. */
	public static final int NUM_FICHEROS = 8;

	/** Permite navegar entre las vistas de la aplicación. */
	private Navigator navegador;

	// Servicios con lógica de negocio y acceso a BB.DD.

	private ServicioGestorDirectorios servicioGestorDirectorios;
	private ServicioGestorFicheros servicioGestorFicheros;

	public VistaDemo(Navigator navegador) {
		this.navegador = navegador;
		this.servicioGestorDirectorios = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioGestorDirectorios.class);
		this.servicioGestorFicheros = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioGestorFicheros.class);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (servicioGestorDirectorios.listaDirectorios().isEmpty()) {
			limpiaDirectorio(ServicioGestorDirectorios.DIRECTORIO_BASE);
			for (int i = 0; i < NUM_DIRECTORIOS; i++) {
				Directorio directorio = new Directorio();
				directorio.setRuta("directorio" + i);
				servicioGestorDirectorios.agregaDirectorio(directorio);
				for (int j = 0; j < NUM_FICHEROS; j++) {
					Fichero fichero = new Fichero();
					fichero.setNombre("fichero" + i);
					fichero.setDescripcion("Fichero de prueba");
					fichero.setVersion(1.0);
					servicioGestorFicheros.agregaFichero(directorio.getId(), fichero);
				}
			}
			Notification.show("Cargados datos de DEMOSTRACIÓN");
		}
		navegador.navigateTo("");
	}

	private void limpiaDirectorio(String ruta) {
		Path rootPath = Paths.get(ruta);
		try {
			Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					System.out.println("delete file: " + file.toString());
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					System.out.println("delete dir: " + dir.toString());
					return FileVisitResult.CONTINUE;
				}
			});
			System.out.println("create dir: " + rootPath.toString());
			Files.createDirectory(rootPath);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

}
