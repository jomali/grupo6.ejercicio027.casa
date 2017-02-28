package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;
import java.util.List;

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

	/** Referencia a la vista padre de la que cuelga el layout. */
	private VistaDocumentos padre;

	/** Lógica de negocio con acceso a BB.DD. */
	private ServicioGestorFicheros servicioGestorFicheros;

	private Directorio nuevoDirectorio, directorioSeleccionado, eliminaDirectorio, actualizaDirectorio;
	
	private Grid gridDirectorios;
	
	private Button botonAgregarDirectorio, botonBorrarDirectorio, botonRenombrarDirectorio;

	@PropertyId("ruta")
	protected TextField textFieldRutaDirectorio;

	public LayoutDirectorios(VistaDocumentos padre, ServicioGestorFicheros servicioGestorFicheros) {
		this.padre = padre;
		this.servicioGestorFicheros = servicioGestorFicheros;
		
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
				padre.activaBotonAgregarFichero(true);
				muestraDirectorio(directorio);
			} else {
				botonBorrarDirectorio.setVisible(false);
				botonRenombrarDirectorio.setVisible(false);
				botonAgregarDirectorio.setVisible(true);
				padre.activaBotonAgregarFichero(false);
				reiniciaTextField();
			}
			padre.cargaGridFicheros(directorio);
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
		
		this.setSizeFull();
		this.setMargin(false);
		this.setSpacing(true);
		this.addComponents(gridDirectorios, layoutBotonesDirectorios);
	}
	
	public void modificaBotones() {
		botonAgregarDirectorio.setVisible(true);
		botonRenombrarDirectorio.setVisible(false);
		botonBorrarDirectorio.setVisible(false);
	}

	public void muestraDirectorio(Directorio directorio) {
		textFieldRutaDirectorio.setValue(directorio.getRuta());
	}

	public void reiniciaTextField() {
		textFieldRutaDirectorio.clear();
	}

	public void agregarDirectorio(Directorio directorio) {
		Directorio nuevoDirectorio = new Directorio();
		nuevoDirectorio.setRuta(textFieldRutaDirectorio.getValue());
		servicioGestorFicheros.agregaDirectorio(nuevoDirectorio);
	}

	private List<Directorio> cargarLista() {
		return servicioGestorFicheros.listaDirectorios();
	}

	public void cargaGridDirectorios() {
		Collection<Directorio> directorios = servicioGestorFicheros.listaDirectorios();
		gridDirectorios.setContainerDataSource(new BeanItemContainer<>(Directorio.class, directorios));
	}

	public void actualizarDirectorio(long directorioId, Directorio directorio) {
		servicioGestorFicheros.modificaDirectorio(directorioId, directorio);
	}

	public void borraDirectorio(Directorio directorio) {
		servicioGestorFicheros.eliminaDirectorio(directorio.getId());
	}


}