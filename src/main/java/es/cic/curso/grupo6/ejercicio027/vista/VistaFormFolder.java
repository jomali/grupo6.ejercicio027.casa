package es.cic.curso.grupo6.ejercicio027.vista;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import es.cic.curso.grupo6.ejercicio027.modelo.Directorio;
import es.cic.curso.grupo6.ejercicio027.modelo.Fichero;

public class VistaFormFolder extends FormLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5081842639219654799L;

	@PropertyId("ruta")
	protected TextField ruta;
	@PropertyId("descripcion")
	protected TextField descripcion;
	@PropertyId("version")
	protected TextField version;
	
	private Button aceptar;
	private Button cancelar;
	private Directorio directorio;
	
	private VistaFolder padre;
	
	public VistaFormFolder(VistaFolder padre) {
		this.padre = padre;
		
		VerticalLayout verticalPrincipal= new VerticalLayout();

		HorizontalLayout nombreLayout = new HorizontalLayout();
		ruta = new TextField("Route: ");
		ruta.setInputPrompt("Route:");
		nombreLayout.addComponent(ruta);


		HorizontalLayout buttonsLayout = new HorizontalLayout();
		aceptar = new Button("Ok");
		aceptar.setIcon(FontAwesome.SAVE);
		aceptar.addClickListener(e -> {
			//padre.cargaGrid(fichero);
			//TODO Aqui tenemos que hacer que vaya a VistaFile
		});
		cancelar = new Button("Cancel");
		cancelar.setIcon(FontAwesome.CLOSE);
		cancelar.addClickListener(e-> {
			//padre.borrarGrid(fichero);
			//TODO Aqui tenemos que hacer que vaya a VisaHome
		});
		buttonsLayout.addComponents(aceptar, cancelar);
		
		addComponents(verticalPrincipal);
		
		
		//setFichero(null);
	}
}
