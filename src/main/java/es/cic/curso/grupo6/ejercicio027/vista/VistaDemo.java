package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.List;

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
	public static final int NUM_DIRECTORIOS = 3;
	
	/** Número de ficheros que se crean por directorio para la demo. */
	public static final int NUM_FICHEROS = 5;

	/** Permite navegar entre las vistas de la aplicación. */
	private Navigator navegador;

	/** Servicios con lógica de negocio y acceso a BB.DD. */
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
		if (!servicioGestorDirectorios.listaDirectorios().isEmpty())
			return;
		
		for (int i = 0; i < NUM_DIRECTORIOS; i++) {
			Directorio directorio = new Directorio();
			directorio.setRuta("demo/directorio" + i + "/");
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
		navegador.navigateTo("");
	}

}
