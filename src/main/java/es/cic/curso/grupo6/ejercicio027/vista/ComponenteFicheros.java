package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;

import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

public class ComponenteFicheros extends VerticalLayout {
	private static final long serialVersionUID = -7013768321773232310L;
	
	private Grid gridFicheros;
	private Button botonAgregarFichero, botonBorrarFichero, botonActualizarFichero;
	private FormularioFicheros formulario;


	public ComponenteFicheros() {

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

		// BUTTON AGREGAR FICHERO
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

		// BUTTON BORRAR FICHERO
		botonBorrarFichero = new Button("Borrar");
		botonBorrarFichero.setIcon(FontAwesome.ERASER);
		botonBorrarFichero.setEnabled(true);
		botonBorrarFichero.setVisible(false);
		botonBorrarFichero.addClickListener(e -> {
			borraFichero(eliminaFichero);
			cargaGridFicheros(directorioSeleccionado);
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
	
}
