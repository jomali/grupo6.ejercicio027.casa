package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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

	private ServicioGestorDirectorios servicioGestorDirectorios;

	// Componentes gráficos:

	private Grid gridDirectorios;

	private FormularioDirectorios formulario = new FormularioDirectorios(null);
	Button carga = new Button("Carga");
	Button crea = new Button("Crea");
	Button borra = new Button("Borra");
	Button actualiza = new Button("Actualiza");

	@SuppressWarnings("serial")
	public VistaDirectorios(MenuNavegacion menuNavegacion) {
		servicioGestorDirectorios = ContextLoader.getCurrentWebApplicationContext()
				.getBean(ServicioGestorDirectorios.class);

		MenuBar menu = menuNavegacion.creaMenu(MyUI.VISTA_DIRECTORIOS);

		VerticalLayout layoutPrincipal = new VerticalLayout();
		layoutPrincipal.setMargin(true);
		layoutPrincipal.setSpacing(true);

		gridDirectorios = new Grid();
		gridDirectorios.setColumns("id", "ruta");
		gridDirectorios.setSizeFull();
		gridDirectorios.setSelectionMode(SelectionMode.SINGLE);

		gridDirectorios.addSelectionListener(new SelectionListener() {
			@Override
			public void select(SelectionEvent event) {
//				Directorio carpetaSeleccionada = (Directorio) gridDirectorios.getSelectedRow();
				crea.setVisible(true);
				borra.setVisible(true);
				actualiza.setVisible(true);
			}

		});

		layoutPrincipal.addComponent(gridDirectorios);

		HorizontalLayout layoutHorizontal = new HorizontalLayout();
		layoutHorizontal.setSpacing(true);

		crea.setVisible(false);
		borra.setVisible(false);
		actualiza.setVisible(false);
		formulario.setVisible(false);

		carga.addClickListener(clickEvent -> {
			carga.setVisible(false);
			crea.setVisible(true);
			borra.setVisible(true);
			actualiza.setVisible(true);
			formulario = new FormularioDirectorios(null);
			formulario.setVisible(true);
		});
		;
		layoutHorizontal.addComponents(carga, crea, borra, actualiza);
		layoutPrincipal.addComponents(layoutHorizontal, formulario);

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
