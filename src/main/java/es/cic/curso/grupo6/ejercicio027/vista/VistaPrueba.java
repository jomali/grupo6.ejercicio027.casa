package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class VistaPrueba extends VerticalLayout implements View {
 	private static final long serialVersionUID = 8801525565672617295L;
 		public VistaPrueba(Navigator navegador) {

		VistaPrincipal vista = new VistaPrincipal();
		
	
		addComponent(vista);
	
 	}
	
 	@Override
	public void enter(ViewChangeEvent event) {

 	}
}
