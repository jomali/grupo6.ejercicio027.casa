package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class VistaFiles extends CustomComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6825028526184768126L;
	private VerticalLayout layout;
	private VerticalLayout cuerpo;
	private ServicioGestorFicheros servicioGestorFicheros;
	Button carga = new Button("Carga");
	Button crea = new Button("Crea");
	Button borra = new Button("Borra");
	Button actualiza = new Button("Actualiza");
	ComboBox escogeCarpeta = new ComboBox();
	
	VistaMenu vistaMenu = new VistaMenu();

	
	public VistaFiles() {
		super();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		Grid gridArchivos = new Grid();
		// GRID DE SESIONES:
		
		gridArchivos.setColumns("Ruta del Archivo", "Nombre del Archivo", "Descripción", "Versión");
		gridArchivos.setSizeFull();
		gridArchivos.setSelectionMode(SelectionMode.SINGLE);
		layout.addComponent(gridArchivos);
		
		HorizontalLayout layoutHorizontal = new HorizontalLayout();
		layoutHorizontal.setSpacing(true);
		
		crea.setVisible(false);
		borra.setVisible(false);
		actualiza.setVisible(false);
		
		carga.addClickListener(clickEvent -> {
//			List<Fichero> listaFicheros = servicioGestorFicheros.listaFicheros();
//			for (int i = 0; i < listaFicheros.size(); i++){
//				grillafiles.addRow(listaFicheros.get(i).getNombre(), listaFicheros.get(i).getDescripcion(), listaFicheros.get(i).getVersion());
//			}
			carga.setVisible(false);
			escogeCarpeta.setVisible(true);
			escogeCarpeta.addValueChangeListener(escogeCarpeta);
			
		});
//		crea.setVisible(true);
//		borra.setVisible(true);
//		actualiza.setVisible(true);
		layoutHorizontal.addComponents(carga, crea, borra, actualiza);
		layout.addComponent(layoutHorizontal);
		this.setCompositionRoot(layout);

		
	}

	public VistaFiles(Component compositionRoot) {
		super(compositionRoot);
		// TODO Auto-generated constructor stub
	}


	
}
