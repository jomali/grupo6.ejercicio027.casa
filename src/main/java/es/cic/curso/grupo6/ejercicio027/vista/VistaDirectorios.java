
package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorDirectorios;

public class VistaDirectorios extends VerticalLayout implements View {
	private static final long serialVersionUID = 6362449485036174011L;

	// Servicios con lógica de negocio y acceso a BB.DD.

	private ServicioGestorDirectorios servicioGestorDirectorios;

	// Componentes gráficos:
	
	@PropertyId("ruta")
	protected TextField textFieldRuta;

	private Grid gridDirectorios;
	private Button botonAgregar, botonBorrar, botonActualizar;
	private Directorio eliminaDirectorio;
	private Directorio actualizaDirectorio;

	@SuppressWarnings("serial")
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
		gridDirectorios.setCaption("Lista Directorios:");
		gridDirectorios.addSelectionListener(new SelectionListener() {
			@Override
			public void select(SelectionEvent event) {
				Directorio directorio = (Directorio) gridDirectorios.getSelectedRow();
				if (directorio != null) {
					eliminaDirectorio = directorio;
					botonAgregar.setVisible(false);
					botonActualizar.setVisible(true);
					botonBorrar.setVisible(true);
					muestraDirectorio(directorio);
					
				} else {
					botonBorrar.setVisible(false);
				}
				
			}
		});

		// BOTONES
		textFieldRuta = new TextField();
		textFieldRuta.setInputPrompt("Ruta de la Carpeta");
		
		
		
		botonAgregar = new Button("Añadir Directorio");
		botonAgregar.setIcon(FontAwesome.PLUS_CIRCLE);
		botonAgregar.setVisible(true);
		botonAgregar.setEnabled(true);
		botonAgregar.addClickListener(agregar -> {
			botonAgregar.setVisible(false);
			botonActualizar.setVisible(false);
			
		});

		botonBorrar = new Button("Borrar");
		botonBorrar.setIcon(FontAwesome.ERASER);
		botonBorrar.setVisible(false);
		botonBorrar.addClickListener(borrar -> {
			borraDirectorio(eliminaDirectorio);
			cargaGridDirectorios();
		});
		
		botonActualizar = new Button("Actualizar datos");
		botonActualizar.setIcon(FontAwesome.REFRESH);
		botonActualizar.setVisible(false);
		botonActualizar.setEnabled(true);
		botonActualizar.addClickListener(aactualizar -> {
			actualizarDirectorio(actualizaDirectorio);
			cargaGridDirectorios();
		});

		cargaGridDirectorios();

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(false);
		layoutBotones.setSpacing(true);
		layoutBotones.addComponents(textFieldRuta, botonAgregar, botonActualizar, botonBorrar);

		// LAYOUT PRINCIPAL

		VerticalLayout layoutPrincipal = new VerticalLayout();
		layoutPrincipal.setMargin(true);
		layoutPrincipal.setSpacing(true);
		layoutPrincipal.addComponents(gridDirectorios, layoutBotones);

		addComponents(menu, layoutPrincipal);
	}

	public void borraDirectorio(Directorio directorio) {
		servicioGestorDirectorios.eliminaDirectorio(directorio.getId());
	}
	
	public void muestraDirectorio(Directorio directorio) {
		textFieldRuta.setValue(directorio.getRuta());
	}
	
	public void actualizarDirectorio(Directorio directorio) {
		servicioGestorDirectorios.modificaDirectorio(directorio.getId(), actualizaDirectorio);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		cargaGridDirectorios();
		System.out.println(servicioGestorDirectorios.test());
	}

	public void cargaGridDirectorios() {
		Collection<Directorio> directorios = servicioGestorDirectorios.listaDirectorios();
		gridDirectorios.setContainerDataSource(new BeanItemContainer<>(Directorio.class, directorios));
	}

}
