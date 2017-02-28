package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
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
	private TextField textFieldRutaDirectorio;

	public LayoutDirectorios(VistaDocumentos padre, ServicioGestorFicheros servicioGestorFicheros) {
		this.servicioGestorFicheros = servicioGestorFicheros;

		Label titulo = new Label("Directorios:");
		titulo.setContentMode(ContentMode.HTML);
		
		// GRID DIRECTORIOS
		gridDirectorios = new Grid();
		gridDirectorios.setColumns("ruta");
		gridDirectorios.setSizeFull();
		gridDirectorios.setSelectionMode(SelectionMode.SINGLE);
		gridDirectorios.addSelectionListener(e -> {
			directorioSeleccionado = null;
			if (!e.getSelected().isEmpty()) {
				directorioSeleccionado = (Directorio) e.getSelected().iterator().next();
				textFieldRutaDirectorio.setValue(directorioSeleccionado.getRuta());
				botonAgregarDirectorio.setVisible(false);
				botonRenombrarDirectorio.setVisible(true);
				botonBorrarDirectorio.setVisible(true);
				padre.muestraBotonAgregarFichero(true);
				
				boolean hoja = servicioGestorFicheros.esHoja(directorioSeleccionado.getId());
				textFieldRutaDirectorio.setEnabled(hoja);
				botonRenombrarDirectorio.setEnabled(hoja);
			} else {
				botonAgregarDirectorio.setVisible(true);
				botonRenombrarDirectorio.setVisible(false);
				botonBorrarDirectorio.setVisible(false);
				padre.muestraBotonAgregarFichero(false);
				textFieldRutaDirectorio.clear();
				textFieldRutaDirectorio.setEnabled(true);
				botonRenombrarDirectorio.setEnabled(true);
			}
			padre.cargaGridFicheros(directorioSeleccionado);
		});

		// LABEL RUTA DIRECTORIO
		Label labelRutaDirectorio = new Label("<strong>Dir.:</strong>");
		labelRutaDirectorio.setContentMode(ContentMode.HTML);
		labelRutaDirectorio.setVisible(false);

		// TEXTFIELD RUTA DIRECTORIO
		textFieldRutaDirectorio = new TextField();
		textFieldRutaDirectorio.setCaption("Nombre:");
		textFieldRutaDirectorio.setInputPrompt("Nombre del directorio");

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
		});

		// BUTTON BORRAR DIRECTORIO
		botonBorrarDirectorio = new Button("Borrar");
		botonBorrarDirectorio.setIcon(FontAwesome.ERASER);
		botonBorrarDirectorio.setVisible(false);
		botonBorrarDirectorio.addClickListener(
				e -> this.getUI().getUI().addWindow(creaVentanaConfirmacionBorrado(directorioSeleccionado.getRuta())));

		// BUTTON RENOMBRAR DIRECTORIO
		botonRenombrarDirectorio = new Button("Renombrar");
		botonRenombrarDirectorio.setIcon(FontAwesome.REFRESH);
		botonRenombrarDirectorio.setVisible(false);
		botonRenombrarDirectorio.setEnabled(true);
		botonRenombrarDirectorio.addClickListener(e -> {
			if (textFieldRutaDirectorio.getValue() != "") {
				directorioSeleccionado.setRuta(textFieldRutaDirectorio.getValue());
				servicioGestorFicheros.modificaDirectorio(directorioSeleccionado.getId(), directorioSeleccionado);
				textFieldRutaDirectorio.clear();
				cargaGridDirectorios();
				Notification.show("Directorio modificado.");
			}
		});

		// LAYOUT BOTONES DIRECTORIOS
		VerticalLayout layoutBotonesDirectoriosA = new VerticalLayout();
		layoutBotonesDirectoriosA.setMargin(false);
		layoutBotonesDirectoriosA.setSpacing(true);
		layoutBotonesDirectoriosA.addComponents(labelRutaDirectorio, textFieldRutaDirectorio, botonAgregarDirectorio,
				botonRenombrarDirectorio, botonBorrarDirectorio);

		this.setSizeFull();
		this.setMargin(true);
		this.setSpacing(true);
		this.addComponents(titulo, gridDirectorios, layoutBotonesDirectoriosA);
	}

	public void cargaGridDirectorios() {
		Collection<Directorio> directorios = servicioGestorFicheros.listaDirectorios();
		gridDirectorios.setContainerDataSource(new BeanItemContainer<>(Directorio.class, directorios));
	}

	private Window creaVentanaConfirmacionBorrado(String directorioRuta) {
		Window resultado = new Window();
		resultado.setWidth(350.0F, Unit.PIXELS);
		resultado.setModal(true);
		resultado.setClosable(false);
		resultado.setResizable(false);
		resultado.setDraggable(false);

		Label label = new Label("¿Está seguro de que desea borrar el directorio: <strong>\"" + directorioRuta
				+ "\"</strong>, junto con todos sus ficheros?");
		label.setContentMode(ContentMode.HTML);

		Button botonAceptar = new Button("Aceptar");
		botonAceptar.addClickListener(e -> {
			servicioGestorFicheros.eliminaDirectorio(directorioSeleccionado.getId());
			textFieldRutaDirectorio.clear();
			cargaGridDirectorios();
			resultado.close();
		});

		Button botonCancelar = new Button("Cancelar");
		botonCancelar.addClickListener(e -> resultado.close());

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(true);
		layoutBotones.setSpacing(true);
		layoutBotones.setWidth(100.0F, Unit.PERCENTAGE);
		layoutBotones.addComponents(botonAceptar, botonCancelar);

		final FormLayout content = new FormLayout();
		content.setMargin(true);
		content.addComponents(label, layoutBotones);
		resultado.setContent(content);
		resultado.center();
		return resultado;
	}

}
