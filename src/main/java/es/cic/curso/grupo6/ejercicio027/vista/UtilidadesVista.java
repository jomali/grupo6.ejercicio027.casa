package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class UtilidadesVista {
	
	public static Window creaVentanaModal(String mensaje) {
		Window resultado = new Window();
		resultado.setWidth(350.0F, Unit.PIXELS);
		resultado.setModal(true);
		resultado.setClosable(false);
		resultado.setResizable(false);
		resultado.setDraggable(false);

		Label label = new Label(mensaje);
		label.setContentMode(ContentMode.HTML);

		Button botonAceptar = new Button("Aceptar");
		botonAceptar.addClickListener(e -> resultado.close() );

		HorizontalLayout layoutBotones = new HorizontalLayout();
		layoutBotones.setMargin(true);
		layoutBotones.setSpacing(true);
		layoutBotones.setWidth(100.0F, Unit.PERCENTAGE);
		layoutBotones.addComponents(botonAceptar);

		final FormLayout content = new FormLayout();
		content.setMargin(true);
		content.addComponents(label, layoutBotones);
		resultado.setContent(content);
		resultado.center();
		return resultado;
	}

}
