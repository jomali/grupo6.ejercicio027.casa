package es.cic.curso.grupo6.ejercicio027.vista;

import java.io.File;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import com.vaadin.ui.VerticalLayout;

public class VistaHome extends VerticalLayout implements View {
 	private static final long serialVersionUID = 8801525565672617295L;
 		public VistaHome(Navigator navegador) {

		VistaMenu vista = new VistaMenu();
		
		
		
	
		addComponents(vista);
	
 	}
	
 	@Override
	public void enter(ViewChangeEvent event) {

 	}
}
