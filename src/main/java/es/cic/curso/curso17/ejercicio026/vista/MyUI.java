package es.cic.curso.curso17.ejercicio026.vista;

import javax.servlet.annotation.WebServlet;

import org.springframework.web.context.ContextLoader;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import es.cic.curso.curso17.ejercicio026.modelo.Circulo;
import es.cic.curso.curso17.ejercicio026.modelo.Figura;
import es.cic.curso.curso17.ejercicio026.modelo.Linea;
import es.cic.curso.curso17.ejercicio026.modelo.Rectangulo;
import es.cic.curso.curso17.ejercicio026.servicio.ServicioGestorFiguras;

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

	private ServicioGestorFiguras servicioGestorFiguras;
	
	@Override
	protected void init(VaadinRequest request) {
		servicioGestorFiguras = ContextLoader.getCurrentWebApplicationContext().getBean(ServicioGestorFiguras.class);
		cargaBBDD(); // XXX - Inicializaciones de la demo

		getPage().setTitle("Figuras");

		// Crea el navegador para controlar las vistas:
		navegador = new Navigator(this, this);

		// Crea y registra las vistas:
		navegador.addView("", new VistaFiguras(navegador, servicioGestorFiguras));
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = -2502393197016663089L;
	}
	
	private void cargaBBDD() {
		if (servicioGestorFiguras.listaFiguras().isEmpty()) {
			Figura f1 = new Figura();
			f1.setX(10.0);
			f1.setY(0.0);
			f1.setColor("#002222");
			Linea linea = new Linea();
			linea.setFigura(f1);
			linea.setX2(25.0);
			linea.setY2(25.0);
			servicioGestorFiguras.agregaLinea(linea);

			Figura f2 = new Figura();
			f2.setX(10.0);
			f2.setY(10.0);
			f2.setColor("#FF0000");
			Circulo circulo = new Circulo();
			circulo.setFigura(f2);
			circulo.setRadio(10.0);
			servicioGestorFiguras.agregaCirculo(circulo);

			Figura f3 = new Figura();
			f3.setX(0.0);
			f3.setY(10.0);
			f3.setColor("#00FF00");
			Rectangulo rectangulo1 = new Rectangulo();
			rectangulo1.setFigura(f3);
			rectangulo1.setAncho(25.0);
			rectangulo1.setAlto(10.0);
			servicioGestorFiguras.agregaRectangulo(rectangulo1);

			Figura f4 = new Figura();
			f4.setX(-10.0);
			f4.setY(-20.0);
			f4.setColor("#FFFFFF");
			f4.setRotacion(45);
			Rectangulo rectangulo2 = new Rectangulo();
			rectangulo2.setFigura(f4);
			rectangulo2.setAncho(10.0);
			rectangulo2.setAlto(20.0);
			servicioGestorFiguras.agregaRectangulo(rectangulo2);
		}
	}

}
