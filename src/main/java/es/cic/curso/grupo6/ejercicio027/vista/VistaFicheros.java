package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;
import java.util.List;

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

	// Componentes gráficos:
	
	private Grid gridFicheros;

	private ServicioGestorFicheros servicioGestorFicheros;
	private ServicioGestorDirectorios servicioGestorDirectorios;
	private ComboBox lista;
	Button carga = new Button("Cargar");
	Button crea = new Button("Crear");
	Button borra = new Button("Borrar");
	Button actualiza = new Button("Actualizar");
	private List<Directorio> listaDirectorios;
	private static final String SELECCIONA = "Selecciona";

	public VistaFicheros(MenuNavegacion menuNavegacion) {
		servicioGestorFicheros = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorFicheros.class);
		servicioGestorDirectorios = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioGestorDirectorios.class);

		MenuBar menu = menuNavegacion.creaMenu(MyUI.VISTA_FICHEROS);

		VerticalLayout layoutPrincipal = new VerticalLayout();
		layoutPrincipal.setMargin(true);
		layoutPrincipal.setSpacing(true);
		
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
		layoutPrincipal.addComponent(gridFicheros);

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(false);
		layoutBotones.setSpacing(true);

		carga.setVisible(false);
		crea.setVisible(false);
		borra.setVisible(false);
		actualiza.setVisible(false);

		lista = new ComboBox();
		lista.setInputPrompt(SELECCIONA);
		listaDirectorios = servicioGestorDirectorios.listaDirectorios();
		for (int i = 1; i <= listaDirectorios.size(); i++) {
			lista.addItem(i);
			lista.setItemCaption(i, "Directorio " + i);
		}

		lista.setNullSelectionAllowed(false);
		lista.setImmediate(true);
		carga.setVisible(true);
		carga.setEnabled(false);
		crea.setVisible(true);
		crea.setEnabled(false);
		borra.setVisible(true);
		borra.setEnabled(false);
		actualiza.setVisible(true);
		actualiza.setEnabled(false);
		layoutBotones.addComponents(lista, carga, crea, borra, actualiza);
		layoutPrincipal.addComponent(layoutBotones);

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
