package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorDirectorios;

public class VistaDirectorios extends VerticalLayout implements View {
	private static final long serialVersionUID = 6362449485036174011L;

	// Servicios con lógica de negocio y acceso a BB.DD.

	private ServicioGestorDirectorios servicioGestorDirectorios;

	// Componentes gráficos:

	private Grid gridDirectorios;
	private FormularioDirectorios formulario;
	private Button botonAgregar, botonBorrar, botonActualizar;

	public VistaDirectorios(MenuNavegacion menuNavegacion) {
		servicioGestorDirectorios = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioGestorDirectorios.class);

		// MENÚ de NAVEGACIÓN

		MenuBar menu = menuNavegacion.creaMenu(MyUI.VISTA_DIRECTORIOS);

		// GRID de DIRECTORIOS

		gridDirectorios = new Grid();
		gridDirectorios.setColumns("id", "ruta");
		gridDirectorios.setSizeFull();
		gridDirectorios.setSelectionMode(SelectionMode.SINGLE);
		gridDirectorios.addSelectionListener(e -> {
			if (!e.getSelected().isEmpty()) {
				// Directorio directorioSeleccionado = (Directorio)
				// e.getSelected().iterator().next();
				botonAgregar.setEnabled(true);
				botonBorrar.setEnabled(true);
				botonActualizar.setEnabled(true);
			} else {
				botonAgregar.setEnabled(true);
				botonBorrar.setEnabled(false);
				botonActualizar.setEnabled(true);
			}
		});

		// BOTONES

		botonAgregar = new Button("Añadir Directorio");
		botonAgregar.setIcon(FontAwesome.PLUS_CIRCLE);
		botonAgregar.setVisible(true);
		botonAgregar.setEnabled(true);

		botonBorrar = new Button("Borrar");
		botonBorrar.setIcon(FontAwesome.MINUS_CIRCLE);
		botonBorrar.setVisible(true);
		botonBorrar.setEnabled(false);

		botonActualizar = new Button("Recarga datos");
		botonActualizar.setIcon(FontAwesome.REFRESH);
		botonActualizar.setVisible(true);
		botonActualizar.setEnabled(true);
		botonActualizar.addClickListener(e -> cargaGridDirectorios());

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(false);
		layoutBotones.setSpacing(true);
		layoutBotones.addComponents(botonAgregar, botonBorrar, botonActualizar);

		// FORMULARIO

		formulario = new FormularioDirectorios(null);
		formulario.setVisible(false);

		// LAYOUT PRINCIPAL

		VerticalLayout layoutPrincipal = new VerticalLayout();
		layoutPrincipal.setMargin(true);
		layoutPrincipal.setSpacing(true);
		layoutPrincipal.addComponents(gridDirectorios, layoutBotones, formulario);

		addComponents(menu, layoutPrincipal);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		cargaGridDirectorios();
	}

	public void cargaGridDirectorios() {
		Collection<Directorio> directorios = servicioGestorDirectorios.listaDirectorios();
		gridDirectorios.setContainerDataSource(new BeanItemContainer<>(Directorio.class, directorios));
	}

}
