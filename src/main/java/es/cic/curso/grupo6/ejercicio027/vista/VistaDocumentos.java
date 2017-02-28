package es.cic.curso.grupo6.ejercicio027.vista;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

	// Servicios con lógica de negocio y acceso a BB.DD.

	private ServicioGestorFicheros servicioGestorFicheros;

	// Componentes gráficos:

	@PropertyId("ruta")
	protected TextField textFieldRuta;

	private Grid gridDirectorios;
	private Button botonAgregarD, botonBorrarD, botonActualizarD;
	private Directorio nuevoDirectorio, directorioSeleccionado, eliminaDirectorio, actualizaDirectorio;
	private Grid gridFicheros;
	private Button botonAgregarF, botonBorrarF, botonActualizarF;
	private Fichero eliminaFichero;
	private Image imagen;
	private FormularioFicheros formulario;


	public VistaDocumentos() {

		// Servicios con lógica de negocio y acceso a BB.DD.

		servicioGestorFicheros = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorFicheros.class);

		// IMAGEN
		HorizontalLayout layoutimagen = new HorizontalLayout();
		layoutimagen.setSpacing(true);
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/logocic.png"));
		Image imagen = new Image(null, resource);
		imagen.setHeight(10.0F, Unit.PERCENTAGE);
		layoutimagen.addComponents(imagen);
		addComponent(layoutimagen);

		HorizontalLayout layoutBotonesDirectorios = new HorizontalLayout();
		layoutBotonesDirectorios.setMargin(false);
		layoutBotonesDirectorios.setSpacing(true);
		layoutBotonesDirectorios.addComponents(textFieldRuta, botonAgregarD, botonActualizarD, botonBorrarD);

		cargaGridDirectorios();

		HorizontalLayout layoutBotonesFicheros = new HorizontalLayout();
		layoutBotonesFicheros.setMargin(false);
		layoutBotonesFicheros.setSpacing(true);
		layoutBotonesFicheros.addComponents(botonAgregarF, botonActualizarF, botonBorrarF);

		// LAYOUT PRINCIPAL

		HorizontalLayout principalLayout = new HorizontalLayout();
		principalLayout.setMargin(true);
		principalLayout.setSpacing(true);
		principalLayout.setSizeFull();
		

		VerticalLayout layoutDirectorios = new VerticalLayout();
		layoutDirectorios.setMargin(false);
		layoutDirectorios.setSpacing(true);
		layoutDirectorios.addComponents(gridDirectorios, layoutBotonesDirectorios);

		VerticalLayout layoutFicheros = new VerticalLayout();
		layoutFicheros.setMargin(false);
		layoutFicheros.setSpacing(true);
		layoutFicheros.addComponents(gridFicheros, layoutBotonesFicheros);
		
		// GRID de DIRECTORIOS

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
				botonAgregarD.setVisible(false);
				botonActualizarD.setVisible(true);
				botonBorrarD.setVisible(true);
				botonAgregarF.setVisible(true);
				muestraDirectorio(directorio);
			} else {
				botonBorrarD.setVisible(false);
				botonActualizarD.setVisible(false);
				botonAgregarD.setVisible(true);
				botonAgregarF.setVisible(false);
				reiniciaTextField();
			}
			cargaGridFicheros(directorio);
		});

		// GRID de FICHEROS

		gridFicheros = new Grid();
		gridFicheros.setColumns("nombre", "descripcion", "version");
		gridFicheros.setSizeFull();
		gridFicheros.setSelectionMode(SelectionMode.SINGLE);
		gridFicheros.setCaption("Lista Ficheros:");
		gridFicheros.addSelectionListener(new SelectionListener() {

			@Override
			public void select(SelectionEvent event) {
				Set<Object> selected = event.getSelected();
				Fichero fchero = (Fichero) gridFicheros.getSelectedRow();
				if (fchero != null) {
					
					eliminaFichero = fchero;
					botonAgregarF.setVisible(false);
					botonBorrarF.setVisible(true);
					botonActualizarF.setVisible(true);
				} else {
					botonBorrarF.setVisible(false);
					botonActualizarF.setVisible(false);
				}
			}
		});

		// BOTONES DIRECTORIOS
		
		textFieldRuta = new TextField();
		textFieldRuta.setInputPrompt("Ruta de la Carpeta");

		botonAgregarD = new Button("Añadir Directorio");
		botonAgregarD.setIcon(FontAwesome.PLUS_CIRCLE);
		botonAgregarD.setVisible(true);
		botonAgregarD.setEnabled(true);
		botonAgregarD.addClickListener(agregar -> {
			agregarDirectorio(nuevoDirectorio);
			cargaGridDirectorios();
			reiniciaTextField();
			botonAgregarD.setVisible(true);
			botonActualizarD.setVisible(false);
			botonBorrarD.setVisible(false);

		});

		botonBorrarD = new Button("Borrar");
		botonBorrarD.setIcon(FontAwesome.ERASER);
		botonBorrarD.setVisible(false);
		botonBorrarD.addClickListener(borrar -> {
			borraDirectorio(eliminaDirectorio);
			cargaGridDirectorios();
			reiniciaTextField();
			botonActualizarD.setVisible(false);
			botonBorrarD.setVisible(false);

			botonAgregarD.setVisible(true);
		});

		botonActualizarD = new Button("Actualizar datos");
		botonActualizarD.setIcon(FontAwesome.REFRESH);
		botonActualizarD.setVisible(false);
		botonActualizarD.setEnabled(true);
		botonActualizarD.addClickListener(actualizar -> {
			try {
				if (textFieldRuta.getValue() != null) {
					String ruta = textFieldRuta.getValue();
					actualizaDirectorio.setRuta(ruta);
					actualizarDirectorio(actualizaDirectorio.getId(),actualizaDirectorio);
					Notification.show("Directorio modificado.");
					reiniciaTextField();
					cargaGridDirectorios();
				} else {

				}
			} catch (Exception o) {
				Notification.show("Algo está mal.");
			}
		});

		formulario = new FormularioFicheros(null);
		formulario.setVisible(false);
		// BOTONES FICHEROS

		botonAgregarF = new Button("Añade fichero");
		botonAgregarF.setVisible(false);
		botonAgregarF.setEnabled(true);
		botonAgregarF.addClickListener(agregar -> {
//			layoutDirectorios.setVisible(false);
//			layoutFicheros.setVisible(false);
			formulario.setVisible(true);
			
			
			cargaGridDirectorios();
			reiniciaTextField();
			botonAgregarD.setVisible(true);
			botonActualizarD.setVisible(false);
			botonBorrarD.setVisible(false);

		});

		botonBorrarF = new Button("Borrar");
		botonBorrarF.setIcon(FontAwesome.ERASER);
		botonBorrarF.setEnabled(true);
		botonBorrarF.setVisible(false);
		botonBorrarF.addClickListener(e -> {
			borraFichero(eliminaFichero);
			cargaGridFicheros(directorioSeleccionado);
		});

		botonActualizarF = new Button("Actualizar fichero");
		botonActualizarF.setIcon(FontAwesome.REFRESH);
		botonActualizarF.setVisible(false);
		botonActualizarF.setEnabled(true);

		

		principalLayout.addComponents(layoutDirectorios, layoutFicheros,formulario);
		addComponents(layoutimagen, principalLayout);
	}

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
		nuevoDirectorio.setRuta(textFieldRuta.getValue());
		servicioGestorFicheros.agregaDirectorio(nuevoDirectorio);
	}
	
	
	public void borraDirectorio(Directorio directorio) {
		servicioGestorFicheros.eliminaDirectorio(directorio.getId());
	}

	public void muestraDirectorio(Directorio directorio) {
		textFieldRuta.setValue(directorio.getRuta());
	}

	public void reiniciaTextField() {
		textFieldRuta.clear();
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
