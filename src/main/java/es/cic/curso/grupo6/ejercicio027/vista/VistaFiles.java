package es.cic.curso.grupo6.ejercicio027.vista;

import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorDirectorios;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;

public class VistaFiles extends CustomComponent{

	private VerticalLayout layout;
	private VerticalLayout cuerpo;
	private ServicioGestorFicheros servicioGestorFicheros;
	
	public VistaFiles() {
		super();
		layout = new VerticalLayout();
		
		Grid grillafiles = new Grid();
		// GRID DE SESIONES:
		grillafiles.setColumns("NOmbre", "Descripcion", "Version");
		grillafiles.setSizeFull();
		grillafiles.setSelectionMode(SelectionMode.SINGLE);
		layout.addComponent(grillafiles);
		
		
		Button button = new Button("Charge");
		
		button.addClickListener(clickEvent -> {
			List<Fichero> listaFicheros = servicioGestorFicheros.listaFicheros();
			for (int i = 0; i < listaFicheros.size(); i++){
				grillafiles.addRow(listaFicheros.get(i).getNombre(), listaFicheros.get(i).getDescripcion(), listaFicheros.get(i).getVersion());
			}
			
		});
		
		layout.addComponent(button);
		this.setCompositionRoot(layout);
		
		
		
		
		
		
		
		
		
		
	}

	public VistaFiles(Component compositionRoot) {
		super(compositionRoot);
		// TODO Auto-generated constructor stub
	}


	
}
