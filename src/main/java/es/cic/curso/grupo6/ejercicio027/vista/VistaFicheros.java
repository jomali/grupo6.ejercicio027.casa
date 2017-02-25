package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.Collection;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;


import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorDirectorios;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class VistaFicheros extends CustomComponent implements View {
	private static final long serialVersionUID = 6825028526184768126L;
	
	private VerticalLayout layout;
	private VerticalLayout cuerpo;
	private ServicioGestorFicheros servicioGestorFicheros;
	private ServicioGestorDirectorios servicioGestorDirectorios;
	private ComboBox lista;
	Button carga = new Button("Carga");
	Button crea = new Button("Crea");
	Button borra = new Button("Borra");
	Button actualiza = new Button("Actualiza");
	private List<Fichero> listaFicheros;
	private List<Fichero> listaFicherosPorDirectorio;
	private List<Directorio> listaDirectorios;
	private static final String SELECCIONA = "Selecciona";
	
	MenuNavegacion vistaMenu = new MenuNavegacion();

	
	public VistaFicheros(Navigator navegador) {
		super();
		
		servicioGestorFicheros = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorFicheros.class);
		servicioGestorDirectorios = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorDirectorios.class);

		listaDirectorios = servicioGestorDirectorios.listaDirectorios();
		listaFicheros = servicioGestorFicheros.listaFicheros();

		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		Grid gridArchivos = new Grid();
		// GRID DE SESIONES:
		
		gridArchivos.setColumns("Directorio", "Archivo", "Descripción", "Versión");
		gridArchivos.setSizeFull();
		gridArchivos.setSelectionMode(SelectionMode.SINGLE);
		layout.addComponent(gridArchivos);
		
		HorizontalLayout layoutHorizontal = new HorizontalLayout();
		layoutHorizontal.setSpacing(true);
		
		
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
		
		carga.addClickListener(clickEvent -> {
//			List<Fichero> listaFicheros = servicioGestorFicheros.listaFicheros();
//			for (int i = 0; i < listaFicheros.size(); i++){
//				grillafiles.addRow(listaFicheros.get(i).getNombre(), listaFicheros.get(i).getDescripcion(), listaFicheros.get(i).getVersion());
//			}
			carga.setVisible(false);

			
		});
//		crea.setVisible(true);
//		borra.setVisible(true);
//		actualiza.setVisible(true);
		layoutHorizontal.addComponents(carga, crea, borra, actualiza);
		layout.addComponent(layoutHorizontal);
		this.setCompositionRoot(layout);

		
	}

	public VistaFicheros(Component compositionRoot) {
		super(compositionRoot);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}


	
}
