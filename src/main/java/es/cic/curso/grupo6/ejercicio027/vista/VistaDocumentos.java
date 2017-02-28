package es.cic.curso.grupo6.ejercicio027.vista;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class VistaDocumentos extends VerticalLayout implements View {
	private static final long serialVersionUID = 6070082071055226969L;

	/** Lógica de negocio con acceso a BB.DD. */
	private ServicioGestorFicheros servicioGestorFicheros;


	/** Controles para la manipulación de los directorios del sistema. */
	private LayoutDirectorios layoutDirectorios;

	/** Controles para la manipulación de los ficheros del sistema. */
	private LayoutFicheros layoutFicheros;


	public VistaDocumentos() {
		servicioGestorFicheros = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorFicheros.class);

		// layout. ENCABEZADO
		HorizontalLayout layoutEncabezado = inicializaLayoutEncabezado();

		// layout. DIRECTORIOS

		layoutDirectorios = new LayoutDirectorios(this, servicioGestorFicheros);

		// layout. FICHEROS
		layoutFicheros = new LayoutFicheros(this, servicioGestorFicheros);

		VerticalLayout layoutDirectorios = new LayoutDirectorios(this, servicioGestorFicheros);

		// layout. FICHEROS
		VerticalLayout layoutFicheros = new LayoutFicheros(this, servicioGestorFicheros);


		// layout. PRINCIPAL
		HorizontalLayout principalLayout = new HorizontalLayout();
		principalLayout.setMargin(true);
		principalLayout.setSpacing(true);
		principalLayout.setSizeFull();
		principalLayout.addComponents(layoutDirectorios, layoutFicheros);

		addComponents(layoutEncabezado, principalLayout);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Inicialización de componentes gráficos:

	private HorizontalLayout inicializaLayoutEncabezado() {
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/logocic.png"));
		Image imagen = new Image(null, resource);
		imagen.setHeight(10.0F, Unit.PERCENTAGE);

		HorizontalLayout layoutEncabezado = new HorizontalLayout();
		layoutEncabezado.setMargin(false);
		layoutEncabezado.setSpacing(false);
		layoutEncabezado.addComponent(imagen);
		return layoutEncabezado;
	}

	// /////////////////////////////////////////////////////////////////////////

	@Override
	public void enter(ViewChangeEvent event) {
		
		layoutDirectorios.cargaGridDirectorios();
	}
	
	public void activaBotonAgregarFichero(boolean activado) {
		layoutFicheros.activaBotonAgregarFichero(activado);
	}
	
	public void cargaGridFicheros(Directorio directorio){
		layoutFicheros.cargaGridFicheros(directorio);
	}
	

	public void modificaBotonesDirectorios() {
		layoutDirectorios.modificaBotones();
	}

}
