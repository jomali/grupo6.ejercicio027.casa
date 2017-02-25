package es.cic.curso.grupo6.ejercicio027.vista;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
	private static final long serialVersionUID = 1343484792150289272L;

	/** Gestiona una colección de implementaciones de <code>View</code>. */
	Navigator navegador;
	VerticalLayout layout;

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Gestor de Documentos");
		layout = new VerticalLayout();
		// Crea el navegador para controlar las vistas:
		navegador = new Navigator(this, this);

		// Crea y registra las vistas:
		navegador.addView("", new VistaHome(navegador));
	//	layout.addComponentAsFirst(navegador);
	//	this.setContent(layo
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = -2502393197016663089L;
	}

}
