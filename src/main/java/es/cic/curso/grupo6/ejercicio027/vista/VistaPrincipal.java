package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

public class VistaPrincipal extends CustomComponent{

	private VerticalLayout layout;
	private VerticalLayout cuerpo;
	
	public VistaPrincipal() {
		super();
		layout = new VerticalLayout();
		layout.setSpacing(true);
		cuerpo = new VerticalLayout();
		MenuBar menuNavegacion = new MenuBar();
 	

		
		menuNavegacion.addItem("Home", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				cuerpo.removeAllComponents();
				cuerpo.addComponent( new Label( "Inicio"));
			}
		});
		
		menuNavegacion.addItem("Files", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				cuerpo.removeAllComponents();
				cuerpo.addComponent( new VistaFiles());
			}
		});
		
		menuNavegacion.addItem("Folders", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
			
			}
		});	
		
		layout.addComponent(menuNavegacion);
		layout.addComponent(cuerpo);
		
		this.setCompositionRoot(layout);
		
		
		
		
		
		
		
		
		
		
	}

	public VistaPrincipal(Component compositionRoot) {
		super(compositionRoot);
		// TODO Auto-generated constructor stub
	}


	
}
