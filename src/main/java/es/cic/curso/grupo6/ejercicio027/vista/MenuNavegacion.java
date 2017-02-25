package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

public class MenuNavegacion extends CustomComponent {
	private static final long serialVersionUID = -4651426196089860317L;

	private Navigator navegador;

	private VerticalLayout layout;
	private VerticalLayout cuerpo;
	
	@SuppressWarnings("serial")
	public MenuNavegacion(Navigator navegador) {
		super();
		this.navegador = navegador;
		
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
				navegador.navigateTo("");
			}
		});
		
		menuNavegacion.addItem("DIRECTORIOS", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo(MyUI.VISTA_DIRECTORIOS);
			}
		});
		
		menuNavegacion.addItem("FICHEROS", new Command() {
			@Override
			public void menuSelected(final MenuItem selectedItem) {
				navegador.navigateTo(MyUI.VISTA_FICHEROS);
			}
		});	
		
		layout.addComponent(menuNavegacion);
		layout.addComponent(cuerpo);
		
		this.setCompositionRoot(layout);	
	}
	
}
