package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class LayoutFicheros extends VerticalLayout {
	private static final long serialVersionUID = -7013768321773232310L;

	/** Referencia a la vista padre de la que cuelga el layout. */
	private VistaDocumentos padre;

	/** Lógica de negocio con acceso a BB.DD. */
	private ServicioGestorFicheros servicioGestorFicheros;
	
	/** Referencia al fichero seleccionado en el grid. */
	private Fichero ficheroSeleccionado;
	
	/** Grid de ficheros. */
	private Grid gridFicheros;
	
	/** Acciones sobre los ficheros. */
	private Button botonAgregarFichero, botonBorrarFichero, botonActualizarFichero;
	
	/** Formulario para editar los campos de un fichero. */
	private FormularioFicheros formulario;
	
	private Directorio directorioActual;

	public LayoutFicheros(VistaDocumentos padre, ServicioGestorFicheros servicioGestorFicheros) {
		this.padre = padre;
		this.servicioGestorFicheros = servicioGestorFicheros;

		directorioActual = null;
		
		Label titulo = new Label("Ficheros:");
		titulo.setContentMode(ContentMode.HTML);
		
		// GRID FICHEROS
		gridFicheros = new Grid();
		gridFicheros.setColumns("nombre", "descripcion", "version");
		gridFicheros.setSizeFull();
		gridFicheros.setSelectionMode(SelectionMode.SINGLE);
		gridFicheros.addSelectionListener(e -> {
			ficheroSeleccionado = null;
			if (!e.getSelected().isEmpty()) {
					ficheroSeleccionado = (Fichero) e.getSelected().iterator().next();
					botonAgregarFichero.setVisible(false);
					botonBorrarFichero.setVisible(true);
					botonActualizarFichero.setVisible(true);
				} else {
					botonBorrarFichero.setVisible(false);
					botonActualizarFichero.setVisible(false);
					botonAgregarFichero.setVisible(true);
				}
		});



		// FORMULARIO FICHEROS
		formulario = new FormularioFicheros(padre, null, null);
		formulario.setVisible(false);
		
		// BUTTON AGREGAR FICHERO
		botonAgregarFichero = new Button("Añadir fichero");
		botonAgregarFichero.setVisible(false);
		botonAgregarFichero.setEnabled(true);
		botonAgregarFichero.addClickListener(e -> {
			formulario.setVisible(true);
			botonAgregarFichero.setVisible(false);
			botonBorrarFichero.setVisible(false);
			botonActualizarFichero.setVisible(false);
		});

		// BUTTON BORRAR FICHERO
		botonBorrarFichero = new Button("Borrar");
		botonBorrarFichero.setIcon(FontAwesome.ERASER);
		botonBorrarFichero.setEnabled(true);
		botonBorrarFichero.setVisible(false);
		botonBorrarFichero.addClickListener(
				e -> this.getUI().getUI().addWindow(creaVentanaConfirmacionBorradoFicheros(ficheroSeleccionado.getNombre())));

		// BUTTON ACTUALIZAR FICHERO
		botonActualizarFichero = new Button("Actualizar fichero");
		botonActualizarFichero.setIcon(FontAwesome.REFRESH);
		botonActualizarFichero.setVisible(false);
		botonActualizarFichero.setEnabled(true);
		botonActualizarFichero.addClickListener(e -> {
//			formulario.setVisible(true);
			botonAgregarFichero.setVisible(false);
			botonBorrarFichero.setVisible(false);
			botonActualizarFichero.setVisible(false);
			
		});
		
		HorizontalLayout layoutBotonesFicheros = new HorizontalLayout();
		layoutBotonesFicheros.setMargin(false);
		layoutBotonesFicheros.setSpacing(true);
		layoutBotonesFicheros.addComponents(botonAgregarFichero, botonActualizarFichero, botonBorrarFichero, formulario);

		this.setSizeFull();
		this.setMargin(new MarginInfo(false, true, false, true));
		this.setSpacing(true);
		this.addComponents(titulo, gridFicheros, layoutBotonesFicheros);
	}
	
	public Directorio getDirectorioSeleccionado(Directorio directorioSeleccionado){
		directorioSeleccionado = ficheroSeleccionado.getDirectorio();
		return directorioSeleccionado;
	}
	public Fichero getFicheroSeleccionado() {
		return ficheroSeleccionado;
	}

	public void setFicheroSeleccionado(Fichero ficheroSeleccionado) {
		this.ficheroSeleccionado = ficheroSeleccionado;
	}

	public void cargaGridFicheros(Directorio directorio) {
		Collection<Fichero> ficheros = (directorio == null) ? new ArrayList<>()
				: servicioGestorFicheros.listaFicherosPorDirectorio(directorio.getId());
		gridFicheros.setContainerDataSource(new BeanItemContainer<>(Fichero.class, ficheros));
	}
	
	public void muestraBotonAgregarFichero(boolean visible) {
		botonAgregarFichero.setVisible(visible);
	}
	
	public void activaBotonAgregarFichero(boolean activado) {
		botonAgregarFichero.setEnabled(activado);
	}
	
	private Window creaVentanaConfirmacionBorradoFicheros(String nombre) {
		Window resultado = new Window();
		resultado.setWidth(350.0F, Unit.PIXELS);
		resultado.setModal(true);
		resultado.setClosable(false);
		resultado.setResizable(false);
		resultado.setDraggable(false);

		Label label = new Label("¿Está seguro de que desea borrar el archivo: <strong>\"" + nombre
				+ "?");
		label.setContentMode(ContentMode.HTML);

		Button botonAceptar = new Button("Aceptar");
		botonAceptar.addClickListener(e -> {
			servicioGestorFicheros.eliminaFichero(ficheroSeleccionado.getId());
			cargaGridFicheros(ficheroSeleccionado.getDirectorio());
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
