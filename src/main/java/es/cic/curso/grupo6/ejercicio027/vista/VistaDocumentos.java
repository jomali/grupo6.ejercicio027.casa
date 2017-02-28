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

	// Componentes gráficos:
	private Grid gridDirectorios, gridFicheros;

	@PropertyId("ruta")
	protected TextField textFieldRutaDirectorio;

	private Button botonAgregarDirectorio, botonBorrarDirectorio, botonRenombrarDirectorio;
	private Directorio nuevoDirectorio, directorioSeleccionado, eliminaDirectorio, actualizaDirectorio;
	private Button botonAgregarFichero, botonBorrarFichero, botonActualizarFichero;
	private Fichero eliminaFichero;
	private FormularioFicheros formulario;

	@SuppressWarnings("serial")
	public VistaDocumentos() {
		servicioGestorFicheros = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorFicheros.class);

		// layout. ENCABEZADO
		HorizontalLayout layoutEncabezado = inicializaLayoutEncabezado();
		
		// layout. DIRECTORIOS
		VerticalLayout layoutDirectorios = inicializaLayoutDirectorios();

		
		// layout. FICHEROS
		

		// grid. FICHEROS

		gridFicheros = new Grid();
		gridFicheros.setColumns("nombre", "descripcion", "version");
		gridFicheros.setSizeFull();
		gridFicheros.setSelectionMode(SelectionMode.SINGLE);
		gridFicheros.setCaption("Lista Ficheros:");
		gridFicheros.addSelectionListener(new SelectionListener() {
			@Override
			public void select(SelectionEvent event) {
				Fichero fchero = (Fichero) gridFicheros.getSelectedRow();
				if (fchero != null) {
					eliminaFichero = fchero;
					botonAgregarFichero.setVisible(false);
					botonBorrarFichero.setVisible(true);
					botonActualizarFichero.setVisible(true);
				} else {
					botonBorrarFichero.setVisible(false);
					botonActualizarFichero.setVisible(false);
				}
			}
		});

		// botones. FICHEROS

		botonAgregarFichero = new Button("Añadir fichero");
		botonAgregarFichero.setVisible(false);
		botonAgregarFichero.setEnabled(true);
		botonAgregarFichero.addClickListener(e -> {
			formulario.setVisible(true);
			botonAgregarDirectorio.setVisible(true);
			botonRenombrarDirectorio.setVisible(false);
			botonBorrarDirectorio.setVisible(false);
			cargaGridDirectorios();
			reiniciaTextField();
		});

		botonBorrarFichero = new Button("Borrar");
		botonBorrarFichero.setIcon(FontAwesome.ERASER);
		botonBorrarFichero.setEnabled(true);
		botonBorrarFichero.setVisible(false);
		botonBorrarFichero.addClickListener(e -> {
			borraFichero(eliminaFichero);
			cargaGridFicheros(directorioSeleccionado);
		});

		botonActualizarFichero = new Button("Actualizar fichero");
		botonActualizarFichero.setIcon(FontAwesome.REFRESH);
		botonActualizarFichero.setVisible(false);
		botonActualizarFichero.setEnabled(true);

		formulario = new FormularioFicheros(null);
		formulario.setVisible(false);
		
		HorizontalLayout layoutBotonesFicheros = new HorizontalLayout();
		layoutBotonesFicheros.setMargin(false);
		layoutBotonesFicheros.setSpacing(true);
		layoutBotonesFicheros.addComponents(botonAgregarFichero, botonActualizarFichero, botonBorrarFichero);



		// LAYOUT PRINCIPAL

		HorizontalLayout principalLayout = new HorizontalLayout();
		principalLayout.setMargin(true);
		principalLayout.setSpacing(true);
		principalLayout.setSizeFull();


		VerticalLayout layoutFicheros = new VerticalLayout();
		layoutFicheros.setMargin(false);
		layoutFicheros.setSpacing(true);
		
		cargaGridDirectorios();
		layoutFicheros.addComponents(gridFicheros, layoutBotonesFicheros);
		principalLayout.addComponents(layoutDirectorios, layoutFicheros, formulario);
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
	
	private VerticalLayout inicializaLayoutDirectorios() {
		// GRID DIRECTORIOS
		gridDirectorios = new Grid();
		gridDirectorios.setColumns("ruta");
		gridDirectorios.setSizeFull();
		gridDirectorios.setSelectionMode(SelectionMode.SINGLE);
		gridDirectorios.setCaption("Lista Directorios:");
		gridDirectorios.addSelectionListener(e -> {
			Directorio directorio = null;
			if (!e.getSelected().isEmpty()) {
				directorio = (Directorio) e.getSelected().iterator().next();
				actualizaDirectorio = directorio;
				eliminaDirectorio = directorio;
				botonAgregarDirectorio.setVisible(false);
				botonRenombrarDirectorio.setVisible(true);
				botonBorrarDirectorio.setVisible(true);
				botonAgregarFichero.setVisible(true);
				muestraDirectorio(directorio);
			} else {
				botonBorrarDirectorio.setVisible(false);
				botonRenombrarDirectorio.setVisible(false);
				botonAgregarDirectorio.setVisible(true);
				botonAgregarFichero.setVisible(false);
				reiniciaTextField();
			}
			cargaGridFicheros(directorio);
		});

		// TEXTFIELD RUTA DIRECTORIO
		textFieldRutaDirectorio = new TextField();
		textFieldRutaDirectorio.setInputPrompt("Ruta del directorio");

		// BUTTON AGREGAR DIRECTORIO
		botonAgregarDirectorio = new Button("Añadir Directorio");
		botonAgregarDirectorio.setIcon(FontAwesome.PLUS);
		botonAgregarDirectorio.setVisible(true);
		botonAgregarDirectorio.setEnabled(true);
		botonAgregarDirectorio.addClickListener(agregar -> {
			agregarDirectorio(nuevoDirectorio);
			cargaGridDirectorios();
			reiniciaTextField();
			botonAgregarDirectorio.setVisible(true);
			botonRenombrarDirectorio.setVisible(false);
			botonBorrarDirectorio.setVisible(false);

		});

		// BUTTON BORRAR DIRECTORIO
		botonBorrarDirectorio = new Button("Borrar");
		botonBorrarDirectorio.setIcon(FontAwesome.ERASER);
		botonBorrarDirectorio.setVisible(false);
		botonBorrarDirectorio.addClickListener(borrar -> {
			borraDirectorio(eliminaDirectorio);
			cargaGridDirectorios();
			reiniciaTextField();
			botonRenombrarDirectorio.setVisible(false);
			botonBorrarDirectorio.setVisible(false);

			botonAgregarDirectorio.setVisible(true);
		});

		// BUTTON RENOMBRAR DIRECTORIO
		botonRenombrarDirectorio = new Button("Renombrar");
		botonRenombrarDirectorio.setIcon(FontAwesome.REFRESH);
		botonRenombrarDirectorio.setVisible(false);
		botonRenombrarDirectorio.setEnabled(true);
		botonRenombrarDirectorio.addClickListener(e -> {
			try {
				if (textFieldRutaDirectorio.getValue() != null) {
					String ruta = textFieldRutaDirectorio.getValue();
					actualizaDirectorio.setRuta(ruta);
					actualizarDirectorio(actualizaDirectorio.getId(), actualizaDirectorio);
					Notification.show("Directorio modificado.");
					reiniciaTextField();
					cargaGridDirectorios();
				} else {

				}
			} catch (Exception o) {
				Notification.show("Algo está mal.");
			}
		});

		// LAYOUT BOTONES DIRECTORIOS
		HorizontalLayout layoutBotonesDirectorios = new HorizontalLayout();
		layoutBotonesDirectorios.setMargin(false);
		layoutBotonesDirectorios.setSpacing(true);
		layoutBotonesDirectorios.addComponents(textFieldRutaDirectorio, botonAgregarDirectorio,
				botonRenombrarDirectorio, botonBorrarDirectorio);
		
		VerticalLayout layoutDirectorios = new VerticalLayout();
		layoutDirectorios.setMargin(true);
		layoutDirectorios.setSpacing(true);
		layoutDirectorios.addComponents(gridDirectorios, layoutBotonesDirectorios);
		return layoutDirectorios;
	}
	
	private VerticalLayout inicializaLayoutFicheros() {
		VerticalLayout layoutFicheros = new VerticalLayout();
		return layoutFicheros;
	}

	// /////////////////////////////////////////////////////////////////////////

	private List<Directorio> cargarLista() {
		return servicioGestorFicheros.listaDirectorios();
	}

	public void borraFichero(Fichero fichero) {
		servicioGestorFicheros.eliminaFichero(fichero.getId());
	}

	public void cargaGridFicheros(Directorio directorio) {
		directorioSeleccionado = directorio;
		Collection<Fichero> ficheros = (directorio == null) ? new ArrayList<>()
				: servicioGestorFicheros.listaFicherosPorDirectorio(directorio.getId());
		gridFicheros.setContainerDataSource(new BeanItemContainer<>(Fichero.class, ficheros));
	}

	public void actualizarDirectorio(long directorioId, Directorio directorio) {
		servicioGestorFicheros.modificaDirectorio(directorioId, directorio);
	}

	public void agregarDirectorio(Directorio directorio) {
		Directorio nuevoDirectorio = new Directorio();
		nuevoDirectorio.setRuta(textFieldRutaDirectorio.getValue());
		servicioGestorFicheros.agregaDirectorio(nuevoDirectorio);
	}

	public void borraDirectorio(Directorio directorio) {
		servicioGestorFicheros.eliminaDirectorio(directorio.getId());
	}

	public void muestraDirectorio(Directorio directorio) {
		textFieldRutaDirectorio.setValue(directorio.getRuta());
	}

	public void reiniciaTextField() {
		textFieldRutaDirectorio.clear();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		cargaGridDirectorios();

	}

	public void cargaGridDirectorios() {
		Collection<Directorio> directorios = servicioGestorFicheros.listaDirectorios();
		gridDirectorios.setContainerDataSource(new BeanItemContainer<>(Directorio.class, directorios));
	}
}
