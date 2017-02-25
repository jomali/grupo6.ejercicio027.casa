package es.cic.curso.grupo6.ejercicio027.vista;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
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
		cuerpo.setSpacing(true);
		MenuBar menuNavegacion = new MenuBar();
		
 	

		
		menuNavegacion.addItem("Home", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				cuerpo.removeAllComponents();
				String basepath = VaadinService.getCurrent()
		                  .getBaseDirectory().getAbsolutePath();
				FileResource resource = new FileResource(new File(basepath +
                        "/WEB-INF/images/CIC1.png"));
				Image image = new Image("Gestor de Documentos", resource);
				image.setResponsive(isResponsive());
				
				cuerpo.addComponents(image);
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
				cuerpo.removeAllComponents();
				cuerpo.addComponent( new VistaFolder());
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
