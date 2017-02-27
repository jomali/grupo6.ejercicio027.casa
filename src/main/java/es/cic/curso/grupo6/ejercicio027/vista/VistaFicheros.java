package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.web.context.ContextLoader;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
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
	private Fichero fichero;
	private Fichero eliminaFichero;
	private List<Directorio> listaDirectorios;
	private FormularioFicheros formularioFicheros;

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
		gridFicheros.setCaption("Lista Directorios:");
		gridFicheros.addSelectionListener(new SelectionListener() {

			@Override
			public void select(SelectionEvent event) {
				Set<Object> selected = event.getSelected();
				Fichero fchero =  (Fichero) gridFicheros.getSelectedRow();
				if(fchero!=null){
					eliminaFichero = fchero;
					botonBorrar.setVisible(true);
				}else{
					botonBorrar.setVisible(false);
				}
			}
		});

		// COMBOBOX y BOTONES
		listaDirectorios = cargarLista();
		comboBoxDirectorios = new ComboBox();
		comboBoxDirectorios.setInputPrompt(SELECCIONA);
		comboBoxDirectorios.setFilteringMode(FilteringMode.CONTAINS);
		comboBoxDirectorios.setPageLength(3);
		comboBoxDirectorios.setNullSelectionAllowed(false);
		
		for (int i = 0; i < listaDirectorios.size(); i++) {
			comboBoxDirectorios.addItem(listaDirectorios.get(i).getRuta());	
		}
		comboBoxDirectorios.setNullSelectionAllowed(false);
		comboBoxDirectorios.setImmediate(true);
		
		
		
		botonAgregar = new Button("Añade fichero");
		botonAgregar.setVisible(true);
		botonAgregar.setEnabled(false);

		botonBorrar = new Button("Borrar");
		botonBorrar.setIcon(FontAwesome.ERASER);
		botonBorrar.setVisible(false);
		botonBorrar.addClickListener(e -> {
			borraFichero(eliminaFichero);
			cargaGridFicheros();
		});
		
		
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
	

	
	private List<Directorio> cargarLista() {
		return servicioGestorDirectorios.listaDirectorios();
	}
	public void borraFichero(Fichero fichero){
		servicioGestorFicheros.eliminaFichero(fichero.getId());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Vista Ficheros");
		cargaGridFicheros();
		
	}

	public void cargaGridFicheros() {
		Collection<Fichero> ficheros = servicioGestorFicheros.listaFicheros();
		gridFicheros.setContainerDataSource(new BeanItemContainer<>(Fichero.class, ficheros));
	}

}
