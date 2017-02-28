package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
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

	public LayoutFicheros(VistaDocumentos padre, ServicioGestorFicheros servicioGestorFicheros) {
		this.padre = padre;
		this.servicioGestorFicheros = servicioGestorFicheros;

		// GRID FICHEROS
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
					ficheroSeleccionado = fchero;
					botonAgregarFichero.setVisible(false);
					botonBorrarFichero.setVisible(true);
					botonActualizarFichero.setVisible(true);
				} else {
					botonBorrarFichero.setVisible(false);
					botonActualizarFichero.setVisible(false);
				}
			}
		});

		// BUTTON AGREGAR FICHERO
		botonAgregarFichero = new Button("Añadir fichero");
		botonAgregarFichero.setVisible(false);
		botonAgregarFichero.setEnabled(true);
		botonAgregarFichero.addClickListener(e -> {
//			formulario.setVisible(true);
//			padre.modificaBotonesDirectorios();
//			cargaGridDirectorios();
//			Notification.show("Funcionalidad no implementada.");
			this.getUI().getUI().addWindow(new VentanaConfirmacion("Confirmación"));
		});

		// BUTTON BORRAR FICHERO
		botonBorrarFichero = new Button("Borrar");
		botonBorrarFichero.setIcon(FontAwesome.ERASER);
		botonBorrarFichero.setEnabled(true);
		botonBorrarFichero.setVisible(false);
		botonBorrarFichero.addClickListener(e -> {
			servicioGestorFicheros.eliminaFichero(ficheroSeleccionado.getId());
			ficheroSeleccionado = null;
			cargaGridFicheros(null);
		});

		// BUTTON ACTUALIZAR FICHERO
		botonActualizarFichero = new Button("Actualizar fichero");
		botonActualizarFichero.setIcon(FontAwesome.REFRESH);
		botonActualizarFichero.setVisible(false);
		botonActualizarFichero.setEnabled(true);

		// FORMULARIO FICHEROS
		formulario = new FormularioFicheros(null);
		formulario.setVisible(false);
		
		HorizontalLayout layoutBotonesFicheros = new HorizontalLayout();
		layoutBotonesFicheros.setMargin(false);
		layoutBotonesFicheros.setSpacing(true);
		layoutBotonesFicheros.addComponents(botonAgregarFichero, botonActualizarFichero, botonBorrarFichero);
		
		this.setMargin(true);
		this.setSpacing(false);
		this.setSizeFull();
		this.addComponents(gridFicheros, layoutBotonesFicheros, formulario);
	}

	public void cargaGridFicheros(Directorio directorio) {
		Collection<Fichero> ficheros = (directorio == null) ? new ArrayList<>()
				: servicioGestorFicheros.listaFicherosPorDirectorio(directorio.getId());
		gridFicheros.setContainerDataSource(new BeanItemContainer<>(Fichero.class, ficheros));
	}
	
}
