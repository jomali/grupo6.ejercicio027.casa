package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorDirectorios;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class VistaFicheros extends VerticalLayout implements View {
	private static final long serialVersionUID = 6825028526184768126L;

	// Servicios con lógica de negocio y acceso a BB.DD.

	private ServicioGestorFicheros servicioGestorFicheros;
	private ServicioGestorDirectorios servicioGestorDirectorios;

	// Componentes gráficos:

	private Grid gridFicheros;
	private ComboBox comboBoxDirectorios;
	private Button botonAgregar, botonBorrar, botonActualizar;

	private static final String SELECCIONA = "Selecciona";

	public VistaFicheros(MenuNavegacion menuNavegacion) {
		servicioGestorFicheros = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorFicheros.class);
		servicioGestorDirectorios = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioGestorDirectorios.class);

		// MENÚ de NAVEGACIÓN

		MenuBar menu = menuNavegacion.creaMenu(MyUI.VISTA_FICHEROS);

		// GRID de FICHEROS

		gridFicheros = new Grid();
		gridFicheros.setColumns("id", "directorio", "nombre", "descripcion", "version");
		gridFicheros.setSizeFull();
		gridFicheros.setSelectionMode(SelectionMode.SINGLE);
		gridFicheros.addSelectionListener(e -> {
			if (!e.getSelected().isEmpty()) {
			} else {
			}
		});

		// COMBOBOX y BOTONES

		comboBoxDirectorios = new ComboBox();
		comboBoxDirectorios.setInputPrompt(SELECCIONA);
		Collection<Directorio> directorios = servicioGestorDirectorios.listaDirectorios();
		for (int i = 1; i <= directorios.size(); i++) {
			comboBoxDirectorios.addItem(i);
			comboBoxDirectorios.setItemCaption(i, "Directorio " + i);
		}
		comboBoxDirectorios.setNullSelectionAllowed(false);
		comboBoxDirectorios.setImmediate(true);

		botonAgregar = new Button("Añade fichero");
		botonAgregar.setVisible(true);
		botonAgregar.setEnabled(false);

		botonBorrar = new Button("Borra");
		botonBorrar.setVisible(true);
		botonBorrar.setEnabled(false);

		botonActualizar = new Button("Actualizar");
		botonActualizar.setVisible(true);
		botonActualizar.setEnabled(false);

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(false);
		layoutBotones.setSpacing(true);
		layoutBotones.addComponents(comboBoxDirectorios, botonAgregar, botonBorrar, botonActualizar);

		// LAYOUT PRINCIPAL

		VerticalLayout layoutPrincipal = new VerticalLayout();
		layoutPrincipal.setMargin(true);
		layoutPrincipal.setSpacing(true);
		layoutPrincipal.addComponents(gridFicheros, layoutBotones);

		addComponents(menu, layoutPrincipal);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		cargaGridFicheros();
	}

	public void cargaGridFicheros() {
		Collection<Fichero> ficheros = servicioGestorFicheros.listaFicheros();
		gridFicheros.setContainerDataSource(new BeanItemContainer<>(Fichero.class, ficheros));
	}

}
