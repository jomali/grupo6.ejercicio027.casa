package es.cic.curso.grupo6.ejercicio027.vista;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

public class VistaMenu extends CustomComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -10662430461480165L;
	private VerticalLayout layout;
	private VerticalLayout cuerpo;
	
	public VistaMenu() {
		super();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		cuerpo = new VerticalLayout();
		cuerpo.setMargin(true);
		cuerpo.setSpacing(true);
		MenuBar menuNavegacion = new MenuBar();
		
 	

		
		menuNavegacion.addItem("INICIO", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				cuerpo.removeAllComponents();
				cuerpo.setMargin(true);
				cuerpo.setSpacing(true);
				String basepath = VaadinService.getCurrent()
		                .getBaseDirectory().getAbsolutePath();
				FileResource resource = new FileResource(new File(basepath +
		              "/WEB-INF/images/CIC1.png"));
				Image image = new Image("Gestor de Documentos", resource);
				image.setSizeFull();
				cuerpo.addComponent(image);
			}
		});
		
		menuNavegacion.addItem("ARCHIVOS", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				cuerpo.removeAllComponents();
				cuerpo.setMargin(true);
				cuerpo.setSpacing(true);
				cuerpo.addComponent( new VistaFiles());
			}
		});
		
		menuNavegacion.addItem("CARPETAS", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				cuerpo.removeAllComponents();
				cuerpo.setMargin(true);
				cuerpo.setSpacing(true);
				cuerpo.addComponent( new VistaFolder());
			}
		});	
		
		layout.addComponent(menuNavegacion);
		layout.addComponent(cuerpo);
		
		this.setCompositionRoot(layout);
		
		
		
		
		
		
		
		
		
		
	}

	public VistaMenu(Component compositionRoot) {
		super(compositionRoot);
		// TODO Auto-generated constructor stub
	}


	
}
