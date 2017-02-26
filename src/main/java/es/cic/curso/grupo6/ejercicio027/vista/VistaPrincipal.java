package es.cic.curso.grupo6.ejercicio027.vista;

import java.io.File;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

public class VistaPrincipal extends VerticalLayout implements View {
	private static final long serialVersionUID = 8801525565672617295L;
	
	public VistaPrincipal(MenuNavegacion menuNavegacion) {
		MenuBar menu = menuNavegacion.creaMenu("");
		
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		// Imagen
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/CIC1.png"));
		Image imagen = new Image(null, resource);
		imagen.setAlternateText("CIC");

		Label label = new Label("Prueba de Evaluación del <strong>Grupo 6</strong>");
		label.setHeight(12.0F, Unit.PIXELS);
		label.setContentMode(ContentMode.HTML);
		Label label2 = new Label("Curso06 - Jose María Cagigas");
		Label label3 = new Label("Curso17 - Jose Francisco Martín");
		layout.addComponents(label, label2, label3, imagen);

		addComponents(menu, layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}
}
