package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class VistaPrueba extends VerticalLayout implements View {
	private static final long serialVersionUID = 8801525565672617295L;
		
	// /////////////////////////////////////////////////////////////////////////
	// Referencias  a componentes gráficos
	
	// /////////////////////////////////////////////////////////////////////////
	// Métodos públicos

	public VistaPrueba(Navigator navegador) {
		this.setMargin(true);
		this.setSpacing(true);
		
		Label label = new Label("¡Hola <strong>Mundo</strong>!");
		label.setContentMode(ContentMode.HTML);
		
		VistaPrincipal vista = new VistaPrincipal();
		
				
		
		
	
		addComponent(vista);
		
	}

	
	
	
	@Override
	public void enter(ViewChangeEvent
			event) {

	}
	
	// /////////////////////////////////////////////////////////////////////////
	// Métodos privados

}
