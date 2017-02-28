package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class VentanaConfirmacion extends Window {
	private static final long serialVersionUID = -8446817805133401098L;

    public VentanaConfirmacion(String titulo) {
    	super(titulo);
    	setModal(true);
    	setClosable(true);
    	setResizable(false);
    	setDraggable(true);
       	
    	Label label = new Label("Hola <strong>mundo</strong>");
    	label.setContentMode(ContentMode.HTML);
    	
    	final FormLayout content = new FormLayout();
        content.setMargin(true);
        content.addComponent(label);
        
        setContent(content);
    	
    	this.center();
    }

}
