
package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;
import java.util.Set;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
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
	private Notification muestraError = new Notification("ERROR: Algún dato está mal introducido o no ha sido introducido");
	private Directorio directorio;
	private Directorio eliminaDirectorio;

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
				Set<Object> selected = event.getSelected();
				Directorio directorio =  (Directorio) gridDirectorios.getSelectedRow();
				if(directorio!=null){
					eliminaDirectorio = directorio;
					botonBorrar.setVisible(true);
				}else{
					botonBorrar.setVisible(false);
				}
			}
		});
		// BOTONES

		botonAgregar = new Button("Añadir Directorio");
		botonAgregar.setIcon(FontAwesome.PLUS_CIRCLE);
		botonAgregar.setVisible(true);
		botonAgregar.setEnabled(true);
		botonAgregar.addClickListener(d -> {
			gridDirectorios.setVisible(false);
			botonAgregar.setVisible(false);
			botonActualizar.setVisible(false);
			formulario.setEnabled(true);
			formulario.setVisible(true);
		});
		
		botonBorrar = new Button("Borrar");
		botonBorrar.setIcon(FontAwesome.ERASER);
		botonBorrar.setVisible(false);
		botonBorrar.addClickListener(e -> {
			borraDirectorio(eliminaDirectorio);
			cargaGridDirectorios();
		});
		
		botonActualizar = new Button("Recarga datos");
		botonActualizar.setIcon(FontAwesome.REFRESH);
		botonActualizar.setVisible(true);
		botonActualizar.setEnabled(true);

		cargaGridDirectorios();

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(false);
		layoutBotones.setSpacing(true);
		layoutBotones.addComponents(botonAgregar, botonBorrar, botonActualizar);

		// FORMULARIO

		formulario = new FormularioDirectorios(this);
		formulario.setVisible(false);

		// LAYOUT PRINCIPAL

		VerticalLayout layoutPrincipal = new VerticalLayout();
		layoutPrincipal.setMargin(true);
		layoutPrincipal.setSpacing(true);
		layoutPrincipal.addComponents(gridDirectorios, layoutBotones, formulario);

		addComponents(menu, layoutPrincipal);
	}

	public void borraDirectorio(Directorio directorio){
		servicioGestorDirectorios.eliminaDirectorio(directorio.getId());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Vista Directorios");
		cargaGridDirectorios();
	}

	public void cargaGridDirectorios() {
		Collection<Directorio> directorios = servicioGestorDirectorios.listaDirectorios();
		gridDirectorios.setContainerDataSource(new BeanItemContainer<>(Directorio.class, directorios));
	}

}
