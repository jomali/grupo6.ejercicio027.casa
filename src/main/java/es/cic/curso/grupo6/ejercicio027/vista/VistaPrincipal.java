package es.cic.curso.grupo6.ejercicio027.vista;

import java.io.File;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorDirectorios;
import es.cic.curso.grupo6.ejercicio027.servicio.ServicioGestorFicheros;

public class VistaPrincipal extends VerticalLayout implements View {
	private static final long serialVersionUID = 8801525565672617295L;

	public VistaPrincipal(Navigator navegador) {
		MenuNavegacion vista = new MenuNavegacion(navegador);

		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/CIC1.png"));
		Image image = new Image("Gestor de Documentos", resource);
		image.setSizeFull();

		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		Label label = new Label("Prueba de Evaluación del Grupo 6");
		Label label2 = new Label("Curso06 - Jose María Cagigas");
		Label label3 = new Label("Curso17 - Jose Francisco Martín");
		layout.addComponents(label, label2, label3);

		addComponents(vista, image, layout);

	}

	@Override
	public void enter(ViewChangeEvent event) {

	}
}
