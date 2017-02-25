package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class VistaFolder extends CustomComponent{

	private VerticalLayout layout;
	private VerticalLayout cuerpo;
	private ServicioGestorFicheros servicioGestorFicheros;
	Button buttonCharge = new Button("Charge");
	Button buttonCreate = new Button("Create");
	Button buttonDelete = new Button("Delete");
	Button buttonUpdate = new Button("Update");
	
	
	public VistaFolder() {
		super();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		Grid grillafiles = new Grid();
		// GRID DE SESIONES:
		grillafiles.setColumns("Folder Name", "File Name", "Description", "Version");
		grillafiles.setSizeFull();
		grillafiles.setSelectionMode(SelectionMode.SINGLE);
		layout.addComponent(grillafiles);
		
		
		HorizontalLayout layoutHorizontal = new HorizontalLayout();
		layoutHorizontal.setSpacing(true);
		
		buttonCreate.setVisible(false);
		buttonDelete.setVisible(false);
		buttonUpdate.setVisible(false);
		
		buttonCharge.addClickListener(clickEvent -> {
//			List<Fichero> listaFicheros = servicioGestorFicheros.listaFicheros();
//			for (int i = 0; i < listaFicheros.size(); i++){
//				grillafiles.addRow(listaFicheros.get(i).getNombre(), listaFicheros.get(i).getDescripcion(), listaFicheros.get(i).getVersion());
//			}
			buttonCharge.setVisible(false);
			buttonCreate.setVisible(true);
			buttonDelete.setVisible(true);
			buttonUpdate.setVisible(true);
			
		});
		
		layoutHorizontal.addComponents(buttonCharge, buttonCreate, buttonDelete, buttonUpdate);
		layout.addComponent(layoutHorizontal);
		this.setCompositionRoot(layout);
		
		
		
		
		
		
		
		
		
		
	}

	public VistaFolder(Component compositionRoot) {
		super(compositionRoot);
		// TODO Auto-generated constructor stub
	}


	
}
