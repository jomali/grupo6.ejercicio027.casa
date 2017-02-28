package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class LayoutDirectorios extends VerticalLayout {
	private static final long serialVersionUID = -514197825792558255L;

	/** Lógica de negocio con acceso a BB.DD. */
	private ServicioGestorFicheros servicioGestorFicheros;
	
	/** Componente de tipo grid. */
	private Grid gridDirectorios;

	/** Referencia al directorio seleccionado en el grid. */
	private Directorio directorioSeleccionado;
	
	private Button botonAgregarDirectorio, botonBorrarDirectorio, botonRenombrarDirectorio;

	@PropertyId("ruta")
	protected TextField textFieldRutaDirectorio;

	public LayoutDirectorios(VistaDocumentos padre, ServicioGestorFicheros servicioGestorFicheros) {
		this.servicioGestorFicheros = servicioGestorFicheros;
		
		// GRID DIRECTORIOS
		gridDirectorios = new Grid();
		gridDirectorios.setColumns("ruta");
		gridDirectorios.setSizeFull();
		gridDirectorios.setSelectionMode(SelectionMode.SINGLE);
		gridDirectorios.setCaption("Lista Directorios:");
		gridDirectorios.addSelectionListener(e -> {
			directorioSeleccionado = null;
			if (!e.getSelected().isEmpty()) {
				directorioSeleccionado = (Directorio) e.getSelected().iterator().next();
				botonAgregarDirectorio.setVisible(false);
				botonRenombrarDirectorio.setVisible(true);
				botonBorrarDirectorio.setVisible(true);
				padre.muestraBotonAgregarFichero(true);
			} else {
				botonAgregarDirectorio.setVisible(true);
				botonRenombrarDirectorio.setVisible(false);
				botonBorrarDirectorio.setVisible(false);
				padre.muestraBotonAgregarFichero(false);
				textFieldRutaDirectorio.clear();
			}
			padre.cargaGridFicheros(directorioSeleccionado);
		});

		// TEXTFIELD RUTA DIRECTORIO
		textFieldRutaDirectorio = new TextField();
		textFieldRutaDirectorio.setInputPrompt("Ruta del directorio");

		// BUTTON AGREGAR DIRECTORIO
		botonAgregarDirectorio = new Button("Añadir Directorio");
		botonAgregarDirectorio.setIcon(FontAwesome.PLUS);
		botonAgregarDirectorio.setVisible(true);
		botonAgregarDirectorio.setEnabled(true);
		botonAgregarDirectorio.addClickListener(e -> {
			Directorio nuevoDirectorio = new Directorio();
			nuevoDirectorio.setRuta(textFieldRutaDirectorio.getValue());
			servicioGestorFicheros.agregaDirectorio(nuevoDirectorio);
			textFieldRutaDirectorio.clear();
			cargaGridDirectorios();
//			botonAgregarDirectorio.setVisible(true);
//			botonRenombrarDirectorio.setVisible(false);
//			botonBorrarDirectorio.setVisible(false);

		});

		// BUTTON BORRAR DIRECTORIO
		botonBorrarDirectorio = new Button("Borrar");
		botonBorrarDirectorio.setIcon(FontAwesome.ERASER);
		botonBorrarDirectorio.setVisible(false);
		botonBorrarDirectorio.addClickListener(e -> {
			servicioGestorFicheros.eliminaDirectorio(directorioSeleccionado.getId());
			textFieldRutaDirectorio.clear();
			cargaGridDirectorios();
		});

		// BUTTON RENOMBRAR DIRECTORIO
		botonRenombrarDirectorio = new Button("Renombrar");
		botonRenombrarDirectorio.setIcon(FontAwesome.REFRESH);
		botonRenombrarDirectorio.setVisible(false);
		botonRenombrarDirectorio.setEnabled(true);
		botonRenombrarDirectorio.addClickListener(e -> {
			try {
				if (textFieldRutaDirectorio.getValue() != null) {
					directorioSeleccionado.setRuta(textFieldRutaDirectorio.getValue());
					servicioGestorFicheros.modificaDirectorio(directorioSeleccionado.getId(), directorioSeleccionado);
					textFieldRutaDirectorio.clear();
					cargaGridDirectorios();
					Notification.show("Directorio modificado.");
				} else {
					// TODO - Validar campo
				}
			} catch (Exception ex) {
				// TODO - Gestionar errores
			}
		});

		// LAYOUT BOTONES DIRECTORIOS
		HorizontalLayout layoutBotonesDirectorios = new HorizontalLayout();
		layoutBotonesDirectorios.setMargin(false);
		layoutBotonesDirectorios.setSpacing(true);
		layoutBotonesDirectorios.addComponents(textFieldRutaDirectorio, botonAgregarDirectorio,
				botonRenombrarDirectorio, botonBorrarDirectorio);
		
		this.setSizeFull();
		this.setMargin(false);
		this.setSpacing(true);
		this.addComponents(gridDirectorios, layoutBotonesDirectorios);
	}

	public void cargaGridDirectorios() {	
		Collection<Directorio> directorios = servicioGestorFicheros.listaDirectorios();
		gridDirectorios.setContainerDataSource(new BeanItemContainer<>(Directorio.class, directorios));
	}

}
