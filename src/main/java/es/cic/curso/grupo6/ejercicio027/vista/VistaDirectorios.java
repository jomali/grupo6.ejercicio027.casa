package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
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
		gridDirectorios.addSelectionListener(e -> {
			if (!e.getSelected().isEmpty()) {
				// Directorio directorioSeleccionado = (Directorio)
				// e.getSelected().iterator().next();
				botonAgregar.setEnabled(true);
				botonBorrar.setEnabled(true);
				botonActualizar.setEnabled(true);
			} else {
				botonAgregar.setEnabled(true);
				botonBorrar.setEnabled(true);
				botonActualizar.setEnabled(true);
			}
		});

		// BOTONES

		botonAgregar = new Button("Añadir Directorio");
		botonAgregar.setIcon(FontAwesome.PLUS_CIRCLE);
		botonAgregar.setVisible(true);
		botonAgregar.setEnabled(true);
		botonAgregar.addClickListener(clickEvent -> {
			botonAgregar.setVisible(false);
			gridDirectorios.setVisible(false);
			formulario.setVisible(true);
			botonBorrar.setVisible(true);
			botonActualizar.setVisible(false);
			
		});

		botonBorrar = new Button("Borrar");
		botonBorrar.setIcon(FontAwesome.ERASER);
		botonBorrar.setVisible(true);
		botonBorrar.setEnabled(true);
		botonBorrar.addClickListener(e -> {
				Notification.show("BORRADO: DIrectorio ID="+directorio.getId());
				borraDirectorio(directorio.getId());
				cargaGridDirectorios();
				setDirectorio(directorio);
				botonBorrar.setVisible(false);
		});
		
		botonActualizar = new Button("Recarga datos");
		botonActualizar.setIcon(FontAwesome.REFRESH);
		botonActualizar.setVisible(true);
		botonActualizar.setEnabled(true);
//		botonActualizar.addClickListener(e -> {
//			if(compruebaDatos()){
//				Directorio directorioModificado = new Directorio(e
//				directorioModificado.setId(directorio.getId());
//				servicioGestorDirectorios.
//				cargaGridDirectorios();
//				setDirectorio(directorioModificado);
//			}else{
//				muestraError.show(Page.getCurrent());
//			}
//		});
		cargaGridDirectorios();

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

	public Directorio borraDirectorio(Long id) {
		return  servicioGestorDirectorios.eliminaDirectorio(id);
		}

	public void setDirectorio(Directorio directorio)
	{  
		this.directorio=directorio;
		
		if(directorio!=null)
		{
		    BeanFieldGroup.bindFieldsUnbuffered(directorio, this);			
		} else {
			BeanFieldGroup.bindFieldsUnbuffered(new Directorio(), this);
		}
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
